package presentation.view;

import presentation.presenter.login_registerPresenter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Objects;

public class register {
    public JPanel registerAccount() {
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

        JLabel title = new JLabel("Register your account");
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

        JLabel dob = new JLabel("Date of birth: ");
        setComp(dob);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        smallerRightPanel.add(dob, gbc);

        JTextField dobText = new JTextField();
        setRadiusTextField(dobText);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        smallerRightPanel.add(dobText, gbc);

        JLabel gender = new JLabel("Gender: ");
        setComp(gender);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        smallerRightPanel.add(gender, gbc);

        ButtonGroup btnGroup = new ButtonGroup();
        JRadioButton btn1 = new JRadioButton("Male");
        JRadioButton btn2 = new JRadioButton("Female");
        btnGroup.add(btn1);
        btnGroup.add(btn2);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        smallerRightPanel.add(btn1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        smallerRightPanel.add(btn2, gbc);

        JLabel password = new JLabel("Password: ");
        setComp(password);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        smallerRightPanel.add(password, gbc);

        JTextField passwordText = new JTextField();
        setRadiusTextField(passwordText);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        smallerRightPanel.add(passwordText, gbc);

        JLabel rePassword = new JLabel("Re-enter password: ");
        setComp(rePassword);
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        smallerRightPanel.add(rePassword, gbc);

        JTextField rePasswordText = new JTextField();
        setRadiusTextField(rePasswordText);
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        smallerRightPanel.add(rePasswordText, gbc);

        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.weightx = 2;

        JButton register = new JButton("Register account");
        setButton(register);
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 1;
        smallerRightPanel.add(register, gbc);

        JButton login = new JButton("Back to login");
        login.addActionListener(new login_registerPresenter.btnLoginActionListener());
        setButton(login);
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.gridwidth = 1;
        smallerRightPanel.add(login, gbc);

        rightPanel.add(smallerRightPanel);
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        return mainPanel;
    }

    public void setComp(JLabel label) {
        label.setFont(new Font("Serif", Font.PLAIN, 20));
    }

    public void setRadiusTextField(JTextField textField) {
        textField.setBorder(new LineBorder(null, 2, true));
        textField.setPreferredSize(new Dimension(0, 30));
        textField.setColumns(20);
        textField.setFont(new Font("Serif", Font.PLAIN, 20));
    }

    public void setButton(JButton button) {
        button.setFont(new Font("Serif", Font.BOLD, 15));
        button.setPreferredSize(new Dimension(5, 40));
        if (Objects.equals(button.getText(), "Back to login")) {
            button.setBackground(Color.BLUE);
            button.setForeground(Color.WHITE);
        }
        else {
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLUE);
        }
    }
}
