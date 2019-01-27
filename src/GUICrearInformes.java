import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class GUICrearInformes  extends JFrame {

    private final BaseDeDatos bd;
    private Container contenedor;
    private JButton infUsuario,infInventario,infSedes;
    private JButton infVentas,infCotizaciones,infOrdenes;
    private Font font;
    private JLabel icon;
    private JButton salir;
    private JSeparator separator_1,separator_2;

    public GUICrearInformes(BaseDeDatos bd){
        super("Informes");
        this.bd=bd;

        initGUI();
    }

    private void initGUI() {
        contenedor = getContentPane();
        contenedor.removeAll();
        getContentPane().setLayout(null);

        BotonListener listener = new BotonListener();

        font = new Font("Tahoma", Font.PLAIN, 14);

        infUsuario = new JButton("Usuarios");
        infUsuario.setFont(font);
        infUsuario.setBounds(22, 144, 142, 32);
        infUsuario.addActionListener(listener);
        getContentPane().add(infUsuario);

        infSedes = new JButton("Sedes");
        infSedes.setFont(font);
        infSedes.setBounds(22, 101, 142, 32);
        infSedes.addActionListener(listener);
        getContentPane().add(infSedes);

        infInventario = new JButton("Inventario");
        infInventario.setFont(font);
        infInventario.setBounds(22, 187, 142, 32);
        infInventario.addActionListener(listener);
        getContentPane().add(infInventario);

        infCotizaciones = new JButton("Cotizaciones");
        infCotizaciones.setFont(font);
        infCotizaciones.setBounds(187, 101, 142, 32);
        infCotizaciones.addActionListener(listener);
        getContentPane().add(infCotizaciones);

        infVentas = new JButton("Ventas");
        infVentas.setFont(font);
        infVentas.setBounds(187, 144, 142, 32);
        infVentas.addActionListener(listener);
        getContentPane().add(infVentas);

        infOrdenes = new JButton("Ordenes de trabajo");
        infOrdenes.setFont(font);
        infOrdenes.setBounds(187, 187, 142, 32);
        getContentPane().add(infOrdenes);

        salir = new JButton("Salir");
        salir.addActionListener(listener);
        salir.setFont(font);
        salir.setBounds(193, 293, 136, 32);
        getContentPane().add(salir);

        icon = new JLabel("");
        URL filePath = this.getClass().getResource("/images/home.png");
        icon.setIcon(new ImageIcon(filePath));
        icon.setBounds(31, 11, 66, 66);
        getContentPane().add(icon);

        separator_1 = new JSeparator();
        separator_1.setBounds(22, 88, 307, 2);
        getContentPane().add(separator_1);

        separator_2 = new JSeparator();
        separator_2.setBounds(22, 280, 307, 2);
        getContentPane().add(separator_2);

        setSize(359,363);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);

    }

    private class BotonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {


            if (actionEvent.getSource()==salir){
                System.exit(0);
            }
        }
    }
}
