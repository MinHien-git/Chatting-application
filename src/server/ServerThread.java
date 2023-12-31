package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import client.Application;
import client.User;

public class ServerThread implements Runnable {
    static final String URL = "jdbc:postgresql://localhost:5432/chatting-application";
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String USER = "postgres";
    static final String PW = "123456";

    private Socket socketOfServer;
    private String userID;//SocketID
    private String actual_userID;
    private BufferedReader is;
    private BufferedWriter os;
    private boolean isClosed;

    private static final String ACCOUNT_HISTORY_SQL = "UPDATE public.\"users\" SET history = ? WHERE id = ?";

    private static final String GET_ADMIN_INGROUP_SQL = "select p.id,p.name from public.\"groups\" u join public.\"users\" "
            + "p on p.id = any(u.admin) where u.id = '?' group by p.id,p.name";

    private static final String GET_USER_SQL = "select * from public.\"users\" ";
    private static final String INSERT_USER_SQL = "INSERT INTO public.\"users\" (id,name,email,password,friends,isAdmin,lock,history)"
            + "values (?,?,?,?,{},?,false,'')";
    private static final String DELETE_USER_SQL = "DELETE FROM public.\"users\" WHERE id = ?";

    private static final String GET_ALL_GROUPCHAT_SQL = "select * from public.\"groups\" ";
    private static final String GET_SPAMLIST_SQL = "select * from public.\"spams\" ";
    private static final String GET_ONLINE_FRIEND_SQL = "select p.id,p.name from public.\"users\" u join public.\"users\" "
            + "p on p.id = any(u.friends) where u.id = ? and p.isOnline = true group by p.id,u.name,u.id";
    private static final String REPORT_SPAM_SQL = "insert into public.\"spams\" (id,time) values (?,?)";


    private static final String UPDATE_GROUP_NAME_SQL = "UPDATE public.groups"
            + "SET groupname=?"
            + "WHERE groupid = ? and ? = any(admin)";
    private static final String ADD_ADMIN_SQL = "UPDATE public.groups"
            + "SET admin =  array_append(admin,?)"
            + "WHERE groupid = ? and ? = any(admin)"; // dấu hỏi đầu là tên user muốn add 2 là id group 3 là người add có phải admin hay không
    private static final String REMOVE_ADMIN_SQL = "UPDATE public.groups"
            + "SET admin =  array_remove(admin,?)"
            + "WHERE groupid = ? and ? = any(admin)"; // dấu hỏi đầu là tên user muốn add 2 là id group 3 là người add có phải admin hay không

    public BufferedReader getIs() {
        return is;
    }

    public void setuserID(String id) {
        userID = id;
    }

    public String getUserID() {
        return userID;
    }

    public BufferedWriter getOs() {
        return os;
    }

    public String getuserID() {
        return userID;
    }
    
    public String getActualUserID() {
        return actual_userID;
    }

    public ServerThread(Socket socketOfServer, String userID) {
        this.socketOfServer = socketOfServer;
        this.userID = userID;
        System.out.println("Server thread number " + userID + " started");
        isClosed = false;
    }

    @Override
    public void run() {
        try {
            // Mở luồng vào ra trên Socket tại Server.
            is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
            System.out.println("Khời động luông mới thành công, ID là: " + userID);
//            Server.serverThreadBus.sendOnlineList();
            //Server.serverThreadBus.mutilCastSend("global-message"+","+"---Client "+this.userID+" đã đăng nhập---");
            Server.serverThreadBus.boardCast(userID, "id" + "|" + userID);
            
            String message;
            while (!isClosed) {
                message = is.readLine();
                if (message == null) {
                    break;
                }
                String[] messageSplit = message.split("\\|");
                String commandString = messageSplit[0];
                System.out.println(commandString);
                if(commandString.equals("Login")) {
                	String result = Login(messageSplit[1],messageSplit[2]);
                	if(!result.equals("")) {
                		actual_userID = result.split("\\|")[0];
                		System.out.println("Dang nhap thanh cong"+messageSplit[messageSplit.length -1]);
                		Server.serverThreadBus.boardCast(messageSplit[messageSplit.length -1],"Login_Success|"+result);
                		String onlineList = GetOnlineFriends(actual_userID);
                		Server.serverThreadBus.boardCast(messageSplit[messageSplit.length -1], "OnlineList"+onlineList);
                	}
                	System.out.println(result);
                }else if(commandString.equals("Register")) {
                	if(Register(messageSplit[1],messageSplit[2],messageSplit[3],messageSplit[4])) {
                		System.out.println("Dang ki thanh cong"+messageSplit[messageSplit.length -1]);
                		Server.serverThreadBus.boardCast(messageSplit[messageSplit.length -1], "Register_Success|");
                	}
                }else if(commandString.equals("ResetPassword")) {
                	if(ForgotPassword(messageSplit[1])) {
                		Server.serverThreadBus.boardCast(messageSplit[messageSplit.length -1], "Reset_password|");
                	}
                }
                else if (commandString.equals("MessageData")) {
                    String id1 = messageSplit[2];//Người gửi
                    String id2 = messageSplit[3];//Từ user
                    String[] mess = GetMessage(id1+"|"+ id2);
                    if(!mess[0].equals("")) {
                    	
                    	Server.serverThreadBus.boardCast(messageSplit[messageSplit.length -1], "MessageData|"+mess[0]+"||"+mess[1]);
                    }
                }
                else if (commandString.equals("DirectMessage")) {
                    System.out.println("DirectMessage");
                    String id1 = messageSplit[1];//Người gửi
                    String id2 = messageSplit[2];//Từ user
                    String content = messageSplit[3];
                    String[] mess = CheckMessageExists(id1, id2);
                    if (!mess[0].equals("")) {
                        System.out.println("Update");
                        ServerThread.UpdateExistsMessage(id1, id2, content);
                    } else {
                        System.out.println("Insert");
                        ServerThread.InsertMessage(id1, id2, content);
                    }
                  
                    Server.serverThreadBus.boardCast(id1, "send-to-user|" + String.join(", ", mess[1]));
                    Server.serverThreadBus.boardCast(id2, "send-to-user|" + String.join(", ", mess[1]));
                } else if (commandString.equals("AddFriend")) {
                    String id1 = messageSplit[1];//From
                    String id2 = messageSplit[2];//To
                    AddFriend(id1, id2);
                    System.out.println("AddFriend");
                } else if (commandString.equals("DeleteFriend")) {
                    String id1 = messageSplit[1];//Người gửi
                    String id2 = messageSplit[2];//Từ user
                    RemoveFriend(id1, id2);
                    System.out.println("DeleteFriend");
                } else if (commandString.equals("DeleteMessage")) {
                    String id1 = messageSplit[1];//người muốn xoá
                    String id2 = messageSplit[2];// người xoá
                    RemoveMessage(id1 + "|" + id2);
                } else if (commandString.equals("CreateGroup")) {
                    System.out.println("CreateGroup");
                    String name = messageSplit[1];//Người gửi
                    String id = messageSplit[2];//Từ user
                    InsertGroup(name, id);
                } else if (commandString.equals("GetOnline")) {
                    System.out.println("GetOnline");
                    ArrayList<String> result = GetFriendListOnline(messageSplit[1]);
                    Server.serverThreadBus.boardCast(messageSplit[1], "get-online-friend|" + String.join(", ", result));
                } else if (commandString.equals("Online")) {
                    System.out.println("Online");
                    String id = messageSplit[1];//Từ user
                    SetOnline(id);
                } else if (commandString.equals("Offline")) {
                    System.out.println("Offline");
                    String id = messageSplit[1];//Từ user
                    SetOffline(id);
                } else if (commandString.equals("BlockAccount")) {
                    System.out.println("BlockAccount");
                    String id1 = messageSplit[1];//người muốn chặn
                    String id2 = messageSplit[2];// người chặn
                    BlockAccount(id1, id2);
                } else if (commandString.equals("ChangeGroupName")) {
                    System.out.println("ChangeGroupName");
                    String groupid = messageSplit[1];
                    String id = messageSplit[2];
                    String newName = messageSplit[3];
                    ChangeGroupName(groupid, id, newName);
                } else if (commandString.equals("AddMemberToGroup")) {
                    String groupid = messageSplit[1];
                    String id = messageSplit[2];
                    System.out.println("AddMemberToGroup");
                    AddMemberToGroup(groupid, id);
                } else if (commandString.equals("RemoveMemberGroup")) {
                    System.out.println("RemoveMemberGroup");
                    String groupid = messageSplit[1];
                    String id = messageSplit[2];
                    RemoveMemberGroup(groupid, id);
                } else if (commandString.equals("GroupChat")) {
                    System.out.println("GroupChat");
                    String groupid = messageSplit[1];
                    String id = messageSplit[2];
                    String content = messageSplit[3];
                    UpdateGroupChatMessage(groupid, content);
                } else if (commandString.equals("ReportSpam")) {
                    System.out.println("ReportSpam");
                    String username = messageSplit[1];
                    String byUser = messageSplit[2];
                    ReportSpam(username, byUser);
                    //------------------------------------------------------------------------------------------------------------------------------
                } else if (commandString.equals("AdminGetListUser")) {
                    AdminGetListUser(messageSplit);
                } else if (commandString.equals("AdminAddNewAccount")) {
                    AdminAddNewAccount(messageSplit);
                } else if (commandString.equals("AdminUpdateAccount")) {
                    AdminUpdateAccount(messageSplit);
                } else if (commandString.equals("AdminDeleteAccount")) {
                    AdminDeleteAccount(messageSplit);
                } else if (commandString.equals("AdminLockAccount")) {
                    AdminLockAccount(messageSplit);
                } else if (commandString.equals("AdminUnlockAccount")) {
                    AdminUnlockAccount(messageSplit);
                } else if (commandString.equals("AdminRenewPassword")) {
                    AdminRenewPassword(messageSplit);
                } else if (commandString.equals("AdminGetListLoginHistory")) {
                    AdminGetListLoginHistory(messageSplit);
                } else if (commandString.equals("AdminGetListFriend")) {
                    AdminGetListFriend(messageSplit);
                } else if (commandString.equals("AdminGetListLogin")) {
                    AdminGetListLogin();
                } else if (commandString.equals("AdminGetListGroup")) {
                    AdminGetListGroup(messageSplit);
                } else if (commandString.equals("AdminGetListMemGroup")) {
                    AdminGetListMemGroup(messageSplit);
                } else if (commandString.equals("AdminGetListAdmin")) {
                    AdminGetListAdmin(messageSplit);
                } else if (commandString.equals("AdminGetListSpam")) {
                    AdminGetListSpam(messageSplit);
                } else if (commandString.equals("AdminGetListNew")) {
                    AdminGetListNew(messageSplit);
                } else if (commandString.equals("AdminGetChartNew")) {
                    AdminGetChartNew(messageSplit);
                } else if (commandString.equals("AdminGetListFriendPlus")) {
                    AdminGetListFriendPlus(messageSplit);
                } else if (commandString.equals("AdminGetListOpen")) {
                    AdminGetListOpen(messageSplit);
                }
                //------------------------------------------------------------------------------------------------------------------------------
                else if (commandString.equals("AdminGetLoginActivities")) {
                    System.out.println("AdminGetLoginActivities");
                    AdminGetLoginActivities();
                } else if (commandString.equals("AdminGetGroup")) {
                    System.out.println("AdminGetGroup");
//                } else if (commandString.equals("AdminDeleteAccount")) {
//                    String username = messageSplit[1];
//                    AdminDeleteAccount(username);
//                    System.out.println("AdminDeleteAccount");
                } else if (commandString.equals("AdminAddAccount")) {
                    System.out.println("AdminAddAccount");
                    //complete
                } else if (commandString.equals("AdminEditAccount")) {
                    System.out.println("AdminEditAccount");
                    //complete
                } else if (commandString.equals("AdminGetGroupAdmin")) {
                    System.out.println("AdminGetGroupAdmin");

                } else if (commandString.equals("AdminGetGroupMember")) {
                    System.out.println("AdminGetGroupMember");

                } else if (commandString.equals("AdminSpamlist")) {
                    System.out.println("AdminSpamlist");
                    //complete
                } else if (commandString.equals("AdminRegisterStatistic")) {
                    System.out.println("AdminRegisterStatistic");
                    //complete
//                } else if (commandString.equals("AdminLockAccount")) {
//                    System.out.println("AdminLockAccount");
//                    //complete
//                } else if (commandString.equals("AdminUnLockAccount")) {
//                    System.out.println("AdminUnLockAccount");
//                    //complete
                } else if (commandString.equals("AdminGetUserFriends")) {
                    System.out.println("AdminGetUserFriends");
                } else if (commandString.equals("AdminGetRegisterAmountByYear")) {
                    System.out.println("AdminGetRegisterAmountByYear");
                } else if (commandString.equals("AdminGetActiveAmountByYear")) {
                    System.out.println("AdminGetActiveAmountByYear");
                }
            }
        } catch (IOException e) {
            isClosed = true;
            if(!actual_userID.equals("")) {
            	SetOffline(actual_userID);
            }
            Server.serverThreadBus.remove(userID);
            System.out.println(userID + " exited " + actual_userID);
        }
        finally {

        }
    }

    public void write(String message) throws IOException {
        os.write(message);
        os.newLine();
        os.flush();
    }
    //Register
    public static boolean Register(String id,String name,String email,String password) {
    	String INSERT_USERS_SQL = "INSERT INTO public.\"users\" (id,name, email,password) values (?,?,?,?)";
    	String USER_EXIST = "SELECT * FROM public.\"users\" where email = ? ";
    	try (Connection connection = DriverManager.getConnection(URL, USER, PW);
   			 PreparedStatement stmt = connection.prepareStatement(USER_EXIST);
   			 // Step 2:Create a statement using connection object
   			 PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
   			preparedStatement.setString(1, id);
   			preparedStatement.setString(2, name);
   			preparedStatement.setString(3, email);
   			preparedStatement.setString(4, password);

   			stmt.setString(1, email);
   			ResultSet rs = stmt.executeQuery();
   			if (rs.next()) {
   				System.out.print(rs.getString("id"));
   				return false;
   			}

   			// Step 3: Execute the query or update query
   			int count = preparedStatement.executeUpdate();
   			System.out.println(count);
   			return count > 0;
   		} catch (SQLException e) {
   			System.out.println("Unable to connect to database");
   			e.printStackTrace();
   			System.exit(1);
   			// print SQL exception information
   			return false;
   		}
	}
   //Login 
    public static String Login(String email,String password) {
    	String FIND_USERS_SQL = "SELECT * FROM public.\"users\" where email = ? and password = ?";
    	try (Connection connection = DriverManager.getConnection(URL, USER, PW);
   			 // Step 2:Create a statement using connection object
   			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_USERS_SQL)) {
   			preparedStatement.setString(1, email);
   			preparedStatement.setString(2, password);

   			// Step 3: Execute the query or update query
   			ResultSet rs = preparedStatement.executeQuery();
   			if (rs.next()) {
   				return rs.getString("id") + "|" +  rs.getString("name") + "|" +  rs.getString("email")+ "|" +  rs.getString("password");   				
   			}
   			return "";
   		} catch (SQLException e) {
   			System.out.println("Unable to connect to database");
   			e.printStackTrace();
   			System.exit(1);
   			// print SQL exception information
   			return "";
   		}
    }
    //Forgot password
    public static boolean UpdatePassword(String email,String password) {
    	String UPDATE_USERS_SQL = "UPDATE public.\"users\" set \"password\" = ? where email=?";
    	try (Connection connection = DriverManager.getConnection(URL, USER, PW);
   			 // Step 2:Create a statement using connection object
   			 PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL)) {
   			preparedStatement.setString(1, password);
   			preparedStatement.setString(2, email);
   			
   			int count = preparedStatement.executeUpdate();
   			System.out.println(count);
   			return count > 0;
   		} catch (SQLException e) {
   			System.out.println("Unable to connect to database");
   			e.printStackTrace();
   			System.exit(1);
   			// print SQL exception information
   			return false;
   		}
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
    
    public static String hashPassword(String pw)
	{
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashedBytes = digest.digest(pw.getBytes());

			// Convert byte array to a hexadecimal string
			StringBuilder hexString = new StringBuilder();
			for (byte hashedByte : hashedBytes) {
				String hex = Integer.toHexString(0xff & hashedByte);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}

			String hashedPW = hexString.toString();
			return hashedPW;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Hashing algorithm not found");
			e.printStackTrace();
			return null;
		}
	}
    
    public static boolean ForgotPassword(String email) {
    	final String email_password = "LMinhHien16102003";
		String from = "lmhien21@clc.fitus.edu.vn";
		String host = "smtp.gmail.com";//or IP address  
  
		Properties props = new Properties();
		 props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.ssl.trust", "*");
	      props.put("mail.smtp.ssl.protocols", "TLSv1.2");
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.port", "587");

		     
	   Session session = Session.getDefaultInstance(props,  
			   new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(from, email_password);
            }
         });
	  
	   //Compose the message  
	    try {  
	     MimeMessage message = new MimeMessage(session);  
	     message.setFrom(new InternetAddress(from));  
	     message.addRecipient(Message.RecipientType.TO,new InternetAddress(email));  
	     message.setSubject("Reset Password");  
	     String password = generateRandomPassword(15);
	     message.setText("Your updated password is :" + password);  
	     UpdatePassword(email,hashPassword(password));
	    //send the message  
	     Transport.send(message);  
	  
	     System.out.println("message sent successfully...");  
	     return true;
	     } catch (MessagingException err) {err.printStackTrace();}  
	    return false;
    }
    
    public static String GetOnlineFriends(String id) {
    	String FIND_ONLINE_FRIENDS = "SELECT u2.id, u2.name, u2.lock, u2.\"isOnline\" ,u2.blocks FROM public.\"users\" u JOIN public.\"users\" u2 "
    			+ "ON u2.id = ANY (u.friends) where u.id = ? and not(u2.id = ANY(u.blocks))  ";
    	try (Connection connection = DriverManager.getConnection(URL, USER, PW)) {
			PreparedStatement online = connection.prepareStatement(FIND_ONLINE_FRIENDS);
			online.setString(1,id);
			ResultSet friendList = online.executeQuery();
			String friendString = new String("");
			while (friendList.next())
			{
				String _id = friendList.getString("id");
				boolean isLocked = friendList.getBoolean("lock");
				boolean isOnline = friendList.getBoolean("isOnline");
//				java.sql.Array bl;
//				ArrayList<String> blocks;
//				if(friendList.getArray("blocks") != null) {
//					bl = friendList.getArray("blocks");
//					blocks = new ArrayList<>(Arrays.asList((String []) bl.getArray()));
//				}
				//if (isLocked || blocks.contains(id)) continue;
				String _name = friendList.getString("name");

				friendString += "||"+"user"+"|"+_id +"|"+ _name + "|"+isOnline;
			}
			return friendString;

		} catch (SQLException sqlException) {
			System.out.println("Unable to connect to database");
			sqlException.printStackTrace();
			return "";
		}
    }
  //Get Message Data
    public static String[] GetMessage(String id) {
        String FIND_MESSAGE_SQL = "SELECT idChat,content FROM public.\"messages\" where idchat = ?";
        
        String[] result = new String[2];
        try (Connection connection = DriverManager.getConnection(URL, USER, PW);

             PreparedStatement preparedStatement = connection.prepareStatement(FIND_MESSAGE_SQL)) {
            preparedStatement.setString(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
            	Array  arr =  rs.getArray("content");;
                result[0] = rs.getString("idChat");
                
                result[1] = "";
                String[] m = (String[]) arr.getArray();
                for(int i = 0;i<m.length;++i) {
                	if(i == m.length-1)
                		result[1] += m[i];
                	else
                		result[1] += m[i] + "|";
                }
                System.out.println(result[1]);
                
                
            } else {
                result[0] = "";
                result[1] ="";
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return result;
        }
    }
    
    public static String[] CheckMessageExists(String id, String id2) {
        String FIND_MESSAGE_SQL = "SELECT idChat,content FROM public.\"messages\" where idchat = ? or idchat = ?";
        String idChat1 = id + "|" + id2;
        String idChat2 = id2 + "|" + id;
        String[] result = new String[2];
        try (Connection connection = DriverManager.getConnection(URL, USER, PW);

             PreparedStatement preparedStatement = connection.prepareStatement(FIND_MESSAGE_SQL)) {
            preparedStatement.setString(1, idChat1);
            preparedStatement.setString(2, idChat2);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                result[0] = rs.getString("idChat");
                result[1] = rs.getArray("content").toString();
            } else {
                result[0] = "";
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return result;
        }
    }
  //DirectMessage
    public static boolean UpdateExistsMessage(String id, String id2, String content) {
        String idChat1 = id + "|" + id2;
        String idChat2 = id2 + "|" + id;
        String UPDATE_MESSAGE_SQL = "Update public.\"messages\" SET content =array_append(content,?) WHERE idchat = ? or idchat = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MESSAGE_SQL)) {
            preparedStatement.setString(1, content);
            preparedStatement.setString(2, idChat1);
            preparedStatement.setString(3, idChat2);

            int count = preparedStatement.executeUpdate();

            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return false;
        }
    }

    public static boolean InsertMessage(String id, String id2, String content) {
        String idChat1 = id + "|" + id2;
        String idChat2 = id2 + "|" + id;
        String INSERT_MESSAGE_SQL = "INSERT INTO public.\"messages\" (idChat,users,content) values (?,?,?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_MESSAGE_SQL);
             PreparedStatement preparedStatement1 = connection.prepareStatement(INSERT_MESSAGE_SQL)) {
            String[] contents = new String[1];
            contents[0] = content;
            Array array = connection.createArrayOf("TEXT", contents);

            String[] users = new String[2];
            users[0] = id;
            users[1] = id2;
            Array u = connection.createArrayOf("TEXT", users);
            preparedStatement.setString(1, idChat1);
            preparedStatement.setArray(2, u);
            preparedStatement.setArray(3, array);

            preparedStatement1.setString(1, idChat2);
            preparedStatement1.setArray(2, u);
            preparedStatement1.setArray(3, array);

            int count = preparedStatement.executeUpdate();
            int count2 = preparedStatement1.executeUpdate();
            return count > 0 && count2 > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return false;
        }
    }
   
    //AddFriend
    public static boolean AddFriend(String userId, String FriendId) {
        String ADD_FRIEND_SQL = "UPDATE public.\"users\" SET friends = array_append(friends,?)"
                + "WHERE id =? and exists (select * from public.\"users\" where id = ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_FRIEND_SQL);
             PreparedStatement preparedStatement1 = connection.prepareStatement(ADD_FRIEND_SQL)) {
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, FriendId);
            preparedStatement.setString(3, userId);
            preparedStatement1.setString(1, FriendId);
            preparedStatement1.setString(2, userId);
            preparedStatement1.setString(3, userId);

            int count = preparedStatement.executeUpdate();
            int count1 = preparedStatement1.executeUpdate();
            return count > 0 && count1 > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return false;
        }
    }

    //DeleteFriend
    public static boolean RemoveFriend(String userId, String FriendId) {
        String REMOVE_FRIEND_LIST_SQL = "UPDATE public.\"users\" SET friends = array_remove(friends, ?) WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_FRIEND_LIST_SQL);
             PreparedStatement preparedStatement1 = connection.prepareStatement(REMOVE_FRIEND_LIST_SQL)) {
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, FriendId);
            preparedStatement1.setString(1, FriendId);
            preparedStatement1.setString(2, userId);

            int count = preparedStatement.executeUpdate();
            int count1 = preparedStatement1.executeUpdate();
            return count > 0 && count1 > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return false;
        }
    }

    //RemoveMesssage
    public static boolean RemoveMessage(String id) {
        String REMOVE_MESSAGE_SQL = "Update public.\"messages\" SET content = '{}' WHERE idchat = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_MESSAGE_SQL)) {
            preparedStatement.setString(1, id);

            int count = preparedStatement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return false;
        }
    }

    //CREATE GROUP
    public static boolean InsertGroup(String group_name, String creator) {
        String CREATE_GROUP_SQL = "INSERT INTO public.groups "
                + "	(groupid, admin, groupname, users, content)\r\n"
                + "	VALUES (?, ?, ?, ?, '{}');";
        long id = (long) (Math.random() * (10000000000l - 1)) + 1;
        try (Connection connection = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_GROUP_SQL)) {
            preparedStatement.setString(1, id + "");
            String[] user = new String[1];
            user[0] = creator;
            preparedStatement.setArray(2, connection.createArrayOf("TEXT", user));
            preparedStatement.setString(3, group_name);
            preparedStatement.setArray(4, connection.createArrayOf("TEXT", user));

            int count = preparedStatement.executeUpdate();

            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return true;
        }
    }

    //Get FRIEND Online
    public static ArrayList<String> GetFriendListOnline(String id) {
        String GET_FRIEND_LIST_SQL = "select p.id,p.name from public.\"users\" u join public.\"users\" "
                + "p on p.id = any(u.friends) where u.id = ? group by p.id,u.name,u.id";

        try (Connection connection = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement preparedStatement = connection.prepareStatement(GET_FRIEND_LIST_SQL)) {
            preparedStatement.setString(1, id);
            ArrayList<String> result = new ArrayList<String>();

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String tempString = rs.getString("id") + "&" + rs.getString("name");
                result.add(tempString);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    //SET ONLINE
    public static boolean SetOnline(String id) {
        String SET_ONLINE_SQL = "UPDATE public.\"users\" SET \"isOnline\" = true where id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement preparedStatement = connection.prepareStatement(SET_ONLINE_SQL)) {
            preparedStatement.setString(1, id);

            int count = preparedStatement.executeUpdate();

            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return false;
        }
    }

    //SET OFFLINE
    public static boolean SetOffline(String id) {
    	System.out.println("Offline");
        String SET_ONLINE_SQL = "UPDATE public.\"users\" SET \"isOnline\" = false where id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement preparedStatement = connection.prepareStatement(SET_ONLINE_SQL)) {
            preparedStatement.setString(1, id);

            int count = preparedStatement.executeUpdate();

            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return false;
        }
    }

    //BLOCK ACCOUNT 
    public static boolean BlockAccount(String id, String id2) {
        String BLOCK_ACCOUNT_SQL = "UPDATE public.\"users\" SET blocks = array_append(blocks,?)"
                + "WHERE id =? and exists (select * from public.\"users\" where id = ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement preparedStatement = connection.prepareStatement(BLOCK_ACCOUNT_SQL)) {
            preparedStatement.setString(1, id2);
            preparedStatement.setString(2, id);
            preparedStatement.setString(3, id2);
            int count = preparedStatement.executeUpdate();

            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return false;
        }
    }

    //Change Group Name
    public static boolean ChangeGroupName(String groupID, String adminId, String newName) {
        String UPDATE_GROUP_NAME_SQL = "UPDATE public.groups"
                + " SET groupname = ?"
                + " WHERE groupid = ? and ? = any(admin) ";

        try (Connection connection = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_GROUP_NAME_SQL)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, groupID);
            preparedStatement.setString(3, adminId);
            System.out.print(preparedStatement);
            int count = preparedStatement.executeUpdate();

            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return true;
        }
    }

    //Add Member To Group
    public static boolean AddMemberToGroup(String groupID, String memeberid) {
        String ADD_MEMBER_SQL = "UPDATE public.\"groups\" SET users = array_append(users,?)"
                + "WHERE groupid =?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_MEMBER_SQL)) {
            preparedStatement.setString(1, memeberid);
            preparedStatement.setString(2, groupID);

            int count = preparedStatement.executeUpdate();

            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return true;
        }
    }

    //Remove Member out of Group
    public static boolean RemoveMemberGroup(String groupID, String memeberid) {
        String ADD_MEMBER_SQL = "UPDATE public.\"groups\" SET users = array_remove(users,?)"
                + "WHERE groupid =?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_MEMBER_SQL)) {
            preparedStatement.setString(1, memeberid);
            preparedStatement.setString(2, groupID);

            int count = preparedStatement.executeUpdate();

            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return true;
        }
    }

    //Send message to group chat
    public static boolean UpdateGroupChatMessage(String id, String content) {
        String UPDATE_MESSAGE_SQL = "Update public.\"groups\" SET content =array_append(content,?) WHERE groupid = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MESSAGE_SQL)) {
            preparedStatement.setString(1, content);
            preparedStatement.setString(2, id);

            int count = preparedStatement.executeUpdate();

            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return false;
        }
    }

    //Report Spam
    public static boolean ReportSpam(String username, String byUser) {
        String UPDATE_MESSAGE_SQL = "Insert into public.\"spams\" Values (?,current_timestamp,?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MESSAGE_SQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, byUser);
            int count = preparedStatement.executeUpdate();

            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return false;
        }
    }

    //------------------------------------------------------------------------------------------------------------
    public static void AdminGetListUser(String[] messageSplit) {
        try {
            Class.forName(JDBC_DRIVER);
            String ADMIN_GET_LIST_USER_SQL;
            System.out.println(Arrays.toString(messageSplit));

            if (messageSplit[4].isEmpty()) {
                if (Objects.equals(messageSplit[5], "Both")) {
                    ADMIN_GET_LIST_USER_SQL = "SELECT * FROM public.\"test_users\"";
                } else {
                    ADMIN_GET_LIST_USER_SQL = "SELECT * FROM public.\"test_users\" WHERE \"isOnline\" = ?";
                }

                if (messageSplit[2].equals("1") && messageSplit[3].equals("1")) {
                    ADMIN_GET_LIST_USER_SQL += " ORDER BY username DESC, \"createAt\" DESC";
                } else if (messageSplit[2].equals("1")) {
                    ADMIN_GET_LIST_USER_SQL += " ORDER BY username DESC";
                } else if (messageSplit[3].equals("1")) {
                    ADMIN_GET_LIST_USER_SQL += " ORDER BY \"createAt\" DESC";
                }
            } else {
                if (messageSplit[1].equals("1")) {
                    if (Objects.equals(messageSplit[5], "Both")) {
                        ADMIN_GET_LIST_USER_SQL = "SELECT * FROM public.\"test_users\" WHERE username LIKE ?";
                    } else {
                        ADMIN_GET_LIST_USER_SQL = "SELECT * FROM public.\"test_users\" WHERE username LIKE ? AND \"isOnline\" = ?";
                    }

                    if (messageSplit[2].equals("1") && messageSplit[3].equals("1")) {
                        ADMIN_GET_LIST_USER_SQL += " ORDER BY username DESC, \"createAt\" DESC";
                    } else if (messageSplit[2].equals("1")) {
                        ADMIN_GET_LIST_USER_SQL += " ORDER BY username DESC";
                    } else if (messageSplit[3].equals("1")) {
                        ADMIN_GET_LIST_USER_SQL += " ORDER BY \"createAt\" DESC";
                    }
                } else {
                    if (Objects.equals(messageSplit[5], "Both")) {
                        ADMIN_GET_LIST_USER_SQL = "SELECT * FROM public.\"test_users\" WHERE fullname LIKE ?";
                    } else {
                        ADMIN_GET_LIST_USER_SQL = "SELECT * FROM public.\"test_users\" WHERE fullname LIKE ? AND \"isOnline\" = ?";
                    }

                    if (messageSplit[2].equals("1") && messageSplit[3].equals("1")) {
                        System.out.println("IN");
                        ADMIN_GET_LIST_USER_SQL += " ORDER BY fullname DESC, \"createAt\" DESC";
                    } else if (messageSplit[2].equals("1")) {
                        ADMIN_GET_LIST_USER_SQL += " ORDER BY fullname DESC";
                    } else if (messageSplit[3].equals("1")) {
                        ADMIN_GET_LIST_USER_SQL += " ORDER BY \"createAt\" DESC";
                    }
                }
            }

            try (Connection connection = DriverManager.getConnection(URL, USER, PW);
                 PreparedStatement preparedStatement = connection.prepareStatement(ADMIN_GET_LIST_USER_SQL)) {

                if (messageSplit[4].isEmpty()) {
                    if (!Objects.equals(messageSplit[5], "Both")) {
                        preparedStatement.setString(1, messageSplit[5]);
                    }
                } else {
                    if (messageSplit[1].equals("1")) {
                        if (Objects.equals(messageSplit[5], "Both")) {
                            preparedStatement.setString(1, "%" + messageSplit[4] + "%");
                        } else {
                            preparedStatement.setString(1, "%" + messageSplit[4] + "%");
                            preparedStatement.setString(2, messageSplit[5]);
                        }
                    } else {
                        if (Objects.equals(messageSplit[5], "Both")) {
                            preparedStatement.setString(1, "%" + messageSplit[4] + "%");
                        } else {
                            preparedStatement.setString(1, "%" + messageSplit[4] + "%");
                            preparedStatement.setString(2, messageSplit[5]);
                        }
                    }
                }

                ResultSet rs = preparedStatement.executeQuery();

                if (!rs.next()) {
                    Server.serverThreadBus.boardCast("1", "AdminGetListUser|no data|END");
                } else {
                    do {
                        StringBuilder result = new StringBuilder();
                        result.append(rs.getString("username")).append(", ");
                        result.append(rs.getString("fullname")).append(", ");
                        result.append(rs.getString("address")).append(", ");
                        result.append(rs.getString("dob")).append(", ");
                        result.append(rs.getString("gender")).append(", ");
                        if (rs.isLast()) {
                            result.append(rs.getString("email")).append("|END");
                        } else {
                            result.append(rs.getString("email")).append(", ");
                        }

                        String fullReturn = "AdminGetListUser|" + result;
                        Server.serverThreadBus.boardCast("1", fullReturn);
                    } while (rs.next());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void AdminAddNewAccount(String[] messageSplit) {
        try {
            Class.forName(JDBC_DRIVER);
            String ADMIN_ADD_NEW_ACCOUNT_SQL;
            String ADMIN_CHECK_USERNAME;
            String ADMIN_CHECK_EMAIL;

            ADMIN_ADD_NEW_ACCOUNT_SQL = "INSERT INTO public.\"test_users\" (username, fullname, address, dob, gender, email, \"isOnline\", lock, \"createAt\", password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ADMIN_CHECK_USERNAME = "SELECT * FROM public.\"test_users\" WHERE username = ?";
            ADMIN_CHECK_EMAIL = "SELECT * FROM public.\"test_users\" WHERE email = ?";

            String dateString = messageSplit[4];
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            LocalDate curDate = LocalDate.now();
            try {
                java.util.Date parsedDate = dateFormat.parse(dateString);
                java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

                java.sql.Date sqlDateCreate = java.sql.Date.valueOf(curDate);

                try (Connection connection = DriverManager.getConnection(URL, USER, PW);
                     PreparedStatement preparedStatement = connection.prepareStatement(ADMIN_ADD_NEW_ACCOUNT_SQL);
                     PreparedStatement preparedStatement1 = connection.prepareStatement(ADMIN_CHECK_USERNAME);
                     PreparedStatement preparedStatement2 = connection.prepareStatement(ADMIN_CHECK_EMAIL)) {

                    preparedStatement1.setString(1, messageSplit[1]);
                    preparedStatement2.setString(1, messageSplit[6]);
                    ResultSet set1 = preparedStatement1.executeQuery();
                    ResultSet set2 = preparedStatement2.executeQuery();

                    if (!set1.next() && !set2.next()) {
                        preparedStatement.setString(1, messageSplit[1]);
                        preparedStatement.setString(2, messageSplit[2]);
                        preparedStatement.setString(3, messageSplit[3]);
                        preparedStatement.setDate(4, sqlDate);
                        preparedStatement.setString(5, messageSplit[5]);
                        preparedStatement.setString(6, messageSplit[6]);
                        preparedStatement.setString(7, "Offline");
                        preparedStatement.setBoolean(8, false);
                        preparedStatement.setDate(9, sqlDateCreate);
                        preparedStatement.setString(10, "1");

                        int rowsAffected = preparedStatement.executeUpdate();
                        System.out.println(rowsAffected);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void AdminUpdateAccount(String[] messageSplit) {
        try {
            Class.forName(JDBC_DRIVER);
            String ADMIN_UPDATE_ACCOUNT_SQL;
            String ADMIN_CHECK_USERNAME;

            ADMIN_UPDATE_ACCOUNT_SQL = "UPDATE public.\"test_users\" SET username = ?, fullname = ?, address = ? WHERE email = ?";
            ADMIN_CHECK_USERNAME = "SELECT * FROM public.\"test_users\" WHERE username = ?";

            try (Connection connection = DriverManager.getConnection(URL, USER, PW);
                 PreparedStatement preparedStatement = connection.prepareStatement(ADMIN_UPDATE_ACCOUNT_SQL);
                 PreparedStatement preparedStatement1 = connection.prepareStatement(ADMIN_CHECK_USERNAME)) {
                preparedStatement1.setString(1, messageSplit[1]);

                ResultSet set1 = preparedStatement1.executeQuery();
                if (!set1.next()) {
                    preparedStatement.setString(1, messageSplit[1]);
                    preparedStatement.setString(2, messageSplit[2]);
                    preparedStatement.setString(3, messageSplit[3]);
                    preparedStatement.setString(4, messageSplit[4]);

                    int rowsAffected = preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void AdminDeleteAccount(String[] messageSplit) {
        try {
            Class.forName(JDBC_DRIVER);
            String ADMIN_DELETE_ACCOUNT_SQL;

            ADMIN_DELETE_ACCOUNT_SQL = "DELETE FROM public.\"test_users\" WHERE username = ?";

            try (Connection connection = DriverManager.getConnection(URL, USER, PW);
                 PreparedStatement preparedStatement = connection.prepareStatement(ADMIN_DELETE_ACCOUNT_SQL)) {
                preparedStatement.setString(1, messageSplit[1]);

                int rowsAffected = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void AdminLockAccount(String[] messageSplit) {
        try {
            Class.forName(JDBC_DRIVER);
            String ADMIN_LOCK_ACCOUNT_SQL;

            ADMIN_LOCK_ACCOUNT_SQL = "UPDATE public.\"test_users\" SET lock = ? WHERE username = ?";

            try (Connection connection = DriverManager.getConnection(URL, USER, PW);
                 PreparedStatement preparedStatement = connection.prepareStatement(ADMIN_LOCK_ACCOUNT_SQL)) {
                preparedStatement.setBoolean(1, true);
                preparedStatement.setString(2, messageSplit[1]);

                int rowsAffected = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void AdminUnlockAccount(String[] messageSplit) {
        try {
            Class.forName(JDBC_DRIVER);
            String ADMIN_UNLOCK_ACCOUNT_SQL;

            ADMIN_UNLOCK_ACCOUNT_SQL = "UPDATE public.\"test_users\" SET lock = ? WHERE username = ?";

            try (Connection connection = DriverManager.getConnection(URL, USER, PW);
                 PreparedStatement preparedStatement = connection.prepareStatement(ADMIN_UNLOCK_ACCOUNT_SQL)) {
                preparedStatement.setBoolean(1, false);
                preparedStatement.setString(2, messageSplit[1]);

                int rowsAffected = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void AdminRenewPassword(String[] messageSplit) {
        try {
            Class.forName(JDBC_DRIVER);
            String ADMIN_RENEW_PASSWORD_SQL;

            ADMIN_RENEW_PASSWORD_SQL = "UPDATE public.\"test_users\" SET password = ? WHERE username = ?";

            try (Connection connection = DriverManager.getConnection(URL, USER, PW);
                 PreparedStatement preparedStatement = connection.prepareStatement(ADMIN_RENEW_PASSWORD_SQL)) {
                preparedStatement.setString(1, messageSplit[2]);
                preparedStatement.setString(2, messageSplit[1]);

                int rowsAffected = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void AdminGetListLoginHistory(String[] messageSplit) {
        try {
            Class.forName(JDBC_DRIVER);
            String ADMIN_GET_LIST_LOGIN_HISTORY_SQL;

            ADMIN_GET_LIST_LOGIN_HISTORY_SQL = "SELECT * FROM public.\"test_logs\" WHERE username = ?";

            try (Connection connection = DriverManager.getConnection(URL, USER, PW);
                 PreparedStatement preparedStatement = connection.prepareStatement(ADMIN_GET_LIST_LOGIN_HISTORY_SQL)) {

                preparedStatement.setString(1, messageSplit[1]);
                ResultSet rs = preparedStatement.executeQuery();

                if (!rs.next()) {
                    Server.serverThreadBus.boardCast("1", "AdminGetListUser|no data|END");
                } else {
                    do {
                        StringBuilder result = new StringBuilder();
                        result.append(rs.getString("username")).append(", ");
                        if (rs.isLast()) {
                            result.append(rs.getString("logdate")).append("|END");
                        } else {
                            result.append(rs.getString("logdate")).append(", ");
                        }

                        String fullReturn = "AdminGetListLoginHistory|" + result;
                        Server.serverThreadBus.boardCast("1", fullReturn);
                    } while (rs.next());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void AdminGetListFriend(String[] messageSplit) {
        try {
            Class.forName(JDBC_DRIVER);
            String ADMIN_GET_LIST_FRIEND_SQL;

            ADMIN_GET_LIST_FRIEND_SQL = "SELECT * FROM public.\"test_users\" as Fr WHERE Fr.username IN (SELECT unnest(array_agg(friends)) FROM public.\"test_users\" WHERE username = ? AND (SELECT COALESCE(ARRAY_LENGTH(friends, 1), 0) AS num_elements FROM public.\"test_users\" as Ch WHERE Ch.username = ?) > 0);";

            try (Connection connection = DriverManager.getConnection(URL, USER, PW);
                 PreparedStatement preparedStatement = connection.prepareStatement(ADMIN_GET_LIST_FRIEND_SQL)) {

                preparedStatement.setString(1, messageSplit[1]);
                preparedStatement.setString(2, messageSplit[1]);
                ResultSet rs = preparedStatement.executeQuery();

                if (!rs.next()) {
                    Server.serverThreadBus.boardCast("1", "AdminGetListFriend|no data|END");
                } else {
                    do {
                        StringBuilder result = new StringBuilder();
                        result.append(rs.getString("username")).append(", ");
                        if (rs.isLast()) {
                            result.append(rs.getString("isOnline")).append("|END");
                        } else {
                            result.append(rs.getString("isOnline")).append(", ");
                        }

                        String fullReturn = "AdminGetListFriend|" + result;
                        Server.serverThreadBus.boardCast("1", fullReturn);
                    } while (rs.next());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void AdminGetListLogin() {
        try {
            Class.forName(JDBC_DRIVER);
            String ADMIN_GET_LIST_LOGIN_SQL;

            ADMIN_GET_LIST_LOGIN_SQL = "SELECT u.username, u.fullname, l.logdate FROM public.\"test_users\" as u JOIN public.\"test_logs\" as l ON u.username = l.username;";

            try (Connection connection = DriverManager.getConnection(URL, USER, PW);
                 PreparedStatement preparedStatement = connection.prepareStatement(ADMIN_GET_LIST_LOGIN_SQL)) {
                ResultSet rs = preparedStatement.executeQuery();

                if (!rs.next()) {
                    Server.serverThreadBus.boardCast("1", "AdminGetListLogin|no data|END");
                } else {
                    do {
                        StringBuilder result = new StringBuilder();
                        result.append(rs.getString("logdate")).append(", ");
                        result.append(rs.getString("username")).append(", ");
                        if (rs.isLast()) {
                            result.append(rs.getString("fullname")).append("|END");
                        } else {
                            result.append(rs.getString("fullname")).append(", ");
                        }

                        String fullReturn = "AdminGetListLogin|" + result;
                        Server.serverThreadBus.boardCast("1", fullReturn);
                    } while (rs.next());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void AdminGetListGroup(String[] messageSplit) {
        try {
            Class.forName(JDBC_DRIVER);
            String ADMIN_GET_LIST_GROUP_SQL;

            if (messageSplit.length != 4) {
                ADMIN_GET_LIST_GROUP_SQL = "SELECT groupname, ARRAY_TO_STRING(admin, ' - ') AS ad, ARRAY_LENGTH(users, 1) AS mems, \"createAt\" FROM public.\"groups\"";
            } else {
                ADMIN_GET_LIST_GROUP_SQL = "SELECT groupname, ARRAY_TO_STRING(admin, ' - ') AS ad, ARRAY_LENGTH(users, 1) AS mems, \"createAt\" as createat FROM public.\"groups\" WHERE groupname LIKE ?";
            }

            if (messageSplit[1].equals("1") && messageSplit[2].equals("1")) {
                ADMIN_GET_LIST_GROUP_SQL += " ORDER BY groupname DESC, \"createAt\" DESC";
            } else if (messageSplit[1].equals("1")) {
                ADMIN_GET_LIST_GROUP_SQL += " ORDER BY groupname DESC";
            } else if (messageSplit[2].equals("1")) {
                ADMIN_GET_LIST_GROUP_SQL += " ORDER BY \"createAt\" DESC";
            }

            try (Connection connection = DriverManager.getConnection(URL, USER, PW);
                 PreparedStatement preparedStatement = connection.prepareStatement(ADMIN_GET_LIST_GROUP_SQL)) {
                if (messageSplit.length == 4) {
                    preparedStatement.setString(1, messageSplit[3] + "%");
                }
                ResultSet rs = preparedStatement.executeQuery();

                if (!rs.next()) {
                    Server.serverThreadBus.boardCast("1", "AdminGetListGroup|no data|END");
                } else {
                    do {
                        StringBuilder result = new StringBuilder();
                        result.append(rs.getString("groupname")).append(", ");
                        result.append(rs.getInt("mems")).append(", ");
                        result.append(rs.getString("ad")).append(", ");
                        if (rs.isLast()) {
                            result.append(rs.getString("createat")).append("|END");
                        } else {
                            result.append(rs.getString("createat")).append(", ");
                        }

                        String fullReturn = "AdminGetListGroup|" + result;
                        Server.serverThreadBus.boardCast("1", fullReturn);
                    } while (rs.next());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void AdminGetListMemGroup(String[] messageSplit) {
        try {
            Class.forName(JDBC_DRIVER);
            String ADMIN_GET_LIST_MEM_GROUP_SQL = "SELECT * FROM (SELECT UNNEST(users) AS username FROM public.\"groups\" WHERE groupname LIKE ? EXCEPT SELECT UNNEST(admin) FROM public.\"groups\" WHERE groupname LIKE ?) AS unique_users";
            String ADMIN_GET_LIST_MEM_GROUP_AD_SQL = "SELECT UNNEST(admin) AS username FROM public.\"groups\" WHERE groupname LIKE ?";

            try (Connection connection = DriverManager.getConnection(URL, USER, PW);
                 PreparedStatement preparedStatement = connection.prepareStatement(ADMIN_GET_LIST_MEM_GROUP_SQL);
                 PreparedStatement preparedStatementAd = connection.prepareStatement(ADMIN_GET_LIST_MEM_GROUP_AD_SQL);) {
                preparedStatement.setString(1, messageSplit[1] + "%");
                preparedStatement.setString(2, messageSplit[1] + "%");

                preparedStatementAd.setString(1, messageSplit[1] + "%");
                ResultSet rsAd = preparedStatementAd.executeQuery();
                ResultSet rs = preparedStatement.executeQuery();

                if (!rs.next()) {
                    Server.serverThreadBus.boardCast("1", "AdminGetListMemGroup|no data|END");
                } else {
                    do {
                        StringBuilder result = new StringBuilder();
                        result.append(rs.getString("username")).append(", ");
                        result.append("Thành viên");

                        String fullReturn = "AdminGetListMemGroup|" + result;
                        Server.serverThreadBus.boardCast("1", fullReturn);
                    } while (rs.next());
                }

                if (!rsAd.next()) {
                    Server.serverThreadBus.boardCast("1", "AdminGetListMemGroup|no data|END");
                } else {
                    do {
                        StringBuilder result = new StringBuilder();
                        result.append(rsAd.getString("username")).append(", ");
                        if (rsAd.isLast()) {
                            result.append("Quản trị viên").append("|END");
                        } else {
                            result.append("Quản trị viên");
                        }

                        String fullReturn = "AdminGetListMemGroup|" + result;
                        Server.serverThreadBus.boardCast("1", fullReturn);
                    } while (rsAd.next());
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void AdminGetListAdmin(String[] messageSplit) {
        try {
            System.out.println(Arrays.toString(messageSplit));
            Class.forName(JDBC_DRIVER);
            String ADMIN_GET_LIST_ADMIN_SQL = "SELECT groupname, ARRAY_TO_STRING(admin, ' - ') AS ad FROM public.\"groups\" WHERE groupname LIKE ?";
            try (Connection connection = DriverManager.getConnection(URL, USER, PW);
                 PreparedStatement preparedStatement = connection.prepareStatement(ADMIN_GET_LIST_ADMIN_SQL)) {
                if (messageSplit.length != 1) {
                    preparedStatement.setString(1, messageSplit[1] + "%");
                } else {
                    preparedStatement.setString(1, "%");
                }

                ResultSet rs = preparedStatement.executeQuery();

                if (!rs.next()) {
                    Server.serverThreadBus.boardCast("1", "AdminGetListAdmin|no data|END");
                } else {
                    do {
                        StringBuilder result = new StringBuilder();
                        result.append(rs.getString("ad")).append(", ");
                        if (rs.isLast()) {
                            result.append(rs.getString("groupname")).append("|END");
                        } else {
                            result.append(rs.getString("groupname"));
                        }
                        String fullReturn = "AdminGetListAdmin|" + result;
                        Server.serverThreadBus.boardCast("1", fullReturn);
                    } while (rs.next());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void AdminGetListSpam(String[] messageSplit) {
        try {
            Class.forName(JDBC_DRIVER);
            String ADMIN_GET_LIST_SPAM_SQL = "SELECT * FROM public.\"spams\"";

            if (messageSplit.length == 2) {} else {
                if (messageSplit[2].equals("1")) {
                    ADMIN_GET_LIST_SPAM_SQL += " WHERE username LIKE ?";
                } else if (messageSplit[2].equals("-1")) {
                    ADMIN_GET_LIST_SPAM_SQL += " WHERE EXTRACT(YEAR FROM date)::TEXT LIKE ?";
                }
            }

            if (messageSplit[1].equals("1")) {
                ADMIN_GET_LIST_SPAM_SQL += " ORDER BY username ASC";
            } else if (messageSplit[1].equals("-1")) {
                ADMIN_GET_LIST_SPAM_SQL += " ORDER BY date ASC";
            }

            try (Connection connection = DriverManager.getConnection(URL, USER, PW);
                 PreparedStatement preparedStatement = connection.prepareStatement(ADMIN_GET_LIST_SPAM_SQL)) {

                if (messageSplit.length != 2) {
                    preparedStatement.setString(1, messageSplit[3] + "%");
                }

                ResultSet rs = preparedStatement.executeQuery();

                if (!rs.next()) {
                    Server.serverThreadBus.boardCast("1", "AdminGetListSpam|no data|END");
                } else {
                    do {
                        StringBuilder result = new StringBuilder();
                        result.append(rs.getString("username")).append(", ");
                        result.append(rs.getString("date")).append(", ");
                        if (rs.isLast()) {
                            result.append(rs.getString("ByUser")).append("|END");
                        } else {
                            result.append(rs.getString("ByUser"));
                        }

                        String fullReturn = "AdminGetListSpam|" + result;
                        Server.serverThreadBus.boardCast("1", fullReturn);
                    } while (rs.next());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void AdminGetListNew(String[] messageSplit) {
        try {
            Class.forName(JDBC_DRIVER);
            String ADMIN_GET_LIST_NEW_SQL = "SELECT * FROM public.\"test_users\"";

            if (messageSplit.length == 4) {
                ADMIN_GET_LIST_NEW_SQL += " WHERE \"createAt\" BETWEEN ?::DATE AND ?::DATE";
            } else {
                ADMIN_GET_LIST_NEW_SQL += " WHERE \"createAt\" BETWEEN ?::DATE AND ?::DATE AND username LIKE ?";
            }
            if (messageSplit[1].equals("1")) {
                ADMIN_GET_LIST_NEW_SQL += " ORDER BY fullname ASC";
            } else if (messageSplit[3].equals("-1")) {
                ADMIN_GET_LIST_NEW_SQL += " ORDER BY date ASC";
            }

            try (Connection connection = DriverManager.getConnection(URL, USER, PW);
                 PreparedStatement preparedStatement = connection.prepareStatement(ADMIN_GET_LIST_NEW_SQL)) {

                preparedStatement.setString(1, messageSplit[1]);
                preparedStatement.setString(2, messageSplit[2]);
                if (messageSplit.length != 4) {
                    preparedStatement.setString(3, messageSplit[3]);
                }

                ResultSet rs = preparedStatement.executeQuery();

                if (!rs.next()) {
                    Server.serverThreadBus.boardCast("1", "AdminGetListNew|no data|END");
                } else {
                    do {
                        StringBuilder result = new StringBuilder();
                        result.append(rs.getString("username")).append(", ");
                        result.append(rs.getString("fullname")).append(", ");
                        result.append(rs.getString("address")).append(", ");
                        result.append(rs.getString("dob")).append(", ");
                        result.append(rs.getString("gender")).append(", ");
                        result.append(rs.getString("email")).append(", ");
                        if (rs.isLast()) {
                            result.append(rs.getString("createAt")).append("|END");
                        } else {
                            result.append(rs.getString("createAt"));
                        }

                        String fullReturn = "AdminGetListNew|" + result;
                        Server.serverThreadBus.boardCast("1", fullReturn);
                    } while (rs.next());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void AdminGetChartNew(String[] messageSplit) {
        try {
            Class.forName(JDBC_DRIVER);
            String ADMIN_GET_CHART_NEW_SQL = " WITH months AS (\n" +
                    "                SELECT generate_series(1, 12) AS month\n" +
                    "            )\n" +
                    "            SELECT months.month,\n" +
                    "                   COUNT(public.test_users.\"createAt\") AS row_count\n" +
                    "            FROM months\n" +
                    "            LEFT JOIN public.test_users ON EXTRACT(MONTH FROM public.test_users.\"createAt\") = months.month\n" +
                    "                               AND EXTRACT(YEAR FROM public.test_users.\"createAt\") = ?\n" +
                    "            GROUP BY months.month\n" +
                    "            ORDER BY months.month;";

            try (Connection connection = DriverManager.getConnection(URL, USER, PW);
                 PreparedStatement preparedStatement = connection.prepareStatement(ADMIN_GET_CHART_NEW_SQL)) {
                preparedStatement.setInt(1, Integer.parseInt(messageSplit[1]));

                ResultSet rs = preparedStatement.executeQuery();

                if (!rs.next()) {
                    Server.serverThreadBus.boardCast("1", "AdminGetChartNew|no data|END");
                } else {
                    StringBuilder result = new StringBuilder();
                    do {
                        result.append(rs.getInt("month")).append(", ");
                        if (rs.isLast()) {
                            result.append(rs.getBigDecimal("row_count"));
                        } else {
                            result.append(rs.getBigDecimal("row_count")).append(", ");
                        }
                    } while (rs.next());
                    String fullReturn = "AdminGetChartNew|" + result + "|" + messageSplit[1];
                    Server.serverThreadBus.boardCast("1", fullReturn);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void AdminGetListFriendPlus(String[] messageSplit) {
        try {
            Class.forName(JDBC_DRIVER);
            String ADMIN_GET_LIST_FRIEND_PLUS_SQL = "SELECT * FROM (SELECT tu1.username, array_length(tu1.friends, 1) AS dirfr, SUM(array_length(tu2.friends, 1)) AS total FROM test_users tu1 LEFT JOIN test_users tu2 ON tu2.username = ANY(tu1.friends) GROUP BY tu1.username, tu1.friends) AS fr JOIN test_users tu3 ON tu3.username = fr.username";

            if (messageSplit.length >= 3) {
                ADMIN_GET_LIST_FRIEND_PLUS_SQL += " WHERE fr.username LIKE ?";
            }
            if (messageSplit.length == 4) {
                ADMIN_GET_LIST_FRIEND_PLUS_SQL += " AND fr.dirfr " + messageSplit[3].charAt(0) + " " + Integer.parseInt(messageSplit[3].substring(1));
            }

            if (messageSplit[1].equals("1")) {
                ADMIN_GET_LIST_FRIEND_PLUS_SQL += " ORDER BY tu3.username";
            } else if (messageSplit[1].equals("-1")) {
                ADMIN_GET_LIST_FRIEND_PLUS_SQL += " ORDER BY tu3.\"createAt\"";
            }
            try (Connection connection = DriverManager.getConnection(URL, USER, PW);
                 PreparedStatement preparedStatement = connection.prepareStatement(ADMIN_GET_LIST_FRIEND_PLUS_SQL)) {
                if (messageSplit.length >= 3) {
                    preparedStatement.setString(1, messageSplit[2] + "%");
                }

                ResultSet rs = preparedStatement.executeQuery();

                if (!rs.next()) {
                    Server.serverThreadBus.boardCast("1", "AdminGetListFriendPlus|no data|END");
                } else {
                    do {
                        StringBuilder result = new StringBuilder();
                        result.append(rs.getString("username")).append(", ");
                        result.append(rs.getInt("dirfr")).append(", ");
                        if (rs.isLast()) {
                            result.append(rs.getInt("total") + rs.getInt("dirfr")).append("|END");
                        } else {
                            result.append(rs.getInt("total") + rs.getInt("dirfr"));
                        }

                        String fullReturn = "AdminGetListFriendPlus|" + result;
                        Server.serverThreadBus.boardCast("1", fullReturn);
                    } while (rs.next());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void AdminGetListOpen(String[] messageSplit) {
        try {
            Class.forName(JDBC_DRIVER);
            //WITH LogAppearance AS (
            //    SELECT
            //        username,
            //        COUNT(*) AS appearance_count
            //    FROM
            //        test_logs
            //    WHERE
            //        logdate BETWEEN '2020-10-01' AND '2023-10-01'
            //    GROUP BY
            //        username
            //)
            //
            //SELECT
            //    LogAppearance.username,
            //    LogAppearance.appearance_count,
            //    COUNT(*) FILTER (WHERE LogAppearance.username = ANY(public.groups.users)) AS numgroup,
            //    COUNT(*) FILTER (WHERE LogAppearance.username = ANY(messages.users)) AS numchat
            //FROM
            //    LogAppearance
            //LEFT JOIN
            //    public.groups ON LogAppearance.username = ANY(users)
            //LEFT JOIN
            //    public.messages AS messages ON LogAppearance.username = ANY(messages.users)
            //GROUP BY
            //    LogAppearance.username, LogAppearance.appearance_count;
            String ADMIN_GET_LIST_OPEN_SQL = "";

            try (Connection connection = DriverManager.getConnection(URL, USER, PW);
                 PreparedStatement preparedStatement = connection.prepareStatement(ADMIN_GET_LIST_OPEN_SQL)) {
                if (messageSplit.length >= 3) {
                    preparedStatement.setString(1, messageSplit[2] + "%");
                }

                ResultSet rs = preparedStatement.executeQuery();

                if (!rs.next()) {
                    Server.serverThreadBus.boardCast("1", "AdminGetListFriendPlus|no data|END");
                } else {
                    do {
                        StringBuilder result = new StringBuilder();
                        result.append(rs.getString("username")).append(", ");
                        result.append(rs.getInt("dirfr")).append(", ");
                        if (rs.isLast()) {
                            result.append(rs.getInt("total") + rs.getInt("dirfr")).append("|END");
                        } else {
                            result.append(rs.getInt("total") + rs.getInt("dirfr"));
                        }

                        String fullReturn = "AdminGetListFriendPlus|" + result;
                        Server.serverThreadBus.boardCast("1", fullReturn);
                    } while (rs.next());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //------------------------------------------------------------------------------------------------------------
    //Admin Get Login Activities
    public static ArrayList<String> AdminGetLoginActivities() {
        String GET_LOGIN_ACTIVITES_SQL = "SELECT * FROM public.\"logs\" JOIN public.\"users\" ON username = email";
        try (Connection connection = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement preparedStatement = connection.prepareStatement(GET_LOGIN_ACTIVITES_SQL)) {
            ArrayList<String> resultArrayList = new ArrayList<String>();
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String logString = rs.getString("id") + "|" + rs.getString("name") + "|" + rs.getDate("date").toString();
                resultArrayList.add(logString);
            }
            return resultArrayList;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    //Admin Get Active user
//    public static ArrayList<String> AdminGetActiveUser() {
//    	String GET_ACTIVEUSER_SQL = "SELECT distinct name, COUNT() FROM public.\"systems\" JOIN public.\"users\" "
//    			+ "ON email = username GROUP BY name,";
//		try (Connection connection =  DriverManager.getConnection(URL, USER, PW);
//				PreparedStatement preparedStatement = connection.prepareStatement(GET_ACTIVEUSER_SQL)) { 
//	        ArrayList<String> resultArrayList = new ArrayList<String>();    
//			ResultSet rs = preparedStatement.executeQuery();
//	        while(rs.next()) {    
//	            String logString = rs.getString("id") +"|"+ rs.getString("name") +"|" + rs.getDate("date").toString();
//	            resultArrayList.add(logString);
//	        }
//	        return resultArrayList;
//	        } catch (SQLException e) {
//	        	e.printStackTrace();
//                System.exit(1);
//	           return null;
//	        }
//    }
    public static boolean EditAccount(String name, String email, String password) {
        String UPDATE_ACCOUNT_SQL = "UPDATE public.\"users\" SET name=?, email=?, password=? WHERE email = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ACCOUNT_SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, email);

            int count = preparedStatement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return false;
        }
    }

    public static boolean LockAccount(String id, boolean lock) {
        String LOCK_SQL = "UPDATE public.\"users\" SET  lock = true WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PW);

             PreparedStatement preparedStatement = connection.prepareStatement(LOCK_SQL)) {
            preparedStatement.setBoolean(1, lock);
            preparedStatement.setString(2, id);

            int count = preparedStatement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return false;
        }
    }

    public static boolean UnLockAccount(String id, boolean lock) {
        String UNLOCK_SQL = "UPDATE public.\"users\" SET  lock = false WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PW);

             PreparedStatement preparedStatement = connection.prepareStatement(UNLOCK_SQL)) {
            preparedStatement.setBoolean(1, lock);
            preparedStatement.setString(2, id);

            int count = preparedStatement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return false;
        }
    }

    public static ArrayList<String> GetAdminInGroup(String groupID) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ADMIN_INGROUP_SQL)) {
            preparedStatement.setString(1, groupID);
            ArrayList<String> result = new ArrayList<String>();
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String tempString = rs.getString("id") + rs.getString("name");
                result.add(tempString);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    public static ArrayList<String> GetAdminLoginActivities() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ADMIN_INGROUP_SQL)) {
            ArrayList<String> result = new ArrayList<String>();
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String tempString = rs.getString("id") + rs.getString("name");
                result.add(tempString);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

//    public static boolean AdminDeleteAccount(String username) {
//        String DELETE_SQL = "delete public.\"users\" where email = ?";
//        try (Connection connection = DriverManager.getConnection(URL, USER, PW);
//             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
//            preparedStatement.setString(1, username);
//            ArrayList<String> result = new ArrayList<String>();
//            ResultSet rs = preparedStatement.executeQuery();
//            return rs.next();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.exit(1);
//            return false;
//        }
//    }

    public static ArrayList<String> AdminSpamlist() {
        String DELETE_SQL = "delete public.\"spams\" where email = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {

            ArrayList<String> result = new ArrayList<String>();
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String tempString = rs.getString("id") + "|" + rs.getString("name");
                result.add(tempString);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    public static ArrayList<String> AdminRegisterStatistic(int year) {
        String DELETE_SQL = "select * public.\"users\" where Year(created) = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {

            ArrayList<String> result = new ArrayList<String>();
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String tempString = rs.getString("id") + "|" + rs.getString("name") + "|" + rs.getDate("created").toString();
                result.add(tempString);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }
}

