package client;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class onlineUsers extends JPanel {
    private DefaultListModel<Object> sideList;
    private JList<Object> usersAndgroups;
    private JTextField searchBar;
    private JLabel navigation;
    private Application parent;
    static class CustomRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof User) {
                String status = ((User) value).isOnline() ? "online" : "offline";
                setText(((User) value).getName() + " - Status: " + status);
                if(((User) value).chatWithU) {
                	setForeground(Color.RED);
                }else {
                	setForeground(Color.BLUE);
                }
            }
            if (value instanceof groupChat) {
                setText("Group chat - " + ((groupChat) value).getGroupName());
                setForeground(Color.BLACK);
            }

            return renderer;
        }
    }

    private void showPopupMenuDirect(int x, int y, JList<Object> list, User user) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem spam = new JMenuItem("Report For Spam");
        JMenuItem viewChatHistory = new JMenuItem("View Chat History");
        JMenuItem clearChatHistory = new JMenuItem("Clear Chat History");
        JMenuItem searchChatHistory = new JMenuItem("Search Chat History");

        spam.addActionListener(e -> {
            Object selected = list.getSelectedValue();
            if (selected != null && selected instanceof User) {
                User reportedUser = (User) selected;
                String reportedName = reportedUser.getName();
                String byUserName = user.getName();
                try {
                	parent.write("ReportSpam|" + reportedName + "|" + byUserName);
                    JOptionPane.showMessageDialog(this, "The user is successfully reported!");
                    parent.write("BlockAccount|" + user.getId() + "|" + reportedUser.getId());
                    sideList.removeElement(selected);
                } catch (IOException ex) {
                    System.out.println("Unable to write");
                    ex.printStackTrace();
                }
            }
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

    private void showPopupMenuGroup(int x, int y, JList<Object> list, User user) {
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

    private void showPopupOptions(int x, int y, JList<Object> list, User user) {
        JPopupMenu options = new JPopupMenu();
        JMenuItem refresh = new JMenuItem("Refresh");

        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserAuthentication.updateOnlList(user);
                UserAuthentication.updateGroups(user);
                sideList.clear();
                int i = 0;
                for (int j = 0; j < user.getOnlineList().size(); ++i, ++j) {
                    sideList.add(i, user.getOnlineList().get(j));
                }

                for (int j = 0; j < user.getGroupList().size(); ++i, ++j) {
                    sideList.add(i, user.getGroupList().get(j));
                }
            }
        });

        options.add(refresh);
        options.show(list, x, y);
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

    public onlineUsers(Application app, User user) {
    	this.parent = app;
        this.setLayout(new BorderLayout());
        navigation = new JLabel("Welcome, " + user.getName());
        navigation.setFont(new Font("Source Code Pro", Font.BOLD, 14));
        navigation.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding
        navigation.setOpaque(true);
        navigation.setBackground(Color.LIGHT_GRAY);

        searchBar = new JTextField();
        searchBar.setMargin(new Insets(15, 10, 15, 10));
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
        searchBar.getDocument().addDocumentListener(new DocumentListener() {
             public void changedUpdate(DocumentEvent e)
             {
             }
             public void removeUpdate(DocumentEvent e) {filter();}
             public void insertUpdate(DocumentEvent e) {filter();}
             private void filter() {
                 String filter = searchBar.getText();
                 filterModel((DefaultListModel<Object>)sideList, filter);
             }
        });

        JPanel usersAndgroupsPanel = new JPanel(new BorderLayout());

        sideList = new DefaultListModel<>();
        //we can dynamically add users/groups here
        int i = 0;
        for (int j = 0; j < user.getOnlineList().size(); ++i, ++j) {
            sideList.add(i, user.getOnlineList().get(j));
        }

        for (int j = 0; j < user.getGroupList().size(); ++i, ++j) {
            sideList.add(i, user.getGroupList().get(j));
        }

        usersAndgroups = new JList<>(sideList);
        usersAndgroups.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        usersAndgroups.setCellRenderer(new CustomRenderer());
        usersAndgroups.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Object selected = usersAndgroups.getSelectedValue();
	                  if(selected==null) {
	                	  usersAndgroups.clearSelection();
	                  }else {
            	   if (selected.getClass().getSimpleName().equals("User")) {
                       User selectedUser = (User) selected;
                       String id = selectedUser.getId();
                       selectedUser.chatWithU = false;
                       parent.focusIDString = selectedUser.getId();
                       parent.focusNameString = selectedUser.getName();
                       if(parent.mainPanel instanceof home) {
                    	   	home h =(home) parent.mainPanel;
                    	   	chatting userChat =(chatting)h.chatPanel;
                       		userChat.ClearChat();
                       }
                       try {
                       	parent.write("MessageData"+"|"+"user"+"|"+user.getId()+"|"+id);
                     
                       } catch (IOException ex) {
                           System.out.println("Unable to write");
                           ex.printStackTrace();
                       }
                       
                   } else if (selected.getClass().getSimpleName().equals("groupChat")) {
                       groupChat selectedGroup = (groupChat) selected;
                       String id = selectedGroup.getGroupID();
                       chatting userChat = new chatting(id);
                       Component tmp = parent.getApplicationFrame().getContentPane().getComponent(1);

                       if (tmp.getClass().getSimpleName().equals("home")) {
                           home b = (home) tmp;
                           b.setChatPanel(userChat);
                       }
                   }
	                  }
                    
                }
            }
        });

        usersAndgroups.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = usersAndgroups.locationToIndex(e.getPoint());
                    if (index != -1) {
                        Rectangle bounds = usersAndgroups.getCellBounds(index, index);
                        if (bounds != null && bounds.contains(e.getPoint())) {
                            usersAndgroups.setSelectedIndex(index);
                            if (usersAndgroups.getModel().getElementAt(index).getClass().getSimpleName().equalsIgnoreCase("groupChat")) {
                                showPopupMenuGroup(e.getX(), e.getY(), usersAndgroups, user);
                            } else showPopupMenuDirect(e.getX(), e.getY(), usersAndgroups, user);
                        } else showPopupOptions(e.getX(), e.getY(), usersAndgroups, user);
                    }
                    if (index == -1 && sideList.isEmpty()) {
                        showPopupOptions(e.getX(), e.getY(), usersAndgroups, user);
                    }
                }
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
    public void SetOffline(String id) {
    	for (int i = 0;i < sideList.size();++i) {
    		User user=(User)sideList.get(i);
    		if(user.id.equals(id)) {
    			sideList.remove(i);
    			user.setOnline(false);
    			sideList.add(i, user);
    			break;
    		}
    	}
    }
    public void SetMessage(String id) {
    	for (int i = 0;i < sideList.size();++i) {
    		if(sideList.get(i) instanceof User) {
    		User user=(User)sideList.get(i);
    		if(user.id.equals(id)) {
    			((User)sideList.get(i)).chatWithU = true;
    		}
    	}}
    }
    
    public void filterModel(DefaultListModel<Object> model, String filter) {
    	if(!filter.trim().equals("") && !filter.equals("Chat With A Friend")) {
        for (int i = 0;i< parent.currentUser.friends.size();++i){ {
        	if(parent.currentUser.friends.get(i) instanceof User) {
	        	User s =(User) parent.currentUser.friends.get(i);
	            if (!s.name.contains(filter)) {
	                if (model.contains(s)) {
	                    model.removeElement(s);
	                }
	            } else {
	                if (!model.contains(s)) {
	                    model.addElement(s);
	                }
	            	}
	        	}
    	}
       }
       }else {
    	   model.clear();
    	   for (int i = 0;i< parent.currentUser.friends.size();++i){ 
    		   model.addElement(parent.currentUser.friends.get(i));
       	}
       }
    }
    
    public void SetOnline(String id) {
    	for (int i = 0;i < sideList.size();++i) {
    		User user=(User)sideList.get(i);
    		if(user.id.equals(id)) {
    			sideList.remove(i);
    			user.setOnline(true);
    			sideList.add(i, user);
    			break;
    		}
    	}
    }
    
    public void ClearChat() {
    	sideList.clear();
    }
    
    public void UpdateList(User user) {
    	
        //we can dynamically add users/groups here
        int i = 0;
        for (int j = 0; j < user.friends.size(); ++i, ++j) {
        	sideList.addElement(user.friends.get(j));
            System.out.println("call _ user onlinelist " + user.friends.size());
        }

        for (int j = 0; j < user.getGroupList().size(); ++i, ++j) {
            sideList.add(i, user.getGroupList().get(j));
        }

        System.out.println("end user list");
    }
    
    public void UpdateNewMessageList(String id) {
    	
    		for (int i =0;i < sideList.size();++i) {
    			if(sideList.get(i) instanceof User) {
    				if(((User) sideList.get(i)).id.equals(id))
    					((User) sideList.get(i)).chatWithU = true;
    			}
    		}
        
    }
}
