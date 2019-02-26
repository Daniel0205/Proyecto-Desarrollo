import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.event.ActionEvent;

/**
 * Esta clase permite mostrar una interfaz sencilla para la consulta
 * de sedes en la base de datos de la empresa.
 *
 *  El criterio de la consulta es 'Id'.
 *
 *  El resultado de la consulta se muestra en una tabla.
 */

@SuppressWarnings("serial")
public class GUIConsultarSede extends JFrame {

    private BaseDeDatos bd;
    private JLabel icon,idl;
    private JTextField busqueda;
    private JButton salir, consultar;
    private JSeparator separator_1, separator_2;
    private Font font;
    private ActionListener listener;


    //Creacion y despliegue de la interfaz de consulta
    public GUIConsultarSede(BaseDeDatos bdIn) {
        super("Consultar Sede");

        bd = bdIn;
        font = new Font("Tahoma", Font.PLAIN, 14);
        getContentPane().setLayout(null);
        listener = new ManejadorDeBotones();

        icon = new JLabel(" ");
        URL filePath = this.getClass().getResource("/images/search.png");
        icon.setIcon(new ImageIcon(filePath));
        icon.setBounds(31, 11, 66, 66);
        getContentPane().add(icon);

        idl = new JLabel("Id");
        idl.setFont(new Font("Tahoma", Font.PLAIN, 14));
        idl.setBounds(62, 112, 25, 32);
        getContentPane().add(idl);

        consultar = new JButton("Consultar");
        consultar.setFont(font);
        consultar.setBounds(400, 112, 101, 32);
        consultar.addActionListener(listener);
        getContentPane().add(consultar);

        busqueda = new JTextField();
        busqueda.setFont(font);
        busqueda.setBounds(95, 113, 295, 32);
        getContentPane().add(busqueda);
        busqueda.setColumns(10);


        separator_1 = new JSeparator();
        separator_1.setBounds(31, 100, 482, 4);
        getContentPane().add(separator_1);

        separator_2 = new JSeparator();
        separator_2.setBounds(21, 181, 492, 4);
        getContentPane().add(separator_2);

        salir = new JButton("Salir");
        salir.setFont(font);
        salir.setBounds(412, 192, 89, 32);
        salir.addActionListener(listener);
        getContentPane().add(salir);

        setSize(561,264);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
    }


    //Manejador de eventos sobre los botones del menu de consulta
    private class ManejadorDeBotones implements ActionListener{

        public void actionPerformed(ActionEvent actionEvent){
            if (actionEvent.getSource() == consultar){
                String campos = "id_sede, direccion, numero,empleado_a_cargo";
                String[][] resultado = bd.consultarSede((String)busqueda.getText(),campos);
                resultadosConsultaGUI(resultado);
            }
            else if (actionEvent.getSource() == salir)
                dispose();
        }
    }

    //Despliegue de la interfaz con el resultado de la consulta
    private void resultadosConsultaGUI(String[][] board){

        if(board==null || board.length==0)
            JOptionPane.showMessageDialog(null, "La busqueda no produjo resultados.");

        else{
            String idEncargado = board[0][3];
            String nombreE = bd.obtenerS(idEncargado, "nombres");
            String apellidoE = bd.obtenerS(idEncargado, "apellidos");
            board[0][3] = nombreE.trim()+" "+apellidoE.trim();
            JFrame frame = new JFrame();
            String column[]={"id", "Direccion" ,"Telefono", "Empleado a cargo"};
            JTable table = new JTable(board,column);
            table.setRowMargin(0);
            table.setFont(font);
            JScrollPane sp = new JScrollPane(table);
            frame.getContentPane().add(sp);
            frame.setSize(1200,400);
            frame.setVisible(true);
        }
    }
}
