import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUInitAction extends JFrame {

    private Container contenedor;
    private JLabel instruccion;
    private JButton registrar,actualizar;
    private BaseDeDatos bd;

    public GUInitAction(BaseDeDatos bdIn){
        super("Acciones");

        initGUI();
        bd=bdIn;
    }

    private void initGUI() {

        contenedor = getContentPane();
        contenedor.removeAll();

        JPanel panel1 = new JPanel(new GridLayout(2,1));
        contenedor.add(panel1);

        instruccion = new JLabel("Selecciona la acción que deseas realizar");
        instruccion.setHorizontalAlignment(JLabel.CENTER);
        instruccion.setVerticalAlignment(JLabel.CENTER);
        panel1.add(instruccion);

        JPanel panel2 = new JPanel(new GridLayout(1,2));
        panel1.add(panel2);

        ActionListener listener = new ManejadorDeBotones();

        registrar = new JButton("Registrar usuario");
        registrar.addActionListener(listener);
        panel2.add(registrar);

        actualizar = new JButton("Actualizar usuario");
        actualizar.addActionListener(listener);
        panel2.add(actualizar);

        setSize(300,100);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private class ManejadorDeBotones implements ActionListener{

        public void actionPerformed(ActionEvent actionEvent){
           if (actionEvent.getSource() == registrar){
               JFrame formulario = new GUIFormulario(bd);
               formulario.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
           } else if (actionEvent.getSource() == actualizar){
               JFrame formulario2 = new GUIActualizar(bd);
               formulario2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
           }
        }

    }
}
