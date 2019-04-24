import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.DefaultXYDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

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

            llenarTabla("Nombre del producto","Cantidad vendida o cotizada",rep);

        }else if(informe.getSelectedItem()=="Ganancias"){
            DefaultXYDataset dataset = new DefaultXYDataset();
            dataset.addSeries("firefox", new double[][] {{ 2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017 }, { 25, 29.1, 32.1, 32.9, 31.9, 25.5, 20.1, 18.4, 15.3, 11.4, 9.5 }});
            dataset.addSeries("ie", new double[][] {{ 2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017 }, { 67.7, 63.1, 60.2, 50.6, 41.1, 31.8, 27.6, 20.4, 17.3, 12.3, 8.1 }});
            dataset.addSeries("chrome", new double[][] {{ 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017 }, { 0.2, 6.4, 14.6, 25.3, 30.1, 34.3, 43.2, 47.3, 58.4 }});

            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
            renderer.setSeriesPaint(0, Color.ORANGE);
            renderer.setSeriesPaint(1, Color.BLUE);
            renderer.setSeriesPaint(2, Color.GREEN);
            renderer.setSeriesStroke(0, new BasicStroke(2));
            renderer.setSeriesStroke(1, new BasicStroke(2));
            renderer.setSeriesStroke(2, new BasicStroke(2));

            if(panel!=null)getContentPane().remove(panel);
            JFreeChart chart = ChartFactory.createXYLineChart("Browser Quota", "Year", "Quota", dataset);
            chart.getXYPlot().getRangeAxis().setRange(0, 100);
            ((NumberAxis) chart.getXYPlot().getRangeAxis()).setNumberFormatOverride(new DecimalFormat("#'%'"));
            chart.getXYPlot().setRenderer(renderer);

            panel = new ChartPanel(chart);
            panel.setBounds(25, 155, 629,314);
            panel.setVisible(true);
            getContentPane().remove(panel);
            getContentPane().add(panel);



        }else if(informe.getSelectedItem()=="Inventario"){



        }else if(informe.getSelectedItem()=="Ventas"){



        }else if(informe.getSelectedItem()=="Cotizaciones"){


        }else if(informe.getSelectedItem()=="Ordenes de trabajo"){


        }

        paintAll(getGraphics());
        repaint();

    }

    private void llenarTabla(String column1, String column2,String[][] datos){

        modelo = new DefaultTableModel();

        modelo.addColumn(column1);
        modelo.addColumn(column2);

        for (int i=0;i<datos.length ; i++){
            String[] vec = new String[]{datos[i][0],datos[i][1]};
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

            if (actionEvent.getSource()==salir){
                dispose();
            }else if (actionEvent.getSource()==consultar){
                graficar();
            }
        }
    }


}
