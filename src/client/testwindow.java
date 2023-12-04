package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class testwindow {

	private JFrame frmClientDemo;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_12;
	private JTextField textField_11;
	private JTextField textField_13;
	private JTextField textField_14;
	private JTextField textField_15;
	private JTextField textField_16;
	private JTextField textField_18;
	private JTextField textField_17;
	private JTextField textField_19;
	private JTextField textField_20;
	private JTextField textField_21;
	private JTextField textField_22;
	private JTextField textField_23;
	private JTextField textField_24;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					testwindow window = new testwindow();
					window.frmClientDemo.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public testwindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmClientDemo = new JFrame();
		frmClientDemo.setTitle("User demo");
		frmClientDemo.setBounds(100, 100, 657, 588);
		frmClientDemo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frmClientDemo.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(53, 27, 35, 24);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Gửi");
		btnNewButton.setBounds(423, 28, 89, 23);
		panel.add(btnNewButton);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(181, 27, 232, 24);
		panel.add(textField_1);
		
		JLabel lblNewLabel = new JLabel("send to");
		lblNewLabel.setBounds(10, 32, 46, 14);
		panel.add(lblNewLabel);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(53, 62, 35, 24);
		panel.add(textField_2);
		
		JLabel lblNewLabel_1 = new JLabel("send to");
		lblNewLabel_1.setBounds(10, 67, 46, 14);
		panel.add(lblNewLabel_1);
		
		JButton btnThmBnB = new JButton("Thêm bạn bè");
		btnThmBnB.setBounds(181, 63, 110, 23);
		panel.add(btnThmBnB);
		
		JButton btnXoKtBn = new JButton("Xoá kết bạn");
		btnXoKtBn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnXoKtBn.setBounds(181, 98, 110, 23);
		panel.add(btnXoKtBn);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(53, 97, 35, 24);
		panel.add(textField_3);
		
		JLabel lblNewLabel_1_1 = new JLabel("send to");
		lblNewLabel_1_1.setBounds(10, 102, 46, 14);
		panel.add(lblNewLabel_1_1);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(74, 132, 35, 24);
		panel.add(textField_4);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("current user");
		lblNewLabel_1_1_1.setBounds(10, 136, 65, 14);
		panel.add(lblNewLabel_1_1_1);
		
		JButton btnXoTinNhn = new JButton("Xoá tin nhắn");
		btnXoTinNhn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnXoTinNhn.setBounds(202, 132, 110, 23);
		panel.add(btnXoTinNhn);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(125, 27, 46, 24);
		panel.add(textField_5);
		
		JLabel lblFrom = new JLabel("from");
		lblFrom.setBounds(97, 32, 46, 14);
		panel.add(lblFrom);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(125, 62, 46, 24);
		panel.add(textField_6);
		
		JLabel lblFrom_1 = new JLabel("from");
		lblFrom_1.setBounds(98, 67, 22, 14);
		panel.add(lblFrom_1);
		
		JLabel lblFrom_1_1 = new JLabel("from");
		lblFrom_1_1.setBounds(98, 102, 22, 14);
		panel.add(lblFrom_1_1);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(125, 97, 46, 24);
		panel.add(textField_7);
		
		JLabel lblFrom_1_1_1 = new JLabel("from");
		lblFrom_1_1_1.setBounds(119, 137, 22, 14);
		panel.add(lblFrom_1_1_1);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(146, 132, 46, 24);
		panel.add(textField_8);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("group name");
		lblNewLabel_1_1_1_1.setBounds(10, 171, 65, 14);
		panel.add(lblNewLabel_1_1_1_1);
		
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBounds(74, 167, 97, 24);
		panel.add(textField_9);
		
		textField_10 = new JTextField();
		textField_10.setColumns(10);
		textField_10.setBounds(247, 166, 46, 24);
		panel.add(textField_10);
		
		JLabel lblFrom_1_1_1_1 = new JLabel("created by");
		lblFrom_1_1_1_1.setBounds(181, 172, 52, 14);
		panel.add(lblFrom_1_1_1_1);
		
		JButton btnXoTinNhn_1 = new JButton("Tạo nhóm");
		btnXoTinNhn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnXoTinNhn_1.setBounds(303, 167, 110, 23);
		panel.add(btnXoTinNhn_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Dùng tên đăng nhập");
		lblNewLabel_1_2.setBounds(301, 67, 174, 14);
		panel.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Dùng tên đăng nhập");
		lblNewLabel_1_2_1.setBounds(301, 102, 174, 14);
		panel.add(lblNewLabel_1_2_1);
		
		JLabel lblNewLabel_1_2_2 = new JLabel("Dùng id");
		lblNewLabel_1_2_2.setBounds(518, 32, 65, 14);
		panel.add(lblNewLabel_1_2_2);
		
		JLabel lblNewLabel_1_2_2_1 = new JLabel("Dùng id");
		lblNewLabel_1_2_2_1.setBounds(322, 137, 65, 14);
		panel.add(lblNewLabel_1_2_2_1);
		
		textField_12 = new JTextField();
		textField_12.setColumns(10);
		textField_12.setBounds(42, 226, 46, 24);
		panel.add(textField_12);
		
		JLabel lblFrom_1_1_1_1_1 = new JLabel("id");
		lblFrom_1_1_1_1_1.setBounds(10, 231, 78, 14);
		panel.add(lblFrom_1_1_1_1_1);
		
		JButton btnXoTinNhn_1_1 = new JButton("Lấy bạn bè online");
		btnXoTinNhn_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnXoTinNhn_1_1.setBounds(97, 227, 152, 23);
		panel.add(btnXoTinNhn_1_1);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 261, 403, 74);
		panel.add(textArea);
		
		JLabel lblFrom_1_1_1_1_1_1 = new JLabel("id");
		lblFrom_1_1_1_1_1_1.setBounds(10, 201, 78, 14);
		panel.add(lblFrom_1_1_1_1_1_1);
		
		textField_11 = new JTextField();
		textField_11.setColumns(10);
		textField_11.setBounds(42, 196, 46, 24);
		panel.add(textField_11);
		
		JButton btnXoTinNhn_1_2 = new JButton("set user online");
		btnXoTinNhn_1_2.setBounds(97, 197, 136, 23);
		panel.add(btnXoTinNhn_1_2);
		
		textField_13 = new JTextField();
		textField_13.setColumns(10);
		textField_13.setBounds(279, 196, 46, 24);
		panel.add(textField_13);
		
		JLabel lblFrom_1_1_1_1_1_1_1 = new JLabel("id");
		lblFrom_1_1_1_1_1_1_1.setBounds(247, 201, 78, 14);
		panel.add(lblFrom_1_1_1_1_1_1_1);
		
		JButton btnXoTinNhn_1_2_1 = new JButton("set user offline");
		btnXoTinNhn_1_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnXoTinNhn_1_2_1.setBounds(334, 197, 136, 23);
		panel.add(btnXoTinNhn_1_2_1);
		
		textField_14 = new JTextField();
		textField_14.setColumns(10);
		textField_14.setBounds(74, 346, 35, 24);
		panel.add(textField_14);
		
		JLabel lblNewLabel_1_1_1_2 = new JLabel("current user");
		lblNewLabel_1_1_1_2.setBounds(10, 350, 65, 14);
		panel.add(lblNewLabel_1_1_1_2);
		
		textField_15 = new JTextField();
		textField_15.setColumns(10);
		textField_15.setBounds(146, 346, 46, 24);
		panel.add(textField_15);
		
		JButton btnBlockTk = new JButton("Block tk");
		btnBlockTk.setBounds(202, 346, 110, 23);
		panel.add(btnBlockTk);
		
		JLabel lblFrom_1_1_1_2 = new JLabel("from");
		lblFrom_1_1_1_2.setBounds(119, 351, 22, 14);
		panel.add(lblFrom_1_1_1_2);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("group id");
		lblNewLabel_1_1_1_1_1.setBounds(10, 386, 65, 14);
		panel.add(lblNewLabel_1_1_1_1_1);
		
		textField_16 = new JTextField();
		textField_16.setColumns(10);
		textField_16.setBounds(53, 381, 97, 24);
		panel.add(textField_16);
		
		JLabel lblFrom_1_1_1_1_2 = new JLabel("Tên mới");
		lblFrom_1_1_1_1_2.setBounds(160, 386, 52, 14);
		panel.add(lblFrom_1_1_1_1_2);
		
		JButton btnXoTinNhn_1_3 = new JButton("Đổi tên");
		btnXoTinNhn_1_3.setBounds(303, 382, 110, 23);
		panel.add(btnXoTinNhn_1_3);
		
		textField_18 = new JTextField();
		textField_18.setColumns(10);
		textField_18.setBounds(202, 381, 97, 24);
		panel.add(textField_18);
		
		JButton btnXoTinNhn_1_3_1 = new JButton("Thêm");
		btnXoTinNhn_1_3_1.setBounds(328, 411, 110, 23);
		panel.add(btnXoTinNhn_1_3_1);
		
		textField_17 = new JTextField();
		textField_17.setColumns(10);
		textField_17.setBounds(223, 411, 97, 24);
		panel.add(textField_17);
		
		JLabel lblFrom_1_1_1_1_2_1 = new JLabel("id thành viên");
		lblFrom_1_1_1_1_2_1.setBounds(160, 416, 73, 14);
		panel.add(lblFrom_1_1_1_1_2_1);
		
		textField_19 = new JTextField();
		textField_19.setColumns(10);
		textField_19.setBounds(53, 411, 97, 24);
		panel.add(textField_19);
		
		JLabel lblNewLabel_1_1_1_1_1_1 = new JLabel("group id");
		lblNewLabel_1_1_1_1_1_1.setBounds(10, 416, 65, 14);
		panel.add(lblNewLabel_1_1_1_1_1_1);
		
		JButton btnXoTinNhn_1_3_1_1 = new JButton("Xoá");
		btnXoTinNhn_1_3_1_1.setBounds(328, 445, 110, 23);
		panel.add(btnXoTinNhn_1_3_1_1);
		
		textField_20 = new JTextField();
		textField_20.setColumns(10);
		textField_20.setBounds(223, 445, 97, 24);
		panel.add(textField_20);
		
		JLabel lblFrom_1_1_1_1_2_1_1 = new JLabel("id thành viên");
		lblFrom_1_1_1_1_2_1_1.setBounds(160, 450, 73, 14);
		panel.add(lblFrom_1_1_1_1_2_1_1);
		
		textField_21 = new JTextField();
		textField_21.setColumns(10);
		textField_21.setBounds(53, 445, 97, 24);
		panel.add(textField_21);
		
		JLabel lblNewLabel_1_1_1_1_1_1_1 = new JLabel("group id");
		lblNewLabel_1_1_1_1_1_1_1.setBounds(10, 450, 65, 14);
		panel.add(lblNewLabel_1_1_1_1_1_1_1);
		
		JLabel lblGroupId = new JLabel("Group id");
		lblGroupId.setBounds(10, 484, 46, 14);
		panel.add(lblGroupId);
		
		textField_22 = new JTextField();
		textField_22.setColumns(10);
		textField_22.setBounds(53, 479, 35, 24);
		panel.add(textField_22);
		
		JLabel lblFrom_2 = new JLabel("from");
		lblFrom_2.setBounds(97, 484, 46, 14);
		panel.add(lblFrom_2);
		
		textField_23 = new JTextField();
		textField_23.setColumns(10);
		textField_23.setBounds(125, 479, 46, 24);
		panel.add(textField_23);
		
		textField_24 = new JTextField();
		textField_24.setColumns(10);
		textField_24.setBounds(181, 479, 232, 24);
		panel.add(textField_24);
		
		JButton btnNewButton_1 = new JButton("Gửi");
		btnNewButton_1.setBounds(423, 480, 89, 23);
		panel.add(btnNewButton_1);
		
		JLabel lblNewLabel_1_2_2_2 = new JLabel("Dùng id");
		lblNewLabel_1_2_2_2.setBounds(518, 484, 65, 14);
		panel.add(lblNewLabel_1_2_2_2);
	}
}
