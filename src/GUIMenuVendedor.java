import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

@SuppressWarnings("serial")
public class GUIMenuVendedor extends JFrame {

	private final BaseDeDatos bd;
	private final String id;
	private Container contenedor;
	private JSeparator separator_1;
	private Font font;
	private JButton consultaProducto, venta, cotizacion, salir;
	private JLabel lblSalir, lblProductos;
	private ManejadorDeBotones listener;
	private int pX, pY;

	public GUIMenuVendedor(BaseDeDatos bd, String id) {
		super("MENU VENDEDOR");
		this.id = id;
		this.bd = bd;
		initGUI();
	}

	// Funcion para inicializar todos los elementos graficos
	private void initGUI() {

		// Configuraciones de la ventana principal
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

		// Etiqueta para el nombre al lado del logo
		JLabel lblMueblesYMuebles = new JLabel("   Muebles y Muebles");
		lblMueblesYMuebles.setFont(font);
		lblMueblesYMuebles.setForeground(Color.WHITE);
		lblMueblesYMuebles.setBounds(390, 77, 152, 32);
		getContentPane().add(lblMueblesYMuebles);

		// Etiqueta para el logo XYZ
		JLabel imagenLogo = new JLabel("");
		imagenLogo.setIcon(new ImageIcon(GUIMenuAdmin.class.getResource("/images/logo_blanco.png")));
		imagenLogo.setBounds(288, 23, 213, 82);
		getContentPane().add(imagenLogo);
		
		//Boton para consultar la disponibilidad de un producto
		consultaProducto = new JButton("CONSULTAR");
		consultaProducto.setForeground(SystemColor.textHighlight);
		consultaProducto.setVerticalTextPosition(SwingConstants.BOTTOM);
		consultaProducto.setHorizontalTextPosition(SwingConstants.CENTER);
		consultaProducto.setIcon(new ImageIcon(GUIMenuAdmin.class.getResource("/images/producto_buscar.png")));
		consultaProducto.setFocusPainted(false);
		consultaProducto.setBorderPainted(false);
		consultaProducto.setOpaque(true);
		consultaProducto.setBackground(new Color(227, 227, 227));
        consultaProducto.setFont(font);
        consultaProducto.setBounds(117, 219, 140, 120);
		consultaProducto.addActionListener(listener);
		getContentPane().add(consultaProducto);

		//Boton para concretar una venta cotizada
		venta = new JButton("VENDER");
		venta.setForeground(SystemColor.textHighlight);
		venta.setVerticalTextPosition(SwingConstants.BOTTOM);
		venta.setHorizontalTextPosition(SwingConstants.CENTER);
		venta.setIcon(new ImageIcon(GUIMenuAdmin.class.getResource("/images/producto_vender.png")));
		venta.setFocusPainted(false);
		venta.setBorderPainted(false);
		venta.setOpaque(true);
		venta.setBackground(new Color(227, 227, 227));
		venta.setFont(font);
		venta.setBounds(267, 219, 140, 120);
		venta.addActionListener(listener);
		getContentPane().add(venta);

		//Boton empezar un proceso de cotizacion
		cotizacion = new JButton("ACTUALIZAR");
		cotizacion.setForeground(SystemColor.textHighlight);
		cotizacion.setVerticalTextPosition(SwingConstants.BOTTOM);
		cotizacion.setHorizontalTextPosition(SwingConstants.CENTER);
		cotizacion.setIcon(new ImageIcon(GUIMenuAdmin.class.getResource("/images/orden_buscar.png")));
		cotizacion.setFocusPainted(false);
		cotizacion.setBorderPainted(false);
		cotizacion.setOpaque(true);
		cotizacion.setBackground(new Color(227, 227, 227));
		cotizacion.setFont(font);
		cotizacion.setBounds(417, 219, 140, 120);
		cotizacion.addActionListener(listener);
		getContentPane().add(cotizacion);

		// Boton salir
		salir = new JButton("");
		salir.setIcon(new ImageIcon(GUIMenuAdmin.class.getResource("/images/salir.png")));
		salir.setBorderPainted(false);
		salir.setBorder(null);
		salir.setMargin(new Insets(0, 0, 0, 0));
		salir.setContentAreaFilled(false);
		salir.addActionListener(listener);
		salir.setBounds(588, 411, 81, 59);
		getContentPane().add(salir);

		// -- Eriqueta --//
		lblProductos = new JLabel("PRODUCTOS");
		lblProductos.setForeground(SystemColor.textHighlight);
		lblProductos.setFont(font);
		lblProductos.setBounds(117, 192, 123, 14);
		getContentPane().add(lblProductos);

		// -- Separadores --//
		separator_1 = new JSeparator();
		separator_1.setForeground(SystemColor.textHighlight);
		separator_1.setBackground(new Color(255, 255, 255));
		separator_1.setBounds(205, 199, 352, 9);
		getContentPane().add(separator_1);

		// Etiqueta salir
		lblSalir = new JLabel("SALIR");
		lblSalir.setHorizontalAlignment(SwingConstants.CENTER);
		lblSalir.setForeground(SystemColor.textHighlight);
		lblSalir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSalir.setBounds(597, 391, 49, 14);
		getContentPane().add(lblSalir);
		// Etiqueta superior azul
		JLabel fondoAzul = new JLabel("");
		fondoAzul.setOpaque(true);
		fondoAzul.setBackground(new Color(45, 118, 232));// azul #2D76E8
		fondoAzul.setBounds(0, 0, 679, 120);
		getContentPane().add(fondoAzul);

		// Titulo para tipo de usuario
		JLabel titulo = new JLabel("VENDEDOR");
		titulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		titulo.setForeground(SystemColor.textHighlight);
		titulo.setBounds(452, 146, 105, 21);
		getContentPane().add(titulo);

		// Etiqueta principal blanca
		JLabel fondoBlanco = new JLabel("");
		fondoBlanco.setOpaque(true);
		fondoBlanco.setBackground(new Color(255, 255, 255));
		fondoBlanco.setBounds(0, 120, 679, 377);
		getContentPane().add(fondoBlanco);

		// Configuraciones adicionales de la ventana principal
		setSize(679, 497);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 5, 5));
	}

	// Clase para manejar los eventos sobre los botones
	private class ManejadorDeBotones implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			if (actionEvent.getSource() == cotizacion) {
				GUICotizacion cot = new GUICotizacion(bd, id);
				cot.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			} else if (actionEvent.getSource() == salir) {
				GUILogin login = new GUILogin();
				login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				dispose();
			} else if (actionEvent.getSource() == venta) {
				GUIVenta ven = new GUIVenta(bd, id);
				ven.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
