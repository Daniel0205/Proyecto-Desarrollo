import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

public class GUILoggin extends JFrame {


    private JTextField user;
    private JPasswordField password;
    private JLabel lblPassword,lblUser, lblUserType, icon;
    private JButton btnLogin, btnCancel;
    private JComboBox user_type;

    public GUILoggin(){
        super("Loggin");

        crearComponentes();
    }

    private void crearComponentes() {

        Font font = new Font("Tahoma", Font.PLAIN, 14);
        ManejadorDeBotones listener = new ManejadorDeBotones();

        lblUser = new JLabel("User");
        lblUser.setFont(font);
        lblUser.setBounds(48, 146, 70, 14);
        getContentPane().add(lblUser);

        lblPassword = new JLabel("Password");
        lblPassword.setFont(font);
        lblPassword.setBounds(48, 175, 70, 14);
        getContentPane().add(lblPassword);

        user = new JTextField();
        user.setFont(font);
        user.setBounds(128, 143, 149, 20);
        getContentPane().add(user);
        user.setColumns(10);

        password = new JPasswordField();
        password.setFont(font);
        password.setBounds(128, 174, 149, 20);
        getContentPane().add(password);

        btnLogin = new JButton("Login");
        btnLogin.addActionListener(listener);
        btnLogin.setFont(font);
        btnLogin.setBounds(81, 216, 89, 23);
        getContentPane().add(btnLogin);

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(listener);
        btnCancel.setFont(font);
        btnCancel.setBounds(188, 216, 89, 23);
        getContentPane().add(btnCancel);

        user_type = new JComboBox(new String[] {"Gerente", "Jefe de taller", "Vendedor"});
        user_type.setFont(font);
        user_type.setBounds(128, 112, 149, 20);
        getContentPane().add(user_type);

        lblUserType = new JLabel("User Type");
        lblUserType.setFont(font);
        lblUserType.setBounds(48, 110, 70, 20);
        getContentPane().add(lblUserType);

        icon = new JLabel("");
        URL filePath = this.getClass().getResource("/images/login.png");
        icon.setHorizontalAlignment(SwingConstants.CENTER);
        icon.setIcon(new ImageIcon(filePath));
        icon.setBounds(122, 11, 118, 90);
        getContentPane().add(icon);

        //setForeground(Color.LIGHT_GRAY);
        //getContentPane().setForeground(Color.WHITE);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, -22, 353, 300);
        getContentPane().setLayout(null);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);

    }

    private class ManejadorDeBotones implements ActionListener{


        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            if (actionEvent.getSource()==btnLogin){
                String id=user.getText(), pass = new String(password.getPassword());

                if (id.compareTo("admin")==0 && pass.compareTo("admin")==0){
                    setVisible(false);

                    BaseDeDatos bd = new BaseDeDatos();
                    GUInitAction menu = new GUInitAction(bd);

                    menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                }
            }

            if (actionEvent.getSource()==btnCancel)System.exit(0);
        }
    }
}
