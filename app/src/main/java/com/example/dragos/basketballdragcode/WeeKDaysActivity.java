package com.example.dragos.basketballdragcode;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * Created by Dragos on 2016-10-17.
 */

public class WeeKDaysActivity extends AppCompatActivity implements View.OnClickListener
{

    private Button inButton;
    private Button outButton;
    private ListView listParticipants;
    private ArrayList<Player> Players;
    private playerAdapter adapter;
    private Firebase firebase = new Firebase("https://basketball514-6f71d.firebaseio.com/");
    private TextView dayOfTheWeek;
    private String price;
    private String time;
    private String location;
    private String weekDay;
    private Bundle extras;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_days);

        Players = new ArrayList<>();
        dayOfTheWeek=(TextView)findViewById(R.id.DWMonday);
        extras = getIntent().getExtras();

        switch (extras.getInt("POSITION_LIST")){

            case 0:
                weekDay="Monday";
                break;
            case 1:
                weekDay="Tuesday";
                break;
            case 2:
                weekDay="Wednesday";
                break;
            case 3:
                weekDay="Thursday";
                break;
            case 4:
                weekDay="Friday";
                break;
            case 5:
                weekDay="Saturday";
                break;
            case 6:
                weekDay="Sunday";
                break;
        }

        dayOfTheWeek.setText(weekDay);
        dayOfTheWeek.setOnClickListener(this);


        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot playerSnapshot : dataSnapshot.child(weekDay+"_Players").getChildren()) {
                    String name = (String) playerSnapshot.child("uname").getValue();
                    String phoneNumber = (String) playerSnapshot.child("uphoneNumber").getValue();
                    String userID = (String) playerSnapshot.child("userId").getValue();
                    String userKey = (String) playerSnapshot.getKey();
                    String profilPic=(String) playerSnapshot.child("profifPic").getValue();
                    Player player = new Player();
                    player.setphoneNumber(phoneNumber);
                    player.setUserKey(userKey);
                    player.setProfifPic(profilPic);
                    player.setName(name);
                    player.setUserId(userID);

                    if (chekifIsNotAlreadyInList(Players, player.getUserId())) {
                        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                            if (player.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                inButton.setVisibility(View.GONE);
                                outButton.setVisibility(View.VISIBLE);
                            }}else {
                            inButton.setVisibility(View.GONE);
                            outButton.setVisibility(View.GONE);
                        }
                            Players.add(player);
                            setAdapter();
                    }
                }
                for (DataSnapshot playerSnapshot : dataSnapshot.child(weekDay+"_Details").getChildren()) {

                    price=(String)playerSnapshot.child("price").getValue();
                    time=(String)playerSnapshot.child("time").getValue();
                    location=(String)playerSnapshot.child("location").getValue();
                    }

                }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        listParticipants = (ListView) findViewById(R.id.listM);
        inButton = (Button) findViewById(R.id.mBut);
        outButton = (Button) findViewById(R.id.outButton);

        inButton.setOnClickListener(this);
        outButton.setOnClickListener(this);

        playerAdapter adapter = new playerAdapter(WeeKDaysActivity.this, Players);
        listParticipants.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {  if(v == inButton){
        for(Player player: main2.Players) {
            if (player.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) && chekifIsNotAlreadyInList(Players,player.getUserId())){
                firebase.child(weekDay+"_Players").push().setValue(player);

                adapter = new playerAdapter(WeeKDaysActivity.this,Players);

                listParticipants.setAdapter(adapter);
                inButton.setVisibility(View.GONE);
                outButton.setVisibility(View.VISIBLE);

            }
        }
    } else if(v==outButton){
        Player descard= null;

        for(Player player:Players){
            if(player.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                descard=player;
            }}
        if(descard != null) {
            firebase.child(weekDay+"_Players").child(descard.getUserKey()).removeValue();
            Players.remove(descard);

        setAdapter();

            inButton.setVisibility(View.VISIBLE);
            outButton.setVisibility(View.GONE);
    }}
        else  if(v==dayOfTheWeek){
        request_Organizer_Password();
    }
   }

    public boolean chekifIsNotAlreadyInList(ArrayList<Player> list, String id){
    for(Player player:list){
        if(player.getUserId().equals(id)){
            return false;
        }
    }
    return true;
}

    void setAdapter(){
        adapter = new playerAdapter(WeeKDaysActivity.this,Players);
        listParticipants.setAdapter(adapter);
    }

    private void request_Organizer_Password() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(weekDay+" Info");
        builder.setMessage("Price: "+price+"\n"+"\n"+"Time: "+time+"\n"+"\n"+"Location: "+location+"\n"+"\n"+"Number Of Participants: "+(Players.size()));
        builder.setPositiveButton("OK",null);


        builder.show();
    }
}