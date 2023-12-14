package client;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

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
import java.util.Arrays;
import javax.swing.border.BevelBorder;

public class Admin_demo {
    private Thread thread;
    private BufferedWriter os;
    private BufferedReader is;
    private Socket socketOfClient;
    private JFrame frame;
    private JTabbedPane allTab;
    private JTextField inputSearch;
    private JPanel listUser;
    private GridBagConstraints gbcListUser;
    private JCheckBox checkBoxUsernamep1;
    private JCheckBox checkBoxSortNamep1;
    private JCheckBox checkBoxSortCreatep1;
    private JRadioButton btnAllStatusp1;
    private JRadioButton btnOnlinep1;
    private JRadioButton btnOfflinep1;
    private JButton btnFindp1;
    private JTextField unAddtf;
    private JTextField fnAddtf;
    private JTextField addrAddtf;
    private JTextField dobAddtf;
    private JTextField genderAddtf;
    private JTextField emailAddtf;
    private JTextField unUpdatetf;
    private JTextField fnUpdatetf;
    private JTextField addrUpdatetf;
    private JTextField emailUpdatetf;
    private JTextField unDeltf;
    private JTextField inputLock;
    private JTextField inputLabelUsername;
    private JTextField inputLabelNewPass;
    private JTextField inputLabelRePass;
    private JPanel listLoginHistory;
    private GridBagConstraints gbcListHist;
    private JTextField inputUnameHist;
    private JPanel listFriend;
    private GridBagConstraints gbcListFriend;
    private JTextField inputUnameFriend;
    private JPanel listLogin;
    private GridBagConstraints gbcListLogin;
    private JPanel listGroup;
    private GridBagConstraints gbcListGroup;
    private JTextField inputGSearch;
    private JRadioButton btnSortName;
    private JRadioButton btnSortDateCreate;
    private JPanel listMember;
    private GridBagConstraints gbcListMember;
    private JTextField inputMemGroupSearch;
    private JPanel listAdmin;
    private GridBagConstraints gbcListAdmin;
    private JTextField inputAdminSearch;
    private JPanel listSpam;
    private GridBagConstraints gbcListSpam;
    private JRadioButton btnSortNamet4;
    private JRadioButton btnSortDatet4;
    private JRadioButton btnFilterName;
    private JRadioButton btnFilterDate;
    private JTextField inputSpamSearch;
    private JTextField inputLockt4;
    private JPanel listNew;
    private GridBagConstraints gbcListNew;
    private JRadioButton btnSortNamet5;
    private JRadioButton btnSortDatet5;
    private JTextField inputNewSearch;
    private JTextField inputFromDate;
    private JTextField inputToDate;
    private JPanel listFriendPlus;
    private GridBagConstraints gbcListFriendPlus;
    private JRadioButton btnSortNamet7;
    private JRadioButton btnSortDatet7;
    private JTextField inputNameSearch;
    private JTextField inputDir_fr;
    private JPanel listOpen;
    private GridBagConstraints gbcListOpen;
    private JRadioButton btnSortNamet8;
    private JRadioButton btnSortDatet8;
    private JTextField inputNameSearcht8;
    private JTextField inputDir_open;
    private GridBagConstraints gbcMain;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        Admin_demo window = new Admin_demo();
        window.frame.setVisible(true);
        window.setUpSocket();
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
        panel3.add(this.trang3());
        panel4.add(this.trang4());
        panel5.add(this.trang5());
        panel6.add(this.trang6());
        panel7.add(this.trang7());
        panel8.add(this.trang8());
        panel9.add(this.trang9());

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

    private void updateListUser(ArrayList<String> listUserInString, int checkEnd) {
        if (gbcListUser.gridy == 0) {
            int compCount = listUser.getComponentCount();
            if (compCount > 6) {
                for (int i = compCount - 1; i >= 6; i--) {
                    listUser.remove(i);
                }
            }

            gbcListUser.gridy += 1;
            gbcListUser.gridx = 0;
            for (String string : listUserInString) {
                if (string.equals("no data")) {
                    break;
                }
                JLabel label = new JLabel(string);

                listUser.add(label, gbcListUser);

                gbcListUser.gridx += 1;
            }

            listUser.revalidate();
            listUser.repaint();
        } else {
            gbcListUser.gridy += 1;
            gbcListUser.gridx = 0;
            for (String string : listUserInString) {
                JLabel label = new JLabel(string);

                listUser.add(label, gbcListUser);

                gbcListUser.gridx += 1;
            }
            listUser.revalidate();
        }

        if (checkEnd == 1) {
            gbcListUser.gridy = 0;
        }
    }
    private void updateListLoginHist(ArrayList<String> listLoginHistInString, int checkEnd) {
        if (gbcListHist.gridy == 0) {
            int compCount = listLoginHistory.getComponentCount();
            if (compCount > 2) {
                for (int i = compCount - 1; i >= 2; i--) {
                    listLoginHistory.remove(i);
                }
            }

            gbcListHist.gridy += 1;
            gbcListHist.gridx = 0;
            for (String string : listLoginHistInString) {
                if (string.equals("no data")) {
                    break;
                }
                JLabel label = new JLabel(string);

                listLoginHistory.add(label, gbcListHist);

                gbcListHist.gridx += 1;
            }

            listLoginHistory.revalidate();
            listLoginHistory.repaint();
        } else {
            gbcListHist.gridy += 1;
            gbcListHist.gridx = 0;
            for (String string : listLoginHistInString) {
                JLabel label = new JLabel(string);

                listLoginHistory.add(label, gbcListHist);

                gbcListHist.gridx += 1;
            }
            listLoginHistory.revalidate();
        }

        if (checkEnd == 1) {
            gbcListHist.gridy = 0;
        }
    }

    private void updateListFriend(ArrayList<ArrayList<String>> listFriendInString) {
        // list of friend will be here
        for (ArrayList<String> strings : listFriendInString) {
            gbcListFriend.gridy += 1;
            gbcListFriend.gridx = 0;
            for (String string : strings) {
                JLabel label = new JLabel(string);

                listFriend.add(label, gbcListFriend);

                gbcListFriend.gridx += 1;
            }
        }
        listFriend.revalidate();
    }

    private void updateListLogin(ArrayList<ArrayList<String>> listLoginInString) {
        // list of login will be here
        for (ArrayList<String> strings : listLoginInString) {
            gbcListLogin.gridy += 1;
            gbcListLogin.gridx = 0;
            for (String string : strings) {
                JLabel label = new JLabel(string);

                listLogin.add(label, gbcListLogin);

                gbcListLogin.gridx += 1;
            }
        }
        listLogin.revalidate();
    }

    private void updateListGroup(ArrayList<ArrayList<String>> listGroupInString) {
        // list of group will be here
        for (ArrayList<String> strings : listGroupInString) {
            gbcListGroup.gridy += 1;
            gbcListGroup.gridx = 0;
            for (String string : strings) {
                JLabel label = new JLabel(string);

                listGroup.add(label, gbcListGroup);

                gbcListGroup.gridx += 1;
            }
        }
        listGroup.revalidate();
    }

    private void updateListMemGroup(ArrayList<ArrayList<String>> listMemGroupInString) {
        // list of group will be here
        for (ArrayList<String> strings : listMemGroupInString) {
            gbcListMember.gridy += 1;
            gbcListMember.gridx = 0;
            for (String string : strings) {
                JLabel label = new JLabel(string);

                listMember.add(label, gbcListMember);

                gbcListMember.gridx += 1;
            }
        }
        listMember.revalidate();
    }

    private void updateListAdmin(ArrayList<ArrayList<String>> listAdminInString) {
        // list of admin will be here
        for (ArrayList<String> strings : listAdminInString) {
            gbcListAdmin.gridy += 1;
            gbcListAdmin.gridx = 0;
            for (String string : strings) {
                JLabel label = new JLabel(string);

                listAdmin.add(label, gbcListAdmin);

                gbcListAdmin.gridx += 1;
            }
        }
        listAdmin.revalidate();
    }

    private void updateListSpam(ArrayList<ArrayList<String>> listSpamInString) {
        // list of spam will be here
        for (ArrayList<String> strings : listSpamInString) {
            gbcListSpam.gridy += 1;
            gbcListSpam.gridx = 0;
            for (String string : strings) {
                JLabel label = new JLabel(string);

                listSpam.add(label, gbcListSpam);

                gbcListSpam.gridx += 1;
            }
        }
        listSpam.revalidate();
    }

    private void updateListNew(ArrayList<ArrayList<String>> listNewInString) {
        // list of new user will be here
        for (ArrayList<String> strings : listNewInString) {
            gbcListNew.gridy += 1;
            gbcListNew.gridx = 0;
            for (String string : strings) {
                JLabel label = new JLabel(string);

                listNew.add(label, gbcListNew);

                gbcListNew.gridx += 1;
            }
        }
        listNew.revalidate();
    }

    private void updateListFriendPlus(ArrayList<ArrayList<String>> listFriendPlusInString) {
        // list of friends and friends of friends will be here
        for (ArrayList<String> strings : listFriendPlusInString) {
            gbcListFriendPlus.gridy += 1;
            gbcListFriendPlus.gridx = 0;
            for (String string : strings) {
                JLabel label = new JLabel(string);

                listNew.add(label, gbcListFriendPlus);

                gbcListFriendPlus.gridx += 1;
            }
        }
        listFriendPlus.revalidate();
    }

    private void updateListOpen(ArrayList<ArrayList<String>> listOpenInString) {
        // list of user open will be here
        for (ArrayList<String> strings : listOpenInString) {
            gbcListOpen.gridy += 1;
            gbcListOpen.gridx = 0;
            for (String string : strings) {
                JLabel label = new JLabel(string);

                listOpen.add(label, gbcListOpen);

                gbcListOpen.gridx += 1;
            }
        }
        listOpen.revalidate();
    }

    private JScrollPane trang1() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.insets = new Insets(0, 0, 2, 0);

        // chức năng 1a
        listUser = new JPanel();
        listUser.setSize(800, 800);
        listUser.setLayout(new GridBagLayout());
        gbcListUser = new GridBagConstraints();
        gbcListUser.insets = new Insets(0, 2, 5, 10);

        JLabel uname = new JLabel("Tên đăng nhập");
        JLabel fname = new JLabel("Họ tên");
        JLabel addr = new JLabel("Địa chỉ");
        JLabel dob = new JLabel("Ngày sinh");
        JLabel gender = new JLabel("Giới tính");
        JLabel email = new JLabel("Email");

        // set the style for the label
        setLabel(uname);
        setLabel(fname);
        setLabel(addr);
        setLabel(dob);
        setLabel(gender);
        setLabel(email);

        // set the position and add to the list user panel
        gbcListUser.gridx = 0;
        gbcListUser.gridy = 0;
        listUser.add(uname, gbcListUser);

        gbcListUser.gridx = 1;
        gbcListUser.gridy = 0;
        listUser.add(fname, gbcListUser);

        gbcListUser.gridx = 2;
        gbcListUser.gridy = 0;
        listUser.add(addr, gbcListUser);

        gbcListUser.gridx = 3;
        gbcListUser.gridy = 0;
        listUser.add(dob, gbcListUser);

        gbcListUser.gridx = 4;
        gbcListUser.gridy = 0;
        listUser.add(gender, gbcListUser);

        gbcListUser.gridx = 5;
        gbcListUser.gridy = 0;
        listUser.add(email, gbcListUser);

        // set the style for the list user panel
        listUser.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // add the list user panel to the scrollpane & set up the function for the scrollpane
        JScrollPane myScroll = new JScrollPane(listUser);
        myScroll.setPreferredSize(new Dimension(1000, 300));
        myScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        myScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        myScroll.getVerticalScrollBar().setUnitIncrement(20);

        // add the scrollpane of list user to the main panel
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.gridwidth = 3;
        mainPanel.add(myScroll, gbcMain);

        gbcMain.gridwidth = 1;

        // declare components to filter the list user
        inputSearch = new JTextField();
        checkBoxUsernamep1 = new JCheckBox("Tên đăng nhập");
        checkBoxSortNamep1 = new JCheckBox("Sắp xếp theo tên");
        checkBoxSortCreatep1 = new JCheckBox("Sắp xếp theo ngày tạo");
        btnAllStatusp1 = new JRadioButton("Trực tuyến & ngoại tuyến");
        btnOnlinep1 = new JRadioButton("Trực tuyến");
        btnOfflinep1 = new JRadioButton("Ngoại tuyến");
        btnFindp1 = new JButton("Tìm kiếm");

        setTextfield(inputSearch);

        // set the function for the button "find"
        btnFindp1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tempCheckBoxUsername = checkBoxUsernamep1.isSelected() ? "1" : "0";
                String tempCheckBoxSortName = checkBoxSortNamep1.isSelected() ? "1" : "0";
                String tempCheckBoxSortCreate = checkBoxSortCreatep1.isSelected() ? "1" : "0";
                String nameToSearch = inputSearch.getText().isEmpty() ? "" : inputSearch.getText();
                String status = "";
                if (btnAllStatusp1.isSelected()) {
                    status = "Both";
                } else if (btnOnlinep1.isSelected()) {
                    status = "Online";
                } else {
                    status = "Offline";
                }
                try {
                    write("AdminGetListUser|%s|%s|%s|%s|%s".formatted(tempCheckBoxUsername, tempCheckBoxSortName, tempCheckBoxSortCreate, nameToSearch, status));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // put 3 buttons in a group
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(btnAllStatusp1);
        btnGroup.add(btnOnlinep1);
        btnGroup.add(btnOfflinep1);

        // add the input textfield to the main panel
        gbcMain.gridx = 0;
        gbcMain.gridy += 1;
        gbcMain.gridwidth = 1;
        gbcMain.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(inputSearch, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(checkBoxUsernamep1, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(btnAllStatusp1, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(btnOnlinep1, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(btnOfflinep1, gbcMain);

        // add a separator to panel to separate checkbox, radio button
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        gbcMain.gridy += 1;
        gbcMain.gridwidth = 1;
        gbcMain.insets = new Insets(3, 0, 3, 0);
        gbcMain.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(sep, gbcMain);
        gbcMain.fill = GridBagConstraints.NONE;
        gbcMain.insets = new Insets(0, 0, 2, 0);

        // add checkbox to the panel
        gbcMain.gridy += 1;
        mainPanel.add(checkBoxSortNamep1, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(checkBoxSortCreatep1, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(btnFindp1, gbcMain);

        // add a separator to separate the function
        JSeparator sep1 = new JSeparator(SwingConstants.HORIZONTAL);
        gbcMain.gridy += 1;
        gbcMain.gridwidth = 3;
        gbcMain.insets = new Insets(3, 0, 3, 0);
        gbcMain.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(sep1, gbcMain);
        gbcMain.fill = GridBagConstraints.NONE;
        gbcMain.insets = new Insets(0, 0, 2, 0);

        // chức năng 1b

        // panel used to add an user
        JPanel userAdd = new JPanel();
        userAdd.setLayout(new GridBagLayout());
        GridBagConstraints gbcUserAdd = new GridBagConstraints();
        gbcUserAdd.insets = new Insets(0, 2, 4, 2);
        gbcUserAdd.anchor = GridBagConstraints.LINE_START;

        // panel used to update an user
        JPanel userUpdate = new JPanel();
        userUpdate.setLayout(new GridBagLayout());
        GridBagConstraints gbcUserUpdate = new GridBagConstraints();
        gbcUserUpdate.insets = new Insets(0, 2, 4, 2);
        gbcUserUpdate.anchor = GridBagConstraints.LINE_START;

        // panel used to delete an user
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

        JLabel unUpdate = new JLabel("Tên đăng nhập mới");
        JLabel fnUpdate = new JLabel("Họ & tên");
        JLabel addrUpdate = new JLabel("Địa chỉ");
        JLabel emailUpdate = new JLabel("Email hiện tại");

        JLabel unDel = new JLabel("Tên đăng nhập");

        // set the textfield for 3 function add, update and delete user
        unAddtf = new JTextField(20);
        fnAddtf = new JTextField(20);
        addrAddtf = new JTextField(20);
        dobAddtf = new JTextField(10);
        genderAddtf = new JTextField(6);
        emailAddtf = new JTextField(20);

        unUpdatetf = new JTextField(20);
        fnUpdatetf = new JTextField(20);
        addrUpdatetf = new JTextField(20);
        emailUpdatetf = new JTextField(20);

        unDeltf = new JTextField(20);

        // declare button to execute those function
        JButton toAdd = new JButton("Thêm người dùng");
        JButton toUpdate = new JButton("Cập nhật người dùng");
        JButton toDel = new JButton("Xóa người dùng");

        // set the function to add an user
        toAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String unAdd = unAddtf.getText().trim();
                String fnAdd = fnAddtf.getText().trim();
                String addrAdd = addrAddtf.getText().trim();
                String dobAdd = dobAddtf.getText().trim();
                String genderAdd = genderAddtf.getText().trim();
                String emailAdd = emailAddtf.getText().trim();

                if (!unAdd.isEmpty() && !fnAdd.isEmpty() && !addrAdd.isEmpty() && !dobAdd.isEmpty() && !genderAdd.isEmpty() && !emailAdd.isEmpty()) {
                    unAddtf.setText("");
                    fnAddtf.setText("");
                    addrAddtf.setText("");
                    dobAddtf.setText("");
                    genderAddtf.setText("");
                    emailAddtf.setText("");

                    try {
                        write("AdminAddNewAccount|%s|%s|%s|%s|%s|%s".formatted(unAdd, fnAdd, addrAdd, dobAdd, genderAdd, emailAdd));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        // set the function to update an user
        toUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String unUpdate = unUpdatetf.getText().trim();
                String fnUpdate = fnUpdatetf.getText().trim();
                String addrUpdate = addrUpdatetf.getText().trim();
                String emailUpdate = emailUpdatetf.getText().trim();

                if (!unUpdate.isEmpty() && !fnUpdate.isEmpty() && !addrUpdate.isEmpty() && !emailUpdate.isEmpty()) {
                    unUpdatetf.setText("");
                    fnUpdatetf.setText("");
                    addrUpdatetf.setText("");
                    emailUpdatetf.setText("");

                    try {
                        write("AdminUpdateAccount|%s|%s|%s|%s".formatted(unUpdate, fnUpdate, addrUpdate, emailUpdate));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        // set the function to delete an user
        toDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String unDel = unDeltf.getText().trim();

                if (!unDel.isEmpty()) {
                    unDeltf.setText("");
                    try {
                        write("AdminDeleteAccount|%s".formatted(unDel));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

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

        // add 3 panels to the main panel
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
        inputLock = new JTextField(20);
        JButton lock = new JButton("Khóa tài khoản");
        JButton unlock = new JButton("Mở khóa tài khoản");
        // set the event for the button
        lock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!inputLock.getText().isEmpty()) {
                    String tempInputLock = inputLock.getText().trim();
                    try {
                        write("AdminLockAccount|%s".formatted(tempInputLock));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                inputLock.setText("");
            }
        });
        unlock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!inputLock.getText().isEmpty()) {
                    String tempInputLock = inputLock.getText().trim();
                    try {
                        write("AdminUnlockAccount|%s".formatted(tempInputLock));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                inputLock.setText("");
            }
        });

        // add the components to the panel
        accountLock.add(label);
        accountLock.add(inputLock);
        accountLock.add(lock);
        accountLock.add(unlock);

        // add the panel to the main panel
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
        inputLabelUsername = new JTextField(18);
        inputLabelNewPass = new JTextField(18);
        inputLabelRePass = new JTextField(18);
        JButton btnUpdatePass = new JButton("Cập nhật");

        // set the event for the button
        btnUpdatePass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String un = inputLabelUsername.getText();
                String pw = inputLabelNewPass.getText();
                String repw = inputLabelRePass.getText();
                if (!pw.isEmpty() && !repw.isEmpty() && pw.equals(repw)) {
                    try {
                        write("AdminRenewPassword|%s|%s".formatted(un, pw));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                inputLabelUsername.setText("");
                inputLabelNewPass.setText("");
                inputLabelRePass.setText("");
            }
        });

        JPanel tempPanel1 = new JPanel();
        JPanel tempPanel2 = new JPanel();
        JPanel tempPanel3 = new JPanel();

        tempPanel1.add(label1);
        tempPanel1.add(inputLabelUsername);
        tempPanel2.add(label2);
        tempPanel2.add(inputLabelNewPass);
        tempPanel3.add(label3);
        tempPanel3.add(inputLabelRePass);

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
        listLoginHistory = new JPanel();
        listLoginHistory.setSize(300, 800);
        listLoginHistory.setLayout(new GridBagLayout());
        gbcListHist = new GridBagConstraints();
        gbcListHist.insets = new Insets(0, 2, 5, 10);

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

        inputUnameHist = new JTextField(15);
        inputUnameHist.setText("Nhập tên đăng nhập");
        JButton btnLoginHist = new JButton("Xem lịch sử đăng nhập");

        btnLoginHist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String un = inputUnameHist.getText();
                if (!un.equals("Nhập tên đăng nhập")) {
                    try {
                        write("AdminGetListLoginHistory|%s".formatted(un));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

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
        listFriend = new JPanel();
        listFriend.setSize(300, 800);
        listFriend.setLayout(new GridBagLayout());
        gbcListFriend = new GridBagConstraints();
        gbcListFriend.insets = new Insets(0, 2, 5, 10);

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

        inputUnameFriend = new JTextField(15);
        inputUnameFriend.setText("Nhập tên đăng nhập");
        JButton btnFriend = new JButton("Xem danh sách bạn bè");

        btnFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String un = inputUnameFriend.getText();
                if (!un.equals("Nhập tên đăng nhập")) {
                    // write the info to the server
                    ArrayList<ArrayList<String>> temp = null;
                    updateListFriend(temp);
                } else {
                    inputUnameFriend.setText("");
                }
            }
        });

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
        listLogin = new JPanel();
        listLogin.setSize(800, 800);
        listLogin.setLayout(new GridBagLayout());
        gbcListLogin = new GridBagConstraints();
        gbcListLogin.insets = new Insets(0, 2, 5, 2);

        JLabel time = new JLabel("Thời gian");
        JLabel uname = new JLabel("Tên đăng nhập");
        JLabel fname = new JLabel("Họ tên");

        setLabel(uname);
        setLabel(fname);
        setLabel(time);

        gbcListLogin.gridx = 0;
        gbcListLogin.gridy = 0;
        listLogin.add(time, gbcListLogin);

        gbcListLogin.gridx = 1;
        gbcListLogin.gridy = 0;
        listLogin.add(uname, gbcListLogin);

        gbcListLogin.gridx = 2;
        gbcListLogin.gridy = 0;
        listLogin.add(fname, gbcListLogin);

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

        // add action to the button
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // write info to the server and get the data back
                ArrayList<ArrayList<String>> temp = null;
                updateListLogin(temp);
            }
        });

        gbcMain.gridy += 1;
        mainPanel.add(btn, gbcMain);

        JScrollPane outerScrollPane = new JScrollPane(mainPanel);
        outerScrollPane.setPreferredSize(new Dimension(1600, 750));
        outerScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        outerScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outerScrollPane.getVerticalScrollBar().setUnitIncrement(40);

        return outerScrollPane;
    }

    private JScrollPane trang3() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.insets = new Insets(0, 0, 2, 0);

        // chức năng 3a & 3b
        listGroup = new JPanel();
        listGroup.setSize(800, 800);
        listGroup.setLayout(new GridBagLayout());
        gbcListGroup = new GridBagConstraints();
        gbcListGroup.insets = new Insets(0, 2, 5, 2);

        JLabel gname = new JLabel("Tên nhóm");
        JLabel nummember = new JLabel("Số thành viên");
        JLabel gadmin = new JLabel("Quản trị viên");
        JLabel timecreate = new JLabel("Thời gian tạo");

        setLabel(gname);
        setLabel(nummember);
        setLabel(gadmin);
        setLabel(timecreate);

        gbcListGroup.gridx = 0;
        gbcListGroup.gridy = 0;
        listGroup.add(gname, gbcListGroup);

        gbcListGroup.gridx = 1;
        gbcListGroup.gridy = 0;
        listGroup.add(nummember, gbcListGroup);

        gbcListGroup.gridx = 2;
        gbcListGroup.gridy = 0;
        listGroup.add(gadmin, gbcListGroup);

        gbcListGroup.gridx = 3;
        gbcListGroup.gridy = 0;
        listGroup.add(timecreate, gbcListGroup);

        listGroup.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JScrollPane myScroll = new JScrollPane(listGroup);
        myScroll.setPreferredSize(new Dimension(900, 300));
        myScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        myScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        myScroll.getVerticalScrollBar().setUnitIncrement(20);

        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.gridwidth = 3;
        mainPanel.add(myScroll, gbcMain);

        gbcMain.gridwidth = 1;

        btnSortName = new JRadioButton("Sắp xếp theo tên");
        btnSortDateCreate = new JRadioButton("Sắp xếp theo thời gian tạo");
        inputGSearch = new JTextField();
        JButton btn = new JButton("Xem danh sách nhóm");
        JLabel labelGName = new JLabel("Tên nhóm");

        setTextfield(inputGSearch);
        setLabel(labelGName);

        // add action to the button to find group
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sortBy = btnSortName.isSelected() ? 1 : 0;
                String tempInputGSearch = inputGSearch.getText();
                // write info to the server
                ArrayList<ArrayList<String>> temp = null;
                updateListGroup(temp);
            }
        });

        ButtonGroup btnG = new ButtonGroup();
        btnG.add(btnSortName);
        btnG.add(btnSortDateCreate);

        gbcMain.anchor = GridBagConstraints.LINE_START;
        gbcMain.gridy += 1;
        mainPanel.add(btnSortName, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(btnSortDateCreate, gbcMain);

        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        gbcMain.gridy += 1;
        gbcMain.gridwidth = 1;
        gbcMain.insets = new Insets(3, 0, 3, 0);
        gbcMain.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(sep, gbcMain);
        gbcMain.fill = GridBagConstraints.NONE;
        gbcMain.insets = new Insets(0, 0, 2, 0);

        gbcMain.gridy += 1;
        mainPanel.add(labelGName, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(inputGSearch, gbcMain);

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

        // chức năng 3c
        listMember = new JPanel();
        listMember.setSize(800, 800);
        listMember.setLayout(new GridBagLayout());
        gbcListMember = new GridBagConstraints();
        gbcListMember.insets = new Insets(0, 2, 5, 2);

        JLabel unameMember = new JLabel("Tên đăng nhập (thành viên)");
        JLabel roleMember = new JLabel("Vai trò");

        setLabel(unameMember);
        setLabel(roleMember);

        gbcListMember.gridx = 0;
        gbcListMember.gridy = 0;
        listMember.add(unameMember, gbcListMember);

        gbcListMember.gridx = 1;
        gbcListMember.gridy = 0;
        listMember.add(roleMember, gbcListMember);

        listMember.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JScrollPane myScrollMember = new JScrollPane(listMember);
        myScrollMember.setPreferredSize(new Dimension(600, 300));
        myScrollMember.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        myScrollMember.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        myScrollMember.getVerticalScrollBar().setUnitIncrement(20);

        gbcMain.gridx = 0;
        gbcMain.gridy += 1;
        gbcMain.gridwidth = 3;
        mainPanel.add(myScrollMember, gbcMain);

        gbcMain.gridwidth = 1;

        inputMemGroupSearch = new JTextField();
        JButton btnMem = new JButton("Xem danh sách thành viên");
        JLabel labelGName1 = new JLabel("Tên nhóm");

        btnMem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tempGName = inputMemGroupSearch.getText();
                // write info to the server and get the data back
                ArrayList<ArrayList<String>> temp = null;
                updateListMemGroup(temp);
            }
        });

        setTextfield(inputMemGroupSearch);
        setLabel(labelGName1);

        gbcMain.gridy += 1;
        mainPanel.add(labelGName1, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(inputMemGroupSearch, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(btnMem, gbcMain);

        JSeparator sep2 = new JSeparator(SwingConstants.HORIZONTAL);
        gbcMain.gridy += 1;
        gbcMain.gridwidth = 3;
        gbcMain.insets = new Insets(3, 0, 3, 0);
        gbcMain.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(sep2, gbcMain);
        gbcMain.fill = GridBagConstraints.NONE;
        gbcMain.insets = new Insets(0, 0, 2, 0);

        // chức năng 3d
        listAdmin = new JPanel();
        listAdmin.setSize(800, 800);
        listAdmin.setLayout(new GridBagLayout());
        gbcListAdmin = new GridBagConstraints();
        gbcListAdmin.insets = new Insets(0, 2, 5, 2);

        JLabel unameAdmin = new JLabel("Tên đăng nhập (Quản trị viên)");
        JLabel gnameAdmin = new JLabel("Tên nhóm");

        setLabel(unameAdmin);
        setLabel(gnameAdmin);

        gbcListAdmin.gridx = 0;
        gbcListAdmin.gridy = 0;
        listAdmin.add(unameAdmin, gbcListAdmin);

        gbcListAdmin.gridx = 1;
        gbcListAdmin.gridy = 0;
        listAdmin.add(gnameAdmin, gbcListAdmin);

        listAdmin.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JScrollPane myScrollAdmin = new JScrollPane(listAdmin);
        myScrollAdmin.setPreferredSize(new Dimension(600, 300));
        myScrollAdmin.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        myScrollAdmin.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        myScrollAdmin.getVerticalScrollBar().setUnitIncrement(20);

        gbcMain.gridx = 0;
        gbcMain.gridy += 1;
        gbcMain.gridwidth = 3;
        mainPanel.add(myScrollAdmin, gbcMain);

        gbcMain.gridwidth = 1;

        inputAdminSearch = new JTextField();
        JButton btnAdmin = new JButton("Xem danh sách quản trị viên");
        JLabel labelGName2 = new JLabel("Tên nhóm");

        btnAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tempGName = inputAdminSearch.getText();
                // write info to the server and get the data back
                ArrayList<ArrayList<String>> temp = null;
                updateListAdmin(temp);
            }
        });

        setTextfield(inputAdminSearch);
        setLabel(labelGName2);

        gbcMain.gridy += 1;
        mainPanel.add(labelGName2, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(inputAdminSearch, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(btnAdmin, gbcMain);

        JScrollPane outerScrollPane = new JScrollPane(mainPanel);
        outerScrollPane.setPreferredSize(new Dimension(1600, 750));
        outerScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        outerScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outerScrollPane.getVerticalScrollBar().setUnitIncrement(40);

        return outerScrollPane;
    }

    private JScrollPane trang4() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.insets = new Insets(0, 0, 2, 0);

        // chức năng 4a & 4b & 4c
        listSpam = new JPanel();
        listSpam.setSize(800, 800);
        listSpam.setLayout(new GridBagLayout());
        gbcListSpam = new GridBagConstraints();
        gbcListSpam.insets = new Insets(0, 2, 5, 2);

        JLabel uname = new JLabel("Tên đăng nhập");
        JLabel timespam = new JLabel("Thời gian báo cáo");

        setLabel(uname);
        setLabel(timespam);

        gbcListSpam.gridx = 0;
        gbcListSpam.gridy = 0;
        listSpam.add(uname, gbcListSpam);

        gbcListSpam.gridx = 1;
        gbcListSpam.gridy = 0;
        listSpam.add(timespam, gbcListSpam);

        listSpam.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JScrollPane myScrollSpam = new JScrollPane(listSpam);
        myScrollSpam.setPreferredSize(new Dimension(400, 300));
        myScrollSpam.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        myScrollSpam.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        myScrollSpam.getVerticalScrollBar().setUnitIncrement(20);

        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.gridwidth = 3;
        gbcMain.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(myScrollSpam, gbcMain);

        gbcMain.gridwidth = 1;

        btnSortNamet4 = new JRadioButton("Sắp xếp theo tên đăng nhập");
        btnSortDatet4 = new JRadioButton("Sắp xếp theo thời gian báo cáo");
        btnFilterName = new JRadioButton("Lọc theo tên đăng nhập");
        btnFilterDate = new JRadioButton("Lọc theo thời gian");
        inputSpamSearch = new JTextField();
        JButton btn = new JButton("Xem danh sách báo cáo spam");

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sortBy = btnSortNamet4.isSelected() ? 1 : btnSortDatet4.isSelected() ? -1 : 0;
                int filterBy = btnFilterName.isSelected() ? 1 : btnFilterDate.isSelected() ? -1 : 0;
                String tempInputSpamSearch = inputSpamSearch.getText();

                ArrayList<ArrayList<String>> temp = null;
                updateListSpam(temp);
            }
        });

        setTextfield(inputSpamSearch);

        ButtonGroup btnG = new ButtonGroup();
        btnG.add(btnSortNamet4);
        btnG.add(btnSortDatet4);

        ButtonGroup btnG1 = new ButtonGroup();
        btnG1.add(btnFilterName);
        btnG1.add(btnFilterDate);

        gbcMain.anchor = GridBagConstraints.LINE_START;
        gbcMain.gridy += 1;
        mainPanel.add(btnSortNamet4, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(btnSortDatet4, gbcMain);

        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        gbcMain.gridy += 1;
        gbcMain.gridwidth = 1;
        gbcMain.insets = new Insets(3, 0, 3, 0);
        gbcMain.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(sep, gbcMain);
        gbcMain.fill = GridBagConstraints.NONE;
        gbcMain.insets = new Insets(0, 0, 2, 0);

        gbcMain.gridy += 1;
        mainPanel.add(btnFilterName, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(btnFilterDate, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(inputSpamSearch, gbcMain);

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

        JPanel accountLock = new JPanel();
        JLabel label = new JLabel("Tên đăng nhập");
        inputLockt4 = new JTextField(20);
        JButton lock = new JButton("Khóa tài khoản");

        lock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tempInputLockT4 = inputLockt4.getText();
                // write info to the server
            }
        });

        setLabel(label);
        setTextfield(inputLockt4);

        accountLock.add(label);
        accountLock.add(inputLockt4);
        accountLock.add(lock);

        gbcMain.gridy += 1;
        gbcMain.gridwidth = 2;
        mainPanel.add(accountLock, gbcMain);

        JScrollPane outerScrollPane = new JScrollPane(mainPanel);
        outerScrollPane.setPreferredSize(new Dimension(1600, 750));
        outerScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        outerScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outerScrollPane.getVerticalScrollBar().setUnitIncrement(40);

        return outerScrollPane;
    }

    private JScrollPane trang5() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.insets = new Insets(0, 0, 2, 0);

        // chức năng 5a & 5b
        listNew = new JPanel();
        listNew.setSize(800, 800);
        listNew.setLayout(new GridBagLayout());
        gbcListNew = new GridBagConstraints();
        gbcListNew.insets = new Insets(0, 2, 5, 2);

        JLabel uname = new JLabel("Tên đăng nhập");
        JLabel fname = new JLabel("Họ & tên");
        JLabel addr = new JLabel("Địa chỉ");
        JLabel dob = new JLabel("Ngày sinh");
        JLabel gender = new JLabel("Giới tính");
        JLabel email = new JLabel("Email");
        JLabel timecreate = new JLabel("Thời gian tạo tài khoản");

        setLabel(uname);
        setLabel(fname);
        setLabel(addr);
        setLabel(dob);
        setLabel(gender);
        setLabel(email);
        setLabel(timecreate);

        gbcListNew.gridx = 0;
        gbcListNew.gridy = 0;
        listNew.add(uname, gbcListNew);

        gbcListNew.gridx = 1;
        gbcListNew.gridy = 0;
        listNew.add(fname, gbcListNew);

        gbcListNew.gridx = 2;
        gbcListNew.gridy = 0;
        listNew.add(addr, gbcListNew);

        gbcListNew.gridx = 3;
        gbcListNew.gridy = 0;
        listNew.add(dob, gbcListNew);

        gbcListNew.gridx = 4;
        gbcListNew.gridy = 0;
        listNew.add(gender, gbcListNew);

        gbcListNew.gridx = 5;
        gbcListNew.gridy = 0;
        listNew.add(email, gbcListNew);

        gbcListNew.gridx = 6;
        gbcListNew.gridy = 0;
        listNew.add(timecreate, gbcListNew);

        listNew.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JScrollPane myScroll = new JScrollPane(listNew);
        myScroll.setPreferredSize(new Dimension(1200, 300));
        myScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        myScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        myScroll.getVerticalScrollBar().setUnitIncrement(20);

        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.gridwidth = 3;
        mainPanel.add(myScroll, gbcMain);

        gbcMain.gridwidth = 1;

        btnSortNamet5 = new JRadioButton("Sắp xếp theo tên");
        btnSortDatet5 = new JRadioButton("Sắp xếp theo thời gian tạo");
        inputNewSearch = new JTextField();
        JButton btn = new JButton("Xem danh sách đăng ký mới");
        JLabel labelGName = new JLabel("Tên người dùng");
        JLabel fromDate = new JLabel("Từ ngày");
        JLabel toDate = new JLabel("Đến ngày");
        inputFromDate = new JTextField();
        inputToDate = new JTextField();

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sortBy = btnSortNamet5.isSelected() ? 1 : btnSortDatet5.isSelected() ? -1 : 0;
                String tempInputNewSearch = inputNewSearch.getText();
                String tempInputFromDate = inputFromDate.getText();
                String tempInputToDate = inputToDate.getText();

                ArrayList<ArrayList<String>> temp = null;

                updateListNew(temp);
            }
        });

        setLabel(fromDate);
        setLabel(toDate);
        setTextfield(inputFromDate);
        setTextfield(inputToDate);
        setTextfield(inputNewSearch);
        setLabel(labelGName);

        ButtonGroup btnG = new ButtonGroup();
        btnG.add(btnSortNamet5);
        btnG.add(btnSortDatet5);

        gbcMain.anchor = GridBagConstraints.LINE_START;
        gbcMain.gridy += 1;
        mainPanel.add(fromDate, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(inputFromDate, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(toDate, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(inputToDate, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(btnSortNamet5, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(btnSortDatet5, gbcMain);

        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        gbcMain.gridy += 1;
        gbcMain.gridwidth = 1;
        gbcMain.insets = new Insets(3, 0, 3, 0);
        gbcMain.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(sep, gbcMain);
        gbcMain.fill = GridBagConstraints.NONE;
        gbcMain.insets = new Insets(0, 0, 2, 0);

        gbcMain.gridy += 1;
        mainPanel.add(labelGName, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(inputNewSearch, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(btn, gbcMain);

        JScrollPane outerScrollPane = new JScrollPane(mainPanel);
        outerScrollPane.setPreferredSize(new Dimension(1600, 750));
        outerScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        outerScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outerScrollPane.getVerticalScrollBar().setUnitIncrement(40);

        return outerScrollPane;
    }

    private JPanel trang6() {
        JFreeChart chart = this.createChart(this.createDataset());
        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis xAxis = plot.getDomainAxis();
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        // Customize axis as needed

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(900, 370));

        JLabel year = new JLabel("Năm");
        JTextField inputYear = new JTextField();
        JButton btn = new JButton("Xem biểu đồ");

        setLabel(year);
        setTextfield(inputYear);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.anchor = GridBagConstraints.LINE_START;

        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        mainPanel.add(chartPanel, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(year, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(inputYear, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(btn, gbcMain);

        return mainPanel;
    } // not yet

    private JScrollPane trang7() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.insets = new Insets(0, 0, 2, 0);

        // chức năng 7a & 7b & 7c
        listFriendPlus = new JPanel();
        listFriendPlus.setSize(800, 800);
        listFriendPlus.setLayout(new GridBagLayout());
        gbcListFriendPlus = new GridBagConstraints();
        gbcListFriendPlus.insets = new Insets(0, 2, 5, 2);

        JLabel uname = new JLabel("Tên đăng nhập");
        JLabel fr = new JLabel("Bạn bè (trực tiếp)");
        JLabel fr_fr = new JLabel("Bạn bè (trực tiếp & bạn của bạn)");

        setLabel(uname);
        setLabel(fr);
        setLabel(fr_fr);

        gbcListFriendPlus.gridx = 0;
        gbcListFriendPlus.gridy = 0;
        listFriendPlus.add(uname, gbcListFriendPlus);

        gbcListFriendPlus.gridx = 1;
        gbcListFriendPlus.gridy = 0;
        listFriendPlus.add(fr, gbcListFriendPlus);

        gbcListFriendPlus.gridx = 2;
        gbcListFriendPlus.gridy = 0;
        listFriendPlus.add(fr_fr, gbcListFriendPlus);

        listFriendPlus.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JScrollPane myScroll = new JScrollPane(listFriendPlus);
        myScroll.setPreferredSize(new Dimension(900, 300));
        myScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        myScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        myScroll.getVerticalScrollBar().setUnitIncrement(20);

        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.gridwidth = 3;
        mainPanel.add(myScroll, gbcMain);

        gbcMain.gridwidth = 1;

        btnSortNamet7 = new JRadioButton("Sắp xếp theo tên");
        btnSortDatet7 = new JRadioButton("Sắp xếp theo thời gian tạo");
        JButton btn = new JButton("Xem danh sách bạn bè");
        JLabel labelNName = new JLabel("Tên người dùng");
        inputNameSearch = new JTextField();
        JLabel dir_fr = new JLabel("Số lượng bạn bè trực tiếp");
        inputDir_fr = new JTextField();

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sortBy = btnSortNamet7.isSelected() ? 1 : btnSortDatet7.isSelected() ? -1 : 0;
                String tempInputNameSearch = inputNameSearch.getText();
                String tempInputDir_fr = inputDir_open.getText();

                ArrayList<ArrayList<String>> temp = null;

                updateListFriendPlus(temp);
            }
        });

        setLabel(dir_fr);
        setLabel(labelNName);
        setTextfield(inputDir_fr);
        setTextfield(inputNameSearch);

        ButtonGroup btnG = new ButtonGroup();
        btnG.add(btnSortNamet7);
        btnG.add(btnSortDatet7);

        gbcMain.anchor = GridBagConstraints.LINE_START;

        gbcMain.gridy += 1;
        mainPanel.add(btnSortNamet7, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(btnSortDatet7, gbcMain);

        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        gbcMain.gridy += 1;
        gbcMain.gridwidth = 1;
        gbcMain.insets = new Insets(3, 0, 3, 0);
        gbcMain.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(sep, gbcMain);
        gbcMain.fill = GridBagConstraints.NONE;
        gbcMain.insets = new Insets(0, 0, 2, 0);

        gbcMain.gridy += 1;
        mainPanel.add(labelNName, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(inputNameSearch, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(dir_fr, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(inputDir_fr, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(btn, gbcMain);

        JScrollPane outerScrollPane = new JScrollPane(mainPanel);
        outerScrollPane.setPreferredSize(new Dimension(1600, 750));
        outerScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        outerScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outerScrollPane.getVerticalScrollBar().setUnitIncrement(40);

        return outerScrollPane;
    }

    private JScrollPane trang8() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.insets = new Insets(0, 0, 2, 0);

        // chức năng 8a & 8b & 8c
        listOpen = new JPanel();
        listOpen.setSize(800, 800);
        listOpen.setLayout(new GridBagLayout());
        gbcListOpen = new GridBagConstraints();
        gbcListOpen.insets = new Insets(0, 2, 5, 2);

        JLabel uname = new JLabel("Tên đăng nhập");
        JLabel open = new JLabel("Số lần mở ứng dụng");
        JLabel chatPeop = new JLabel("Số lượng người đã chat");
        JLabel chatGroup = new JLabel("Số lượng nhóm đã chat");

        setLabel(uname);
        setLabel(open);
        setLabel(chatPeop);
        setLabel(chatGroup);

        gbcListOpen.gridx = 0;
        gbcListOpen.gridy = 0;
        listOpen.add(uname, gbcListOpen);

        gbcListOpen.gridx = 1;
        listOpen.add(open, gbcListOpen);

        gbcListOpen.gridx = 2;
        listOpen.add(chatPeop, gbcListOpen);

        gbcListOpen.gridx = 3;
        listOpen.add(chatGroup, gbcListOpen);

        listOpen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JScrollPane myScroll = new JScrollPane(listOpen);
        myScroll.setPreferredSize(new Dimension(900, 300));
        myScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        myScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        myScroll.getVerticalScrollBar().setUnitIncrement(20);

        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.gridwidth = 3;
        mainPanel.add(myScroll, gbcMain);

        gbcMain.gridwidth = 1;

        btnSortNamet8 = new JRadioButton("Sắp xếp theo tên");
        btnSortDatet8 = new JRadioButton("Sắp xếp theo thời gian tạo");
        JButton btn = new JButton("Xem danh sách người dùng hoạt động");
        JLabel labelNName = new JLabel("Tên người dùng");
        inputNameSearcht8 = new JTextField();
        JLabel dir_open = new JLabel("Số lượng hoạt động");
        inputDir_open = new JTextField();

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sortBy = btnSortNamet8.isSelected() ? 1 : btnSortDatet8.isSelected() ? -1 : 0;
                String tempInputNameSearcht8 = inputNameSearcht8.getText();
                String tempInputDir_open = inputDir_open.getText();

                ArrayList<ArrayList<String>> temp = null;

                updateListOpen(temp);
            }
        });

        setLabel(dir_open);
        setLabel(labelNName);
        setTextfield(inputDir_open);
        setTextfield(inputNameSearcht8);

        ButtonGroup btnG = new ButtonGroup();
        btnG.add(btnSortNamet8);
        btnG.add(btnSortDatet8);

        gbcMain.anchor = GridBagConstraints.LINE_START;

        gbcMain.gridy += 1;
        mainPanel.add(btnSortNamet8, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(btnSortDatet8, gbcMain);

        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        gbcMain.gridy += 1;
        gbcMain.gridwidth = 1;
        gbcMain.insets = new Insets(3, 0, 3, 0);
        gbcMain.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(sep, gbcMain);
        gbcMain.fill = GridBagConstraints.NONE;
        gbcMain.insets = new Insets(0, 0, 2, 0);

        gbcMain.gridy += 1;
        mainPanel.add(labelNName, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(inputNameSearcht8, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(dir_open, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(inputDir_open, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(btn, gbcMain);

        JScrollPane outerScrollPane = new JScrollPane(mainPanel);
        outerScrollPane.setPreferredSize(new Dimension(1600, 750));
        outerScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        outerScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outerScrollPane.getVerticalScrollBar().setUnitIncrement(40);

        return outerScrollPane;
    }

    private JPanel trang9() {
        JFreeChart chart = this.createChart1(this.createDataset1());
        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis xAxis = plot.getDomainAxis();
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        // Customize axis as needed

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(900, 370));

        JLabel year = new JLabel("Năm");
        JTextField inputYear = new JTextField();
        JButton btn = new JButton("Xem biểu đồ");

        setLabel(year);
        setTextfield(inputYear);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.anchor = GridBagConstraints.LINE_START;

        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        mainPanel.add(chartPanel, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(year, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(inputYear, gbcMain);

        gbcMain.gridy += 1;
        mainPanel.add(btn, gbcMain);

        return mainPanel;
    } // not yet

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(10, "Người đăng ký mới", "1");
        dataset.addValue(15, "Người đăng ký mới", "2");
        dataset.addValue(20, "Người đăng ký mới", "3");
        dataset.addValue(35, "Người đăng ký mới", "4");
        dataset.addValue(46, "Người đăng ký mới", "5");
        dataset.addValue(25, "Người đăng ký mới", "6");
        dataset.addValue(76, "Người đăng ký mới", "7");
        dataset.addValue(88, "Người đăng ký mới", "8");
        dataset.addValue(109, "Người đăng ký mới", "9");
        dataset.addValue(51, "Người đăng ký mới", "10");
        dataset.addValue(10, "Người đăng ký mới", "11");
        dataset.addValue(25, "Người đăng ký mới", "12");

        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset) {
        return ChartFactory.createBarChart(
                "Biểu đồ số lượng người đăng ký mới năm 2023",
                "Tháng",
                "Số lượng",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
    }

    private CategoryDataset createDataset1() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(10, "Người mở ứng dụng", "1");
        dataset.addValue(15, "Người mở ứng dụng", "2");
        dataset.addValue(20, "Người mở ứng dụng", "3");
        dataset.addValue(35, "Người mở ứng dụng", "4");
        dataset.addValue(46, "Người mở ứng dụng", "5");
        dataset.addValue(25, "Người mở ứng dụng", "6");
        dataset.addValue(76, "Người mở ứng dụng", "7");
        dataset.addValue(88, "Người mở ứng dụng", "8");
        dataset.addValue(109, "Người mở ứng dụng", "9");
        dataset.addValue(51, "Người mở ứng dụng", "10");
        dataset.addValue(10, "Người mở ứng dụng", "11");
        dataset.addValue(25, "Người mở ứng dụng", "12");

        return dataset;
    }

    private JFreeChart createChart1(CategoryDataset dataset) {
        return ChartFactory.createBarChart(
                "Biểu đồ số lượng người mở ứng dụng năm 2023",
                "Tháng",
                "Số lượng",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
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
                            String dataPart = message.split("\\|")[1];

                            String[] strings = dataPart.split(", ");

                            ArrayList<String> result = new ArrayList<>(Arrays.asList(strings));
                            if (message == null) {
                                break;
                            } else if (message.startsWith("AdminGetListUser|")) {
                                if (message.split("\\|").length > 2) {
                                    updateListUser(result, 1);
                                } else {
                                    updateListUser(result, 0);
                                }
                            } else if (message.startsWith("AdminGetListLoginHistory|")) {
                                if (message.split("\\|").length > 2) {
                                    updateListLoginHist(result, 1);
                                } else {
                                    updateListLoginHist(result, 0);
                                }
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
