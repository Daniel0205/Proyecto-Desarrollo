import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.event.ActionEvent;

public class GUIConsultarOrden extends JFrame{
    private BaseDeDatos bd;
    private JLabel icon,idl;
    private JComboBox busqueda;
    private JButton salir, consultar;
    private JSeparator separator_1, separator_2;
    private Font font;
    private ActionListener listener;
    private String idJefe;

public GUIConsultarOrden(BaseDeDatos bdIn,String idJefe){
    super("Consultar Orden");
    bd = bdIn;
    this.idJefe=idJefe;
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

    busqueda = new JComboBox<>(bd.cambiarDimension(
            bd.consultarOrden("User",idJefe,"id_ordenes,nombre")));;
    busqueda.setFont(font);
    busqueda.setBounds(95, 113, 295, 32);
    getContentPane().add(busqueda);
    //busqueda.setColumns(10);


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

    private class ManejadorDeBotones implements ActionListener{

        public void actionPerformed(ActionEvent actionEvent){
            if (actionEvent.getSource() == consultar){
                String idOrden = busqueda.getSelectedItem().toString();
                idOrden=idOrden.substring(0,idOrden.indexOf("-"));

                String campos = "id_ordenes, asignada_a, precio,fecha_entrega,cantidad,finalizada,id_producto,id_usuario";
                String[][] resultado = bd.consultarOrden(idOrden,campos);
                resultadosConsultaGUI(resultado);
            }
            else if (actionEvent.getSource() == salir)
                dispose();
        }
    }
    private void resultadosConsultaGUI(String[][] board){

        if(board==null || board.length==0)
            JOptionPane.showMessageDialog(null, "La busqueda no produjo resultados.");

        else{
            JFrame frame = new JFrame();
            String column[]={"id", "Asignada a" ,"Precio","Fecha de entrega", "Cantidad", "Finalizada", "id Producto", "id Usuario"};
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
