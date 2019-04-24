--Tabla Sedes
DROP TABLE IF EXISTS sedes CASCADE;
CREATE TABLE sedes(
   id_sede        SERIAL PRIMARY KEY,
   direccion      CHAR(50) NOT NULL,
   nombre         CHAR(20) NOT NULL,
   telefono	  BIGINT NOT NULL,
   empleado_a_cargo    BIGINT    --foreing key
);


-- empleados --
CREATE EXTENSION IF NOT EXISTS pgcrypto; --to enable pgcrypto
DROP TABLE IF EXISTS empleados CASCADE;
CREATE TABLE empleados (
	cedula		BIGINT NOT NULL PRIMARY KEY,
	contrasena 	TEXT NOT NULL,
	nombres		CHAR(28) NOT NULL,
	apellidos 	CHAR(28) ,
	direccion 	CHAR(50) ,
	numero		BIGINT NULL,
	email	 	TEXT NOT NULL,
	tipo_usuario 	CHAR(14) NOT NULL,
	sede	 	INT NOT NULL REFERENCES sedes(id_sede),
	activo  	BOOL NOT NULL
CHECK (tipo_usuario IN ('Vendedor', 'Jefe de taller'))
);

--Tabla Producto
DROP TABLE IF EXISTS producto CASCADE;
CREATE TABLE producto(
   id_producto    SERIAL PRIMARY KEY,
   nombre 	  CHAR(20) NOT NULL,
   costo      BIGINT NOT NULL,
   precio         BIGINT NOT NULL,
   descripcion    TEXT
);

--Tabla Ordenes_de_Trabajo
DROP TABLE IF EXISTS ordenes_de_trabajo CASCADE;
CREATE TABLE ordenes_de_trabajo(
   id_ordenes     SERIAL PRIMARY KEY,
   asignada_a     TEXT NOT NULL,
   fecha_entrega  DATE NOT NULL,
   cantidad       INT NOT NULL,
   finalizada     BOOL NOT NULL,
   id_producto    BIGINT NOT NULL  REFERENCES producto (id_producto),
   id_usuario     BIGINT NOT NULL  REFERENCES empleados (cedula)
);

--Tabla Venta_Cotizaciones
DROP TABLE IF EXISTS venta_cotizaciones CASCADE;
CREATE TABLE venta_cotizaciones (
   	id_cotizacion    SERIAL PRIMARY KEY,
   	id_empleado      BIGINT NOT NULL  REFERENCES empleados (cedula),
   	fecha_cotizacion DATE,
   	nombre_cotizante TEXT,
	precio_final     BIGINT NOT NULL,
	tipo 			CHAR(1) NOT NULL
CHECK (tipo IN ('C', 'V'))
);

--Tabla Usuario_Producto
DROP TABLE IF EXISTS inventario CASCADE;
CREATE TABLE inventario (
   id_sede            BIGINT NOT NULL  REFERENCES sedes (id_sede),
   id_producto         BIGINT NOT NULL  REFERENCES producto (id_producto),
   cantidad_disponible INT NOT NULL

);
alter table inventario
  add constraint no_negativos
  check (cantidad_disponible>=0);

--Tabla Ventas_Cotizaciones_Producto
DROP TABLE IF EXISTS ventas_cotizaciones_producto CASCADE;
CREATE TABLE ventas_cotizaciones_producto (
   id_producto      BIGINT NOT NULL  REFERENCES producto (id_producto),
   cantidad_compra  INT NOT NULL,
   id_cotizacion    BIGINT NOT NULL  REFERENCES venta_cotizaciones (id_cotizacion)
);

DROP TABLE IF EXISTS venta CASCADE;
DROP TABLE IF EXISTS venta_info CASCADE;
DROP FUNCTION IF EXISTS restar_inv();

--Constraints
ALTER TABLE ventas_cotizaciones_producto
DROP CONSTRAINT ventas_cotizaciones_producto_id_cotizacion_fkey;

ALTER TABLE  ventas_cotizaciones_producto
ADD CONSTRAINT ventas_cotizaciones_producto_id_cotizacion_fkey
	FOREIGN KEY (id_cotizacion) REFERENCES venta_cotizaciones ON DELETE CASCADE;

--Crear disparadores y constraints

CREATE OR REPLACE FUNCTION crearInventario() RETURNS TRIGGER AS $$
DECLARE
	   row producto%rowtype;
BEGIN
	FOR row IN (SELECT id_producto FROM producto) LOOP
		INSERT INTO inventario(id_sede,id_producto,cantidad_disponible) VALUES(NEW.id_sede,row.id_producto,0);
	END LOOP;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER initInventario AFTER INSERT ON sedes FOR EACH ROW EXECUTE PROCEDURE crearInventario();

CREATE OR REPLACE FUNCTION anadirProducto() RETURNS TRIGGER AS $$
DECLARE
	   row sedes%rowtype;
BEGIN
	FOR row IN (SELECT id_sede FROM sedes) LOOP
		INSERT INTO inventario(id_sede,id_producto,cantidad_disponible) VALUES(row.id_sede,NEW.id_producto,0);
	END LOOP;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER nuevoProducto AFTER INSERT ON producto FOR EACH ROW EXECUTE PROCEDURE anadirProducto();

-- Trigger que me actualiza el inventario en caso de que se efectue una compra
CREATE OR REPLACE FUNCTION actualizarInventario() RETURNS TRIGGER AS $$
DECLARE
	   row ventas_cotizaciones_producto%rowtype;
BEGIN
IF(NEW.tipo = 'V') THEN
	FOR row IN (SELECT id_producto,cantidad_compra FROM ventas_cotizaciones_producto WHERE id_cotizacion=NEW.id_cotizacion) LOOP
		UPDATE inventario SET cantidad_disponible=cantidad_disponible-row.cantidad_compra WHERE id_producto=row.id_producto AND id_sede=(SELECT sede FROM empleados WHERE cedula=NEW.id_empleado);
	END LOOP;
END IF;
RETURN NEW;
END
$$ LANGUAGE plpgsql;

CREATE TRIGGER reduInve AFTER UPDATE ON venta_cotizaciones FOR EACH ROW EXECUTE PROCEDURE actualizarInventario();

--Insertar valores a la tabla


INSERT INTO sedes(direccion,nombre,telefono,empleado_a_cargo) VALUES ('Cra 100 #86-03','Melendez',3890543,null);
INSERT INTO sedes(direccion,nombre,telefono,empleado_a_cargo) VALUES ('Calle 22 #13-06','San Fernando',5359834,null);
INSERT INTO sedes(direccion,nombre,telefono,empleado_a_cargo) VALUES ('Cra 66 #86-3','Jamundi',3890543,null);
INSERT INTO sedes(direccion,nombre,telefono,empleado_a_cargo) VALUES ('Cll 15 #13N-06','Palmira',5359834,null);


INSERT INTO empleados
	VALUES (1670129, crypt('1234', gen_salt('md5')), 'Steban', 'Cadena', 'Calle 33 #86-06', 3222204261, 'cadena.esteban@xyz.com','Jefe de taller',1,true);
INSERT INTO empleados
	VALUES (1629338, crypt('1234', gen_salt('md5')), 'Daniel', 'Diaz', 'Cra 10 #8-05', 3017829351,'daniel.diaz@xyz.com','Jefe de taller',2,true);
INSERT INTO empleados
	VALUES (1628344, crypt('1234', gen_salt('md5')),'Cristina', 'Mejia' , 'Calle 12 #89-105', 3137379109,'cristina.mejia@xyz.com','Vendedor',1,true);
INSERT INTO empleados
	VALUES (1630536, crypt('1234', gen_salt('md5')), 'Jempool' , 'Suarez', 'Cra 103 #83-04', 3148432071,'jem.suarez@xyz.com','Vendedor',1,true);
INSERT INTO empleados
	VALUES (1, crypt('1234', gen_salt('md5')), 'Nicola', 'Tesla', 'Cra 104 #82-03', 3003003000,'nicola.tesla@xyz.com','Jefe de taller',2,true);
INSERT INTO empleados
	VALUES (2, crypt('1234', gen_salt('md5')), 'Thomas', 'Edison', 'Cra 55 #52N-93', 3004004000,'thomas.edison@xyz.com','Vendedor',2,true);
INSERT INTO empleados
	VALUES (289, crypt('1234', gen_salt('md5')), 'Brayan', 'Chaparro', 'Cra 56 #52N-93', 3004004000,'brayan.chaparro@xyz.com','Jefe de taller',3,true);
INSERT INTO empleados
	VALUES (30, crypt('1234', gen_salt('md5')), 'Dario', 'Rojas', 'Cra 45 #52N-93', 3004004000,'dario.rojas@xyz.com','Vendedor',3,true);
INSERT INTO empleados
	VALUES (45, crypt('1234', gen_salt('md5')), 'Steven', 'Morales', 'Cra 86 #52N-93', 3004004000,'steven.morales@xyz.com','Jefe de taller',4,true);
INSERT INTO empleados
	VALUES (308, crypt('1234', gen_salt('md5')), 'Margarita', 'Osorio', 'Cra 45 #52N-93', 3004004000,'margarita.osorio@xyz.com','Vendedor',4,true);


UPDATE sedes SET empleado_a_cargo = 1670129 where id_sede = 1;
UPDATE sedes SET empleado_a_cargo = 1629338 where id_sede = 2;
UPDATE sedes SET empleado_a_cargo = 1628344 where id_sede = 3;
UPDATE sedes SET empleado_a_cargo = 1630536 where id_sede = 4;



INSERT INTO producto(nombre,costo,precio,descripcion) VALUES('Nochero',50000,150000,'Madera 50x50x80');
INSERT INTO producto(nombre,costo,precio,descripcion) VALUES('Camarote',150000,530000,'Madera 120X100X180');
INSERT INTO producto(nombre,costo,precio,descripcion) VALUES('Silla',34000,59000,'Mecedora 80X90X100');
INSERT INTO producto(nombre,costo,precio,descripcion) VALUES('Silla',40000,70000,'Estudio 70X90X80');
INSERT INTO producto(nombre,costo,precio,descripcion) VALUES('Sofa',1000000,1400000,'Espuma rosada 220X100X80');
INSERT INTO producto(nombre,costo,precio,descripcion) VALUES('Escritorio',2000000,2400000,'Madera de roble');
INSERT INTO producto(nombre,costo,precio,descripcion) VALUES('Mecedora',102000,140000,'Madera y espuma cafe');



INSERT INTO public.ordenes_de_trabajo( asignada_a, fecha_entrega, cantidad, finalizada, id_producto, id_usuario)
VALUES ('pedro zamora', '2019-10-05', 15, false, 1, 1630536);
INSERT INTO public.ordenes_de_trabajo( asignada_a, fecha_entrega, cantidad, finalizada, id_producto, id_usuario)
VALUES ('carlos perez', '2019-01-16', 15, true, 2, 1629338);
INSERT INTO public.ordenes_de_trabajo( asignada_a, fecha_entrega, cantidad, finalizada, id_producto, id_usuario)
VALUES ('Roberto Sanchez', '2019-05-16', 45, true, 3, 1629338);
INSERT INTO public.ordenes_de_trabajo( asignada_a, fecha_entrega, cantidad, finalizada, id_producto, id_usuario)
VALUES ('Camilo Rodriguez', '2019-10-06', 450, true, 4, 1670129);
INSERT INTO public.ordenes_de_trabajo( asignada_a, fecha_entrega, cantidad, finalizada, id_producto, id_usuario)
VALUES ('Mario Mejia', '2019-11-06', 4500, true, 5, 1670129);
INSERT INTO public.ordenes_de_trabajo( asignada_a, fecha_entrega, cantidad, finalizada, id_producto, id_usuario)
VALUES ('Agelica Ramirez', '2019-08-09', 80, true, 6, 1670129);
INSERT INTO public.ordenes_de_trabajo( asignada_a, fecha_entrega, cantidad, finalizada, id_producto, id_usuario)
VALUES ('Alejandro Pinerez', '2019-08-05', 800, true, 7, 1670129);



UPDATE inventario SET cantidad_disponible=500 WHERE id_sede=1;
UPDATE inventario SET cantidad_disponible=400 WHERE id_sede=2;
UPDATE inventario SET cantidad_disponible=600 WHERE id_sede=3;
UPDATE inventario SET cantidad_disponible=800 WHERE id_sede=4;

insert into venta_cotizaciones(id_empleado,fecha_cotizacion,nombre_cotizante,precio_final,tipo) values(1628344, '2019-05-16' ,'carolina herrera',28000000,'C');
insert into venta_cotizaciones(id_empleado,fecha_cotizacion,nombre_cotizante,precio_final,tipo) values(1628344, '2019-06-16' ,'Ramiro Cuellar',2800000,'V');
insert into venta_cotizaciones(id_empleado,fecha_cotizacion,nombre_cotizante,precio_final,tipo) values(1630536, '2019-07-16' ,'Carlos Huertado',98000,'C');
insert into venta_cotizaciones(id_empleado,fecha_cotizacion,nombre_cotizante,precio_final,tipo) values(1630536, '2019-06-16' ,'Ramiro Cuellar',7000000,'V');
insert into venta_cotizaciones(id_empleado,fecha_cotizacion,nombre_cotizante,precio_final,tipo) values(30, '2018-06-14' ,'Mauricio Lopez',1400000,'C');
insert into venta_cotizaciones(id_empleado,fecha_cotizacion,nombre_cotizante,precio_final,tipo) values(30, '2018-07-16' ,'Maria Molina',700000,'V');

insert into ventas_cotizaciones_producto values(1,260,1);
insert into ventas_cotizaciones_producto values(2,26,1);
insert into ventas_cotizaciones_producto values(3,2,1);

insert into ventas_cotizaciones_producto values(3,26,2);
insert into ventas_cotizaciones_producto values(4,260,2);
insert into ventas_cotizaciones_producto values(7,2,2);

insert into ventas_cotizaciones_producto values(5,2,3);
insert into ventas_cotizaciones_producto values(6,1,3);
insert into ventas_cotizaciones_producto values(4,26,3);

insert into ventas_cotizaciones_producto values(1,26,4);
insert into ventas_cotizaciones_producto values(2,200,4);

insert into ventas_cotizaciones_producto values(6,2,5);
insert into ventas_cotizaciones_producto values(1,10,5);

insert into ventas_cotizaciones_producto values(7,6,6);
insert into ventas_cotizaciones_producto values(1,9,6);
insert into ventas_cotizaciones_producto values(5,8,6);

----------VISTAS--------
DROP VIEW IF EXISTS imformeProducto;
CREATE VIEW informeProducto AS (SELECT id_producto, nombre, cantidad_compra,fecha_cotizacion,sede
   FROM ventas_cotizaciones_producto NATURAL JOIN venta_cotizaciones NATURAL JOIN producto INNER JOIN empleados ON empleados.cedula = venta_cotizaciones.id_empleado);

DROP VIEW IF EXISTS informeInventario;
CREATE VIEW informeInventario AS (SELECT id_producto,nombre, cantidad_disponible, id_sede
	FROM inventario NATURAL JOIN producto);
