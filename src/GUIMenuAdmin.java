import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class GUIMenuAdmin extends JFrame {

	private Container contenedor;
	private JButton registrarUsuario, actualizarUsuario, mostrarUsuarios;
	private JButton registrarSede, actualizarSede, mostrarSedes, salir,registrarProducto;
	private Font font;
	private ActionListener listener;
	private BaseDeDatos bd;
	private JLabel fondoAzul,lblSalir, label_1, label_2, label_3;
	private JSeparator separator_1, separator_2, separator_3;
    private int pX, pY;
    /**
     * @wbp.nonvisual location=711,689
     */
	
    public GUIMenuAdmin(BaseDeDatos bdIn){
		super("MENU GERENTE");
		initGUI();
		bd = bdIn;
	}

    //Funcion para inicializar todos los elementos graficos
	private void initGUI() {
		
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
		imagenSofa.setBounds(198, 22, 120, 80);
		getContentPane().add(imagenSofa);

		// Etiqueta para el nombre al lado del logo
		JLabel lblMueblesYMuebles = new JLabel("   Muebles y Muebles");
		lblMueblesYMuebles.setFont(font);
		lblMueblesYMuebles.setForeground(Color.WHITE);
		lblMueblesYMuebles.setBounds(389, 84, 152, 32);
		getContentPane().add(lblMueblesYMuebles);

		// Etiqueta para el logo XYZ
		JLabel imagenLogo = new JLabel("");
		imagenLogo.setIcon(
				new ImageIcon(GUIMenuAdmin.class.getResource("/images/logo_blanco.png")));
		imagenLogo.setBounds(287, 30, 213, 82);
		getContentPane().add(imagenLogo);

		//Boton para inicial el registro de los usuarios
		registrarUsuario = new JButton("REGISTRAR");
		registrarUsuario.setForeground(SystemColor.textHighlight);
		registrarUsuario.setVerticalTextPosition(SwingConstants.BOTTOM);
		registrarUsuario.setHorizontalTextPosition(SwingConstants.CENTER);
		registrarUsuario.setIcon(new ImageIcon(GUIMenuAdmin.class.getResource("/images/usuario_crear.png")));
		registrarUsuario.setFocusPainted(false);
		registrarUsuario.setBorderPainted(false);
		registrarUsuario.setOpaque(true);
		registrarUsuario.setBackground(new Color(227, 227, 227));
		registrarUsuario.setFont(font);
		registrarUsuario.setBounds(118, 211, 140, 120);
		registrarUsuario.addActionListener(listener);
		getContentPane().add(registrarUsuario);

		//Boton para busqueda de usuarios
		mostrarUsuarios = new JButton("BUSCAR");
		mostrarUsuarios.setForeground(SystemColor.textHighlight);
		mostrarUsuarios.setVerticalTextPosition(SwingConstants.BOTTOM);
		mostrarUsuarios.setHorizontalTextPosition(SwingConstants.CENTER);
		mostrarUsuarios.setIcon(new ImageIcon(GUIMenuAdmin.class.getResource("/images/usuario_buscar.png")));
		mostrarUsuarios.setFocusPainted(false);
		mostrarUsuarios.setBorderPainted(false);
		mostrarUsuarios.setOpaque(true);
		mostrarUsuarios.setBackground(new Color(227, 227, 227));
		mostrarUsuarios.setFont(font);
		mostrarUsuarios.setBounds(268, 211, 140, 120);
		mostrarUsuarios.addActionListener(listener);
		getContentPane().add(mostrarUsuarios);
		
		//Boton para inicial la actualizacion de los usuarios
		actualizarUsuario = new JButton("ACTUALIZAR");
		actualizarUsuario.setForeground(SystemColor.textHighlight);
		actualizarUsuario.setVerticalTextPosition(SwingConstants.BOTTOM);
		actualizarUsuario.setHorizontalTextPosition(SwingConstants.CENTER);
		actualizarUsuario.setIcon(new ImageIcon(GUIMenuAdmin.class.getResource("/images/usuario_actualizar.png")));
		actualizarUsuario.setFocusPainted(false);
		actualizarUsuario.setBorderPainted(false);
		actualizarUsuario.setOpaque(true);
		actualizarUsuario.setBackground(new Color(227, 227, 227));
		actualizarUsuario.setFont(font);
		actualizarUsuario.setBounds(418, 211, 140, 120);
		actualizarUsuario.addActionListener(listener);
		getContentPane().add(actualizarUsuario);

		//Boton para inicial el registro de sedes
		registrarSede = new JButton("REGISTRAR");
		registrarSede.setForeground(SystemColor.textHighlight);
		registrarSede.setVerticalTextPosition(SwingConstants.BOTTOM);
		registrarSede.setHorizontalTextPosition(SwingConstants.CENTER);
		registrarSede.setIcon(new ImageIcon(GUIMenuAdmin.class.getResource("/images/sede_crear.png")));
		registrarSede.setFocusPainted(false);
		registrarSede.setBorderPainted(false);
		registrarSede.setOpaque(true);
		registrarSede.setBackground(new Color(227, 227, 227));
		registrarSede.setFont(font);
		registrarSede.setBounds(118, 371, 140, 120);
		registrarSede.addActionListener(listener);
		getContentPane().add(registrarSede);

		//Boton para inicial el registro de sedes
		mostrarSedes = new JButton("BUSCAR");
		mostrarSedes.setForeground(SystemColor.textHighlight);
		mostrarSedes.setVerticalTextPosition(SwingConstants.BOTTOM);
		mostrarSedes.setHorizontalTextPosition(SwingConstants.CENTER);
		mostrarSedes.setIcon(new ImageIcon(GUIMenuAdmin.class.getResource("/images/sede_buscar.png")));
		mostrarSedes.setFocusPainted(false);
		mostrarSedes.setBorderPainted(false);
		mostrarSedes.setOpaque(true);
		mostrarSedes.setBackground(new Color(227, 227, 227));
		mostrarSedes.setFont(font);
		mostrarSedes.setBounds(268, 371, 140, 120);
		mostrarSedes.addActionListener(listener);
		getContentPane().add(mostrarSedes);
		
		//Boton para actualizacion de sedes
		actualizarSede = new JButton("ACTUALIZAR");
		actualizarSede.setForeground(SystemColor.textHighlight);
		actualizarSede.setVerticalTextPosition(SwingConstants.BOTTOM);
		actualizarSede.setHorizontalTextPosition(SwingConstants.CENTER);
		actualizarSede.setIcon(new ImageIcon(GUIMenuAdmin.class.getResource("/images/sede_actualizar.png")));
		actualizarSede.setFocusPainted(false);
		actualizarSede.setBorderPainted(false);
		actualizarSede.setOpaque(true);
		actualizarSede.setBackground(new Color(227, 227, 227));
		actualizarSede.setFont(font);
		actualizarSede.setBounds(418, 371, 140, 120);
		actualizarSede.addActionListener(listener);
		getContentPane().add(actualizarSede);

		//Boton para registrar un productp
		registrarProducto = new JButton("REGISTRAR");
		registrarProducto.setForeground(SystemColor.textHighlight);
		registrarProducto.setVerticalTextPosition(SwingConstants.BOTTOM);
		registrarProducto.setHorizontalTextPosition(SwingConstants.CENTER);
		registrarProducto.setIcon(new ImageIcon(GUIMenuAdmin.class.getResource("/images/producto_agregar.png")));
		registrarProducto.setFocusPainted(false);
		registrarProducto.setBorderPainted(false);
		registrarProducto.setOpaque(true);
		registrarProducto.setBackground(new Color(227, 227, 227));
		registrarProducto.setFont(font);
		registrarProducto.setBounds(118, 527, 140, 120);
		registrarProducto.addActionListener(listener);
		getContentPane().add(registrarProducto);
		
		//-- Eriquetas --//
		label_1 = new JLabel("USUARIOS");
		label_1.setForeground(SystemColor.textHighlight);
		label_1.setFont(font);
		label_1.setBounds(118, 184, 74, 14);
		getContentPane().add(label_1);
		label_2 = new JLabel("SEDES");
		label_2.setForeground(SystemColor.textHighlight);
		label_2.setFont(font);
		label_2.setBounds(118, 346, 49, 14);
		getContentPane().add(label_2);
		label_3 = new JLabel("PRODUCTOS");
		label_3.setForeground(SystemColor.textHighlight);
		label_3.setFont(font);
		label_3.setBounds(118, 501, 81, 14);
		getContentPane().add(label_3);
		
		//-- Separadores --//
		separator_1 = new JSeparator();
		separator_1.setForeground(SystemColor.textHighlight);
		separator_1.setBackground(new Color(255, 255, 255));
		separator_1.setBounds(184, 191, 374, 9);
		getContentPane().add(separator_1);
		separator_2 = new JSeparator();
		separator_2.setForeground(SystemColor.textHighlight);
		separator_2.setBackground(Color.WHITE);
		separator_2.setBounds(173, 352, 385, 9);
		getContentPane().add(separator_2);
		separator_3 = new JSeparator();
		separator_3.setForeground(SystemColor.textHighlight);
		separator_3.setBackground(Color.WHITE);
		separator_3.setBounds(206, 507, 52, 9);
		getContentPane().add(separator_3);

		//Boton salir
		salir = new JButton("");
		salir.setIcon(new ImageIcon(GUIMenuAdmin.class.getResource("/images/salir.png")));
		salir.setBorderPainted(false);
		salir.setBorder(null);
		salir.setMargin(new Insets(0, 0, 0, 0));
		salir.setContentAreaFilled(false);
		salir.addActionListener(listener);
		salir.setBounds(588, 593, 81, 59);
		getContentPane().add(salir);
		
		//Etiqueta salir
		lblSalir = new JLabel("SALIR");
		lblSalir.setHorizontalAlignment(SwingConstants.CENTER);
		lblSalir.setForeground(SystemColor.textHighlight);
		lblSalir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSalir.setBounds(597, 576, 49, 14);
		getContentPane().add(lblSalir);
		
		JLabel titulo = new JLabel("GERENTE");
		titulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		titulo.setForeground(SystemColor.textHighlight);
		titulo.setBounds(466, 145, 92, 21);
		getContentPane().add(titulo);

		//Etiqueta superior azul
		fondoAzul = new JLabel("");
		fondoAzul.setOpaque(true);
		fondoAzul.setBackground(new Color(45, 118, 232));// azul #2D76E8
		fondoAzul.setBounds(0, 0, 679, 120);
		getContentPane().add(fondoAzul);
		
		// Etiqueta principal blanca
		JLabel fondoBlanco = new JLabel("");
		fondoBlanco.setOpaque(true);
		fondoBlanco.setBackground(new Color(255, 255, 255));
		fondoBlanco.setBounds(0, 120, 679, 568);
		getContentPane().add(fondoBlanco);

		//Configuraciones adicionales de la ventana principal
		setSize(679, 688);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 5, 5));
}
	

	//Manejador de eventos sobre los botones
	private class ManejadorDeBotones implements ActionListener{

		public void actionPerformed(ActionEvent actionEvent){
			if (actionEvent.getSource() == registrarUsuario){
				JFrame formulario1 = new GUIRegistrarUser(bd);
				formulario1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			} 
			else if (actionEvent.getSource() == actualizarUsuario){
				JFrame formulario2 = new GUIActualizarUser(bd);
				formulario2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
			else if (actionEvent.getSource() == mostrarUsuarios){
				JFrame formulario3 = new GUIConsultarUser(bd);
				formulario3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
			else if (actionEvent.getSource() == mostrarSedes){
				JFrame formulario3 = new GUIConsultarSede(bd);
				formulario3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
			else if (actionEvent.getSource() == registrarSede){
				JFrame formulario4 = new GUIRegistrarSedes(bd);
				formulario4.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
			else if(actionEvent.getSource() == actualizarSede){
                JFrame formulario5 = new GUIActualizarSede(bd);
                formulario5.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
            else if(actionEvent.getSource() == registrarProducto){
                JFrame formulario6 = new GUIRegistrarProd(bd);
                formulario6.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                formulario6.setPreferredSize(new Dimension(400,400));
            }
			else if (actionEvent.getSource() == salir){
                GUILogin login = new GUILogin();
                login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                dispose();
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
