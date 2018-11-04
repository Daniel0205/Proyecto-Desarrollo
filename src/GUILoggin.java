import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUILoggin extends JFrame {

    private Container contenedor;
    private JLabel aviso;
    private JButton registrar;
    private JButton ingresar;
    private JPasswordField contrasena;
    private JLabel inContrasena;
    private JTextField usuario;
    private JLabel inUsuario;

    public GUILoggin(){
        super("Loggin");

        crearComponentes();

        setResizable(false);
        setSize(300,250);
        setVisible(true);
        setLocationRelativeTo(null);


    }

    private void crearComponentes() {

        contenedor = getContentPane();
        contenedor.removeAll();

        setSize(300, 300);


        JPanel panelUsuario = new JPanel(new GridLayout(6,1));
        contenedor.add(panelUsuario);


        aviso = new JLabel("");
        panelUsuario.add(aviso);

        inUsuario = new JLabel("Ingrese Usuario:");
        panelUsuario.add(inUsuario);

        usuario = new JTextField();
        panelUsuario.add(usuario);

        inContrasena = new JLabel("Ingrese Password:");
        panelUsuario.add(inContrasena);

        contrasena = new JPasswordField();
        panelUsuario.add(contrasena);

        ManejadorDeBotones escucha = new ManejadorDeBotones();


        ingresar = new JButton("Ingresar");
        panelUsuario.add(ingresar);
        ingresar.addActionListener(escucha);


        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);


    }

    private class ManejadorDeBotones implements ActionListener{


        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource()==ingresar){
                String user = usuario.getText();
                char[] aux = contrasena.getPassword();
                String password = new String(aux);

                System.out.print(password+" "+ user);


            }
        }
    }
}
