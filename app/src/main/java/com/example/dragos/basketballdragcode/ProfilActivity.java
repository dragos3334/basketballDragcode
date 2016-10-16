package com.example.dragos.basketballdragcode;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ProfilActivity extends AppCompatActivity {

    ViewPager MyPager;
    MyPagerAdapter Myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);



        MyPager =(ViewPager)findViewById(R.id.pager);
        Myadapter= new MyPagerAdapter(getSupportFragmentManager());
        MyPager.setAdapter(Myadapter);

    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {


        public  MyPagerAdapter(FragmentManager fm){
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
                    return new SetPlayerInfoActivity.Fragment1();
                case 1:
                    return new Fragment2();
                case 2:
                    return new SetPlayerInfoActivity.Fragment1();
                default:
                    return null;
            }

        }
    }



}