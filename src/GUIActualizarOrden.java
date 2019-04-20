import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("serial")
public class GUIActualizarOrden extends JFrame {

	private Container contenedor;
	private JLabel instruccion, fechaEntrega, idOrden, cantidad, idEncargado, idProducto;
	private JTextField fechaEntregaIn, cantidadIn, asignadoAIn, idIn, idEncargadoIn;
	private JComboBox<String> idProductoIn, idOrdenIn;
	private JButton actualizarOrden, salir, salir2, buscar, cancelar, finalizar;
	private JSeparator separadorInferior;
	private Font font;
	private BaseDeDatos bd;
	private ActionListener listener;
	private String idJefe;
	private int pX, pY;

	
	public GUIActualizarOrden(BaseDeDatos bdIn, String idJefe) {
		getContentPane().setBackground(Color.BLACK);
		font = new Font("Tahoma", Font.PLAIN, 14);
		this.idJefe = idJefe;
		this.bd = bdIn;
		initGUI();
	}

	// FUnciona para inicializar los elementos graficos
	private void initGUI() {

		// Configuraciones generales de la primer ventana
		this.setUndecorated(true);
		contenedor = getContentPane();
		contenedor.removeAll();
		getContentPane().setLayout(null);
		listener = new ManejadorDeBotones();

		// Etiqueta que describe el funcionamiento
		instruccion = new JLabel("ID orden a modificar");
		instruccion.setFont(font);
		instruccion.setBounds(22, 106, 138, 32);
		getContentPane().add(instruccion);

		idOrdenIn = new JComboBox<>(
        		bd.cambiarDimension(bd.consultarOrden("User",idJefe,"id_ordenes,nombre"))
		);
		idOrdenIn.setFont(font);
		idOrdenIn.setBounds(170, 106, 239, 32);
		getContentPane().add(idOrdenIn);

		salir = new JButton("Salir");
		salir.setOpaque(true);
		salir.setBackground(new Color(227, 227, 227));
		salir.setFont(font);
		salir.setBounds(419, 189, 100, 32);
		salir.addActionListener(listener);
		getContentPane().add(salir);

		buscar = new JButton("Buscar");
		buscar.setOpaque(true);
		buscar.setBackground(new Color(227, 227, 227));
		buscar.setBounds(419, 106, 100, 32);
		buscar.setFont(font);
		buscar.addActionListener(listener);
		getContentPane().add(buscar);

		JSeparator separator = new JSeparator();
		separator.setBounds(22, 177, 497, 2);
		getContentPane().add(separator);

		// Icono a la izquierda del titulo
		JLabel icono = new JLabel("");
		icono.setIcon(new ImageIcon(GUIConsultarUser.class.getResource("/images/actualizar.png")));
		icono.setBounds(11, 1, 48, 90);
		getContentPane().add(icono);

		// Etiqueta del titulo de la ventana
		JLabel titulo = new JLabel("ACTUALIZAR ORDEN DE TRABAJO");
		titulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		titulo.setForeground(Color.WHITE);
		titulo.setBounds(69, 28, 340, 32);
		getContentPane().add(titulo);

		// -- Fondos azul y gris -- //
		JLabel fondoAzul = new JLabel("");
		fondoAzul.setBounds(1, 1, 560, 90);
		fondoAzul.setOpaque(true);
		fondoAzul.setBackground(new Color(45, 118, 232));
		getContentPane().add(fondoAzul);
		JLabel fondoGris = new JLabel("");
		fondoGris.setBounds(1, 89, 560, 166);
		fondoGris.setOpaque(true);
		fondoGris.setBackground(new Color(227, 227, 227));
		getContentPane().add(fondoGris);

		setResizable(false);
		setSize(562, 256);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	// Funcion para inicializar la interfaz grafica para
	// realizar la actualizacion de una orden de trabajo
	private void crearComponentes(String identifier) {

		Container ventana = getContentPane();
		ventana.setLayout(null);
		manejadorDesplazamientoVentana(this);
		ventana.removeAll();
		this.setUndecorated(true);

		idOrden = new JLabel("Id Orden:");
		idOrden.setFont(font);
		idOrden.setBounds(27, 133, 105, 32);
		ventana.add(idOrden);

		idIn = new JTextField(identifier);
		idIn.setFont(font);
		idIn.setEditable(false);
		idIn.setBounds(161, 133, 224, 32);
		ventana.add(idIn);

		idProducto = new JLabel("Id-Producto:");
		idProducto.setFont(font);
		idProducto.setBounds(27, 175, 105, 32);
		ventana.add(idProducto);

		idProductoIn = new JComboBox<>(
        		bd.cambiarDimension(bd.consultarProductos("id_producto, nombre"))
				);
		idProductoIn.setSelectedItem(bd.cambiarDimension(
                bd.consultarOrden("Id",identifier,"id_producto,nombre"))[0]
                		);
		idProductoIn.setFont(font);
		idProductoIn.setBounds(161, 176, 224, 32);
		ventana.add(idProductoIn);

		fechaEntrega = new JLabel("Fecha de Entrega:");
		fechaEntrega.setFont(font);
		fechaEntrega.setBounds(27, 218, 127, 32);
		ventana.add(fechaEntrega);

		fechaEntregaIn = new JTextField(bd.consultarOrden("Id", identifier, "fecha_entrega")[0][0]);
		fechaEntregaIn.setFont(font);
		fechaEntregaIn.setBounds(161, 218, 224, 32);
		ventana.add(fechaEntregaIn);

		cantidad = new JLabel("Cantidad:");
		cantidad.setFont(font);
		cantidad.setBounds(27, 261, 105, 32);
		ventana.add(cantidad);

		cantidadIn = new JTextField(bd.consultarOrden("Id", identifier, "cantidad")[0][0]);
		cantidadIn.setFont(font);
		cantidadIn.setBounds(161, 262, 224, 32);
		ventana.add(cantidadIn);

		idEncargado = new JLabel("Id Encargado:");
		idEncargado.setFont(font);
		idEncargado.setBounds(27, 304, 105, 32);
		ventana.add(idEncargado);

		idEncargadoIn = new JTextField(identifier);
		idEncargadoIn.setEditable(false);
		idEncargadoIn.setBounds(161, 306, 224, 32);
		idEncargadoIn.setFont(font);
		ventana.add(idEncargadoIn);

		salir2 = new JButton("Salir");
		salir2.setOpaque(true);
		salir2.setBackground(new Color(227, 227, 227));
		salir2.setFont(font);
		salir2.setBounds(272, 521, 113, 32);
		salir2.addActionListener(listener);
		ventana.add(salir2);

		actualizarOrden = new JButton("Actualizar");
		actualizarOrden.setOpaque(true);
		actualizarOrden.setBackground(new Color(227, 227, 227));
		actualizarOrden.setFont(font);
		actualizarOrden.setBounds(150, 447, 113, 32);
		actualizarOrden.addActionListener(listener);
		ventana.add(actualizarOrden);
		
		JSeparator separatorSuperior = new JSeparator();
		separatorSuperior.setBounds(231, 426, 154, 2);
		getContentPane().add(separatorSuperior);

		separadorInferior = new JSeparator();
		separadorInferior.setBounds(29, 508, 356, 2);
		ventana.add(separadorInferior);

		JLabel asignadoA = new JLabel("Asignado a:");
		asignadoA.setFont(new Font("Tahoma", Font.PLAIN, 14));
		asignadoA.setBounds(27, 347, 105, 32);
		ventana.add(asignadoA);

		asignadoAIn = new JTextField(bd.consultarOrden("Id", identifier, "asignada_a")[0][0]);
		asignadoAIn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		asignadoAIn.setBounds(161, 348, 224, 32);
		ventana.add(asignadoAIn);

		finalizar = new JButton("Finalizar");
		finalizar.setOpaque(true);
		finalizar.setBackground(new Color(227, 227, 227));
		finalizar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		finalizar.setBounds(272, 447, 113, 32);
		finalizar.addActionListener(listener);
		ventana.add(finalizar);

		cancelar = new JButton("Cancelar");
		cancelar.setOpaque(true);
		cancelar.setBackground(new Color(227, 227, 227));
		cancelar.setBounds(27, 447, 113, 32);
		cancelar.setFont(font);
		cancelar.addActionListener(listener);
		ventana.add(cancelar);
		
		JLabel accionSobreLaOrden = new JLabel("Accion sobre la orden de trabajo");
		accionSobreLaOrden.setFont(new Font("Tahoma", Font.PLAIN, 14));
		accionSobreLaOrden.setBounds(27, 409, 207, 32);
		getContentPane().add(accionSobreLaOrden);

		// Icono a la izquierda del titulo
		JLabel icono = new JLabel("");
		icono.setIcon(new ImageIcon(GUIRegistrarUser.class.getResource("/images/actualizar.png")));
		icono.setBounds(11, 1, 48, 90);
		ventana.add(icono);

		// Etiqueta titulo de la ventana
		JLabel titulo = new JLabel("  MODIFICAR ORDEN DE TRABAJO");
		titulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		titulo.setForeground(Color.WHITE);
		titulo.setBounds(61, 30, 299, 32);
		ventana.add(titulo);

		// -- Fondos azul y gris -- //
		JLabel fondoAzul = new JLabel("");
		fondoAzul.setBounds(1, 1, 421, 90);
		fondoAzul.setOpaque(true);
		fondoAzul.setBackground(new Color(45, 118, 232));
		ventana.add(fondoAzul);
		JLabel fondoGris = new JLabel("");
		fondoGris.setBounds(1, 89, 421, 487);
		fondoGris.setOpaque(true);
		fondoGris.setBackground(new Color(227, 227, 227));
		ventana.add(fondoGris);

		// Configuraciones adicionales de la ventana principal
		setResizable(false);
		setSize(423, 577);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	// Funcion para validar el dominio de los datos ingresados
	private String validar2() {
		String mensaje = "";

		if (!validarDatoEnteroPositivo(cantidadIn))
			mensaje = mensaje + " La cantidad debe ser un numero entero \n";

		Pattern patron = Pattern.compile("[^A-Za-z ]");
		Matcher nombre = patron.matcher(asignadoAIn.getText());
		if (nombre.find() || asignadoAIn.getText().length() > 40)
			mensaje = mensaje + " Digite un nombre valido \n";

		Pattern patron_fecha = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}");
		Matcher fecha = patron_fecha.matcher(fechaEntregaIn.getText());
		if (!fecha.find())
			mensaje = mensaje + " Digite una fecha valida (AAAA-MM-DD) \n";

		if (mensaje.compareTo("") == 0)
			mensaje = "true";

		return mensaje;
	}

	// Funcion que valida si un dato ingresado a traves de un JTextField es entero
	// positivo
	private boolean validarDatoEnteroPositivo(JTextField dato) {
		boolean val = true;
		try {
			Integer.parseInt(dato.getText());

		} catch (NumberFormatException excepcion) {
			val = false;
		}
		val = (Integer.parseInt(dato.getText()) >= 0) ? true : false;
		return val;
	}

	// Manejador de eventos para los botones del apartado Crear-Orden-de-Trabajo
	// Si se presiona <salir>, la interfaz del menu se cierra
	// Si se presiona <crear orden>, se valida que:
	// no hayan campos vacios, los datos esten dentro del dominio y
	// las id ingresadas esten disponibles
	private class ManejadorDeBotones implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == salir || e.getSource() == salir2) {
				dispose();
				return;
			}

			String identifier = idOrdenIn.getSelectedItem().toString();
			identifier = identifier.substring(0, identifier.indexOf("-"));

			if (e.getSource() == buscar) {
				dispose();
				crearComponentes(identifier);
			}

			String idProducto = idProductoIn.getSelectedItem().toString();
			idProducto = idProducto.substring(0, idProducto.indexOf("-"));

			if (e.getSource() == actualizarOrden) {
				if (validar2().compareTo("true") == 0) {

					if (bd.actualizarOrden(identifier, fechaEntregaIn.getText(), cantidadIn.getText(), idProducto,
							identifier, asignadoAIn.getText(), "false")) {
						JOptionPane.showMessageDialog(null, "Orden de trabajo actualizada exitosamente");
					} else
						JOptionPane.showMessageDialog(null, "Error al actualizar orden de trabajo.");
					dispose();
				} else
					JOptionPane.showMessageDialog(null, validar2());
			}

			if (e.getSource() == finalizar) {
				if (bd.finalizarOrden(identifier, idProducto, bd.obtenerS(idJefe, "sede"),
						Integer.parseInt(cantidadIn.getText().trim()))) {

					JOptionPane.showMessageDialog(null, "Orden de trabajo finalizada exitosamente");
				} else
					JOptionPane.showMessageDialog(null, "Error al finalizar orden de trabajo.");
				dispose();
			}

			if (e.getSource() == cancelar) {
				if (bd.cancelarOrden(identifier)) {

					JOptionPane.showMessageDialog(null, "Orden de trabajo cancelada");
				} else
					JOptionPane.showMessageDialog(null, "Error al cancelar orden de trabajo.");
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
