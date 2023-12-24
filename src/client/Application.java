package client;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Application {
    private static JFrame applicationFrame;
    private Thread thread;
    private static BufferedWriter os;
    private static BufferedReader is;
    private Socket socketOfClient;

    public static void write(String message) throws IOException{
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
                        socketOfClient = new Socket("127.0.0.1", 7777);
                        System.out.println("Successfully Connected!");
                        // Tạo luồng đầu ra tại client (Gửi dữ liệu tới server)
                        os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
                        // Luồng đầu vào tại Client (Nhận dữ liệu từ server).
                        is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));

                        String message;

                        while (true) {
                            message = is.readLine();
                            System.out.println(message);
                            if (message == null) {
                                break;
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
            thread.sleep(1000);
            thread.run();
        } catch (Exception e) {
        }
    }

    public Application() {
        try {
            login window = new login();
            window.frmLogin.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JFrame getApplicationFrame() {
        return applicationFrame;
    }

    public static void main(String[] args) {
        applicationFrame = new JFrame();
        Application app = new Application();
        app.setUpSocket();
    }
}