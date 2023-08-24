import javax.swing.*;
import java.awt.*;

public class Main{
    public static void main(String [] args) {
        Gamepanel  g = new Gamepanel();
        JFrame f = new JFrame();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setSize(500,500);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.add(g);

    }
}
