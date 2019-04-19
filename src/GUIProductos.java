import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIProductos extends JFrame {
    private final BaseDeDatos bd;
    private final String id;
    private DefaultTableModel modelo;
    private Container contenedor;
    private JComboBox<String> criterio;
    private JTextField busqueda;
    private JButton consultar,salir;
    private JTable tabla;
    private JScrollPane scroll;


    public GUIProductos(BaseDeDatos bd,String id) {
        super("Productos");
        this.bd = bd;
        this.id = id;

        initGUI();
    }

    private void creaTabla(){
        modelo = new DefaultTableModel();

        modelo.addColumn("Referencia");
        modelo.addColumn("Producto");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Precio");
        modelo.addColumn("Cantidad");
    }

    private void initGUI(){
        contenedor = getContentPane();
        contenedor.removeAll();
        getContentPane().setLayout(null);

        ManejadorDeBotones listener = new ManejadorDeBotones();
        Font font = new Font("Tahoma", Font.PLAIN, 14);

        criterio = new JComboBox<>();
        criterio.setFont(font);
        String[] lista = new String[] {"Id", "Nombre"};
        criterio.setModel(new DefaultComboBoxModel<String>(lista));
        criterio.setBounds(32, 32, 90, 32);
        getContentPane().add(criterio);

        busqueda = new JTextField();
        busqueda.setFont(font);
        busqueda.setBounds(137, 32, 290, 32);
        getContentPane().add(busqueda);
        busqueda.setColumns(10);

        consultar = new JButton("Consultar");
        consultar.setFont(font);
        consultar.setBounds(442, 32, 80, 32);
        consultar.addActionListener(listener);
        getContentPane().add(consultar);

        tabla = new JTable(modelo);
        tabla.setShowHorizontalLines( false );
        tabla.setRowSelectionAllowed( true );
        tabla.setColumnSelectionAllowed( true );

        scroll = new JScrollPane(tabla);
        scroll.setBounds(32,96,490,211);
        getContentPane().add(scroll);

        salir = new JButton("Salir");
        salir.setFont(font);
        salir.setBounds(442, 331, 80, 32);
        salir.addActionListener(listener);
        getContentPane().add(salir);

        setSize(562,412);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private class ManejadorDeBotones implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}