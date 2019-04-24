import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class GUIMenuJefe extends JFrame {

	//Variables y componentes de la GUI
    private final BaseDeDatos bd;
    private Container contenedor;
    private Font font;
    private JButton crearOrden,modificarOrden,consultarOrden,salir;
    private JLabel lblSalir, label_1;
    private  ManejadorDeBotones listener;
    private String idJefe;
    private int pX, pY;
    private JSeparator separator_1;
    /**
     * @wbp.nonvisual location=741,519
     */
    private final JLabel label = new JLabel("New label");
    
    public GUIMenuJefe(BaseDeDatos bd, String idJefe){
        super("MENU JEFE DE TALLER");
        this.bd=bd;
        this.idJefe=idJefe;
        InitGUI();
    }

  //Funcion para inicializar todos los elementos graficos
    private void InitGUI() {

		//Configuraciones de la ventana principal
		contenedor = getContentPane();
		contenedor.removeAll();
		getContentPane().setLayout(null);
		this.setUndecorated(true);
		listener = new ManejadorDeBotones();
		manejadorDesplazamientoVentana(this);

		// Logo del sofa
		JLabel imagenSofa = new JLabel("");
		imagenSofa.setIcon(new ImageIcon(GUIMenuAdmin.class.getResource("/images/sofa.png")));
		imagenSofa.setBounds(199, 15, 120, 80);
		getContentPane().add(imagenSofa);

		// Etiqueta para el nombre al ladpo del logo
		JLabel lblMueblesYMuebles = new JLabel("   Muebles y Muebles");
		lblMueblesYMuebles.setFont(font);
		lblMueblesYMuebles.setForeground(Color.WHITE);
		lblMueblesYMuebles.setBounds(390, 77, 152, 32);
		getContentPane().add(lblMueblesYMuebles);

		// Etiqueta para el logo XYZ
		JLabel imagenLogo = new JLabel("");
		imagenLogo.setIcon(
				new ImageIcon(GUIMenuAdmin.class.getResource("/images/logo_blanco.png")));
		imagenLogo.setBounds(288, 23, 213, 82);
		getContentPane().add(imagenLogo);

		//Boton para inicial crear ordenes de trabajo
		crearOrden = new JButton("CREAR");
		crearOrden.setForeground(SystemColor.textHighlight);
		crearOrden.setVerticalTextPosition(SwingConstants.BOTTOM);
		crearOrden.setHorizontalTextPosition(SwingConstants.CENTER);
		crearOrden.setIcon(new ImageIcon(GUIMenuAdmin.class.getResource("/images/orden_crear.png")));
		crearOrden.setFocusPainted(false);
		crearOrden.setBorderPainted(false);
		crearOrden.setOpaque(true);
		crearOrden.setBackground(new Color(227, 227, 227));
        crearOrden.setFont(font);
        crearOrden.setBounds(117, 219, 140, 120);
        crearOrden.addActionListener(listener);
        getContentPane().add(crearOrden);

		//Boton para modificar ordenes de trabajo
		modificarOrden = new JButton("MODIFICAR");
		modificarOrden.setForeground(SystemColor.textHighlight);
		modificarOrden.setVerticalTextPosition(SwingConstants.BOTTOM);
		modificarOrden.setHorizontalTextPosition(SwingConstants.CENTER);
		modificarOrden.setIcon(new ImageIcon(GUIMenuAdmin.class.getResource("/images/orden_editar.png")));
		modificarOrden.setFocusPainted(false);
		modificarOrden.setBorderPainted(false);
		modificarOrden.setOpaque(true);
		modificarOrden.setBackground(new Color(227, 227, 227));
		modificarOrden.setFont(font);
		modificarOrden.setBounds(267, 219, 140, 120);
        modificarOrden.addActionListener(listener);
        getContentPane().add(modificarOrden);

		//Boton para inicial la actualizacion de los usuarios
		consultarOrden = new JButton("CONSULTAR");
		consultarOrden.setForeground(SystemColor.textHighlight);
		consultarOrden.setVerticalTextPosition(SwingConstants.BOTTOM);
		consultarOrden.setHorizontalTextPosition(SwingConstants.CENTER);
		consultarOrden.setIcon(new ImageIcon(GUIMenuAdmin.class.getResource("/images/orden_buscar.png")));
		consultarOrden.setFocusPainted(false);
		consultarOrden.setBorderPainted(false);
		consultarOrden.setOpaque(true);
		consultarOrden.setBackground(new Color(227, 227, 227));
		consultarOrden.setFont(font);
		consultarOrden.setBounds(417, 219, 140, 120);
        consultarOrden.addActionListener(listener);
        getContentPane().add(consultarOrden);

        //Boton salir
		salir = new JButton("");
		salir.setIcon(new ImageIcon(GUIMenuAdmin.class.getResource("/images/salir.png")));
		salir.setBorderPainted(false);
		salir.setBorder(null);
		salir.setMargin(new Insets(0, 0, 0, 0));
		salir.setContentAreaFilled(false);
		salir.addActionListener(listener);
		salir.setBounds(588, 411, 81, 59);
		getContentPane().add(salir);

		//-- Eriqueta --//
		label_1 = new JLabel("ORDENES DE TRABAJO");
		label_1.setForeground(SystemColor.textHighlight);
		label_1.setFont(font);
		label_1.setBounds(117, 192, 140, 14);
		getContentPane().add(label_1);

		//-- Separadores --//
		separator_1 = new JSeparator();
		separator_1.setForeground(SystemColor.textHighlight);
		separator_1.setBackground(new Color(255, 255, 255));
		separator_1.setBounds(260, 199, 297, 9);
		getContentPane().add(separator_1);
		
		//Etiqueta salir
		lblSalir = new JLabel("SALIR");
		lblSalir.setHorizontalAlignment(SwingConstants.CENTER);
		lblSalir.setForeground(SystemColor.textHighlight);
		lblSalir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSalir.setBounds(597, 391, 49, 14);
		getContentPane().add(lblSalir);
		//Etiqueta superior azul
		JLabel fondoAzul = new JLabel("");
		fondoAzul.setOpaque(true);
		fondoAzul.setBackground(new Color(45, 118, 232));// azul #2D76E8
		fondoAzul.setBounds(0, 0, 679, 120);
		getContentPane().add(fondoAzul);

		//Titulo para tipo de usuario
		JLabel titulo = new JLabel("JEFE DE TALLER");
		titulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		titulo.setForeground(SystemColor.textHighlight);
		titulo.setBounds(417, 146, 140, 21);
		getContentPane().add(titulo);

		
		// Etiqueta principal blanca
		JLabel fondoBlanco = new JLabel("");
		fondoBlanco.setOpaque(true);
		fondoBlanco.setBackground(new Color(255, 255, 255));
		fondoBlanco.setBounds(0, 120, 679, 377);
		getContentPane().add(fondoBlanco);

		//Configuraciones adicionales de la ventana principal
		setSize(679, 497);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 5, 5));

    }

    //Clase para manejar los eventos sobre los botones
    private class ManejadorDeBotones implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
        	if (actionEvent.getSource() == crearOrden) {
        		GUICrearOrden crearOrden = new GUICrearOrden(bd,idJefe);
                crearOrden.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        	}
        	else if(actionEvent.getSource() == salir){
                GUILogin login = new GUILogin();
                login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dispose();
			}
        	else if(actionEvent.getSource() == modificarOrden){
                GUIActualizarOrden actualizarOrden = new GUIActualizarOrden(bd,idJefe);
                actualizarOrden.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
            else if(actionEvent.getSource() == consultarOrden){
                GUIConsultarOrden consultarOrden = new GUIConsultarOrden(bd,idJefe);
                consultarOrden.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }

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
