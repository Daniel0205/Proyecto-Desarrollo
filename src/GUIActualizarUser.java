import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("serial")
public class GUIActualizarUser extends JFrame {

	// Variables y componentes de la GUI
	private BaseDeDatos bd;
	private Container contenedor;
	private JLabel instruccion, usuario, nombre, apellidos, direccion;
	private JLabel contrasena, eMail, tipoEmpleado, sede, estado, celular;
	private JPasswordField contrasenaIn;
	@SuppressWarnings("unused")
	private JPanel panel;
	private JTextField usuarioIn, nombreIn, apellidosIn, direccionIn, celularIn, eMailIn;
	private JComboBox<String> tipoEmpleadoIn, sedeIn, estadoIn, idIn;
	private JButton salir1, cancelar2, buscar, actualizar;
	private Font font;
	private ActionListener listener = new ManejadorDeBotones();
	private int pX, pY;

	// Constructor
	public GUIActualizarUser(BaseDeDatos bdIn) {
		super("ACTUALIZAR EMPLEADO");
		getContentPane().setBackground(Color.BLACK);

		font = new Font("Tahoma", Font.PLAIN, 14);
		bd = bdIn;
		initGUI();
	}

	// FUnciona para inicializar los elemens graficos
	private void initGUI() {

		String[] users = bd.cambiarDimension(bd.consultarUsuarios(null, null, "cedula,nombres"));

		if (users.length != 0) {

			// Configuraciones generales de la primer ventana
			contenedor = getContentPane();
			contenedor.removeAll();
			getContentPane().setLayout(null);
			this.setUndecorated(true);

			// Etiqueta que describe el funcionamiento
			instruccion = new JLabel("Usuario a modificar");
			instruccion.setFont(font);
			instruccion.setBounds(22, 101, 138, 32);
			getContentPane().add(instruccion);

			idIn = new JComboBox<>(users);
			idIn.setFont(font);
			idIn.setBounds(170, 101, 243, 32);
			getContentPane().add(idIn);

			salir1 = new JButton("Salir");
			salir1.setOpaque(true);
			salir1.setBackground(new Color(227, 227, 227));
			salir1.setFont(font);
			salir1.setBounds(433, 184, 100, 32);
			salir1.addActionListener(listener);
			getContentPane().add(salir1);

			buscar = new JButton("Buscar");
			buscar.setOpaque(true);
			buscar.setBackground(new Color(227, 227, 227));
			buscar.setBounds(433, 101, 100, 32);
			buscar.setFont(font);
			buscar.addActionListener(listener);
			getContentPane().add(buscar);

			JSeparator separador = new JSeparator();
			separador.setBounds(22, 172, 511, 2);
			getContentPane().add(separador);

			// Icono a la izquierda del titulo
			JLabel icono = new JLabel("");
			icono.setIcon(new ImageIcon(GUIConsultarUser.class.getResource("/images/actualizar.png")));
			icono.setBounds(11, 1, 48, 90);
			getContentPane().add(icono);

			// Etiqueta del titulo de la ventana
			JLabel titulo = new JLabel("ACTUALIZAR USUARIO");
			titulo.setFont(new Font("Tahoma", Font.BOLD, 16));
			titulo.setForeground(Color.WHITE);
			titulo.setBounds(69, 28, 175, 32);
			getContentPane().add(titulo);

			// -- Fondos azul y gris -- //
			JLabel fondoAzul = new JLabel("");
			fondoAzul.setBounds(1, 1, 560, 90);
			fondoAzul.setOpaque(true);
			fondoAzul.setBackground(new Color(45, 118, 232));
			getContentPane().add(fondoAzul);
			JLabel fondoGris = new JLabel("");
			fondoGris.setBounds(1, 89, 560, 166);
			fondoGris.setOpaque(true);
			fondoGris.setBackground(new Color(227, 227, 227));// Gris
			getContentPane().add(fondoGris);

			// Fondo negro para las margenes
			JLabel fondoNegro = new JLabel("");
			fondoNegro.setBackground(Color.BLACK);
			fondoNegro.setForeground(Color.RED);
			fondoNegro.setOpaque(true);
			fondoNegro.setBounds(0, 0, 562, 264);
			getContentPane().add(fondoNegro);

			setResizable(false);
			setSize(562, 256);
			setVisible(true);
			setLocationRelativeTo(null);
		} else {
			JOptionPane.showMessageDialog(null, "Actualmente no hay usuarios creados");
		}
	}

	// Funcion para inicializar la interfaz grafica para
	// realizar la actualizacion de un usuario
	private void initGUI2(String identifier) {

		Container ventana = getContentPane();
		ventana.setLayout(null);
		manejadorDesplazamientoVentana(this);
		ventana.removeAll();
		this.setUndecorated(true);

		usuario = new JLabel("id/Documento:");
		usuario.setBounds(25, 128, 105, 32);
		usuario.setFont(font);
		ventana.add(usuario);

		usuarioIn = new JTextField(bd.obtenerS(identifier, "cedula"));
		usuarioIn.setBounds(140, 128, 234, 32);
		usuarioIn.setEditable(false);
		usuarioIn.setFont(font);
		ventana.add(usuarioIn);

		contrasena = new JLabel("Contrasena:");
		contrasena.setBounds(25, 167, 105, 32);
		contrasena.setFont(font);
		ventana.add(contrasena);

		contrasenaIn = new JPasswordField();
		contrasenaIn.setBounds(140, 168, 234, 32);
		contrasenaIn.setFont(font);
		ventana.add(contrasenaIn);

		nombre = new JLabel("Nombres:");
		nombre.setBounds(25, 210, 105, 32);
		nombre.setFont(font);
		ventana.add(nombre);

		nombreIn = new JTextField(bd.obtenerS(identifier, "nombres"));
		nombreIn.setBounds(140, 211, 234, 32);
		nombreIn.setFont(font);
		ventana.add(nombreIn);

		apellidos = new JLabel("Apellidos:");
		apellidos.setBounds(25, 253, 105, 32);
		apellidos.setFont(font);
		ventana.add(apellidos);

		apellidosIn = new JTextField(bd.obtenerS(identifier, "apellidos"));
		apellidosIn.setBounds(140, 254, 234, 32);
		apellidosIn.setFont(font);
		ventana.add(apellidosIn);

		direccion = new JLabel("Direccion:");
		direccion.setBounds(25, 296, 105, 32);
		direccion.setFont(font);
		ventana.add(direccion);

		direccionIn = new JTextField(bd.obtenerS(identifier, "direccion"));
		direccionIn.setBounds(140, 297, 234, 32);
		direccionIn.setHorizontalAlignment(JTextField.LEFT);
		direccionIn.setFont(font);
		ventana.add(direccionIn);

		celular = new JLabel("Celular:");
		celular.setBounds(25, 339, 105, 32);
		celular.setFont(font);
		ventana.add(celular);

		celularIn = new JTextField(bd.obtenerS(identifier, "numero"));
		celularIn.setBounds(140, 340, 234, 32);
		celularIn.setFont(font);
		ventana.add(celularIn);

		eMail = new JLabel("E-Mail:");
		eMail.setBounds(25, 382, 105, 32);
		eMail.setFont(font);
		ventana.add(eMail);

		eMailIn = new JTextField(bd.obtenerS(identifier, "email"));
		eMailIn.setBounds(140, 383, 234, 32);
		eMailIn.setFont(font);
		ventana.add(eMailIn);

		sede = new JLabel("Sede:");
		sede.setBounds(25, 425, 138, 32);
		sede.setFont(font);
		ventana.add(sede);

		sedeIn = new JComboBox<>(bd.cambiarDimension(bd.consultarSede(null, "id_Sede,nombre")));
		sedeIn.setSelectedItem(
				bd.cambiarDimension(bd.consultarSede(bd.obtenerS(identifier, "sede"), "id_sede,nombre"))[0]);
		sedeIn.setBounds(173, 426, 201, 32);
		sedeIn.setSelectedItem(bd.obtenerS(identifier, "sede"));
		sedeIn.setEditable(false);
		sedeIn.setFont(font);
		ventana.add(sedeIn);

		tipoEmpleado = new JLabel("Tipo de Empleado:");
		tipoEmpleado.setBounds(25, 468, 138, 32);
		tipoEmpleado.setFont(font);
		ventana.add(tipoEmpleado);

		String[] listaTipo = new String[] { "Jefe de taller", "Vendedor" };
		tipoEmpleadoIn = new JComboBox<>(listaTipo);
		tipoEmpleadoIn.setBounds(173, 469, 201, 32);
		tipoEmpleadoIn.setSelectedItem(bd.obtenerS(identifier, "tipo_usuario").replaceAll("\\s", ""));
		tipoEmpleadoIn.setEditable(false);
		tipoEmpleadoIn.setFont(font);
		ventana.add(tipoEmpleadoIn);

		estado = new JLabel("Empleado activo:");
		estado.setBounds(25, 511, 120, 32);
		estado.setFont(font);
		ventana.add(estado);

		String[] estado = new String[] { "true", "false" };
		estadoIn = new JComboBox<>(estado);
		estadoIn.setBounds(173, 512, 201, 32);
		if (bd.obtenerB(identifier, "activo")) {
			estadoIn.setSelectedItem("true");
		} else
			estadoIn.setSelectedItem("false");
		estadoIn.setEditable(false);
		estadoIn.setFont(font);
		ventana.add(estadoIn);

		cancelar2 = new JButton("Cancelar");
		cancelar2.setOpaque(true);
		cancelar2.setBackground(new Color(227, 227, 227));
		cancelar2.setBounds(124, 569, 120, 28);
		cancelar2.setFont(font);
		cancelar2.addActionListener(listener);
		ventana.add(cancelar2);

		actualizar = new JButton("Actualizar");
		actualizar.setOpaque(true);
		actualizar.setBackground(new Color(227, 227, 227));
		actualizar.setBounds(254, 569, 120, 28);
		actualizar.setFont(font);
		actualizar.addActionListener(listener);
		ventana.add(actualizar);

		JSeparator separador = new JSeparator();
		separador.setBounds(25, 556, 349, 2);
		ventana.add(separador);
		ventana.add(separador);

		// Icono a la izquierda del titulo
		JLabel icono = new JLabel("");
		icono.setIcon(new ImageIcon(GUIRegistrarUser.class.getResource("/images/actualizar.png")));
		icono.setBounds(11, 1, 48, 90);
		ventana.add(icono);

		// Etiqueta titulo de la ventana
		JLabel titulo = new JLabel("  ACTUALIZAR USUARIO");
		titulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		titulo.setForeground(Color.WHITE);
		titulo.setBounds(61, 30, 211, 32);
		ventana.add(titulo);

		// -- Fondos azul y gris -- //
		JLabel fondoAzul = new JLabel("");
		fondoAzul.setBounds(1, 1, 398, 90);
		fondoAzul.setOpaque(true);
		fondoAzul.setBackground(new Color(45, 118, 232));
		ventana.add(fondoAzul);
		JLabel fondoGris = new JLabel("");
		fondoGris.setBounds(1, 89, 398, 520);
		fondoGris.setOpaque(true);
		fondoGris.setBackground(new Color(227, 227, 227));
		ventana.add(fondoGris);

		// Configuraciones adicionales de la ventana principal
		this.setSize(400, 610);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}

	// Funcion que valida si algun campo a registrar esta vacio
	private boolean validar() {
		boolean val = true;
		if (nombreIn.getText().compareTo("") == 0) {
			val = false;
		}
		if (apellidosIn.getText().compareTo("") == 0) {
			val = false;
		}
		if (usuarioIn.getText().compareTo("") == 0) {
			val = false;
		}
		if (direccionIn.getText().compareTo("") == 0) {
			val = false;
		}
		if (celularIn.getText().compareTo("") == 0) {
			val = false;
		}
		if (eMailIn.getText().compareTo("") == 0) {
			val = false;
		}

		return val;
	}

	// Funcion para validar el dominio de los datos ingresados
	private String validar2() {
		String mensaje = "";

		Pattern patron = Pattern.compile("[^A-Za-z ]");
		Matcher nombre = patron.matcher(nombreIn.getText());
		Matcher apellido = patron.matcher(apellidosIn.getText());

		if (nombre.find() || nombreIn.getText().length() > 30) {
			mensaje = mensaje + " Digite un nombre válido \n";
		}
		if (apellido.find() || apellidosIn.getText().length() > 30) {
			mensaje = mensaje + " Digite un apellido válido \n";
		}

		Pattern patron1 = Pattern.compile("[^A-Za-z0-9_]");
		Matcher usuario = patron1.matcher(usuarioIn.getText());
		Matcher pass = patron1.matcher(new String(contrasenaIn.getPassword()));

		if (usuario.find() || usuarioIn.getText().length() > 30) {
			mensaje = mensaje + " Digite un usuario valido \n";
		}

		if (pass.find()) {
			mensaje = mensaje + " Digite una contrasena valida \n";
		}

		Pattern patron2 = Pattern.compile("[^A-Za-z0-9 #-]");
		Matcher direccion = patron2.matcher(direccionIn.getText());

		if (direccion.find() || direccionIn.getText().length() > 40) {
			mensaje = mensaje + " Digite una direccion valida \n";
		}

		patron2 = Pattern.compile("[^0-9]");
		Matcher cel = patron2.matcher(celularIn.getText());

		if (cel.find() || celularIn.getText().length() > 40 || celularIn.getText().length() < 7) {
			mensaje = mensaje + " Digite un telefono celular valido \n";
		}

		if (!eMailIn.getText().matches("([^@])+@([^@])+[\\p{Punct}&&[^@]](.[a-z]{1,4})*")) {
			mensaje = mensaje + " Digite un E-mail valido \n";
		}

		if (mensaje.compareTo("") == 0) {
			mensaje = "true";
		}

		return mensaje;
	}

	// Clase para manejar los eventos sobre los botones
	private class ManejadorDeBotones implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == salir1 || e.getSource() == cancelar2) {
			    dispose();
			}

			String identifier = idIn.getSelectedItem().toString();
			identifier = identifier.substring(0, identifier.indexOf("-"));

			if (e.getSource() == buscar) {
				dispose();
				initGUI2(identifier);
			}

			if (e.getSource() == actualizar) {
				if (validar()) {
					if (validar2().compareTo("true") == 0) {

						String sede = sedeIn.getSelectedItem().toString();
						sede = sede.substring(0, sede.indexOf("-"));

						String pass = null;

						if (new String(contrasenaIn.getPassword()).compareTo("") != 0)
							pass = new String(contrasenaIn.getPassword());

						boolean var = bd.actualizarUsuario(identifier, nombreIn.getText(), apellidosIn.getText(),
								direccionIn.getText(), celularIn.getText(), eMailIn.getText(),
								(String) tipoEmpleadoIn.getSelectedItem(), sede,
								Boolean.parseBoolean((String) estadoIn.getSelectedItem()), pass);

						if (var) {
							JOptionPane.showMessageDialog(null, "Usuario actualizado exitosamente");
						} else
							JOptionPane.showMessageDialog(null, "Error al actualizar usuario.");
						dispose();

					} else
						JOptionPane.showMessageDialog(null, validar2());

				} else
					JOptionPane.showMessageDialog(null, "Digite todos los campos requeridos.");

			}
		}
	}

	// Manejador del desplazamiento de la ventana causado por el arrastre del
	// mouse
	private void manejadorDesplazamientoVentana(JFrame frame) {
		frame.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				pX = me.getX();
				pY = me.getY();
			}
		});
		frame.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent me) {
				setLocation(getLocation().x + me.getX() - pX, getLocation().y + me.getY() - pY);
			}
		});
	}
}
