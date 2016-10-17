package com.example.dragos.basketballdragcode;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * Created by Dragos on 2016-10-17.
 */

public class MondayActivity extends AppCompatActivity implements View.OnClickListener
{

    private Button inButton;
    private Button outButton;
    private ListView listParticipants;
    private ArrayList<Player> Players;
    private Firebase firebase = new Firebase("https://basketball514-6f71d.firebaseio.com/Monday_Players");
    private  String userID= FirebaseAuth.getInstance().getCurrentUser().getUid();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monday);

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

        listParticipants = (ListView) findViewById(R.id.listM);
        inButton = (Button) findViewById(R.id.mBut);
        outButton=(Button) findViewById(R.id.outButton);



        inButton.setOnClickListener(this);
        outButton.setOnClickListener(this);

        playerAdapter adapter = new playerAdapter(MondayActivity.this,Players);

        listParticipants.setAdapter(adapter);

    }

    public boolean chekifIsInList(ArrayList<Player> list, String id){
    for(Player player:list){
        if(player.getUserId().equals(id)){
            return false;
        }
    }
    return true;
}

    @Override
    public void onClick(View v) {
        if(v == inButton){
            for(Player player: ProfilActivity.Players) {
                if (player.getUserId().equals(userID) && chekifIsInList(Players,player.getUserId())){
                    Players.add(player);
                    firebase.push().setValue(player);

                    playerAdapter adapter = new playerAdapter(MondayActivity.this,Players);

                    listParticipants.setAdapter(adapter);
                }}}
        else if(v==outButton){
            if(Players != null) {
            for(Player player:Players){

                if(player.getUserId().equals(userID)){
                    Players.remove(player);

                        playerAdapter adapter = new playerAdapter(MondayActivity.this, Players);

                        listParticipants.setAdapter(adapter);
                        firebase.child(player.getUserKey()).removeValue();
                    }



                }
            }
        }}}

