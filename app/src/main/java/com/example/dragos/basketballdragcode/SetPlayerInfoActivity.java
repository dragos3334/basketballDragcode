package com.example.dragos.basketballdragcode;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.snapshot.ChildKey;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class SetPlayerInfoActivity extends AppCompatActivity implements View.OnClickListener{

    public final static String DB_url="https://basketball514-6f71d.firebaseio.com/";
    static players cur= new players();
    public static ArrayList<Player>dat= cur.getList();


    private TextView mEmail;
    private static EditText mNumber;
    private static EditText mName;
    private Button  mSave;
    private String player;


    private FirebaseAuth firebaseAuth;
    final static Firebase firebase=new Firebase(DB_url);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_player_info);




        firebaseAuth= FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user= firebaseAuth.getCurrentUser();

        mEmail=(TextView)findViewById(R.id.curemail);
        mEmail.setText("Welcome " + user.getEmail());
        mNumber=(EditText)findViewById(R.id.editphone);
        mName=(EditText)findViewById(R.id.editname);
        mSave=(Button)findViewById(R.id.savebut);



        mSave.setOnClickListener(this);



    }

    public void fup(){
        firebase .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                dupdate(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                dupdate(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public static class Monday extends AppCompatActivity implements View.OnClickListener
    {

        private Button iin;
        private ListView listM;

        @Override
        public void onClick(View v) {
            if(v == iin)
            {
                players li=new players();
                ArrayList<Player> ta= li.getList();
                ta.clear();
                ta.add(dat.get(dat.size()-1));

                playerAdapter adapter = new playerAdapter(this,ta);

                listM.setAdapter(adapter);

            }

        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_monday);

            listM=(ListView)findViewById(R.id.listM);
            iin=(Button)findViewById(R.id.mBut);

            iin.setOnClickListener(this);




        }
    }



    public static class Fragment1 extends Fragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            final View view= inflater.inflate(R.layout.fragment1,container,false);

            playerAdapter adapter = new playerAdapter(getActivity(),dat);

            ListView listView = (ListView) view.findViewById(R.id.list);

            listView.setAdapter(adapter);
            return view;
        }}

        private void dupdate(DataSnapshot ds) {


            for (DataSnapshot data : ds.getChildren()) {

                playerrr p = new playerrr();
                p.setphoneNNumber((String)data.child("uuphoneNumber").getValue());
                p.setNName((String)data.child("uuname").getValue());
                Player z = new Player(p.getUUName(), p.getUUPhoneNumber());
                dat.add(z);

            }

        }


    @Override
    public void onClick(View v) {


        if(v == mSave){


            playerrr p= new playerrr();
            p.setphoneNNumber(mNumber.getText().toString());
            p.setNName(mName.getText().toString());

            firebase.child("all players").push().setValue(p);
            this.fup();
            finish();
            startActivity(new Intent(getApplicationContext(),ProfilActivity.class));
        }

    }}

