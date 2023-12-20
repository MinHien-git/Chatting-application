package client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class globalChatHistory extends JPanel {
    DefaultListModel<String> chatResults;
    private JList<String> chatDisplay;
    private JTextField searchBar;

    private void showPopupMenu(int x, int y, JList<String> list) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem unfriend = new JMenuItem("Unfriend");
        JMenuItem block = new JMenuItem("Block");

        unfriend.addActionListener(e -> {
            // You can perform an action here, e.g., based on the selected item
            String selectedItem = list.getSelectedValue();
            System.out.println("Perform action on: " + selectedItem);
        });

        block.addActionListener(e -> {
            // You can perform an action here, e.g., based on the selected item
            String selectedItem = list.getSelectedValue();
            System.out.println("Perform action on: " + selectedItem);
        });

        popupMenu.add(block);
        popupMenu.add(unfriend);
        popupMenu.show(list, x, y);
    }

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
        searchBar.setMargin(new Insets(15,10,15,10));
        searchBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY), // Border color
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        searchBar.setSize(new Dimension(800, 200));
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
        displayPanel.setSize(new Dimension(800, 400));

        chatResults = new DefaultListModel<>();
        //we can dynamically add users here
        chatResults.add(0, "Hello, my name is Duy");
        chatResults.add(1, "Hello, my name is Daniel, sup");
        chatResults.add(2, "My name is Daniel Pham but you can call me");
        chatDisplay = new JList<>(chatResults);
        chatDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        chatDisplay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = chatDisplay.locationToIndex(e.getPoint());
                    if (index != -1) {
                        Rectangle bounds = chatDisplay.getCellBounds(index, index);
                        if (bounds != null && bounds.contains(e.getPoint()))
                        {
                            chatDisplay.setSelectedIndex(index);
                            showPopupMenu(e.getX(), e.getY(), chatDisplay);
                        }
                    }
                }
            }


        });
        JScrollPane scrollPane = new JScrollPane(chatDisplay);
        scrollPane.setSize(800, 400);
        displayPanel.add(scrollPane, BorderLayout.CENTER);
        this.add(searchBar, BorderLayout.NORTH);
        this.add(displayPanel, BorderLayout.CENTER);
    }
}
