package server;

import java.awt.Taskbar.State;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import client.User;

public class ServerThread implements Runnable {
	static final String URL = "jdbc:postgresql://localhost/chatting-application";
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String USER = "postgres";
    static final String PW = "123456";
    
    private Socket socketOfServer;
    private int clientNumber;
    private BufferedReader is;
    private BufferedWriter os;
    private boolean isClosed;
    private static final String FIND_MESSAGE_SQL = "SELECT idChat,content FROM public.\"messages\" where idchat = ? or idchat = ?";
    private static final String INSERT_MESSAGE_SQL = "INSERT INTO public.\"messages\" (idChat,content) values (?,?)";
    
    private static final String UPDATE_MESSAGE_SQL = "Update public.\"messages\" SET content = ? WHERE idchat = ? or idchat = ?";
    private static final String REMOVE_MESSAGE_SQL = "Update public.\"messages\" SET content = '' WHERE idchat = ?";
    private static final String GET_FRIEND_LIST_SQL = "select p.id,p.name from public.\"users\" u join public.\"users\" "
    		+ "p on p.id = any(u.friends) where u.id = ? group by p.id,u.name,u.id";
    private static final String REMOVE_FRIEND_LIST_SQL = "UPDATE public.\"users\" SET friends = array_remove(friends, ?) WHERE id = ?";
    private static final String UPDATE_PASSWORD_SQL = "UPDATE public.\"users\" SET password = '?' WHERE id = ?";
    private static final String LOCK_UNLOCK_SQL = "UPDATE public.\"users\" SET  lock = ? WHERE id = ?";
    private static final String ACCOUNT_HISTORY_SQL = "UPDATE public.\"users\" SET history = ? WHERE id = ?";
    private static final String ADD_FRIEND_SQL="UPDATE public.\"users\" SET friends = array_append(friends,?)"
    		+ 									"WHERE id =? and exists (select * from public.\"users\" where id = ?)";
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
    private static final String REPORT_SPAM_SQL = "insert into public.\"spams\" (id,time) values (?,?)" ;
    private static final String BLOCK_ACCOUNT_SQL = "UPDATE public.\"users\" SET friends = array_append(blocks,?)"
    		+ 									"WHERE id =? and exists (select * from public.\"users\" where id = ?)";
    private static final String CREATE_GROUP_SQL = "INSERT INTO public.groups "
									    		+ "	groupid, admin, groupname, users, content)\r\n"
									    		+ "	VALUES (?, {?}, ?, {?}, '');" ;
    private static final String UPDATE_GROUP_NAME_SQL = "UPDATE public.groups"
    		+ "SET groupname=?"
    		+"WHERE groupid = ? and ? = any(admin)";
    private static final String ADD_ADMIN_SQL = "UPDATE public.groups"
    		+ "SET admin =  array_append(admin,?)"
    		+"WHERE groupid = ? and ? = any(admin)"; // dấu hỏi đầu là tên user muốn add 2 là id group 3 là người add có phải admin hay không
    private static final String REMOVE_ADMIN_SQL = "UPDATE public.groups"
    		+ "SET admin =  array_remove(admin,?)"
    		+"WHERE groupid = ? and ? = any(admin)"; // dấu hỏi đầu là tên user muốn add 2 là id group 3 là người add có phải admin hay không
    public BufferedReader getIs() {
        return is;
    }
    
    public void setclientNumber(int id) {
    	clientNumber= id;
    }

    public BufferedWriter getOs() {
        return os;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public ServerThread(Socket socketOfServer, int clientNumber) {
        this.socketOfServer = socketOfServer;
        this.clientNumber = clientNumber;
        System.out.println("Server thread number " + clientNumber + " Started");
        isClosed = false;
    }

    @Override
    public void run() {
        try {
            // Mở luồng vào ra trên Socket tại Server.
            is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
            System.out.println("Khời động luông mới thành công, ID là: " + clientNumber);
            write("get-id" + "," + this.clientNumber);
            Server.serverThreadBus.sendOnlineList();
            Server.serverThreadBus.mutilCastSend("global-message"+","+"---Client "+this.clientNumber+" đã đăng nhập---");
            String message;
            while (!isClosed) {
                message = is.readLine();
                if (message == null) {
                    break;
                }
                System.out.print(message);
                String[] messageSplit = message.split("\\|");
                String commandString =  messageSplit[0];
                if(commandString == "DirectMessage") {
	                String id1 = messageSplit[1];//Người gửi
	                String id2 = messageSplit[2];//Người nhận
	                String content = messageSplit[3];
	                String[] mess = ServerThread.CheckMessageExists(id1, id2);
	                if(mess[0] != "") {
	                	System.out.println("Update");
	                	String newContent = mess[1] + "|" + content;
	                	ServerThread.UpdateExistsMessage(id1, id2, newContent);
	                }else {
	                	System.out.println("Insert");
	                	ServerThread.InsertMessage(id1, id2, content);
	                }
                }
                else if(commandString == "AddFriend") {
	                String id1 = messageSplit[1];//Người gửi
	                String id2 = messageSplit[2];//Người nhận
	                String content = messageSplit[3];
	                String[] mess = ServerThread.CheckMessageExists(id1, id2);
	                if(mess[0] != "") {
	                	System.out.println("Update");
	                	String newContent = mess[1] + "|" + content;
	                	ServerThread.UpdateExistsMessage(id1, id2, newContent);
	                }else {
	                	System.out.println("Insert");
	                	ServerThread.InsertMessage(id1, id2, content);
	                }
                }
                else if(commandString == "RemoveFriend") {
	                String id1 = messageSplit[1];//Người gửi
	                String id2 = messageSplit[2];//Người nhận
	                String content = messageSplit[3];
	                String[] mess = ServerThread.CheckMessageExists(id1, id2);
	                if(mess[0] != "") {
	                	System.out.println("Update");
	                	String newContent = mess[1] + "|" + content;
	                	ServerThread.UpdateExistsMessage(id1, id2, newContent);
	                }else {
	                	System.out.println("Insert");
	                	ServerThread.InsertMessage(id1, id2, content);
	                }
                }
                else if(commandString == "RemoveMessage") {
	                String id1 = messageSplit[1];//Người gửi
	                String id2 = messageSplit[2];//Người nhận
	                String content = messageSplit[3];
	                String[] mess = ServerThread.CheckMessageExists(id1, id2);
	                if(mess[0] != "") {
	                	System.out.println("Update");
	                	String newContent = mess[1] + "|" + content;
	                	ServerThread.UpdateExistsMessage(id1, id2, newContent);
	                }else {
	                	System.out.println("Insert");
	                	ServerThread.InsertMessage(id1, id2, content);
	                }
                }

                
            }
        } catch (IOException e) {
            isClosed = true;
            Server.serverThreadBus.remove(clientNumber);
            System.out.println(this.clientNumber+" đã thoát");
            Server.serverThreadBus.sendOnlineList();
            Server.serverThreadBus.mutilCastSend("global-message"+","+"---Client "+this.clientNumber+" đã thoát---");
        }
    }
    public void write(String message) throws IOException{
        os.write(message);
        os.newLine();
        os.flush();
    }
    
    public static String[] CheckMessageExists(String id,String id2) {
    	String idChat1=id+"|" + id2;
    	String idChat2=id2+"|" + id;
    	String[] result = new String[2];
		try (Connection connection =  DriverManager.getConnection(URL, USER, PW);

				PreparedStatement preparedStatement = connection.prepareStatement(FIND_MESSAGE_SQL)) { 
	            preparedStatement.setString(1, idChat1);
	            preparedStatement.setString(2, idChat2);

	            ResultSet rs = preparedStatement.executeQuery();
	            if(rs.next()) {
	            	result[0] =rs.getString("idChat");
	            	result[1] = rs.getString("content");
	            	return result;
	            };
	            return result;
	        } catch (SQLException e) {
	        	e.printStackTrace();
                System.exit(1);
	           return result;
	        }
	}
    
    public static boolean UpdateExistsMessage(String id,String id2,String content) {
    	String idChat1=id+"|" + id2;
    	String idChat2=id2+"|" + id;
    	
		try (Connection connection =  DriverManager.getConnection(URL, USER, PW);

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
    
    public static boolean InsertMessage(String id,String id2,String content) {
    	String idChat1=id+"|" + id2;
    	
		try (Connection connection =  DriverManager.getConnection(URL, USER, PW);

				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_MESSAGE_SQL)) { 
				preparedStatement.setString(1, idChat1);    
				preparedStatement.setString(2, content);
	            
	            
	            int count = preparedStatement.executeUpdate();
	            return count > 0;
	        } catch (SQLException e) {
	        	e.printStackTrace();
                System.exit(1);
	           return false;
	        }
	}
    
    public static boolean RemoveMessage(String id) {
    	try (Connection connection =  DriverManager.getConnection(URL, USER, PW);

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
    
    public static ArrayList<String> GetFriendList(String id) {
    	try (Connection connection =  DriverManager.getConnection(URL, USER, PW);
				PreparedStatement preparedStatement = connection.prepareStatement(GET_FRIEND_LIST_SQL)) { 
				preparedStatement.setString(1, id);    
				ArrayList<String> result = new ArrayList<String>();
	            
	            ResultSet rs = preparedStatement.executeQuery();
	            while(rs.next()) {
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
    
    public static boolean RemoveFriend(String userId,String FriendId) {
    	try (Connection connection =  DriverManager.getConnection(URL, USER, PW);
    			PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_FRIEND_LIST_SQL);
				PreparedStatement preparedStatement1 = connection.prepareStatement(REMOVE_FRIEND_LIST_SQL)) { 
				preparedStatement.setString(1, userId);    
				preparedStatement.setString(2, FriendId);   
				preparedStatement1.setString(1, FriendId);    
				preparedStatement1.setString(2, userId);   
				
	            int count = preparedStatement.executeUpdate();
	            int count1 = preparedStatement1.executeUpdate();
	            return count > 0 && count1>0;
	        } catch (SQLException e) {
	        	e.printStackTrace();
                System.exit(1);
	           return false;
	        }
    }
    
    public static boolean UpdatePassword(String id,String newPassword) {
    	try (Connection connection =  DriverManager.getConnection(URL, USER, PW);

				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD_SQL)) { 
				preparedStatement.setString(1, newPassword);    
				preparedStatement.setString(2, id); 
				
	            int count = preparedStatement.executeUpdate();
	            return count > 0;
	        } catch (SQLException e) {
	        	e.printStackTrace();
                System.exit(1);
	           return false;
	        }
    }
    
    public static boolean LockAccount(String id,boolean lock) {
    	try (Connection connection =  DriverManager.getConnection(URL, USER, PW);

				PreparedStatement preparedStatement = connection.prepareStatement(LOCK_UNLOCK_SQL)) { 
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
    
    public static boolean UpdateAccountHistroy(String id,String history) {
    	try (Connection connection =  DriverManager.getConnection(URL, USER, PW);
				PreparedStatement preparedStatement = connection.prepareStatement(ACCOUNT_HISTORY_SQL)) { 
				preparedStatement.setString(1, history);    
				preparedStatement.setString(2, id); 
				
	            int count = preparedStatement.executeUpdate();
	            return count > 0;
	        } catch (SQLException e) {
	        	e.printStackTrace();
                System.exit(1);
	           return false;
	        }
    }
    
    public static boolean AddFriend(String userId,String FriendId) {
    	try (Connection connection =  DriverManager.getConnection(URL, USER, PW);
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
	            return count > 0 && count1>0;
	        } catch (SQLException e) {
	        	e.printStackTrace();
                System.exit(1);
	           return false;
	        }
    }
    
    public static ArrayList<String> GetAdminInGroup(String groupID) {
    	try (Connection connection =  DriverManager.getConnection(URL, USER, PW);
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
    
}