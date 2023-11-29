package presentation.view;

import javax.swing.*;

public class register {
    public JPanel registerAccount() {
        JPanel mainPanel = new JPanel();

        JLabel username = new JLabel();
        JTextField usernameText = new JTextField();

        JLabel dob = new JLabel();
        JTextField dobText = new JTextField();

        JLabel gender = new JLabel();
        JTextField genderText = new JTextField();

        JLabel password = new JLabel();
        JTextField passwordText = new JTextField();

        JLabel rePassword = new JLabel();
        JTextField rePasswordText = new JTextField();

        return  mainPanel;
    }
}
