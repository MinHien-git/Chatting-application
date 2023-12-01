package client;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import java.awt.Window.Type;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.UIManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.net.*;
import java.io.*;

public class chatting {

	public JFrame frmChatting;
	private Thread thread;
	private Thread thread2;
    private BufferedWriter os;
    private BufferedReader is;
    private Socket socketOfClient;
    private List<String> onlineList;
    public String id = "1";
    JTextArea textArea;
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
		initialize();  
		onlineList = new ArrayList<>();
		frmChatting.setVisible(true);
		setUpSocket();
	}
	
	public chatting(JFrame frame,String _id) {
		id = _id;
		
		initialize();
		onlineList = new ArrayList<>();
		frmChatting.setVisible(true);
		frame.setVisible(false);
		frame.dispose();
		
		setUpSocket();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChatting = new JFrame();
		frmChatting.getContentPane().setBackground(Color.WHITE);
		frmChatting.getContentPane().setForeground(Color.WHITE);
		frmChatting.setFont(new Font("Source Code Pro", Font.PLAIN, 12));
		frmChatting.setResizable(false);
		frmChatting.setTitle("Chatting " + id);
		frmChatting.setBounds(100, 100, 440, 545);
		frmChatting.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChatting.getContentPane().setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(null);
		panel_1.setAutoscrolls(true);
		panel_1.setBounds(205, 0, 230, 506);
		panel_1.setPreferredSize(new Dimension(8, 10));
		panel_1.setBackground(Color.WHITE);
		frmChatting.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(0, 411, 230, 95);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setBounds(10, 11, 199, 37);
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Source Code Pro", Font.PLAIN, 11));
		textArea.setColumns(5);
		textArea.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_2.add(textArea);
		
		JButton btnNewButton = new JButton("Gửi");btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String messageContent = textArea.getText();
//		        if(messageContent.isEmpty()){
//		            JOptionPane.showMessageDialog(rootPane, "Bạn chưa nhập tin nhắn");
//		            return;
//		        }

				try {
//		                String[] parner = ((String)jComboBox1.getSelectedItem()).split(" ");
					write("1|"+"1|"+messageContent);
//		                textArea1.setText(textArea.getText()+ "Bạn (tới Client Sever"): "+ messageContent+ "\n");
//		                textArea1.setCaretPosition(textArea.getDocument().getLength());
				} catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}

				textArea.setText("");
			}
		});
		btnNewButton.setFont(new Font("Source Code Pro Black", Font.PLAIN, 11));
		btnNewButton.setBounds(10, 50, 199, 37);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBorder(null);
		btnNewButton.setBackground(new Color(30, 144, 255));
		panel_2.add(btnNewButton);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(null);
		panel_3.setBackground(Color.DARK_GRAY);
		panel_3.setBounds(0, 0, 220, 34);
		panel_1.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("@username");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Source Code Pro ExtraBold", Font.BOLD, 11));
		lblNewLabel.setBounds(10, 10, 136, 14);
		panel_3.add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("Block");
		btnNewButton_1.setBorder(UIManager.getBorder("Button.border"));
		btnNewButton_1.setBackground(new Color(255, 69, 0));
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setFont(new Font("Source Code Pro Black", Font.BOLD, 11));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(574, 6, 90, 23);
		panel_3.add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("Spam");
		btnNewButton_1_1.setForeground(new Color(255, 0, 0));
		btnNewButton_1_1.setFont(new Font("Source Code Pro Black", Font.BOLD, 11));
		btnNewButton_1_1.setBorder(null);
		btnNewButton_1_1.setBackground(new Color(100, 149, 237));
		btnNewButton_1_1.setBounds(474, 6, 90, 23);
		panel_3.add(btnNewButton_1_1);
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(0, 0, 0, 2, (Color) new Color(192, 192, 192)));
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 206, 506);
		frmChatting.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(null);
		panel_4.setBackground(new Color(30, 144, 255));
		panel_4.setBounds(0, 0, 229, 34);
		panel.add(panel_4);
		panel_4.setLayout(null);
		
		JLabel lblusernameabc = new JLabel("Danh sách");
		lblusernameabc.setForeground(new Color(255, 255, 255));
		lblusernameabc.setBounds(10, 10, 120, 14);
		lblusernameabc.setFont(new Font("Source Code Pro ExtraBold", Font.BOLD, 11));
		panel_4.add(lblusernameabc);
		
		JButton btnngXut = new JButton("Đăng xuất");
		btnngXut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnngXut.setForeground(Color.WHITE);
		btnngXut.setFont(new Font("Source Code Pro Black", Font.PLAIN, 11));
		btnngXut.setBorder(null);
		btnngXut.setBackground(new Color(255, 0, 0));
		btnngXut.setBounds(10, 458, 186, 37);
		panel.add(btnngXut);
		
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
                        // Gửi yêu cầu kết nối tới Server đang lắng nghe
                        // trên máy 'localhost' cổng 7777.
                        socketOfClient = new Socket("127.0.0.1", 7777);
                        System.out.println("Kết nối thành công!");
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
