package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.GraphicsConfigTemplate;

public class Admin_demo {
	private Thread thread;
    private BufferedWriter os;
    private BufferedReader is;
    private Socket socketOfClient;
	private JFrame frame;
	private JTextField GetUser_EMAIL;
	private JTextField GetUser_NAME;
	private JTextField GETGROUP_NAME;
	private JTextField DELETEACCOUNT_name;
	private JTextField ADDACCOUNT_USERNAME;
	private JTextField ADDACCOUNT_PW;
	private JTextField ADDACCOUNT_BIRTH;
	private JTextField EDITACCOUNT_NAME;
	private JTextField EDITACCOUNT_PW;
	private JTextField EDITACCOUNT_BIRTH;
	private JTextField GETGROUPADMIN_ID;
	private JTextField GETGROUPMEMBER_ID;
	private JTextField GETSPAM_DATETO;
	private JTextField GETSPAM_DATEFROM;
	private JTextField GETSPAM_NAME;
	private JTextField GetUser_FROMDATE;
	private JTextField GetUser_TODATE;
	private JTextField GETREGISTERLIST_DATE_FROM;
	private JTextField GETREGISTERLIST_DATE_TO;
	private JTextField LOCKACCOUNT_NAME;
	private JTextField UNLOCKACCOUNT_NAME;
	private JTextField USERSFRIEND_NAME;
	private JTextField USERSFRIEND_AMOUNT_FRIEND;
	private JTextField GETREGISTERAMOUNT_YEAR;
	private JTextField GETACTIVEAMOUNT_YEAR;

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
		setUpSocket();
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
		
		GetUser_EMAIL = new JTextField();
		GetUser_EMAIL.setBounds(47, 21, 61, 20);
		panel.add(GetUser_EMAIL);
		GetUser_EMAIL.setColumns(10);
		
		JButton GetUserButton = new JButton("Get users");
		GetUserButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
		GetUserButton.setBackground(SystemColor.activeCaption);
		GetUserButton.setForeground(SystemColor.inactiveCaptionText);
		JCheckBox GetUser_STATUS = new JCheckBox("By Status");
		GetUser_STATUS.setBounds(121, 20, 91, 23);
		panel.add(GetUser_STATUS);
		JCheckBox GetUser_SORTNAME = new JCheckBox("By name");
		GetUser_SORTNAME.setBounds(10, 47, 76, 23);
		panel.add(GetUser_SORTNAME);
		
		JCheckBox GetUser_SORTDATE = new JCheckBox("By date");
		GetUser_SORTDATE.setBounds(90, 48, 70, 23);
		panel.add(GetUser_SORTDATE);
		
		
		//Get users
		GetUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nameString = GetUser_EMAIL.getText();
				int account_status = GetUser_STATUS.isSelected() ? 1 : 0;
				int sort_by_name = GetUser_SORTNAME.isSelected() ? 1 : 0;
				int sort_by_date = GetUser_SORTDATE.isSelected() ? 1 : 0;
				String name = GetUser_NAME.getText();
				String fromDateString = GetUser_FROMDATE.getText();
				String toDateString = GetUser_TODATE.getText();
				
				try {
					write("AdminGetUser|"+nameString+"|"+account_status+"|"+sort_by_name + "|" + sort_by_date +"|"+name+"|"+fromDateString+"|"+toDateString);
					
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
			}
		});
		GetUserButton.setBounds(200, 77, 110, 23);
		panel.add(GetUserButton);
		
		JList GetUser_LIST = new JList();
		GetUser_LIST.setBounds(10, 102, 253, 97);
		panel.add(GetUser_LIST);
		
		JLabel lblNewLabel = new JLabel("email");
		lblNewLabel.setBounds(10, 24, 46, 14);
		panel.add(lblNewLabel);
		
		GetUser_NAME = new JTextField();
		GetUser_NAME.setColumns(10);
		GetUser_NAME.setBounds(197, 49, 61, 20);
		panel.add(GetUser_NAME);
		
		JLabel lblTn = new JLabel("Tên");
		lblTn.setBounds(166, 52, 46, 14);
		panel.add(lblTn);
		
		
		
		JList GetLOGIN_ACTIVITIES_LIST = new JList();
		GetLOGIN_ACTIVITIES_LIST.setBounds(10, 238, 253, 118);
		panel.add(GetLOGIN_ACTIVITIES_LIST);
		
		JButton GetLOGIN_ACTIVITIES_BTN = new JButton("Lấy ds đăng nhập");
		GetLOGIN_ACTIVITIES_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
		GetLOGIN_ACTIVITIES_BTN.setBackground(SystemColor.activeCaption);
		GetLOGIN_ACTIVITIES_BTN.setForeground(SystemColor.inactiveCaptionText);
		GetLOGIN_ACTIVITIES_BTN.setBorder(UIManager.getBorder("Button.border"));
		
		
		//GET LOGIN ACTIVITIES
		GetLOGIN_ACTIVITIES_BTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					write("AdminGetLoginActivities");
					
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
			}
		});
		GetLOGIN_ACTIVITIES_BTN.setBounds(10, 210, 253, 23);
		panel.add(GetLOGIN_ACTIVITIES_BTN);
		
		JList list_2 = new JList();
		list_2.setBounds(10, 464, 253, 118);
		panel.add(list_2);
		
		GETGROUP_NAME = new JTextField();
		GETGROUP_NAME.setColumns(10);
		GETGROUP_NAME.setBounds(33, 388, 61, 20);
		panel.add(GETGROUP_NAME);
		
		JLabel lblTn_1 = new JLabel("Tên");
		lblTn_1.setBounds(10, 391, 46, 14);
		panel.add(lblTn_1);
		
		JCheckBox GETGROUP_SORT_NAME = new JCheckBox("By name");
		GETGROUP_SORT_NAME.setBounds(90, 385, 76, 23);
		panel.add(GETGROUP_SORT_NAME);
		
		JCheckBox GETGROUP_SORT_DATE = new JCheckBox("By date");
		GETGROUP_SORT_DATE.setBounds(170, 385, 70, 23);
		panel.add(GETGROUP_SORT_DATE);
		
		JButton GETGROUP_BTN = new JButton("get group");
		
		
		//GET GROUP
		GETGROUP_BTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String groupString = GETGROUP_NAME.getText();
				int account_status = GETGROUP_SORT_NAME.isSelected() ? 1 : 0;
				int sort_by_name = GETGROUP_SORT_DATE.isSelected() ? 1 : 0;

				try {
					write("AdminGetGroup|"+groupString+"|"+account_status+"|"+sort_by_name);
					
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
			}
		});
		GETGROUP_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
		GETGROUP_BTN.setBackground(SystemColor.activeCaption);
		GETGROUP_BTN.setForeground(SystemColor.inactiveCaptionText);
		GETGROUP_BTN.setBorder(UIManager.getBorder("Button.border"));
		GETGROUP_BTN.setBounds(10, 419, 253, 23);
		panel.add(GETGROUP_BTN);
		
		DELETEACCOUNT_name = new JTextField();
		DELETEACCOUNT_name.setColumns(10);
		DELETEACCOUNT_name.setBounds(538, 226, 61, 20);
		panel.add(DELETEACCOUNT_name);
		
		JLabel DELETEACCOUNTLABEL = new JLabel("name");
		DELETEACCOUNTLABEL.setBounds(496, 230, 46, 14);
		panel.add(DELETEACCOUNTLABEL);
		
		ADDACCOUNT_USERNAME = new JTextField();
		ADDACCOUNT_USERNAME.setColumns(10);
		ADDACCOUNT_USERNAME.setBounds(530, 259, 74, 20);
		panel.add(ADDACCOUNT_USERNAME);
		
		JButton DELETEACCOUNT_BTN = new JButton("Xoá");
		DELETEACCOUNT_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
		DELETEACCOUNT_BTN.setForeground(SystemColor.inactiveCaptionText);
		DELETEACCOUNT_BTN.setBackground(SystemColor.activeCaption);
		DELETEACCOUNT_BTN.setBorder(UIManager.getBorder("Button.border"));
		
		
		//DELETE ACCOUNT
		DELETEACCOUNT_BTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String groupString = DELETEACCOUNT_name.getText();
				
				try {
					write("AdminDeleteAccount|"+groupString);
					
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
			}
		});
		DELETEACCOUNT_BTN.setBounds(609, 225, 76, 23);
		panel.add(DELETEACCOUNT_BTN);
		
		JLabel lblName = new JLabel("name");
		lblName.setBounds(496, 262, 34, 14);
		panel.add(lblName);
		
		ADDACCOUNT_PW = new JTextField();
		ADDACCOUNT_PW.setColumns(10);
		ADDACCOUNT_PW.setBounds(628, 259, 61, 20);
		panel.add(ADDACCOUNT_PW);
		
		JLabel lblPw = new JLabel("pw");
		lblPw.setBounds(610, 262, 23, 14);
		panel.add(lblPw);
		
		JButton ADDACCOUNT_BTN = new JButton("Thêm");
		JCheckBox ADDACCOUNT_SX = new JCheckBox("sex");
		ADDACCOUNT_SX.setBounds(496, 289, 91, 23);
		panel.add(ADDACCOUNT_SX);
		
		JCheckBox EDITACCOUNT_SX = new JCheckBox("sex");
		EDITACCOUNT_SX.setBounds(491, 367, 91, 23);
		panel.add(EDITACCOUNT_SX);

		ADDACCOUNT_BIRTH = new JTextField();
		ADDACCOUNT_BIRTH.setColumns(10);
		ADDACCOUNT_BIRTH.setBounds(628, 290, 61, 20);
		panel.add(ADDACCOUNT_BIRTH);
		
		
		//ADD ACCOUNT
		ADDACCOUNT_BTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = ADDACCOUNT_USERNAME.getText();
				String pw = ADDACCOUNT_PW.getText();
				int sxString =ADDACCOUNT_SX.isSelected() ? 1 : 0;
				String birth = ADDACCOUNT_BIRTH.getText();
				try {
					write("AdminAddAccount|"+name + pw + sxString+birth);
					
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
			}
		});
		
		ADDACCOUNT_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
		ADDACCOUNT_BTN.setBackground(SystemColor.activeCaption);
		ADDACCOUNT_BTN.setForeground(SystemColor.inactiveCaptionText);
		ADDACCOUNT_BTN.setBorder(UIManager.getBorder("Button.border"));
		ADDACCOUNT_BTN.setBounds(699, 258, 76, 23);
		panel.add(ADDACCOUNT_BTN);
		
		
		JLabel lblBirth = new JLabel("birth");
		lblBirth.setBounds(598, 293, 23, 14);
		panel.add(lblBirth);
		
		EDITACCOUNT_NAME = new JTextField();
		EDITACCOUNT_NAME.setColumns(10);
		EDITACCOUNT_NAME.setBounds(530, 336, 74, 20);
		panel.add(EDITACCOUNT_NAME);
		
		JLabel lblName_1 = new JLabel("name");
		lblName_1.setBounds(496, 339, 34, 14);
		panel.add(lblName_1);
		
		JLabel lblPw_1 = new JLabel("pw");
		lblPw_1.setBounds(610, 339, 23, 14);
		panel.add(lblPw_1);
		
		EDITACCOUNT_PW = new JTextField();
		EDITACCOUNT_PW.setColumns(10);
		EDITACCOUNT_PW.setBounds(628, 336, 61, 20);
		panel.add(EDITACCOUNT_PW);
		
		JLabel lblBirth_1 = new JLabel("birth");
		lblBirth_1.setBounds(588, 371, 23, 14);
		panel.add(lblBirth_1);
		
		EDITACCOUNT_BIRTH = new JTextField();
		EDITACCOUNT_BIRTH.setColumns(10);
		EDITACCOUNT_BIRTH.setBounds(628, 368, 61, 20);
		panel.add(EDITACCOUNT_BIRTH);
		
		JButton EDITACCOUNT_BTN = new JButton("Sửa");
		EDITACCOUNT_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
		EDITACCOUNT_BTN.setBackground(SystemColor.activeCaption);
		EDITACCOUNT_BTN.setForeground(SystemColor.inactiveCaptionText);
		EDITACCOUNT_BTN.setBorder(UIManager.getBorder("Button.border"));
		
		
		//EDIT ACCOUNT
		EDITACCOUNT_BTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = EDITACCOUNT_NAME.getText();
				String pw = EDITACCOUNT_PW.getText();
				int sxString =EDITACCOUNT_SX.isSelected() ? 1 : 0;
				String birth = EDITACCOUNT_BIRTH.getText();
				try {
					write("AdminEditAccount|"+name + pw + sxString+birth);
					
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
			}
		});
		EDITACCOUNT_BTN.setBounds(699, 335, 76, 23);
		panel.add(EDITACCOUNT_BTN);
		
		JList list_2_1 = new JList();
		list_2_1.setBounds(12, 625, 179, 97);
		panel.add(list_2_1);
		
		JLabel lblIdGroup_1 = new JLabel("id group");
		lblIdGroup_1.setBounds(8, 597, 46, 14);
		panel.add(lblIdGroup_1);
		
		GETGROUPADMIN_ID = new JTextField();
		GETGROUPADMIN_ID.setColumns(10);
		GETGROUPADMIN_ID.setBounds(64, 594, 61, 20);
		panel.add(GETGROUPADMIN_ID);
		
		JButton GETGROUPADMIN_BTN = new JButton("get admin");
		GETGROUPADMIN_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
		GETGROUPADMIN_BTN.setBackground(SystemColor.activeCaption);
		GETGROUPADMIN_BTN.setForeground(SystemColor.inactiveCaptionText);
		GETGROUPADMIN_BTN.setBorder(UIManager.getBorder("Button.border"));
		
		
		//GET GROUP ADMIN
		GETGROUPADMIN_BTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = GETGROUPADMIN_ID.getText();
				try {
					write("AdminGetGroupAdmin|"+id);
					
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
			}
		});
		GETGROUPADMIN_BTN.setBounds(125, 593, 87, 23);
		panel.add(GETGROUPADMIN_BTN);
		
		JButton GETGROUPMEMBER_BTN = new JButton("get users");
		
		
		//GET GROUP MEMBER
		GETGROUPMEMBER_BTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = GETGROUPMEMBER_ID.getText();
				try {
					write("AdminGetGroupMember|"+id);
					
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
			}
		});
		GETGROUPMEMBER_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
		GETGROUPMEMBER_BTN.setBackground(SystemColor.activeCaption);
		GETGROUPMEMBER_BTN.setForeground(SystemColor.inactiveCaptionText);
		GETGROUPMEMBER_BTN.setBorder(UIManager.getBorder("Button.border"));
		GETGROUPMEMBER_BTN.setBounds(341, 591, 87, 23);
		panel.add(GETGROUPMEMBER_BTN);
		
		GETGROUPMEMBER_ID = new JTextField();
		GETGROUPMEMBER_ID.setColumns(10);
		GETGROUPMEMBER_ID.setBounds(280, 593, 61, 20);
		panel.add(GETGROUPMEMBER_ID);
		
		JLabel lblIdGroup_1_1 = new JLabel("id group");
		lblIdGroup_1_1.setBounds(235, 597, 46, 14);
		panel.add(lblIdGroup_1_1);
		
		JList list_2_1_1 = new JList();
		list_2_1_1.setBounds(235, 625, 179, 97);
		panel.add(list_2_1_1);
		
		JList GETSPAM_LIST = new JList();
		GETSPAM_LIST.setBounds(496, 79, 341, 118);
		panel.add(GETSPAM_LIST);
		
		GETSPAM_DATETO = new JTextField();
		GETSPAM_DATETO.setColumns(10);
		GETSPAM_DATETO.setBounds(663, 21, 46, 20);
		panel.add(GETSPAM_DATETO);
		
		JLabel lable = new JLabel("To date");
		lable.setBounds(604, 24, 61, 14);
		panel.add(lable);
		
		GETSPAM_DATEFROM = new JTextField();
		GETSPAM_DATEFROM.setColumns(10);
		GETSPAM_DATEFROM.setBounds(533, 21, 61, 20);
		panel.add(GETSPAM_DATEFROM);
		
		JLabel GETSPAM_FROM = new JLabel("From");
		GETSPAM_FROM.setBounds(496, 24, 46, 14);
		panel.add(GETSPAM_FROM);
		
		GETSPAM_NAME = new JTextField();
		GETSPAM_NAME.setColumns(10);
		GETSPAM_NAME.setBounds(527, 48, 61, 20);
		panel.add(GETSPAM_NAME);
		
		JLabel lblTn_2 = new JLabel("Tên");
		lblTn_2.setBounds(496, 51, 46, 14);
		panel.add(lblTn_2);
		
		JCheckBox GETSPAM_SORTNAME = new JCheckBox("By name");
		GETSPAM_SORTNAME.setBounds(594, 47, 76, 23);
		panel.add(GETSPAM_SORTNAME);
		
		JCheckBox GETSPAM_SORTDATE = new JCheckBox("By date");
		GETSPAM_SORTDATE.setBounds(674, 47, 70, 23);
		panel.add(GETSPAM_SORTDATE);
		
		JButton GETSPAM_BTN = new JButton("get spam");
		
		
		//GET SPAM LIST
		GETSPAM_BTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nameString = GETSPAM_NAME.getText();
				int sort_by_name = GETSPAM_SORTNAME.isSelected() ? 1 : 0;
				int sort_by_date = GETSPAM_SORTDATE.isSelected() ? 1 : 0;
				String name = GetUser_NAME.getText();
				String fromDateString = GETSPAM_DATEFROM.getText();
				String toDateString = GETSPAM_DATETO.getText();
				
				try {
					write("AdminSpamlist|"+nameString+"|"+sort_by_name + "|" + sort_by_date +"|"+name+"|"+fromDateString+"|"+toDateString);
					
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
			}
		});
		
		GETSPAM_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
		GETSPAM_BTN.setBackground(SystemColor.activeCaption);
		GETSPAM_BTN.setForeground(SystemColor.inactiveCaptionText);
		GETSPAM_BTN.setBorder(UIManager.getBorder("Button.border"));
		GETSPAM_BTN.setBounds(750, 47, 87, 23);
		panel.add(GETSPAM_BTN);
		
		GetUser_FROMDATE = new JTextField();
		GetUser_FROMDATE.setColumns(10);
		GetUser_FROMDATE.setBounds(40, 77, 46, 20);
		panel.add(GetUser_FROMDATE);
		
		JLabel lblNewLabel_1_1 = new JLabel("From");
		lblNewLabel_1_1.setBounds(10, 80, 46, 14);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblTrngThi_2_1 = new JLabel("To date");
		lblTrngThi_2_1.setBounds(100, 80, 61, 14);
		panel.add(lblTrngThi_2_1);
		
		GetUser_TODATE = new JTextField();
		GetUser_TODATE.setColumns(10);
		GetUser_TODATE.setBounds(144, 77, 46, 20);
		panel.add(GetUser_TODATE);
		
		JList list_1_1 = new JList();
		list_1_1.setBounds(484, 474, 253, 118);
		panel.add(list_1_1);
		
		JButton GETREGISTERLIST_BTN = new JButton("Lấy ds đăng kí");
		JCheckBox GETREGISTERLIST_SORTNAME = new JCheckBox("By name");
		GETREGISTERLIST_SORTNAME.setBounds(484, 444, 76, 23);
		panel.add(GETREGISTERLIST_SORTNAME);
		
		
		//GET REGISTER LIST
		GETREGISTERLIST_BTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int sort_by_name = GETREGISTERLIST_SORTNAME.isSelected() ? 1 : 0;
				int sort_by_date = GETREGISTERLIST_SORTNAME.isSelected() ? 1 : 0;
				String fromDateString = GETREGISTERLIST_DATE_FROM.getText();
				String toDateString = GETREGISTERLIST_DATE_TO.getText();
				
				try {
					write("AdminRegisterStatistic|"+sort_by_date+"|"+sort_by_name + "|" + fromDateString +"|"+toDateString);
					
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
			}
		});
		GETREGISTERLIST_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
		GETREGISTERLIST_BTN.setBackground(SystemColor.activeCaption);
		GETREGISTERLIST_BTN.setForeground(SystemColor.inactiveCaptionText);
		GETREGISTERLIST_BTN.setBorder(UIManager.getBorder("Button.border"));
		GETREGISTERLIST_BTN.setBounds(484, 413, 117, 23);
		panel.add(GETREGISTERLIST_BTN);
		
		GETREGISTERLIST_DATE_FROM = new JTextField();
		GETREGISTERLIST_DATE_FROM.setColumns(10);
		GETREGISTERLIST_DATE_FROM.setBounds(644, 413, 61, 20);
		panel.add(GETREGISTERLIST_DATE_FROM);
		
		JLabel lblNewLabel_1_2 = new JLabel("From");
		lblNewLabel_1_2.setBounds(607, 416, 46, 14);
		panel.add(lblNewLabel_1_2);
		
		JLabel lblTrngThi_2_2 = new JLabel("To date");
		lblTrngThi_2_2.setBounds(715, 416, 61, 14);
		panel.add(lblTrngThi_2_2);
		
		GETREGISTERLIST_DATE_TO = new JTextField();
		GETREGISTERLIST_DATE_TO.setColumns(10);
		GETREGISTERLIST_DATE_TO.setBounds(774, 413, 46, 20);
		panel.add(GETREGISTERLIST_DATE_TO);
		
		JCheckBox GETREGISTERLIST_SORTDATE = new JCheckBox("By date");
		GETREGISTERLIST_SORTDATE.setBounds(564, 444, 70, 23);
		panel.add(GETREGISTERLIST_SORTDATE);
		
		
		
		LOCKACCOUNT_NAME = new JTextField();
		LOCKACCOUNT_NAME.setColumns(10);
		LOCKACCOUNT_NAME.setBounds(567, 603, 61, 20);
		panel.add(LOCKACCOUNT_NAME);
		
		JLabel lblTn_1_1 = new JLabel("Khoá tài khoản");
		lblTn_1_1.setBounds(486, 606, 98, 14);
		panel.add(lblTn_1_1);
		
		JButton LOCKACCOUNT_BTN = new JButton("Khoá");
		
		
		//LOCK ACCOUNT
		LOCKACCOUNT_BTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = LOCKACCOUNT_NAME.getText();
				
				try {
					write("AdminLockAccount|"+name);
					
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
			}
		});
		LOCKACCOUNT_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
		LOCKACCOUNT_BTN.setBackground(SystemColor.activeCaption);
		LOCKACCOUNT_BTN.setForeground(SystemColor.inactiveCaptionText);
		LOCKACCOUNT_BTN.setBounds(640, 602, 69, 23);
		panel.add(LOCKACCOUNT_BTN);
		
		JButton UNLOCKACCOUNT_BTN = new JButton("Mở khoá");
		UNLOCKACCOUNT_NAME = new JTextField();
		UNLOCKACCOUNT_NAME.setColumns(10);
		UNLOCKACCOUNT_NAME.setBounds(565, 635, 61, 20);
		panel.add(UNLOCKACCOUNT_NAME);
		
		
		//UNLOCK ACCOUNT
		UNLOCKACCOUNT_BTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = UNLOCKACCOUNT_NAME.getText();
				
				try {
					write("AdminUnLockAccount|"+name);
					
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
			}
		});
		UNLOCKACCOUNT_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
		UNLOCKACCOUNT_BTN.setBackground(SystemColor.activeCaption);
		UNLOCKACCOUNT_BTN.setForeground(SystemColor.inactiveCaptionText);
		UNLOCKACCOUNT_BTN.setBounds(638, 634, 87, 23);
		panel.add(UNLOCKACCOUNT_BTN);
		
		JLabel lblTn_1_1_1 = new JLabel("Mở tài khoản");
		lblTn_1_1_1.setBounds(484, 638, 98, 14);
		panel.add(lblTn_1_1_1);
		
		JList list_1_1_1 = new JList();
		list_1_1_1.setBounds(484, 729, 253, 118);
		panel.add(list_1_1_1);
		
		JCheckBox USERSFRIEND_SORT_NAME = new JCheckBox("By name");
		USERSFRIEND_SORT_NAME.setBounds(484, 699, 76, 23);
		panel.add(USERSFRIEND_SORT_NAME);
		
		JCheckBox USERSFRIEND_SORT_DATE = new JCheckBox("By date");
		USERSFRIEND_SORT_DATE.setBounds(564, 699, 70, 23);
		panel.add(USERSFRIEND_SORT_DATE);
		
		USERSFRIEND_NAME = new JTextField();
		USERSFRIEND_NAME.setColumns(10);
		USERSFRIEND_NAME.setBounds(680, 668, 61, 20);
		panel.add(USERSFRIEND_NAME);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("name");
		lblNewLabel_1_2_1.setBounds(643, 671, 46, 14);
		panel.add(lblNewLabel_1_2_1);
		
		JButton USERSFRIEND_BTN = new JButton("Lấy ds người dùng");
		
		
		//GET USER FRIENDS
		USERSFRIEND_BTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = USERSFRIEND_NAME.getText();
				String account_friend = USERSFRIEND_AMOUNT_FRIEND.getText();
				int sort_by_name = USERSFRIEND_SORT_NAME.isSelected() ? 1 : 0;
				int sort_by_date = USERSFRIEND_SORT_DATE.isSelected() ? 1 : 0;
				
				try {
					write("AdminGetUserFriends|"+name+"|"+account_friend+"|"+sort_by_name + "|" + sort_by_date);
					
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
			}
		});
		USERSFRIEND_BTN.setBackground(SystemColor.activeCaption);
		USERSFRIEND_BTN.setForeground(SystemColor.inactiveCaptionText);
		USERSFRIEND_BTN.setBorder(UIManager.getBorder("Button.border"));
		USERSFRIEND_BTN.setBounds(484, 668, 150, 23);
		panel.add(USERSFRIEND_BTN);
		
		JLabel lblTrngThi_2_2_1 = new JLabel("amount f");
		lblTrngThi_2_2_1.setBounds(643, 702, 58, 14);
		panel.add(lblTrngThi_2_2_1);
		
		USERSFRIEND_AMOUNT_FRIEND = new JTextField();
		USERSFRIEND_AMOUNT_FRIEND.setColumns(10);
		USERSFRIEND_AMOUNT_FRIEND.setBounds(704, 699, 46, 20);
		panel.add(USERSFRIEND_AMOUNT_FRIEND);
		
		JList list_1_1_1_1 = new JList();
		list_1_1_1_1.setBounds(10, 761, 253, 64);
		panel.add(list_1_1_1_1);
		
		JButton GETREGISTERAMOUNT_BTN = new JButton("Lấy SL đk");
		
		
		//GET REGISTER AMOUNT
		GETREGISTERAMOUNT_BTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String year = GETREGISTERAMOUNT_YEAR.getText();
				try {
					write("AdminGetRegisterAmountByYear|"+year);
					
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
			}
		});
		GETREGISTERAMOUNT_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
		GETREGISTERAMOUNT_BTN.setBackground(SystemColor.activeCaption);
		GETREGISTERAMOUNT_BTN.setForeground(SystemColor.inactiveCaptionText);
		GETREGISTERAMOUNT_BTN.setBorder(UIManager.getBorder("Button.border"));
		GETREGISTERAMOUNT_BTN.setBounds(103, 733, 139, 23);
		panel.add(GETREGISTERAMOUNT_BTN);
		
		JLabel lblTrngThi_2_2_2 = new JLabel("Year");
		lblTrngThi_2_2_2.setBounds(10, 736, 61, 14);
		panel.add(lblTrngThi_2_2_2);
		
		GETREGISTERAMOUNT_YEAR = new JTextField();
		GETREGISTERAMOUNT_YEAR.setColumns(10);
		GETREGISTERAMOUNT_YEAR.setBounds(47, 733, 46, 20);
		panel.add(GETREGISTERAMOUNT_YEAR);
		
		JList list_1_1_1_1_1 = new JList();
		list_1_1_1_1_1.setBounds(10, 864, 253, 64);
		panel.add(list_1_1_1_1_1);
		
		JButton GETACTIVEAMOUNT_BTN = new JButton("Lấy SL HD");
		
		
		//GET ACTIVE LOG
		GETACTIVEAMOUNT_BTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String year = GETACTIVEAMOUNT_YEAR.getText();
				try {
					write("AdminGetActiveAmountByYear|"+year);
					
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
			}
		});
		GETACTIVEAMOUNT_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
		GETACTIVEAMOUNT_BTN.setBackground(SystemColor.activeCaption);
		GETACTIVEAMOUNT_BTN.setForeground(SystemColor.inactiveCaptionText);
		GETACTIVEAMOUNT_BTN.setBorder(UIManager.getBorder("Button.border"));
		GETACTIVEAMOUNT_BTN.setBounds(103, 836, 139, 23);
		panel.add(GETACTIVEAMOUNT_BTN);
		
		GETACTIVEAMOUNT_YEAR = new JTextField();
		GETACTIVEAMOUNT_YEAR.setColumns(10);
		GETACTIVEAMOUNT_YEAR.setBounds(47, 836, 46, 20);
		panel.add(GETACTIVEAMOUNT_YEAR);
		
		JLabel lblTrngThi_2_2_2_1 = new JLabel("Active");
		lblTrngThi_2_2_2_1.setBounds(10, 839, 61, 14);
		panel.add(lblTrngThi_2_2_2_1);
		
		
		
		
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
