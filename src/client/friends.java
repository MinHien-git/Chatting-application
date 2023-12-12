package client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class friends extends JPanel {
    DefaultListModel<String> allFriends;
    private JList<String> userList;
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

    public friends() {
        this.setLayout(new BorderLayout());
        searchBar = new JTextField();
        searchBar.setMargin(new Insets(15,10,15,10));
        searchBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY), // Border color
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        searchBar.setSize(new Dimension(600, 200));
        SetPlaceholder(searchBar, "Add A New Friend");

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

        JPanel userListPanel = new JPanel(new BorderLayout());
        userListPanel.setSize(new Dimension(600, 600));

        allFriends = new DefaultListModel<>();
        //we can dynamically add users here
        allFriends.add(0, "user1");
        allFriends.add(1, "user2");
        allFriends.add(2, "user3");
        userList = new JList<>(allFriends);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        userList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = userList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        userList.setSelectedIndex(index);
                        showPopupMenu(e.getX(), e.getY(), userList);
                    }
                }
            }


        });
        JScrollPane scrollPane = new JScrollPane(userList);
        scrollPane.setSize(600, 400);
        userListPanel.add(scrollPane, BorderLayout.CENTER);
        this.add(searchBar, BorderLayout.NORTH);
        this.add(userListPanel, BorderLayout.CENTER);
    }
}
