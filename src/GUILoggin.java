import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;




@SuppressWarnings("serial")
public class GUILoggin extends JFrame {

	private JTextField user;
	private JPasswordField password;
	private JLabel lblPassword,lblUser, lblUserType, icon;
	private JButton btnLogin, btnCancel;
	private JComboBox<String> user_type;


	public GUILoggin(){

		super("Loggin");
		Font font = new Font("Tahoma", Font.PLAIN, 14);
		ManejadorDeBotones listener = new ManejadorDeBotones();

		lblUser = new JLabel("User");
		lblUser.setFont(font);
		lblUser.setBounds(53, 153, 70, 32);
		getContentPane().add(lblUser);

		lblPassword = new JLabel("Password");
		lblPassword.setFont(font);
		lblPassword.setBounds(53, 196, 70, 32);
		getContentPane().add(lblPassword);

		user = new JTextField();
		user.setFont(font);
		user.setBounds(128, 152, 149, 32);
		getContentPane().add(user);
		user.setColumns(10);

		password = new JPasswordField();
		password.setFont(font);
		password.setBounds(128, 195, 149, 32);
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

		String[] listaTipo = new String[] {"Gerente", "Jefe de taller", "Vendedor"};
		user_type = new JComboBox<>(listaTipo);
		user_type.setFont(font);
		user_type.setBounds(128, 112, 149, 32);
		getContentPane().add(user_type);

		lblUserType = new JLabel("User Type");
		lblUserType.setFont(font);
		lblUserType.setBounds(53, 112, 70, 32);
		getContentPane().add(lblUserType);

		icon = new JLabel("");
		URL filePath = this.getClass().getResource("/images/login.png");
		icon.setHorizontalAlignment(SwingConstants.CENTER);
		icon.setIcon(new ImageIcon(filePath));
		icon.setBounds(122, 11, 118, 90);
		getContentPane().add(icon);

		setBounds(0, -22, 353, 322);
		getContentPane().setLayout(null);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
	}

    private boolean validar(){
        boolean val=true;
        if(user.getText().compareTo("")==0){
            val=false;
        }
        String p = new String(password.getPassword());
        if(p.compareTo("")==0){
            val=false;
        }

        return val;
    }

    private class ManejadorDeBotones implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            if (actionEvent.getSource() == btnLogin) {
                if (validar()) {
                    String id = user.getText(), pass = new String(password.getPassword());

                    if (id.compareTo("admin") == 0 && pass.compareTo("admin") == 0) {
                        setVisible(false);

                        BaseDeDatos bd = new BaseDeDatos();
                        GUInitAction menu = new GUInitAction(bd);

                        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    }

                } else JOptionPane.showMessageDialog(null, "Debe llenar todas los campos");
            }
            if (actionEvent.getSource() == btnCancel) System.exit(0);
        }
    }
}
