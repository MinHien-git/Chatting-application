package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class friends extends JPanel {
    DefaultListModel<User> allFriends;
    private JList<User> userList;
    private JTextField searchBar;

    static class CustomCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof User) {
                setText(((User) value).getName());
                setForeground(Color.BLUE);
            }

            return renderer;
        }
    }

    private void SetPlaceholder(JTextField textField, String placeholder) {
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

    private void showPopupMenu(int x, int y, JList<User> list, User user) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem unfriend = new JMenuItem("Unfriend");
        JMenuItem block = new JMenuItem("Block");

        unfriend.addActionListener(e -> {
            User deletedFriend = list.getSelectedValue();

            String fromUser = user.getId();
            String deletedUser = deletedFriend.getId();

            if (deletedUser != null) {
                try {
                    Application.write("DeleteFriend|"+fromUser+"|"+deletedUser);
                    System.out.println("deleted friend successfully");
                    UserAuthentication.updateFriendsList(user);

                    allFriends.removeElement(deletedFriend);
                } catch (IOException ex) {
                    ex.getStackTrace();
                    System.out.println("Unable to carry out action");
                }
            }
        });

        block.addActionListener(e -> {
            // You can perform an action here, e.g., based on the selected item
            System.out.println("Perform action on: ");
        });

        popupMenu.add(block);
        popupMenu.add(unfriend);
        popupMenu.show(list, x, y);
    }

    public friends(User user) {
        this.setLayout(new BorderLayout());
        searchBar = new JTextField();
        searchBar.setMargin(new Insets(15, 10, 15, 10));
        searchBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY), // Border color
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        searchBar.setSize(new Dimension(600, 200));
        SetPlaceholder(searchBar, "Add A New Friend");

        //add event for enter key -> query user -> add...
        searchBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == searchBar) {
                    String fromUser = user.getId();
                    String toUser = UserAuthentication.usernameToID(searchBar.getText());
                    User newFriend = UserAuthentication.idToUser(toUser);

                    if (toUser != null) {
                        try {
                            Application.write("AddFriend|" + fromUser + "|" + toUser);
                            System.out.println("added friend successfully");
                            UserAuthentication.updateFriendsList(user);
                            allFriends.addElement(newFriend);
                            searchBar.setText("");
                        } catch (IOException ex) {
                            ex.getStackTrace();
                            System.out.println("Unable to carry out action");
                        }
                    }
                }
            }
        });

        JPanel userListPanel = new JPanel(new BorderLayout());
        userListPanel.setSize(new Dimension(600, 600));

        allFriends = new DefaultListModel<>();
        //we can dynamically add users here
        for (User friend : user.getFriends()) {
            allFriends.addElement(friend);
        }

        userList = new JList<>(allFriends);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.setCellRenderer(new CustomCellRenderer());
        userList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = userList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        userList.setSelectedIndex(index);
                        showPopupMenu(e.getX(), e.getY(), userList, user);
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
