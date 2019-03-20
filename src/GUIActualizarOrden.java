import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("serial")
public class GUIActualizarOrden extends JFrame {

    private Container contenedor;
    private JLabel icon,instruccion, fechaEntrega, idOrden, cantidad, idEncargado, idProducto;
    private JTextField fechaEntregaIn, cantidadIn, asignadoAIn ,idIn,idEncargadoIn;
    private JComboBox<String> idProductoIn,idOrdenIn ;
    private JButton actualizarOrden,salir,buscar,cancelar,finalizar;
    private JSeparator separator_1, separator_2;
    private Font font;
    private BaseDeDatos bd;
    private ActionListener listener;
    private String idJefe;


    public GUIActualizarOrden(BaseDeDatos bdIn,String idJefe) {
        super("ACTUALIZAR ORDEN DE TRABAJO");

        font = new Font("Tahoma", Font.PLAIN, 14);
        this.idJefe=idJefe;
        this.bd = bdIn;
        initGUI();
    }

    // Funcion que crea la interfaz y sus componentes
    private void initGUI() {

        contenedor = getContentPane();
        contenedor.removeAll();
        getContentPane().setLayout(null);

        listener = new ManejadorDeBotones();

        instruccion = new JLabel("ID orden a modificar");
        instruccion.setFont(font);
        instruccion.setBounds(22, 101, 138, 32);
        getContentPane().add(instruccion);

        idOrdenIn = new JComboBox<>(bd.cambiarDimension(
                bd.consultarOrden("User",idJefe,"id_ordenes,nombre")));
        idOrdenIn.setFont(font);
        idOrdenIn.setBounds(159, 101, 99, 32);
        getContentPane().add(idOrdenIn);

        salir = new JButton("Salir");
        salir.setFont(font);
        salir.setBounds(271, 184, 100, 32);
        salir.addActionListener(listener);
        getContentPane().add(salir);

        buscar = new JButton("Buscar");
        buscar.setBounds(271, 101, 100, 32);
        buscar.setFont(font);
        buscar.addActionListener(listener);
        getContentPane().add(buscar);

        icon = new JLabel("");
        icon.setBounds(21, 11, 66, 66);
        URL filePath = this.getClass().getResource("/images/update.png");
        icon.setIcon(new ImageIcon(filePath));
        getContentPane().add(icon);

        separator_1 = new JSeparator();
        separator_1.setBounds(21, 88, 349, 2);
        getContentPane().add(separator_1);

        JSeparator separator = new JSeparator();
        separator.setBounds(22, 172, 349, 2);
        getContentPane().add(separator);

        setResizable(false);
        setSize(400,256);
        setVisible(true);
        setLocationRelativeTo(null);
    }
    private void crearComponentes(String identifier) {

        contenedor = getContentPane();
        contenedor.removeAll();
        getContentPane().setLayout(null);

        JPanel panelUsuario = new JPanel();
        panelUsuario.setBounds(0, 0, 415, 475);
        contenedor.add(panelUsuario);
        panelUsuario.setLayout(null);

        idOrden = new JLabel("Id Orden:");
        idOrden.setFont(font);
        idOrden.setBounds(21, 101, 105, 32);
        panelUsuario.add(idOrden);

        idIn = new JTextField(identifier);
        idIn.setFont(font);
        idIn.setEditable(false);
        idIn.setBounds(159, 101, 234, 32);
        panelUsuario.add(idIn);

        idProducto = new JLabel("Id-Producto:");
        idProducto.setFont(font);
        idProducto.setBounds(21, 143, 105, 32);
        panelUsuario.add(idProducto);

        idProductoIn = new JComboBox<>(bd.cambiarDimension(bd.consultarProductos("id_producto, nombre")));
        idProductoIn.setSelectedItem(bd.cambiarDimension(
                bd.consultarOrden("Id",identifier,"id_producto,nombre"))[0]);
        idProductoIn.setFont(font);
        idProductoIn.setBounds(159, 144, 201, 32);
        panelUsuario.add(idProductoIn);

        fechaEntrega = new JLabel("Fecha de Entrega:");
        fechaEntrega.setFont(font);
        fechaEntrega.setBounds(21, 186, 127, 32);
        panelUsuario.add(fechaEntrega);

        fechaEntregaIn = new JTextField(bd.consultarOrden("Id",identifier,"fecha_entrega")[0][0]);
        fechaEntregaIn.setFont(font);
        fechaEntregaIn.setBounds(159, 186, 234, 32);
        panelUsuario.add(fechaEntregaIn);

        cantidad = new JLabel("Cantidad:");
        cantidad.setFont(font);
        cantidad.setBounds(21, 229, 105, 32);
        panelUsuario.add(cantidad);

        cantidadIn = new JTextField(bd.consultarOrden("Id",identifier,"cantidad")[0][0]);
        cantidadIn.setFont(font);
        cantidadIn.setBounds(159, 230, 234, 32);
        panelUsuario.add(cantidadIn);

        idEncargado = new JLabel("Id Encargado:");
        idEncargado.setFont(font);
        idEncargado.setBounds(21, 272, 105, 32);
        panelUsuario.add(idEncargado);

        idEncargadoIn = new JTextField(identifier);
        idEncargadoIn.setEditable(false);
        idEncargadoIn.setBounds(159, 274, 234, 32);
        idEncargadoIn.setFont(font);
        panelUsuario.add(idEncargadoIn);

        salir.setBounds(143, 436, 113, 28);
        salir.addActionListener(listener);
        panelUsuario.add(salir);

        actualizarOrden = new JButton("Actualizar Orden");
        actualizarOrden.setFont(font);
        actualizarOrden.setBounds(266, 436, 127, 28);
        actualizarOrden.addActionListener(listener);
        panelUsuario.add(actualizarOrden);

        separator_2 = new JSeparator();
        separator_2.setBounds(44, 423, 349, 2);
        panelUsuario.add(separator_2);

        separator_1 = new JSeparator();
        separator_1.setBounds(44, 88, 349, 2);
        panelUsuario.add(separator_1);

        JLabel lblNewLabel = new JLabel("");
        URL filePath = this.getClass().getResource("/images/home.png");
        lblNewLabel.setIcon(new ImageIcon(filePath));
        lblNewLabel.setBounds(21, 11, 66, 66);
        panelUsuario.add(lblNewLabel);

        JLabel asignadoA = new JLabel("Asignado a:");
        asignadoA.setFont(new Font("Tahoma", Font.PLAIN, 14));
        asignadoA.setBounds(21, 315, 105, 32);
        panelUsuario.add(asignadoA);

        asignadoAIn = new JTextField(bd.consultarOrden("Id",identifier,"asignada_a")[0][0]);
        asignadoAIn.setFont(new Font("Tahoma", Font.PLAIN, 14));
        asignadoAIn.setBounds(159, 316, 234, 32);
        panelUsuario.add(asignadoAIn);

        finalizar = new JButton("Finalizar orden");
        finalizar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        finalizar.setBounds(212, 359, 181, 32);
        finalizar.addActionListener(listener);
        panelUsuario.add(finalizar);

        cancelar = new JButton("Cancelar Orden");
        cancelar.setBounds(21, 358, 181, 32);
        cancelar.setFont(font);
        cancelar.addActionListener(listener);
        panelUsuario.add(cancelar);

        setResizable(false);
        setSize(423, 502);
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

    // Funcion que valida sí un dato ingresado a traves de un JTextField es entero
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
            if(e.getSource()==salir ){
                dispose();
                return;
            }

            String identifier = idOrdenIn.getSelectedItem().toString();
            identifier=identifier.substring(0,identifier.indexOf("-"));

            if(e.getSource()==buscar) {
                crearComponentes(identifier);
            }

            String idProducto = idProductoIn.getSelectedItem().toString();
            idProducto = idProducto.substring(0, idProducto.indexOf("-"));

            if (e.getSource() == actualizarOrden) {
                if (validar2().compareTo("true") == 0) {

                    if ( bd.actualizarOrden(identifier, fechaEntregaIn.getText(),
                            cantidadIn.getText(), idProducto, identifier, asignadoAIn.getText(),
                            "false")) {
                        JOptionPane.showMessageDialog(null, "Orden de trabajo actualizada exitosamente");
                    }else
                        JOptionPane.showMessageDialog(null, "Error al actualizar orden de trabajo.");
                    dispose();
                } else
                    JOptionPane.showMessageDialog(null, validar2());
            }

            if (e.getSource()== finalizar){
                if(bd.finalizarOrden(identifier,idProducto, bd.obtenerS(idJefe,"sede"),
                        Integer.parseInt(cantidadIn.getText().trim()))){

                    JOptionPane.showMessageDialog(null, "Orden de trabajo finalizada exitosamente");
                }else
                    JOptionPane.showMessageDialog(null, "Error al finalizar orden de trabajo.");
                dispose();
            }

            if(e.getSource()== cancelar){
                if(bd.cancelarOrden(identifier)){

                    JOptionPane.showMessageDialog(null, "Orden de trabajo cancelada exitosamente");
                }else
                    JOptionPane.showMessageDialog(null, "Error al cancelar orden de trabajo.");
                dispose();
            }
        }
    }
}
