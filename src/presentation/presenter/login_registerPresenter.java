package presentation.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import presentation.view.login;
import presentation.view.register;

public class login_registerPresenter {
    private JPanel renderPanel;
    private static SubmitListener submitListenerRegister;
    private static SubmitListener submitListenerLogin;

    public interface SubmitListener {
        void onSubmit();
    }
    public void setSubmitListenerToRegister(SubmitListener listener) {
        submitListenerRegister = listener;
    }
    public void setSubmitListenerToLogin(SubmitListener listener) {
        submitListenerLogin = listener;
    }
    public static class btnRegisterActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (submitListenerRegister != null) {
                submitListenerRegister.onSubmit();
            }
        }
    }

    public static class btnLoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (submitListenerLogin != null) {
                submitListenerLogin.onSubmit();
            }
        }
    }
    public login_registerPresenter() {
        login a = new login();
        this.renderPanel = a.loginAccount();
    }
    public void renderViewRegister() {
        register a = new register();
        this.renderPanel = a.registerAccount();
    }

    public void renderViewLogin() {
        login a = new login();
        this.renderPanel = a.loginAccount();
    }
    public JPanel getRenderPanel() {
        return renderPanel;
    }
}

