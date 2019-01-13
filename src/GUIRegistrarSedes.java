import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressWarnings("serial")
public class GUIRegistrarSedes extends JFrame{

	private Container contenedor;
	private JLabel id, direccion, telefono, idEncargado;
	private JTextField idIn, direccionIn, telefonoIn, idEncargadoIn;
	private JButton registrar, cancelar;
	private JSeparator separator_1, separator_2;
	private Font font;
	private BaseDeDatos bd;
	private ActionListener listener;

	public GUIRegistrarSedes(BaseDeDatos bdIn){
		setResizable(false);
		setTitle("REGISTRAR SEDE");

		font = new Font("Tahoma", Font.PLAIN, 14);
		crearComponentes();
		bd = bdIn;
	}


	private void crearComponentes() {

		contenedor = getContentPane();
		contenedor.removeAll();
		getContentPane().setLayout(null);

		JPanel panelUsuario = new JPanel();
		panelUsuario.setBounds(0, 0, 394, 575);
		contenedor.add(panelUsuario);
		panelUsuario.setLayout(null);

		id = new JLabel("Id:");
		id.setFont(font);
		id.setBounds(21, 101, 105, 32);
		panelUsuario.add(id);

		idIn = new JTextField();
		idIn.setFont(font);
		idIn.setBounds(136, 101, 234, 32);
		panelUsuario.add(idIn);

		direccion = new JLabel("Direccion:");
		direccion.setFont(font);
		direccion.setBounds(21, 143, 105, 32);
		panelUsuario.add(direccion);

		direccionIn = new JTextField();
		direccionIn.setFont(font);
		direccionIn.setBounds(136, 144, 234, 32);
		panelUsuario.add(direccionIn);

		telefono =  new JLabel("Telefono:");
		telefono.setFont(font);
		telefono.setBounds(21, 186, 105, 32);
		panelUsuario.add(telefono);

		telefonoIn = new JTextField();
		telefonoIn.setFont(font);
		telefonoIn.setBounds(136, 187, 234, 32);
		panelUsuario.add(telefonoIn);

		idEncargado = new JLabel("Id Encargado:");
		idEncargado.setFont(font);
		idEncargado.setBounds(21, 229, 105, 32);
		panelUsuario.add(idEncargado);

		idEncargadoIn = new JTextField();
		idEncargadoIn.setFont(font);
		idEncargadoIn.setBounds(136, 230, 234, 32);
		panelUsuario.add(idEncargadoIn);

		listener = new ManejadorDeBotones();

		cancelar = new JButton("Cancelar");
		cancelar.setFont(font);
		cancelar.setBounds(120, 317, 113, 28);
		cancelar.addActionListener(listener);
		panelUsuario.add(cancelar);

		registrar = new JButton("Registrar Sede");
		registrar.setFont(font);
		registrar.setBounds(243, 317, 127, 28);
		registrar.addActionListener(listener);
		panelUsuario.add(registrar);

		separator_2 = new JSeparator();
		separator_2.setBounds(21, 304, 349, 2);
		panelUsuario.add(separator_2);

		separator_1 = new JSeparator();
		separator_1.setBounds(21, 88, 349, 2);
		panelUsuario.add(separator_1);

		JLabel lblNewLabel = new JLabel("");
		URL filePath = this.getClass().getResource("/images/sede.png");
		lblNewLabel.setIcon(new ImageIcon(filePath));
		lblNewLabel.setBounds(21, 11, 66, 66);
		panelUsuario.add(lblNewLabel);
		setSize(400,390);
		setVisible(true);
		setLocationRelativeTo(null);
	}


	private class ManejadorDeBotones implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			if(actionEvent.getSource() == cancelar){
				dispose();
			}

			else if(actionEvent.getSource() == registrar){
				if (bd.verificarIdSede( Integer.parseInt(idIn.getText()) )) 
					JOptionPane.showMessageDialog(null, "El Id ingresado ya esta siendo utilizado para otra sede", null, 1);
				else if (!bd.obtenerB(Integer.parseInt(idEncargadoIn.getText()), "active")) { 
					JOptionPane.showMessageDialog(null, 
							"El empleado con el id "+idIn.getText()+" no esta activo", null, 1);
				}
				else { //conexion a la bd para registrar la sede
					if(bd.registraSede( idIn.getText(), direccionIn.getText(), 
							telefonoIn.getText(), idEncargadoIn.getText() )) {
						JOptionPane.showMessageDialog(null, 
								"La sede ha sido regitrada satisfactoriamente", null, 1);
						dispose();
					}
					else JOptionPane.showMessageDialog(null, "no se pudo realizar la accion", "operacion", 1);
				}
			}
		}
	}
}



