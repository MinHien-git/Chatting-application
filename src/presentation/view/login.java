package presentation.view;

import presentation.presenter.loginPresenter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class login {
    public JPanel loginAccount() {
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel(new GridBagLayout());
        JPanel smallerRightPanel = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 30, 0);

        leftPanel.setBackground(new Color(151, 255, 255));
        leftPanel.setSize(800, 1600);

        rightPanel.setSize(800, 1600);
        rightPanel.setBackground(new Color(207, 207, 207));

        smallerRightPanel.setLayout(new GridBagLayout());
        smallerRightPanel.setBackground(new Color(207, 207, 207));

        JLabel title = new JLabel("Login your account");
        title.setFont(new Font("Montserrat", Font.BOLD, 20));
        title.setForeground(Color.BLUE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        smallerRightPanel.add(title, gbc);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 5, 0);

        JLabel username = new JLabel("Username: ");
        setComp(username);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        smallerRightPanel.add(username, gbc);

        JTextField usernameText = new JTextField();
        setRadiusTextField(usernameText);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        smallerRightPanel.add(usernameText, gbc);

        JLabel password = new JLabel("Password: ");
        setComp(password);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        smallerRightPanel.add(password, gbc);

        JTextField passwordText = new JTextField();
        setRadiusTextField(passwordText);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        smallerRightPanel.add(passwordText, gbc);

        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.weightx = 2;

        JButton login = new JButton("Login");
        setButton(login);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        smallerRightPanel.add(login, gbc);

        JButton register = new JButton("New user?");
        register.addActionListener(new loginPresenter.btnRegisterActionListener());
        setButton(register);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        smallerRightPanel.add(register, gbc);

        rightPanel.add(smallerRightPanel);
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        return mainPanel;
    }

    private void setComp(JLabel label) {
        label.setFont(new Font("Serif", Font.PLAIN, 20));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 90));
    }

    private void setRadiusTextField(JTextField textField) {
        textField.setBorder(new LineBorder(null, 2, true));
        textField.setColumns(20);
        textField.setPreferredSize(new Dimension(0, 30));
        textField.setFont(new Font("Serif", Font.PLAIN, 20));
    }

    private void setButton(JButton button) {
        button.setFont(new Font("Serif", Font.BOLD, 15));
        button.setPreferredSize(new Dimension(5, 40));
        if (Objects.equals(button.getText(), "Login")) {
            button.setBackground(Color.BLUE);
            button.setForeground(Color.WHITE);
        }
        else {
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLUE);
        }
    }
}
