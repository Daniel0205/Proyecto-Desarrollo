import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressWarnings("serial")
public class GUIActualizarUser extends JFrame {

	private BaseDeDatos bd;
	private Container contenedor;
	private JLabel instruccion, usuario, nombre, apellidos, direccion;
	private JLabel contrasena, eMail, tipoEmpleado, sede, estado, icon,celular;
	private JPasswordField contrasenaIn;
	@SuppressWarnings("unused")
	private JPanel panel, panel2;
	private JTextField usuarioIn, nombreIn, apellidosIn, direccionIn, celularIn, eMailIn;
	private JComboBox<String>  tipoEmpleadoIn, sedeIn, estadoIn,idIn;
	private JButton salir1, cancelar2, buscar, actualizar;
	private JSeparator separator_1, separator_2;
	private Font font;
	private ActionListener listener = new ManejadorDeBotones();

	public GUIActualizarUser(BaseDeDatos bdIn){
		super("ACTUALIZAR EMPLEADO");

		font = new Font("Tahoma", Font.PLAIN, 14);
		bd = bdIn;
		initGUI();
	}

	private void initGUI() {

		contenedor = getContentPane();
		contenedor.removeAll();
		getContentPane().setLayout(null);

		instruccion = new JLabel("ID usuario a modificar");
		instruccion.setFont(font);
		instruccion.setBounds(22, 101, 138, 32);
		getContentPane().add(instruccion);

		idIn = new JComboBox<>(bd.cambiarDimension(
                bd.consultarUsuarios(null,null,"cedula,nombres")));
		idIn.setFont(font);
		idIn.setBounds(165, 102, 99, 32);
		getContentPane().add(idIn);

		salir1 = new JButton("Salir");
		salir1.setFont(font);
		salir1.setBounds(271, 184, 100, 32);
		salir1.addActionListener(listener);
		getContentPane().add(salir1);

		buscar = new JButton("Buscar");
		buscar.setBounds(271, 101, 100, 32);
		buscar.setFont(font);
		buscar.addActionListener(listener);
		getContentPane().add(buscar);
		
		icon = new JLabel("");
		icon.setBounds(21, 11, 66, 66);
		URL filePath = this.getClass().getResource("/images/update.png");
		icon.setIcon(new ImageIcon(filePath));
		getContentPane().add(icon);
		
		separator_1 = new JSeparator();
		separator_1.setBounds(21, 88, 349, 2);
		getContentPane().add(separator_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(22, 172, 349, 2);
		getContentPane().add(separator);

		setResizable(false);
		setSize(400,256); 
		setVisible(true);
		setLocationRelativeTo(null);
	}

	private void initGUI2(String identifier){

		contenedor.removeAll();
		contenedor.repaint();
		getContentPane().setLayout(null);

		JPanel panel2 = new JPanel();
		panel2.setBounds(0, 0, 384, 650);
		contenedor.add(panel2);
		panel2.setLayout(null);

		usuario = new JLabel("id/Documento:");
		usuario.setBounds(21, 101, 105, 32);
		usuario.setFont(font);
		panel2.add(usuario);

		usuarioIn = new JTextField(bd.obtenerS(identifier,"cedula"));
		usuarioIn.setBounds(136, 101, 234, 32);
		usuarioIn.setEditable(false);
		usuarioIn.setFont(font);
		panel2.add(usuarioIn);

		contrasena = new JLabel("Contrasena:");
		contrasena.setBounds(21, 140, 105, 32);
		contrasena.setFont(font);
		panel2.add(contrasena);

		contrasenaIn = new JPasswordField();
		contrasenaIn.setBounds(136, 141, 234, 32);
		contrasenaIn.setFont(font);
		panel2.add(contrasenaIn);

		nombre = new JLabel("Nombres:");
		nombre.setBounds(21, 183, 105, 32);
		nombre.setFont(font);
		panel2.add(nombre);

		nombreIn = new JTextField(bd.obtenerS(identifier,"nombres"));
		nombreIn.setBounds(136, 184, 234, 32);
		nombreIn.setFont(font);
		panel2.add(nombreIn);

		apellidos = new JLabel("Apellidos:");
		apellidos.setBounds(21, 226, 105, 32);
		apellidos.setFont(font);
		panel2.add(apellidos);

		apellidosIn = new JTextField(bd.obtenerS(identifier,"apellidos"));
		apellidosIn.setBounds(136, 227, 234, 32);
		apellidosIn.setFont(font);
		panel2.add(apellidosIn);

		direccion = new JLabel("Direccion:");
		direccion.setBounds(21, 269, 105, 32);
		direccion.setFont(font);
		panel2.add(direccion);

		direccionIn = new JTextField(bd.obtenerS(identifier,"direccion"));
		direccionIn.setBounds(136, 270, 234, 32);
		direccionIn.setHorizontalAlignment(JTextField.LEFT);
		direccionIn.setFont(font);
		panel2.add(direccionIn);

		celular =  new JLabel("Celular:");
		celular.setBounds(21, 312, 105, 32);
		celular.setFont(font);
		panel2.add(celular);

		celularIn = new JTextField(bd.obtenerS(identifier,"numero"));
		celularIn.setBounds(136, 313, 234, 32);
		celularIn.setFont(font);
		panel2.add(celularIn);

		eMail = new JLabel("E-Mail:");
		eMail.setBounds(21, 355, 105, 32);
		eMail.setFont(font);
		panel2.add(eMail);

		eMailIn = new JTextField(bd.obtenerS(identifier,"email"));
		eMailIn.setBounds(136, 356, 234, 32);
		eMailIn.setFont(font);
		panel2.add(eMailIn);

        sede = new JLabel("Sede:");
        sede.setBounds(21, 398, 138, 32);
        sede.setFont(font);
        panel2.add(sede);

        sedeIn = new JComboBox<>(bd.cambiarDimension(bd.consultarSede(null,"id_Sede,nombre")));
        sedeIn.setSelectedItem(bd.cambiarDimension(bd.consultarSede(
                bd.obtenerS(identifier, "sede"),"id_sede,nombre")));
        sedeIn.setBounds(169, 399, 201, 32);
        sedeIn.setSelectedItem(bd.obtenerS(identifier,"sede"));
        sedeIn.setEditable(false);
        sedeIn.setFont(font);
        panel2.add(sedeIn);

		tipoEmpleado = new JLabel("Tipo de Empleado:");
		tipoEmpleado.setBounds(21, 441, 138, 32);
		tipoEmpleado.setFont(font);
		panel2.add(tipoEmpleado);

		String[] listaTipo = new String[] { "Jefe de taller", "Vendedor"};
		tipoEmpleadoIn = new JComboBox<>(listaTipo);
		tipoEmpleadoIn.setBounds(169, 442, 201, 32);
		tipoEmpleadoIn.setSelectedItem(bd.obtenerS(identifier,"tipo_usuario").replaceAll("\\s",""));
		tipoEmpleadoIn.setEditable(false);
		tipoEmpleadoIn.setFont(font);
		panel2.add(tipoEmpleadoIn);

		estado = new JLabel("Empleado activo:");
		estado.setBounds(21, 484, 120, 32);
		estado.setFont(font);
		panel2.add(estado);

		String[] estado = new String[] {"true", "false"};
		estadoIn = new JComboBox<>(estado);
		estadoIn.setBounds(169, 485, 201, 32);
		if(bd.obtenerB(identifier,"activo")){
			estadoIn.setSelectedItem("true");
		}
		else estadoIn.setSelectedItem("false");
		estadoIn.setEditable(false);
		estadoIn.setFont(font);
		panel2.add(estadoIn);

		cancelar2 = new JButton("Cancelar");
		cancelar2.setBounds(120, 542, 120, 28);
		cancelar2.setFont(font);
		cancelar2.addActionListener(listener);
		panel2.add(cancelar2);

		actualizar = new JButton("Actualizar");
		actualizar.setBounds(250, 542, 120, 28);
		actualizar.setFont(font);
		actualizar.addActionListener(listener);
		panel2.add(actualizar);

		separator_2 = new JSeparator();
		separator_2.setBounds(21, 529, 349, 2);
		panel2.add(separator_1);
		panel2.add(separator_2);
		panel2.add(icon);

		setSize(400,610);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
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

		Pattern patron = Pattern.compile("[^A-Za-z ]");
		Matcher nombre = patron.matcher(nombreIn.getText());
		Matcher apellido = patron.matcher(apellidosIn.getText());


		if(nombre.find() || nombreIn.getText().length()>30){
			mensaje = mensaje +" Digite un nombre válido \n";
		}
		if(apellido.find() || apellidosIn.getText().length()>30){
			mensaje = mensaje +" Digite un apellido válido \n";
		}

		Pattern patron1 = Pattern.compile("[^A-Za-z0-9_]");
		Matcher usuario = patron1.matcher(usuarioIn.getText());
		Matcher pass = patron1.matcher(new String(contrasenaIn.getPassword()));

		if(usuario.find() || usuarioIn.getText().length()>30){
			mensaje = mensaje +" Digite un usuario valido \n";
		}

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


    private class ManejadorDeBotones implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            String identifier = idIn.getSelectedItem().toString();
            identifier=identifier.substring(0,identifier.indexOf("-"));

            if(e.getSource()==salir1 || e.getSource()==cancelar2){
                dispose();
            }else if(e.getSource()==buscar){

                initGUI2(identifier);

            }else if(e.getSource()==actualizar){
                if(validar()){
                    if(validar2().compareTo("true")==0){

                        String sede =  sedeIn.getSelectedItem().toString();
                        sede = sede.substring(0,sede.indexOf("-"));

                        boolean var = bd.actualizarUsuario(identifier,nombreIn.getText(),apellidosIn.getText(),
                                direccionIn.getText(),celularIn.getText(),eMailIn.getText(),
                                (String) tipoEmpleadoIn.getSelectedItem(),sede,
                                Boolean.parseBoolean((String)estadoIn.getSelectedItem()));

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
