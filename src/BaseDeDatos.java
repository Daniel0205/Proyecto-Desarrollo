import java.sql.*;

import static org.postgresql.jdbc.SslMode.VALUES;

public class BaseDeDatos {

    private final static String DB = "xyz";

    private final static String USUARIO = "postgres";

    private final static String PASSWORD = "1234";

    private final static String URL = "jdbc:postgresql://localhost:5432/" + DB;

    public boolean insertarUsuario(String usuario,String contrasena, String nombre,
                                String apellido,String direccion,String eMail,
                                String tipoUsuario, String sede, int celular){

        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

            System.out.println("Establishing connection with the database...");
            Statement statement = connection.createStatement();
            System.out.println("Connected to PostgreSQL database!");

            String sql ="INSERT INTO public.empleados(user_alias, password, names,"+
                    "surnames, address, phone_number, email, user_type,"+
                    "headquarter, active) " +
                    "VALUES ('" +
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

}
