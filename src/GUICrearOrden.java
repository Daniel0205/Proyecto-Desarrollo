import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("serial")
public class GUICrearOrden extends JFrame {

    //Variables y componentes de la GUI
	private Container contenedor;
	private JLabel  fechaEntrega, cantidad, idEncargado, idProducto;
	private JLabel icono, titulo, fondoAzul, fondoGris;
	private JTextField  fechaEntregaIn, cantidadIn, asignadoAIn,idEncargadoIn;
	private JButton crearOrden, salir;
	private JSeparator separador;
	private Font font;
	private BaseDeDatos bd;
	private ActionListener listener;
	private JComboBox idProductoIn;
	private String idenfier;
	private int pX,pY;

    //Constructor
	public GUICrearOrden(BaseDeDatos bdIn,String idenfier) {
		super("REGISTRAR SEDE");
		setTitle("CREAR ORDEN DE TRABAJO");
		this.setUndecorated(true);
		font = new Font("Tahoma", Font.PLAIN, 14);
		this.idenfier = idenfier;
		this.bd = bdIn;
		crearComponentes();
	}

	// Funcion que crea la interfaz y sus componentes
	private void crearComponentes() {

	    String[] productos = bd.cambiarDimension(bd.consultarProductos("id_producto, nombre"));

	    //Valida que existan productos para crear una orden de trabajo
	    if(productos.length!=0) {

            //Configuraciones de la ventana principal
            contenedor = getContentPane();
            contenedor.removeAll();
            getContentPane().setLayout(null);
            ActionListener listener = new ManejadorDeBotones();
            manejadorDesplazamientoVentana(this);
            JPanel panelUsuario = new JPanel();
            panelUsuario.setBackground(Color.BLACK);
            panelUsuario.setBounds(0, 0, 423, 456);
            contenedor.add(panelUsuario);
            panelUsuario.setLayout(null);
            listener = new ManejadorDeBotones();

            idProducto = new JLabel("Id-Producto:");
            idProducto.setFont(font);
            idProducto.setBounds(21, 143, 105, 32);
            panelUsuario.add(idProducto);

            idProductoIn = new JComboBox<>(productos);
            idProductoIn.setFont(font);
            idProductoIn.setBounds(159, 144, 234, 32);
            panelUsuario.add(idProductoIn);

            fechaEntrega = new JLabel("Fecha de Entrega:");
            fechaEntrega.setFont(font);
            fechaEntrega.setBounds(21, 186, 127, 32);
            panelUsuario.add(fechaEntrega);

            fechaEntregaIn = new JTextField();
            fechaEntregaIn.setFont(font);
            fechaEntregaIn.setBounds(159, 186, 234, 32);
            panelUsuario.add(fechaEntregaIn);

            cantidad = new JLabel("Cantidad:");
            cantidad.setFont(font);
            cantidad.setBounds(21, 229, 105, 32);
            panelUsuario.add(cantidad);

            cantidadIn = new JTextField();
            cantidadIn.setFont(font);
            cantidadIn.setBounds(159, 230, 234, 32);
            panelUsuario.add(cantidadIn);

            idEncargado = new JLabel("Id Creador:");
            idEncargado.setFont(font);
            idEncargado.setBounds(21, 272, 105, 32);
            panelUsuario.add(idEncargado);

            idEncargadoIn = new JTextField(idenfier);
            idEncargadoIn.setEditable(false);
            idEncargadoIn.setBounds(159, 274, 234, 32);
            idEncargadoIn.setFont(font);
            panelUsuario.add(idEncargadoIn);

            salir = new JButton("Salir");
            salir.setOpaque(true);
            salir.setBackground(new Color(227, 227, 227));
            salir.setFont(font);
            salir.setBounds(143, 404, 113, 28);
            salir.addActionListener(listener);
            panelUsuario.add(salir);

            crearOrden = new JButton("Crear Orden");
            crearOrden.setOpaque(true);
            crearOrden.setBackground(new Color(227, 227, 227));
            crearOrden.setFont(font);
            crearOrden.setBounds(266, 404, 127, 28);
            crearOrden.addActionListener(listener);
            panelUsuario.add(crearOrden);

            separador = new JSeparator();
            separador.setBounds(44, 391, 349, 2);
            panelUsuario.add(separador);

            JLabel asignadoA = new JLabel("Asignado a:");
            asignadoA.setFont(new Font("Tahoma", Font.PLAIN, 14));
            asignadoA.setBounds(21, 315, 105, 32);
            panelUsuario.add(asignadoA);

            asignadoAIn = new JTextField();
            asignadoAIn.setFont(new Font("Tahoma", Font.PLAIN, 14));
            asignadoAIn.setBounds(159, 316, 234, 32);
            panelUsuario.add(asignadoAIn);

            //Icono a la izquierda del titulo
            icono = new JLabel("");
            icono.setIcon(new ImageIcon(GUIRegistrarUser.class.getResource("/images/titulo_flecha.png")));
            icono.setBounds(11, 1, 48, 90);
            panelUsuario.add(icono);

            //Etiqueta titulo de la ventana
            titulo = new JLabel("CREAR ORDEN DE TRABAJO");
            titulo.setFont(new Font("Tahoma", Font.BOLD, 16));
            titulo.setForeground(Color.WHITE);
            titulo.setBounds(61, 30, 295, 32);
            panelUsuario.add(titulo);

            // -- Fondos azul y gris -- //
            fondoAzul = new JLabel("");
            fondoAzul.setBounds(1, 1, 421, 90);
            fondoAzul.setOpaque(true);
            fondoAzul.setBackground(new Color(45, 118, 232));
            panelUsuario.add(fondoAzul);
            fondoGris = new JLabel("");
            fondoGris.setBounds(1, 89, 421, 366);
            fondoGris.setOpaque(true);
            fondoGris.setBackground(new Color(227, 227, 227));
            panelUsuario.add(fondoGris);

            //Configuraciones adicionales de la ventana principal(0, 0, 422, 602);
            setResizable(false);
            setSize(423, 456);
            setVisible(true);
            setLocationRelativeTo(null);
        }
        else{
            JOptionPane.showMessageDialog(null, "Actualmente no hay productos para crear ordenes");
            GUIMenuJefe menu = new GUIMenuJefe(bd, idenfier);
            menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            dispose();
        }
	}

	// Funcion que valida si algun campo a registrar esta vacio
	private boolean validar1() {
		boolean val = true;
		val = ( fechaEntregaIn.getText().compareTo("") == 0 || cantidadIn.getText().compareTo("") == 0
                || asignadoAIn.getText().compareTo("") == 0)
				? false : true;

		return val;
	}

	// Funcion para validar el dominio de los datos ingresados
	private String validar2() {
		String mensaje = "";

		if (!validarDatoEnteroPositivo(cantidadIn))
			mensaje = mensaje + " La cantidad debe ser un numero entero \n";

		Pattern patron = Pattern.compile("[^A-Za-z ]");
		Matcher nombre = patron.matcher(asignadoAIn.getText());
		if (nombre.find() || asignadoAIn.getText().length() > 40)
			mensaje = mensaje + " Digite un nombre valido \n";

		Pattern patron_fecha = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}");
		Matcher fecha = patron_fecha.matcher(fechaEntregaIn.getText());
		if (!fecha.find())
			mensaje = mensaje + " Digite una fecha valida (AAAA-MM-DD) \n";

		if (mensaje.compareTo("") == 0)
			mensaje = "true";

		return mensaje;
	}

	// Funcion que valida s� un dato ingresado a traves de un JTextField es entero
	// positivo
	private boolean validarDatoEnteroPositivo(JTextField dato) {
		boolean val = true;
		try {
			Integer.parseInt(dato.getText());

		} catch (NumberFormatException excepcion) {
			val = false;
		}
		val = (Integer.parseInt(dato.getText()) >= 0) ? true : false;
		return val;
	}

	// Manejador de eventos para los botones del apartado Crear-Orden-de-Trabajo
	// Si se presiona <salir>, la interfaz del menu se cierra
	// Si se presiona <crear orden>, se valida que:
	// no hayan campos vacios, los datos esten dentro del dominio y
	// las id ingresadas esten disponibles
	private class ManejadorDeBotones implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			if (actionEvent.getSource() == salir) {
                GUIMenuJefe menu = new GUIMenuJefe(bd, idenfier);
                menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				dispose();
			} else if (actionEvent.getSource() == crearOrden) {
				if (validar1()) {
					if (validar2().compareTo("true") == 0) {

						String idProducto = idProductoIn.getSelectedItem().toString();
						idProducto = idProducto.substring(0, idProducto.indexOf("-"));
						
						boolean var = bd.crearOrdendeTrabajo( fechaEntregaIn.getText(),
								cantidadIn.getText(), idProducto, idEncargadoIn.getText(), asignadoAIn.getText(),"false");
						if (var)
							JOptionPane.showMessageDialog(null, "Orden de trabajo registrada exitosamente");
						else
							JOptionPane.showMessageDialog(null, "Error al crear orden de trabajo.");
                        GUIMenuJefe menu = new GUIMenuJefe(bd, idenfier);
                        menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						dispose();
					} else
						JOptionPane.showMessageDialog(null, validar2());
				} else
					JOptionPane.showMessageDialog(null, "Digite todos los campos");
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
