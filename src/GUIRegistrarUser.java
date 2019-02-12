import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressWarnings("serial")
public class GUIRegistrarUser extends JFrame {

	private Container contenedor;
	private JLabel id, nombre, apellido, direccion, celular;
	private JLabel eMail, contrasena, tipoEmpleado, sede;
	private JTextField nombreIn, apellidoIn, idIn;
	private JTextField direccionIn, celularIn, eMailIn;
	private JComboBox<String> tipoEmpleadoIn, sedeIn;
	private JPasswordField contrasenaIn;
	private JButton crear, cancelar;
	private JSeparator separator_1, separator_2;
	private Font font;
	private BaseDeDatos bd;

	public GUIRegistrarUser(BaseDeDatos bdIn){
		setResizable(false);
		setTitle("CREAR EMPLEADO");

		font = new Font("Tahoma", Font.PLAIN, 14);
        bd = bdIn;
		crearComponentes();
	}


    private void crearComponentes() {

		contenedor = getContentPane();
		contenedor.removeAll();
		getContentPane().setLayout(null);

		JPanel panelUsuario = new JPanel();
		panelUsuario.setBounds(0, 0, 394, 575);
		contenedor.add(panelUsuario);
		panelUsuario.setLayout(null);

		nombre = new JLabel("Nombre:");
		nombre.setFont(font);
		nombre.setBounds(21, 101, 105, 32);
		panelUsuario.add(nombre);

		nombreIn = new JTextField();
		nombreIn.setFont(font);
		nombreIn.setBounds(136, 101, 234, 32);
		panelUsuario.add(nombreIn);

		apellido = new JLabel("Apellidos:");
		apellido.setFont(font);
		apellido.setBounds(21, 140, 105, 32);
		panelUsuario.add(apellido);

		apellidoIn = new JTextField();
		apellidoIn.setFont(font);
		apellidoIn.setBounds(136, 141, 234, 32);
		panelUsuario.add(apellidoIn);

		id =  new JLabel("Id/Documento:");
		id.setFont(font);
		id.setBounds(21, 183, 105, 32);
		panelUsuario.add(id);

		idIn = new JTextField();
		idIn.setFont(font);
		idIn.setBounds(136, 184, 234, 32);
		panelUsuario.add(idIn);

		contrasena = new JLabel("Contrasena:");
		contrasena.setFont(font);
		contrasena.setBounds(21, 226, 105, 32);
		panelUsuario.add(contrasena);

		contrasenaIn = new JPasswordField();
		contrasenaIn.setFont(font);
		contrasenaIn.setBounds(136, 227, 234, 32);
		panelUsuario.add(contrasenaIn);

		direccion = new JLabel("Direccion:");
		direccion.setFont(font);
		direccion.setBounds(21, 269, 105, 32);
		panelUsuario.add(direccion);

		direccionIn = new JTextField();
		direccionIn.setFont(font);
		direccionIn.setBounds(136, 270, 234, 32);
		panelUsuario.add(direccionIn);

		celular =  new JLabel("Celular:");
		celular.setFont(font);
		celular.setBounds(21, 312, 105, 32);
		panelUsuario.add(celular);

		celularIn = new JTextField();
		celularIn.setFont(font);
		celularIn.setBounds(136, 313, 234, 32);
		panelUsuario.add(celularIn);

		eMail = new JLabel("E-Mail:");
		eMail.setFont(font);
		eMail.setBounds(21, 355, 105, 32);
		panelUsuario.add(eMail);

		eMailIn = new JTextField();
		eMailIn.setFont(font);
		eMailIn.setBounds(136, 356, 234, 32);
		panelUsuario.add(eMailIn);

		tipoEmpleado = new JLabel("Tipo de Empleado:");
		tipoEmpleado.setFont(font);
		tipoEmpleado.setBounds(21, 405, 138, 32);
		panelUsuario.add(tipoEmpleado);

		String[] listaTipo = new String[] { "Jefe de taller", "Vendedor"};
		tipoEmpleadoIn = new JComboBox<>(listaTipo);
		tipoEmpleadoIn.setEditable(false);
		tipoEmpleadoIn.setFont(font);
		tipoEmpleadoIn.setBounds(169, 406, 201, 32);
		panelUsuario.add(tipoEmpleadoIn);

		sede = new JLabel("Sede:");
		sede.setFont(font);
		sede.setBounds(21, 448, 138, 32);
		panelUsuario.add(sede);

		sedeIn = new JComboBox<>(bd.cambiarDimension(bd.consultarSede(null,"id_Sede,nombre")));
		sedeIn.setEditable(false);
		sedeIn.setFont(font);
		sedeIn.setBounds(169, 449, 201, 32);
		panelUsuario.add(sedeIn);

		ActionListener listener = new ManejadorDeBotones();

		cancelar = new JButton("Cancelar");
		cancelar.setFont(font);
		cancelar.setBounds(120, 526, 120, 28);
		cancelar.addActionListener(listener);
		panelUsuario.add(cancelar);

		crear = new JButton("Crear Usuario");
		crear.setFont(font);
		crear.setBounds(250, 526, 120, 28);
		crear.addActionListener(listener);
		panelUsuario.add(crear);

		separator_2 = new JSeparator();
		separator_2.setBounds(21, 513, 349, 2);
		panelUsuario.add(separator_2);

		separator_1 = new JSeparator();
		separator_1.setBounds(21, 88, 349, 2);
		panelUsuario.add(separator_1);

		JLabel lblNewLabel = new JLabel("");
		URL filePath = this.getClass().getResource("/images/create.png");
		lblNewLabel.setIcon(new ImageIcon(filePath));
		lblNewLabel.setBounds(21, 11, 66, 66);
		panelUsuario.add(lblNewLabel);
		setSize(400,602);
		setVisible(true);
		setLocationRelativeTo(null);
	}


	private boolean validar(){

        if(nombreIn.getText().compareTo("")==0){
            return false;
        }
        if(apellidoIn.getText().compareTo("")==0){
            return false;
        }
        if(idIn.getText().compareTo("")==0){
            return false;
        }
        if(direccionIn.getText().compareTo("")==0){
            return false;
        }
        if(celularIn.getText().compareTo("")==0){
            return false;
        }
        if(eMailIn.getText().compareTo("")==0){
            return false;
        }
        String p = new String(contrasenaIn.getPassword());
        if(p.compareTo("")==0){
            return false;
        }
        return true;
    }

    private String validar2(){
        String mensaje = "";

        Pattern patron = Pattern.compile("[^A-Za-z ]");
        Matcher nombre = patron.matcher(nombreIn.getText());
        Matcher apellido = patron.matcher(apellidoIn.getText());


        if(nombre.find() || nombreIn.getText().length()>30){
            mensaje = mensaje +" Digite un nombre válido \n";
        }
        if(apellido.find() || apellidoIn.getText().length()>30){
            mensaje = mensaje +" Digite un apellido válido \n";
        }

        Pattern patron1 = Pattern.compile("[^0-9]");
        Matcher usuario = patron1.matcher(idIn.getText());

        if(usuario.find() || idIn.getText().length()>30){
            mensaje = mensaje +" Digite un usuario valido \n";
        }
        patron1 = Pattern.compile("[^A-Za-z0-9_]");
        Matcher pass = patron1.matcher(new String(contrasenaIn.getPassword()));

        if(pass.find()){
            mensaje = mensaje +" Digite una contrasena valida \n";
        }

        Pattern patron2 = Pattern.compile("[^A-Za-z0-9 #-]");
        Matcher direccion = patron2.matcher(direccionIn.getText());

        if(direccion.find()|| direccionIn.getText().length()>40) {
            mensaje = mensaje + " Digite una direccion valida \n";
        }

         patron2 = Pattern.compile("[^0-9]");
        Matcher cel = patron2.matcher(celularIn.getText());

        if(cel.find()|| celularIn.getText().length()>40|| celularIn.getText().length()<7) {
            mensaje = mensaje + " Digite un telefono celular valido \n";
        }

        if(!eMailIn.getText().matches("([^@])+@([^@])+[\\p{Punct}&&[^@]](.[a-z]{1,4})*")){
            mensaje = mensaje + " Digite un E-mail valido \n";
        }

        if(mensaje.compareTo("")==0){
            mensaje="true";
        }

        return mensaje;
    }

    private class ManejadorDeBotones implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            if (actionEvent.getSource() == crear) {
                if (validar()) {
                    if (validar2().compareTo("true") == 0) {
                        String idSede = sedeIn.getSelectedItem().toString();
                        idSede=idSede.substring(0,idSede.indexOf("-"));
                        System.out.print(idSede);

                        boolean var = bd.insertarUsuario(idIn.getText(), new String(contrasenaIn.getPassword()),
                                nombreIn.getText(), apellidoIn.getText(), direccionIn.getText(),
                                eMailIn.getText(), (String) tipoEmpleadoIn.getSelectedItem(),
                                idSede, celularIn.getText());
                        if (var) {
                            JOptionPane.showMessageDialog(null, "Usuario creado exitosamente");
                            dispose();
                        } else JOptionPane.showMessageDialog(null, "Error al crear usuario.");
                    } else JOptionPane.showMessageDialog(null, validar2());

                } else JOptionPane.showMessageDialog(null, "Debe llenar todas los campos");
            }
            if (actionEvent.getSource() == cancelar)
                dispose();
        }
    }
}
