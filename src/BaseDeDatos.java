
import java.sql.*;

public class BaseDeDatos {

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


	//
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

	public boolean actualizarOrden(String identifier,String fechaEntrega, String cantidad,String idProducto,
                                   String idEncargado, String asignadoA, String finalizada){

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

	public String[][] consultarOrden(String busqueda,String campos) {
		String sql = "SELECT "+ campos
				+ " FROM public.ordenes_de_trabajo";
		int columns = contarCampos(campos);

		if(busqueda!=null)sql += " WHERE id_ordnes = " + busqueda;

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

    //Funcion para contar la cantidad de campos a consultar
    private int contarCampos(String campos){
        int contador=0;
        for (int i=0;i< campos.length();i++){
            if (campos.charAt(i)==',')contador++;
        }
        return contador+1;
    }

    //Funcion para transformar matrices bidimensionales a unidimensionales
    public String[] cambiarDimension(String[][] array){
        String[] aux = new String[array.length];
        for (int i=0;i < array.length;i++){

            aux[i]=array[i][0].trim()+"-"+array[i][1].trim();

        }
        return aux;
    }

	//Funcion para acceder a la base de datos y registrar una sede
	//Si la operacion se realiza con exito retorna true y en caso contrario false
	public boolean registraSede(String id, String direccion,String telefono,
			String idEncargado,String nombre){

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
			@SuppressWarnings("unused")
			Statement statement = connection.createStatement();
			String sql ="INSERT INTO public.sedes(id_sede,nombre, direccion, telefono, "
					+ "empleado_a_cargo) VALUES ("+ id +",'"+ nombre+ "','"+ direccion +"',"+
					telefono +","+ idEncargado +");";
			System.out.print(sql);
			PreparedStatement psSql = connection.prepareStatement(sql);
			psSql.execute();

			crearInventarioSede(id);

			return true;
		}
		catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
			return false;
		}
	}

    private void crearInventarioSede(String id) {

	    String[][] productos = consultarProductos("id_producto");

	    try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

	        for (int i = 0; i <(int)productos.length; i++) {
                String sql ="INSERT INTO public.sedes_producto(id_sedes,id_producto,cantidad_disponible) VALUES (" +
                        id+","+productos[i][0]+",0);";

                PreparedStatement psSql = connection.prepareStatement(sql);
                psSql.execute();
            }
        }catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
        }
    }


    //
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

	//
    public boolean registrarProducto(String id, String nombre, String precio,String descripcion){
        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
            @SuppressWarnings("unused")
            Statement statement = connection.createStatement();
            String sql ="INSERT INTO public.producto(id_producto,nombre, precio, descripcion) "+
                    " VALUES ("+ id +",'"+ nombre+ "',"+ precio +",'"+descripcion+"');";
            System.out.print(sql);
            PreparedStatement psSql = connection.prepareStatement(sql);
            psSql.execute();

            crearInventarioProducto(id);

            return true;
        }
        catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
            return false;
        }

    }

    private void crearInventarioProducto(String id) {

        String[][] sedes = consultarSede(null,"id_sede");

        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

            for (int i = 0; i <(int)sedes.length; i++) {
                String sql ="INSERT INTO public.sedes_producto(id_producto,id_sedes,cantidad_disponible) VALUES (" +
                        id+","+sedes[i][0]+",0);";

                PreparedStatement psSql = connection.prepareStatement(sql);
                psSql.execute();
            }
        }catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
        }
    }

    //
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

    public boolean finalizarOrden(String idOrden, String idProducto,String idSede,int cantidad) {
        String sql = "UPDATE ordenes_de_trabajo SET finalizada=true WHERE id_ordenes = " + idOrden;

        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

            int nuevaCantidad= cantidadDisponible(idProducto,idSede)+cantidad;
            sql= "UPDATE ordenes_de_trabajo SET finalizada=true WHERE id_ordenes = " + idOrden;

            PreparedStatement psSql = connection.prepareStatement(sql);
            psSql.executeUpdate();

            sql= "UPDATE sedes_producto SET cantidad_disponible="+ nuevaCantidad +
                    "WHERE id_sedes = " + idSede + "AND id_producto = "+ idProducto+";";

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

    private int cantidadDisponible(String idProducto,String idSede) {
        String sql = "SELECT cantidad_disponible FROM public.sedes_producto"+
                     " WHERE id_sedes = " + idSede +" AND id_producto = " + idProducto + ";";

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
}
