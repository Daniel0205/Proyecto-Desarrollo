import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class GUICotizacionVenta extends JFrame {

	// Variables y componentes de la GUI
	private final BaseDeDatos bd;
	private Container contenedor;
	private final String id, nombre_cliente, fnt, id_cotizacion, fecha_f, nombre, sede;
	private JLabel fecha1, fecha2, coti1, coti2, vendedor1, vendedor2, total1, total2, cliente1, cliente2;
	private JLabel fondoAzul, fondoGris, icono, titulo;
	private DefaultTableModel modelo;
	private JTable productos;
	private JButton anadirProducto, finalizarCot, registarCot, registarVen;
	private JScrollPane scroll;
	private String[] datos;
	private int pX, pY;

	// Constructor
	public GUICotizacionVenta(BaseDeDatos bd, String id, String nombre_cliente, String fnt) {
		getContentPane().setBackground(Color.BLACK);

		// Inicializacion de base de datos y variables
		this.bd = bd;
		this.id = id;
		this.nombre_cliente = nombre_cliente;
		this.fnt = fnt;

		id_cotizacion = bd.insertarCot(id, nombre_cliente, fnt);
		fecha_f = bd.getFechacot(id_cotizacion);
		sede = bd.obtenerS(id, "sede");

		nombre = bd.obtenerS(id, "nombres") + " " + bd.obtenerS(id, "apellidos");
		creaTabla();

		initGUI();
	}

	// Inicializa los componentes de la GUI
	private void initGUI() {

		// Configuraciones de la ventana principal
		getContentPane().setForeground(Color.BLACK);
		getContentPane().setLayout(null);
		this.setUndecorated(true);
		ManejadorDeBotones listener = new ManejadorDeBotones();
		Font font = new Font("Tahoma", Font.PLAIN, 14);
		manejadorDesplazamientoVentana(this);

		fecha1 = new JLabel("Fecha: ");
		fecha1.setFont(font);
		fecha1.setBounds(32, 97, 55, 32);
		getContentPane().add(fecha1);

		fecha2 = new JLabel(fecha_f);
		fecha2.setFont(font);
		fecha2.setBounds(92, 97, 220, 32);
		getContentPane().add(fecha2);

		coti1 = new JLabel("Cotizacion No: ");
		coti1.setFont(font);
		coti1.setBounds(32, 129, 110, 32);
		getContentPane().add(coti1);

		coti2 = new JLabel(id_cotizacion);
		coti2.setFont(font);
		coti2.setBounds(137, 129, 100, 32);
		getContentPane().add(coti2);

		vendedor1 = new JLabel("Vendedor: ");
		vendedor1.setFont(font);
		vendedor1.setBounds(310, 97, 100, 32);
		getContentPane().add(vendedor1);

		vendedor2 = new JLabel(nombre);
		vendedor2.setFont(font);
		vendedor2.setBounds(385, 97, 100, 32);
		getContentPane().add(vendedor2);

		cliente1 = new JLabel("Cliente: ");
		cliente1.setFont(font);
		cliente1.setBounds(310, 129, 100, 32);
		getContentPane().add(cliente1);

		cliente2 = new JLabel(nombre_cliente);
		cliente2.setFont(font);
		cliente2.setBounds(365, 129, 200, 32);
		getContentPane().add(cliente2);

		productos = new JTable(modelo);
		productos.setShowHorizontalLines(false);
		productos.setRowSelectionAllowed(true);
		productos.setColumnSelectionAllowed(true);

		scroll = new JScrollPane(productos);
		scroll.setBounds(21, 169, 630, 211);
		getContentPane().add(scroll);

		total1 = new JLabel("Total: ");
		total1.setFont(font);
		total1.setBounds(530, 386, 45, 32);
		getContentPane().add(total1);

		total2 = new JLabel("0");
		total2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		total2.setForeground(Color.red);
		total2.setBounds(572, 386, 100, 32);
		getContentPane().add(total2);

		anadirProducto = new JButton("Anadir producto");
		anadirProducto.setOpaque(true);
		anadirProducto.setBackground(new Color(227, 227, 227));
		anadirProducto.setFont(font);
		anadirProducto.setBounds(21, 420, 145, 32);
		anadirProducto.addActionListener(listener);
		getContentPane().add(anadirProducto);

		if (fnt == "C") {

			registarCot = new JButton("Hacer Cotizacion");
			registarCot.setOpaque(true);
			registarCot.setBackground(new Color(227, 227, 227));
			registarCot.setFont(font);
			registarCot.setBounds(272, 420, 145, 32);
			registarCot.addActionListener(listener);
			getContentPane().add(registarCot);
			titulo = new JLabel("COTIZACION");

		} else if (fnt == "V") {

			registarVen = new JButton("Hacer Venta");
			registarVen.setOpaque(true);
			registarVen.setBackground(new Color(227, 227, 227));
			registarVen.setFont(font);
			registarVen.setBounds(272, 420, 145, 32);
			registarVen.addActionListener(listener);
			getContentPane().add(registarVen);
			titulo = new JLabel("VENTA");

		}

		finalizarCot = new JButton("Cancelar");
		finalizarCot.setOpaque(true);
		finalizarCot.setBackground(new Color(227, 227, 227));
		finalizarCot.setFont(font);
		finalizarCot.setBounds(562, 420, 89, 32);
		finalizarCot.addActionListener(listener);
		getContentPane().add(finalizarCot);

		// Icono a la izquierda del titulo
		icono = new JLabel("");
		icono.setIcon(new ImageIcon(GUIConsultarUser.class.getResource("/images/titulo_flecha.png")));
		icono.setBounds(11, 1, 48, 90);
		getContentPane().add(icono);

		// Etiqueta del titulo de la ventana
		titulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		titulo.setForeground(Color.WHITE);
		titulo.setBounds(69, 28, 175, 32);
		getContentPane().add(titulo);

		// -- Fondos azul y gris -- //
		fondoAzul = new JLabel("");
		fondoAzul.setBounds(1, 1, 670, 90);
		fondoAzul.setOpaque(true);
		fondoAzul.setBackground(new Color(45, 118, 232));
		getContentPane().add(fondoAzul);
		fondoGris = new JLabel("");
		fondoGris.setBounds(1, 89, 670, 390);
		fondoGris.setOpaque(true);
		fondoGris.setBackground(new Color(227, 227, 227));
		getContentPane().add(fondoGris);

		// Configuraciones adicionales de la ventana
		setSize(672, 480);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	// Crea una tabla con los campos que se mostraran de cada producto en la
	// cotizacion
	private void creaTabla() {
		modelo = new DefaultTableModel();

		modelo.addColumn("Referencia");
		modelo.addColumn("Producto");
		modelo.addColumn("Cantidad");
		modelo.addColumn("Precio Unitartio");
		modelo.addColumn("Precio");
	}

	// Agrega un producto a la cotizacion y lo muestra en la tabla que lista los
	// prodcutos
	private void agregarProducto(String id_pro, String cant) {

		String precio_U = bd.consultarProducto(id_pro, "precio", "Id");
		datos = new String[] { id_pro, bd.consultarProducto(id_pro, "nombre", "Id"), cant, precio_U,
				String.valueOf(Integer.parseInt(precio_U) * Integer.parseInt(cant)) };

		modelo.addRow(datos);

		actualizarTotal();
	}

	// ACtualiza el precio total deacuerdo a los productos y cantidades que hay
	// en
	// la tabla
	private void actualizarTotal() {
		int filas = productos.getRowCount();
		int aux = 0;

		for (int i = 0; i < filas; i++) {
			aux = aux + Integer.parseInt((String) productos.getValueAt(i, 4));
		}
		total2.setText(String.valueOf(aux));
	}

	// Manejador de eventos
	private class ManejadorDeBotones implements ActionListener {

		public void actionPerformed(ActionEvent actionEvent) {
			if (actionEvent.getSource() == anadirProducto) {

				GUIProductocot newProduct = new GUIProductocot(bd, id_cotizacion);
				newProduct.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

			} else if (actionEvent.getSource() == finalizarCot) {

				if (bd.cancelarCoti(id_cotizacion)) {
					JOptionPane.showMessageDialog(null, "Factura cancelada");
					dispose();
				}

			} else if (actionEvent.getSource() == registarCot) {

				if (bd.actualizarCoti(id_cotizacion, total2.getText())) {
					JOptionPane.showMessageDialog(null, "Factura cotizada");
					dispose();
				}

			} else if (actionEvent.getSource() == registarVen) {

				if (bd.actualizarCoti(id_cotizacion, total2.getText())) {
					JOptionPane.showMessageDialog(null, "Factura vendida");
					dispose();
				}

			}
		}
	}

	// Clase privada para el manejo de agregar un nuevo producto
	private class GUIProductocot extends JFrame {

		// Variables y componentes de la GUI
		private final BaseDeDatos bd;
		private final String id_cot;
		private Container contenedor;
		private JButton agregar, cancelar;
		private JLabel producto1, cantidad1;
		private JTextField cantidad2;
		private JComboBox producto2;

		// Constructor
		public GUIProductocot(BaseDeDatos bd, String id_cot) {
			super("Nuevo producto");
			this.bd = bd;
			this.id_cot = id_cot;

			initGUI2();
		}

		// Vuelve la GUI al estado inicial
		private void limpiarGUI() {
			cantidad2.setText("");
			producto2.setSelectedItem(0);
		}

		// Inicializa la GUI
		private void initGUI2() {

			contenedor = getContentPane();
			contenedor.removeAll();
			getContentPane().setLayout(null);

			ManejadorDeBotones2 listener = new ManejadorDeBotones2();
			Font font = new Font("Tahoma", Font.PLAIN, 14);

			producto1 = new JLabel("Producto");
			producto1.setFont(font);
			producto1.setBounds(32, 32, 90, 32);
			getContentPane().add(producto1);

			cantidad1 = new JLabel("Cantidad");
			cantidad1.setFont(font);
			cantidad1.setBounds(32, 96, 90, 32);
			getContentPane().add(cantidad1);

			producto2 = new JComboBox<>(bd.cambiarDimension(bd.consultarProductos("id_producto, nombre")));
			producto2.setFont(font);
			producto2.setBounds(137, 32, 170, 32);
			getContentPane().add(producto2);

			cantidad2 = new JTextField("");
			cantidad2.setFont(font);
			cantidad2.setBounds(137, 96, 119, 32);
			getContentPane().add(cantidad2);

			agregar = new JButton("Agregar");
			agregar.setOpaque(true);
			agregar.setBackground(new Color(227, 227, 227));
			agregar.setFont(font);
			agregar.setBounds(32, 172, 119, 32);
			agregar.addActionListener(listener);
			getContentPane().add(agregar);

			cancelar = new JButton("Finalizar");
			cancelar.setOpaque(true);
			cancelar.setBackground(new Color(227, 227, 227));
			cancelar.setFont(font);
			cancelar.setBounds(171, 172, 119, 32);
			cancelar.addActionListener(listener);
			getContentPane().add(cancelar);

			setSize(330, 250);
			setResizable(false);
			setVisible(true);
			setLocationRelativeTo(null);
		}

		// Verifica que la cantidad ingresada sea numerica
		private boolean isNumeric(String cadena) {

			boolean resultado;

			try {
				Integer.parseInt(cadena);
				resultado = true;
			} catch (NumberFormatException excepcion) {
				resultado = false;
			}

			return resultado;
		}

		// Manejador de eventos de la GUI
		private class ManejadorDeBotones2 implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == agregar) {
					String cant;
					String id_p = producto2.getSelectedItem().toString();
					id_p = id_p.substring(0, id_p.indexOf("-"));
					cant = cantidad2.getText();

					if (isNumeric(cant) == true) {

						if (fnt == "V") {

							if (Integer.parseInt(cant) < bd.cantidadDisponible(id_p, sede)) {

								if (bd.agregarProCot(id_p, cant, id_cot)) {
									JOptionPane.showMessageDialog(null, "Producto agregado a la cotizacion.");
									agregarProducto(id_p, cant);

								} else {
									JOptionPane.showMessageDialog(null, "Error al añadir el producto");
								}
							} else {
								JOptionPane.showMessageDialog(null, "No hay esa cantidad disponible del producto");
							}

						} else if (fnt == "C") {

							if (bd.agregarProCot(id_p, cant, id_cot)) {
								JOptionPane.showMessageDialog(null, "Producto agregado a la cotizacion.");
								agregarProducto(id_p, cant);

							} else {
								JOptionPane.showMessageDialog(null, "Error al añadir el producto");
							}
						}

					} else {
						JOptionPane.showMessageDialog(null, "Inserte un valor numerico en la cantidad");
					}

					limpiarGUI();
				} else if (e.getSource() == cancelar) {
					dispose();
				}
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
