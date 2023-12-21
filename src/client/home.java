package client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class home extends JPanel implements ActionListener{
    final static String CHAT_PANEL = "Chat Panel";
    final static String FRIENDS_PANEL = "Friends Panel";
    final static String GLOBAL_CHAT_HISTORY = "Search In Global Chat";
    private JPanel chatPanel;
    private JPanel userPanel;
    private JPanel mainPanel;
    private JPanel friendsList;
    private JPanel chatHistory;

    public void actionPerformed(ActionEvent e) {
        CardLayout cardLayout = (CardLayout) (this.getLayout());
        cardLayout.show(this, e.getActionCommand());
    }

    public home(JFrame mainFrame, JPanel users, JPanel friends, JPanel chat, JPanel history) {
        this.setLayout(new CardLayout());
        mainPanel = new JPanel(new BorderLayout());

        int totalWidth = 800;

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

        JButton toChatHistory = new JButton("Search In Global Chat History");
        toChatHistory.setActionCommand(GLOBAL_CHAT_HISTORY);
        toChatHistory.addActionListener(this);

        JPanel controlPanel = new JPanel();
        controlPanel.add(toChat);
        controlPanel.add(toFriends);
        controlPanel.add(toChatHistory);

        this.add(mainPanel, CHAT_PANEL);
        this.add(friendsList, FRIENDS_PANEL);
        this.add(chatHistory, GLOBAL_CHAT_HISTORY);

        mainFrame.add(controlPanel, BorderLayout.NORTH);
        mainFrame.add(this, BorderLayout.CENTER);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //JFrame mainFrame = new JFrame();
                //onlineUsers onl = new onlineUsers(mainFrame);
//                friends flist = new friends();
//                chatting c = new chatting();
//                globalChatHistory gch = new globalChatHistory();
//                home h = new home(mainFrame, onl, flist, c, gch);
            }
        });
    }
}