package client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import javax.swing.*;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import java.awt.Dimension;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import server.Server;
import server.ServerThread;
import server.ServerThreadBus;

public class login {

	public JFrame frmLogin;
	private JTextField email;
	private JTextField password;

	/**
	 * Create the application.
	 */
	public login() {
		initialize();
	}

	public static String generateRandomPassword(int length) {
		String validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

		SecureRandom random = new SecureRandom();
		StringBuilder password = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			int randomIndex = random.nextInt(validChars.length());
			password.append(validChars.charAt(randomIndex));
		}

		return password.toString();
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
							onlineUsers onlList = new onlineUsers(Application.getApplicationFrame(), user);
							friends flist = new friends(user);
							chatting c = new chatting();
							globalChatHistory gbc = new globalChatHistory();
							home h = new home(Application.getApplicationFrame(), onlList, flist, c, gbc);
							try {
								Application.write("Online|"+user.getId());
							}catch (IOException ex) {
								System.out.println("An error occurred");
								ex.printStackTrace();
							}
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

		JButton resetPW = new JButton("Reset Password");
		resetPW.setBorder(UIManager.getBorder("Button.border"));
		resetPW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String fromEmail = "chattingapplication21ktpm4@gmail.com";
				final String password = "testpassword123";
				String toEmail = JOptionPane.showInputDialog("Please specify your email for password recovery");

				System.out.println("TLS Email Start");
				Properties props = new Properties();
				props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
				props.put("mail.smtp.port", "587"); //TLS Port
				props.put("mail.smtp.auth", "true"); //enable authentication
				props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

				//create Authenticator object to pass in Session.getInstance argument
				Authenticator auth = new Authenticator() {
					//override the getPasswordAuthentication method
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(fromEmail, password);
					}
				};
				Session session = Session.getInstance(props,auth);
				try {
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(fromEmail)); // Sender's email
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); // Recipient's email
					message.setSubject("Password Reset - Chatting Application");

					String messageContent = """
							Dear user,
							You received this message as a request to reset your password
							Your new password is: """;
					String newPw = generateRandomPassword(15);
					UserAuthentication.resetPassword(User.hashPassword(newPw), toEmail);
					messageContent += newPw + "\nIf you did not make this request, please ignore this email. \nBest regards,";
					message.setText(messageContent);

					Transport.send(message);
					System.out.println("Email sent successfully!");
				} catch (MessagingException me) {
					System.out.println("Failed to send email");
					me.printStackTrace();
				}
			}
		});
		resetPW.setForeground(Color.WHITE);
		resetPW.setBackground(Color.RED);
		resetPW.setFont(new Font("Source Code Pro Black", Font.PLAIN, 11));
		resetPW.setBounds(46, 342, 203, 38);
		panel_1.add(resetPW);

		frmLogin.setBounds(100, 100, 605, 476);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}