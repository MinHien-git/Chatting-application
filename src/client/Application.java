package client;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.Font;
import java.awt.Color;
import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Properties;
import server.Server;
import server.ServerThread;
import server.ServerThreadBus;
import javax.mail.*;  
import javax.mail.internet.*;  
import javax.activation.*;

public class Application {
    private static JFrame applicationFrame;
    private Thread thread;
    private static BufferedWriter os;
    private static BufferedReader is;
    private Socket socketOfClient;
    public User currentUser;
    public static String id;
    public static Application app;
    
    public void write(String message) throws IOException{
    	System.out.println(message + "|" + id);
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
                            String[] dataSplit = message.split("\\|");
                            System.out.println("clear"+" "+message);
                            if (message == null) {
                                break;
                            }
                            //Setup send ID
                            if(dataSplit[0].equals("id")) {
                            	id = dataSplit[1];
                            	System.out.println(id);
                            }
                            if(dataSplit[0].equals("Login_Success")) {
                            	
                            	//User user = new User(Integer.toString(id) ,name.getText(),email.getText(),hashedPW);
            					
                            }if(dataSplit[0].equals("Reset_password")){
                            	JOptionPane.showMessageDialog(applicationFrame,"Please Check your email");
                        	}
                            if(dataSplit[0].equals("Register_Success")) {
                            	System.out.print("Register_Success");
                            	JOptionPane.showMessageDialog(applicationFrame, "You are successfully registered, you will be redirected to the login page shortly");
                            	ChangeTab(new login(app),605, 476);
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
    
    public static void ChangeTab(JPanel newPanel,int h,int w) {
    	applicationFrame.getContentPane().removeAll();
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
    }

    public static void main(String[] args) {
        
        app = new Application();
        app.setUpSocket();
    }
}