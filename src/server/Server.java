package server;

import com.sun.tools.jconsole.JConsoleContext;
import com.sun.tools.jconsole.JConsolePlugin;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {

    public static volatile ServerThreadBus serverThreadBus;
    public static Socket socketOfServer;

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/";
        String username = "postgres";
        String password = "123456";
        String databaseName = "chatting-application";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            if (!databaseExists(connection, databaseName)) {

                System.out.println(databaseExists(connection, databaseName));
                createDatabase(connection, databaseName);

                jdbcUrl += databaseName;
                try(Connection connectionNew = DriverManager.getConnection(jdbcUrl, username, password)) {
                    createTables(connectionNew);

                    System.out.println("Database and tables created successfully.");
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Database already exists. Doing nothing.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        ServerSocket listener = null;
        serverThreadBus = new ServerThreadBus();
        System.out.println("Server is waiting to accept users...");

        try {
            listener = new ServerSocket(7777);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10, // corePoolSize
                100, // maximumPoolSize
                10, // thread timeout
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(8) // queueCapacity
        );
        try {
            char newKey = 'a';
            String id = "";
            while (true) {
                socketOfServer = listener.accept();
                //ID là client Number nhớ thay đổi tham số 2 của serverThread
                id += newKey++;
//                id = "1";
                ServerThread serverThread = new ServerThread(socketOfServer, id);
                serverThreadBus.add(serverThread);
                System.out.println("Thread ID: " + id);
                System.out.println("Number of users active: " + serverThreadBus.getLength());
                executor.execute(serverThread);

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                listener.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static boolean databaseExists(Connection connection, String databaseName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT FROM pg_database WHERE datname = '" + databaseName + "'");
            return ((ResultSet) resultSet).next();
        }
    }

    public static void createDatabase(Connection connection, String databaseName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String createDatabaseQuery = "CREATE DATABASE \"" + databaseName + "\"";
            statement.executeUpdate(createDatabaseQuery);
        }
    }

    private static void createTables(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String createUsers = "CREATE TABLE IF NOT EXISTS users (" +
                    "id TEXT PRIMARY KEY," +
                    "username TEXT," +
                    "fullname TEXT," +
                    "email TEXT," +
                    "password TEXT," +
                    "friends TEXT[]," +
                    "\"isAdmin\" BOOLEAN," +
                    "lock BOOLEAN DEFAULT false," +
                    "\"isOnline\" BOOLEAN DEFAULT false," +
                    "\"createAt\" DATE," +
                    "address TEXT," +
                    "dob DATE," +
                    "blocks TEXT[]," +
                    "gender TEXT)";

            String createLogs = "CREATE TABLE IF NOT EXISTS logs (" +
                    "username TEXT," +
                    "logdate TIMESTAMP WITH TIME ZONE, " +
                    "PRIMARY KEY(username, logdate))";

            String createMessages = "CREATE TABLE IF NOT EXISTS messages (" +
                    "\"idChat\" TEXT PRIMARY KEY," +
                    "users TEXT[]," +
                    "content TEXT[])";

            String createSpams = "CREATE TABLE IF NOT EXISTS spams (" +
                    "username TEXT," +
                    "\"ByUser\" TEXT," +
                    "date DATE," +
                    "PRIMARY KEY(username, \"ByUser\"))";

            String createGroups = "CREATE TABLE IF NOT EXISTS groups (" +
                    "groupid TEXT PRIMARY KEY," +
                    "admin TEXT[]," +
                    "groupname TEXT," +
                    "users TEXT[]," +
                    "content TEXT[]," +
                    "\"createAt\" DATE)";

            String createSystems = "CREATE TABLE IF NOT EXISTS systems (" +
                    "username TEXT," +
                    "type INTEGER," +
                    "\"idChat\" TEXT," +
                    "\"time\" TIMESTAMP WITH TIME ZONE," +
                    "PRIMARY KEY(username, \"time\"))";

            statement.executeUpdate(createUsers);
            statement.executeUpdate(createLogs);
            statement.executeUpdate(createSpams);
            statement.executeUpdate(createGroups);
            statement.executeUpdate(createMessages);
            statement.executeUpdate(createSystems);
        }
    }
}