package client;

import java.util.*;

import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;

public class UserAuthentication {
	private static final int maxUserID = 1000000;
	private static final int minUserID = 1;
	static final String URL = "jdbc:postgresql://localhost/chatting-application";
	static final String JDBC_DRIVER = "org.postgresql.Driver";
	static final String USER = "postgres";
	static final String PW = "123456";

	private static final String FIND_USERS_SQL = "SELECT * FROM public.\"users\" where email = ? and password = ?";

	private static final String INSERT_USERS_SQL = "INSERT INTO public.\"users\" (id,name, email,password) values (?,?,?,?)";

	private static final String USER_EXIST = "SELECT * FROM public.\"users\" where email = ? ";

	private static final String ID_EXISTS = "SELECT * FROM public.\"users\" where id = ? ";

	private static final String USERNAME_EXISTS = "SELECT * FROM public.\"users\" where name = ?";

	private static final String UPDATE_REGISTER = "UPDATE public.\"users\" SET friends = array[]::text[], \"isAdmin\" = false , lock = false, history = '', \"isOnline\" = false, blocks = array[]::text[] where id = ?";

	private static final String FIND_BLOCK_LIST = "SELECT u.blocks FROM public.\"users\" u where u.id = ?";

	private static final String FIND_FRIENDS = "SELECT u2.id, u2.name, u2.lock, u2.\"isOnline\", u2.blocks FROM public.\"users\" u JOIN public.\"users\" u2 ON u2.id = ANY (u.friends) where u.id = ?";

	private static final String FIND_ONLINE_FRIENDS = "SELECT u2.id, u2.name, u2.lock, u2.\"isOnline\", u2.blocks FROM public.\"users\" u JOIN public.\"users\" u2 ON u2.id = ANY (u.friends) where u.id = ? and u2.\"isOnline\" = true";

	private static final String FIND_GROUPS = "SELECT g.* FROM public.\"users\" u JOIN public.\"groups\" g ON u.id = ANY(g.users) where u.id = ?";

	private static final String RESET_PW = "UPDATE public.\"users\" SET \"password\" = ? where email = ?";
	public UserAuthentication() {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException cnf) {
			System.out.println("Class not found");
			cnf.printStackTrace();
			System.exit(1);
		}
	}

	public static User idToUser(String _id) {
		try (Connection connection = DriverManager.getConnection(URL, USER, PW)) {
			PreparedStatement idExists = connection.prepareStatement(ID_EXISTS);
			idExists.setString(1, _id);
			ResultSet results = idExists.executeQuery();

			if (results.next())
			{
				boolean isLocked = results.getBoolean("lock");
				java.sql.Array bl = results.getArray("blocks");
				ArrayList<String> blocks = new ArrayList<>(Arrays.asList((String []) bl.getArray()));
				String _name = results.getString("name");
				boolean onlStatus = results.getBoolean("isOnline");
				return new User(_id, _name, isLocked, onlStatus, blocks);
			}
			return null;
		} catch (SQLException sqlException) {
			System.out.println("Unable to connect to database");
			sqlException.printStackTrace();
			return null;
		}
	}

	public static String usernameToID(String _username)
	{
		try (Connection connection = DriverManager.getConnection(URL, USER, PW)) {
			PreparedStatement id = connection.prepareStatement(USERNAME_EXISTS);
			id.setString(1, _username);
			ResultSet results = id.executeQuery();

			if (results.next())
			{
				return results.getString("id");
			}
			return null;
		} catch (SQLException sqlException) {
			System.out.println("Unable to connect to database");
			sqlException.printStackTrace();
			return null;
		}
	}

	public static String SignIn(User user) {
		try (Connection connection = DriverManager.getConnection(URL, USER, PW);
			 // Step 2:Create a statement using connection object
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_USERS_SQL)) {
			preparedStatement.setString(1, user.email);
			preparedStatement.setString(2, user.password);

			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				return rs.getString("id");
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

	public static boolean resetPassword(String pw, String email)
	{
		try (Connection connection = DriverManager.getConnection(URL, USER, PW)) {
			PreparedStatement reset = connection.prepareStatement(RESET_PW);
			reset.setString(1, pw);
			reset.setString(2, email);

			int results = reset.executeUpdate();

			if (results != 0) return true;
			return false;
		} catch (SQLException sqlException) {
			System.out.println("Unable to connect to database");
			sqlException.printStackTrace();
			return false;
		}
	}

	public static boolean updateRegister(User user) {
		try (Connection connection = DriverManager.getConnection(URL, USER, PW)) {
			PreparedStatement update = connection.prepareStatement(UPDATE_REGISTER);
			update.setString(1, user.getId());
			int results = update.executeUpdate();

			if (results != 0) return true;
			return false;
		} catch (SQLException sqlException) {
			System.out.println("Unable to connect to database");
			sqlException.printStackTrace();
			return false;
		}
	}

	public static boolean updateCredentials(User user) {
		try (Connection connection = DriverManager.getConnection(URL, USER, PW)) {
			PreparedStatement preparedStatement = connection.prepareStatement(USER_EXIST);
			preparedStatement.setString(1, user.getEmail());
			ResultSet results = preparedStatement.executeQuery();

			if (results.next())
			{
				String _id = results.getString("id");
				user.setId(_id);
				String name = results.getString("name");
				user.setName(name);
				boolean isAdmin = results.getBoolean("isAdmin");
				user.setAdmin(isAdmin);
				boolean isLocked = results.getBoolean("lock");
				user.setLocked(isLocked);
				String history = results.getString("history");
				user.setHistory(history);
				user.setOnline(true);
			}
			return true;

		} catch (SQLException sqlException) {
			System.out.println("Unable to connect to database");
			sqlException.printStackTrace();
			return false;
		}
	}

	public static boolean updateBlockList(User user) {
		try (Connection connection = DriverManager.getConnection(URL, USER, PW)) {
			PreparedStatement blocks = connection.prepareStatement(FIND_BLOCK_LIST);
			blocks.setString(1, user.getId());
			ResultSet blockList = blocks.executeQuery();

			if (blockList.next())
			{
				java.sql.Array bl = blockList.getArray("blocks");
				user.setBlockList(new ArrayList<>(Arrays.asList((String []) bl.getArray()))); //convert from sql arrays to arraylist
			}
			return true;

		} catch (SQLException sqlException) {
			System.out.println("Unable to connect to database");
			sqlException.printStackTrace();
			return false;
		}
	}

	public static boolean updateFriendsList(User user)
	{
		try (Connection connection = DriverManager.getConnection(URL, USER, PW)) {
			PreparedStatement friends = connection.prepareStatement(FIND_FRIENDS);
			friends.setString(1, user.getId());
			ResultSet friendList = friends.executeQuery();

			while (friendList.next())
			{
				String _id = friendList.getString("id");
				boolean isLocked = friendList.getBoolean("lock");
				java.sql.Array bl = friendList.getArray("blocks");
				ArrayList<String> blocks = new ArrayList<>(Arrays.asList((String []) bl.getArray()));
				if (user.getBlockList().contains(_id) || isLocked || blocks.contains(user.getId())) continue;
				String _name = friendList.getString("name");
				boolean onlStatus = friendList.getBoolean("isOnline");

				User friend = new User(_id, _name, false, onlStatus, blocks);
				user.getFriends().add(friend);
			}
			return true;

		} catch (SQLException sqlException) {
			System.out.println("Unable to connect to database");
			sqlException.printStackTrace();
			return false;
		}
	}

	public static boolean updateOnlList(User user)
	{
		try (Connection connection = DriverManager.getConnection(URL, USER, PW)) {
			PreparedStatement online = connection.prepareStatement(FIND_ONLINE_FRIENDS);
			online.setString(1, user.getId());
			ResultSet friendList = online.executeQuery();

			while (friendList.next())
			{
				String _id = friendList.getString("id");
				boolean isLocked = friendList.getBoolean("lock");
				java.sql.Array bl = friendList.getArray("blocks");
				ArrayList<String> blocks = new ArrayList<>(Arrays.asList((String []) bl.getArray()));
				if (user.getBlockList().contains(_id) || isLocked || blocks.contains(user.getId())) continue;
				String _name = friendList.getString("name");
				boolean onlStatus = friendList.getBoolean("isOnline");

				User onlFriend = new User(_id, _name, false, onlStatus, blocks);
				user.getOnlineList().add(onlFriend);
			}
			return true;

		} catch (SQLException sqlException) {
			System.out.println("Unable to connect to database");
			sqlException.printStackTrace();
			return false;
		}
	}

	public static boolean updateGroups(User user)
	{
		try (Connection connection = DriverManager.getConnection(URL, USER, PW)) {
			PreparedStatement groups = connection.prepareStatement(FIND_GROUPS);
			groups.setString(1, user.getId());
			ResultSet groupList = groups.executeQuery();

			ArrayList<User> adminList = new ArrayList<>();
			ArrayList<User> userList = new ArrayList<>();

			while (groupList.next())
			{
				String gID = groupList.getString("groupid");
				String groupName = groupList.getString("groupname");
				String content = groupList.getString("content");

				java.sql.Array ad = groupList.getArray("admin");
				ArrayList<String> admins = new ArrayList<>(Arrays.asList((String []) ad.getArray()));
				admins.forEach(id -> {
					adminList.add(idToUser(id));
				});

				java.sql.Array u = groupList.getArray("users");
				ArrayList<String> users = new ArrayList<>(Arrays.asList((String[]) u.getArray()));
				users.forEach(id -> {
					userList.add(idToUser(id));
				});

				user.getGroupList().add(new groupChat(gID, adminList, groupName, userList, content));
			}
			return true;

		} catch (SQLException sqlException) {
			System.out.println("Unable to connect to database");
			sqlException.printStackTrace();
			return false;
		}
	}

	public static boolean updateUser(User user) {
		if (!updateCredentials(user)) return false;

		if (updateBlockList(user) && updateFriendsList(user) && updateOnlList(user) && updateGroups(user)) return true;
		else return false;
	}

	public static boolean SignUp(User user) {
		while (!checkIDExists(user.getId())) {
			int id = (int) (Math.random() * (maxUserID - minUserID + 1)) + minUserID;
			user.setId(Integer.toString(id));
		}

		try (Connection connection = DriverManager.getConnection(URL, USER, PW);
			 PreparedStatement stmt = connection.prepareStatement(USER_EXIST);
			 // Step 2:Create a statement using connection object
			 PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
			preparedStatement.setString(1, user.id);
			preparedStatement.setString(2, user.name);
			preparedStatement.setString(3, user.email);
			preparedStatement.setString(4, user.password);

			stmt.setString(1, user.email);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				System.out.print(rs.getString("id"));
				return false;
			}

			// Step 3: Execute the query or update query
			int count = preparedStatement.executeUpdate();
			System.out.println(count);
			return count > 0 && updateRegister(user);
		} catch (SQLException e) {
			System.out.println("Unable to connect to database");
			e.printStackTrace();
			System.exit(1);
			// print SQL exception information
			return false;
		}
	}

	private static boolean checkIDExists(String _id) {
		try (Connection connection = DriverManager.getConnection(URL, USER, PW)) {
			PreparedStatement idExists = connection.prepareStatement(ID_EXISTS);
			idExists.setString(1, _id);
			ResultSet results = idExists.executeQuery();

			return !results.next();
		} catch (SQLException sqlException) {
			System.out.println("Unable to connect to database");
			sqlException.printStackTrace();
			return false;
		}
	}
}