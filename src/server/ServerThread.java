package server;

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
    private static final String GET_MESSAGE_SQL = "Update INTO public.\"messages\" SET content = ? WHERE idchat = ? or idchat = ?";
    
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
                String id1 = messageSplit[0];//Người gửi
                String id2 = messageSplit[1];//Người nhận
                String content = messageSplit[2];
                String[] mess = ServerThread.CheckMessageExists(id1, id2);
                if(mess[0] != "") {
                	System.out.println("Update");
                	String newContent = mess[1] + "|" + content;
                	ServerThread.UpdateExistsMessage(id1, id2, newContent);
                }else {
                	System.out.println("Insert");
                	ServerThread.InsertMessage(id1, id2, content);
                }
//                if(messageSplit[0].equals("send-to-global")){
//                    Server.serverThreadBus.boardCast(this.getClientNumber(),"global-message"+","+"Client "+messageSplit[2]+": "+messageSplit[1]);
//                }
//                if(messageSplit[0].equals("send-to-person")){
//                    Server.serverThreadBus.sendMessageToPersion(Integer.parseInt(messageSplit[3]),"Client "+ messageSplit[2]+" (tới bạn): "+messageSplit[1]);
//                }
                System.out.println(id1 + " " + id2 + " " + content);
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
}