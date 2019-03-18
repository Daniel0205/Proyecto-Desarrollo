import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("serial")
public class GUICrearOrden extends JFrame {

	private Container contenedor;
	private JLabel  fechaEntrega, cantidad, idEncargado, idProducto;
	private JTextField  fechaEntregaIn, cantidadIn, asignadoAIn,idEncargadoIn;
	private JButton crearOrden, salir;
	private JSeparator separator_1, separator_2;
	private Font font;
	private BaseDeDatos bd;
	private ActionListener listener;
	private JComboBox idProductoIn;
	private String idenfier;


	public GUICrearOrden(BaseDeDatos bdIn,String idenfier) {
		super("REGISTRAR SEDE");
		setTitle("CREAR ORDEN DE TRABAJO");

		font = new Font("Tahoma", Font.PLAIN, 14);

		this.idenfier = idenfier;
		this.bd = bdIn;
		crearComponentes();
	}

	// Funcion que crea la interfaz y sus componentes
	private void crearComponentes() {

		contenedor = getContentPane();
		contenedor.removeAll();
		getContentPane().setLayout(null);

		JPanel panelUsuario = new JPanel();
		panelUsuario.setBounds(0, 0, 415, 475);
		contenedor.add(panelUsuario);
		panelUsuario.setLayout(null);
/*
		idOrden = new JLabel("Id Orden:");
		idOrden.setFont(font);
		idOrden.setBounds(21, 101, 105, 32);
		panelUsuario.add(idOrden);

		idOrdenIn = new JTextField();
		idOrdenIn.setFont(font);
		idOrdenIn.setBounds(159, 101, 234, 32);
		panelUsuario.add(idOrdenIn);
*/
		idProducto = new JLabel("Id-Producto:");
		idProducto.setFont(font);
		idProducto.setBounds(21, 143, 105, 32);
		panelUsuario.add(idProducto);

		idProductoIn = new JComboBox<>(bd.cambiarDimension(bd.consultarProductos("id_producto, nombre")));
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

		listener = new ManejadorDeBotones();

		salir = new JButton("Salir");
		salir.setFont(font);
		salir.setBounds(143, 436, 113, 28);
		salir.addActionListener(listener);
		panelUsuario.add(salir);

		crearOrden = new JButton("Crear Orden");
		crearOrden.setFont(font);
		crearOrden.setBounds(266, 436, 127, 28);
		crearOrden.addActionListener(listener);
		panelUsuario.add(crearOrden);

		separator_2 = new JSeparator();
		separator_2.setBounds(44, 423, 349, 2);
		panelUsuario.add(separator_2);

		separator_1 = new JSeparator();
		separator_1.setBounds(44, 88, 349, 2);
		panelUsuario.add(separator_1);

		JLabel lblNewLabel = new JLabel("");
		URL filePath = this.getClass().getResource("/images/home.png");
		lblNewLabel.setIcon(new ImageIcon(filePath));
		lblNewLabel.setBounds(21, 11, 66, 66);
		panelUsuario.add(lblNewLabel);

		JLabel asignadoA = new JLabel("Asignado a:");
		asignadoA.setFont(new Font("Tahoma", Font.PLAIN, 14));
		asignadoA.setBounds(21, 315, 105, 32);
		panelUsuario.add(asignadoA);

		asignadoAIn = new JTextField();
		asignadoAIn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		asignadoAIn.setBounds(159, 316, 234, 32);
		panelUsuario.add(asignadoAIn);

		setResizable(false);
		setSize(423, 502);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	// Funcion que valida si algun campo a registrar esta vacio
	private boolean validar1() {
		boolean val = true;
		val = ( fechaEntregaIn.getText().compareTo("") == 0|| cantidadIn.getText().compareTo("") == 0
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
						dispose();
					} else
						JOptionPane.showMessageDialog(null, validar2());
				} else
					JOptionPane.showMessageDialog(null, "Digite todos los campos");
			}
		}
	}
}
