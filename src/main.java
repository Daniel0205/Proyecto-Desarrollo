import javax.swing.*;

public class main {

    public static void main(String[] args){

        BaseDeDatos bd = new BaseDeDatos();

        JFrame formulario = new GUIFormulario(bd);
        formulario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




    }
}
