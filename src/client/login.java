package client;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JSplitPane;
import java.awt.Color;
import javax.swing.*;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
//import java.security.Identity;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.UIManager;
import java.awt.Dimension;
import java.awt.Window.Type;

public class login {

	public JFrame frmLogin;
	private JTextField email;
	private JTextField password;
	private UserAuthentication auth = new UserAuthentication();

	/**
	 * Create the application.
	 */
	public login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setForeground(Color.BLACK);
		frmLogin.setTitle("Login");
		frmLogin.setFont(new Font("Source Code Pro Light", Font.PLAIN, 12));
		frmLogin.getContentPane().setBackground(Color.WHITE);
		frmLogin.setBackground(Color.WHITE);
		frmLogin.getContentPane().setFont(new Font("Source Code Pro Medium", Font.PLAIN, 11));
		frmLogin.getContentPane().setLayout(new BoxLayout(frmLogin.getContentPane(), BoxLayout.X_AXIS));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(135, 206, 250));
		frmLogin.getContentPane().add(panel);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		frmLogin.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setFont(new Font("Source Code Pro ExtraBold", Font.PLAIN, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 70, 290, 28);
		panel_1.add(lblNewLabel);

		email = new JTextField();
		email.setFont(new Font("Source Code Pro", Font.PLAIN, 11));
		email.setPreferredSize(new Dimension(7, 22));
		email.setBounds(46, 129, 202, 28);
		panel_1.add(email);
		email.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("email:");
		lblNewLabel_1.setFont(new Font("Source Code Pro", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(46, 108, 202, 14);
		panel_1.add(lblNewLabel_1);

		password = new JTextField();
		password.setFont(new Font("Source Code Pro", Font.PLAIN, 11));
		password.setPreferredSize(new Dimension(7, 22));
		password.setColumns(10);
		password.setBounds(46, 189, 202, 28);
		panel_1.add(password);

		JLabel lblNewLabel_1_1 = new JLabel("password:");
		lblNewLabel_1_1.setFont(new Font("Source Code Pro", Font.PLAIN, 11));
		lblNewLabel_1_1.setBounds(46, 168, 202, 14);
		panel_1.add(lblNewLabel_1_1);

		JButton btnNewButton = new JButton("Login");
		btnNewButton.setBorder(UIManager.getBorder("Button.border"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(password.getText() != "" && email.getText() != "") {
					String hashedPW = User.hashPassword(password.getText());
					if (hashedPW == null) hashedPW = password.getText();

					User user = new User(email.getText(),hashedPW);

					String _id = user.LogIn();
					if(!_id.equals("")) {
						if (user.update())
						{
							System.out.println(user.getId());
							System.out.println(user.getName());
							System.out.println(user.getEmail());
							System.out.println(user.getBlockList());
							System.out.println(user.getFriends());
							System.out.println(user.getOnlineList());
							System.out.println(user.isAdmin());
							System.out.println(user.isLocked());
							System.out.println(user.isOnline());

							onlineUsers onlList = new onlineUsers(Application.getApplicationFrame(), user);
							friends flist = new friends();
							chatting c = new chatting();
							globalChatHistory gbc = new globalChatHistory();
							home h = new home(Application.getApplicationFrame(), onlList, flist, c, gbc);
							Application.getApplicationFrame().setVisible(true);
							frmLogin.dispose();
						}
					}
					else {
						JOptionPane.showMessageDialog(Application.getApplicationFrame(), "Invalid credentials, try again");
					}
				}
			}
		});
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(new Color(30, 144, 255));
		btnNewButton.setFont(new Font("Source Code Pro Black", Font.PLAIN, 11));
		btnNewButton.setBounds(46, 246, 203, 38);
		panel_1.add(btnNewButton);

		JButton btnlogin = new JButton("Register");
		btnlogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				register register = new register();
				register.frmRegister.setVisible(true);
				frmLogin.dispose();
			}
		});
		btnlogin.setForeground(new Color(30, 144, 255));
		btnlogin.setFont(new Font("Source Code Pro Black", Font.PLAIN, 11));
		btnlogin.setBackground(Color.WHITE);
		btnlogin.setBounds(46, 294, 203, 38);
		panel_1.add(btnlogin);
		frmLogin.setBounds(100, 100, 605, 476);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}