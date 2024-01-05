package presentation.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JPanel;

import presentation.view.chat;

public class chatPresenter {
    private JPanel renderPanel;
    private Thread thread;
    private BufferedWriter os;
    private BufferedReader is;
    private Socket socketOfClient;

    public interface clickListener {
        void onClick();
    }

    public JPanel getRenderPanel() {
        return renderPanel;
    }

    private static clickListener sendMsgListener;
    private static clickListener blockListener;
    private static clickListener spamListener;
    private static clickListener logoutListener;

    public static void setSendMsgListener(clickListener sendMsgListener) {
        chatPresenter.sendMsgListener = sendMsgListener;
    }

    public static void setBlockListener(clickListener blockListener) {
        chatPresenter.blockListener = blockListener;
    }

    public static void setLogoutListener(clickListener logoutListener) {
        chatPresenter.logoutListener = logoutListener;
    }

    public static void setSpamListener(clickListener spamListener) {
        chatPresenter.spamListener = spamListener;
    }

    private void write(String message) throws IOException {
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
                        // Gửi yêu cầu kết nối tới Server đang lắng nghe
                        // trên máy 'localhost' cổng 7777.
                        socketOfClient = new Socket("127.0.0.1", 7777);
                        System.out.println("Kết nối thành công!");
                        // Tạo luồng đầu ra tại client (Gửi dữ liệu tới server)
                        os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
                        // Luồng đầu vào tại Client (Nhận dữ liệu từ server).
                        is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));

                        String message;

                        while (true) {

                            System.out.println();

                            message = is.readLine();
                            if(message==null){
                                break;
                            }

                        }
//                    os.close();
//                    is.close();
//                    socketOfClient.close();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            };
            Thread.sleep(1000);
            thread.run();
        } catch (Exception e) {
        }
    }

    public chatPresenter() {
        renderPanel = new chat();
    }

    public void setChatView() {
        renderPanel = new chat();
    }

    public static class btnSendActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (sendMsgListener != null) {
                sendMsgListener.onClick();
            }
        }
    }

        public static class btnBlockActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (blockListener != null) {
                    blockListener.onClick();
                }
            }
        }

        public static class btnSpamActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (spamListener != null) {
                    spamListener.onClick();
                }
            }
        }

        public static class btnLogoutActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (logoutListener != null) {
                    logoutListener.onClick();
                }
            }
        }
}
