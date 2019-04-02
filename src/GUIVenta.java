import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.ArrayList;

public class GUIVenta extends JFrame{

    private final BaseDeDatos bd;
    private final String id;
    private final String id_venta;
    private String nombre,sede;
    private Container contenedor;
    private JLabel fecha1,fecha2,coti1,coti2,vendedor1,vendedor2,total1,total2,cliente1,cliente2;
    private DefaultTableModel modelo;
    private JTable productos;
    private JButton anadirProducto,finalizarCot;
    private JScrollPane scroll;
    private String[] datos;
    private Date fecha ;
    private final String fechaF,nombre_cliente;

    public GUIVenta(BaseDeDatos bd,String id){
        super("Venta");
        this.bd = bd;
        this.id = id;
        fecha = new Date();
        final int dia = 24;
        final int mes = 11;
        final int ano = 2018;
        fechaF = ""+ano+"-"+mes+"-"+dia+"";

        nombre_cliente = JOptionPane.showInputDialog("Ingrese el nombre del cliente: ");

        id_venta = bd.obtenerIdVenta();

        nombre = bd.obtenerS(id,"nombres")+" "+bd.obtenerS(id,"apellidos");
        creaTabla();

        sede = bd.obtenerS(id,"sede");

        initGUI();
    }

    private void creaTabla(){
        modelo = new DefaultTableModel();

        modelo.addColumn("Referencia");
        modelo.addColumn("Producto");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio Unitartio");
        modelo.addColumn("Precio");
    }
    private void initGUI() {

        contenedor = getContentPane();
        contenedor.removeAll();
        getContentPane().setLayout(null);

        ManejadorDeBotones listener = new ManejadorDeBotones();
        Font font = new Font("Tahoma", Font.PLAIN, 14);

        fecha1 = new JLabel("Fecha: ");
        fecha1.setFont(font);
        fecha1.setBounds(32, 32, 45, 32);
        getContentPane().add(fecha1);

        fecha2 = new JLabel(fechaF);
        fecha2.setFont(font);
        fecha2.setBounds(82, 32, 220, 32);
        getContentPane().add(fecha2);

        coti1 = new JLabel("Venta No: ");
        coti1.setFont(font);
        coti1.setBounds(32, 64, 100, 32);
        getContentPane().add(coti1);

        coti2 = new JLabel(id_venta);
        coti2.setFont(font);
        coti2.setBounds(127, 64, 100, 32);
        getContentPane().add(coti2);

        vendedor1 = new JLabel("Vendedor: ");
        vendedor1.setFont(font);
        vendedor1.setBounds(310, 32, 100, 32);
        getContentPane().add(vendedor1);

        vendedor2 = new JLabel(nombre);
        vendedor2.setFont(font);
        vendedor2.setBounds(380, 32, 100, 32);
        getContentPane().add(vendedor2);

        cliente1 = new JLabel("Cliente: ");
        cliente1.setFont(font);
        cliente1.setBounds(310, 64, 100, 32);
        getContentPane().add(cliente1);

        cliente2 = new JLabel(nombre_cliente);
        cliente2.setFont(font);
        cliente2.setBounds(360, 64, 200, 32);
        getContentPane().add(cliente2);

        productos = new JTable(modelo);
        productos.setShowHorizontalLines( false );
        productos.setRowSelectionAllowed( true );
        productos.setColumnSelectionAllowed( true );

        scroll = new JScrollPane(productos);
        scroll.setBounds(21,104,630,211);
        getContentPane().add(scroll);

        total1 = new JLabel("Total: ");
        total1.setFont(font);
        total1.setBounds(530, 321, 45, 32);
        getContentPane().add(total1);

        total2 = new JLabel("0");
        total2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        total2.setForeground(Color.red);
        total2.setBounds(572 , 321, 100, 32);
        getContentPane().add(total2);

        anadirProducto = new JButton("Anadir producto");
        anadirProducto.setFont(font);
        anadirProducto.setBounds(21, 355, 145, 32);
        anadirProducto.addActionListener(listener);
        getContentPane().add(anadirProducto);

        finalizarCot = new JButton("Finalizar");
        finalizarCot.setFont(font);
        finalizarCot.setBounds(562, 355, 89, 32);
        finalizarCot.addActionListener(listener);
        getContentPane().add(finalizarCot);

        setSize(672,433);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void agregarProducto(String id_pro,String cant){

        String precio_U = bd.consultarProducto(id_pro,"precio");
        datos = new String[]{id_pro, bd.consultarProducto(id_pro, "nombre"), cant, precio_U, String.valueOf(Integer.parseInt(precio_U) * Integer.parseInt(cant))};

        modelo.addRow(datos);

        actualizarTotal();
    }

    private void actualizarTotal(){
        int filas = productos.getRowCount();
        int aux = 0;

        for(int i = 0; i<filas;i++){
            aux = aux + Integer.parseInt((String) productos.getValueAt(i,4));
        }

        total2.setText(String.valueOf(aux));
        // total2.repaint();
    }

    private String realizarVenta(){
        int filas = productos.getRowCount();
        String msj="";
        for(int i = 0;i<filas;i++){
            if(bd.verificarVenta(sede,(String)(productos.getValueAt(i,0)),Integer.parseInt((String) productos.getValueAt(i,2)))){
               System.out.println("");
            }
            else{
                msj=msj+"No hay suficientes unidades para el producto de id:"+(String)(productos.getValueAt(i,0))+"\n";
            }

        }
        if(msj.compareTo("")==0){
            for(int i = 0;i<filas;i++){
                bd.insertarVenta((String)(productos.getValueAt(i,0)),sede,Integer.parseInt((String) productos.getValueAt(i,2)),id_venta);
            }
            bd.insertarVentaInfo(id,fechaF,nombre_cliente,total2.getText());

            return "Su compra fue exitosa!";
        }else{
            return msj;
        }



    }

    private class ManejadorDeBotones implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent){
            if(actionEvent.getSource()==anadirProducto){
                GUIProductocot newProduct= new GUIProductocot(bd,id_venta);
                newProduct.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }else if(actionEvent.getSource()==finalizarCot){
                String msj=realizarVenta();
                JOptionPane.showMessageDialog(null, msj);
                dispose();
            }
        }
    }


    //Clase privada para el manejo de añadir un nuevo producto
    private class  GUIProductocot extends JFrame{
        private final BaseDeDatos bd;
        private final String id_cot;
        private Container contenedor;
        private JButton agregar,cancelar;
        private JLabel producto1,cantidad1;
        private JTextField cantidad2;
        private JComboBox producto2;

        public GUIProductocot(BaseDeDatos bd,String id_cot){
            super("Nuevo producto");
            this.bd = bd;
            this.id_cot = id_cot;

            initGUI2();
        }

        private void limpiarGUI(){
            cantidad2.setText("");
            producto2.setSelectedItem(0);
        }

        private void initGUI2(){

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
            agregar.setFont(font);
            agregar.setBounds(32, 172, 119, 32);
            agregar.addActionListener(listener);
            getContentPane().add(agregar);

            cancelar = new JButton("Cancelar");
            cancelar.setFont(font);
            cancelar.setBounds(171, 172, 119, 32);
            cancelar.addActionListener(listener);
            getContentPane().add(cancelar);

            setSize(330,250);
            setResizable(false);
            setVisible(true);
            setLocationRelativeTo(null);
        }

        private class ManejadorDeBotones2 implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==agregar){
                    String cant;
                    String id_p = producto2.getSelectedItem().toString();
                    id_p = id_p.substring(0, id_p.indexOf("-"));
                    cant = cantidad2.getText();
                    JOptionPane.showMessageDialog(null, "Producto agregado a la venta.");
                    agregarProducto(id_p,cant);
                    limpiarGUI();
                }else if(e.getSource()==cancelar){
                    dispose();
                }
            }
        }
    }
}
