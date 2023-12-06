package client;

import javax.swing.*;
import java.awt.*;

public class Trang_chu {
    public JPanel component() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton btn1 = new JButton("Chuyển đến trang chức năng 1");
        JButton btn2 = new JButton("Chuyển đến trang chức năng 2");
        JButton btn3 = new JButton("Chuyển đến trang chức năng 3");
        JButton btn4 = new JButton("Chuyển đến trang chức năng 4");
        JButton btn5 = new JButton("Chuyển đến trang chức năng 5");
        JButton btn6 = new JButton("Chuyển đến trang chức năng 6");
        JButton btn7 = new JButton("Chuyển đến trang chức năng 7");
        JButton btn8 = new JButton("Chuyển đến trang chức năng 8");
        JButton btn9 = new JButton("Chuyển đến trang chức năng 9");

        JPanel panel1 = new JPanel(new SpringLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(btn1, gbc);
        return mainPanel;
    }
}
