package client;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
    private DefaultListModel<String> sideList;
    private JList jList;
    private JScrollPane jScrollPane;
    private ArrayList<String> chatContent = new ArrayList<String>();
    public Application parent;
    /**
     * Create the application.
     */
    public chatting(Application application) {
    	parent = application;
        id = "";
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
    	chatContent.add(newString);
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
        sideList = new DefaultListModel<String>();

        chatArea = new JPanel();
        jList= new JList(sideList);
        jScrollPane = new JScrollPane(jList);
        chatArea.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        chatArea.setSize(new Dimension(360, 500));
        jScrollPane.setSize(new Dimension(360, 500));
        jScrollPane.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        
        JTextField searchBar = new JTextField();
        jList.setSize(new Dimension(360, 500));
        this.add(chatArea, BorderLayout.CENTER);
        this.add(searchBar, BorderLayout.NORTH);
        chatArea.add(jScrollPane,BorderLayout.CENTER);
        chatArea.setLayout(new BorderLayout());
        Font font = new Font("Arial", Font.BOLD, 14); // Font(name, style, size)
        chatArea.setFont(font);
        
        searchBar.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e)
            {
            }
            public void removeUpdate(DocumentEvent e) {filter();}
            public void insertUpdate(DocumentEvent e) {filter();}
            private void filter() {
                String filter = searchBar.getText();
                filterModel(sideList, filter);
            }
       });
        
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
                	sideList.addElement("(" +parent.currentUser.name + ") "+ chatInput.getText());
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
    
    public void filterModel(DefaultListModel<String> model, String filter) {
    	if(!filter.trim().equals("")) {
    	for (int i = 0;i< chatContent.size();++i){ {
        	String s = chatContent.get(i);
            if (!s.contains(filter)) {
                if (model.contains(s)) {
                    model.removeElement(s);
                }
            } else {
                if (!model.contains(s)) {
                    model.addElement(s);
                }
            }
        }}
       }else {
    	   model.clear();
    	   
    	   for (String s : chatContent) {
               model.addElement(s);
           }
       }
    }
    
    private void initialize(String groupID) {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.setForeground(Color.WHITE);
        this.setFont(new Font("Source Code Pro", Font.PLAIN, 14));
        this.setBounds(100, 100, 360, 800);
        
        sideList = new DefaultListModel<String>();

        chatArea = new JPanel();
        jList= new JList(sideList);
        jScrollPane = new JScrollPane(jList);
        chatArea.add(jScrollPane);
        Font font = new Font("Arial", Font.BOLD, 14); // Font(name, style, size)
        chatArea.setFont(font);

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
