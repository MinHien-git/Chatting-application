package presentation.presenter;

import presentation.view.login;
import presentation.view.register;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class loginPresenter {
    private JPanel renderPanel;
    private static SubmitListener submitListener;

    public interface SubmitListener {
        void onSubmit();
    }
    public void setSubmitListenerToRegister(SubmitListener listener) {
        submitListener = listener;
    }
    public static class btnRegisterActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (submitListener != null) {
                submitListener.onSubmit();
            }
        }
    }
    public loginPresenter() {
        login a = new login();
        this.renderPanel = a.loginAccount();
    }
    public void renderView() {
        register a = new register();
        this.renderPanel = a.registerAccount();
    }
    public JPanel getRenderPanel() {
        return renderPanel;
    }
}

