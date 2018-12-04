import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.event.ActionEvent;


/**
 * Esta clase permite mostrar una interfaz sencilla para la consulta
 * de empleados en la base de datos de la empresa.
 * 
 *  Los criterios de la consulta son: 'Id', 'Nombres' o 'Apellidos'.
 *  
 *  El resultado de la consulta se muestra en una tabla.
 */
 
@SuppressWarnings("serial")
public class GUIConsultarEmpleados extends JFrame {

	private BaseDeDatos bd;
	private JLabel icon;
	private JTextField busqueda;
	private JComboBox<String> criterio;
	private JButton salir, consultar;
	private JSeparator separator_1, separator_2;
	private Font font;
	private ActionListener listener; 

	
	//Creacion y despliegue de la interfaz de consulta
	public GUIConsultarEmpleados(BaseDeDatos bdIn) {

		bd = bdIn;
		font = new Font("Tahoma", Font.PLAIN, 14);
		getContentPane().setLayout(null);
		listener = new ManejadorDeBotones();

		icon = new JLabel("");
		URL filePath = this.getClass().getResource("/images/search.png");
		icon.setIcon(new ImageIcon(filePath));
		icon.setBounds(21, 23, 66, 66);
		getContentPane().add(icon);

		consultar = new JButton("Consultar");
		consultar.setFont(font);
		consultar.setBounds(433, 112, 101, 32);
		consultar.addActionListener(listener);
		getContentPane().add(consultar);

		busqueda = new JTextField();
		busqueda.setFont(font);
		busqueda.setBounds(132, 113, 295, 32);
		getContentPane().add(busqueda);
		busqueda.setColumns(10);

		criterio = new JComboBox<>();
		criterio.setFont(font);
		String[] lista = new String[] {"Id", "Nombres", "Apellidos"};
		criterio.setModel(new DefaultComboBoxModel<String>(lista));
		criterio.setBounds(31, 113, 91, 32);
		getContentPane().add(criterio);

		separator_1 = new JSeparator();
		separator_1.setBounds(31, 100, 503, 4);
		getContentPane().add(separator_1);

		separator_2 = new JSeparator();
		separator_2.setBounds(21, 181, 513, 4);
		getContentPane().add(separator_2);

		salir = new JButton("Salir");
		salir.setFont(font);
		salir.setBounds(445, 192, 89, 32);
		salir.addActionListener(listener);
		getContentPane().add(salir);

		setSize(561,264);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
	}


	//Manejador de eventos sobre los botones del menu de consulta
	private class ManejadorDeBotones implements ActionListener{

		public void actionPerformed(ActionEvent actionEvent){
			if (actionEvent.getSource() == consultar){
				String[][] resultado = bd.consultarUsuarios( 
						(String)criterio.getSelectedItem(),	(String)busqueda.getText());
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
			JFrame frame = new JFrame();
			String column[]={"Usuario", "id", "Nombres", "Apellidos", "Direccion" ,"Telefono",
					"Correo electronico" , "Tipo de usuario", "Sede", "Activo"};
			JTable table = new JTable(board,column); 
			table.setRowMargin(0); 
			table.setFont(font);
			JScrollPane sp = new JScrollPane(table);
			frame.getContentPane().add(sp);	
			frame.setSize(1200,400); 
			frame.setVisible(true);
		}
	}
}





