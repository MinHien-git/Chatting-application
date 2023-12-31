package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class chatting extends JPanel {
    private JPanel chatArea;
    private JTextField chatInput;
    private JButton sendButton;
    private String id;
    private DefaultListModel<Object> sideList;
    private JList jList;
    private JScrollPane jScrollPane;
    
    private ArrayList<String> chatContent;
    public Application parent;
    /**
     * Create the application.
     */
    public chatting(Application application) {
    	parent = application;
        id = "";
        chatContent = new ArrayList<>();
        initialize("usr1", "usr2");
        //setUpSocket();
    }

    public chatting(String _id1, String _id2) {
        initialize(_id1, _id2);
    }

    public chatting(String gID) {
        id = gID;
        initialize(gID);
    }

    private static String extractID(String input) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\((\\d+)\\)");
        java.util.regex.Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    // Method to extract text after the dash "-"
    private static String extractText(String input) {
        String[] parts = input.split("-");
        if (parts.length > 1) {
            return parts[1];
        }
        return "";
    }
    
    public void ClearChat() {
    	sideList.clear();
    }
    
    public void AddChat(String newString) {
    	sideList.addElement(newString);
    }

//    public JTextArea getChatArea() {
//        return chatArea;
//    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(String id1, String id2) {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.setForeground(Color.WHITE);
        
        this.setFont(new Font("Source Code Pro", Font.PLAIN, 14));
        this.setBounds(100, 100, 360, 800);
        sideList = new DefaultListModel<Object>();

        chatArea = new JPanel();
        jList= new JList(sideList);
        jScrollPane = new JScrollPane(jList);
        chatArea.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        chatArea.setSize(new Dimension(360, 500));
        jScrollPane.setSize(new Dimension(360, 500));
        jScrollPane.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        
        jList.setSize(new Dimension(360, 500));
        this.add(chatArea, BorderLayout.CENTER);
        chatArea.add(jScrollPane,BorderLayout.CENTER);
        chatArea.setLayout(new BorderLayout());
        Font font = new Font("Arial", Font.BOLD, 14); // Font(name, style, size)
        chatArea.setFont(font);
        chatContent = UserAuthentication.getMessageContent(id1, id2);
//        if (chatContent == null) {
//            chatArea.setText("");
//        } else {
//            for (String text : chatContent) {
//                String id = extractID(text);
//                String msg = extractText(text);
//                String name = UserAuthentication.idToUser(id).getName();
//
//                chatArea.append(name + " - " + msg + "\n");
//            }
//        }
      
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setSize(360, 200);

        chatInput = new JTextField();
        chatInput.setSize(new Dimension(360, 100));
        inputPanel.add(chatInput, BorderLayout.NORTH);

        sendButton = new JButton("Send");
        sendButton.setAlignmentX(360);
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String msg = chatInput.getText();
                if(!parent.focusIDString.equals("") && !msg.equals("")) {
                try {
                    String send = parent.currentUser.getId() + " - " + msg; //identify send format here
                    parent.write("DirectMessage|"+parent.currentUser.getId()+"|"+parent.focusIDString+"|"+send);
                    chatInput.setText("");
                } catch (IOException ioe) {
                    System.out.println("IO Exception found");
                    ioe.printStackTrace();
                    System.exit(1);
                }
                }
            }
        });
        inputPanel.add(sendButton, BorderLayout.CENTER);
        this.add(inputPanel, BorderLayout.SOUTH);
    }

    private void initialize(String groupID) {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.setForeground(Color.WHITE);
        this.setFont(new Font("Source Code Pro", Font.PLAIN, 14));
        this.setBounds(100, 100, 360, 800);
        
        sideList = new DefaultListModel<Object>();
        sideList.addElement("Hello");
        sideList.addElement("Hello World");
        sideList.addElement("Java test");
        chatArea = new JPanel();
        jList= new JList(sideList);
        jScrollPane = new JScrollPane(jList);
        chatArea.add(jScrollPane);
        Font font = new Font("Arial", Font.BOLD, 14); // Font(name, style, size)
        chatArea.setFont(font);
        chatContent = UserAuthentication.getGroupContent(groupID);

        chatArea.setSize(new Dimension(360, 500));
        JScrollPane scrollPane = new JScrollPane(chatArea);
        this.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setSize(360, 200);
        jScrollPane.setSize(new Dimension(360, 500));
        jList.setSize(new Dimension(360, 500));
        
        chatInput = new JTextField();
        chatInput.setSize(new Dimension(360, 100));
        inputPanel.add(chatInput, BorderLayout.NORTH);

        sendButton = new JButton("Send");
        sendButton.setAlignmentX(360);
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String msg = chatInput.getText();
                try {
                    String send = id + " - " + msg; //identify send format here
                    parent.write(send);
                    chatInput.setText("");
                } catch (IOException ioe) {
                    System.out.println("IO Exception found");
                    ioe.printStackTrace();
                    System.exit(1);
                }
            }
        });
        inputPanel.add(sendButton, BorderLayout.CENTER);
        this.add(inputPanel, BorderLayout.SOUTH);
    }
}
