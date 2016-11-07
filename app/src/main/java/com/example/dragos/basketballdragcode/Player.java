package com.example.dragos.basketballdragcode;

/**
 * Created by Dragos on 2016-08-31.
 */
public class Player {

    private String name;
    private String phoneNumber;
    private String userId;
    private String userKey;
    private String email;
    private String profilPic;

    Player() {}


    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getUserKey() {return userKey;}
    public void setUserKey(String userKey) {this.userKey = userKey;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getName()
    {
        return name;
    }
    public void setName(String name){this.name =name;}

    public String getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(String phone){
        this.phoneNumber = phone;
    }


    public String getProfilPic() {
        return profilPic;
    }
    public void setProfilPic(String profilPic) {
        this.profilPic = profilPic;
    }
}
