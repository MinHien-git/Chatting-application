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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class testwindow {
	private Thread thread;
    private BufferedWriter os;
    private BufferedReader is;
    private Socket socketOfClient;
	private JFrame frmClientDemo;
	private JTextField userId1_send_message;
	private JTextField message_send_direct;
	private JTextField Add_User;
	private JTextField Delete_User;
	private JTextField Current_DELETE_MESSAGE_USER;
	private JTextField userId2_send_message;
	private JTextField Current_Add_user;
	private JTextField Current_Delete_user;
	private JTextField FROM_DELETE_MESSAGE_USER;
	private JTextField CREATE_GROUP_NAME;
	private JTextField ID_ADMIN_GROUP_NAME;
	private JTextField GETONLINE_USER;
	private JTextField SETUSERID_ONLINE;
	private JTextField SETUSERID_OFFLINE;
	private JTextField CURRENTUSERBLOCK;
	private JTextField BLOCKEDUSER;
	private JTextField CHANGEGROUPNAMEID;
	private JTextField CHANGEGROUPNAME_NEWNAME;
	private JTextField ADDMEMEBER_USERID;
	private JTextField ADDMEMEBER_ID;
	private JTextField DELETEMEMBER_MEMBERID;
	private JTextField DELETEMEMBER_GROUPID;
	private JTextField GROUPCHAT_ID;
	private JTextField GROUPCHAT_MEMBERID;
	private JTextField GROUPCHAT_ID_CONTENT;

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
		setUpSocket();
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
		
		
		
		//SEND MESSAGE
		userId1_send_message = new JTextField();
		userId1_send_message.setBounds(53, 27, 35, 24);
		panel.add(userId1_send_message);
		userId1_send_message.setColumns(10);
		message_send_direct = new JTextField();
		message_send_direct.setColumns(10);
		message_send_direct.setBounds(181, 27, 232, 24);
		panel.add(message_send_direct);
		
		JButton send_direct_message = new JButton("Gửi");
		
		send_direct_message.setBounds(423, 28, 89, 23);
		panel.add(send_direct_message);
		
		send_direct_message.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id1 = userId1_send_message.getText();//gửi đến user
				String id2 = userId2_send_message.getText();// từ user
				try {
					write("DirectMessage|"+id1+"|"+id2+"|"+message_send_direct.getText());
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
				userId1_send_message.setText("");
				userId2_send_message.setText("");
			}
		}); 
		
		JLabel lblNewLabel = new JLabel("send to");
		lblNewLabel.setBounds(10, 32, 46, 14);
		panel.add(lblNewLabel);
		
		Add_User = new JTextField();
		Add_User.setColumns(10);
		Add_User.setBounds(53, 62, 35, 24);
		panel.add(Add_User);
		
		JLabel lblNewLabel_1 = new JLabel("send to");
		lblNewLabel_1.setBounds(10, 67, 46, 14);
		panel.add(lblNewLabel_1);
		
		JButton AddFriendButton = new JButton("Thêm bạn bè");
		
		
		//Thêm bạn 
		AddFriendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id1 = Add_User.getText(); //gửi đến user
				String id2 = Current_Add_user.getText(); // từ user
				try {
					write("DeleteFriend|"+id1+"|"+id2);
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
				Add_User.setText("");
				Current_Add_user.setText("");
			}
		});
		AddFriendButton.setBounds(181, 63, 110, 23);
		panel.add(AddFriendButton);
		
		JButton DeleteFriend = new JButton("Xoá kết bạn");
		DeleteFriend.setBounds(181, 98, 110, 23);
		panel.add(DeleteFriend);
		
		Delete_User = new JTextField();
		Delete_User.setColumns(10);
		Delete_User.setBounds(53, 97, 35, 24);
		panel.add(Delete_User);
		JLabel lblNewLabel_1_1 = new JLabel("send to");
		lblNewLabel_1_1.setBounds(10, 102, 46, 14);
		panel.add(lblNewLabel_1_1);
		
		
		//Xoá kết bạn
		DeleteFriend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id1 = Delete_User.getText();//gửi đến user
				String id2 = Current_Delete_user.getText();// từ user
				try {
					write("DeleteFriend|"+id1+"|"+id2);
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
				Delete_User.setText("");
				Current_Delete_user.setText("");
			}
		});
		
		
		JLabel lblNewLabel_1_1_1 = new JLabel("current user");
		lblNewLabel_1_1_1.setBounds(10, 136, 65, 14);
		panel.add(lblNewLabel_1_1_1);
		Current_DELETE_MESSAGE_USER = new JTextField();
		Current_DELETE_MESSAGE_USER.setColumns(10);
		Current_DELETE_MESSAGE_USER.setBounds(74, 132, 35, 24);
		panel.add(Current_DELETE_MESSAGE_USER);
		
		
		//Xoá tin nhắn 
		JButton DELETE_MESSAGE = new JButton("Xoá tin nhắn");
		DELETE_MESSAGE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id1 = Current_DELETE_MESSAGE_USER.getText();//gửi đến user
				String id2 = FROM_DELETE_MESSAGE_USER.getText();// từ user
				try {
					write("DeleteMessage|"+id1+"|"+id2);
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
				Current_DELETE_MESSAGE_USER.setText("");
				FROM_DELETE_MESSAGE_USER.setText("");
			}
		});
		
		DELETE_MESSAGE.setBounds(202, 132, 110, 23);
		panel.add(DELETE_MESSAGE);
		
		userId2_send_message = new JTextField();
		userId2_send_message.setColumns(10);
		userId2_send_message.setBounds(125, 27, 46, 24);
		panel.add(userId2_send_message);
		
		JLabel lblFrom = new JLabel("from");
		lblFrom.setBounds(97, 32, 46, 14);
		panel.add(lblFrom);
		
		Current_Add_user = new JTextField();
		Current_Add_user.setColumns(10);
		Current_Add_user.setBounds(125, 62, 46, 24);
		panel.add(Current_Add_user);
		
		JLabel lblFrom_1 = new JLabel("from");
		lblFrom_1.setBounds(98, 67, 22, 14);
		panel.add(lblFrom_1);
		
		JLabel lblFrom_1_1 = new JLabel("from");
		lblFrom_1_1.setBounds(98, 102, 22, 14);
		panel.add(lblFrom_1_1);
		
		Current_Delete_user = new JTextField();
		Current_Delete_user.setColumns(10);
		Current_Delete_user.setBounds(125, 97, 46, 24);
		panel.add(Current_Delete_user);
		
		JLabel lblFrom_1_1_1 = new JLabel("from");
		lblFrom_1_1_1.setBounds(119, 137, 22, 14);
		panel.add(lblFrom_1_1_1);
		
		FROM_DELETE_MESSAGE_USER = new JTextField();
		FROM_DELETE_MESSAGE_USER.setColumns(10);
		FROM_DELETE_MESSAGE_USER.setBounds(146, 132, 46, 24);
		panel.add(FROM_DELETE_MESSAGE_USER);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("group name");
		lblNewLabel_1_1_1_1.setBounds(10, 171, 65, 14);
		panel.add(lblNewLabel_1_1_1_1);
		
		CREATE_GROUP_NAME = new JTextField();
		CREATE_GROUP_NAME.setColumns(10);
		CREATE_GROUP_NAME.setBounds(74, 167, 97, 24);
		panel.add(CREATE_GROUP_NAME);
		
		ID_ADMIN_GROUP_NAME = new JTextField();
		ID_ADMIN_GROUP_NAME.setColumns(10);
		ID_ADMIN_GROUP_NAME.setBounds(247, 166, 46, 24);
		panel.add(ID_ADMIN_GROUP_NAME);
		
		JLabel lblFrom_1_1_1_1 = new JLabel("created by");
		lblFrom_1_1_1_1.setBounds(181, 172, 52, 14);
		panel.add(lblFrom_1_1_1_1);
		
		JButton CREATEGROUPBUTTON = new JButton("Tạo nhóm");
		
		
		//Tạo nhóm
		CREATEGROUPBUTTON.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = CREATE_GROUP_NAME.getText();//gửi đến user
				String id = ID_ADMIN_GROUP_NAME.getText();// từ user
				try {
					write("CreateGroup|"+name+"|"+id);
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
				CREATE_GROUP_NAME.setText("");
				ID_ADMIN_GROUP_NAME.setText("");
			}
		});
		
		CREATEGROUPBUTTON.setBounds(303, 167, 110, 23);
		panel.add(CREATEGROUPBUTTON);
		
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
		
		GETONLINE_USER = new JTextField();
		GETONLINE_USER.setColumns(10);
		GETONLINE_USER.setBounds(42, 226, 46, 24);
		panel.add(GETONLINE_USER);
		
		JLabel lblFrom_1_1_1_1_1 = new JLabel("id");
		lblFrom_1_1_1_1_1.setBounds(10, 231, 78, 14);
		panel.add(lblFrom_1_1_1_1_1);
		
		JButton GETONLINEFRIEND = new JButton("Lấy bạn bè online");
		
		
		//GET BAN BE ONLINE
		GETONLINEFRIEND.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id1 = GETONLINE_USER.getText();
				try {
					write("GetOnline|"+id1);
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
				GETONLINE_USER.setText("");
			}
		});
		
		GETONLINEFRIEND.setBounds(97, 227, 152, 23);
		panel.add(GETONLINEFRIEND);
		
		JTextArea ONLINEUSER = new JTextArea();
		ONLINEUSER.setBounds(10, 261, 403, 74);
		panel.add(ONLINEUSER);
		
		JLabel lblFrom_1_1_1_1_1_1 = new JLabel("id");
		lblFrom_1_1_1_1_1_1.setBounds(10, 201, 78, 14);
		panel.add(lblFrom_1_1_1_1_1_1);
		
		SETUSERID_ONLINE = new JTextField();
		SETUSERID_ONLINE.setColumns(10);
		SETUSERID_ONLINE.setBounds(42, 196, 46, 24);
		panel.add(SETUSERID_ONLINE);
		
		JButton SETUSERONLINE = new JButton("set user online");
		
		
		//SET NGƯỜI DÙNG ONLINE
		SETUSERONLINE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id1 = SETUSERID_ONLINE.getText();
				try {
					write("Online|"+id1);
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
				SETUSERID_ONLINE.setText("");
			}
		});
		
		SETUSERONLINE.setBounds(97, 197, 136, 23);
		panel.add(SETUSERONLINE);
		
		SETUSERID_OFFLINE = new JTextField();
		SETUSERID_OFFLINE.setColumns(10);
		SETUSERID_OFFLINE.setBounds(279, 196, 46, 24);
		panel.add(SETUSERID_OFFLINE);
		
		JLabel lblFrom_1_1_1_1_1_1_1 = new JLabel("id");
		lblFrom_1_1_1_1_1_1_1.setBounds(247, 201, 78, 14);
		panel.add(lblFrom_1_1_1_1_1_1_1);
		
		JButton SETUSEROFFLINE = new JButton("set user offline");
		
		
		//SET NGƯỜI DÙNG Offline
		SETUSEROFFLINE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id1 = SETUSERID_OFFLINE.getText();
				try {
					write("Offline|"+id1);
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
				SETUSERID_OFFLINE.setText("");
			}
		});
		SETUSEROFFLINE.setBounds(334, 197, 136, 23);
		panel.add(SETUSEROFFLINE);
		
		CURRENTUSERBLOCK = new JTextField();
		CURRENTUSERBLOCK.setColumns(10);
		CURRENTUSERBLOCK.setBounds(74, 346, 35, 24);
		panel.add(CURRENTUSERBLOCK);
		
		JLabel lblNewLabel_1_1_1_2 = new JLabel("current user");
		lblNewLabel_1_1_1_2.setBounds(10, 350, 65, 14);
		panel.add(lblNewLabel_1_1_1_2);
		
		BLOCKEDUSER = new JTextField();
		BLOCKEDUSER.setColumns(10);
		BLOCKEDUSER.setBounds(146, 346, 46, 24);
		panel.add(BLOCKEDUSER);
		
		JButton btnBlockTk = new JButton("Block tk");
		
		
		//Block ACCOUNT
		btnBlockTk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id1 = BLOCKEDUSER.getText();//nguoi muon block đến user
				String id2 = CURRENTUSERBLOCK.getText();// từ user
				try {
					write("BlockAccount|"+id1+"|"+id2);
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
				CURRENTUSERBLOCK.setText("");
				CURRENTUSERBLOCK.setText("");
			}
		});
		
		btnBlockTk.setBounds(202, 346, 110, 23);
		panel.add(btnBlockTk);
		
		JLabel lblFrom_1_1_1_2 = new JLabel("from");
		lblFrom_1_1_1_2.setBounds(119, 351, 22, 14);
		panel.add(lblFrom_1_1_1_2);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("group id");
		lblNewLabel_1_1_1_1_1.setBounds(10, 386, 65, 14);
		panel.add(lblNewLabel_1_1_1_1_1);
		
		CHANGEGROUPNAMEID = new JTextField();
		CHANGEGROUPNAMEID.setColumns(10);
		CHANGEGROUPNAMEID.setBounds(53, 381, 97, 24);
		panel.add(CHANGEGROUPNAMEID);
		
		JLabel lblFrom_1_1_1_1_2 = new JLabel("Tên mới");
		lblFrom_1_1_1_1_2.setBounds(160, 386, 52, 14);
		panel.add(lblFrom_1_1_1_1_2);
		
		JButton CHANGEGROPNAME_BTN = new JButton("Đổi tên");
		CHANGEGROPNAME_BTN.setBounds(303, 382, 110, 23);
		panel.add(CHANGEGROPNAME_BTN);
		
		CHANGEGROUPNAME_NEWNAME = new JTextField();
		CHANGEGROUPNAME_NEWNAME.setColumns(10);
		CHANGEGROUPNAME_NEWNAME.setBounds(202, 381, 97, 24);
		panel.add(CHANGEGROUPNAME_NEWNAME);
		
		
		//CHANGE GROUP NAME
		CHANGEGROPNAME_BTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id1 = CHANGEGROUPNAMEID.getText();//nguoi muon block đến user
				String newName = CHANGEGROUPNAME_NEWNAME.getText();// từ user
				try {
					write("ChangeGroupName|"+id1+"|"+newName);
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
				CHANGEGROUPNAMEID.setText("");
				CHANGEGROUPNAME_NEWNAME.setText("");
			}
		});
		
		JButton ADDMEMBER_BTN = new JButton("Thêm");
		ADDMEMEBER_USERID = new JTextField();
		ADDMEMEBER_USERID.setColumns(10);
		ADDMEMEBER_USERID.setBounds(223, 411, 97, 24);
		panel.add(ADDMEMEBER_USERID);
		
		JLabel lblFrom_1_1_1_1_2_1 = new JLabel("id thành viên");
		lblFrom_1_1_1_1_2_1.setBounds(160, 416, 73, 14);
		panel.add(lblFrom_1_1_1_1_2_1);
		
		ADDMEMEBER_ID = new JTextField();
		ADDMEMEBER_ID.setColumns(10);
		ADDMEMEBER_ID.setBounds(53, 411, 97, 24);
		panel.add(ADDMEMEBER_ID);
		
		
		//Add member to group
		ADDMEMBER_BTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id1 = ADDMEMEBER_ID.getText();//nguoi muon block đến user
				String id2 = ADDMEMEBER_USERID.getText();// từ user
				try {
					write("AddMemberToGroup|"+id1+"|"+id2);
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
				ADDMEMEBER_ID.setText("");
				ADDMEMEBER_USERID.setText("");
			}
		});
		ADDMEMBER_BTN.setBounds(328, 411, 110, 23);
		panel.add(ADDMEMBER_BTN);
		
		
		
		JLabel lblNewLabel_1_1_1_1_1_1 = new JLabel("group id");
		lblNewLabel_1_1_1_1_1_1.setBounds(10, 416, 65, 14);
		panel.add(lblNewLabel_1_1_1_1_1_1);
		
		JButton btnXoTinNhn_1_3_1_1 = new JButton("Xoá");
		
		
		//Remove member
		btnXoTinNhn_1_3_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id1 = DELETEMEMBER_GROUPID.getText();//nguoi muon block đến user
				String id2 = DELETEMEMBER_MEMBERID.getText();// từ user
				try {
					write("RemoveMemberGroup|"+id1+"|"+id2);
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
				DELETEMEMBER_GROUPID.setText("");
				DELETEMEMBER_MEMBERID.setText("");
			}
		});
		btnXoTinNhn_1_3_1_1.setBounds(328, 445, 110, 23);
		panel.add(btnXoTinNhn_1_3_1_1);
		
		DELETEMEMBER_MEMBERID = new JTextField();
		DELETEMEMBER_MEMBERID.setColumns(10);
		DELETEMEMBER_MEMBERID.setBounds(223, 445, 97, 24);
		panel.add(DELETEMEMBER_MEMBERID);
		
		JLabel lblFrom_1_1_1_1_2_1_1 = new JLabel("id thành viên");
		lblFrom_1_1_1_1_2_1_1.setBounds(160, 450, 73, 14);
		panel.add(lblFrom_1_1_1_1_2_1_1);
		
		DELETEMEMBER_GROUPID = new JTextField();
		DELETEMEMBER_GROUPID.setColumns(10);
		DELETEMEMBER_GROUPID.setBounds(53, 445, 97, 24);
		panel.add(DELETEMEMBER_GROUPID);
		
		JLabel lblNewLabel_1_1_1_1_1_1_1 = new JLabel("group id");
		lblNewLabel_1_1_1_1_1_1_1.setBounds(10, 450, 65, 14);
		panel.add(lblNewLabel_1_1_1_1_1_1_1);
		
		JLabel lblGroupId = new JLabel("Group id");
		lblGroupId.setBounds(10, 484, 46, 14);
		panel.add(lblGroupId);
		
		GROUPCHAT_ID = new JTextField();
		GROUPCHAT_ID.setColumns(10);
		GROUPCHAT_ID.setBounds(53, 479, 35, 24);
		panel.add(GROUPCHAT_ID);
		
		JLabel lblFrom_2 = new JLabel("from");
		lblFrom_2.setBounds(97, 484, 46, 14);
		panel.add(lblFrom_2);
		
		GROUPCHAT_MEMBERID = new JTextField();
		GROUPCHAT_MEMBERID.setColumns(10);
		GROUPCHAT_MEMBERID.setBounds(125, 479, 46, 24);
		panel.add(GROUPCHAT_MEMBERID);
		
		GROUPCHAT_ID_CONTENT = new JTextField();
		GROUPCHAT_ID_CONTENT.setColumns(10);
		GROUPCHAT_ID_CONTENT.setBounds(181, 479, 232, 24);
		panel.add(GROUPCHAT_ID_CONTENT);
		
		JButton GROUPCHAT_BTN = new JButton("Gửi");
		
		
		//Send Message in group
		GROUPCHAT_BTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id1 = GROUPCHAT_ID.getText();//nguoi muon block đến user
				String id2 = GROUPCHAT_MEMBERID.getText();// từ user
				String content = GROUPCHAT_ID_CONTENT.getText();
				try {
					write("GroupChat|"+id1+"|"+id2+"|"+content);
				}catch (IOException ex) {
					//JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
				}
				GROUPCHAT_ID.setText("");
				GROUPCHAT_MEMBERID.setText("");
				GROUPCHAT_ID_CONTENT.setText("");
			}
		});
		GROUPCHAT_BTN.setBounds(423, 480, 89, 23);
		panel.add(GROUPCHAT_BTN);
		
		JLabel lblNewLabel_1_2_2_2 = new JLabel("Dùng id");
		lblNewLabel_1_2_2_2.setBounds(518, 484, 65, 14);
		panel.add(lblNewLabel_1_2_2_2);
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
//	                    os.close();
//	                    is.close();
//	                    socketOfClient.close();
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
