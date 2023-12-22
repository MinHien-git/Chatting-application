package client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class globalChatHistory extends JPanel {
    DefaultListModel<String> chatResults;
    private JList<String> chatDisplay;
    private JTextField searchBar;

    private void SetPlaceholder(JTextField textField, String placeholder)
    {
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK); // Set text color to default when focused
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder); // Reset placeholder text when focus is lost
                }
            }
        });
    }

    public globalChatHistory() {
        this.setLayout(new BorderLayout());
        searchBar = new JTextField();
        chatResults = new DefaultListModel<>();
        searchBar.setMargin(new Insets(15,10,15,10));
        searchBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY), // Border color
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        searchBar.setSize(new Dimension(600, 200));
        SetPlaceholder(searchBar, "Type A Sentence You Want To Search For");

        //add event for enter key -> query user -> add...
        searchBar.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        JPanel displayPanel = new JPanel(new BorderLayout());
        displayPanel.setSize(new Dimension(600, 600));

        JScrollPane scrollPane = new JScrollPane(chatDisplay);
        scrollPane.setSize(600, 400);
        displayPanel.add(scrollPane, BorderLayout.CENTER);
        this.add(searchBar, BorderLayout.NORTH);
        this.add(displayPanel, BorderLayout.CENTER);
    }
}
