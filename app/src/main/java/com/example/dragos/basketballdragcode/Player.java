package com.example.dragos.basketballdragcode;

import android.graphics.Bitmap;
import android.net.Uri;

import java.net.URI;

/**
 * Created by Dragos on 2016-08-31.
 */
public class Player {

    private String mName;
    private String mPhoneNumber ;
    private String userId;
    private String userKey;
    private String profifPic;

    public Player() {}
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getUserKey() {return userKey;}

    public void setUserKey(String userKey) {
        this.userKey = userKey;
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

    public String getProfifPic() {
        return profifPic;
    }

    public void setProfifPic(String profifPic) {
        this.profifPic = profifPic;
    }
}
