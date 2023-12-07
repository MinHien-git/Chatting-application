package client;

import java.awt.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.border.BevelBorder;

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

    private JTabbedPane allTab;

    private JTextField inputSearch;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        Admin_demo window = new Admin_demo();
        window.frame.setVisible(true);
//        window.setUpSocket();
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
//        frame.setBounds(100, 100, 916, 991);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        JPanel panel = new JPanel();
//        frame.getContentPane().add(panel, BorderLayout.CENTER);
//        panel.setLayout(null);
//
//        GetUser_EMAIL = new JTextField();
//        GetUser_EMAIL.setBounds(47, 21, 61, 20);
//        panel.add(GetUser_EMAIL);
//        GetUser_EMAIL.setColumns(10);
//
//        JButton GetUserButton = new JButton("Get users");
//        GetUserButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
//        GetUserButton.setBackground(SystemColor.activeCaption);
//        GetUserButton.setForeground(SystemColor.inactiveCaptionText);
//        JCheckBox GetUser_STATUS = new JCheckBox("By Status");
//        GetUser_STATUS.setBounds(121, 20, 91, 23);
//        panel.add(GetUser_STATUS);
//        JCheckBox GetUser_SORTNAME = new JCheckBox("By name");
//        GetUser_SORTNAME.setBounds(10, 47, 76, 23);
//        panel.add(GetUser_SORTNAME);
//
//        JCheckBox GetUser_SORTDATE = new JCheckBox("By date");
//        GetUser_SORTDATE.setBounds(90, 48, 70, 23);
//        panel.add(GetUser_SORTDATE);
//
//
//        //Get users
//        GetUserButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String nameString = GetUser_EMAIL.getText();
//                int account_status = GetUser_STATUS.isSelected() ? 1 : 0;
//                int sort_by_name = GetUser_SORTNAME.isSelected() ? 1 : 0;
//                int sort_by_date = GetUser_SORTDATE.isSelected() ? 1 : 0;
//                String name = GetUser_NAME.getText();
//                String fromDateString = GetUser_FROMDATE.getText();
//                String toDateString = GetUser_TODATE.getText();
//
//                try {
//                    write("AdminGetUser|" + nameString + "|" + account_status + "|" + sort_by_name + "|" + sort_by_date + "|" + name + "|" + fromDateString + "|" + toDateString);
//
//                } catch (IOException ex) {
//                    //JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
//                }
//            }
//        });
//        GetUserButton.setBounds(200, 77, 110, 23);
//        panel.add(GetUserButton);
//
//        JList GetUser_LIST = new JList();
//        GetUser_LIST.setBounds(10, 102, 253, 97);
//        panel.add(GetUser_LIST);
//
//        JLabel lblNewLabel = new JLabel("email");
//        lblNewLabel.setBounds(10, 24, 46, 14);
//        panel.add(lblNewLabel);
//
//        GetUser_NAME = new JTextField();
//        GetUser_NAME.setColumns(10);
//        GetUser_NAME.setBounds(197, 49, 61, 20);
//        panel.add(GetUser_NAME);
//
//        JLabel lblTn = new JLabel("Tên");
//        lblTn.setBounds(166, 52, 46, 14);
//        panel.add(lblTn);
//
//
//        JList GetLOGIN_ACTIVITIES_LIST = new JList();
//        GetLOGIN_ACTIVITIES_LIST.setBounds(10, 238, 253, 118);
//        panel.add(GetLOGIN_ACTIVITIES_LIST);
//
//        JButton GetLOGIN_ACTIVITIES_BTN = new JButton("Lấy ds đăng nhập");
//        GetLOGIN_ACTIVITIES_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
//        GetLOGIN_ACTIVITIES_BTN.setBackground(SystemColor.activeCaption);
//        GetLOGIN_ACTIVITIES_BTN.setForeground(SystemColor.inactiveCaptionText);
//        GetLOGIN_ACTIVITIES_BTN.setBorder(UIManager.getBorder("Button.border"));
//
//
//        //GET LOGIN ACTIVITIES
//        GetLOGIN_ACTIVITIES_BTN.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    write("AdminGetLoginActivities");
//                } catch (IOException ex) {
//                    //JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
//                }
//            }
//        });
//        GetLOGIN_ACTIVITIES_BTN.setBounds(10, 210, 253, 23);
//        panel.add(GetLOGIN_ACTIVITIES_BTN);
//
//        JList list_2 = new JList();
//        list_2.setBounds(10, 464, 253, 118);
//        panel.add(list_2);
//
//        GETGROUP_NAME = new JTextField();
//        GETGROUP_NAME.setColumns(10);
//        GETGROUP_NAME.setBounds(33, 388, 61, 20);
//        panel.add(GETGROUP_NAME);
//
//        JLabel lblTn_1 = new JLabel("Tên");
//        lblTn_1.setBounds(10, 391, 46, 14);
//        panel.add(lblTn_1);
//
//        JCheckBox GETGROUP_SORT_NAME = new JCheckBox("By name");
//        GETGROUP_SORT_NAME.setBounds(90, 385, 76, 23);
//        panel.add(GETGROUP_SORT_NAME);
//
//        JCheckBox GETGROUP_SORT_DATE = new JCheckBox("By date");
//        GETGROUP_SORT_DATE.setBounds(170, 385, 70, 23);
//        panel.add(GETGROUP_SORT_DATE);
//
//        JButton GETGROUP_BTN = new JButton("get group");
//
//
//        //GET GROUP
//        GETGROUP_BTN.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String groupString = GETGROUP_NAME.getText();
//                int account_status = GETGROUP_SORT_NAME.isSelected() ? 1 : 0;
//                int sort_by_name = GETGROUP_SORT_DATE.isSelected() ? 1 : 0;
//
//                try {
//                    write("AdminGetGroup|" + groupString + "|" + account_status + "|" + sort_by_name);
//
//                } catch (IOException ex) {
//                    //JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
//                }
//            }
//        });
//        GETGROUP_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
//        GETGROUP_BTN.setBackground(SystemColor.activeCaption);
//        GETGROUP_BTN.setForeground(SystemColor.inactiveCaptionText);
//        GETGROUP_BTN.setBorder(UIManager.getBorder("Button.border"));
//        GETGROUP_BTN.setBounds(10, 419, 253, 23);
//        panel.add(GETGROUP_BTN);
//
//        DELETEACCOUNT_name = new JTextField();
//        DELETEACCOUNT_name.setColumns(10);
//        DELETEACCOUNT_name.setBounds(538, 226, 61, 20);
//        panel.add(DELETEACCOUNT_name);
//
//        JLabel DELETEACCOUNTLABEL = new JLabel("name");
//        DELETEACCOUNTLABEL.setBounds(496, 230, 46, 14);
//        panel.add(DELETEACCOUNTLABEL);
//
//        ADDACCOUNT_USERNAME = new JTextField();
//        ADDACCOUNT_USERNAME.setColumns(10);
//        ADDACCOUNT_USERNAME.setBounds(530, 259, 74, 20);
//        panel.add(ADDACCOUNT_USERNAME);
//
//        JButton DELETEACCOUNT_BTN = new JButton("Xoá");
//        DELETEACCOUNT_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
//        DELETEACCOUNT_BTN.setForeground(SystemColor.inactiveCaptionText);
//        DELETEACCOUNT_BTN.setBackground(SystemColor.activeCaption);
//        DELETEACCOUNT_BTN.setBorder(UIManager.getBorder("Button.border"));
//
//
//        //DELETE ACCOUNT
//        DELETEACCOUNT_BTN.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String username = DELETEACCOUNT_name.getText();
//
//                try {
//                    write("AdminDeleteAccount|" + username);
//
//                } catch (IOException ex) {
//                    //JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
//                }
//            }
//        });
//        DELETEACCOUNT_BTN.setBounds(609, 225, 76, 23);
//        panel.add(DELETEACCOUNT_BTN);
//
//        JLabel lblName = new JLabel("name");
//        lblName.setBounds(496, 262, 34, 14);
//        panel.add(lblName);
//
//        ADDACCOUNT_PW = new JTextField();
//        ADDACCOUNT_PW.setColumns(10);
//        ADDACCOUNT_PW.setBounds(628, 259, 61, 20);
//        panel.add(ADDACCOUNT_PW);
//
//        JLabel lblPw = new JLabel("pw");
//        lblPw.setBounds(610, 262, 23, 14);
//        panel.add(lblPw);
//
//        JButton ADDACCOUNT_BTN = new JButton("Thêm");
//        JCheckBox ADDACCOUNT_SX = new JCheckBox("sex");
//        ADDACCOUNT_SX.setBounds(496, 289, 91, 23);
//        panel.add(ADDACCOUNT_SX);
//
//        JCheckBox EDITACCOUNT_SX = new JCheckBox("sex");
//        EDITACCOUNT_SX.setBounds(491, 367, 91, 23);
//        panel.add(EDITACCOUNT_SX);
//
//        ADDACCOUNT_BIRTH = new JTextField();
//        ADDACCOUNT_BIRTH.setColumns(10);
//        ADDACCOUNT_BIRTH.setBounds(628, 290, 61, 20);
//        panel.add(ADDACCOUNT_BIRTH);
//
//
//        //ADD ACCOUNT
//        ADDACCOUNT_BTN.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String name = ADDACCOUNT_USERNAME.getText();
//                String pw = ADDACCOUNT_PW.getText();
//                int sxString = ADDACCOUNT_SX.isSelected() ? 1 : 0;
//                String birth = ADDACCOUNT_BIRTH.getText();
//                try {
//                    write("AdminAddAccount|" + name + pw + sxString + birth);
//
//                } catch (IOException ex) {
//                    //JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
//                }
//            }
//        });
//
//        ADDACCOUNT_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
//        ADDACCOUNT_BTN.setBackground(SystemColor.activeCaption);
//        ADDACCOUNT_BTN.setForeground(SystemColor.inactiveCaptionText);
//        ADDACCOUNT_BTN.setBorder(UIManager.getBorder("Button.border"));
//        ADDACCOUNT_BTN.setBounds(699, 258, 76, 23);
//        panel.add(ADDACCOUNT_BTN);
//
//
//        JLabel lblBirth = new JLabel("birth");
//        lblBirth.setBounds(598, 293, 23, 14);
//        panel.add(lblBirth);
//
//        EDITACCOUNT_NAME = new JTextField();
//        EDITACCOUNT_NAME.setColumns(10);
//        EDITACCOUNT_NAME.setBounds(530, 336, 74, 20);
//        panel.add(EDITACCOUNT_NAME);
//
//        JLabel lblName_1 = new JLabel("name");
//        lblName_1.setBounds(496, 339, 34, 14);
//        panel.add(lblName_1);
//
//        JLabel lblPw_1 = new JLabel("pw");
//        lblPw_1.setBounds(610, 339, 23, 14);
//        panel.add(lblPw_1);
//
//        EDITACCOUNT_PW = new JTextField();
//        EDITACCOUNT_PW.setColumns(10);
//        EDITACCOUNT_PW.setBounds(628, 336, 61, 20);
//        panel.add(EDITACCOUNT_PW);
//
//        JLabel lblBirth_1 = new JLabel("birth");
//        lblBirth_1.setBounds(588, 371, 23, 14);
//        panel.add(lblBirth_1);
//
//        EDITACCOUNT_BIRTH = new JTextField();
//        EDITACCOUNT_BIRTH.setColumns(10);
//        EDITACCOUNT_BIRTH.setBounds(628, 368, 61, 20);
//        panel.add(EDITACCOUNT_BIRTH);
//
//        JButton EDITACCOUNT_BTN = new JButton("Sửa");
//        EDITACCOUNT_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
//        EDITACCOUNT_BTN.setBackground(SystemColor.activeCaption);
//        EDITACCOUNT_BTN.setForeground(SystemColor.inactiveCaptionText);
//        EDITACCOUNT_BTN.setBorder(UIManager.getBorder("Button.border"));
//
//
//        //EDIT ACCOUNT
//        EDITACCOUNT_BTN.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String name = EDITACCOUNT_NAME.getText();
//                String pw = EDITACCOUNT_PW.getText();
//                int sxString = EDITACCOUNT_SX.isSelected() ? 1 : 0;
//                String birth = EDITACCOUNT_BIRTH.getText();
//                try {
//                    write("AdminEditAccount|" + name + pw + sxString + birth);
//
//                } catch (IOException ex) {
//                    //JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
//                }
//            }
//        });
//        EDITACCOUNT_BTN.setBounds(699, 335, 76, 23);
//        panel.add(EDITACCOUNT_BTN);
//
//        JList list_2_1 = new JList();
//        list_2_1.setBounds(12, 625, 179, 97);
//        panel.add(list_2_1);
//
//        JLabel lblIdGroup_1 = new JLabel("id group");
//        lblIdGroup_1.setBounds(8, 597, 46, 14);
//        panel.add(lblIdGroup_1);
//
//        GETGROUPADMIN_ID = new JTextField();
//        GETGROUPADMIN_ID.setColumns(10);
//        GETGROUPADMIN_ID.setBounds(64, 594, 61, 20);
//        panel.add(GETGROUPADMIN_ID);
//
//        JButton GETGROUPADMIN_BTN = new JButton("get admin");
//        GETGROUPADMIN_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
//        GETGROUPADMIN_BTN.setBackground(SystemColor.activeCaption);
//        GETGROUPADMIN_BTN.setForeground(SystemColor.inactiveCaptionText);
//        GETGROUPADMIN_BTN.setBorder(UIManager.getBorder("Button.border"));
//
//
//        //GET GROUP ADMIN
//        GETGROUPADMIN_BTN.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String id = GETGROUPADMIN_ID.getText();
//                try {
//                    write("AdminGetGroupAdmin|" + id);
//
//                } catch (IOException ex) {
//                    //JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
//                }
//            }
//        });
//        GETGROUPADMIN_BTN.setBounds(125, 593, 87, 23);
//        panel.add(GETGROUPADMIN_BTN);
//
//        JButton GETGROUPMEMBER_BTN = new JButton("get users");
//
//
//        //GET GROUP MEMBER
//        GETGROUPMEMBER_BTN.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String id = GETGROUPMEMBER_ID.getText();
//                try {
//                    write("AdminGetGroupMember|" + id);
//
//                } catch (IOException ex) {
//                    //JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
//                }
//            }
//        });
//        GETGROUPMEMBER_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
//        GETGROUPMEMBER_BTN.setBackground(SystemColor.activeCaption);
//        GETGROUPMEMBER_BTN.setForeground(SystemColor.inactiveCaptionText);
//        GETGROUPMEMBER_BTN.setBorder(UIManager.getBorder("Button.border"));
//        GETGROUPMEMBER_BTN.setBounds(341, 591, 87, 23);
//        panel.add(GETGROUPMEMBER_BTN);
//
//        GETGROUPMEMBER_ID = new JTextField();
//        GETGROUPMEMBER_ID.setColumns(10);
//        GETGROUPMEMBER_ID.setBounds(280, 593, 61, 20);
//        panel.add(GETGROUPMEMBER_ID);
//
//        JLabel lblIdGroup_1_1 = new JLabel("id group");
//        lblIdGroup_1_1.setBounds(235, 597, 46, 14);
//        panel.add(lblIdGroup_1_1);
//
//        JList list_2_1_1 = new JList();
//        list_2_1_1.setBounds(235, 625, 179, 97);
//        panel.add(list_2_1_1);
//
//        JList GETSPAM_LIST = new JList();
//        GETSPAM_LIST.setBounds(496, 79, 341, 118);
//        panel.add(GETSPAM_LIST);
//
//        GETSPAM_DATETO = new JTextField();
//        GETSPAM_DATETO.setColumns(10);
//        GETSPAM_DATETO.setBounds(663, 21, 46, 20);
//        panel.add(GETSPAM_DATETO);
//
//        JLabel lable = new JLabel("To date");
//        lable.setBounds(604, 24, 61, 14);
//        panel.add(lable);
//
//        GETSPAM_DATEFROM = new JTextField();
//        GETSPAM_DATEFROM.setColumns(10);
//        GETSPAM_DATEFROM.setBounds(533, 21, 61, 20);
//        panel.add(GETSPAM_DATEFROM);
//
//        JLabel GETSPAM_FROM = new JLabel("From");
//        GETSPAM_FROM.setBounds(496, 24, 46, 14);
//        panel.add(GETSPAM_FROM);
//
//        GETSPAM_NAME = new JTextField();
//        GETSPAM_NAME.setColumns(10);
//        GETSPAM_NAME.setBounds(527, 48, 61, 20);
//        panel.add(GETSPAM_NAME);
//
//        JLabel lblTn_2 = new JLabel("Tên");
//        lblTn_2.setBounds(496, 51, 46, 14);
//        panel.add(lblTn_2);
//
//        JCheckBox GETSPAM_SORTNAME = new JCheckBox("By name");
//        GETSPAM_SORTNAME.setBounds(594, 47, 76, 23);
//        panel.add(GETSPAM_SORTNAME);
//
//        JCheckBox GETSPAM_SORTDATE = new JCheckBox("By date");
//        GETSPAM_SORTDATE.setBounds(674, 47, 70, 23);
//        panel.add(GETSPAM_SORTDATE);
//
//        JButton GETSPAM_BTN = new JButton("get spam");
//
//
//        //GET SPAM LIST
//        GETSPAM_BTN.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String nameString = GETSPAM_NAME.getText();
//                int sort_by_name = GETSPAM_SORTNAME.isSelected() ? 1 : 0;
//                int sort_by_date = GETSPAM_SORTDATE.isSelected() ? 1 : 0;
//                String name = GetUser_NAME.getText();
//                String fromDateString = GETSPAM_DATEFROM.getText();
//                String toDateString = GETSPAM_DATETO.getText();
//
//                try {
//                    write("AdminSpamlist|" + nameString + "|" + sort_by_name + "|" + sort_by_date + "|" + name + "|" + fromDateString + "|" + toDateString);
//
//                } catch (IOException ex) {
//                    //JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
//                }
//            }
//        });
//
//        GETSPAM_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
//        GETSPAM_BTN.setBackground(SystemColor.activeCaption);
//        GETSPAM_BTN.setForeground(SystemColor.inactiveCaptionText);
//        GETSPAM_BTN.setBorder(UIManager.getBorder("Button.border"));
//        GETSPAM_BTN.setBounds(750, 47, 87, 23);
//        panel.add(GETSPAM_BTN);
//
//        GetUser_FROMDATE = new JTextField();
//        GetUser_FROMDATE.setColumns(10);
//        GetUser_FROMDATE.setBounds(40, 77, 46, 20);
//        panel.add(GetUser_FROMDATE);
//
//        JLabel lblNewLabel_1_1 = new JLabel("From");
//        lblNewLabel_1_1.setBounds(10, 80, 46, 14);
//        panel.add(lblNewLabel_1_1);
//
//        JLabel lblTrngThi_2_1 = new JLabel("To date");
//        lblTrngThi_2_1.setBounds(100, 80, 61, 14);
//        panel.add(lblTrngThi_2_1);
//
//        GetUser_TODATE = new JTextField();
//        GetUser_TODATE.setColumns(10);
//        GetUser_TODATE.setBounds(144, 77, 46, 20);
//        panel.add(GetUser_TODATE);
//
//        JList list_1_1 = new JList();
//        list_1_1.setBounds(484, 474, 253, 118);
//        panel.add(list_1_1);
//
//        JButton GETREGISTERLIST_BTN = new JButton("Lấy ds đăng kí");
//        JCheckBox GETREGISTERLIST_SORTNAME = new JCheckBox("By name");
//        GETREGISTERLIST_SORTNAME.setBounds(484, 444, 76, 23);
//        panel.add(GETREGISTERLIST_SORTNAME);
//
//
//        //GET REGISTER LIST
//        GETREGISTERLIST_BTN.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                int sort_by_name = GETREGISTERLIST_SORTNAME.isSelected() ? 1 : 0;
//                int sort_by_date = GETREGISTERLIST_SORTNAME.isSelected() ? 1 : 0;
//                String fromDateString = GETREGISTERLIST_DATE_FROM.getText();
//                String toDateString = GETREGISTERLIST_DATE_TO.getText();
//
//                try {
//                    write("AdminRegisterStatistic|" + sort_by_date + "|" + sort_by_name + "|" + fromDateString + "|" + toDateString);
//
//                } catch (IOException ex) {
//                    //JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
//                }
//            }
//        });
//        GETREGISTERLIST_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
//        GETREGISTERLIST_BTN.setBackground(SystemColor.activeCaption);
//        GETREGISTERLIST_BTN.setForeground(SystemColor.inactiveCaptionText);
//        GETREGISTERLIST_BTN.setBorder(UIManager.getBorder("Button.border"));
//        GETREGISTERLIST_BTN.setBounds(484, 413, 117, 23);
//        panel.add(GETREGISTERLIST_BTN);
//
//        GETREGISTERLIST_DATE_FROM = new JTextField();
//        GETREGISTERLIST_DATE_FROM.setColumns(10);
//        GETREGISTERLIST_DATE_FROM.setBounds(644, 413, 61, 20);
//        panel.add(GETREGISTERLIST_DATE_FROM);
//
//        JLabel lblNewLabel_1_2 = new JLabel("From");
//        lblNewLabel_1_2.setBounds(607, 416, 46, 14);
//        panel.add(lblNewLabel_1_2);
//
//        JLabel lblTrngThi_2_2 = new JLabel("To date");
//        lblTrngThi_2_2.setBounds(715, 416, 61, 14);
//        panel.add(lblTrngThi_2_2);
//
//        GETREGISTERLIST_DATE_TO = new JTextField();
//        GETREGISTERLIST_DATE_TO.setColumns(10);
//        GETREGISTERLIST_DATE_TO.setBounds(774, 413, 46, 20);
//        panel.add(GETREGISTERLIST_DATE_TO);
//
//        JCheckBox GETREGISTERLIST_SORTDATE = new JCheckBox("By date");
//        GETREGISTERLIST_SORTDATE.setBounds(564, 444, 70, 23);
//        panel.add(GETREGISTERLIST_SORTDATE);
//
//
//        LOCKACCOUNT_NAME = new JTextField();
//        LOCKACCOUNT_NAME.setColumns(10);
//        LOCKACCOUNT_NAME.setBounds(567, 603, 61, 20);
//        panel.add(LOCKACCOUNT_NAME);
//
//        JLabel lblTn_1_1 = new JLabel("Khoá tài khoản");
//        lblTn_1_1.setBounds(486, 606, 98, 14);
//        panel.add(lblTn_1_1);
//
//        JButton LOCKACCOUNT_BTN = new JButton("Khoá");
//
//
//        //LOCK ACCOUNT
//        LOCKACCOUNT_BTN.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String name = LOCKACCOUNT_NAME.getText();
//
//                try {
//                    write("AdminLockAccount|" + name);
//
//                } catch (IOException ex) {
//                    //JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
//                }
//            }
//        });
//        LOCKACCOUNT_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
//        LOCKACCOUNT_BTN.setBackground(SystemColor.activeCaption);
//        LOCKACCOUNT_BTN.setForeground(SystemColor.inactiveCaptionText);
//        LOCKACCOUNT_BTN.setBounds(640, 602, 69, 23);
//        panel.add(LOCKACCOUNT_BTN);
//
//        JButton UNLOCKACCOUNT_BTN = new JButton("Mở khoá");
//        UNLOCKACCOUNT_NAME = new JTextField();
//        UNLOCKACCOUNT_NAME.setColumns(10);
//        UNLOCKACCOUNT_NAME.setBounds(565, 635, 61, 20);
//        panel.add(UNLOCKACCOUNT_NAME);
//
//
//        //UNLOCK ACCOUNT
//        UNLOCKACCOUNT_BTN.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String name = UNLOCKACCOUNT_NAME.getText();
//
//                try {
//                    write("AdminUnLockAccount|" + name);
//
//                } catch (IOException ex) {
//                    //JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
//                }
//            }
//        });
//        UNLOCKACCOUNT_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
//        UNLOCKACCOUNT_BTN.setBackground(SystemColor.activeCaption);
//        UNLOCKACCOUNT_BTN.setForeground(SystemColor.inactiveCaptionText);
//        UNLOCKACCOUNT_BTN.setBounds(638, 634, 87, 23);
//        panel.add(UNLOCKACCOUNT_BTN);
//
//        JLabel lblTn_1_1_1 = new JLabel("Mở tài khoản");
//        lblTn_1_1_1.setBounds(484, 638, 98, 14);
//        panel.add(lblTn_1_1_1);
//
//        JList list_1_1_1 = new JList();
//        list_1_1_1.setBounds(484, 729, 253, 118);
//        panel.add(list_1_1_1);
//
//        JCheckBox USERSFRIEND_SORT_NAME = new JCheckBox("By name");
//        USERSFRIEND_SORT_NAME.setBounds(484, 699, 76, 23);
//        panel.add(USERSFRIEND_SORT_NAME);
//
//        JCheckBox USERSFRIEND_SORT_DATE = new JCheckBox("By date");
//        USERSFRIEND_SORT_DATE.setBounds(564, 699, 70, 23);
//        panel.add(USERSFRIEND_SORT_DATE);
//
//        USERSFRIEND_NAME = new JTextField();
//        USERSFRIEND_NAME.setColumns(10);
//        USERSFRIEND_NAME.setBounds(680, 668, 61, 20);
//        panel.add(USERSFRIEND_NAME);
//
//        JLabel lblNewLabel_1_2_1 = new JLabel("name");
//        lblNewLabel_1_2_1.setBounds(643, 671, 46, 14);
//        panel.add(lblNewLabel_1_2_1);
//
//        JButton USERSFRIEND_BTN = new JButton("Lấy ds người dùng");
//
//
//        //GET USER FRIENDS
//        USERSFRIEND_BTN.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String name = USERSFRIEND_NAME.getText();
//                String account_friend = USERSFRIEND_AMOUNT_FRIEND.getText();
//                int sort_by_name = USERSFRIEND_SORT_NAME.isSelected() ? 1 : 0;
//                int sort_by_date = USERSFRIEND_SORT_DATE.isSelected() ? 1 : 0;
//
//                try {
//                    write("AdminGetUserFriends|" + name + "|" + account_friend + "|" + sort_by_name + "|" + sort_by_date);
//
//                } catch (IOException ex) {
//                    //JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
//                }
//            }
//        });
//        USERSFRIEND_BTN.setBackground(SystemColor.activeCaption);
//        USERSFRIEND_BTN.setForeground(SystemColor.inactiveCaptionText);
//        USERSFRIEND_BTN.setBorder(UIManager.getBorder("Button.border"));
//        USERSFRIEND_BTN.setBounds(484, 668, 150, 23);
//        panel.add(USERSFRIEND_BTN);
//
//        JLabel lblTrngThi_2_2_1 = new JLabel("amount f");
//        lblTrngThi_2_2_1.setBounds(643, 702, 58, 14);
//        panel.add(lblTrngThi_2_2_1);
//
//        USERSFRIEND_AMOUNT_FRIEND = new JTextField();
//        USERSFRIEND_AMOUNT_FRIEND.setColumns(10);
//        USERSFRIEND_AMOUNT_FRIEND.setBounds(704, 699, 46, 20);
//        panel.add(USERSFRIEND_AMOUNT_FRIEND);
//
//        JList list_1_1_1_1 = new JList();
//        list_1_1_1_1.setBounds(10, 761, 253, 64);
//        panel.add(list_1_1_1_1);
//
//        JButton GETREGISTERAMOUNT_BTN = new JButton("Lấy SL đk");
//
//
//        //GET REGISTER AMOUNT
//        GETREGISTERAMOUNT_BTN.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String year = GETREGISTERAMOUNT_YEAR.getText();
//                try {
//                    write("AdminGetRegisterAmountByYear|" + year);
//
//                } catch (IOException ex) {
//                    //JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
//                }
//            }
//        });
//        GETREGISTERAMOUNT_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
//        GETREGISTERAMOUNT_BTN.setBackground(SystemColor.activeCaption);
//        GETREGISTERAMOUNT_BTN.setForeground(SystemColor.inactiveCaptionText);
//        GETREGISTERAMOUNT_BTN.setBorder(UIManager.getBorder("Button.border"));
//        GETREGISTERAMOUNT_BTN.setBounds(103, 733, 139, 23);
//        panel.add(GETREGISTERAMOUNT_BTN);
//
//        JLabel lblTrngThi_2_2_2 = new JLabel("Year");
//        lblTrngThi_2_2_2.setBounds(10, 736, 61, 14);
//        panel.add(lblTrngThi_2_2_2);
//
//        GETREGISTERAMOUNT_YEAR = new JTextField();
//        GETREGISTERAMOUNT_YEAR.setColumns(10);
//        GETREGISTERAMOUNT_YEAR.setBounds(47, 733, 46, 20);
//        panel.add(GETREGISTERAMOUNT_YEAR);
//
//        JList list_1_1_1_1_1 = new JList();
//        list_1_1_1_1_1.setBounds(10, 864, 253, 64);
//        panel.add(list_1_1_1_1_1);
//
//        JButton GETACTIVEAMOUNT_BTN = new JButton("Lấy SL HD");
//
//
//        //GET ACTIVE LOG
//        GETACTIVEAMOUNT_BTN.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String year = GETACTIVEAMOUNT_YEAR.getText();
//                try {
//                    write("AdminGetActiveAmountByYear|" + year);
//
//                } catch (IOException ex) {
//                    //JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
//                }
//            }
//        });
//        GETACTIVEAMOUNT_BTN.setFont(new Font("Tahoma", Font.PLAIN, 10));
//        GETACTIVEAMOUNT_BTN.setBackground(SystemColor.activeCaption);
//        GETACTIVEAMOUNT_BTN.setForeground(SystemColor.inactiveCaptionText);
//        GETACTIVEAMOUNT_BTN.setBorder(UIManager.getBorder("Button.border"));
//        GETACTIVEAMOUNT_BTN.setBounds(103, 836, 139, 23);
//        panel.add(GETACTIVEAMOUNT_BTN);
//
//        GETACTIVEAMOUNT_YEAR = new JTextField();
//        GETACTIVEAMOUNT_YEAR.setColumns(10);
//        GETACTIVEAMOUNT_YEAR.setBounds(47, 836, 46, 20);
//        panel.add(GETACTIVEAMOUNT_YEAR);
//
//        JLabel lblTrngThi_2_2_2_1 = new JLabel("Active");
//        lblTrngThi_2_2_2_1.setBounds(10, 839, 61, 14);
//        panel.add(lblTrngThi_2_2_2_1);

        JPanel defaultPanel = new JPanel();

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();
        JPanel panel6 = new JPanel();
        JPanel panel7 = new JPanel();
        JPanel panel8 = new JPanel();
        JPanel panel9 = new JPanel();


        allTab = new JTabbedPane();
        allTab.addTab("Trang chủ", defaultPanel);
        allTab.addTab("Chức năng 1", panel1);
        allTab.addTab("Chức năng 2", panel2);
        allTab.addTab("Chức năng 3", panel3);
        allTab.addTab("Chức năng 4", panel4);
        allTab.addTab("Chức năng 5", panel5);
        allTab.addTab("Chức năng 6", panel6);
        allTab.addTab("Chức năng 7", panel7);
        allTab.addTab("Chức năng 8", panel8);
        allTab.addTab("Chức năng 9", panel9);

        defaultPanel.add(this.trangChu());
        panel1.add(this.trang1());
        panel2.add(this.trang2());

        frame.add(allTab);
        frame.setSize(1200, 1000);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        allTab.setSelectedIndex(0);
    }

    private JPanel trangChu() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        ArrayList<JButton> listButton = this.getButton();
        ArrayList<JPanel> listFunction = new ArrayList<>();
        listFunction.add(this.getFunction1());
        listFunction.add(this.getFunction2());
        listFunction.add(this.getFunction3());
        listFunction.add(this.getFunction4());
        listFunction.add(this.getFunction5());
        listFunction.add(this.getFunction6());
        listFunction.add(this.getFunction7());
        listFunction.add(this.getFunction8());
        listFunction.add(this.getFunction9());

        gbc.insets = new Insets(0, 2, 2, 2);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;

        for (int i = 0; i < listButton.size(); i++) {
            mainPanel.add(listButton.get(i), gbc);

            gbc.gridx = 1;

            mainPanel.add(listFunction.get(i), gbc);

            gbc.gridy += 1;
            gbc.gridx = 0;
        }

        return mainPanel;
    }

    private JScrollPane trang1() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.insets = new Insets(0, 0, 2, 0);

        // chức năng 1a
        JPanel listUser = new JPanel();
        listUser.setSize(800, 800);
        listUser.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 2, 5, 2);

        JLabel uname = new JLabel("Tên đăng nhập");
        JLabel fname = new JLabel("Họ tên");
        JLabel addr = new JLabel("Địa chỉ");
        JLabel dob = new JLabel("Ngày sinh");
        JLabel gender = new JLabel("Giới tính");
        JLabel email = new JLabel("Email");

        setLabel(uname);
        setLabel(fname);
        setLabel(addr);
        setLabel(dob);
        setLabel(gender);
        setLabel(email);

        gbc.gridx = 0;
        gbc.gridy = 0;
        listUser.add(uname, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        listUser.add(fname, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        listUser.add(addr, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        listUser.add(dob, gbc);

        gbc.gridx = 4;
        gbc.gridy = 0;
        listUser.add(gender, gbc);

        gbc.gridx = 5;
        gbc.gridy = 0;
        listUser.add(email, gbc);


        // list of user will be here
        for (int i = 0; i < 15; i++) {
            gbc.gridy += 1;
            gbc.gridx = 0;
            for (int j = 0; j < 6; j++) {
                JLabel label = new JLabel("This is a very long long info");

                listUser.add(label, gbc);

                gbc.gridx += 1;
            }
        }

        listUser.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JScrollPane myScroll = new JScrollPane(listUser);
        myScroll.setPreferredSize(new Dimension(1000, 300));
        myScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        myScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        myScroll.getVerticalScrollBar().setUnitIncrement(20);

        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.gridwidth = 3;
        mainPanel.add(myScroll, gbcMain);

        gbcMain.gridwidth = 1;

        inputSearch = new JTextField("Nhập tên cần tìm kiếm");
        JCheckBox checkBox = new JCheckBox("Tên đăng nhập");
        JCheckBox sortName = new JCheckBox("Sắp xếp theo tên");
        JCheckBox sortCreate = new JCheckBox("Sắp xếp theo ngày tạo");
        JRadioButton btn1 = new JRadioButton("Trực tuyến & ngoại tuyến");
        JRadioButton btn2 = new JRadioButton("Trực tuyến");
        JRadioButton btn3 = new JRadioButton("Ngoại tuyến");
        JButton btn = new JButton("Tìm kiếm");

        setTextfield(inputSearch);
        inputSearch.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                inputSearch.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                inputSearch.setText("Nhập tên cần tìm kiếm");
            }
        });

        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(btn1);
        btnGroup.add(btn2);
        btnGroup.add(btn3);

        gbcMain.gridx = 0;
        gbcMain.gridy += 1;
        gbcMain.gridwidth = 1;
        gbcMain.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(inputSearch, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(checkBox, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(btn1, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(btn2, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(btn3, gbcMain);

        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        gbcMain.gridy += 1;
        gbcMain.gridwidth = 1;
        gbcMain.insets = new Insets(3, 0, 3, 0);
        gbcMain.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(sep, gbcMain);
        gbcMain.fill = GridBagConstraints.NONE;
        gbcMain.insets = new Insets(0, 0, 2, 0);

        gbcMain.gridy += 1;
        mainPanel.add(sortName, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(sortCreate, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(btn, gbcMain);

        JSeparator sep1 = new JSeparator(SwingConstants.HORIZONTAL);
        gbcMain.gridy += 1;
        gbcMain.gridwidth = 3;
        gbcMain.insets = new Insets(3, 0, 3, 0);
        gbcMain.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(sep1, gbcMain);
        gbcMain.fill = GridBagConstraints.NONE;
        gbcMain.insets = new Insets(0, 0, 2, 0);

        // chức năng 1b
        JPanel userAdd = new JPanel();
        userAdd.setLayout(new GridBagLayout());
        GridBagConstraints gbcUserAdd = new GridBagConstraints();
        gbcUserAdd.insets = new Insets(0, 2, 4, 2);
        gbcUserAdd.anchor = GridBagConstraints.LINE_START;

        JPanel userUpdate = new JPanel();
        userUpdate.setLayout(new GridBagLayout());
        GridBagConstraints gbcUserUpdate= new GridBagConstraints();
        gbcUserUpdate.insets = new Insets(0, 2, 4, 2);
        gbcUserUpdate.anchor = GridBagConstraints.LINE_START;

        JPanel userDel = new JPanel();
        userDel.setLayout(new GridBagLayout());
        GridBagConstraints gbcUserDel = new GridBagConstraints();
        gbcUserDel.insets = new Insets(0, 100, 4, 2);
        gbcUserDel.anchor = GridBagConstraints.LINE_START;

        // label cho tất cả 3 chức năng
        JLabel unAdd = new JLabel("Tên đăng nhập");
        JLabel fnAdd = new JLabel("Họ & tên");
        JLabel addrAdd = new JLabel("Địa chỉ");
        JLabel dobAdd = new JLabel("Ngày sinh");
        JLabel genderAdd = new JLabel("Giới tính");
        JLabel emailAdd = new JLabel("Email");

        JLabel unUpdate = new JLabel("Tên đăng nhập");
        JLabel fnUpdate = new JLabel("Họ & tên");
        JLabel addrUpdate = new JLabel("Địa chỉ");
        JLabel emailUpdate = new JLabel("Email");

        JLabel unDel = new JLabel("Tên đăng nhập");

        JTextField unAddtf = new JTextField(20);
        JTextField fnAddtf = new JTextField(20);
        JTextField addrAddtf = new JTextField(20);
        JTextField dobAddtf = new JTextField(10);
        JTextField genderAddtf = new JTextField(6);
        JTextField emailAddtf = new JTextField(20);

        JTextField unUpdatetf = new JTextField(20);
        JTextField fnUpdatetf = new JTextField(20);
        JTextField addrUpdatetf = new JTextField(20);
        JTextField emailUpdatetf = new JTextField(20);

        JTextField unDeltf = new JTextField(20);

        JButton toAdd = new JButton("Thêm người dùng");
        JButton toUpdate = new JButton("Cập nhật người dùng");
        JButton toDel = new JButton("Xóa người dùng");

        // thêm người dùng
        gbcUserAdd.gridx = 0;
        gbcUserAdd.gridy = 0;
        userAdd.add(unAdd, gbcUserAdd);
        gbcUserAdd.gridy += 1;
        userAdd.add(unAddtf, gbcUserAdd);

        gbcUserAdd.gridy += 1;
        userAdd.add(fnAdd, gbcUserAdd);
        gbcUserAdd.gridy += 1;
        userAdd.add(fnAddtf, gbcUserAdd);

        gbcUserAdd.gridy += 1;
        userAdd.add(addrAdd, gbcUserAdd);
        gbcUserAdd.gridy += 1;
        userAdd.add(addrAddtf, gbcUserAdd);

        gbcUserAdd.gridy += 1;
        userAdd.add(dobAdd, gbcUserAdd);
        gbcUserAdd.gridy += 1;
        userAdd.add(dobAddtf, gbcUserAdd);

        gbcUserAdd.gridy += 1;
        userAdd.add(genderAdd, gbcUserAdd);
        gbcUserAdd.gridy += 1;
        userAdd.add(genderAddtf, gbcUserAdd);

        gbcUserAdd.gridy += 1;
        userAdd.add(emailAdd, gbcUserAdd);
        gbcUserAdd.gridy += 1;
        userAdd.add(emailAddtf, gbcUserAdd);

        gbcUserAdd.gridy += 1;
        userAdd.add(toAdd, gbcUserAdd);

        // cập nhật người dùng
        gbcUserUpdate.gridx = 0;
        gbcUserUpdate.gridy = 0;
        userUpdate.add(unUpdate, gbcUserUpdate);
        gbcUserUpdate.gridy += 1;
        userUpdate.add(unUpdatetf, gbcUserUpdate);

        gbcUserUpdate.gridy += 1;
        userUpdate.add(fnUpdate, gbcUserUpdate);
        gbcUserUpdate.gridy += 1;
        userUpdate.add(fnUpdatetf, gbcUserUpdate);

        gbcUserUpdate.gridy += 1;
        userUpdate.add(addrUpdate, gbcUserUpdate);
        gbcUserUpdate.gridy += 1;
        userUpdate.add(addrUpdatetf, gbcUserUpdate);

        gbcUserUpdate.gridy += 1;
        userUpdate.add(emailUpdate, gbcUserUpdate);
        gbcUserUpdate.gridy += 1;
        userUpdate.add(emailUpdatetf, gbcUserUpdate);

        gbcUserUpdate.gridy += 1;
        userUpdate.add(toUpdate, gbcUserUpdate);

        // xóa người dùng
        gbcUserDel.gridx = 0;
        gbcUserDel.gridy = 0;
        userDel.add(unDel, gbcUserDel);
        gbcUserDel.gridy += 1;
        userDel.add(unDeltf, gbcUserDel);

        gbcUserDel.gridy += 1;
        userDel.add(toDel, gbcUserDel);

        gbcMain.gridx = 0;
        gbcMain.gridy += 1;
        mainPanel.add(userAdd, gbcMain);

        gbcMain.gridx = 1;
        mainPanel.add(userUpdate, gbcMain);

        gbcMain.gridx = 2;
        mainPanel.add(userDel, gbcMain);

        gbcMain.gridx = 0;

        JSeparator sep2 = new JSeparator(SwingConstants.HORIZONTAL);
        gbcMain.gridy += 1;
        gbcMain.gridwidth = 3;
        gbcMain.insets = new Insets(3, 0, 3, 0);
        gbcMain.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(sep2, gbcMain);
        gbcMain.fill = GridBagConstraints.NONE;
        gbcMain.insets = new Insets(0, 0, 2, 0);


        // chức năng 1c
        JPanel accountLock = new JPanel();
        JLabel label = new JLabel("Tên đăng nhập");
        JTextField inputLock = new JTextField(20);
        JButton lock = new JButton("Khóa tài khoản");
        JButton unlock = new JButton("Mở khóa tài khoản");

        accountLock.add(label);
        accountLock.add(inputLock);
        accountLock.add(lock);
        accountLock.add(unlock);

        gbcMain.gridy += 1;
        gbcMain.gridwidth = 2;
        mainPanel.add(accountLock, gbcMain);

        JSeparator sep3 = new JSeparator(SwingConstants.HORIZONTAL);
        gbcMain.gridy += 1;
        gbcMain.gridwidth = 3;
        gbcMain.insets = new Insets(3, 0, 3, 0);
        gbcMain.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(sep3, gbcMain);
        gbcMain.fill = GridBagConstraints.NONE;
        gbcMain.insets = new Insets(0, 0, 2, 0);

        // chức năng 1d
        JLabel label1 = new JLabel("Tên đăng nhập");
        JLabel label2 = new JLabel("Cập nhật mật khẩu mới");
        JLabel label3 = new JLabel("Nhập lại mật khẩu");
        JTextField inputLabel1 = new JTextField(18);
        JTextField inputLabel2 = new JTextField(18);
        JTextField inputLabel3 = new JTextField(18);
        JButton btnUpdatePass = new JButton("Cập nhật");

        JPanel tempPanel1 = new JPanel();
        JPanel tempPanel2 = new JPanel();
        JPanel tempPanel3 = new JPanel();

        tempPanel1.add(label1);
        tempPanel1.add(inputLabel1);
        tempPanel2.add(label2);
        tempPanel2.add(inputLabel2);
        tempPanel3.add(label3);
        tempPanel3.add(inputLabel3);

        gbcMain.gridy += 1;
        gbcMain.gridwidth = 1;
        gbcMain.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(tempPanel1, gbcMain);
        gbcMain.gridy += 1;
        mainPanel.add(tempPanel2, gbcMain);
        gbcMain.gridy += 1;
        mainPanel.add(tempPanel3, gbcMain);
        gbcMain.gridy += 1;
        mainPanel.add(btnUpdatePass, gbcMain);

        JSeparator sep4 = new JSeparator(SwingConstants.HORIZONTAL);
        gbcMain.gridy += 1;
        gbcMain.gridwidth = 3;
        gbcMain.insets = new Insets(3, 0, 3, 0);
        gbcMain.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(sep4, gbcMain);
        gbcMain.fill = GridBagConstraints.NONE;
        gbcMain.insets = new Insets(0, 0, 2, 0);

        //chức năng 1e
        JPanel listLoginHistory = new JPanel();
        listLoginHistory.setSize(300, 800);
        listLoginHistory.setLayout(new GridBagLayout());
        GridBagConstraints gbcListHist = new GridBagConstraints();
        gbcListHist.insets = new Insets(0, 2, 5, 2);

        JLabel unameLoginHist = new JLabel("Tên đăng nhập");
        JLabel loginDate = new JLabel("Thời gian đăng nhập");

        setLabel(unameLoginHist);
        setLabel(loginDate);

        gbcListHist.gridx = 0;
        gbcListHist.gridy = 0;
        listLoginHistory.add(unameLoginHist, gbcListHist);

        gbcListHist.gridx = 1;
        gbcListHist.gridy = 0;
        listLoginHistory.add(loginDate, gbcListHist);

        // list of login history will be here
        for (int i = 0; i < 15; i++) {
            gbcListHist.gridy += 1;
            gbcListHist.gridx = 0;
            for (int j = 0; j < 2; j++) {
                JLabel labelLoginHist = new JLabel("This is a very long long info");

                listLoginHistory.add(labelLoginHist, gbcListHist);

                gbcListHist.gridx += 1;
            }
        }

        listLoginHistory.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JScrollPane myScrollLoginHist = new JScrollPane(listLoginHistory);
        myScrollLoginHist.setPreferredSize(new Dimension(600, 300));
        myScrollLoginHist.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        myScrollLoginHist.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        myScrollLoginHist.getVerticalScrollBar().setUnitIncrement(20);

        gbcMain.gridx = 0;
        gbcMain.gridy += 1;
        gbcMain.gridwidth = 3;
        gbcMain.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(myScrollLoginHist, gbcMain);

        gbcMain.gridwidth = 1;

        JTextField inputUnameHist = new JTextField(15);
        inputUnameHist.setText("Nhập tên đăng nhập");
        JButton btnLoginHist = new JButton("Xem lịch sử đăng nhập");

        gbcMain.gridx = 0;
        gbcMain.gridy += 1;
        gbcMain.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(inputUnameHist, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(btnLoginHist, gbcMain);

        JSeparator sep5 = new JSeparator(SwingConstants.HORIZONTAL);
        gbcMain.gridy += 1;
        gbcMain.gridwidth = 3;
        gbcMain.insets = new Insets(3, 0, 3, 0);
        gbcMain.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(sep5, gbcMain);
        gbcMain.fill = GridBagConstraints.NONE;
        gbcMain.insets = new Insets(0, 0, 2, 0);

        // chức năng 1f
        JPanel listFriend = new JPanel();
        listFriend.setSize(300, 800);
        listFriend.setLayout(new GridBagLayout());
        GridBagConstraints gbcListFriend = new GridBagConstraints();
        gbcListFriend.insets = new Insets(0, 2, 5, 2);

        JLabel unameFriend = new JLabel("Tên đăng nhập (bạn bè)");
        JLabel status = new JLabel("Trạng thái");

        setLabel(unameFriend);
        setLabel(status);

        gbcListFriend.gridx = 0;
        gbcListFriend.gridy = 0;
        listFriend.add(unameFriend, gbcListFriend);

        gbcListFriend.gridx = 1;
        gbcListFriend.gridy = 0;
        listFriend.add(status, gbcListFriend);

        // list of friend will be here
        for (int i = 0; i < 15; i++) {
            gbcListFriend.gridy += 1;
            gbcListFriend.gridx = 0;
            for (int j = 0; j < 2; j++) {
                JLabel labelFriend = new JLabel("This is a very long long info");

                listFriend.add(labelFriend, gbcListFriend);

                gbcListFriend.gridx += 1;
            }
        }

        listFriend.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JScrollPane myScrollFriend = new JScrollPane(listFriend);
        myScrollFriend.setPreferredSize(new Dimension(600, 300));
        myScrollFriend.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        myScrollFriend.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        myScrollFriend.getVerticalScrollBar().setUnitIncrement(20);

        gbcMain.gridx = 0;
        gbcMain.gridy += 1;
        gbcMain.gridwidth = 3;
        gbcMain.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(myScrollFriend, gbcMain);

        gbcMain.gridwidth = 1;

        JTextField inputUnameFriend = new JTextField(15);
        inputUnameFriend.setText("Nhập tên đăng nhập");
        JButton btnFriend = new JButton("Xem danh sách bạn bè");

        gbcMain.gridx = 0;
        gbcMain.gridy += 1;
        gbcMain.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(inputUnameFriend, gbcMain);

        gbcMain.gridx = 0;
        gbcMain.gridy += 1;
        mainPanel.add(btnFriend, gbcMain);


        JScrollPane outerScrollPane = new JScrollPane(mainPanel);
        outerScrollPane.setPreferredSize(new Dimension(1600, 750));
        outerScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        outerScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outerScrollPane.getVerticalScrollBar().setUnitIncrement(40);

        return outerScrollPane;
    }

    private JScrollPane trang2() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.insets = new Insets(0, 0, 2, 0);

        // chức năng 2
        JPanel listLogin = new JPanel();
        listLogin.setSize(800, 800);
        listLogin.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 2, 5, 2);

        JLabel time = new JLabel("Thời gian");
        JLabel uname = new JLabel("Tên đăng nhập");
        JLabel fname = new JLabel("Họ tên");

        setLabel(uname);
        setLabel(fname);
        setLabel(time);

        gbc.gridx = 0;
        gbc.gridy = 0;
        listLogin.add(time, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        listLogin.add(uname, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        listLogin.add(fname, gbc);

        // list of user will be here
        for (int i = 0; i < 15; i++) {
            gbc.gridy += 1;
            gbc.gridx = 0;
            for (int j = 0; j < 3; j++) {
                JLabel label = new JLabel("This is a very long long info");

                listLogin.add(label, gbc);

                gbc.gridx += 1;
            }
        }

        listLogin.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JScrollPane myScroll = new JScrollPane(listLogin);
        myScroll.setPreferredSize(new Dimension(600, 300));
        myScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        myScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        myScroll.getVerticalScrollBar().setUnitIncrement(20);

        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.gridwidth = 3;
        mainPanel.add(myScroll, gbcMain);

        gbcMain.gridwidth = 1;

        JButton btn = new JButton("Xem danh sách đăng nhâp");

        gbcMain.gridy += 1;
        mainPanel.add(btn, gbcMain);

        JScrollPane outerScrollPane = new JScrollPane(mainPanel);
        outerScrollPane.setPreferredSize(new Dimension(1600, 750));
        outerScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        outerScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outerScrollPane.getVerticalScrollBar().setUnitIncrement(40);

        return outerScrollPane;
    }
    private void setTextfield(JTextField textfield) {
        textfield.setFont(new Font("Serif", Font.PLAIN, 20));
        textfield.setPreferredSize(new Dimension(260, 30));
    }

    private void setLabel(JLabel label) {
        label.setFont(new Font("Serif", Font.BOLD, 20));
    }

    private ArrayList<JButton> getButton() {
        JButton btn1 = new JButton("Chức năng 1");
        JButton btn2 = new JButton("Chức năng 2");
        JButton btn3 = new JButton("Chức năng 3");
        JButton btn4 = new JButton("Chức năng 4");
        JButton btn5 = new JButton("Chức năng 5");
        JButton btn6 = new JButton("Chức năng 6");
        JButton btn7 = new JButton("Chức năng 7");
        JButton btn8 = new JButton("Chức năng 8");
        JButton btn9 = new JButton("Chức năng 9");

        ArrayList<JButton> list = new ArrayList<>();

        list.add(btn1);
        list.add(btn2);
        list.add(btn3);
        list.add(btn4);
        list.add(btn5);
        list.add(btn6);
        list.add(btn7);
        list.add(btn8);
        list.add(btn9);

        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allTab.setSelectedIndex(1);
            }
        });

        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allTab.setSelectedIndex(2);
            }
        });

        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allTab.setSelectedIndex(3);
            }
        });

        btn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allTab.setSelectedIndex(4);
            }
        });

        btn5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allTab.setSelectedIndex(5);
            }
        });

        btn6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allTab.setSelectedIndex(6);
            }
        });

        btn7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allTab.setSelectedIndex(7);
            }
        });

        btn8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allTab.setSelectedIndex(8);
            }
        });

        btn9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allTab.setSelectedIndex(9);
            }
        });

        return list;
    }

    private JPanel getFunction1() {
        JPanel outerPanel1 = new JPanel();
        JPanel panel1 = new JPanel(new GridBagLayout());
        GridBagConstraints gbcPanel1 = new GridBagConstraints();

        gbcPanel1.gridx = 0;
        gbcPanel1.gridy = 0;
        gbcPanel1.insets = new Insets(0, 0, 3, 0);
        gbcPanel1.anchor = GridBagConstraints.LINE_START;

        JLabel label1 = new JLabel("Quản lý danh sách người dùng. Thông tin người dùng gồm: tên đăng nhập, họ tên, địa chỉ, ngày sinh, giới tính, email.");
        JLabel label1a = new JLabel("a. Xem danh sách cho phép lọc theo tên/tên đăng nhập/trạng thái, sắp xếp theo tên/ngày tạo.");
        JLabel label1b = new JLabel("b. Thêm/cập nhật/xoá.");
        JLabel label1c = new JLabel("c. Khoá/mở khóa tài khoản.");
        JLabel label1d = new JLabel("d. Cập nhật mật khẩu.");
        JLabel label1e = new JLabel("e. Xem lịch sử đăng nhập.");
        JLabel label1f = new JLabel("f. Danh sách bạn bè.");

        label1.setFont(new Font("Serif", Font.ITALIC, 12));

        ArrayList<JLabel> list = new ArrayList<>();
        list.add(label1);
        list.add(label1a);
        list.add(label1b);
        list.add(label1c);
        list.add(label1d);
        list.add(label1e);
        list.add(label1f);

        for (int i = 0; i < list.size(); i++) {
            panel1.add(list.get(i), gbcPanel1);
            gbcPanel1.gridy += 1;
        }

        outerPanel1.add(panel1);
        outerPanel1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        return outerPanel1;
    }

    private JPanel getFunction2() {
        JPanel outerPanel2 = new JPanel();
        JPanel panel2 = new JPanel(new GridBagLayout());
        GridBagConstraints gbcPanel2 = new GridBagConstraints();

        gbcPanel2.gridx = 0;
        gbcPanel2.gridy = 0;
        gbcPanel2.insets = new Insets(0, 0, 3, 0);
        gbcPanel2.anchor = GridBagConstraints.LINE_START;

        JLabel label2 = new JLabel("Xem danh sách đăng nhập theo thứ tự thời gian. Thông tin gồm: thời gian, tên đăng nhập, họ tên.");

        label2.setFont(new Font("Serif", Font.ITALIC, 12));

        ArrayList<JLabel> list = new ArrayList<>();
        list.add(label2);

        for (int i = 0; i < list.size(); i++) {
            panel2.add(list.get(i), gbcPanel2);
            gbcPanel2.gridy += 1;
        }

        outerPanel2.add(panel2);
        outerPanel2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        return outerPanel2;
    }

    private JPanel getFunction3() {
        JPanel outerPanel3 = new JPanel();
        JPanel panel3 = new JPanel(new GridBagLayout());
        GridBagConstraints gbcPanel3 = new GridBagConstraints();

        gbcPanel3.gridx = 0;
        gbcPanel3.gridy = 0;
        gbcPanel3.insets = new Insets(0, 0, 3, 0);
        gbcPanel3.anchor = GridBagConstraints.LINE_START;

        JLabel label3 = new JLabel("Xem danh sách các nhóm chat.");
        JLabel label3a = new JLabel("a. Sắp xếp theo tên/thời gian tạo.");
        JLabel label3b = new JLabel("b. Lọc theo tên.");
        JLabel label3c = new JLabel("c. Xem danh sách thành viên 1 nhóm.");
        JLabel label3d = new JLabel("d. Xem danh sách admin 1 nhóm.");

        label3.setFont(new Font("Serif", Font.ITALIC, 12));

        ArrayList<JLabel> list = new ArrayList<>();
        list.add(label3);
        list.add(label3a);
        list.add(label3b);
        list.add(label3c);
        list.add(label3d);

        for (int i = 0; i < list.size(); i++) {
            panel3.add(list.get(i), gbcPanel3);
            gbcPanel3.gridy += 1;
        }

        outerPanel3.add(panel3);
        outerPanel3.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        return outerPanel3;
    }

    private JPanel getFunction4() {
        JPanel outerPanel4 = new JPanel();
        JPanel panel4 = new JPanel(new GridBagLayout());
        GridBagConstraints gbcPanel4 = new GridBagConstraints();

        gbcPanel4.gridx = 0;
        gbcPanel4.gridy = 0;
        gbcPanel4.insets = new Insets(0, 0, 3, 0);
        gbcPanel4.anchor = GridBagConstraints.LINE_START;

        JLabel label4 = new JLabel("Xem danh sách báo cáo spam.");
        JLabel label4a = new JLabel("a. Sắp xếp theo thời gian/tên đăng nhập.");
        JLabel label4b = new JLabel("b. Lọc theo thời gian.");
        JLabel label4c = new JLabel("c. Lọc theo tên đăng nhập.");
        JLabel label4d = new JLabel("d. Khóa tài khoản người dùng.");

        label4.setFont(new Font("Serif", Font.ITALIC, 12));

        ArrayList<JLabel> list = new ArrayList<>();
        list.add(label4);
        list.add(label4a);
        list.add(label4b);
        list.add(label4c);
        list.add(label4d);

        for (int i = 0; i < list.size(); i++) {
            panel4.add(list.get(i), gbcPanel4);
            gbcPanel4.gridy += 1;
        }

        outerPanel4.add(panel4);
        outerPanel4.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        return outerPanel4;
    }

    private JPanel getFunction5() {
        JPanel outerPanel5 = new JPanel();
        JPanel panel5 = new JPanel(new GridBagLayout());
        GridBagConstraints gbcPanel5 = new GridBagConstraints();

        gbcPanel5.gridx = 0;
        gbcPanel5.gridy = 0;
        gbcPanel5.insets = new Insets(0, 0, 3, 0);
        gbcPanel5.anchor = GridBagConstraints.LINE_START;

        JLabel label5 = new JLabel("Xem danh sách người dùng đăng ký mới: chọn khoảng thời gian, hiện ra danh sách người dùng đăng ký mới.");
        JLabel label5a = new JLabel("a. Sắp xếp theo tên/thời gian tạo.");
        JLabel label5b = new JLabel("b. Lọc theo tên.");

        label5.setFont(new Font("Serif", Font.ITALIC, 12));

        ArrayList<JLabel> list = new ArrayList<>();
        list.add(label5);
        list.add(label5a);
        list.add(label5b);

        for (int i = 0; i < list.size(); i++) {
            panel5.add(list.get(i), gbcPanel5);
            gbcPanel5.gridy += 1;
        }

        outerPanel5.add(panel5);
        outerPanel5.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        return outerPanel5;
    }

    private JPanel getFunction6() {
        JPanel outerPanel6 = new JPanel();
        JPanel panel6 = new JPanel(new GridBagLayout());
        GridBagConstraints gbcPanel6 = new GridBagConstraints();

        gbcPanel6.gridx = 0;
        gbcPanel6.gridy = 0;
        gbcPanel6.insets = new Insets(0, 0, 3, 0);
        gbcPanel6.anchor = GridBagConstraints.LINE_START;

        JLabel label6 = new JLabel("Biểu đồ số lượng người đăng ký mới theo năm: chọn năm, vẽ biểu đồ với trục hoành là tháng, trục tung là số lượng người đăng ký mới.");

        label6.setFont(new Font("Serif", Font.ITALIC, 12));

        ArrayList<JLabel> list = new ArrayList<>();
        list.add(label6);

        for (int i = 0; i < list.size(); i++) {
            panel6.add(list.get(i), gbcPanel6);
            gbcPanel6.gridy += 1;
        }

        outerPanel6.add(panel6);
        outerPanel6.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        return outerPanel6;
    }

    private JPanel getFunction7() {
        JPanel outerPanel7 = new JPanel();
        JPanel panel7 = new JPanel(new GridBagLayout());
        GridBagConstraints gbcPanel7 = new GridBagConstraints();

        gbcPanel7.gridx = 0;
        gbcPanel7.gridy = 0;
        gbcPanel7.insets = new Insets(0, 0, 3, 0);
        gbcPanel7.anchor = GridBagConstraints.LINE_START;

        JLabel label7 = new JLabel("Xem danh sách người dùng và số lượng bạn bè (1 cột bạn bè trực tiếp, 1 cột tính luôn số lượng bạn của bạn).");
        JLabel label7a = new JLabel("a. Sắp xếp theo tên/thời gian tạo.");
        JLabel label7b = new JLabel("b. Lọc theo tên.");
        JLabel label7c = new JLabel("c. Lọc theo số lượng bạn trực tiếp (bằng, nhỏ hơn, lớn hơn 1 số được nhập).");

        label7.setFont(new Font("Serif", Font.ITALIC, 12));

        ArrayList<JLabel> list = new ArrayList<>();
        list.add(label7);
        list.add(label7a);
        list.add(label7b);
        list.add(label7c);

        for (int i = 0; i < list.size(); i++) {
            panel7.add(list.get(i), gbcPanel7);
            gbcPanel7.gridy += 1;
        }

        outerPanel7.add(panel7);
        outerPanel7.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        return outerPanel7;
    }

    private JPanel getFunction8() {
        JPanel outerPanel8 = new JPanel();
        JPanel panel8 = new JPanel(new GridBagLayout());
        GridBagConstraints gbcPanel8 = new GridBagConstraints();

        gbcPanel8.gridx = 0;
        gbcPanel8.gridy = 0;
        gbcPanel8.insets = new Insets(0, 0, 3, 0);
        gbcPanel8.anchor = GridBagConstraints.LINE_START;

        JLabel label8 = new JLabel("Xem danh sách người dùng hoạt động: chọn khoảng thời gian, hiện ra danh sách người dùng có hoạt đặng và các số liệu (mở ứng dụng, chat với bao nhiêu người, chat bao nhiêu nhóm).");
        JLabel label8a = new JLabel("a. Sắp xếp theo tên/thời gian tạo.");
        JLabel label8b = new JLabel("b. Lọc theo tên.");
        JLabel label8c = new JLabel("c. Lọc theo số lượng hoạt động (bằng, nhỏ hơn, lớn hơn 1 số được nhập).");

        label8.setFont(new Font("Serif", Font.ITALIC, 12));

        ArrayList<JLabel> list = new ArrayList<>();
        list.add(label8);
        list.add(label8a);
        list.add(label8b);
        list.add(label8c);

        for (int i = 0; i < list.size(); i++) {
            panel8.add(list.get(i), gbcPanel8);
            gbcPanel8.gridy += 1;
        }

        outerPanel8.add(panel8);
        outerPanel8.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        return outerPanel8;
    }

    private JPanel getFunction9() {
        JPanel outerPanel9 = new JPanel();
        JPanel panel9 = new JPanel(new GridBagLayout());
        GridBagConstraints gbcPanel9 = new GridBagConstraints();

        gbcPanel9.gridx = 0;
        gbcPanel9.gridy = 0;
        gbcPanel9.insets = new Insets(0, 0, 3, 0);
        gbcPanel9.anchor = GridBagConstraints.LINE_START;

        JLabel label9 = new JLabel("Biểu đồ số lượng người hoạt động theo năm: chọn năm, vẽ biểu đồ với trục hoành là tháng, trục tung là số lượng người có mở ứng dụng.");

        label9.setFont(new Font("Serif", Font.ITALIC, 12));

        ArrayList<JLabel> list = new ArrayList<>();
        list.add(label9);

        for (int i = 0; i < list.size(); i++) {
            panel9.add(list.get(i), gbcPanel9);
            gbcPanel9.gridy += 1;
        }

        outerPanel9.add(panel9);
        outerPanel9.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        return outerPanel9;
    }
    private void write(String message) throws IOException {
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
                            if (message == null) {
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
