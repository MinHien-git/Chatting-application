package client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class home extends JPanel {
    private JPanel chatPanel;
    private JPanel userPanel;

    private static void addComponent(Container container, Component component, GridBagConstraints gbc, int gridx, int gridy) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        container.add(component, gbc);
    }

    public home(JFrame mainFrame, JPanel users, JPanel chat) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        chatPanel = chat;
        userPanel = users;

        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        addComponent(this, userPanel, gbc, 0, 0);

        gbc.weightx = 0.7;
        addComponent(this, chatPanel, gbc, 1, 0);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame mainFrame = new JFrame();
                onlineUsers onl = new onlineUsers(mainFrame);
                JPanel chat = new JPanel();
                mainFrame.add(new home(mainFrame, onl ,chat));
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainFrame.setSize(800, 600);
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setVisible(true);
            }
        });
    }
}
