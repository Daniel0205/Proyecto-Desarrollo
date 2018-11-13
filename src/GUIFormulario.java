

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIFormulario extends JFrame {


    private Container contenedor;
    private JLabel usuario, nombre, apellido, direccion, celular;
    private JLabel eMail, contrasena, tipoEmpleado, sede;
    private JTextField nombreIn, apellidoIn, usuarioIn;
    private JTextField direccionIn, celularIn, eMailIn;
    private JComboBox tipoEmpleadoIn, sedeIn;
    private JPasswordField contrasenaIn;
    private JButton crear, cancelar;
    private BaseDeDatos bd;

    public GUIFormulario(BaseDeDatos bdIn){
        super("Crear Empleado");

        crearComponentes();
        bd=bdIn;
    }

    private void crearComponentes() {

        contenedor = getContentPane();
        contenedor.removeAll();

        JPanel panelUsuario = new JPanel(new GridLayout(10,2));
        contenedor.add(panelUsuario);

        nombre = new JLabel("Nombre:");
        panelUsuario.add(nombre);

        nombreIn = new JTextField();
        panelUsuario.add(nombreIn);

        apellido = new JLabel("Apellidos:");
        panelUsuario.add(apellido);

        apellidoIn = new JTextField();
        panelUsuario.add(apellidoIn);

        usuario =  new JLabel("Usuario:");
        panelUsuario.add(usuario);

        usuarioIn = new JTextField();
        panelUsuario.add(usuarioIn);

        contrasena = new JLabel("Contraseña:");
        panelUsuario.add(contrasena);

        contrasenaIn = new JPasswordField();
        panelUsuario.add(contrasenaIn);

        direccion = new JLabel("Direccion:");
        panelUsuario.add(direccion);

        direccionIn = new JTextField();
        panelUsuario.add(direccionIn);

        celular =  new JLabel("Celular:");
        panelUsuario.add(celular);

        celularIn = new JTextField();
        panelUsuario.add(celularIn);

        eMail = new JLabel("E-Mail.");
        panelUsuario.add(eMail);

        eMailIn = new JTextField();
        panelUsuario.add(eMailIn);

        tipoEmpleado = new JLabel("Tipo de Empleado:");
        panelUsuario.add(tipoEmpleado);

        tipoEmpleadoIn = new JComboBox(new String[] { "jefe de taller" , "vendedor" });
        panelUsuario.add(tipoEmpleadoIn);

        sede = new JLabel("Sede:");
        panelUsuario.add(sede);

        sedeIn = new JComboBox(new String[] { "Central", "Cartagena", "Cali", "Medellin"});
        panelUsuario.add(sedeIn);

        ActionListener listener = new ManejadorDeBotones();

        cancelar = new JButton("Cancelar");
        cancelar.addActionListener(listener);
        panelUsuario.add(cancelar);

        crear = new JButton("Crea Usuario");
        crear.addActionListener(listener);
        panelUsuario.add(crear);

        setResizable(false);
        setSize(400,500);
        setVisible(true);
        setLocationRelativeTo(null);
    }


    private class ManejadorDeBotones implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource() == crear){
               boolean var = bd.insertarUsuario(usuarioIn.getText(),new String(contrasenaIn.getPassword()),
                       nombreIn.getText(),apellidoIn.getText(),direccionIn.getText(),
                       eMailIn.getText(),(String)tipoEmpleadoIn.getSelectedItem(),
                       (String)sedeIn.getSelectedItem(), Integer.parseInt(celularIn.getText()));

               if (var) JOptionPane.showMessageDialog(null, "Usuario creado exitosamente");
               else JOptionPane.showMessageDialog(null, "Error al crear usuario.");

            }
            if (actionEvent.getSource()==cancelar) System.exit(0);
        }
    }
}
