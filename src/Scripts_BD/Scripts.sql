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
INSERT INTO sedes(direccion,nombre,telefono,empleado_a_cargo) VALUES ('Cll 15 #13-06','San Fernando',5359834,null);

INSERT INTO empleados
	VALUES (3743, crypt('1234', gen_salt('md5')), 'Pepito', 'Perez', 'Cra 100 #86-06', 31273954335, 'pepito.perez@xyz.com','Jefe de taller',1,true);
INSERT INTO empleados
	VALUES (3225, crypt('1234', gen_salt('md5')), 'Manuela', 'Diaz', 'Cra 101 #85-05', 3127325837,'manuela.diaz@xyz.com','Jefe de taller',2,true);
INSERT INTO empleados
	VALUES (3653, crypt('1234', gen_salt('md5')),'Melissa', 'Fuentes' , 'Cra 102 #84-05', 3127399281,'melissa.fuentes@xyz.com','Vendedor',1,true);
INSERT INTO empleados
	VALUES (3414, crypt('1234', gen_salt('md5')), 'Jempool' , 'Rivera', 'Cra 103 #83-04', 3205465535,'valeria.rivera@xyz.com','Vendedor',1,true);
INSERT INTO empleados
	VALUES (3369, crypt('1234', gen_salt('md5')), 'Felipe', 'Gil', 'Cra 104 #82-03', 3126249102,'felipin.gil@xyz.com','Vendedor',2,true);

UPDATE sedes SET empleado_a_cargo = 3743 where id_sede = 1;
UPDATE sedes SET empleado_a_cargo = 3225 where id_sede = 2;

INSERT INTO producto(nombre,costo,precio,descripcion) VALUES('Nochero',50000,150000,'Mesa de noche de tama�o mediano');
INSERT INTO producto(nombre,costo,precio,descripcion) VALUES('Camarote',150000,530000,'Cama de dos pisos :v');
INSERT INTO producto(nombre,costo,precio,descripcion) VALUES('Silla',30000,50000,'Silla de 4 patas');

UPDATE inventario SET cantidad_disponible=500 WHERE id_sede=1;
