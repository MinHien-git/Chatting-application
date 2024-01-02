package data.model;

import java.util.Date;

import data.repository.userRepo;

public class userModel {
    private String username;
    private String fullname;
    private String address;
    private Date birthday;
    private boolean gender;
    private String email;

    userModel(String username, boolean gender, String email) {
        this.username = username;
        this.gender = gender;
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public boolean getGender() {
        return this.gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public String authenticate(String username, String password) {
        userRepo checkLogin = new userRepo();
        if (checkLogin.checkLogin(username, password)) {
            return "in";
        }
        else {
            return "out";
        }
    }

//    public boolean register(String username, String password, )
}
