import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.GroupLayout.Alignment;

public class GUIRegistrarProd extends JFrame {

    //Variables y componentes de la GUI
    private final Font font;
    private final BaseDeDatos bd;
    private JSeparator separador;
    private Container contenedor;
    private JLabel  precio, descripcion,nombre, icono, titulo, fondoAzul, fondoGris,costo;
    private JTextField  precioIn, descripcionIn,nombreIn,costoIn;
    private JButton cancelar, crear;
    private int pX, pY;

    //Constructor
    public GUIRegistrarProd(BaseDeDatos bdIn) {
        super("CREAR PRODUCTO"); 
        this.setUndecorated(true);
        font = new Font("Tahoma", Font.PLAIN, 14); 
        bd = bdIn;
		crearComponentes();
	}


	//Funcion que crea la interfaz y sus componentes
	private void crearComponentes() {

		//Configuraciones de la ventana principal
		contenedor = getContentPane();
		contenedor.removeAll();
		getContentPane().setLayout(null);
		ActionListener listener = new ManejadorDeBotones();
		manejadorDesplazamientoVentana(this);
		JPanel panelUsuario = new JPanel();
		panelUsuario.setBackground(Color.BLACK);
		panelUsuario.setBounds(0, 0, 400, 406);
		contenedor.add(panelUsuario);
		panelUsuario.setLayout(null);
		listener = new ManejadorDeBotones();


        nombre = new JLabel("Nombre: ");
        nombre.setFont(font);
        nombre.setBounds(21, 140, 105, 32);
        panelUsuario.add(nombre);

        nombreIn = new JTextField();
        nombreIn.setFont(font);
        nombreIn.setBounds(136, 141, 234, 32);
        panelUsuario.add(nombreIn);

        costo = new JLabel("Costo: ");
        costo.setFont(font);
        costo.setBounds(21, 183, 105, 32);
        panelUsuario.add(costo);

        costoIn = new JTextField();
        costoIn.setFont(font);
        costoIn.setBounds(136, 184, 234, 32);
        panelUsuario.add(costoIn);

        precio = new JLabel("Precio:");
        precio.setFont(font);
        precio.setBounds(21, 226, 105, 32);
        panelUsuario.add(precio);

        precioIn = new JTextField();
        precioIn.setFont(font);
        precioIn.setBounds(136, 227, 234, 32);
        panelUsuario.add(precioIn);

        descripcion = new JLabel("Descripcion:");
        descripcion.setFont(font);
        descripcion.setBounds(21, 266, 105, 32);
        panelUsuario.add(descripcion);

        descripcionIn = new JTextField();
        descripcionIn.setFont(font);
        descripcionIn.setBounds(136, 266, 234, 32);
        panelUsuario.add(descripcionIn);

        cancelar = new JButton("Cancelar");
		cancelar.setOpaque(true);
		cancelar.setBackground(new Color(227, 227, 227));
        cancelar.setFont(font);
        cancelar.setBounds(92, 322, 120, 28);
        cancelar.addActionListener(listener);
        panelUsuario.add(cancelar);

        crear = new JButton("Crear Producto");
		crear.setOpaque(true);
		crear.setBackground(new Color(227, 227, 227));
        crear.setFont(font);
        crear.setBounds(222, 322, 148, 28);
        crear.addActionListener(listener);
        panelUsuario.add(crear);

        separador = new JSeparator();
        separador.setBounds(21, 309, 349, 2);
        panelUsuario.add(separador);

      //Icono a la izquierda del titulo
		icono = new JLabel("");
		icono.setIcon(new ImageIcon(GUIRegistrarUser.class.getResource("/images/titulo_flecha.png")));
		icono.setBounds(11, 1, 48, 90);
		panelUsuario.add(icono);

		//Etiqueta titulo de la ventana
		titulo = new JLabel("REGISTRAR PRODUCTO");
		titulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		titulo.setForeground(Color.WHITE);
		titulo.setBounds(61, 30, 211, 32);
		panelUsuario.add(titulo);

		// -- Fondos azul y gris -- //
		fondoAzul = new JLabel("");
		fondoAzul.setBounds(1, 1, 398, 90);
		fondoAzul.setOpaque(true);
		fondoAzul.setBackground(new Color(45, 118, 232));
		panelUsuario.add(fondoAzul);
		fondoGris = new JLabel("");
		fondoGris.setBounds(1, 89, 398, 316);
		fondoGris.setOpaque(true);
		fondoGris.setBackground(new Color(227,227,227));
		panelUsuario.add(fondoGris);

		//Configuraciones adicionales de la ventana principal
        setResizable(false);
        setSize(400, 406);
        setVisible(true);
        setLocationRelativeTo(null);
    }


    //Funcion que valida si algun campo a registrar esta vacio
    private boolean validar1(){
        boolean val=true;
        val = (costoIn.getText().compareTo("")==0 ||
                precioIn.getText().compareTo("")==0 ||
                nombreIn.getText().compareTo("")==0) ? false : true;

        return val;
    }

    //Funcion para validar el dominio de los datos ingresados
    private String validar2(){
        String mensaje = "";


        Pattern patron = Pattern.compile("[^A-Za-z0-9 #-]");
        Matcher nombre = patron.matcher(nombreIn.getText());
        if(nombre.find()|| nombreIn.getText().length()>20)
            mensaje = mensaje + " Digite un nombre valido \n";

        Matcher direccion = patron.matcher(descripcionIn.getText());
        if(direccion.find()|| descripcionIn.getText().length()>40)
            mensaje = mensaje + " Digite una descripcion valida \n";

        patron = Pattern.compile("[^0-9]");
        Matcher pre = patron.matcher(precioIn.getText());
        if(pre.find())
            mensaje = mensaje + " Digite un precio valido \n";

        Matcher cos = patron.matcher(costoIn.getText());
        if(cos.find())
            mensaje = mensaje + " Digite un costo valido \n";

        if(mensaje.compareTo("")==0)
            mensaje="true";

        return mensaje;
    }

    //Manejador de eventos
    private class ManejadorDeBotones implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource() == crear){
                if(validar1()) {
                    if(validar2().compareTo("true")==0) {
                        boolean var;
                        String descrip = "";
                        if (descripcionIn.getText().compareTo("")!=0)descrip=descripcionIn.getText();

                        var= bd.registrarProducto(nombreIn.getText(),costoIn.getText() ,precioIn.getText(),descrip);
                        if (var) {
                            JOptionPane.showMessageDialog(null, "Producto creado exitosamente");

                        } else JOptionPane.showMessageDialog(null, "Error al crear producto.");

                        GUIMenuAdmin menu = new GUIMenuAdmin(bd);
                        menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        dispose();
                    }
                    else JOptionPane.showMessageDialog(null, validar2());
                }
                else JOptionPane.showMessageDialog(null, "Digite todos los campos");
            }

            if (actionEvent.getSource() == cancelar){
                GUIMenuAdmin menu = new GUIMenuAdmin(bd);
                menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
