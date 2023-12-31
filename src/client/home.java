package client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class home extends JPanel implements ActionListener{
    final static String CHAT_PANEL = "Chat Panel";
    final static String FRIENDS_PANEL = "Friends Panel";
    final static String GLOBAL_CHAT_HISTORY = "Search In Chat";
    final static String CREATE_GROUP = "Create Group";
    public JPanel chatPanel;
    public JPanel userPanel;
    public JPanel mainPanel;
    public JPanel friendsList;
    public JPanel chatHistory;
    JPanel mainContainer;
    public Application parent;
    public void actionPerformed(ActionEvent e) {
        CardLayout cardLayout = (CardLayout) (mainContainer.getLayout());
        cardLayout.show(mainContainer, e.getActionCommand());
    }

    public JPanel getChatPanel() { return chatPanel; }

    public void setChatPanel(JPanel chat) {
        mainPanel.remove(1);
        chatPanel = chat;
        mainPanel.add(chatPanel, BorderLayout.CENTER);
        mainPanel.repaint();
        mainPanel.revalidate();
        this.repaint();
        this.revalidate();
    }

    public home(Application app,JFrame mainFrame, JPanel users, JPanel friends, JPanel chat, JPanel history) {
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        mainContainer = new JPanel(new CardLayout());
        mainPanel = new JPanel(new BorderLayout());
        this.parent = app;
        
        int totalWidth = 600;

        int userWidth = (int) (totalWidth * 0.4);
        int chatWidth = (int) (totalWidth * 0.6);

        userPanel = users;
        friendsList = friends;
        chatPanel = chat;
        chatHistory = history;

        chatPanel.setPreferredSize(new Dimension(chatWidth, 800));
        userPanel.setPreferredSize(new Dimension(userWidth, 800));

        mainPanel.add(userPanel, BorderLayout.WEST);
        mainPanel.add(chatPanel, BorderLayout.CENTER);

        JButton toChat = new JButton("Chat With Friends");
        toChat.setActionCommand(CHAT_PANEL);
        toChat.addActionListener(this);

        JButton toFriends = new JButton("Find Friends");
        toFriends.setActionCommand(FRIENDS_PANEL);
        toFriends.addActionListener(this);

        JButton toChatHistory = new JButton("Search In Chat");
        toChatHistory.setActionCommand(GLOBAL_CHAT_HISTORY);
        toChatHistory.addActionListener(this);
        
        JButton toCreateGroup = new JButton("Create Group");
        toCreateGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField name = new JTextField();
				JLabel guide = new JLabel("Please type your friend name seperate by (|)");
				JTextField memebers = new JTextField();
				Object[] message = {
				    "Group name:", name,
				    "Guide: ",guide,
				    "Friend Names:", memebers
				};

				int option = JOptionPane.showConfirmDialog(null, message, "Create Group", JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
				    if (!name.getText().equals("") && !memebers.getText().equals("")) {
				        try {
							parent.write("CreateGroup||"+ name.getText()+ "||" + parent.currentUser.id + "||" + memebers.getText() + "|" + parent.currentUser.id+ "|");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				    } 
				} 
//				try {
//					parent.write("ResetPassword|"+toEmail);
//				}catch (IOException ex) {
//					System.out.println("An error occurred");
//					ex.printStackTrace();
//				}
			 }  
		});
        JPanel controlPanel = new JPanel();
        controlPanel.add(toChat);
        controlPanel.add(toFriends);
        controlPanel.add(toChatHistory);
        controlPanel.add(toCreateGroup);
        controlPanel.setLayout(new GridLayout());
        
        mainContainer.add(mainPanel, CHAT_PANEL);
        mainContainer.add(friendsList, FRIENDS_PANEL);
        mainContainer.add(chatHistory, GLOBAL_CHAT_HISTORY);

        this.add(controlPanel, BorderLayout.NORTH);

        this.add(mainContainer, BorderLayout.CENTER);
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);
    }
}