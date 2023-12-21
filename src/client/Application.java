package client;
import javax.swing.*;

public class Application{
    private static JFrame applicationFrame;

    public Application() {
        login l = new login();
    }

    public static JFrame getApplicationFrame() {
        return applicationFrame;
    }

    public static void main(String[] args) {
        applicationFrame = new JFrame();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    login window = new login();
                    window.frmLogin.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
