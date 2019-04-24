
import java.sql.*;

public class BaseDeDatos {

	//Constantes para acceder a la base de datos
	private final static String DB = "xyz";
	private final static String USUARIO = "postgres";
	private final static String PASSWORD = "1234";
	private final static String URL = "jdbc:postgresql://localhost:5432/" + DB;

    //Método usado para insertar un usuario en la base de datos, retorna true si fue insertado
    //correctamente y false si ocurrio un error
	public boolean insertarUsuario(String id,String contrasena, String nombre,
			String apellido,String direccion,String eMail,
			String tipoUsuario, String sede, String celular){

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

			System.out.println("Establishing connection with the database...");
			@SuppressWarnings("unused")
			Statement statement = connection.createStatement();
			System.out.println("Connected to PostgreSQL database!");

			String sql ="INSERT INTO public.empleados(cedula, contrasena, nombres,"+
					" apellidos, direccion, numero, email, tipo_usuario,"+
					"sede, activo) VALUES ('" +
					id + "', crypt('" + contrasena + "', gen_salt('md5')),'" +
					nombre + "','" + apellido + "','" + direccion + "'," +
					celular + ",'" + eMail + "','"+ tipoUsuario + "','" +
					sede + "'," +"true);";

			PreparedStatement psSql = connection.prepareStatement(sql);
			psSql.execute();

			return true;
		}
		catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
			return false;
		}
	}


	//Verifica que el id de un empleado se encuentre en la Base de Datos
	public boolean verificarId(int id) {

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

			String sql ="SELECT * from empleados";
			PreparedStatement psSql = connection.prepareStatement(sql);
			ResultSet rs = psSql.executeQuery();
			int verify = 0;

			while (rs.next()){
				if(Integer.parseInt(rs.getString("cedula"))==id){
					verify++;
				}
			}

			if(verify==1){
				return true;
			}
			else return false;
		}
		catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
			return false;
		}
	}

	//Verifica que el id de una sede se encuentre en la Base de Datos
	public boolean verificarIdSede(int id) {

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

			String sql ="SELECT * from sedes";
			PreparedStatement psSql = connection.prepareStatement(sql);
			ResultSet rs = psSql.executeQuery();
			int verify = 0;

			while (rs.next()){
				if(Integer.parseInt(rs.getString("id_sede"))==id){
					verify++;
				}
			}

			if(verify==1){
				return true;
			}
			else return false;
		}
		catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
			return false;
		}
	}

	//Método para obtener un elemento de tipo String perteneciente al valor de algún atributo de un empleado.
	public String obtenerS(String identifier, String campo) {

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

			String sql ="SELECT "+campo+" FROM empleados WHERE cedula = "+identifier;
			PreparedStatement psSql = connection.prepareStatement(sql);
			ResultSet rs = psSql.executeQuery();

			System.out.print(sql);

			rs.next();
			String resultado = rs.getString(1);
			return resultado.trim();
		}
		catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
			return "";
		}
	}

	//Método para obtener un elemento de tipo String perteneciente al valor de algún atributo de una sede.
	public String obtenerSede(String identifier, String campo) {

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

			String sql ="SELECT "+campo+" FROM sedes WHERE id_sede = "+identifier;
			PreparedStatement psSql = connection.prepareStatement(sql);
			ResultSet rs = psSql.executeQuery();

			rs.next();
			String resultado = rs.getString(1);
			if (resultado!=null)resultado=resultado.trim();
			return resultado;
		}
		catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
			return "";
		}
	}

	//Metodo para obtener un elemento de tipo boolean perteneciente al valor de algún atributo.
	public boolean obtenerB(String identifier, String campo) {

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

			String sql ="SELECT "+campo+" FROM empleados WHERE cedula = "+identifier;
			PreparedStatement psSql = connection.prepareStatement(sql);
			ResultSet rs = psSql.executeQuery();

			rs.next();
			boolean resultado = rs.getBoolean(1);
			return resultado;
		}
		catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
			return false;
		}
	}

	//Metodo para actualizar como inactivo a un empleado en la base de datos
	private void eliminarEncargado(String encargadoId){
        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
            String sql ="UPDATE sedes SET empleado_a_cargo = "+null+
                " WHERE empleado_a_cargo = "+encargadoId+";";

            System.out.print(sql);
            PreparedStatement psSql = connection.prepareStatement(sql);
            psSql.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }


	//Metodo para actualizar datos de un empleado
	public boolean actualizarUsuario(String identifier, String nombres, String apellidos,
 			String direccion, String celular, String email, String tipo, String sede, boolean activo,String password) {

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
			String sql ="UPDATE empleados SET nombres = '"+nombres+
					"', apellidos = '"+apellidos+"', direccion = '"+direccion+"', numero = "+celular+
					", email = '"+email+"', tipo_usuario = '"+tipo+"', sede = '"+sede+"', activo = "+activo;
			if(password!=null) sql+=", contrasena = crypt('" + password + "', gen_salt('md5'))";

            sql+=" WHERE cedula = "+identifier+";";

			PreparedStatement psSql = connection.prepareStatement(sql);
			psSql.executeUpdate();

			if(!activo)eliminarEncargado(identifier);

			return true;
		}
		catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
			return false;
		}
	}

    //Metodo para actualizar datos de una sede con un identificador en especifico
	public boolean actualizarSede(String identifier, String direccion, String telefono, String empleadoACargo) {

	    try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
			String sql ="UPDATE sedes SET direccion = '"+direccion+"', telefono = "+telefono+
					", empleado_a_cargo = "+empleadoACargo+" WHERE id_sede = "+identifier+";";

			System.out.print(sql);
			PreparedStatement psSql = connection.prepareStatement(sql);
			psSql.executeUpdate();

			return true;
		}
		catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
			return false;
		}
	}

    //Metodo para actualizar datos de una orden de trabajo con un identificador en especifico
	public boolean actualizarOrden(String identifier,String fechaEntrega, String cantidad,String idProducto,
                                   String asignadoA, String finalizada){

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
			String sql ="UPDATE ordenes_de_trabajo SET asignada_a = '"+asignadoA+"', fecha_entrega = '"+fechaEntrega+
					"', cantidad = "+cantidad+", finalizada = "+finalizada+", id_producto = "+idProducto
                    +" WHERE id_ordenes = "+identifier+";";
			System.out.print(sql);

			PreparedStatement psSql = connection.prepareStatement(sql);
			psSql.executeUpdate();

			return true;
		}
		catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
			return false;
		}
	}


	//Funcion para obtener una lista de usuarios segun un 'criterio' de busqueda
	//y una palabra clave, en este caso llamada 'busqueda'
	public String[][] consultarUsuarios(String criterio, String busqueda,String campos) {
        String sql = "SELECT "+campos+" FROM public.empleados";

		if(criterio=="Id") sql += " WHERE cedula = " + busqueda;
		if(criterio=="Nombres") sql += " WHERE nombres = '" + busqueda + "'";
		if(criterio=="Apellidos") sql += " WHERE apellidos = '" + busqueda + "'";
        if(criterio=="Sede") sql += " WHERE activo=true AND sede = " + busqueda ;
		if(criterio=="Activo") sql += " WHERE activo=true ";

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = statement.executeQuery(sql);

			resultSet.last();
			int rows = resultSet.getRow(); //# de filas resulado de la consulta
			resultSet.beforeFirst();

			Array arraySQL = null;
			int columns = contarCampos(campos); //# de columnas a mostrar (predeterminado)
			String[][] resultadoConsulta = new String[rows][columns];

			int j = 0;
			while (resultSet.next()) {
				for (int i = 0; i < columns; i++) {
					arraySQL = resultSet.getArray(i+1);
					resultadoConsulta[j][i] = arraySQL.toString().trim();
				} j++;
			}

			return resultadoConsulta;
		}
		catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
			return null;
		}
	}

    //Metodo para consultar unos campos en especifico de una sede dato un criterio de busqueda
	public String[][] consultarSede(String busqueda,String campos) {
		String sql = "SELECT "+ campos
				+ " FROM public.sedes";

        int columns = contarCampos(campos);

		if(busqueda!=null)sql += " WHERE id_sede = " + busqueda;

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = statement.executeQuery(sql);

			resultSet.last();
			int rows = resultSet.getRow(); //# de filas resulado de la consulta
			resultSet.beforeFirst();

			Array arraySQL = null;
			String[][] resultadoConsulta = new String[rows][columns];

			int j = 0;
			while (resultSet.next()) {
				for (int i = 0; i < columns; i++) {
					arraySQL = resultSet.getArray(i+1);
					if(arraySQL!=null) resultadoConsulta[j][i] = arraySQL.toString();
					else resultadoConsulta[j][i]=null;

				} j++;
			}

			return resultadoConsulta;
		}
		catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
			return null;
		}
	}

    //Metodo para consultar unos campos en especifico de una orden de trabajo dato un criterio de busqueda
	public String[][] consultarOrden(String busqueda,String campos) {
		String sql = "SELECT "+ campos
				+ " FROM public.ordenes_de_trabajo NATURAL JOIN producto";
		int columns = contarCampos(campos);

		if(busqueda!=null)sql += " WHERE id_ordenes = " + busqueda;

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = statement.executeQuery(sql);

			resultSet.last();
			int rows = resultSet.getRow(); //# de filas resulado de la consulta
			resultSet.beforeFirst();

			Array arraySQL = null;
			String[][] resultadoConsulta = new String[rows][columns];

			int j = 0;
			while (resultSet.next()) {
				for (int i = 0; i < columns; i++) {
					arraySQL = resultSet.getArray(i+1);
					if(arraySQL!=null) resultadoConsulta[j][i] = arraySQL.toString();
					else resultadoConsulta[j][i]=null;

				} j++;
			}

			return resultadoConsulta;
		}
		catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
			return null;
		}
	}

    //Funcion para contar la cantidad de campos a consultar usando
    //el caracter ',' como referencia
    private int contarCampos(String campos){
        int contador=0;
        for (int i=0;i< campos.length();i++){
            if (campos.charAt(i)==',')contador++;
        }
        return contador+1;
    }

    //Funcion para transformar matrices bidimensionales a unidimensionales
    //juntando dos atributos
    public String[] cambiarDimension(String[][] array){
        String[] aux = new String[array.length];
        for (int i=0;i < array.length;i++){

            aux[i]=array[i][0].trim()+"-"+array[i][1].trim();

        }
        return aux;
    }

    //Funcion para transformar matrices bidimensionales a unidimensionales
    public String[] cambiarDimension2(String[][] array){
        String[] aux = new String[array.length];
        for (int i=0;i < array.length;i++){

            aux[i]=array[i][0].trim();

        }
        return aux;
    }

	//Funcion para acceder a la base de datos y registrar una sede
	//Si la operacion se realiza con exito retorna true y en caso contrario false
	public boolean registraSede( String direccion,String telefono,
			String idEncargado,String nombre){

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
			@SuppressWarnings("unused")
			Statement statement = connection.createStatement();
			String sql ="INSERT INTO public.sedes(nombre, direccion, telefono, "
					+ "empleado_a_cargo) VALUES ('"+ nombre+ "','"+ direccion +"',"+
					telefono +","+ idEncargado +");";
			System.out.print(sql);
			PreparedStatement psSql = connection.prepareStatement(sql);
			psSql.execute();

			return true;
		}
		catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
			return false;
		}
	}



    //Funcion que valida el login retornando el tipo de usuario en caso de ser un login valido
    // o retornando un string vacion en otro caso
	public String validarLogin( String user, String pass){
        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

            String sql ="SELECT * FROM empleados WHERE cedula = '"+ user
                        + "' AND contrasena = "
                        + " crypt('"+pass+"',contrasena) ";

            PreparedStatement psSql = connection.prepareStatement(sql);
            ResultSet result = psSql.executeQuery();

            result.next();

            if (result.getBoolean("activo"))
            	return result.getString("tipo_usuario");
            else return "";
        }
        catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
            return "";
        }

    }

	//Funcion para registrar un producto nuevo producto en el inventario de la empresa y de cada sede
    public boolean registrarProducto( String nombre, String costo, String precio,String descripcion){
        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
            @SuppressWarnings("unused")
            Statement statement = connection.createStatement();
            String sql ="INSERT INTO public.producto(nombre, costo,precio, descripcion) "+
                    " VALUES ('"+ nombre+ "',"+costo+ ","+ precio +",'"+descripcion+"');";
            System.out.print(sql);
            PreparedStatement psSql = connection.prepareStatement(sql);
            psSql.execute();

            return true;
        }
        catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
            return false;
        }

    }


    //Funcion para crear una nueva orden de trabajo
    public boolean crearOrdendeTrabajo(String fechaEntrega, String cantidad,
    		String idProducto, String idEncargado, String asignadoA, String finalizada){

    	try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
            @SuppressWarnings("unused")
            Statement statement = connection.createStatement();
            String sql ="INSERT INTO public.ordenes_de_trabajo( asignada_a, "
            		+ "fecha_entrega, cantidad, finalizada, id_producto, id_usuario) "+
                    " VALUES ('"+ asignadoA +"','"+ fechaEntrega + "',"+ cantidad +"," +
            		finalizada + "," + idProducto + ","+ idEncargado +");";
            System.out.print(sql);
            PreparedStatement psSql = connection.prepareStatement(sql);
            psSql.execute();

            return true;
        }
        catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
            return false;
        }
    }

    //Funcion para consultar ciertos campos de los productos
  	public String[][] consultarProductos(String campos) {
          String sql = "SELECT "+campos+" FROM public.producto";

  		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
  			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
  					ResultSet.CONCUR_READ_ONLY);
  			ResultSet resultSet = statement.executeQuery(sql);

  			resultSet.last();
  			int rows = resultSet.getRow(); //# de filas resulado de la consulta
  			resultSet.beforeFirst();

  			Array arraySQL = null;
  			int columns = contarCampos(campos); //# de columnas a mostrar (predeterminado)
  			String[][] resultadoConsulta = new String[rows][columns];

  			int j = 0;
  			while (resultSet.next()) {
  				for (int i = 0; i < columns; i++) {
  					arraySQL = resultSet.getArray(i+1);
  					resultadoConsulta[j][i] = arraySQL.toString().trim();
  				} j++;
  			}

  			return resultadoConsulta;
  		}
  		catch (SQLException e) {
  			System.out.println("Connection failure");
  			e.printStackTrace();
  			return null;
  		}
  	}

  	//Funcion para consultar campos de la orden de trabajo dado un criterio de busqueda
    public String[][] consultarOrden(String criterio, String busqueda,String campos) {
        String sql = "SELECT "+campos+" FROM public.ordenes_de_trabajo natural join public.producto" +
                     " WHERE finalizada=false";

        if(criterio=="User") sql += " AND id_usuario = " + busqueda;
        if(criterio=="Id") sql += " AND id_ordenes = " + busqueda ;

        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(sql);

            resultSet.last();
            int rows = resultSet.getRow(); //# de filas resulado de la consulta
            resultSet.beforeFirst();

            Array arraySQL = null;
            int columns = contarCampos(campos); //# de columnas a mostrar (predeterminado)
            String[][] resultadoConsulta = new String[rows][columns];


            int j = 0;
            while (resultSet.next()) {
                for (int i = 0; i < columns; i++) {
                    arraySQL = resultSet.getArray(i+1);
                    resultadoConsulta[j][i] = arraySQL.toString().trim();
                } j++;
            }

            return resultadoConsulta;
        }
        catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
            return null;
        }

    }

    //Funcion para finalizar orden de trabajo almacenandola como finalizada en la base de datos
    public boolean finalizarOrden(String idOrden, String idProducto,String idSede,int cantidad) {
        String sql = "UPDATE ordenes_de_trabajo SET finalizada=true WHERE id_ordenes = " + idOrden;

        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

            int nuevaCantidad= cantidadDisponible(idProducto,idSede)+cantidad;
            sql= "UPDATE ordenes_de_trabajo SET finalizada=true WHERE id_ordenes = " + idOrden;

            PreparedStatement psSql = connection.prepareStatement(sql);
            psSql.executeUpdate();

            sql= "UPDATE inventario SET cantidad_disponible="+ nuevaCantidad +
                    "WHERE id_sede = " + idSede + "AND id_producto = "+ idProducto+";";

            psSql = connection.prepareStatement(sql);
            psSql.executeUpdate();

            return true;
        }
        catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
            return false;
        }

    }


    //Funcion para consultar la cantidad disponible de un producto en una sede en especifico
    public int cantidadDisponible(String idProducto,String idSede) {
        String sql = "SELECT cantidad_disponible FROM public.inventario"+
                     " WHERE id_sede = " + idSede +" AND id_producto = " + idProducto + ";";

        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(sql);

            resultSet.last();
            resultSet.beforeFirst();

           resultSet.next();

            System.out.print(resultSet.getInt(1));

            return resultSet.getInt(1);
        }
        catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
            return 0;
        }

    }

    //Funcion para cancelar orden de trabajo y borrarla de la basse de datos
    public boolean cancelarOrden(String identifier) {
	    String sql = "DELETE FROM ordenes_de_trabajo WHERE id_ordenes="+identifier;

        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

            PreparedStatement psSql = connection.prepareStatement(sql);
            psSql.executeUpdate();

            return true;
        }
        catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
            return false;
        }
    }

    //Funcion para insertar una cotizacion
    public String insertarCot(String id_emp,String nombre_cot,String tipo){

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
			@SuppressWarnings("unused")
			Statement statement = connection.createStatement();
			String sql ="INSERT INTO public.venta_cotizaciones(id_empleado,fecha_cotizacion,nombre_cotizante," +
					"precio_final,tipo) VALUES ("+id_emp+",'NOW()','"+nombre_cot+"',0, '"+tipo+"') RETURNING id_cotizacion";
			PreparedStatement psSql = connection.prepareStatement(sql);
			ResultSet rs = psSql.executeQuery();

			System.out.print(sql);

			rs.next();
			String resultado = rs.getString(1);
			return resultado.trim();
		}
		catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
			return "";
		}
	}

	//Funcion para cancelar una cotizacion y borrarla de la base de datos
	public boolean cancelarCoti(String identifier){
		String sql = "DELETE FROM venta_cotizaciones WHERE id_cotizacion="+identifier;

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

			PreparedStatement psSql = connection.prepareStatement(sql);
			psSql.executeUpdate();

			return true;
		}
		catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
			return false;
		}
	}

	//Funcion para actualizar una cotizacion
	public boolean actualizarCoti(String identifier,String total){
		String sql = "UPDATE venta_cotizaciones SET precio_final = "+total+" WHERE id_cotizacion="+identifier;

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

			PreparedStatement psSql = connection.prepareStatement(sql);
			psSql.executeUpdate();

			return true;
		}
		catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
			return false;
		}
	}

	//Funcion para obtener la fecha de una cotizacion
	public String getFechacot(String id_cot){
        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

            String sql ="SELECT fecha_cotizacion FROM venta_cotizaciones WHERE id_cotizacion = "+id_cot;
            PreparedStatement psSql = connection.prepareStatement(sql);
            ResultSet rs = psSql.executeQuery();

            System.out.print(sql);

            rs.next();
            String resultado = rs.getString(1);
            return resultado.trim();
        }
        catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
            return "";
        }
    }


    //Funcion para agregar un nuevo prodcuto a la cotizacion con una cantidad a cotizar
	public boolean agregarProCot(String id_pro, String cantidad, String id_cot){
        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
            @SuppressWarnings("unused")
            Statement statement = connection.createStatement();
            String sql ="INSERT INTO public.ventas_cotizaciones_producto VALUES ("
                    +id_pro+","+cantidad+","+id_cot+");";
            System.out.print(sql);
            PreparedStatement psSql = connection.prepareStatement(sql);
            psSql.execute();

            return true;
        }
        catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
            return false;
        }
    }

    //Funcion para consultar campos en especifico de los productos
    public String consultarProducto(String identifier, String campo, String criterio) {

        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

            String sql ="SELECT "+campo+" FROM public.producto ";

            if(criterio=="Id") sql += "WHERE id_producto = "+identifier;
            if(criterio=="Nombre") sql += "WHERE nombre = "+identifier;

            PreparedStatement psSql = connection.prepareStatement(sql);
            ResultSet rs = psSql.executeQuery();

            System.out.print(sql);

            rs.next();
            String resultado = rs.getString(1);
            return resultado.trim();
        }
        catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
            return "";
        }
    }

    //Funcion para obtener ciertos campos de un usuario en especifico
    public String consultarDatoUsuario(String criterio, String busqueda,String campos){

        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

            String sql = "SELECT "+campos+" FROM public.empleados";

            if(criterio=="Id") sql += " WHERE cedula = " + busqueda;

            PreparedStatement psSql = connection.prepareStatement(sql);
            ResultSet rs = psSql.executeQuery();

            rs.next();
            String resultado = rs.getString(1);
            return resultado.trim();
        }
        catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
            return "";
        }
    }

    //Funcion para obtener los productos de una sede dado un criterio de busqueda
    public String[][] listarProductos(String criterio, String busqueda,String campos,String sede) {
        String sql = "SELECT "+campos+" FROM public.producto NATURAL JOIN public.inventario";



        if(criterio=="Id_producto") sql += " WHERE id_producto = " + busqueda;
        if(criterio=="Nombre") sql += " WHERE nombre = '" + busqueda + "'";

        sql += " AND id_sede = " + sede;

        System.out.print(sql);

        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(sql);

            resultSet.last();
            int rows = resultSet.getRow(); //# de filas resulado de la consulta
            resultSet.beforeFirst();

            Array arraySQL = null;
            int columns = contarCampos(campos); //# de columnas a mostrar (predeterminado)
            String[][] resultadoConsulta = new String[rows][columns];

            int j = 0;
            while (resultSet.next()) {
                for (int i = 0; i < columns; i++) {
                    arraySQL = resultSet.getArray(i+1);
                    resultadoConsulta[j][i] = arraySQL.toString().trim();
                } j++;
            }

            return resultadoConsulta;
        }
        catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
            return null;
        }
    }

    // Esto va aaa generar los datos

    public String[][] informeProductos(String intervalo, int sede) {

        String sql = "SELECT nombre,sum(cantidad_compra) FROM informeProducto WHERE fecha_cotizacion BETWEEN NOW()-INTERVAL'";

        if(intervalo=="Mensual") sql += "1";
        if(intervalo=="Anual") sql += "12";
        if(intervalo=="Semestral") sql += "6";

        sql += " month' AND NOW()";

        if(sede!=0) sql += " AND sede =" + sede +"";

        sql += " GROUP BY id_producto,nombre" ;

        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(sql);

            resultSet.last();
            int rows = resultSet.getRow(); //# de filas resulado de la consulta
            resultSet.beforeFirst();

            Array arraySQL = null;
            int columns = 2; //# de columnas a mostrar (predeterminado)
            String[][] resultadoConsulta = new String[rows][columns];

            int j = 0;
            while (resultSet.next()) {
                for (int i = 0; i < columns; i++) {
                    arraySQL = resultSet.getArray(i+1);
                    resultadoConsulta[j][i] = arraySQL.toString().trim();
                } j++;
            }

            return resultadoConsulta;
        }
        catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
            return null;
        }
    }

    public String[][] informeInventario(int sede) {

        String sql = "SELECT nombre,sum(cantidad_disponible),id_sede FROM informeInventario ";

        if(sede!=0) sql += " WHERE sede =" + sede +"";

        sql += " GROUP BY nombre,id_producto,id_sede" ;

        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(sql);

            resultSet.last();
            int rows = resultSet.getRow(); //# de filas resulado de la consulta
            resultSet.beforeFirst();

            Array arraySQL = null;
            int columns = 3; //# de columnas a mostrar (predeterminado)
            String[][] resultadoConsulta = new String[rows][columns];

            int j = 0;
            while (resultSet.next()) {
                for (int i = 0; i < columns; i++) {
                    arraySQL = resultSet.getArray(i+1);
                    resultadoConsulta[j][i] = arraySQL.toString().trim();
                } j++;
            }

            return resultadoConsulta;
        }
        catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
            return null;
        }
    }

    public String[][] informeVentas(String intervalo, int sede) {

        String sql = "SELECT nombre,sum(cantidad_compra),sede FROM informeVentas WHERE fecha_cotizacion BETWEEN NOW()-INTERVAL'";

        if(intervalo=="Mensual") sql += "1";
        if(intervalo=="Anual") sql += "12";
        if(intervalo=="Semestral") sql += "6";

        sql += " month' AND NOW()";

        if(sede!=0) sql += " AND sede =" + sede +"";

        sql += " GROUP BY id_producto,nombre,sede" ;

        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(sql);

            resultSet.last();
            int rows = resultSet.getRow(); //# de filas resulado de la consulta
            resultSet.beforeFirst();

            Array arraySQL = null;
            int columns = 3; //# de columnas a mostrar (predeterminado)
            String[][] resultadoConsulta = new String[rows][columns];

            int j = 0;
            while (resultSet.next()) {
                for (int i = 0; i < columns; i++) {
                    arraySQL = resultSet.getArray(i+1);
                    resultadoConsulta[j][i] = arraySQL.toString().trim();
                } j++;
            }

            return resultadoConsulta;
        }
        catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
            return null;
        }
    }

    public String[][] informeCotizaciones(String intervalo, int sede) {

        String sql = "SELECT nombre,sum(cantidad_compra),sede FROM informeCotizaciones WHERE fecha_cotizacion BETWEEN NOW()-INTERVAL'";

        if(intervalo=="Mensual") sql += "1";
        if(intervalo=="Anual") sql += "12";
        if(intervalo=="Semestral") sql += "6";

        sql += " month' AND NOW()";

        if(sede!=0) sql += " AND sede =" + sede +"";

        sql += " GROUP BY id_producto,nombre,sede" ;


        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(sql);

            resultSet.last();
            int rows = resultSet.getRow(); //# de filas resulado de la consulta
            resultSet.beforeFirst();

            Array arraySQL = null;
            int columns = 3; //# de columnas a mostrar (predeterminado)
            String[][] resultadoConsulta = new String[rows][columns];

            int j = 0;
            while (resultSet.next()) {
                for (int i = 0; i < columns; i++) {
                    arraySQL = resultSet.getArray(i+1);
                    resultadoConsulta[j][i] = arraySQL.toString().trim();
                } j++;
            }

            return resultadoConsulta;
        }
        catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
            return null;
        }
    }

    public String[][] informeOrden(int sede) {

        String sql = "SELECT id_producto,sum(cantidad),sede FROM informeOrden ";

        if (sede != 0) sql += " WHERE sede =" + sede + "";

        sql += " GROUP BY id_producto,sede";

        System.out.print(sql);


        try (Connection connection = DriverManager.getConnection(URL, USUARIO, PASSWORD)) {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(sql);

            resultSet.last();
            int rows = resultSet.getRow(); //# de filas resulado de la consulta
            resultSet.beforeFirst();

            Array arraySQL = null;
            int columns = 3; //# de columnas a mostrar (predeterminado)
            String[][] resultadoConsulta = new String[rows][columns];

            int j = 0;
            while (resultSet.next()) {
                for (int i = 0; i < columns; i++) {
                    arraySQL = resultSet.getArray(i + 1);
                    if(arraySQL==null)resultadoConsulta[j][i] = "0";
                    else resultadoConsulta[j][i] = arraySQL.toString().trim();
                }
                j++;
            }

            return resultadoConsulta;
        } catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
            return null;
        }
    }






}
