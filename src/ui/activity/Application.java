package ui.activity;

import presentation.presenter.chatPresenter;
import presentation.presenter.login_registerPresenter;

import javax.swing.*;

public class Application {
    private final login_registerPresenter control = new login_registerPresenter();
    private final chatPresenter chatControl = new chatPresenter();
    Application() {
        JFrame app = new JFrame();
        JPanel mainPanel = new JPanel();

        mainPanel.add(control.getRenderPanel());

        control.setSubmitListenerToRegister(() -> {
            mainPanel.removeAll();
            mainPanel.revalidate();
            mainPanel.repaint();
            control.renderViewRegister();
            mainPanel.add(control.getRenderPanel());
        });

        control.setSubmitListenerToLogin(() -> {
            mainPanel.removeAll();
            mainPanel.revalidate();
            mainPanel.repaint();
            control.renderViewLogin();
            mainPanel.add(control.getRenderPanel());
        });

        app.add(mainPanel);
        app.setVisible(true);
        app.setSize(1000, 600);
        app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Application::new);
    }
}
