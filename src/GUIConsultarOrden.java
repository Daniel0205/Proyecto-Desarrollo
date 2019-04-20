import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class GUIConsultarOrden extends JFrame {
	private BaseDeDatos bd;
	private JLabel idl, fondoAzul, fondoGris, icono, titulo;
	@SuppressWarnings("rawtypes")
	private JComboBox busqueda;
	private JButton salir, consultar;
	private JSeparator separador;
	private Font font;
	private ActionListener listener;
	private int pX, pY;

	// Creacion y despliegue de la interfaz de consulta
	public GUIConsultarOrden(BaseDeDatos bdIn, String idJefe) {

		// Configuraciones de la ventana principal
		getContentPane().setBackground(Color.BLACK);
		bd = bdIn;
		font = new Font("Tahoma", Font.PLAIN, 14);
		getContentPane().setLayout(null);
		this.setUndecorated(true);
		listener = new ManejadorDeBotones();
		manejadorDesplazamientoVentana(this);

		idl = new JLabel("Id");
		idl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		idl.setBounds(44, 130, 25, 32);
		getContentPane().add(idl);

		//Boton para iniciar el proceso de consulta
		consultar = new JButton("Consultar");
        consultar.setOpaque(true);
		consultar.setBackground(new Color(227, 227, 227));
		consultar.setFont(font);
		consultar.setBounds(423, 130, 101, 32);
		consultar.addActionListener(listener);
		getContentPane().add(consultar);

		busqueda = new JComboBox<>(
    		bd.cambiarDimension(
            bd.consultarOrden("User",idJefe,"id_ordenes,nombre"))
		);
		busqueda.setFont(font);
		busqueda.setBounds(84, 131, 329, 32);
		getContentPane().add(busqueda);

		//Separador inferior
		separador = new JSeparator();
		separador.setBounds(44, 199, 492, 4);
		getContentPane().add(separador);

		//Boton para salir del menu de consulta
		salir = new JButton("Salir");
        salir.setOpaque(true);
		salir.setBackground(new Color(227, 227, 227));
		salir.setFont(font);
		salir.setBounds(435, 210, 89, 32);
		salir.addActionListener(listener);
		getContentPane().add(salir);

		// Icono a la izquierda del titulo
		icono = new JLabel("");
		icono.setIcon(new ImageIcon(GUIConsultarUser.class.getResource("/images/buscar.png")));
		icono.setBounds(11, 1, 48, 90);
		getContentPane().add(icono);

		// Etiqueta del titulo de la ventana
		titulo = new JLabel("BUSCAR ORDEN DE TRABAJO");
		titulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		titulo.setForeground(Color.WHITE);
		titulo.setBounds(69, 28, 282, 32);
		getContentPane().add(titulo);

		// -- Fondos azul y gris -- //
		fondoAzul = new JLabel("");
		fondoAzul.setBounds(1, 1, 559, 90);
		fondoAzul.setOpaque(true);
		fondoAzul.setBackground(new Color(45, 118, 232));
		getContentPane().add(fondoAzul);
		fondoGris = new JLabel("");
		fondoGris.setBounds(1, 89, 559, 174);
		fondoGris.setOpaque(true);
		fondoGris.setBackground(new Color(227, 227, 227));
		getContentPane().add(fondoGris);

		// Configuraciones adicionales de la ventana
		setSize(561, 264);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	// Manejador de eventos sobre los botones del menu de consulta
	private class ManejadorDeBotones implements ActionListener {

		public void actionPerformed(ActionEvent actionEvent) {
			if (actionEvent.getSource() == consultar) {
				String idOrden = busqueda.getSelectedItem().toString();
				idOrden = idOrden.substring(0, idOrden.indexOf("-"));

				String campos = "id_ordenes, asignada_a, precio,fecha_entrega,cantidad,finalizada,id_producto,id_usuario";
				String[][] resultado = bd.consultarOrden(idOrden, campos);
				resultadosConsultaGUI(resultado);
			} else if (actionEvent.getSource() == salir) {
				dispose();
			}
		}
	}

	private void resultadosConsultaGUI(String[][] board) {

		if (board == null || board.length == 0)
			JOptionPane.showMessageDialog(null, "La busqueda no produjo resultados.");

		else {
			JFrame frame = new JFrame();
			String column[] = { "id", "Asignada a", "Precio", "Fecha de entrega", "Cantidad", "Finalizada",
					"id Producto", "id Usuario" };
			JTable table = new JTable(board, column);
			table.setRowMargin(0);
			table.setFont(font);
			JScrollPane sp = new JScrollPane(table);
			frame.getContentPane().add(sp);
			frame.setSize(1200, 400);
			frame.setVisible(true);
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
