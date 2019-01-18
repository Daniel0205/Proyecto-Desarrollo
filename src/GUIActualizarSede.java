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
    private JSeparator separator_1, separator_2;
    private JButton salir1, cancelar2, buscar, actualizar;
    private JTextField direccionIn, celularIn, aCargoDeIn;
    private JTextField idIn;

    public GUIActualizarSede(BaseDeDatos bdIn){

        setResizable(false);
        setTitle("ACTUALIZAR SEDE");

        font = new Font("Tahoma", Font.PLAIN, 14);
        initGUI();
        bd = bdIn;
    }

    private void initGUI(){
        contenedor = getContentPane();
        contenedor.removeAll();
        getContentPane().setLayout(null);

        instruccion = new JLabel("ID sede a modificar");
        instruccion.setFont(font);
        instruccion.setBounds(22, 101, 138, 32);
        getContentPane().add(instruccion);

        idIn = new JTextField();
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

    private void initGUI2(int identifier){
        contenedor.removeAll();
        contenedor.repaint();
        getContentPane().setLayout(null);

        JPanel panel2 = new JPanel();
        panel2.setBounds(0, 0, 384, 572);
        contenedor.add(panel2);
        panel2.setLayout(null);

        direccion = new JLabel("Direccion:");
        direccion.setBounds(21, 101, 105, 32);
        direccion.setFont(font);
        panel2.add(direccion);

        direccionIn = new JTextField(bd.obtenerSede(identifier,"address"));
        direccionIn.setBounds(136, 101, 234, 32);
        direccionIn.setFont(font);
        panel2.add(direccionIn);

        celular = new JLabel("Telefono:");
        celular.setBounds(21, 140, 105, 32);
        celular.setFont(font);
        panel2.add(celular);

        celularIn = new JTextField(bd.obtenerSede(identifier,"phone_number"));
        celularIn.setBounds(136, 141, 234, 32);
        celularIn.setFont(font);
        panel2.add(celularIn);

        aCargoDe = new JLabel("Empleado encargado:");
        aCargoDe.setBounds(21, 183, 105, 32);
        aCargoDe.setFont(font);
        panel2.add(aCargoDe);

        aCargoDeIn = new JTextField(bd.obtenerSede(identifier,"employee_in_charge"));
        aCargoDeIn.setBounds(136, 184, 234, 32);
        aCargoDeIn.setFont(font);
        panel2.add(aCargoDeIn);

        cancelar2 = new JButton("Cancelar");
        cancelar2.setBounds(120, 240, 120, 28);
        cancelar2.setFont(font);
        cancelar2.addActionListener(listener);
        panel2.add(cancelar2);

        actualizar = new JButton("Actualizar");
        actualizar.setBounds(250, 240, 120, 28);
        actualizar.setFont(font);
        actualizar.addActionListener(listener);
        panel2.add(actualizar);

        separator_2 = new JSeparator();
        separator_2.setBounds(21, 227, 349, 2);
        panel2.add(separator_1);
        panel2.add(separator_2);
        panel2.add(icon);

        setSize(400,300);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);

    }

    private class ManejadorDeBotones implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==salir1 || e.getSource()==cancelar2){
                dispose();
            }
            else if(e.getSource()==buscar){
                int identifier = Integer.parseInt(idIn.getText());

                if(bd.verificarIdSede(identifier)){
                    initGUI2(identifier);
                }
                else{
                    JOptionPane.showMessageDialog(null, "La sede con id: "+ idIn.getText() +" no se encuentra registrada");
                    dispose();
                }
            }
            else if(e.getSource()==actualizar){
                int identifier = Integer.parseInt(idIn.getText());
                boolean var = bd.actualizarSede(identifier, direccionIn.getText(),celularIn.getText(),aCargoDeIn.getText());

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
