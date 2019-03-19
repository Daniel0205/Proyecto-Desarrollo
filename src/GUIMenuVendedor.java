import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class GUIMenuVendedor extends JFrame {

    private final BaseDeDatos bd;
    private final String id;
    private Container contenedor;
    private JSeparator separator_1,separator_2;
    private Font font;
    private JButton consultaProducto,venta,cotizacion,salir;
    private JLabel icon;
   
    public GUIMenuVendedor(BaseDeDatos bd,String id) {
        super("MENU VENDEDOR");

        this.id = id;
        this.bd = bd;
        initGUI();
    }

    private void initGUI() {

        contenedor = getContentPane();
        contenedor.removeAll();
        getContentPane().setLayout(null);

        ManejadorDeBotones listener = new ManejadorDeBotones();

        font = new Font("Tahoma", Font.PLAIN, 14);

        consultaProducto = new JButton("Productos");
        consultaProducto.setFont(font);
        consultaProducto.setBounds(22, 101, 162, 32);
        consultaProducto.addActionListener(listener);
        getContentPane().add(consultaProducto);

        venta = new JButton("Realizar venta");
        venta.setFont(font);
        venta.setBounds(22, 144, 162, 32);
        venta.addActionListener(listener);
        getContentPane().add(venta);

        cotizacion = new JButton("Realizar cotizacion");
        cotizacion.setFont(font);
        cotizacion.setBounds(22, 187, 162, 32);
        cotizacion.addActionListener(listener);
        getContentPane().add(cotizacion);

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

    private class ManejadorDeBotones implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(actionEvent.getSource() == cotizacion){
                GUICotizacion cot = new GUICotizacion(bd,id);
                cot.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            } else if(actionEvent.getSource() == salir){
                dispose();
            }
        }
    }
}
