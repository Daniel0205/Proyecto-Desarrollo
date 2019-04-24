import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

@SuppressWarnings("serial")
public class GUILogin extends JFrame {

	private Container contenedor;
	private JTextField id;
	private JPasswordField password;
	private JButton btnLogin, btnCancel;
	private BaseDeDatos bd;
	private int pX, pY;
	private JLabel lblContrasena, lblUsuario;

	
    //Funcion para inicializar todos los elementos graficos	
	public GUILogin() {

		// Configuraciones de la ventana principal
		contenedor = getContentPane();
		contenedor.removeAll();
		getContentPane().setLayout(null);
		this.setUndecorated(true);
		manejadorDesplazamientoVentana(this);
		ManejadorDeBotones listener = new ManejadorDeBotones();
		Font font = new Font("Tahoma", Font.PLAIN, 14);
		bd = new BaseDeDatos();
		
		// Etiqueta para el nombre al lado del logo
		JLabel lblMueblesYMuebles = new JLabel("   Muebles y Muebles");
		lblMueblesYMuebles.setFont(font);
		lblMueblesYMuebles.setForeground(Color.WHITE);
		lblMueblesYMuebles.setBounds(177, 69, 152, 32);
		getContentPane().add(lblMueblesYMuebles);

		// Etiqueta para el logo XYZ
		JLabel imagenLogo = new JLabel("");
		imagenLogo.setIcon(
				new ImageIcon(GUIMenuAdmin.class.getResource("/images/logo_blanco.png")));
		imagenLogo.setBounds(82, 11, 213, 82);
		getContentPane().add(imagenLogo);

		//-- Eriquetas --//
		JLabel label_1 = new JLabel("BIENVENIDO");
		label_1.setForeground(SystemColor.textHighlight);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label_1.setBounds(130, 148, 125, 26);
		getContentPane().add(label_1);

		lblUsuario = new JLabel("");
		lblUsuario.setIcon(new ImageIcon(GUILogin.class.getResource("/images/login_usuario.png")));
		lblUsuario.setOpaque(true);
		lblUsuario.setBackground(new Color(45, 118, 232));
		lblUsuario.setBounds(96, 210, 36, 36);
		getContentPane().add(lblUsuario);
		
		lblContrasena = new JLabel("");
		lblContrasena.setHorizontalAlignment(SwingConstants.CENTER);
		lblContrasena.setIcon(new ImageIcon(GUILogin.class.getResource("/images/login_contrasena.png")));
		lblContrasena.setOpaque(true);
		lblContrasena.setBackground(new Color(45, 118, 232));
		lblContrasena.setBounds(96, 272, 36, 36);
		getContentPane().add(lblContrasena);

		//JText para ingresar el Id o Usuario
		id = new JTextField(); 
		id.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				id.setText(""); 
			}
		});
		id.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		id.setFont(font);
		id.setBounds(132, 210, 159, 36);
		getContentPane().add(id);
		id.setColumns(10);

		//JText para ingresar la contrase�a (Contrase\u00F1a)		
		password = new JPasswordField();
		password.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		password.setFont(font);
		password.setBounds(132, 272, 159, 36);
		getContentPane().add(password);

		//Boton para iniciar el inicio de sesion
		btnLogin = new JButton("Login");
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setOpaque(true);
		btnLogin.setBackground(new Color(45, 118, 232));
		btnLogin.addActionListener(listener);
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnLogin.setBounds(98, 340, 89, 32);
		getContentPane().add(btnLogin);

		//Boton para cancelar el inicio de sesion
		// y la interaccion con la aplicacion
		btnCancel = new JButton("Cancel");
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setOpaque(true);
		btnCancel.setBackground(new Color(45, 118, 232));
		btnCancel.addActionListener(listener);
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCancel.setBounds(197, 340, 89, 32);
		getContentPane().add(btnCancel);

		//Etiqueta superior azul
		JLabel fondoAzul = new JLabel("");
		fondoAzul.setOpaque(true);
		fondoAzul.setBackground(new Color(45, 118, 232));// azul #2D76E8
		fondoAzul.setBounds(0, 0, 369, 120);
		getContentPane().add(fondoAzul);
		
		// Etiqueta principal blanca
		JLabel fondoGris = new JLabel("");
		fondoGris.setOpaque(true);
		fondoGris.setBackground(new Color(227, 227, 227));
		fondoGris.setBounds(0, 120, 369, 296);
		getContentPane().add(fondoGris);

		//Configuraciones adicionales de la ventana principal
		setBounds(0, -22, 369, 416);
		getContentPane().setLayout(null);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 5, 5));
	}

	private boolean validar() {
		boolean val = true;
		if (id.getText().compareTo("") == 0) {
			val = false;
		}
		String p = new String(password.getPassword());
		if (p.compareTo("") == 0) {
			val = false;
		}
		return val;
	}

	//Manejador de eventos sobre los botones
	private class ManejadorDeBotones implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			if (actionEvent.getSource() == btnLogin) {
				if (validar()) {
					String user = id.getText(), pass = new String(password.getPassword());

					if (user.compareTo("admin") == 0 && pass.compareTo("admin") == 0) {
						GUIMenuAdmin menu = new GUIMenuAdmin(bd);
						menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						dispose();

					} else {
						String tipo_user = bd.validarLogin(user, pass);
						if (tipo_user.compareTo("") != 0) {
							if (tipo_user.compareTo("Jefe de taller") == 0) {
								GUIMenuJefe menu = new GUIMenuJefe(bd, user);
								menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

							}
							if (tipo_user.compareTo("Vendedor      ") == 0) {
								GUIMenuVendedor menu = new GUIMenuVendedor(bd, id.getText());
								menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

							}
                            dispose();

						} else
							JOptionPane.showMessageDialog(null, "Campos erroneos o usuario inactivo");
					}
				} else
					JOptionPane.showMessageDialog(null, "Debe llenar todas los campos");
			}
			if (actionEvent.getSource() == btnCancel)
				System.exit(0);
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
