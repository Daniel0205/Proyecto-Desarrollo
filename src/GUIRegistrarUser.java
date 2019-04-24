import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressWarnings("serial")
public class GUIRegistrarUser extends JFrame {

    //Variables y componentes de la GUI
	private Container contenedor;
	private JLabel id, nombre, apellido, direccion, celular;
	private JLabel eMail, contrasena, tipoEmpleado, sede;
	private JTextField nombreIn, apellidoIn, idIn;
	private JTextField direccionIn, celularIn, eMailIn;
	private JComboBox<String> tipoEmpleadoIn, sedeIn;
	private JPasswordField contrasenaIn;
	private JButton crear, cancelar;
	private JSeparator separator;
	private Font font;
	private BaseDeDatos bd;
	private JLabel fondoAzul, fondoGris, icono, titulo;
	private int pX,pY;

	//Constructor
	public GUIRegistrarUser(BaseDeDatos bdIn){
		super("CREAR EMPLEADO");
		this.setUndecorated(true);
		font = new Font("Tahoma", Font.PLAIN, 14);
        bd = bdIn;
		crearComponentes();
	}

    //Inicializa los componentes de la GUI
    private void crearComponentes() {

		String[] sedes = bd.cambiarDimension(bd.consultarSede(null,"id_Sede,nombre"));

		if(sedes.length!=0) {
            //Configuraciones de la ventana principal
            contenedor = getContentPane();
            contenedor.removeAll();
            getContentPane().setLayout(null);
            ActionListener listener = new ManejadorDeBotones();
            manejadorDesplazamientoVentana(this);
            JPanel panelUsuario = new JPanel();
            panelUsuario.setBackground(Color.BLACK);
            panelUsuario.setBounds(0, 0, 400, 602);
            contenedor.add(panelUsuario);
            panelUsuario.setLayout(null);

            nombre = new JLabel("Nombre:");
            nombre.setFont(font);
            nombre.setBounds(22, 138, 105, 32);
            panelUsuario.add(nombre);

            nombreIn = new JTextField();
            nombreIn.setFont(font);
            nombreIn.setBounds(137, 138, 234, 32);
            panelUsuario.add(nombreIn);

            apellido = new JLabel("Apellidos:");
            apellido.setFont(font);
            apellido.setBounds(22, 177, 105, 32);
            panelUsuario.add(apellido);

            apellidoIn = new JTextField();
            apellidoIn.setFont(font);
            apellidoIn.setBounds(137, 178, 234, 32);
            panelUsuario.add(apellidoIn);

            id = new JLabel("Id/Documento:");
            id.setFont(font);
            id.setBounds(22, 220, 105, 32);
            panelUsuario.add(id);

            idIn = new JTextField();
            idIn.setFont(font);
            idIn.setBounds(137, 221, 234, 32);
            panelUsuario.add(idIn);

            contrasena = new JLabel("Contrasena:");
            contrasena.setFont(font);
            contrasena.setBounds(22, 263, 105, 32);
            panelUsuario.add(contrasena);

            contrasenaIn = new JPasswordField();
            contrasenaIn.setFont(font);
            contrasenaIn.setBounds(137, 264, 234, 32);
            panelUsuario.add(contrasenaIn);

            direccion = new JLabel("Direccion:");
            direccion.setFont(font);
            direccion.setBounds(22, 306, 105, 32);
            panelUsuario.add(direccion);

            direccionIn = new JTextField();
            direccionIn.setFont(font);
            direccionIn.setBounds(137, 307, 234, 32);
            panelUsuario.add(direccionIn);

            celular = new JLabel("Celular:");
            celular.setFont(font);
            celular.setBounds(22, 349, 105, 32);
            panelUsuario.add(celular);

            celularIn = new JTextField();
            celularIn.setFont(font);
            celularIn.setBounds(137, 350, 234, 32);
            panelUsuario.add(celularIn);

            eMail = new JLabel("E-Mail:");
            eMail.setFont(font);
            eMail.setBounds(22, 392, 105, 32);
            panelUsuario.add(eMail);

            eMailIn = new JTextField();
            eMailIn.setFont(font);
            eMailIn.setBounds(137, 393, 234, 32);
            panelUsuario.add(eMailIn);

            tipoEmpleado = new JLabel("Tipo de Empleado:");
            tipoEmpleado.setFont(font);
            tipoEmpleado.setBounds(22, 442, 138, 32);
            panelUsuario.add(tipoEmpleado);

            String[] listaTipo = new String[]{"Jefe de taller", "Vendedor"};
            tipoEmpleadoIn = new JComboBox<>(listaTipo);
            tipoEmpleadoIn.setEditable(false);
            tipoEmpleadoIn.setFont(font);
            tipoEmpleadoIn.setBounds(170, 443, 201, 32);
            panelUsuario.add(tipoEmpleadoIn);

            sede = new JLabel("Sede:");
            sede.setFont(font);
            sede.setBounds(22, 485, 138, 32);
            panelUsuario.add(sede);

            sedeIn = new JComboBox<>(sedes);
            sedeIn.setEditable(false);
            sedeIn.setFont(font);
            sedeIn.setBounds(170, 486, 201, 32);
            panelUsuario.add(sedeIn);

            cancelar = new JButton("Cancelar");
            cancelar.setOpaque(true);
            cancelar.setBackground(new Color(227, 227, 227));
            cancelar.setFont(font);
            cancelar.setBounds(121, 563, 120, 28);
            cancelar.addActionListener(listener);
            panelUsuario.add(cancelar);

            crear = new JButton("Crear Usuario");
            crear.setOpaque(true);
            crear.setBackground(new Color(227, 227, 227));
            crear.setFont(font);
            crear.setBounds(251, 563, 120, 28);
            crear.addActionListener(listener);
            panelUsuario.add(crear);

            //Separador inferior
            separator = new JSeparator();
            separator.setBounds(22, 550, 349, 2);
            panelUsuario.add(separator);

            //Icono a la izquierda del titulo
            icono = new JLabel("");
            icono.setIcon(new ImageIcon(GUIRegistrarUser.class.getResource("/images/titulo_flecha.png")));
            icono.setBounds(11, 1, 48, 90);
            panelUsuario.add(icono);

            //Etiqueta titulo de la ventana
            titulo = new JLabel("REGISTRAR USUARIO");
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
            fondoGris.setBounds(1, 89, 398, 512);
            fondoGris.setOpaque(true);
            fondoGris.setBackground(new Color(227, 227, 227));//Gris
            panelUsuario.add(fondoGris);

            //Configuraciones adicionales de la ventana principal
            setSize(400, 602);
            setVisible(true);
            setResizable(false);
            setLocationRelativeTo(null);
        }
        else{
            JOptionPane.showMessageDialog(null, "Actualmente no hay productos para crear ordenes");
        }
	}

    //Funcion que valida si algun campo a registrar esta vacio
	private boolean validar(){

        if(nombreIn.getText().compareTo("")==0){
            return false;
        }
        if(apellidoIn.getText().compareTo("")==0){
            return false;
        }
        if(idIn.getText().compareTo("")==0){
            return false;
        }
        if(direccionIn.getText().compareTo("")==0){
            return false;
        }
        if(celularIn.getText().compareTo("")==0){
            return false;
        }
        if(eMailIn.getText().compareTo("")==0){
            return false;
        }
        String p = new String(contrasenaIn.getPassword());
        if(p.compareTo("")==0){
            return false;
        }
        return true;
    }

    //Funcion para validar el dominio de los datos ingresados
    private String validar2(){
        String mensaje = "";

        Pattern patron = Pattern.compile("[^A-Za-z ]");
        Matcher nombre = patron.matcher(nombreIn.getText());
        Matcher apellido = patron.matcher(apellidoIn.getText());


        if(nombre.find() || nombreIn.getText().length()>30){
            mensaje = mensaje +" Digite un nombre válido \n";
        }
        if(apellido.find() || apellidoIn.getText().length()>30){
            mensaje = mensaje +" Digite un apellido válido \n";
        }

        Pattern patron1 = Pattern.compile("[^0-9]");
        Matcher usuario = patron1.matcher(idIn.getText());

        if(usuario.find() || idIn.getText().length()>30){
            mensaje = mensaje +" Digite un usuario valido \n";
        }
        patron1 = Pattern.compile("[^A-Za-z0-9_]");
        Matcher pass = patron1.matcher(new String(contrasenaIn.getPassword()));

        if(pass.find()){
            mensaje = mensaje +" Digite una contrasena valida \n";
        }

        Pattern patron2 = Pattern.compile("[^A-Za-z0-9 #-]");
        Matcher direccion = patron2.matcher(direccionIn.getText());

        if(direccion.find()|| direccionIn.getText().length()>40) {
            mensaje = mensaje + " Digite una direccion valida \n";
        }

         patron2 = Pattern.compile("[^0-9]");
        Matcher cel = patron2.matcher(celularIn.getText());

        if(cel.find()|| celularIn.getText().length()>40|| celularIn.getText().length()<7) {
            mensaje = mensaje + " Digite un telefono celular valido \n";
        }

        if(!eMailIn.getText().matches("([^@])+@([^@])+[\\p{Punct}&&[^@]](.[a-z]{1,4})*")){
            mensaje = mensaje + " Digite un E-mail valido \n";
        }

        if(mensaje.compareTo("")==0){
            mensaje="true";
        }
        return mensaje;
    }

    //Clase para manejar los eventos de los botones en la interfaz grafic
    private class ManejadorDeBotones implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            if (actionEvent.getSource() == crear) {
                if (validar()) {
                    if (validar2().compareTo("true") == 0) {
                        String idSede = sedeIn.getSelectedItem().toString();
                        idSede=idSede.substring(0,idSede.indexOf("-"));
                        System.out.print(idSede);

                        boolean var = bd.insertarUsuario(idIn.getText(), new String(contrasenaIn.getPassword()),
                                nombreIn.getText(), apellidoIn.getText(), direccionIn.getText(),
                                eMailIn.getText(), (String) tipoEmpleadoIn.getSelectedItem(),
                                idSede, celularIn.getText());
                        if (var) {
                            JOptionPane.showMessageDialog(null, "Usuario creado exitosamente");

                        } else JOptionPane.showMessageDialog(null, "Error al crear usuario.");

                        GUIMenuAdmin menu = new GUIMenuAdmin(bd);
                        menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        dispose();

                    } else JOptionPane.showMessageDialog(null, validar2());

                } else JOptionPane.showMessageDialog(null, "Debe llenar todas los campos");
            }
            if (actionEvent.getSource() == cancelar) {
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
