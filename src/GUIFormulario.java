

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        setSize(300,400);
        setVisible(true);
        setLocationRelativeTo(null);
    }


    private boolean validar(){
        boolean val=true;
        if(nombreIn.getText().compareTo("")==0){
            val=false;
        }
        if(apellidoIn.getText().compareTo("")==0){
            val=false;
        }
        if(usuarioIn.getText().compareTo("")==0){
            val=false;
        }
        if(direccionIn.getText().compareTo("")==0){
            val=false;
        }
        if(celularIn.getText().compareTo("")==0){
            val=false;
        }
        if(eMailIn.getText().compareTo("")==0){
            val=false;
        }
        String p = new String(contrasenaIn.getPassword());
        if(p.compareTo("")==0){
            val=false;
        }
        return val;
    }

    private String validar2(){
        String mensaje = "";
        try{
            Integer.parseInt(celularIn.getText());

        } catch (NumberFormatException excepcion){
            mensaje = mensaje +" Digite un número valido en el campo del celular";
        }

        Pattern patron = Pattern.compile("[^A-Za-z ]");
        Matcher nombre = patron.matcher(nombreIn.getText());
        Matcher apellido = patron.matcher(apellidoIn.getText());


        if(nombre.find()){
            mensaje = mensaje +" Digite un nombre válido";
        }
        if(apellido.find()){
            mensaje = mensaje +" Digite un apellido válido";
        }

        if(mensaje.compareTo("")==0){
            mensaje="true";
        }



        return mensaje;



    }


    private class ManejadorDeBotones implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource() == crear){
                if (validar()){
                    if(validar2().compareTo("true")==0){
                        boolean var = bd.insertarUsuario(usuarioIn.getText(), new String(contrasenaIn.getPassword()),
                                nombreIn.getText(), apellidoIn.getText(), direccionIn.getText(),
                                eMailIn.getText(), (String) tipoEmpleadoIn.getSelectedItem(),
                                (String) sedeIn.getSelectedItem(), Integer.parseInt(celularIn.getText()));
                        if (var) {
                            JOptionPane.showMessageDialog(null, "Usuario creado exitosamente");
                            contenedor.removeAll();
                            crearComponentes();
                        } else JOptionPane.showMessageDialog(null, "Error al crear usuario.");
                    } else JOptionPane.showMessageDialog(null, validar2());

                } else JOptionPane.showMessageDialog(null, "Debe llenar todas los campos");

            }
            if (actionEvent.getSource()==cancelar) dispose();
        }
    }
}
