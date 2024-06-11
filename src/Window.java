import javax.swing.*;

public class Window extends JFrame {

    public Window() {
        setTitle("PING PONG");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(new GamePanel(1280, 720));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}