import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class GUIMenuJefe extends JFrame {

    private final BaseDeDatos bd;
    private Container contenedor;
    private JSeparator separator_1,separator_2;
    private Font font;
    private JButton crearOrden,modificarOrden,consultarOrden,salir;
    private JLabel icon;
    private String idJefe;

    public GUIMenuJefe(BaseDeDatos bd,String idJefe){
        super("MENU JEFE DE TALLER");

        this.bd=bd;
        this.idJefe=idJefe;
        InitGUI();
    }

    private void InitGUI() {

        contenedor = getContentPane();
        contenedor.removeAll();
        getContentPane().setLayout(null);

        ManejadorDeBotones listener = new ManejadorDeBotones();

        font = new Font("Tahoma", Font.PLAIN, 14);

        crearOrden = new JButton("Crear Orden");
        crearOrden.setFont(font);
        crearOrden.setBounds(22, 101, 142, 32);
        crearOrden.addActionListener(listener);
        getContentPane().add(crearOrden);

        modificarOrden = new JButton("Modificar Orden");
        modificarOrden.setFont(font);
        modificarOrden.setBounds(22, 144, 142, 32);
        modificarOrden.addActionListener(listener);
        getContentPane().add(modificarOrden);

        consultarOrden = new JButton("Consultar Orden");
        consultarOrden.setFont(font);
        consultarOrden.setBounds(22, 187, 142, 32);
        consultarOrden.addActionListener(listener);
        getContentPane().add(consultarOrden);   

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
        	if (actionEvent.getSource() == crearOrden) {
        		GUICrearOrden crearOrden = new GUICrearOrden(bd,idJefe);
        	}
        	else if(actionEvent.getSource() == salir){
                System.exit(0);
			}
        	else if(actionEvent.getSource() == modificarOrden){
                GUIActualizarOrden actualizarOrden = new GUIActualizarOrden(bd,idJefe);
            }
            else if(actionEvent.getSource() == consultarOrden){
                GUIConsultarOrden consultarOrden = new GUIConsultarOrden(bd);
            }

        }
    }
}
