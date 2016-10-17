package com.example.dragos.basketballdragcode;

/**
 * Created by Dragos on 2016-08-31.
 */
public class Player {

    private String mName;
    private String mPhoneNumber ;
    private String userId;
    private String userKey;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public Player()
    {
    }

    public String getUName()
    {
        return mName;
    }


    public String getUPhoneNumber()
    {
        return mPhoneNumber;
    }

    public void setphoneNumber(String phone){
        this.mPhoneNumber= phone;
    }

    public void setName(String name){this.mName=name;}



}
