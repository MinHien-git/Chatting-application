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

public class chatting {

	private JFrame frmChatting;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					chatting window = new chatting();
					window.frmChatting.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public chatting() {
		initialize();
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
		frmChatting.setType(Type.UTILITY);
		frmChatting.setTitle("Chatting");
		frmChatting.setBounds(100, 100, 919, 545);
		frmChatting.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChatting.getContentPane().setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(null);
		panel_1.setAutoscrolls(true);
		panel_1.setBounds(229, 0, 674, 506);
		panel_1.setPreferredSize(new Dimension(8, 10));
		panel_1.setBackground(Color.WHITE);
		frmChatting.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(10, 454, 654, 52);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 8, 534, 37);
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Source Code Pro", Font.PLAIN, 11));
		textArea.setColumns(5);
		textArea.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_2.add(textArea);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.setFont(new Font("Source Code Pro Black", Font.PLAIN, 11));
		btnNewButton.setBounds(554, 8, 94, 37);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBorder(null);
		btnNewButton.setBackground(new Color(30, 144, 255));
		panel_2.add(btnNewButton);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(null);
		panel_3.setBackground(new Color(100, 149, 237));
		panel_3.setBounds(0, 0, 674, 34);
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
		panel.setBounds(0, 0, 229, 506);
		frmChatting.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(null);
		panel_4.setBackground(new Color(30, 144, 255));
		panel_4.setBounds(0, 0, 229, 34);
		panel.add(panel_4);
		panel_4.setLayout(null);
		
		JLabel lblusernameabc = new JLabel("@usernameABC");
		lblusernameabc.setForeground(new Color(255, 255, 255));
		lblusernameabc.setBounds(10, 10, 120, 14);
		lblusernameabc.setFont(new Font("Source Code Pro ExtraBold", Font.BOLD, 11));
		panel_4.add(lblusernameabc);
		
		JButton btnNewButton_1_2 = new JButton("logout");
		btnNewButton_1_2.setBounds(10, 472, 84, 23);
		panel.add(btnNewButton_1_2);
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1_2.setForeground(Color.WHITE);
		btnNewButton_1_2.setFont(new Font("Source Code Pro Black", Font.BOLD, 11));
		btnNewButton_1_2.setBorder(null);
		btnNewButton_1_2.setBackground(new Color(255, 69, 0));
	}
}
