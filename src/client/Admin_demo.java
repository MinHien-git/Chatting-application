package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.awt.SystemColor;

public class Admin_demo {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_1;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_13;
	private JTextField textField_14;
	private JTextField textField_15;
	private JTextField textField_16;
	private JTextField textField_17;
	private JTextField textField_18;
	private JTextField textField_19;
	private JTextField textField_20;
	private JTextField textField_21;
	private JTextField textField_22;
	private JTextField textField_23;
	private JTextField textField_24;
	private JTextField textField_25;
	private JTextField textField_26;
	private JTextField textField_27;
	private JTextField textField_28;
	private JTextField textField_29;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Admin_demo window = new Admin_demo();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Admin_demo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 916, 991);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(47, 21, 61, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("get");
		btnNewButton.setBackground(SystemColor.activeCaption);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(154, 47, 61, 23);
		panel.add(btnNewButton);
		
		JList list = new JList();
		list.setBounds(10, 81, 253, 118);
		panel.add(list);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(177, 21, 46, 20);
		panel.add(textField_2);
		
		JLabel lblNewLabel = new JLabel("email");
		lblNewLabel.setBounds(10, 24, 46, 14);
		panel.add(lblNewLabel);
		
		JLabel lblTrngThi = new JLabel("Trạng thái");
		lblTrngThi.setBounds(118, 24, 61, 14);
		panel.add(lblTrngThi);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(264, 21, 61, 20);
		panel.add(textField_1);
		
		JLabel lblTn = new JLabel("Tên");
		lblTn.setBounds(233, 24, 46, 14);
		panel.add(lblTn);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("By name");
		chckbxNewCheckBox.setBounds(10, 47, 76, 23);
		panel.add(chckbxNewCheckBox);
		
		JCheckBox chckbxByDate = new JCheckBox("By date");
		chckbxByDate.setBounds(90, 47, 70, 23);
		panel.add(chckbxByDate);
		
		JList list_1 = new JList();
		list_1.setBounds(10, 238, 253, 118);
		panel.add(list_1);
		
		JButton btnLyDsng = new JButton("Lấy ds đăng nhập");
		btnLyDsng.setBackground(SystemColor.activeCaption);
		btnLyDsng.setForeground(Color.WHITE);
		btnLyDsng.setBorder(UIManager.getBorder("Button.border"));
		btnLyDsng.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnLyDsng.setBounds(10, 210, 117, 23);
		panel.add(btnLyDsng);
		
		JList list_2 = new JList();
		list_2.setBounds(10, 464, 253, 118);
		panel.add(list_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(264, 386, 61, 20);
		panel.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(177, 386, 46, 20);
		panel.add(textField_4);
		
		JLabel lblTn_1 = new JLabel("Tên");
		lblTn_1.setBounds(233, 389, 46, 14);
		panel.add(lblTn_1);
		
		JLabel lblTrngThi_1 = new JLabel("Trạng thái");
		lblTrngThi_1.setBounds(118, 389, 61, 14);
		panel.add(lblTrngThi_1);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(47, 386, 61, 20);
		panel.add(textField_5);
		
		JLabel lblIdGroup = new JLabel("id group");
		lblIdGroup.setBounds(10, 389, 46, 14);
		panel.add(lblIdGroup);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("By name");
		chckbxNewCheckBox_1.setBounds(10, 417, 76, 23);
		panel.add(chckbxNewCheckBox_1);
		
		JCheckBox chckbxByDate_1 = new JCheckBox("By date");
		chckbxByDate_1.setBounds(90, 417, 70, 23);
		panel.add(chckbxByDate_1);
		
		JButton btnNewButton_1 = new JButton("get");
		btnNewButton_1.setBackground(SystemColor.activeCaption);
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBorder(UIManager.getBorder("Button.border"));
		btnNewButton_1.setBounds(166, 417, 49, 23);
		panel.add(btnNewButton_1);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(517, 227, 61, 20);
		panel.add(textField_6);
		
		JLabel lblId = new JLabel("id");
		lblId.setBounds(496, 230, 46, 14);
		panel.add(lblId);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(530, 259, 74, 20);
		panel.add(textField_7);
		
		JButton btnThm = new JButton("Xoá");
		btnThm.setForeground(SystemColor.window);
		btnThm.setBackground(SystemColor.activeCaption);
		btnThm.setBorder(UIManager.getBorder("Button.border"));
		btnThm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnThm.setBounds(588, 226, 76, 23);
		panel.add(btnThm);
		
		JLabel lblName = new JLabel("name");
		lblName.setBounds(496, 262, 34, 14);
		panel.add(lblName);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(628, 259, 61, 20);
		panel.add(textField_8);
		
		JLabel lblPw = new JLabel("pw");
		lblPw.setBounds(614, 262, 23, 14);
		panel.add(lblPw);
		
		JButton btnThm_2 = new JButton("Thêm");
		btnThm_2.setBackground(SystemColor.activeCaption);
		btnThm_2.setForeground(Color.WHITE);
		btnThm_2.setBorder(UIManager.getBorder("Button.border"));
		btnThm_2.setBounds(699, 258, 76, 23);
		panel.add(btnThm_2);
		
		textField_11 = new JTextField();
		textField_11.setColumns(10);
		textField_11.setBounds(527, 290, 61, 20);
		panel.add(textField_11);
		
		JLabel lblSex = new JLabel("sex");
		lblSex.setBounds(506, 293, 23, 14);
		panel.add(lblSex);
		
		textField_12 = new JTextField();
		textField_12.setColumns(10);
		textField_12.setBounds(619, 290, 61, 20);
		panel.add(textField_12);
		
		JLabel lblBirth = new JLabel("birth");
		lblBirth.setBounds(598, 293, 23, 14);
		panel.add(lblBirth);
		
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBounds(530, 336, 74, 20);
		panel.add(textField_9);
		
		JLabel lblName_1 = new JLabel("name");
		lblName_1.setBounds(496, 339, 34, 14);
		panel.add(lblName_1);
		
		JLabel lblPw_1 = new JLabel("pw");
		lblPw_1.setBounds(614, 339, 23, 14);
		panel.add(lblPw_1);
		
		textField_10 = new JTextField();
		textField_10.setColumns(10);
		textField_10.setBounds(628, 336, 61, 20);
		panel.add(textField_10);
		
		JLabel lblSex_1 = new JLabel("sex");
		lblSex_1.setBounds(496, 371, 23, 14);
		panel.add(lblSex_1);
		
		textField_13 = new JTextField();
		textField_13.setColumns(10);
		textField_13.setBounds(517, 368, 61, 20);
		panel.add(textField_13);
		
		JLabel lblBirth_1 = new JLabel("birth");
		lblBirth_1.setBounds(588, 371, 23, 14);
		panel.add(lblBirth_1);
		
		textField_14 = new JTextField();
		textField_14.setColumns(10);
		textField_14.setBounds(609, 368, 61, 20);
		panel.add(textField_14);
		
		JButton btnThm_2_1 = new JButton("Sửa");
		btnThm_2_1.setBackground(SystemColor.activeCaption);
		btnThm_2_1.setForeground(Color.WHITE);
		btnThm_2_1.setBorder(UIManager.getBorder("Button.border"));
		btnThm_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnThm_2_1.setBounds(699, 335, 76, 23);
		panel.add(btnThm_2_1);
		
		JList list_2_1 = new JList();
		list_2_1.setBounds(12, 625, 179, 97);
		panel.add(list_2_1);
		
		JLabel lblIdGroup_1 = new JLabel("id group");
		lblIdGroup_1.setBounds(12, 597, 46, 14);
		panel.add(lblIdGroup_1);
		
		textField_15 = new JTextField();
		textField_15.setColumns(10);
		textField_15.setBounds(49, 594, 61, 20);
		panel.add(textField_15);
		
		JButton btnNewButton_1_1 = new JButton("get admin");
		btnNewButton_1_1.setBackground(SystemColor.activeCaption);
		btnNewButton_1_1.setForeground(Color.WHITE);
		btnNewButton_1_1.setBorder(UIManager.getBorder("Button.border"));
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1_1.setBounds(118, 591, 87, 23);
		panel.add(btnNewButton_1_1);
		
		JButton btnNewButton_1_1_1 = new JButton("get users");
		btnNewButton_1_1_1.setBackground(SystemColor.activeCaption);
		btnNewButton_1_1_1.setForeground(Color.WHITE);
		btnNewButton_1_1_1.setBorder(UIManager.getBorder("Button.border"));
		btnNewButton_1_1_1.setBounds(341, 591, 87, 23);
		panel.add(btnNewButton_1_1_1);
		
		textField_16 = new JTextField();
		textField_16.setColumns(10);
		textField_16.setBounds(280, 593, 61, 20);
		panel.add(textField_16);
		
		JLabel lblIdGroup_1_1 = new JLabel("id group");
		lblIdGroup_1_1.setBounds(235, 597, 46, 14);
		panel.add(lblIdGroup_1_1);
		
		JList list_2_1_1 = new JList();
		list_2_1_1.setBounds(235, 625, 179, 97);
		panel.add(list_2_1_1);
		
		JList list_2_2 = new JList();
		list_2_2.setBounds(496, 79, 341, 118);
		panel.add(list_2_2);
		
		textField_17 = new JTextField();
		textField_17.setColumns(10);
		textField_17.setBounds(663, 21, 46, 20);
		panel.add(textField_17);
		
		JLabel lblTrngThi_2 = new JLabel("To date");
		lblTrngThi_2.setBounds(604, 24, 61, 14);
		panel.add(lblTrngThi_2);
		
		textField_18 = new JTextField();
		textField_18.setColumns(10);
		textField_18.setBounds(533, 21, 61, 20);
		panel.add(textField_18);
		
		JLabel lblNewLabel_1 = new JLabel("From");
		lblNewLabel_1.setBounds(496, 24, 46, 14);
		panel.add(lblNewLabel_1);
		
		textField_19 = new JTextField();
		textField_19.setColumns(10);
		textField_19.setBounds(527, 48, 61, 20);
		panel.add(textField_19);
		
		JLabel lblTn_2 = new JLabel("Tên");
		lblTn_2.setBounds(496, 51, 46, 14);
		panel.add(lblTn_2);
		
		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("By name");
		chckbxNewCheckBox_2.setBounds(594, 47, 76, 23);
		panel.add(chckbxNewCheckBox_2);
		
		JCheckBox chckbxByDate_2 = new JCheckBox("By date");
		chckbxByDate_2.setBounds(674, 47, 70, 23);
		panel.add(chckbxByDate_2);
		
		JButton btnNewButton_2 = new JButton("get spam");
		btnNewButton_2.setBackground(SystemColor.activeCaption);
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.setBorder(UIManager.getBorder("Button.border"));
		btnNewButton_2.setBounds(750, 47, 87, 23);
		panel.add(btnNewButton_2);
		
		textField_20 = new JTextField();
		textField_20.setColumns(10);
		textField_20.setBounds(262, 50, 61, 20);
		panel.add(textField_20);
		
		JLabel lblNewLabel_1_1 = new JLabel("From");
		lblNewLabel_1_1.setBounds(225, 53, 46, 14);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblTrngThi_2_1 = new JLabel("To date");
		lblTrngThi_2_1.setBounds(333, 53, 61, 14);
		panel.add(lblTrngThi_2_1);
		
		textField_21 = new JTextField();
		textField_21.setColumns(10);
		textField_21.setBounds(392, 50, 46, 20);
		panel.add(textField_21);
		
		JList list_1_1 = new JList();
		list_1_1.setBounds(484, 474, 253, 118);
		panel.add(list_1_1);
		
		JButton btnLyDsng_2 = new JButton("Lấy ds đăng kí");
		btnLyDsng_2.setBackground(SystemColor.activeCaption);
		btnLyDsng_2.setForeground(Color.WHITE);
		btnLyDsng_2.setBorder(UIManager.getBorder("Button.border"));
		btnLyDsng_2.setBounds(484, 413, 117, 23);
		panel.add(btnLyDsng_2);
		
		textField_22 = new JTextField();
		textField_22.setColumns(10);
		textField_22.setBounds(644, 413, 61, 20);
		panel.add(textField_22);
		
		JLabel lblNewLabel_1_2 = new JLabel("From");
		lblNewLabel_1_2.setBounds(607, 416, 46, 14);
		panel.add(lblNewLabel_1_2);
		
		JLabel lblTrngThi_2_2 = new JLabel("To date");
		lblTrngThi_2_2.setBounds(715, 416, 61, 14);
		panel.add(lblTrngThi_2_2);
		
		textField_23 = new JTextField();
		textField_23.setColumns(10);
		textField_23.setBounds(774, 413, 46, 20);
		panel.add(textField_23);
		
		JCheckBox chckbxByDate_1_1 = new JCheckBox("By date");
		chckbxByDate_1_1.setBounds(564, 444, 70, 23);
		panel.add(chckbxByDate_1_1);
		
		JCheckBox chckbxNewCheckBox_1_1 = new JCheckBox("By name");
		chckbxNewCheckBox_1_1.setBounds(484, 444, 76, 23);
		panel.add(chckbxNewCheckBox_1_1);
		
		textField_24 = new JTextField();
		textField_24.setColumns(10);
		textField_24.setBounds(567, 603, 61, 20);
		panel.add(textField_24);
		
		JLabel lblTn_1_1 = new JLabel("Khoá tài khoản");
		lblTn_1_1.setBounds(486, 606, 98, 14);
		panel.add(lblTn_1_1);
		
		JButton btnNewButton_1_2_1 = new JButton("Khoá");
		btnNewButton_1_2_1.setBackground(SystemColor.activeCaption);
		btnNewButton_1_2_1.setForeground(Color.WHITE);
		btnNewButton_1_2_1.setBounds(640, 602, 69, 23);
		panel.add(btnNewButton_1_2_1);
		
		JButton btnNewButton_1_2_1_1 = new JButton("Mở khoá");
		btnNewButton_1_2_1_1.setBackground(SystemColor.activeCaption);
		btnNewButton_1_2_1_1.setForeground(Color.WHITE);
		btnNewButton_1_2_1_1.setBounds(638, 634, 87, 23);
		panel.add(btnNewButton_1_2_1_1);
		
		textField_25 = new JTextField();
		textField_25.setColumns(10);
		textField_25.setBounds(565, 635, 61, 20);
		panel.add(textField_25);
		
		JLabel lblTn_1_1_1 = new JLabel("Mở tài khoản");
		lblTn_1_1_1.setBounds(484, 638, 98, 14);
		panel.add(lblTn_1_1_1);
		
		JList list_1_1_1 = new JList();
		list_1_1_1.setBounds(484, 729, 253, 118);
		panel.add(list_1_1_1);
		
		JCheckBox chckbxNewCheckBox_1_1_1 = new JCheckBox("By name");
		chckbxNewCheckBox_1_1_1.setBounds(484, 699, 76, 23);
		panel.add(chckbxNewCheckBox_1_1_1);
		
		JCheckBox chckbxByDate_1_1_1 = new JCheckBox("By date");
		chckbxByDate_1_1_1.setBounds(564, 699, 70, 23);
		panel.add(chckbxByDate_1_1_1);
		
		textField_26 = new JTextField();
		textField_26.setColumns(10);
		textField_26.setBounds(644, 668, 61, 20);
		panel.add(textField_26);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("name");
		lblNewLabel_1_2_1.setBounds(607, 671, 46, 14);
		panel.add(lblNewLabel_1_2_1);
		
		JButton btnLyDsng_2_1 = new JButton("Lấy ds người dùng");
		btnLyDsng_2_1.setBackground(SystemColor.activeCaption);
		btnLyDsng_2_1.setForeground(Color.WHITE);
		btnLyDsng_2_1.setBorder(UIManager.getBorder("Button.border"));
		btnLyDsng_2_1.setBounds(484, 668, 117, 23);
		panel.add(btnLyDsng_2_1);
		
		JLabel lblTrngThi_2_2_1 = new JLabel("amount f");
		lblTrngThi_2_2_1.setBounds(715, 671, 61, 14);
		panel.add(lblTrngThi_2_2_1);
		
		textField_27 = new JTextField();
		textField_27.setColumns(10);
		textField_27.setBounds(774, 668, 46, 20);
		panel.add(textField_27);
		
		JList list_1_1_1_1 = new JList();
		list_1_1_1_1.setBounds(10, 761, 253, 64);
		panel.add(list_1_1_1_1);
		
		JButton btnLyDsng_2_1_1 = new JButton("Lấy SL đk");
		btnLyDsng_2_1_1.setBackground(SystemColor.activeCaption);
		btnLyDsng_2_1_1.setForeground(Color.WHITE);
		btnLyDsng_2_1_1.setBorder(UIManager.getBorder("Button.border"));
		btnLyDsng_2_1_1.setBounds(103, 733, 139, 23);
		panel.add(btnLyDsng_2_1_1);
		
		JLabel lblTrngThi_2_2_2 = new JLabel("Year");
		lblTrngThi_2_2_2.setBounds(10, 736, 61, 14);
		panel.add(lblTrngThi_2_2_2);
		
		textField_28 = new JTextField();
		textField_28.setColumns(10);
		textField_28.setBounds(47, 733, 46, 20);
		panel.add(textField_28);
		
		JList list_1_1_1_1_1 = new JList();
		list_1_1_1_1_1.setBounds(10, 864, 253, 64);
		panel.add(list_1_1_1_1_1);
		
		JButton btnLyDsng_2_1_1_1 = new JButton("Lấy SL HD");
		btnLyDsng_2_1_1_1.setBackground(SystemColor.activeCaption);
		btnLyDsng_2_1_1_1.setForeground(Color.WHITE);
		btnLyDsng_2_1_1_1.setBorder(UIManager.getBorder("Button.border"));
		btnLyDsng_2_1_1_1.setBounds(103, 836, 139, 23);
		panel.add(btnLyDsng_2_1_1_1);
		
		textField_29 = new JTextField();
		textField_29.setColumns(10);
		textField_29.setBounds(47, 836, 46, 20);
		panel.add(textField_29);
		
		JLabel lblTrngThi_2_2_2_1 = new JLabel("Active");
		lblTrngThi_2_2_2_1.setBounds(10, 839, 61, 14);
		panel.add(lblTrngThi_2_2_2_1);
	}
}
