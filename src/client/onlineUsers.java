package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class onlineUsers extends JPanel {
    private DefaultListModel<String> listModel;
    private JList<String> userList;
    private JTextField searchBar;
    private JLabel navigation;
    public onlineUsers(JFrame mainFrame) {
        this.setLayout(new BorderLayout());
        navigation = new JLabel("Welcome, User");
        navigation.setFont(new Font("Source Code Pro", Font.BOLD, 14));
        searchBar = new JTextField();
        searchBar.setMargin(new Insets(15,10,15,10));
        JPanel userListPanel = new JPanel(new BorderLayout());

        listModel = new DefaultListModel<>();
        userList = new JList<>(listModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
        userListPanel.add(scrollPane, BorderLayout.CENTER);
        userListPanel.setSize(new Dimension(500, 1000));
        this.add(navigation, BorderLayout.NORTH);
        this.add(searchBar, BorderLayout.CENTER);
        this.add(userListPanel, BorderLayout.AFTER_LAST_LINE);
    }

    private class AddUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = JOptionPane.showInputDialog("Enter Username:");
            if (username != null && !username.trim().isEmpty()) {
                listModel.addElement(username);
            }
        }
    }

    private class RemoveUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = userList.getSelectedIndex();
            if (selectedIndex != -1) {
                listModel.remove(selectedIndex);
            }
        }
    }
}

