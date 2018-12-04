import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GUIActualizar extends JFrame {

    private BaseDeDatos bd;
    private Container contenedor;
    private JLabel instruccion, usuario, nombre, apellidos, direccion, celular, eMail, tipoEmpleado, sede, estado;
    private JPanel panel, panel2;
    private JTextField idIn, usuarioIn, nombreIn, apellidosIn, direccionIn, celularIn, eMailIn;
    private JComboBox  tipoEmpleadoIn, sedeIn, estadoIn;
    private JButton cancelar, aceptar, actualizar;
    private ActionListener listener = new ManejadorDeBotones();

    public GUIActualizar(BaseDeDatos bdIn){

        super("Actualizar empleado");

        initGUI();
        bd=bdIn;
    }

    private void initGUI() {

        contenedor = getContentPane();
        contenedor.removeAll();

        panel = new JPanel(new GridLayout(2,2));
        contenedor.add(panel);

        instruccion = new JLabel("ID usuario a modificar");
        panel.add(instruccion);

        idIn = new JTextField();
        panel.add(idIn);

        cancelar = new JButton("Cancelar");
        cancelar.addActionListener(listener);
        panel.add(cancelar);

        aceptar = new JButton("Aceptar");
        aceptar.addActionListener(listener);
        panel.add(aceptar);

        setResizable(false);
        setSize(300,100);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void initGUI2(int identifier){

        contenedor.removeAll();
        contenedor.repaint();

        panel2 = new JPanel(new GridLayout(10,2));
        contenedor.add(panel2);

        usuario = new JLabel("Usuario:");
        panel2.add(usuario);

        usuarioIn = new JTextField(bd.obtenerS(identifier,"user_alias"));
        panel2.add(usuarioIn);

        nombre = new JLabel("Nombres:");
        panel2.add(nombre);

        nombreIn = new JTextField(bd.obtenerS(identifier,"names"));
        panel2.add(nombreIn);

        apellidos = new JLabel("Apellidos:");
        panel2.add(apellidos);

        apellidosIn = new JTextField(bd.obtenerS(identifier,"surnames"));
        panel2.add(apellidosIn);

        direccion = new JLabel("Direccion:");
        panel2.add(direccion);

        direccionIn = new JTextField(bd.obtenerS(identifier,"address"));
        panel2.add(direccionIn);

        celular =  new JLabel("Celular:");
        panel2.add(celular);

        celularIn = new JTextField(bd.obtenerS(identifier,"phone_number"));
        panel2.add(celularIn);

        eMail = new JLabel("E-Mail.");
        panel2.add(eMail);

        eMailIn = new JTextField(bd.obtenerS(identifier,"email"));
        panel2.add(eMailIn);

        tipoEmpleado = new JLabel("Tipo de Empleado:");
        panel2.add(tipoEmpleado);

        tipoEmpleadoIn = new JComboBox(new String[] { "jefe de taller" , "vendedor" });
        tipoEmpleadoIn.setSelectedItem(bd.obtenerS(identifier,"user_type"));
        panel2.add(tipoEmpleadoIn);

        sede = new JLabel("Sede:");
        panel2.add(sede);

        sedeIn = new JComboBox(new String[] { "Central", "Cartagena", "Cali", "Medellin"});
        sedeIn.setSelectedItem(bd.obtenerS(identifier,"headquarter"));
        panel2.add(sedeIn);

        estado = new JLabel("Empleado activo:");
        panel2.add(estado);

        estadoIn = new JComboBox(new String[] {"true", "false"});
        if(bd.obtenerB(identifier,"active")){
            estadoIn.setSelectedItem("true");
        }else estadoIn.setSelectedItem("false");
        panel2.add(estadoIn);

        panel2.add(cancelar);

        actualizar = new JButton("Actualizar");
        actualizar.addActionListener(listener);
        panel2.add(actualizar);

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
        if(apellidosIn.getText().compareTo("")==0){
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
        Matcher apellido = patron.matcher(apellidosIn.getText());


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

    private boolean validarId() {
        boolean val = true;
        try {
            Integer.parseInt(idIn.getText());

        } catch (NumberFormatException excepcion) {
            val=false;
        }
        return val;
    }

    private class ManejadorDeBotones implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {


            if(e.getSource()==cancelar){
                dispose();
            }else if(e.getSource()==aceptar){
                if(!(idIn.getText().compareTo("")==0)){
                    if(validarId()){
                        int identifier = Integer.parseInt(idIn.getText());

                        if(bd.verificarId(identifier)){
                            initGUI2(identifier);
                        }else{
                            JOptionPane.showMessageDialog(null, "El empleado con id: "+ idIn.getText() +" no se encuentra registrado");
                            dispose();
                        }

                    }else JOptionPane.showMessageDialog(null, "Digite un ID válido");
                } else JOptionPane.showMessageDialog(null, "Digite el ID del empleado");
            }else if(e.getSource()==actualizar){
                if(validar()){
                    if(validar2().compareTo("true")==0){
                        int identifier = Integer.parseInt(idIn.getText());
                        boolean var = bd.actualizarUsuario(identifier,usuarioIn.getText(),nombreIn.getText(),apellidosIn.getText(),
                                direccionIn.getText(),Integer.parseInt(celularIn.getText()),eMailIn.getText(),(String) tipoEmpleadoIn.getSelectedItem(),
                                (String) sedeIn.getSelectedItem(),Boolean.parseBoolean((String)estadoIn.getSelectedItem()));

                        if (var){
                            JOptionPane.showMessageDialog(null, "Usuario actualizado exitosamente");
                            dispose();
                        }
                        else JOptionPane.showMessageDialog(null, "Error al actualizar usuario.");
                        dispose();

                    } else JOptionPane.showMessageDialog(null, validar2());


                } else JOptionPane.showMessageDialog(null, "Digite todos los campos requeridos.");

            }
        }
    }
}
