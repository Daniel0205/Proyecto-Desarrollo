import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class GUIConsultarProductos extends JFrame {

	// Variables y componentes de la GUI
	private final BaseDeDatos bd;
	private final String id;
	private DefaultTableModel modelo;
	private Container contenedor;
	private JComboBox<String> criterio;
	private JTextField busqueda;
	private JButton consultar, salir;
	private JTable tabla;
	private JScrollPane scroll;
	private String[] datos;
	private int pX, pY;
	private JLabel fondoAzul, fondoGris, titulo, icono;

	// Constructor
	public GUIConsultarProductos(BaseDeDatos bd, String id) {
		super("Productos");
		getContentPane().setBackground(Color.BLACK);
		this.bd = bd;
		this.id = id;
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
		manejadorDesplazamientoVentana(this);
		Font font = new Font("Tahoma", Font.PLAIN, 14);

		criterio = new JComboBox<>();
		criterio.setFont(font);
		String[] lista = new String[] { "Id", "Nombre" };
		criterio.setModel(new DefaultComboBoxModel<String>(lista));
		criterio.setBounds(32, 118, 90, 32);
		getContentPane().add(criterio);

		busqueda = new JTextField();
		busqueda.setFont(font);
		busqueda.setBounds(127, 118, 290, 32);
		getContentPane().add(busqueda);
		busqueda.setColumns(10);

		consultar = new JButton("Consultar");
		consultar.setOpaque(true);
		consultar.setBackground(new Color(227, 227, 227));
		consultar.setFont(font);
		consultar.setBounds(422, 118, 100, 32);
		consultar.addActionListener(listener);
		getContentPane().add(consultar);

		tabla = new JTable(modelo);
		tabla.setShowHorizontalLines(false);
		tabla.setRowSelectionAllowed(true);
		tabla.setColumnSelectionAllowed(true);

		scroll = new JScrollPane(tabla);
		scroll.setBounds(32, 182, 490, 211);
		getContentPane().add(scroll);

		salir = new JButton("Salir");
		salir.setOpaque(true);
		salir.setBackground(new Color(227, 227, 227));
		salir.setFont(font);
		salir.setBounds(442, 417, 80, 32);
		salir.addActionListener(listener);
		getContentPane().add(salir);

		// Icono a la izquierda del titulo
		icono = new JLabel("");
		icono.setIcon(new ImageIcon(GUIConsultarUser.class.getResource("/images/buscar.png")));
		icono.setBounds(11, 1, 48, 90);
		getContentPane().add(icono);

		// Etiqueta del titulo de la ventana
		titulo = new JLabel("CONSULTAR PRODUCTOS");
		titulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		titulo.setForeground(Color.WHITE);
		titulo.setBounds(69, 28, 262, 32);
		getContentPane().add(titulo);

		// -- Fondos azul y gris -- //
		fondoAzul = new JLabel("");
		fondoAzul.setBounds(1, 1, 560, 90);
		fondoAzul.setOpaque(true);
		fondoAzul.setBackground(new Color(45, 118, 232));
		getContentPane().add(fondoAzul);
		fondoGris = new JLabel("");
		fondoGris.setBounds(1, 89, 560, 382);
		fondoGris.setOpaque(true);
		fondoGris.setBackground(new Color(227, 227, 227));// Gris
		getContentPane().add(fondoGris);

		setSize(562, 472);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	// Define unos campos para mostrar los campos de los productos
	private void creaTabla() {

		modelo = new DefaultTableModel();
		modelo.addColumn("Referencia");
		modelo.addColumn("Producto");
		modelo.addColumn("Descripcion");
		modelo.addColumn("Precio");
		modelo.addColumn("Cantidad");
	}

	// Muestra los datos de un producto que ha sido buscado
	private void mostrar() {

		String sede = bd.consultarDatoUsuario("Id", id, "sede");

		String[][] result = bd.listarProductos((String) criterio.getSelectedItem(), (String) busqueda.getText(),
				"id_producto,nombre,descripcion,precio,cantidad_disponible", sede);

		if (result == null || result.length == 0) {
			JOptionPane.showMessageDialog(null, "La busqueda no produjo resultados.");
		} else {
			datos = new String[] { result[0][0], result[0][1], result[0][2], result[0][3], result[0][4] };
			modelo.setRowCount(0);
			modelo.addRow(datos);
		}
	}

	// Manejador de eventos
	private class ManejadorDeBotones implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == consultar) {
				mostrar();
			} else if (e.getSource() == salir) {
				dispose();
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
