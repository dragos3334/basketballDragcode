package com.example.dragos.basketballdragcode;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.braintreepayments.api.BraintreePaymentActivity;
import com.braintreepayments.api.PaymentRequest;
import com.braintreepayments.api.models.PaymentMethodNonce;
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
    private Button sendMessageButton;
    private ListView listParticipants;
    private ArrayList<Player> Players;
    private PlayerAdapter adapter;
    private Firebase firebase = new Firebase("https://basketball514-6f71d.firebaseio.com/");
    private TextView dayOfTheWeek;
    private String price;
    private String time;
    private String location;
    private String weekDay;
    private Bundle extras;
    private final int REQUEST_CODE=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_days);

        Players = new ArrayList<>();
        extras = getIntent().getExtras();

        dayOfTheWeek=(TextView)findViewById(R.id.DWMonday);
        listParticipants = (ListView) findViewById(R.id.listM);
        inButton = (Button) findViewById(R.id.mBut);
        outButton = (Button) findViewById(R.id.outButton);
        sendMessageButton =(Button) findViewById(R.id.send_message);

        inButton.setOnClickListener(this);
        outButton.setOnClickListener(this);
        sendMessageButton.setOnClickListener(this);

        PlayerAdapter adapter = new PlayerAdapter(WeeKDaysActivity.this, Players);
        listParticipants.setAdapter(adapter);

        displayRightDay();

        dayOfTheWeek.setText(weekDay);
        dayOfTheWeek.setOnClickListener(this);




        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot playerSnapshot : dataSnapshot.child(weekDay+"_Players").getChildren()) {
                    String name = (String) playerSnapshot.child("name").getValue();
                    String phoneNumber = (String) playerSnapshot.child("phoneNumber").getValue();
                    String userID = (String) playerSnapshot.child("userId").getValue();
                    String userKey = playerSnapshot.getKey();
                    String email=(String) playerSnapshot.child("email").getValue();
                    String profilPic=(String) playerSnapshot.child("profilPic").getValue();
                    Player player = new Player();
                    player.setPhoneNumber(phoneNumber);
                    player.setUserKey(userKey);
                    player.setEmail(email);
                    player.setProfilPic(profilPic);
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
                            sendMessageButton.setVisibility(View.VISIBLE);
                        }
                            Players.add(player);
                            setAdapter();
                    }
                }
                for (DataSnapshot playerSnapshot : dataSnapshot.child(weekDay+"_Details").getChildren()) {

                    price=(String)playerSnapshot.child("price").getValue();
                    time=(String)playerSnapshot.child("time").getValue();
                    location=(String)playerSnapshot.child("location").getValue();
                    }}
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    @Override
    public void onClick(View v) {  if(v == inButton){
        for(Player player: MainActicity2.Players) {
            if (player.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) && chekifIsNotAlreadyInList(Players,player.getUserId())){
                firebase.child(weekDay+"_Players").push().setValue(player);

                adapter = new PlayerAdapter(WeeKDaysActivity.this,Players);

                listParticipants.setAdapter(adapter);
                inButton.setVisibility(View.GONE);
                outButton.setVisibility(View.VISIBLE);



                PaymentRequest paymentRequest = new PaymentRequest().primaryDescription("Cart")
                        .secondaryDescription("3 Items")
                        .amount("$35")
                        .submitButtonText("Purchase")
                        .clientToken("eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiI1ZjZlOGI1OWI4MTc4YWU4OGI5N2Y5YjgwY2EzYjBlMDYzNjMzYzk4NjNiN2EwZWQzYjBjNmEzMjI3ZDlhNWQwfGNyZWF0ZWRfYXQ9MjAxNi0xMS0wNVQxOTo0NDozNi4zNDExNzE3ODgrMDAwMFx1MDAyNm1lcmNoYW50X2lkPTM0OHBrOWNnZjNiZ3l3MmJcdTAwMjZwdWJsaWNfa2V5PTJuMjQ3ZHY4OWJxOXZtcHIiLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvMzQ4cGs5Y2dmM2JneXcyYi9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJjaGFsbGVuZ2VzIjpbXSwiZW52aXJvbm1lbnQiOiJzYW5kYm94IiwiY2xpZW50QXBpVXJsIjoiaHR0cHM6Ly9hcGkuc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbTo0NDMvbWVyY2hhbnRzLzM0OHBrOWNnZjNiZ3l3MmIvY2xpZW50X2FwaSIsImFzc2V0c1VybCI6Imh0dHBzOi8vYXNzZXRzLmJyYWludHJlZWdhdGV3YXkuY29tIiwiYXV0aFVybCI6Imh0dHBzOi8vYXV0aC52ZW5tby5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tIiwiYW5hbHl0aWNzIjp7InVybCI6Imh0dHBzOi8vY2xpZW50LWFuYWx5dGljcy5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tLzM0OHBrOWNnZjNiZ3l3MmIifSwidGhyZWVEU2VjdXJlRW5hYmxlZCI6dHJ1ZSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFsIjp7ImRpc3BsYXlOYW1lIjoiQWNtZSBXaWRnZXRzLCBMdGQuIChTYW5kYm94KSIsImNsaWVudElkIjpudWxsLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidXNlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodHRwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxvd0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjp0cnVlLCJlbnZpcm9ubWVudCI6Im9mZmxpbmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50SWQiOiJtYXN0ZXJjbGllbnQzIiwiYmlsbGluZ0FncmVlbWVudHNFbmFibGVkIjp0cnVlLCJtZXJjaGFudEFjY291bnRJZCI6ImFjbWV3aWRnZXRzbHRkc2FuZGJveCIsImN1cnJlbmN5SXNvQ29kZSI6IlVTRCJ9LCJjb2luYmFzZUVuYWJsZWQiOmZhbHNlLCJtZXJjaGFudElkIjoiMzQ4cGs5Y2dmM2JneXcyYiIsInZlbm1vIjoib2ZmIn0=");
                startActivityForResult(paymentRequest.getIntent(this), REQUEST_CODE);
            }
        }
    }
    else if(v==outButton){
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
        request_day_details();
    }

    else  if(v== sendMessageButton){
       setSendMessage();
    }
   }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentMethodNonce paymentMethodNonce = data.getParcelableExtra(
                        BraintreePaymentActivity.EXTRA_PAYMENT_METHOD_NONCE
                );
                String nonce = paymentMethodNonce.getNonce();
                // Send the nonce to your server.
            }
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
        adapter = new PlayerAdapter(WeeKDaysActivity.this,Players);
        listParticipants.setAdapter(adapter);
    }

    private void request_day_details() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(weekDay+" Info");
        builder.setMessage("Price: "+price+"\n"+"\n"+"Time: "+time+"\n"+"\n"+"Location: "+location+"\n"+"\n"+"Number Of Participants: "+(Players.size()));
        builder.setPositiveButton("OK",null);
        builder.setNeutralButton("see on map", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q="+location));
                startActivity(intent);
            }
        });

        builder.show();
    }

    private void setSendMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Send message");
        final EditText input_field = new EditText(this);

        final String[]emails= new String[60];
        for(int i = 0; i<= Players.size()-1;i++){
            emails[i]=Players.get(i).getEmail();
        }


        builder.setView(input_field);
        builder.setPositiveButton("send email", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String message = input_field.getText().toString();

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, emails);
                intent.putExtra(Intent.EXTRA_SUBJECT, "empire514");
                intent.putExtra(Intent.EXTRA_TEXT,message);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        builder.setNeutralButton("send sms", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String message = input_field.getText().toString();

                String phonenumbers="";
                for(Player player:Players){

                    if(phonenumbers.equals("")){
                        phonenumbers= player.getPhoneNumber();
                    }
                    phonenumbers= phonenumbers+";"+player.getPhoneNumber();
                }

                Intent smsIntent = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+phonenumbers));
                smsIntent.putExtra("sms_body", message);
                startActivity(smsIntent);
            }
        });
        builder.show();
    }

    private void displayRightDay(){
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

    }
}