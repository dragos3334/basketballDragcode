package com.example.dragos.basketballdragcode;

/**
 * Created by Dragos on 2016-08-31.
 */
public class Player {

    private String mName;
    private String mPhoneNumber ;



    public Player(String UName, String UPhoneNumber)
    {
        mName= UName;
        mPhoneNumber= UPhoneNumber;

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
