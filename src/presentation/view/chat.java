package presentation.view;
import presentation.presenter.chatPresenter;
import ui.activity.Application;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class chat extends JPanel {
    JTextArea textArea;
    static String id;

    public String getId() {
        return id;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public static void configMainFrame(JFrame mainFrame) {
        mainFrame.getContentPane().setBackground(Color.WHITE);
        mainFrame.getContentPane().setForeground(Color.WHITE);
        mainFrame.setFont(new Font("Source Code Pro", Font.PLAIN, 12));
        mainFrame.setResizable(false);
        mainFrame.setTitle("Chatting " + id);
        mainFrame.setBounds(100, 100, 440, 545);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setLayout(null);
    }

    public void configTextArea() {
        textArea.setBounds(10, 11, 199, 37);
        textArea.setLineWrap(true);
        textArea.setFont(new Font("Source Code Pro", Font.PLAIN, 11));
        textArea.setColumns(5);
        textArea.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
    }

    public chat()
    {
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(null);
        panel_1.setAutoscrolls(true);
        panel_1.setBounds(205, 0, 230, 506);
        panel_1.setPreferredSize(new Dimension(8, 10));
        panel_1.setBackground(Color.WHITE);
        this.add(panel_1);
        panel_1.setLayout(null);

        JPanel panel_2 = new JPanel();
        panel_2.setBackground(Color.WHITE);
        panel_2.setBounds(0, 411, 230, 95);
        panel_1.add(panel_2);
        panel_2.setLayout(null);

        textArea = new JTextArea();
        this.configTextArea();
        panel_2.add(textArea);

        JButton btnSend = new JButton("Gửi");
        btnSend.addActionListener(new chatPresenter.btnSendActionListener());
        btnSend.setFont(new Font("Source Code Pro Black", Font.PLAIN, 11));
        btnSend.setBounds(10, 50, 199, 37);
        btnSend.setForeground(Color.WHITE);
        btnSend.setBorder(null);
        btnSend.setBackground(new Color(30, 144, 255));
        panel_2.add(btnSend);

        JPanel panel_3 = new JPanel();
        panel_3.setBorder(null);
        panel_3.setBackground(Color.DARK_GRAY);
        panel_3.setBounds(0, 0, 220, 34);
        panel_1.add(panel_3);
        panel_3.setLayout(null);

        JLabel username = new JLabel("@username");
        username.setForeground(new Color(255, 255, 255));
        username.setFont(new Font("Source Code Pro ExtraBold", Font.BOLD, 11));
        username.setBounds(10, 10, 136, 14);
        panel_3.add(username);

        JButton btnBlock = new JButton("Block");
        btnBlock.setBorder(UIManager.getBorder("Button.border"));
        btnBlock.setBackground(new Color(255, 69, 0));
        btnBlock.setForeground(Color.WHITE);
        btnBlock.setFont(new Font("Source Code Pro Black", Font.BOLD, 11));
        btnBlock.addActionListener(new chatPresenter.btnBlockActionListener());
        btnBlock.setBounds(574, 6, 90, 23);
        panel_3.add(btnBlock);

        JButton btnSpam = new JButton("Spam");
        btnSpam.setForeground(new Color(255, 0, 0));
        btnSpam.setFont(new Font("Source Code Pro Black", Font.BOLD, 11));
        btnSpam.setBorder(null);
        btnSpam.setBackground(new Color(100, 149, 237));
        btnSpam.setBounds(474, 6, 90, 23);
        btnSpam.addActionListener(new chatPresenter.btnSpamActionListener());
        panel_3.add(btnSpam);

        JPanel panel = new JPanel();
        panel.setBorder(new MatteBorder(0, 0, 0, 2, (Color) new Color(192, 192, 192)));
        panel.setBackground(Color.WHITE);
        panel.setBounds(0, 0, 206, 506);
        this.add(panel);
        panel.setLayout(null);

        JPanel panel_4 = new JPanel();
        panel_4.setBorder(null);
        panel_4.setBackground(new Color(30, 144, 255));
        panel_4.setBounds(0, 0, 229, 34);
        panel.add(panel_4);
        panel_4.setLayout(null);

        JLabel lbl_list = new JLabel("Danh sách");
        lbl_list.setForeground(new Color(255, 255, 255));
        lbl_list.setBounds(10, 10, 120, 14);
        lbl_list.setFont(new Font("Source Code Pro ExtraBold", Font.BOLD, 11));
        panel_4.add(lbl_list);

        JButton btnOut = new JButton("Đăng xuất");
        btnOut.addActionListener(new chatPresenter.btnLogoutActionListener());
        btnOut.setForeground(Color.WHITE);
        btnOut.setFont(new Font("Source Code Pro Black", Font.PLAIN, 11));
        btnOut.setBorder(null);
        btnOut.setBackground(new Color(255, 0, 0));
        btnOut.setBounds(10, 458, 186, 37);
        panel.add(btnOut);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(new chat());
        configMainFrame(frame);
        frame.setVisible(true);
    }
}
