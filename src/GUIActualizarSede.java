import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class GUIActualizarSede extends JFrame {

    private BaseDeDatos bd;
    private Font font;
    private ActionListener listener = new ManejadorDeBotones();
    private Container contenedor;
    private JLabel instruccion,icon, direccion,celular, aCargoDe;
    private JLabel id,nombre;
    private JSeparator separator_1, separator_2;
    private JButton salir1, cancelar2, buscar, actualizar;
    private JTextField direccionIn, celularIn,nombreIn,idT;
    private JComboBox idIn,aCargoDeIn;

    public GUIActualizarSede(BaseDeDatos bdIn){
        super("ACTUALIZAR SEDE");

        font = new Font("Tahoma", Font.PLAIN, 14);
        bd = bdIn;
        initGUI();
    }

    private void initGUI(){
        contenedor = getContentPane();
        contenedor.removeAll();
        getContentPane().setLayout(null);

        instruccion = new JLabel("Id-Nombre de sede");
        instruccion.setFont(font);
        instruccion.setBounds(22, 101, 138, 32);
        getContentPane().add(instruccion);

        idIn = new JComboBox<>(bd.cambiarDimension(bd.consultarSede(null,"id_Sede,nombre")));
        idIn.setFont(font);
        idIn.setBounds(165, 102, 99, 32);
        getContentPane().add(idIn);

        salir1 = new JButton("Salir");
        salir1.setFont(font);
        salir1.setBounds(271, 184, 100, 32);
        salir1.addActionListener(listener);
        getContentPane().add(salir1);

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


    private void initGUI2(String identifier){
        contenedor.removeAll();
        contenedor.repaint();
        getContentPane().setLayout(null);

        JPanel panel2 = new JPanel();
        panel2.setBounds(0, 0, 384, 572);
        contenedor.add(panel2);
        panel2.setLayout(null);

        id = new JLabel("Nombre:");
        id.setBounds(21, 101, 105, 32);
        id.setFont(font);
        panel2.add(id);

        idT = new JTextField(identifier);
        idT.setBounds(136, 101, 234, 32);
        idT.setEditable(false);
        idT.setFont(font);
        panel2.add(idT);

        nombre = new JLabel("Nombre:");
        nombre.setBounds(21, 140, 105, 32);
        nombre.setFont(font);
        panel2.add(nombre);

        nombreIn = new JTextField(bd.obtenerSede(identifier,"nombre"));
        nombreIn.setBounds(136, 141, 234, 32);
        nombreIn.setFont(font);
        panel2.add(nombreIn);

        direccion = new JLabel("Direccion:");
        direccion.setBounds(21, 183, 105, 32);
        direccion.setFont(font);
        panel2.add(direccion);

        direccionIn = new JTextField(bd.obtenerSede(identifier,"direccion"));
        direccionIn.setBounds(136, 184, 234, 32);
        direccionIn.setFont(font);
        panel2.add(direccionIn);

        celular = new JLabel("Telefono:");
        celular.setBounds(21, 226, 105, 32);
        celular.setFont(font);
        panel2.add(celular);

        celularIn = new JTextField(bd.obtenerSede(identifier,"telefono"));
        celularIn.setBounds(136, 227, 234, 32);
        celularIn.setFont(font);
        panel2.add(celularIn);

        aCargoDe = new JLabel("Empleado encargado:");
        aCargoDe.setBounds(21, 269, 105, 32);
        aCargoDe.setFont(font);
        panel2.add(aCargoDe);

        aCargoDeIn = new JComboBox<>(bd.cambiarDimension(
                bd.consultarUsuarios("Sede",identifier,"cedula,nombres")));
        seleccionarEmpleado(identifier);
        aCargoDeIn.setBounds(136, 270, 234, 32);
        aCargoDeIn.setFont(font);
        panel2.add(aCargoDeIn);

        cancelar2 = new JButton("Cancelar");
        cancelar2.setBounds(120, 326, 120, 28);
        cancelar2.setFont(font);
        cancelar2.addActionListener(listener);
        panel2.add(cancelar2);

        actualizar = new JButton("Actualizar");
        actualizar.setBounds(250, 326, 120, 28);
        actualizar.setFont(font);
        actualizar.addActionListener(listener);
        panel2.add(actualizar);

        separator_2 = new JSeparator();
        separator_2.setBounds(21, 313, 349, 2);
        panel2.add(separator_1);
        panel2.add(separator_2);
        panel2.add(icon);

        setSize(400,500);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);

    }

    private void seleccionarEmpleado(String identifier) {
        if(bd.obtenerSede(identifier,"empleado_a_cargo")!=null) {
            String[][] aux = bd.consultarUsuarios("Id",
                    bd.obtenerSede(identifier, "empleado_a_cargo"),"cedula,nombres,sede");

            if(aux[0][2].compareTo(identifier)!=0){
             aCargoDeIn.addItem(bd.cambiarDimension(aux)[0]);
            }
            aCargoDeIn.addItem(null);
            aCargoDeIn.setSelectedItem(bd.cambiarDimension(aux)[0]);
        }
        else aCargoDeIn.setSelectedItem(null);
    }

    private class ManejadorDeBotones implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String aCargo,identifier = idIn.getSelectedItem().toString();
            identifier=identifier.substring(0,identifier.indexOf("-"));

            if(e.getSource()==salir1 || e.getSource()==cancelar2){
                dispose();
            }
            else if(e.getSource()==buscar){


                initGUI2(identifier);
            }
            else if(e.getSource()==actualizar){
                if (aCargoDeIn.getSelectedItem()!=null) {
                    aCargo = aCargoDeIn.getSelectedItem().toString();
                    aCargo = aCargo.substring(0, aCargo.indexOf("-"));
                }else aCargo =null;

                boolean var = bd.actualizarSede(identifier, direccionIn.getText(),
                        celularIn.getText(),aCargo);

                if (var){
                    JOptionPane.showMessageDialog(null, "Usuario actualizado exitosamente");
                    dispose();
                }
                else JOptionPane.showMessageDialog(null, "Error al actualizar usuario");
                dispose();
            }
        }
    }
}
