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
    private Thread thread;
    private BufferedWriter os;
    private BufferedReader is;
    private Socket socketOfClient;
    private JTextArea chatArea;
    private JTextField chatInput;
    private JButton sendButton;
    private String id;
    private ArrayList<String> chatContent;

    /**
     * Create the application.
     */
    public chatting() {
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

    public JTextArea getChatArea() {
        return chatArea;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(String id1, String id2) {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.setForeground(Color.WHITE);
        this.setFont(new Font("Source Code Pro", Font.PLAIN, 14));
        this.setBounds(100, 100, 360, 800);

        chatArea = new JTextArea();
        Font font = new Font("Arial", Font.BOLD, 14); // Font(name, style, size)
        chatArea.setFont(font);
        chatContent = UserAuthentication.getMessageContent(id1, id2);
        if (chatContent == null) {
            chatArea.setText("");
        } else {
            for (String text : chatContent) {
                String id = extractID(text);
                String msg = extractText(text);
                String name = UserAuthentication.idToUser(id).getName();

                chatArea.append(name + " - " + msg + "\n");
            }
        }
        chatArea.setEditable(false);
        chatArea.setSize(new Dimension(360, 500));
        JScrollPane scrollPane = new JScrollPane(chatArea);
        this.add(scrollPane, BorderLayout.CENTER);

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
                try {
                    String send = id + " - " + msg; //identify send format here
                    write(send);
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

    private void initialize(String groupID) {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.setForeground(Color.WHITE);
        this.setFont(new Font("Source Code Pro", Font.PLAIN, 14));
        this.setBounds(100, 100, 360, 800);

        chatArea = new JTextArea();
        Font font = new Font("Arial", Font.BOLD, 14); // Font(name, style, size)
        chatArea.setFont(font);
        chatContent = UserAuthentication.getGroupContent(groupID);

        if (chatContent == null) {
            chatArea.setText("");
        } else {
            for (String text : chatContent) {
                String id = extractID(text);
                String msg = extractText(text);
                String name = UserAuthentication.idToUser(id).getName();

                chatArea.append(name + " - " + msg + "\n");
            }
        }
        chatArea.setEditable(false);
        chatArea.setSize(new Dimension(360, 500));
        JScrollPane scrollPane = new JScrollPane(chatArea);
        this.add(scrollPane, BorderLayout.CENTER);

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
                try {
                    String send = id + " - " + msg; //identify send format here
                    write(send);
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

    private void write(String message) throws IOException {
        os.write(message);
        os.newLine();
        os.flush();
    }
}
