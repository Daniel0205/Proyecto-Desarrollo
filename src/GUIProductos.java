import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@SuppressWarnings("serial")
public class GUIProductos extends JFrame {

	//Variables y componentes de la GUI
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

	//Constructor
	public GUIProductos(BaseDeDatos bd, String id) {
		super("Productos");
		this.bd = bd;
		this.id = id;
		creaTabla();
		initGUI();
	}

	//Inicializa los componentes de la GUI
	private void initGUI() {
		contenedor = getContentPane();
		contenedor.removeAll();
		getContentPane().setLayout(null);

		ManejadorDeBotones listener = new ManejadorDeBotones();
		Font font = new Font("Tahoma", Font.PLAIN, 14);

		criterio = new JComboBox<>();
		criterio.setFont(font);
		String[] lista = new String[] { "Id", "Nombre" };
		criterio.setModel(new DefaultComboBoxModel<String>(lista));
		criterio.setBounds(32, 32, 90, 32);
		getContentPane().add(criterio);

		busqueda = new JTextField();
		busqueda.setFont(font);
		busqueda.setBounds(127, 32, 290, 32);
		getContentPane().add(busqueda);
		busqueda.setColumns(10);

		consultar = new JButton("Consultar");
		consultar.setOpaque(true);
		consultar.setBackground(new Color(227, 227, 227));
		consultar.setFont(font);
		consultar.setBounds(422, 32, 100, 32);
		consultar.addActionListener(listener);
		getContentPane().add(consultar);

		tabla = new JTable(modelo);
		tabla.setShowHorizontalLines(false);
		tabla.setRowSelectionAllowed(true);
		tabla.setColumnSelectionAllowed(true);

		scroll = new JScrollPane(tabla);
		scroll.setBounds(32, 96, 490, 211);
		getContentPane().add(scroll);

		salir = new JButton("Salir");
		salir.setOpaque(true);
		salir.setBackground(new Color(227, 227, 227));
		salir.setFont(font);
		salir.setBounds(442, 331, 80, 32);
		salir.addActionListener(listener);
		getContentPane().add(salir);

		setSize(562, 412);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
	}


    //Define unos campos para mostrar los campos de los productos
    private void creaTabla() {

        modelo = new DefaultTableModel();
        modelo.addColumn("Referencia");
        modelo.addColumn("Producto");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Precio");
        modelo.addColumn("Cantidad");
    }

    //Muestra los datos de un producto que ha sido buscado
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

	//Manejador de eventos
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
}
