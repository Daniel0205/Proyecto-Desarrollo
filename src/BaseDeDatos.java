
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

	//Método para obtener un elemento de tipo boolean perteneciente al valor de algún atributo.
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



	public boolean actualizarUsuario(String identifier, String nombres, String apellidos,
 			String direccion, String celular, String email, String tipo, String sede, boolean activo) {

		try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {
			String sql ="UPDATE empleados SET nombres = '"+nombres+
					"', apellidos = '"+apellidos+"', direccion = '"+direccion+"', numero = "+celular+
					", email = '"+email+"', tipo_usuario = '"+tipo+"', sede = '"+sede+"', activo = "+activo+
					" WHERE cedula = "+identifier+";";
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

    //Funcion para contar la cantidad de campos a consultar
    private int contarCampos(String campos){
        int contador=0;
        for (int i=0;i< campos.length();i++){
            if (campos.charAt(i)==',')contador++;
        }
        return contador+1;
    }

    //Funcion para transformar matrics bidimensionales a unidimensionales
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

            String sql ="SELECT active FROM empleados WHERE cedula = '"+ user
                        + "' AND contrasena = "
                        + " crypt('"+pass+"',contrasena) AND user_type =  '" +tipoUsuario+"'" ;

            System.out.print(sql+"\n");

            PreparedStatement psSql = connection.prepareStatement(sql);
            ResultSet result = psSql.executeQuery();

            result.next();

            if (result.getBoolean("activo"))return true;
            else return false;
        }
        catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
            return false;
        }

    }
	
}
