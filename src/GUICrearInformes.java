import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class GUICrearInformes  extends JFrame {

    //Variables y componentes de la GUI
    private final BaseDeDatos bd;
    private Container contenedor;
    private JComboBox<String> informe, periodo, sedes;
    private Font font;
    private JButton salir, consultar;
    private JLabel inf,per,sed;
    private DefaultTableModel modelo;
    private JTable tabla;
    private JScrollPane scroll;
    private ChartPanel panel;

    public GUICrearInformes(BaseDeDatos bd){
        super("Informes");
        this.bd=bd;
        modelo = new DefaultTableModel();

        initGUI();
    }

    private void initGUI() {



        contenedor = getContentPane();
        contenedor.removeAll();
        getContentPane().setLayout(null);

        BotonListener listener = new BotonListener();

        font = new Font("Tahoma", Font.PLAIN, 14);

        inf = new JLabel("Seleccione el informe que desea generar:");
        inf.setForeground(SystemColor.textHighlight);
        inf.setFont(font);
        inf.setBounds(25, 25, 250, 20);
        getContentPane().add(inf);

        String[] tipInfo = new String[]{"Productos populares","Ganancias","Inventario","Ventas","Cotizaciones","Ordenes de trabajo"};
        informe= new JComboBox<>(tipInfo);
        informe.addActionListener(listener);
        informe.setBounds(25,50,200,20);
        getContentPane().add(informe);

        per = new JLabel("Seleccione un periodo de tiempo:");
        per.setForeground(SystemColor.textHighlight);
        per.setFont(font);
        per.setBounds(25, 75, 250, 20);
        getContentPane().add(per);

        String[] tipPer = new String[]{"Mensual","Semestral","Anual"};
        periodo= new JComboBox<>(tipPer);
        periodo.setBounds(25,100,200,20);
        periodo.setEditable(false);
        getContentPane().add(periodo);

        sed = new JLabel("Seleccione una sede en particular:");
        sed.setForeground(SystemColor.textHighlight);
        sed.setFont(font);
        sed.setBounds(300, 25, 250, 20);
        getContentPane().add(sed);

        sedes= new JComboBox<>(bd.cambiarDimension(bd.consultarSede(null,"id_Sede,nombre")));
        sedes.addItem("0-Todas");
        sedes.setSelectedItem("0-Todas");
        sedes.setBounds(300,50,200,20);
        sedes.setEditable(false);
        getContentPane().add(sedes);


        consultar = new JButton("Consultar");
        consultar.addActionListener(listener);
        consultar.setFont(font);
        consultar.setBounds(25,615, 120, 28);
        getContentPane().add(consultar);

        salir = new JButton("Salir");
        salir.addActionListener(listener);
        salir.setFont(font);
        salir.setBounds(573, 615, 81, 28);
        getContentPane().add(salir);


        setSize(679,688);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void graficar() {

        DefaultPieDataset data = new DefaultPieDataset();
        DefaultCategoryDataset datainv = new DefaultCategoryDataset();

        if(informe.getSelectedItem()=="Productos populares"){

            String idSede = sedes.getSelectedItem().toString();
            idSede=idSede.substring(0,idSede.indexOf("-"));
            int sed = Integer.parseInt(idSede);

            String[][] rep =  bd.informeProductos(periodo.getSelectedItem().toString(),sed);

            for(int i = 0; i < rep.length; i++){
                    data.setValue(rep[i][0], (Integer.parseInt(rep[i][1])));
            }

            if(panel!=null)getContentPane().remove(panel);
            JFreeChart chart = ChartFactory.createPieChart("Productos populares",data,true,true,false);
            panel = new ChartPanel(chart);
            panel.setBounds(25, 155, 629,314);
            panel.setVisible(true);
            getContentPane().remove(panel);
            getContentPane().add(panel);

            String[] columns = new String[]{"Nombre del producto","Cantidad vendida o cotizada"};

            llenarTabla(columns,rep);

        }else if(informe.getSelectedItem()=="Ganancias"){


        }else if(informe.getSelectedItem()=="Inventario"){

            String idSede = sedes.getSelectedItem().toString();
            idSede=idSede.substring(0,idSede.indexOf("-"));
            int sed = Integer.parseInt(idSede);

            String[][] rep =  bd.informeInventario(sed);

            for(int i=0;i<rep.length;i++){
                datainv.addValue(Integer.parseInt(rep[i][1]),rep[i][2],rep[i][0]);
            }

            if(panel!=null)getContentPane().remove(panel);
            JFreeChart chart = ChartFactory.createBarChart("Inventario de productos","Productos","Disponibilidad",datainv, PlotOrientation.VERTICAL,true,true,false);
            panel = new ChartPanel(chart);
            panel.setBounds(25, 155, 629,314);
            panel.setVisible(true);
            getContentPane().remove(panel);
            getContentPane().add(panel);

            String[] columns = new String[]{"Nombre del producto","Cantidad disponible","Sede correspondiente"};

            llenarTabla(columns,rep);


        }else if(informe.getSelectedItem()=="Ventas"){

            String idSede = sedes.getSelectedItem().toString();
            idSede=idSede.substring(0,idSede.indexOf("-"));
            int sed = Integer.parseInt(idSede);

            String[][] rep =  bd.informeVentas(periodo.getSelectedItem().toString(),sed);

            for(int i=0;i<rep.length;i++){
                datainv.addValue(Integer.parseInt(rep[i][1]),rep[i][2],rep[i][0]);
            }

            if(panel!=null)getContentPane().remove(panel);
            JFreeChart chart = ChartFactory.createBarChart("Ventas","Productos","Cantidad vendida",datainv, PlotOrientation.VERTICAL,true,true,false);
            panel = new ChartPanel(chart);
            panel.setBounds(25, 155, 629,314);
            panel.setVisible(true);
            getContentPane().remove(panel);
            getContentPane().add(panel);

            String[] columns = new String[]{"Nombre del producto","Cantidad vendida","Sede correspondiente"};

            llenarTabla(columns,rep);

        }else if(informe.getSelectedItem()=="Cotizaciones"){

            String idSede = sedes.getSelectedItem().toString();
            idSede=idSede.substring(0,idSede.indexOf("-"));
            int sed = Integer.parseInt(idSede);

            String[][] rep =  bd.informeCotizaciones(periodo.getSelectedItem().toString(),sed);

            for(int i=0;i<rep.length;i++){
                datainv.addValue(Integer.parseInt(rep[i][1]),rep[i][2],rep[i][0]);
            }

            if(panel!=null)getContentPane().remove(panel);
            JFreeChart chart = ChartFactory.createBarChart("Cotizaciones","Productos","Cantidad vendida",datainv, PlotOrientation.VERTICAL,true,true,false);
            panel = new ChartPanel(chart);
            panel.setBounds(25, 155, 629,314);
            panel.setVisible(true);
            getContentPane().remove(panel);
            getContentPane().add(panel);

            String[] columns = new String[]{"Nombre del producto","Cantidad cotizada","Sede correspondiente"};

            llenarTabla(columns,rep);

        }else if(informe.getSelectedItem()=="Ordenes de trabajo"){
            String idSede = sedes.getSelectedItem().toString();
            idSede=idSede.substring(0,idSede.indexOf("-"));
            int sed = Integer.parseInt(idSede);

            String[][] rep =  bd.informeOrden(sed);

            for(int i=0;i<rep.length;i++){
                datainv.addValue(Integer.parseInt(rep[i][1]),rep[i][2],rep[i][0]);
            }

            if(panel!=null)getContentPane().remove(panel);
            JFreeChart chart = ChartFactory.createBarChart("Productos realizados por ordenes de trabajo","Productos","Cantidad",datainv, PlotOrientation.VERTICAL,true,true,false);
            panel = new ChartPanel(chart);
            panel.setBounds(25, 155, 629,314);
            panel.setVisible(true);
            getContentPane().remove(panel);
            getContentPane().add(panel);

            String[] columns = new String[]{"Nombre del producto","Cantidad disponible","Sede correspondiente"};

            llenarTabla(columns,rep);


        }

        paintAll(getGraphics());
        repaint();

    }

    private void llenarTabla(String[] columns,String[][] datos){

        modelo.setRowCount(0);
        modelo.setColumnCount(0);

        for(int i =0;i<columns.length;i++){
            modelo.addColumn(columns[i]);
        }

        for(int i=0;i<datos.length ; i++){

            String[] vec = new String[datos[i].length];
            for(int j=0;j<datos[i].length;j++){
                vec[j] = datos[i][j];
            }
            modelo.addRow(vec);
        }

        tabla = new JTable(modelo);
        tabla.setShowHorizontalLines(false);
        tabla.setRowSelectionAllowed(true);
        tabla.setColumnSelectionAllowed(true);

        scroll = new JScrollPane(tabla);
        scroll.setBounds(25, 480, 629, 120);
        getContentPane().add(scroll);
    }


    private class BotonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(actionEvent.getSource()==informe){
                if(informe.getSelectedItem().toString()=="Inventario"||
                informe.getSelectedItem().toString()=="Ordenes de trabajo" ||
                informe.getSelectedItem().toString()=="Ganancias"){
                    periodo.setEnabled(false);
                }
                else periodo.setEnabled(true);
            }

            if (actionEvent.getSource()==salir){
                dispose();
            }else if (actionEvent.getSource()==consultar){
                graficar();
            }
        }
    }


}
