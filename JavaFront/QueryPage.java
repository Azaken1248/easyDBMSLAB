import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;

public class QueryPage {
    Dimension screenSize;
    ImageIcon icon;

    JFrame window;

    QueryPage() {
        icon = new ImageIcon("./Assets/icon.png");

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        window = new JFrame("SQL App");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setIconImage(icon.getImage());
        window.setSize(screenWidth / 2, screenHeight / 2);
        window.setResizable(false);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(new BorderLayout());
        window.setLocationRelativeTo(null);

    }
}
