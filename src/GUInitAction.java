import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;


@SuppressWarnings("serial")
public class GUInitAction extends JFrame {

	private Container contenedor;
	private JButton registrarUsuario,actualizarUsuario,mostrarUsuarios;
	private JButton actualizarSede, registrarSede, mostrarSedes, salir;
	private JSeparator separator_1, separator_2;
	private JLabel icon;
	private Font font;
	private ActionListener listener;
	private BaseDeDatos bd;


	public GUInitAction(BaseDeDatos bdIn){

		super("Acciones");
		font = new Font("Tahoma", Font.PLAIN, 14);
		initGUI();
		bd = bdIn;
	}

	private void initGUI() {

		contenedor = getContentPane();
		contenedor.removeAll();
		getContentPane().setLayout(null);

		listener = new ManejadorDeBotones();

		actualizarUsuario = new JButton("Actualizar usuario");
		actualizarUsuario.setFont(font);
		actualizarUsuario.setBounds(22, 144, 142, 32);
		actualizarUsuario.addActionListener(listener);
		getContentPane().add(actualizarUsuario);

		registrarUsuario = new JButton("Registrar usuario");
		registrarUsuario.setFont(font);
		registrarUsuario.setBounds(22, 101, 142, 32);
		registrarUsuario.addActionListener(listener);
		getContentPane().add(registrarUsuario);

		mostrarUsuarios = new JButton("Mostrar usuarios");
		mostrarUsuarios.setFont(font);
		mostrarUsuarios.setBounds(22, 187, 142, 32);
		mostrarUsuarios.addActionListener(listener);
		getContentPane().add(mostrarUsuarios);

		mostrarSedes = new JButton("Mostrar sedes");
		mostrarSedes.setFont(font);
		mostrarSedes.setBounds(187, 187, 142, 32);
		getContentPane().add(mostrarSedes);

		registrarSede = new JButton("Registrar Sede");
		registrarSede.setFont(font);
		registrarSede.setBounds(187, 101, 142, 32);
		getContentPane().add(registrarSede);

		actualizarSede = new JButton("Actualizar sede");
		actualizarSede.setFont(font);
		actualizarSede.setBounds(187, 144, 142, 32);
		getContentPane().add(actualizarSede);

		salir = new JButton("Salir");
		salir.addActionListener(listener);
		salir.setFont(font);
		salir.setBounds(193, 267, 136, 32);
		getContentPane().add(salir);

		icon = new JLabel("");
		URL filePath = this.getClass().getResource("/images/home.png");
		icon.setIcon(new ImageIcon(filePath));
		icon.setBounds(31, 11, 66, 66);
		getContentPane().add(icon);

		separator_1 = new JSeparator();
		separator_1.setBounds(22, 88, 307, 2);
		getContentPane().add(separator_1);

		separator_2 = new JSeparator();
		separator_2.setBounds(22, 258, 307, 2);
		getContentPane().add(separator_2);
		
		setSize(359,339);
		setResizable(false);
		setVisible(true);  
		setLocationRelativeTo(null);
	}

	//Manejador de eventos sobre los botones
	private class ManejadorDeBotones implements ActionListener{

		public void actionPerformed(ActionEvent actionEvent){
			if (actionEvent.getSource() == registrarUsuario){
				JFrame formulario1 = new GUIFormulario(bd);
				formulario1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			} 
			else if (actionEvent.getSource() == actualizarUsuario){
				JFrame formulario2 = new GUIActualizar(bd);
				formulario2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
			else if (actionEvent.getSource() == mostrarUsuarios){
				JFrame formulario3 = new GUIConsultarEmpleados(bd);
				formulario3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
			else if (actionEvent.getSource() == salir){
				System.exit(0);
			}
		}

	}
}
