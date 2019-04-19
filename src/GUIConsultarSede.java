import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JLabel fondoAzul, fondoNegro, fondoGris, titulo, icono,idl;
    private JTextField busqueda;
    private JButton salir, consultar;
    private JSeparator separador;
    private Font font;
    private int pX, pY;
    private ActionListener listener;


    //Creacion y despliegue de la interfaz de consulta
    public GUIConsultarSede(BaseDeDatos bdIn) {
        
      //Configuraciones de la ventana principal
    	super("Consultar Sede");
  		getContentPane().setForeground(Color.BLACK);
  		bd = bdIn;
  		font = new Font("Tahoma", Font.PLAIN, 14);
  		getContentPane().setLayout(null);
		this.setUndecorated(true);
  		listener = new ManejadorDeBotones();
  		manejadorDesplazamientoVentana(this);

        idl = new JLabel("Id");
        idl.setFont(new Font("Tahoma", Font.PLAIN, 14));
        idl.setBounds(62, 119, 25, 32);
        getContentPane().add(idl);

        consultar = new JButton("Consultar");
        consultar.setOpaque(true);
		consultar.setBackground(new Color(227, 227, 227));
        consultar.setFont(font);
        consultar.setBounds(420, 119, 101, 32);
        consultar.addActionListener(listener);
        getContentPane().add(consultar);

        busqueda = new JTextField();
        busqueda.setFont(font);
        busqueda.setBounds(95, 120, 305, 32);
        getContentPane().add(busqueda);
        busqueda.setColumns(10);

        separador = new JSeparator();
        separador.setBounds(41, 188, 492, 4);
        getContentPane().add(separador);

        salir = new JButton("Salir");
        salir.setOpaque(true);
		salir.setBackground(new Color(227, 227, 227));
        salir.setFont(font);
        salir.setBounds(432, 199, 89, 32);
        salir.addActionListener(listener);
        getContentPane().add(salir);

        //Icono a la izquierda del titulo
		icono = new JLabel("");
		icono.setIcon(new ImageIcon(GUIConsultarUser.class.getResource("/images/buscar.png")));
		icono.setBounds(11, 1, 48, 90);
		getContentPane().add(icono);
		
		//Etiqueta del titulo de la ventana
		titulo = new JLabel("BUSCAR SEDE");
		titulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		titulo.setForeground(Color.WHITE);
		titulo.setBounds(69, 28, 175, 32);
		getContentPane().add(titulo);
		
		// -- Fondos azul y gris -- //
		fondoAzul = new JLabel("");
		fondoAzul.setBounds(1, 1, 559, 90);
		fondoAzul.setOpaque(true);
		fondoAzul.setBackground(new Color(45, 118, 232));
		getContentPane().add(fondoAzul);
		fondoGris = new JLabel("");
		fondoGris.setBounds(1, 89, 559, 174);
		fondoGris.setOpaque(true);
		fondoGris.setBackground(new Color(227,227,227));
		getContentPane().add(fondoGris);
	
		// Fondo negro para las margenes
		fondoNegro = new JLabel("");
		fondoNegro.setBackground(Color.BLACK);
		fondoNegro.setOpaque(true);
		fondoNegro.setBounds(0, 0, 561, 264);
		getContentPane().add(fondoNegro);

      	//Configuraciones adicionales de la ventana        
        setSize(561,264);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
    }


    //Manejador de eventos sobre los botones del menu de consulta
    private class ManejadorDeBotones implements ActionListener{

        public void actionPerformed(ActionEvent actionEvent){
            if (actionEvent.getSource() == consultar){
                String campos = "id_sede, direccion, nombre,telefono,empleado_a_cargo";
                String[][] resultado = bd.consultarSede((String)busqueda.getText().trim(),campos);
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
            String idEncargado = board[0][4],nombreE="Sin",apellidoE=" Empleado";
            if(idEncargado!=null) {
                nombreE = bd.obtenerS(idEncargado, "nombres");
                apellidoE = bd.obtenerS(idEncargado, "apellidos");
            }
            board[0][4] = nombreE.trim()+" "+apellidoE.trim();
            JFrame frame = new JFrame();
            String column[]={"id", "Direccion" ,"Nombre","Telefono", "Empleado a cargo"};
            JTable table = new JTable(board,column);
            table.setRowMargin(0);
            table.setFont(font);
            JScrollPane sp = new JScrollPane(table);
            frame.getContentPane().add(sp);
            frame.setSize(1200,400);
            frame.setVisible(true);
        }
    }
    

	// Manejador del desplazamiento de la ventana causado por el arrastre del mouse
		private void manejadorDesplazamientoVentana(JFrame frame) {
			frame.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent me) {
					pX = me.getX();
					pY = me.getY();
				}
			});
			frame.addMouseMotionListener(new MouseAdapter() {
				@Override
				public void mouseDragged(MouseEvent me) {
					setLocation(getLocation().x + me.getX() - pX, getLocation().y + me.getY() - pY);
				}
			});
		}
}
