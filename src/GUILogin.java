import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;


@SuppressWarnings("serial")
public class GUILogin extends JFrame {

	//Variables y componentes de la GUI
	private JTextField id;
	private JPasswordField password;
	private JLabel lblPassword,lblUser, icon;
	private JButton btnLogin, btnCancel;
	private BaseDeDatos bd;

    //COnstructor
	public GUILogin(){
		super("Loggin");
		setTitle("Iniciar Sesion");

        bd = new BaseDeDatos();

		Font font = new Font("Tahoma", Font.PLAIN, 14);
		ManejadorDeBotones listener = new ManejadorDeBotones();

		lblUser = new JLabel("Usuario/Cedula:");
		lblUser.setFont(font);
		lblUser.setBounds(21, 151, 112, 32);
		getContentPane().add(lblUser);

		lblPassword = new JLabel("Contrase\u00F1a:");
		lblPassword.setFont(font);
		lblPassword.setBounds(29, 194, 94, 32);
		getContentPane().add(lblPassword);

		id = new JTextField();
		id.setFont(font);
		id.setBounds(143, 152, 149, 32);
		getContentPane().add(id);
		id.setColumns(10);

		password = new JPasswordField();
		password.setFont(font);
		password.setBounds(143, 195, 149, 32);
		getContentPane().add(password);

		btnLogin = new JButton("Login");
		btnLogin.addActionListener(listener);
		btnLogin.setFont(font);
		btnLogin.setBounds(80, 238, 89, 32);
		getContentPane().add(btnLogin);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(listener);
		btnCancel.setFont(font);
		btnCancel.setBounds(188, 238, 89, 32);
		getContentPane().add(btnCancel);


		icon = new JLabel("");
		URL filePath = this.getClass().getResource("/images/logo.png");
		icon.setHorizontalAlignment(SwingConstants.CENTER);
		icon.setIcon(new ImageIcon(filePath));
		icon.setBounds(113, 32, 120, 120);
		icon.setBackground(null);
		getContentPane().add(icon);

		setBounds(0, -22, 353, 322);
		getContentPane().setLayout(null);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
	}

    // Funcion que valida si algun campo a registrar esta vacio
    private boolean validar(){
        boolean val=true;
        if(id.getText().compareTo("")==0){
            val=false;
        }
        String p = new String(password.getPassword());
        if(p.compareTo("")==0){
            val=false;
        }

        return val;
    }

    //Manejador de eventos
    private class ManejadorDeBotones implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            if (actionEvent.getSource() == btnLogin) {
                if (validar()) {
                    String user = id.getText(), pass = new String(password.getPassword());

					if (user.compareTo("admin") == 0 && pass.compareTo("admin") == 0 ) {
						GUIMenuAdmin menu = new GUIMenuAdmin(bd);
						menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        setVisible(false);

					}else{
						String tipo_user=bd.validarLogin(user,pass);
						if (tipo_user.compareTo("")!=0){
							if(tipo_user.compareTo("Jefe de taller")==0){
								GUIMenuJefe menu = new GUIMenuJefe(bd, user);
								menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

							}if(tipo_user.compareTo("Vendedor      ")==0){
								GUIMenuVendedor menu = new GUIMenuVendedor(bd,id.getText());
								menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

							}
							setVisible(false);


						}
						else JOptionPane.showMessageDialog(null, "Campos erroneos o usuario inactivo");
					}
                } else JOptionPane.showMessageDialog(null, "Debe llenar todas los campos");
            }
            if (actionEvent.getSource() == btnCancel) 
            	System.exit(0);
        }
    }
}
