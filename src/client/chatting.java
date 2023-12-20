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

public class chatting extends JPanel{
	private Thread thread;
    private BufferedWriter os;
    private BufferedReader is;
    private Socket socketOfClient;
	private JTextArea chatArea;
	private JTextField chatInput;
	private JButton sendButton;
	private String id = "1";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		chatting window = new chatting();
	}

	/**
	 * Create the application.
	 */
	public chatting() {
		id = "";
		initialize("test");
		//setUpSocket();
	}

	public chatting (JFrame frame, String _id)
	{
		id = _id;
		initialize("test");
		frame.setVisible(false);
		frame.dispose();
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String username) {
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		this.setForeground(Color.WHITE);
		this.setFont(new Font("Source Code Pro", Font.PLAIN, 14));
		this.setBounds(100, 100, 480, 600);

		chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatArea.setSize(new Dimension(480, 400));
		JScrollPane scrollPane = new JScrollPane(chatArea);
		this.add(scrollPane, BorderLayout.CENTER);

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
		inputPanel.setSize(480, 200);

		chatInput = new JTextField();
		chatInput.setSize(new Dimension(480, 200));
		inputPanel.add(chatInput, BorderLayout.NORTH);

		sendButton = new JButton("Send");
		sendButton.setAlignmentX(480);
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
		
		System.out.println("Complete init!");
	}
	
	private void write(String message) throws IOException{
        os.write(message);
        os.newLine();
        os.flush();
    }
	
    public void setUpSocket() {
        try {
            thread = new Thread() {
                @Override
                public void run() {
                	
                    try {
                        socketOfClient = new Socket("127.0.0.1", 7777);
                        System.out.println("Successfully Connected!");
                        // Tạo luồng đầu ra tại client (Gửi dữ liệu tới server)
                        os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
                        // Luồng đầu vào tại Client (Nhận dữ liệu từ server).
                        is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));
                     
                        String message;
                        
                        while (true) {
                        	
                        	System.out.println(id);
                        	
                            message = is.readLine();
                            if(message==null){
                                break;
                            }
                            
                        }
//                    os.close();
//                    is.close();
//                    socketOfClient.close();
                    } catch (UnknownHostException e) {
                    	e.printStackTrace();
                    	return;
                    } catch (IOException e) {
                    	e.printStackTrace();
                        return;
                    }
                }
            };
            thread.sleep(1000);
            thread.run();
        } catch (Exception e) {
        }
    }
}
