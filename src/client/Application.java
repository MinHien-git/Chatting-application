package client;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.Font;
import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Array;
import java.util.HashSet;
import java.util.Properties;
import server.Server;
import server.ServerThread;
import server.ServerThreadBus;
import javax.mail.*;  
import javax.mail.internet.*;  
import javax.activation.*;

public class Application {
    public static JFrame applicationFrame;
    private Thread thread;
    private static BufferedWriter os;
    private static BufferedReader is;
    private Socket socketOfClient;
    public User currentUser;
    public static String id;
    public static String userID;
    public static Application app;
    public JPanel mainPanel;
    public String focusIDString;
    public String focusNameString;
    
    public void write(String message) throws IOException{
        os.write(message + "|" + id);
        os.newLine();
        os.flush();
    }

    public void setUpSocket() {
        try {
            thread = new Thread() {
                @Override
                public void run() {
                		
                    try {
                        socketOfClient = new Socket("127.0.0.1", 7777);
                        System.out.println("Successfully Connected!");
                        // Tạo luồng đầu ra tại client (Gửi dữ liệu tới server)
                        os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
                        // Luồng đầu vào tại Client (Nhận dữ liệu từ server).
                        is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));
                        String message;
                        while (true) {
                            message = is.readLine();
                            System.out.println("cmd:"+" "+message);
                            String[] dataSplit = message.split("\\|");
                            
                            if (message == null) {
                                break;
                            }
                            //Setup send ID
                            if(dataSplit[0].equals("id")) {
                            	id = dataSplit[1];
                            	System.out.println(id);
                            }
                            if(dataSplit[0].equals("Login_Success")) {
                            	currentUser = new User(dataSplit[1],dataSplit[2],dataSplit[3],dataSplit[4]);
                            	onlineUsers onlList = new onlineUsers(app, currentUser);
                            	friends flist = new friends(app,currentUser);
    							chatting c = new chatting(app);
    							globalChatHistory gbc = new globalChatHistory();
    							ClearTab();
    							try {
    								write("Online|"+ currentUser.getId());
    							}catch (IOException ex) {
    								System.out.println("An error occurred");
    								ex.printStackTrace();
    							}
    							applicationFrame.setLayout(new BorderLayout());
    							//Application.getApplicationFrame().setVisible(true);
    							ChangeTab(new home(app,applicationFrame,onlList, flist, c, gbc),600, 600);
                            	//User user = new User(Integer.toString(id) ,name.getText(),email.getText(),hashedPW);
                            }else if(dataSplit[0].equals("Reset_password")){
                            	JOptionPane.showMessageDialog(applicationFrame,"Please Check your email");
                        	}
                            else if(dataSplit[0].equals("Register_Success")) {
                            	System.out.print("Register_Success");
                            	JOptionPane.showMessageDialog(applicationFrame, "You are successfully registered, you will be redirected to the login page shortly");
                            	ClearTab();
                            	ChangeTab(new login(app),605, 476);
                            }else if(dataSplit[0].equals("OnlineList")) {
                            	if(mainPanel instanceof home) {
                            		home home = (home) mainPanel;
                            		onlineUsers olUsers  = (onlineUsers)home.userPanel;
                            		friends flist  = (friends)home.friendsList;
                            		
                            		currentUser.friends.clear();

                            		String[] current = message.split("\\|\\|");
                            		for(int i= 1;i < current.length;++i) {
                            			String[] m = current[i].split("\\|");
                            			
                            			if(m[0].equals("user")) {
                            				currentUser.friends.add(new User(m[1],m[2],m[3].equals("true") ? true : false));
                            				System.out.println(current[i] +"| "+ current.length);
                            			}
                            			System.out.println(current[i] +"| "+m[0]);
                            		}
                            		
                            		flist.UpdateList(currentUser);
                            		olUsers.UpdateList(currentUser);
                            	}
                            }if(dataSplit[0].equals("MessageData")) {
                            	
                            	System.out.println("call in MessageData");
                            	if(mainPanel instanceof home) {
                            		home home = (home) mainPanel;
                            		chatting chatting  = (chatting)home.chatPanel;
                            		//chatting.ClearChat();
                            		String[] chatSplit = message.split("\\|\\|");
                            		String[] messageStrings = chatSplit[1].split("\\|");
                            		for(int i =0;i < messageStrings.length;++i) {
                            			String msg = messageStrings[i].replace(app.focusIDString +" -","("+app.focusNameString+")")
                            					.replace(app.currentUser.getId() +" -","("+app.currentUser.getName()+")");
                            			
                            			chatting.AddChat(msg);
                            		}
                            	}
                            }else if(dataSplit[0].equals("SendToUser")) {
                            	if(mainPanel instanceof home) {
                            		home home = (home) mainPanel;
                            		chatting chatting  = (chatting)home.chatPanel;
                            		
                            		onlineUsers olOnlineUsers  = (onlineUsers)home.userPanel;
                            		olOnlineUsers.ClearChat();
                            		
                            		currentUser.updateFriend(dataSplit[1]);
                            		olOnlineUsers.UpdateList(currentUser);
                            		System.out.println(dataSplit[1] + " " + app.focusIDString);

                            	}
                            }
                            else if(dataSplit[0].equals("IsOffline")) {
                            	System.out.print(dataSplit[1]);
                            	if(mainPanel instanceof home) {
                            		home home = (home) mainPanel;
                            		onlineUsers olUsers  = (onlineUsers)home.userPanel;
                            		olUsers.SetOffline(dataSplit[1]);
                            	}
                            }else if(dataSplit[0].equals("IsOnline")) {
                            	System.out.print(dataSplit[1]);
                            	if(mainPanel instanceof home) {
                            		home home = (home) mainPanel;
                            		onlineUsers olUsers  = (onlineUsers)home.userPanel;
                            		olUsers.SetOnline(dataSplit[1]);
                            	}
                            }else if(dataSplit[0].equals("AddFriendSuccess")) {
                            	JOptionPane.showMessageDialog(mainPanel, "You just added new friend to the friends list");
                            }
                        }
                    os.close();
                    is.close();
                    socketOfClient.close();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            
            thread.run();
        } catch (Exception e) {
        }
    }

    public Application() {
        try {
        	applicationFrame = new JFrame();
        	applicationFrame.add(new login(this));
        	this.app = this;
            applicationFrame.setForeground(Color.BLACK);
            applicationFrame.setTitle("Login");
            applicationFrame.setFont(new Font("Source Code Pro Light", Font.PLAIN, 12));
            applicationFrame.getContentPane().setBackground(Color.WHITE);
            applicationFrame.setBackground(Color.WHITE);
            applicationFrame.getContentPane().setFont(new Font("Source Code Pro Medium", Font.PLAIN, 11));
            applicationFrame.getContentPane().setLayout(new BoxLayout(applicationFrame.getContentPane(), BoxLayout.X_AXIS));
        	applicationFrame.setBounds(100, 100, 605, 476);
            applicationFrame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JFrame getApplicationFrame() {
        return applicationFrame;
    }
    
    public void ClearTab() {
    	applicationFrame.getContentPane().removeAll();
    }
    
    public void ChangeTab(String name) {
    	applicationFrame.setTitle(name);
    }
    
    public void ChangeTab(JPanel newPanel,int h,int w) {
    	applicationFrame.add(newPanel);
    	
    	applicationFrame.setForeground(Color.BLACK);
        applicationFrame.setTitle("Login");
        applicationFrame.setFont(new Font("Source Code Pro Light", Font.PLAIN, 12));
        applicationFrame.getContentPane().setBackground(Color.WHITE);
        applicationFrame.setBackground(Color.WHITE);
        applicationFrame.getContentPane().setFont(new Font("Source Code Pro Medium", Font.PLAIN, 11));
        applicationFrame.getContentPane().setLayout(new BoxLayout(applicationFrame.getContentPane(), BoxLayout.X_AXIS));
    	applicationFrame.pack();
        applicationFrame.setVisible(true);
        applicationFrame.setSize(h, w);
        mainPanel = newPanel;
    }

    public static void main(String[] args) {
        
        app = new Application();
        app.setUpSocket();
    }
}