import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Esta clase permite mostrar una interfaz sencilla para el registro
 * de nuevas sedes en la base de datos de la empresa.
 * 
 *  Los campos requeridos son: 'Id', 'Direccion', 'Telefono' e 'Id del Encargado''.
 *  
 *  El resultado del registro satisfactorio o las fallas se notifican
 *  por medio cuadros de dialogo.
 */


@SuppressWarnings("serial")
public class GUIRegistrarSedes extends JFrame{

	//Variables y componentes de la GUI
	private Container contenedor;
	private JLabel direccion, telefono, encargado, nombre;
	private JLabel icono, titulo, fondoAzul, fondoGris;
	private JTextField direccionIn, telefonoIn, nombreIn;
	private JComboBox encargadoIn;
	private JButton registrar, cancelar;
	private JSeparator separador;
	private Font font;
	private BaseDeDatos bd;
	private ActionListener listener;
	private int pX,pY;

	//Constructor
	public GUIRegistrarSedes(BaseDeDatos bdIn){
	    super("REGISTRAR SEDE");
		this.setUndecorated(true);
		font = new Font("Tahoma", Font.PLAIN, 14);
        bd = bdIn;
		crearComponentes();
	}

	//Funcion que crea la interfaz y sus componentes
	private void crearComponentes() {

		//Configuraciones de la ventana principal
		contenedor = getContentPane();
		contenedor.removeAll();
		getContentPane().setLayout(null);
		ActionListener listener = new ManejadorDeBotones();
		manejadorDesplazamientoVentana(this);
		JPanel panelUsuario = new JPanel();
		panelUsuario.setBackground(Color.BLACK);
		panelUsuario.setBounds(0, 0, 400, 602);
		contenedor.add(panelUsuario);
		panelUsuario.setLayout(null);
		listener = new ManejadorDeBotones();

		nombre = new JLabel("Nombre:");
		nombre.setFont(font);
		nombre.setBounds(21, 143, 105, 32);
		panelUsuario.add(nombre);

		nombreIn = new JTextField();
		nombreIn.setFont(font);
		nombreIn.setBounds(136, 144, 234, 32);
		panelUsuario.add(nombreIn);

		direccion = new JLabel("Direccion:");
		direccion.setFont(font);
		direccion.setBounds(21, 186, 105, 32);
		panelUsuario.add(direccion);

		direccionIn = new JTextField();
		direccionIn.setFont(font);
		direccionIn.setBounds(136, 186, 234, 32);
		panelUsuario.add(direccionIn);

		telefono =  new JLabel("Telefono:");
		telefono.setFont(font);
		telefono.setBounds(21, 229, 105, 32);
		panelUsuario.add(telefono);

		telefonoIn = new JTextField();
		telefonoIn.setFont(font);
		telefonoIn.setBounds(136, 230, 234, 32);
		panelUsuario.add(telefonoIn);

		encargado = new JLabel("Encargado:");
		encargado.setFont(font);
		encargado.setBounds(21, 272, 105, 32);
		panelUsuario.add(encargado);

		encargadoIn = new JComboBox<>(bd.cambiarDimension(
				bd.consultarUsuarios("Activo",null,"cedula,nombres")));
		encargadoIn.setSelectedItem(null);
		encargadoIn.setFont(font);
		encargadoIn.setBounds(136, 274, 234, 32);
		panelUsuario.add(encargadoIn);

		//Boton para cancelar el registro de un sede
		cancelar = new JButton("Cancelar");
		cancelar.setOpaque(true);
		cancelar.setBackground(new Color(227, 227, 227));
		cancelar.setFont(font);
		cancelar.setBounds(120, 351, 113, 28);
		cancelar.addActionListener(listener);
		panelUsuario.add(cancelar);

		//Boton para iniciar el registro de un sede
		registrar = new JButton("Registrar Sede");
		registrar.setOpaque(true);
		registrar.setBackground(new Color(227, 227, 227));
		registrar.setFont(font);
		registrar.setBounds(243, 351, 127, 28);
		registrar.addActionListener(listener);
		panelUsuario.add(registrar);
		
		//Separador inferior
		separador = new JSeparator();
		separador.setBounds(21, 338, 349, 2);
		panelUsuario.add(separador);

		//Icono a la izquierda del titulo
		icono = new JLabel("");
		icono.setIcon(new ImageIcon(GUIRegistrarUser.class.getResource("/images/titulo_flecha.png")));
		icono.setBounds(11, 1, 48, 90);
		panelUsuario.add(icono);
		
		//Etiqueta titulo de la ventana
		titulo = new JLabel("REGISTRAR SEDE");
		titulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		titulo.setForeground(Color.WHITE);
		titulo.setBounds(61, 30, 211, 32);
		panelUsuario.add(titulo);
		
		// -- Fondos azul y gris -- //
		fondoAzul = new JLabel("");
		fondoAzul.setBounds(1, 1, 398, 90);
		fondoAzul.setOpaque(true);
		fondoAzul.setBackground(new Color(45, 118, 232));
		panelUsuario.add(fondoAzul);
		fondoGris = new JLabel("");
		fondoGris.setBounds(1, 89, 398, 336);
		fondoGris.setOpaque(true);
		fondoGris.setBackground(new Color(227,227,227));
		panelUsuario.add(fondoGris);
		
		//Configuraciones adicionales de la ventana principal
		setResizable(false);
		setSize(400,426);
		setVisible(true);
		setLocationRelativeTo(null);
	}


	//Funcion que valida si algun campo a registrar esta vacio
	private boolean validar1(){
		boolean val=true;
		val = (direccionIn.getText().compareTo("")==0 ||
				telefonoIn.getText().compareTo("")==0 ||
				nombreIn.getText().compareTo("")==0) ? false : true;

		return val;
	}


	//Funcion para validar el dominio de los datos ingresados
	private String validar2(){
		String mensaje = "";


		Pattern patron = Pattern.compile("[^A-Za-z0-9 #-]");
		Matcher direccion = patron.matcher(direccionIn.getText());
		if(direccion.find()|| direccionIn.getText().length()>40) 
			mensaje = mensaje + " Digite una direccion valida \n";

		Matcher nombre = patron.matcher(nombreIn.getText());
        if(nombre.find()|| nombreIn.getText().length()>40)
            mensaje = mensaje + " Digite un nombre valido \n";

		patron = Pattern.compile("[^0-9]");
		Matcher tel = patron.matcher(telefonoIn.getText());
		if(tel.find()|| telefonoIn.getText().length()>40|| telefonoIn.getText().length()<7) 
			mensaje = mensaje + " Digite un numero de telefono valido \n";

		if(mensaje.compareTo("")==0)
			mensaje="true";

		return mensaje;
	}


	//Manejador de eventos para los botones del apartado Registrar-Sede
	//Si se presiona <cancelar>, la interfaz del menu se cierra
	//Si se presiona <registrar>, se valida que:
	//		no hayan campos vacios, los datos esten dentro del dominio y 
	//		las id ingresadas esten disponibles 
	private class ManejadorDeBotones implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			if(actionEvent.getSource() == cancelar){
				dispose();
			}
			else if(actionEvent.getSource() == registrar){
				if(validar1()) {
					if(validar2().compareTo("true")==0) {
						String encargado=null;
						if(encargadoIn.getSelectedItem()!=null){
							if (!(encargadoIn.getSelectedItem().toString().compareTo("")==0)){
								encargado=encargadoIn.getSelectedItem().toString();
								encargado=encargado.substring(0,encargado.indexOf("-"));
							}
						}

						boolean var = bd.registraSede( direccionIn.getText(),
								telefonoIn.getText(),encargado,nombreIn.getText());
						if (var) JOptionPane.showMessageDialog(null, "Sede registrada exitosamente");
						else JOptionPane.showMessageDialog(null, "Error al actualizar usuario.");
						dispose();
					}
					else JOptionPane.showMessageDialog(null, validar2());
				}
				else JOptionPane.showMessageDialog(null, "Digite todos los campos");
			}

		}
	}
	
	
	 // Manejador del desplazamiento de la ventana causado por el arrastre del mouse
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



