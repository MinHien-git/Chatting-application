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

    private void showPopupMenu(int x, int y, JList<String> list) {
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
        searchBar.setSize(new Dimension(600, 200));
        SetPlaceholder(searchBar, "Chat With A Friend");
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
        userList = new JList<>(onlineList);
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
        scrollPane.setSize(600, 400);
        userListPanel.add(scrollPane, BorderLayout.CENTER);
        this.add(navigation, BorderLayout.NORTH);
        this.add(searchBar, BorderLayout.AFTER_LAST_LINE);
        this.add(userListPanel, BorderLayout.CENTER);
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

