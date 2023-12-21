package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class onlineUsers extends JPanel {
    private DefaultListModel<Object> sideList;
    private JList<Object> usersAndgroups;
    private JTextField searchBar;
    private JLabel navigation;

    static class CustomRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof User) {
                String status = ((User) value).isOnline() ? "online" : "offline";
                setText(((User) value).getName() + status);
                setForeground(Color.BLUE);
            }
            if (value instanceof groupChat)
            {
                setText("Group chat - " + ((groupChat) value).getGroupName());
                setForeground(Color.BLACK);
            }

            return renderer;
        }
    }

    private void showPopupMenuDirect(int x, int y, JList<Object> list) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem spam = new JMenuItem("Report For Spam");
        JMenuItem viewChatHistory = new JMenuItem("View Chat History");
        JMenuItem clearChatHistory = new JMenuItem("Clear Chat History");
        JMenuItem searchChatHistory = new JMenuItem("Search Chat History");

        spam.addActionListener(e -> {
            // You can perform an action here, e.g., based on the selected item
            System.out.println("Perform action on: ");
        });

        viewChatHistory.addActionListener(e -> {
            // You can perform an action here, e.g., based on the selected item
            System.out.println("Perform action on: ");
        });

        clearChatHistory.addActionListener(e -> {
            // You can perform an action here, e.g., based on the selected item
            System.out.println("Perform action on: ");

            int choice = JOptionPane.showConfirmDialog(this, "Would you like to clear all of the chat history? (You cannot undo after this)", "Clear Chat History?", JOptionPane.YES_NO_OPTION);
            //Deal with task in accordance to choice
        });

        searchChatHistory.addActionListener(e -> {
            // You can perform an action here, e.g., based on the selected item
            System.out.println("Perform action on: ");
        });

        popupMenu.add(spam);
        popupMenu.add(viewChatHistory);
        popupMenu.add(clearChatHistory);
        popupMenu.add(searchChatHistory);
        popupMenu.show(list, x, y);
    }

    private void showPopupMenuGroup(int x, int y, JList<Object> list) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem changeName = new JMenuItem("Change Group's Name");
        JMenuItem addMember = new JMenuItem("Add A New Member To The Group");
        JMenuItem showMembers = new JMenuItem("Show Group Members");

        changeName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Perform action on: ");
            }
        });

        addMember.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Perform action on: ");
            }
        });

        showMembers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Perform action on: ");
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

    public onlineUsers(JFrame mainFrame, User user) {
        this.setLayout(new BorderLayout());
        navigation = new JLabel("Welcome, " + user.getName());
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

        JPanel usersAndgroupsPanel = new JPanel(new BorderLayout());

        sideList = new DefaultListModel<>();
        //we can dynamically add users/groups here
        int i = 0;
        for (int j = 0; j < user.getOnlineList().size(); ++i, ++j)
        {
            sideList.add(i, user.getOnlineList().get(j));
        }

        for (int j = 0; j < user.getGroupList().size(); ++i, ++j)
        {
            sideList.add(i, user.getGroupList().get(j));
        }

        usersAndgroups = new JList<>(sideList);
        usersAndgroups.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        usersAndgroups.setCellRenderer(new CustomRenderer());
        usersAndgroups.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = usersAndgroups.locationToIndex(e.getPoint());
                    if (index != -1) {
                        Rectangle bounds = usersAndgroups.getCellBounds(index, index);
                        if (bounds != null && bounds.contains(e.getPoint()))
                        {
                            usersAndgroups.setSelectedIndex(index);
                            if (usersAndgroups.getModel().getElementAt(index).getClass().getName() == "groupChat") {
                                showPopupMenuGroup(e.getX(), e.getY(), usersAndgroups);
                            }
                            else showPopupMenuDirect(e.getX(), e.getY(), usersAndgroups);
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

        JScrollPane scrollPane = new JScrollPane(usersAndgroups);
        scrollPane.setSize(320, 400);
        scrollPane.setVerticalScrollBar(new JScrollBar());

        usersAndgroupsPanel.add(scrollPane, BorderLayout.CENTER);
        this.add(navigation, BorderLayout.NORTH);
        this.add(usersAndgroupsPanel, BorderLayout.CENTER);
        this.add(searchBar, BorderLayout.AFTER_LAST_LINE);
    }

    private class AddUsersListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = JOptionPane.showInputDialog("Enter Username:");
            if (username != null && !username.trim().isEmpty()) {
                sideList.addElement(username);
            }
        }
    }

    private class RemoveUsersListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = usersAndgroups.getSelectedIndex();
            if (selectedIndex != -1) {
                sideList.remove(selectedIndex);
            }
        }
    }
}
