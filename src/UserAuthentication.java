import java.util.*;

import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;

public class UserAuthentication {
	static final String URL = "jdbc:postgresql://localhost/chatting-application";
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String USER = "postgres";
    static final String PW = "123456";
    
	private static final String FIND_USERS_SQL = "SELECT * FROM public.\"users\" where email = ? and password = ?";
	
	private static final String INSERT_USERS_SQL = "INSERT INTO public.\"users\" (id,name, email,password) values (?,?,?,?)";
	
	private static final String USER_EXIST = "SELECT * FROM public.\"users\" where email = ? ";
	
	public static boolean SignIn(User user) {
		try (Connection connection =  DriverManager.getConnection(URL, USER, PW);
	            // Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(FIND_USERS_SQL)) { 
	            preparedStatement.setString(1, user.email);
	            preparedStatement.setString(2, user.password);
	            
	            // Step 3: Execute the query or update query
	            ResultSet rs = preparedStatement.executeQuery();
	            if(rs.next()) {
	            	System.out.print("existed");
	            	return true;
	            };
	            return false;
	        } catch (SQLException e) {
	        	e.printStackTrace();
                System.exit(1);
	            // print SQL exception information
	           return false;
	        }
	}
	
	public static boolean SignUp(User user) {
		try (Connection connection =  DriverManager.getConnection(URL, USER, PW);
				PreparedStatement stmt = connection.prepareStatement(USER_EXIST);
	            // Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) { 
	            preparedStatement.setString(1, user.id);
	            preparedStatement.setString(2, user.name);
	            preparedStatement.setString(3, user.email);
	            preparedStatement.setString(4, user.password);
	            
	            System.out.print(preparedStatement);
	            stmt.setString(1, user.email);
	            ResultSet rs = stmt.executeQuery();
	            if(rs.next()) {
	            	System.out.print("existed");
	            	return false;
	            }
	            
	            // Step 3: Execute the query or update query
	            int count =  preparedStatement.executeUpdate();
	            System.out.println(count);
	            return count > 0;
	        } catch (SQLException e) {
	        	e.printStackTrace();
                System.exit(1);
	            // print SQL exception information
	           return false;
	        }
	}
}
