package com.example.dragos.basketballdragcode;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class ProfilActivity extends AppCompatActivity {

    ViewPager MyPager;
    MyPagerAdapter Myadapter;
    public static ArrayList<Player> Players;
    private Firebase firebase = new Firebase(MainActivity.DB_url);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        Players = new ArrayList<>();

        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot playerSnapshot : dataSnapshot.getChildren()) {
                    String name = (String) playerSnapshot.child("uname").getValue();
                    String phoneNumber = (String) playerSnapshot.child("uphoneNumber").getValue();
                    String userID = (String) playerSnapshot.child("userId").getValue();
                    String userKey= (String) playerSnapshot.getKey();
                    Player player = new Player();
                    player.setphoneNumber(phoneNumber);
                    player.setUserKey(userKey);
                    player.setName(name);
                    player.setUserId(userID);
                    Players.add(player);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        MyPager = (ViewPager) findViewById(R.id.pager);
        Myadapter = new MyPagerAdapter(getSupportFragmentManager());
        MyPager.setAdapter(Myadapter);
        MyPager.setCurrentItem(1);

    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public int getCount() {
            return 3;
        }


        @Override
        public Fragment getItem(int position) {


            switch (position) {
                case 0:
                    return new Fragment1();
                case 1:
                    return new Fragment2();
                case 2:
                    return new Fragment1();
                default:
                    return null;
            }

        }
    }


}