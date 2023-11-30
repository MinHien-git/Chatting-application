package ui.activity;

import presentation.presenter.loginPresenter;
import presentation.view.login;
import presentation.view.register;

import javax.swing.*;

public class Application {
    private loginPresenter control = new loginPresenter();
    Application() {
        JFrame app = new JFrame();
        JPanel mainPanel = new JPanel();

        mainPanel.add(control.getRenderPanel());

        control.setSubmitListenerToRegister(() -> {
            System.out.println("IN");
            mainPanel.removeAll();
            mainPanel.revalidate();
            control.renderView();
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
