package com.example.dragos.basketballdragcode.WeekDays;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.dragos.basketballdragcode.Player;
import com.example.dragos.basketballdragcode.ProfilActivity;
import com.example.dragos.basketballdragcode.R;
import com.example.dragos.basketballdragcode.playerAdapter;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ThursdayActivity extends AppCompatActivity implements View.OnClickListener
{

    private Button inButton;
    private Button outButton;
    private ListView listParticipants;
    private ArrayList<Player> Players;
    private playerAdapter adapter;
    private Firebase firebase = new Firebase("https://basketball514-6f71d.firebaseio.com/Thursday_Players");
    private  String userID= FirebaseAuth.getInstance().getCurrentUser().getUid();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thursday);

        Players = new ArrayList<>();

        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot playerSnapshot : dataSnapshot.getChildren()) {
                    String name = (String) playerSnapshot.child("uname").getValue();
                    String phoneNumber = (String) playerSnapshot.child("uphoneNumber").getValue();
                    String userID = (String) playerSnapshot.child("userId").getValue();
                    String userKey = (String) playerSnapshot.getKey();
                    Player player = new Player();
                    player.setphoneNumber(phoneNumber);
                    player.setUserKey(userKey);
                    player.setName(name);
                    player.setUserId(userID);

                    if (chekifIsAlreadyInList(Players, player.getUserId())) {
                        Players.add(player);
                        playerAdapter adapter = new playerAdapter(ThursdayActivity.this, Players);
                        listParticipants.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        listParticipants = (ListView) findViewById(R.id.listTH);
        inButton = (Button) findViewById(R.id.THbut);
        outButton = (Button) findViewById(R.id.THoutButton);

        inButton.setOnClickListener(this);
        outButton.setOnClickListener(this);

        playerAdapter adapter = new playerAdapter(ThursdayActivity.this, Players);
        listParticipants.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {  if(v == inButton){
        for(Player player: ProfilActivity.Players) {
            if (player.getUserId().equals(userID) && chekifIsAlreadyInList(Players,player.getUserId())){
                firebase.push().setValue(player);

                adapter = new playerAdapter(ThursdayActivity.this,Players);

                listParticipants.setAdapter(adapter);
            }
        }
    } else if(v==outButton){

        for(Player player:Players){
            if(player.getUserId().equals(userID)){
                firebase.child(player.getUserKey()).removeValue();
                Players.remove(player);

                adapter = new playerAdapter(ThursdayActivity.this,Players);
                listParticipants.setAdapter(adapter);

            }
        }
    }}

    public boolean chekifIsAlreadyInList(ArrayList<Player> list, String id){
        for(Player player:list){
            if(player.getUserId().equals(id)){
                return false;
            }
        }
        return true;
    }
}
