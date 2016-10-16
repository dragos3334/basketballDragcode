package com.example.dragos.basketballdragcode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Dragos on 2016-09-08.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class playerrr {

    private String mName;
    private String mPhoneNumber ;



    public playerrr(){}

    public String getUUName()
    {
        return mName;
    }


    public String getUUPhoneNumber()
    {
        return mPhoneNumber;
    }

    public void setphoneNNumber(String phone){
        this.mPhoneNumber= phone;
    }

    public void setNName(String name){this.mName=name;}



}
