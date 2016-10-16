package com.example.dragos.basketballdragcode;

import com.firebase.client.Firebase;

/**
 * Created by Dragos on 2016-09-05.
 */
public class InitializeFirebase extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
