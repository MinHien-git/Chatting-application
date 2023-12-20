package client;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class onlineUsers extends JPanel {
    private DefaultListModel<String> onlineList;
    private JList<String> userList;
    private JTextField searchBar;
    private JLabel navigation;

    private void showPopupMenuDirect(int x, int y, JList<String> list) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem spam = new JMenuItem("Report For Spam");
        JMenuItem viewChatHistory = new JMenuItem("View Chat History");
        JMenuItem clearChatHistory = new JMenuItem("Clear Chat History");
        JMenuItem searchChatHistory = new JMenuItem("Search Chat History");

        spam.addActionListener(e -> {
            // You can perform an action here, e.g., based on the selected item
            String selectedItem = list.getSelectedValue();
            System.out.println("Perform action on: " + selectedItem);
        });

        viewChatHistory.addActionListener(e -> {
            // You can perform an action here, e.g., based on the selected item
            String selectedItem = list.getSelectedValue();
            System.out.println("Perform action on: " + selectedItem);
        });

        clearChatHistory.addActionListener(e -> {
            // You can perform an action here, e.g., based on the selected item
            String selectedItem = list.getSelectedValue();
            System.out.println("Perform action on: " + selectedItem);

            int choice = JOptionPane.showConfirmDialog(this, "Would you like to clear all of the chat history? (You cannot undo after this)", "Clear Chat History?", JOptionPane.YES_NO_OPTION);
            //Deal with task in accordance to choice
        });

        searchChatHistory.addActionListener(e -> {
            // You can perform an action here, e.g., based on the selected item
            String selectedItem = list.getSelectedValue();
            System.out.println("Perform action on: " + selectedItem);
        });

        popupMenu.add(spam);
        popupMenu.add(viewChatHistory);
        popupMenu.add(clearChatHistory);
        popupMenu.add(searchChatHistory);
        popupMenu.show(list, x, y);
    }

    private void showPopupMenuGroup(int x, int y, JList<String> list) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem changeName = new JMenuItem("Change Group's Name");
        JMenuItem addMember = new JMenuItem("Add A New Member To The Group");
        JMenuItem showMembers = new JMenuItem("Show Group Members");

        changeName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = list.getSelectedValue();
                System.out.println("Perform action on: " + selectedItem);
            }
        });

        addMember.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = list.getSelectedValue();
                System.out.println("Perform action on: " + selectedItem);
            }
        });

        showMembers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = list.getSelectedValue();
                System.out.println("Perform action on: " + selectedItem);
            }
        });

        popupMenu.add(changeName);
        popupMenu.add(addMember);
        popupMenu.add(showMembers);
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

    public onlineUsers(JFrame mainFrame) {
        this.setLayout(new BorderLayout());
        navigation = new JLabel("Welcome, User");
        navigation.setFont(new Font("Source Code Pro", Font.BOLD, 14));
        navigation.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding
        navigation.setOpaque(true);
        navigation.setBackground(Color.LIGHT_GRAY);

        searchBar = new JTextField();
        searchBar.setMargin(new Insets(15,10,15,10));
        searchBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY), // Border color
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        searchBar.setSize(new Dimension(320, 200));
        SetPlaceholder(searchBar, "Chat With A Friend");
        searchBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    JPopupMenu subMenu = new JPopupMenu();
                    JMenuItem newGroup = new JMenuItem("Create A New Group Chat");

                    newGroup.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("New Group Created");
                        }
                    });

                    subMenu.add(newGroup);
                    subMenu.show(searchBar, e.getX(), e.getY());
                }
            }
        });

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

        onlineList = new DefaultListModel<>();
        //we can dynamically add users here
        onlineList.add(0, "user1");
        onlineList.add(1, "user2");
        onlineList.add(2, "user3");
        onlineList.add(3, "group1");
        onlineList.add(4, "group2");
        onlineList.add(5, "group3");

        userList = new JList<>(onlineList);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        userList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = userList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        Rectangle bounds = userList.getCellBounds(index, index);
                        if (bounds != null && bounds.contains(e.getPoint()))
                        {
                            userList.setSelectedIndex(index);
                            if (userList.getModel().getElementAt(index).contains("group")) {
                                showPopupMenuGroup(e.getX(), e.getY(), userList);
                            }
                            else showPopupMenuDirect(e.getX(), e.getY(), userList);
                        }
                    }
                }
            }
        });

        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem blockedList = new JMenuItem("Blocked List");
        JMenuItem spamList = new JMenuItem("Spam List");
        JMenuItem options = new JMenuItem("Options");
        JMenuItem logout = new JMenuItem("Log Out");

        blockedList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainFrame, "Shows Blocked List");
            }
        });

        spamList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainFrame, "Shows Spam List");
            }
        });

        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainFrame, "Navigates To Options");
            }
        });

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainFrame, "User Logged Out");
            }
        });

        popupMenu.add(blockedList);
        popupMenu.add(spamList);
        popupMenu.add(options);
        popupMenu.add(logout);

        navigation.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopupMenu(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopupMenu(e);
                }
            }

            private void showPopupMenu(MouseEvent e) {
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        JScrollPane scrollPane = new JScrollPane(userList);
        scrollPane.setSize(320, 400);
        scrollPane.setVerticalScrollBar(new JScrollBar());

        userListPanel.add(scrollPane, BorderLayout.CENTER);
        this.add(navigation, BorderLayout.NORTH);
        this.add(userListPanel, BorderLayout.CENTER);
        this.add(searchBar, BorderLayout.AFTER_LAST_LINE);
    }

    private class AddUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = JOptionPane.showInputDialog("Enter Username:");
            if (username != null && !username.trim().isEmpty()) {
                onlineList.addElement(username);
            }
        }
    }

    private class RemoveUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = userList.getSelectedIndex();
            if (selectedIndex != -1) {
                onlineList.remove(selectedIndex);
            }
        }
    }
}

