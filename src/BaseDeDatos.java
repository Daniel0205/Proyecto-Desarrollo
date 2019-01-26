import java.sql.*;

public class BaseDeDatos {

	private final static String DB = "xyz";
	private final static String USUARIO = "postgres";
	private final static String PASSWORD = "1234";
	private final static String URL = "jdbc:postgresql://localhost:5432/" + DB;

    //Método usado para insertar un usuario en la base de datos, retorna true si fue insertado
    //correctamente y false si ocurrio un error
	public boolean insertarUsuario(String usuario,String contrasena, String nombre,
			String apellido,String direccion,String eMail,
			String tipoUsuario, String sede, String celular){

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

			System.out.println("Establishing connection with the database...");
			@SuppressWarnings("unused")
			Statement statement = connection.createStatement();
			System.out.println("Connected to PostgreSQL database!");

			String sql ="INSERT INTO public.empleados(user_alias, password, names,"+
					"surnames, address, phone_number, email, user_type,"+
					"headquarter, active) VALUES ('" +
					usuario + "', crypt('" + contrasena + "', gen_salt('md5')),'" +
					nombre + "','" + apellido + "','" + direccion + "'," +
					celular + ",'" + eMail + "','"+ tipoUsuario + "','" +
					sede + "'," +"true);";
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


	//Verifica que el id de un empleado se encuentre en la Base de Datos
	public boolean verificarId(int id) {

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

			String sql ="SELECT * from empleados";
			PreparedStatement psSql = connection.prepareStatement(sql);
			ResultSet rs = psSql.executeQuery();
			int verify = 0;

			while (rs.next()){
				if(Integer.parseInt(rs.getString("id"))==id){
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
				if(Integer.parseInt(rs.getString("id"))==id){
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
	public String obtenerS(int identifier, String campo) {

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

			String sql ="SELECT "+campo+" FROM empleados WHERE id = "+identifier;
			PreparedStatement psSql = connection.prepareStatement(sql);
			ResultSet rs = psSql.executeQuery();

			rs.next();
			String resultado = rs.getString(1);
			return resultado;
		}
		catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
			return "";
		}
	}

	//Método para obtener un elemento de tipo String perteneciente al valor de algún atributo de una sede.
	public String obtenerSede(int identifier, String campo) {

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

			String sql ="SELECT "+campo+" FROM sedes WHERE id = "+identifier;
			PreparedStatement psSql = connection.prepareStatement(sql);
			ResultSet rs = psSql.executeQuery();

			rs.next();
			String resultado = rs.getString(1);
			return resultado;
		}
		catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
			return "";
		}
	}

	//Método para obtener un elemento de tipo boolean perteneciente al valor de algún atributo.
	public boolean obtenerB(int identifier, String campo) {

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

			String sql ="SELECT "+campo+" FROM empleados WHERE id = "+identifier;
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

	public boolean actualizarUsuario(int identifier, String usuario, String nombres, String apellidos,
 			String direccion, String celular, String email, String tipo, String sede, boolean activo) {

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
			String sql ="UPDATE empleados SET user_alias = '"+usuario+"', names = '"+nombres+
					"', surnames = '"+apellidos+"', address = '"+direccion+"', phone_number = "+celular+
					", email = '"+email+"', user_type = '"+tipo+"', headquarter = '"+sede+"', active = "+activo+
					" WHERE id = "+identifier+";";
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


	public boolean actualizarSede(int identifier, String direccion, String telefono, String empleadoACargo) {
		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
			String sql ="UPDATE sedes SET address = '"+direccion+"', phone_number = "+telefono+
					", employee_in_charge = "+empleadoACargo+" WHERE id = "+identifier+";";
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
	public String[][] consultarUsuarios(String criterio, String busqueda) {
        String sql = "SELECT user_alias, id, names, surnames, address, phone_number, "
                    + "email, user_type,  headquarter, active FROM public.Empleados";

		if(criterio=="Id") sql += " WHERE id = " + busqueda;
		if(criterio=="Nombres") sql += " WHERE names = '" + busqueda + "'";
		if(criterio=="Apellidos") sql += " WHERE surnames = '" + busqueda + "'";

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = statement.executeQuery(sql);

			resultSet.last();
			int rows = resultSet.getRow(); //# de filas resulado de la consulta 
			resultSet.beforeFirst();

			Array arraySQL = null;
			int columns = 10; //# de columnas a mostrar (predeterminado)
			String[][] resultadoConsulta = new String[rows][columns];

			int j = 0;
			while (resultSet.next()) {
				for (int i = 0; i < columns; i++) {
					arraySQL = resultSet.getArray(i+1);
					resultadoConsulta[j][i] = arraySQL.toString();
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

	public String[][] consultarSede(String busqueda) {
		String sql = "SELECT id, adress, phone_number, "
				+ "employee_in_charge FROM public.sedes";

		sql += " WHERE id = " + busqueda;

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = statement.executeQuery(sql);

			resultSet.last();
			int rows = resultSet.getRow(); //# de filas resulado de la consulta
			resultSet.beforeFirst();

			Array arraySQL = null;
			int columns = 4; //# de columnas a mostrar (predeterminado)
			String[][] resultadoConsulta = new String[rows][columns];

			int j = 0;
			while (resultSet.next()) {
				for (int i = 0; i < columns; i++) {
					arraySQL = resultSet.getArray(i+1);
					resultadoConsulta[j][i] = arraySQL.toString();
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
	
	
	//Funcion para acceder a la base de datos y registrar una sede
	//Si la operacion se realiza con exito retorna true y en caso contrario false
	public boolean registraSede(String id, String direccion,String telefono,
			String idEncargado){

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
			@SuppressWarnings("unused")
			Statement statement = connection.createStatement();
			String sql ="INSERT INTO public.sedes(id, address, phone_number, "
					+ "employee_in_charge) VALUES ("+ id +",'"+ direccion +"',"+
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

	//
	public boolean validarLogin( String user, String pass, String tipoUsuario){
        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

            String sql ="SELECT active FROM empleados WHERE user_alias = '"+ user
                        + "' AND password = "
                        + " crypt('"+pass+"',password) AND user_type =  '" +tipoUsuario+"'" ;

            System.out.print(sql+"\n");

            PreparedStatement psSql = connection.prepareStatement(sql);
            ResultSet result = psSql.executeQuery();

            result.next();

            if (result.getBoolean("active"))return true;
            else return false;
        }
        catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
            return false;
        }

    }
	
}
