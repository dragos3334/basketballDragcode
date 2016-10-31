package com.example.dragos.basketballdragcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActicity2 extends AppCompatActivity {

    private ListView lv;
    public static ArrayList<Player> Players;
    private Firebase firebase = new Firebase(MainActivity.DB_url);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv=(ListView)findViewById(R.id.list2);

        final String []WeekDays= {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(MainActicity2.this,android.R.layout.simple_list_item_1,WeekDays);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActicity2.this, WeeKDaysActivity.class);
                intent.putExtra("POSITION_LIST",position);

                startActivity(intent);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (ChekForOrganizer()) {
                    Intent i = new Intent(MainActicity2.this, DayOrganizerActivity.class);
                    i.putExtra("POSITION", position);
                    startActivity(i);
                } else {
                    Intent intent = new Intent(MainActicity2.this, WeeKDaysActivity.class);
                    intent.putExtra("POSITION_LIST",position);

                    startActivity(intent);
                }

                return true;
            }
        });

        Players = new ArrayList<>();

        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot playerSnapshot : dataSnapshot.getChildren()) {
                    String name = (String) playerSnapshot.child("uname").getValue();
                    String phoneNumber = (String) playerSnapshot.child("uphoneNumber").getValue();
                    String userID = (String) playerSnapshot.child("userId").getValue();
                    String profilPic=(String) playerSnapshot.child("profifPic").getValue();
                    Player player = new Player();
                    player.setphoneNumber(phoneNumber);
                    player.setName(name);
                    player.setProfifPic(profilPic);
                    player.setUserId(userID);
                    Players.add(player);

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.all_players) {
            startActivity(new Intent(MainActicity2.this, PlayersListActivity.class));
            return true;
        }else if(id==R.id.logout){
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(MainActicity2.this,LoginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
    private boolean ChekForOrganizer(){
        return FirebaseAuth.getInstance().getCurrentUser() == null;
    }

}
