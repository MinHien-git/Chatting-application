package server;

import java.awt.Taskbar.State;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import client.User;

public class ServerThread implements Runnable {
    static final String URL = "jdbc:postgresql://localhost:5432/chatting";
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String USER = "postgres";
    static final String PW = "123456";

    private Socket socketOfServer;
    private String clientNumber;
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

    public void setclientNumber(String id) {
        clientNumber = id;
    }

    public BufferedWriter getOs() {
        return os;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public ServerThread(Socket socketOfServer, String clientNumber) {
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
//            Server.serverThreadBus.sendOnlineList();
            //Server.serverThreadBus.mutilCastSend("global-message"+","+"---Client "+this.clientNumber+" đã đăng nhập---");
            String message;
            while (!isClosed) {
                message = is.readLine();
                if (message == null) {
                    break;
                }
                String[] messageSplit = message.split("\\|");
                String commandString = messageSplit[0];
                System.out.println(commandString);
                if (commandString.equals("DirectMessage")) {
                    System.out.println("DirectMessage");
                    String id1 = messageSplit[1];//Người gửi
                    String id2 = messageSplit[2];//Từ user
                    String content = messageSplit[3];
                    String[] mess = ServerThread.CheckMessageExists(id1, id2);
                    if (!mess[0].equals("")) {
                        System.out.println("Update");
                        ServerThread.UpdateExistsMessage(id1, id2, content);
                    } else {
                        System.out.println("Insert");
                        ServerThread.InsertMessage(id1, id2, content);
                    }
                    mess = ServerThread.CheckMessageExists(id1, id2);
                    Server.serverThreadBus.boardCast(id1, "send-to-user|" + String.join(", ", mess[1]));
                    Server.serverThreadBus.boardCast(id2, "send-to-user|" + String.join(", ", mess[1]));
                } else if (commandString.equals("AddFriend")) {
                    String id1 = messageSplit[1];//Người gửi
                    String id2 = messageSplit[2];//Từ user
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
            Server.serverThreadBus.remove(clientNumber);
            System.out.println(this.clientNumber + " đã thoát");
//            Server.serverThreadBus.sendOnlineList();
            Server.serverThreadBus.mutilCastSend("global-message" + "," + "---Client " + this.clientNumber + " đã thoát---");
        }
    }

    public void write(String message) throws IOException {
        os.write(message);
        os.newLine();
        os.flush();
    }

    //DirectMessage

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
        String SET_ONLINE_SQL = "UPDATE public.\"users\" SET isOnline = true where id = ?";
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
        String SET_ONLINE_SQL = "UPDATE public.\"users\" SET isOnline = false where id = ?";
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
        String UPDATE_MESSAGE_SQL = "Insert into public.\"spams\" SET (username,date,byUser) Values (?,current_timestamp,?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MESSAGE_SQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(1, byUser);
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
                    System.out.println("IN");
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

