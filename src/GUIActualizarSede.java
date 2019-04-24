import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GUIActualizarSede extends JFrame {

    //Variables y componentes de la GUI
	private BaseDeDatos bd;
	private Font font;
	private ActionListener listener = new ManejadorDeBotones();
	private Container contenedor;
	private JLabel instruccion, direccion, celular, aCargoDe;
	private JLabel id, nombre;
	private JSeparator separador;
	private JButton salir1, cancelar2, buscar, actualizar;
	private JTextField direccionIn, celularIn, nombreIn, idT;
	private JComboBox idIn, aCargoDeIn;
	private int pX, pY;

	//Contructor
	public GUIActualizarSede(BaseDeDatos bdIn) {
		super("ACTUALIZAR SEDE");
		getContentPane().setBackground(Color.BLACK);
		font = new Font("Tahoma", Font.PLAIN, 14);
		bd = bdIn;
		initGUI();
	}

	// FUnciona para inicializar los elementos graficos
	private void initGUI() {
		String[] sedes = bd.cambiarDimension(bd.consultarSede(null, "id_Sede,nombre"));

		if(sedes.length!=0) {
            // Configuraciones generales de la primer ventana
            this.setUndecorated(true);
            contenedor = getContentPane();
            contenedor.removeAll();
            getContentPane().setLayout(null);

            // Etiqueta que describe el funcionamiento
            instruccion = new JLabel("Id-Nombre de sede");
            instruccion.setFont(font);
            instruccion.setBounds(21, 112, 138, 32);
            getContentPane().add(instruccion);

            idIn = new JComboBox<>(bd.cambiarDimension(bd.consultarSede(null, "id_Sede,nombre")));
            idIn.setFont(font);
            idIn.setBounds(164, 113, 250, 32);
            getContentPane().add(idIn);

            salir1 = new JButton("Salir");
            salir1.setOpaque(true);
            salir1.setBackground(new Color(227, 227, 227));
            salir1.setFont(font);
            salir1.setBounds(424, 196, 100, 32);
            salir1.addActionListener(listener);
            getContentPane().add(salir1);

            buscar = new JButton("Buscar");
            buscar.setOpaque(true);
            buscar.setBackground(new Color(227, 227, 227));
            buscar.setBounds(424, 113, 100, 32);
            buscar.setFont(font);
            buscar.addActionListener(listener);
            getContentPane().add(buscar);

            JSeparator separator = new JSeparator();
            separator.setBounds(22, 184, 502, 2);
            getContentPane().add(separator);

            // Icono a la izquierda del titulo
            JLabel icono = new JLabel("");
            icono.setIcon(new ImageIcon(GUIConsultarUser.class.getResource("/images/actualizar.png")));
            icono.setBounds(11, 1, 48, 90);
            getContentPane().add(icono);

            // Etiqueta del titulo de la ventana
            JLabel titulo = new JLabel("ACTUALIZAR SEDE");
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
            fondoGris.setBackground(new Color(227, 227, 227));
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
        }
		else{
			JOptionPane.showMessageDialog(null, "Actualmente no hay sedes creadas");
            GUIMenuAdmin menu = new GUIMenuAdmin(bd);
            menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            dispose();
		}
	}

	// Funcion para inicializar la interfaz grafica para
	// realizar la actualizacion de una sede
	private void initGUI2(String identifier) {

		Container ventana = getContentPane();
		ventana.setLayout(null);
		manejadorDesplazamientoVentana(this);
		ventana.removeAll();
		this.setUndecorated(true);

		id = new JLabel("Id:");
		id.setBounds(22, 122, 105, 32);
		id.setFont(font);
		ventana.add(id);

		idT = new JTextField(identifier);
		idT.setBounds(137, 122, 234, 32);
		idT.setEditable(false);
		idT.setFont(font);
		ventana.add(idT);

		nombre = new JLabel("Nombre:");
		nombre.setBounds(22, 161, 105, 32);
		nombre.setFont(font);
		ventana.add(nombre);

		nombreIn = new JTextField(bd.obtenerSede(identifier, "nombre"));
		nombreIn.setBounds(137, 162, 234, 32);
		nombreIn.setFont(font);
		ventana.add(nombreIn);

		direccion = new JLabel("Direccion:");
		direccion.setBounds(22, 204, 105, 32);
		direccion.setFont(font);
		ventana.add(direccion);

		direccionIn = new JTextField(bd.obtenerSede(identifier, "direccion"));
		direccionIn.setBounds(137, 205, 234, 32);
		direccionIn.setFont(font);
		ventana.add(direccionIn);

		celular = new JLabel("Telefono:");
		celular.setBounds(22, 247, 105, 32);
		celular.setFont(font);
		ventana.add(celular);

		celularIn = new JTextField(bd.obtenerSede(identifier, "telefono"));
		celularIn.setBounds(137, 248, 234, 32);
		celularIn.setFont(font);
		ventana.add(celularIn);

		aCargoDe = new JLabel("Encargado:");
		aCargoDe.setBounds(22, 290, 105, 32);
		aCargoDe.setFont(font);
		ventana.add(aCargoDe);

		aCargoDeIn = new JComboBox<>(bd.cambiarDimension(bd.consultarUsuarios("Sede", identifier, "cedula,nombres")));
		seleccionarEmpleado(identifier);
		aCargoDeIn.setBounds(137, 291, 234, 32);
		aCargoDeIn.setFont(font);
		ventana.add(aCargoDeIn);

		cancelar2 = new JButton("Cancelar");
		cancelar2.setOpaque(true);
		cancelar2.setBackground(new Color(227, 227, 227));
		cancelar2.setBounds(121, 366, 120, 28);
		cancelar2.setFont(font);
		cancelar2.addActionListener(listener);
		ventana.add(cancelar2);

		actualizar = new JButton("Actualizar");
		actualizar.setOpaque(true);
		actualizar.setBackground(new Color(227, 227, 227));
		actualizar.setBounds(251, 366, 120, 28);
		actualizar.setFont(font);
		actualizar.addActionListener(listener);
		ventana.add(actualizar);

		separador = new JSeparator();
		separador.setBounds(22, 346, 349, 2);
		ventana.add(separador);

		// Icono a la izquierda del titulo
		JLabel icono = new JLabel("");
		icono.setIcon(new ImageIcon(GUIRegistrarUser.class.getResource("/images/actualizar.png")));
		icono.setBounds(11, 1, 48, 90);
		ventana.add(icono);

		// Etiqueta titulo de la ventana
		JLabel titulo = new JLabel("  ACTUALIZAR SEDE");
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
		fondoGris.setBounds(1, 89, 398, 345);
		fondoGris.setOpaque(true);
		fondoGris.setBackground(new Color(227, 227, 227));
		ventana.add(fondoGris);

		// Configuraciones adicionales de la ventana principal
		setSize(400, 435);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);

	}

	//Funcion para seleccionar el empleado que se encuentra a cargo actualmente en la sede
    //De no haber un usuario encargado deja vacio el campo
	private void seleccionarEmpleado(String identifier) {
		if (bd.obtenerSede(identifier, "empleado_a_cargo") != null) {
			String[][] aux = bd.consultarUsuarios("Id", bd.obtenerSede(identifier, "empleado_a_cargo"),
					"cedula,nombres,sede");

			if (aux[0][2].compareTo(identifier) != 0) {
				aCargoDeIn.addItem(bd.cambiarDimension(aux)[0]);
			}
			aCargoDeIn.addItem(null);
			aCargoDeIn.setSelectedItem(bd.cambiarDimension(aux)[0]);
		} else
			aCargoDeIn.setSelectedItem(null);
	}


    //Funcion que valida si algun campo a registrar esta vacio
    private boolean validar1(){
        boolean val=true;
        val = (direccionIn.getText().compareTo("")==0 ||
                celularIn.getText().compareTo("")==0 ||
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
        Matcher tel = patron.matcher(celularIn.getText());
        if(tel.find()|| celularIn.getText().length()>40|| celularIn.getText().length()<7)
            mensaje = mensaje + " Digite un numero de telefono valido \n";

        if(mensaje.compareTo("")==0)
            mensaje="true";

        return mensaje;
    }

	// Clase para manejar los eventos sobre los botones
	private class ManejadorDeBotones implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == salir1 || e.getSource() == cancelar2) {
                GUIMenuAdmin menu = new GUIMenuAdmin(bd);
                menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				dispose();
			}

			String aCargo, identifier = idIn.getSelectedItem().toString();
			identifier = identifier.substring(0, identifier.indexOf("-"));
			if (e.getSource() == buscar) {
				dispose();
				initGUI2(identifier);
			}

			if (e.getSource() == actualizar) {
                if(validar1()) {
                    if (validar2().compareTo("true") == 0) {
                        if (aCargoDeIn.getSelectedItem() != null) {
                            aCargo = aCargoDeIn.getSelectedItem().toString();
                            aCargo = aCargo.substring(0, aCargo.indexOf("-"));
                        } else
                            aCargo = null;

                        boolean var = bd.actualizarSede(identifier, direccionIn.getText(), celularIn.getText(), aCargo);

                        if (var) {
                            JOptionPane.showMessageDialog(null, "Usuario actualizado exitosamente");

                        } else
                            JOptionPane.showMessageDialog(null, "Error al actualizar usuario");
                        GUIMenuAdmin menu = new GUIMenuAdmin(bd);
                        menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
