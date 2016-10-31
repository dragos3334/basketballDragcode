package com.example.dragos.basketballdragcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

public class DayOrganizerActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText price;
    private EditText location;
    private EditText time;
    private String setPrice;
    private String setLocation;
    private String setTime;
    private Button saveChanges;
    private DayDetails dayOfTheWeek;
    private Firebase parentFirebase= new Firebase("https://basketball514-6f71d.firebaseio.com/");

    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_organizer);


        price=(EditText)findViewById(R.id.ETprice);
        location=(EditText)findViewById(R.id.ETlocation);
        time=(EditText)findViewById(R.id.ETtime);
        saveChanges=(Button) findViewById(R.id.savChanges);

        saveChanges.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if(v == saveChanges){
            setPrice=price.getText().toString();
            setLocation=location.getText().toString();
            setTime=time.getText().toString();
           extras = getIntent().getExtras();

            dayOfTheWeek = new DayDetails();
            dayOfTheWeek.setLocation(setLocation);
            dayOfTheWeek.setPrice(setPrice);
            dayOfTheWeek.setTime(setTime);
            MatchDays(extras.getInt("POSITION"));
           Firebase firebase = new Firebase("https://basketball514-6f71d.firebaseio.com/"+ dayOfTheWeek.getDayOfTheWeek()+"_Details");


            parentFirebase.child(dayOfTheWeek.getDayOfTheWeek()+"_Details").removeValue();
            firebase.push().setValue(dayOfTheWeek);
            startActivity(new Intent(getApplicationContext(),main2.class));
        }

    }
    private void MatchDays(int position) {
        switch (position){


            case 0:
                dayOfTheWeek.setDayOfTheWeek("Monday");
                break;
            case 1:
                dayOfTheWeek.setDayOfTheWeek("Tuesday");
                break;
            case 2:
                dayOfTheWeek.setDayOfTheWeek("Wednesday");
                break;
            case 3:
                dayOfTheWeek.setDayOfTheWeek("Thursday");
                break;
            case 4:
                dayOfTheWeek.setDayOfTheWeek("Friday");
                break;
            case 5:
                dayOfTheWeek.setDayOfTheWeek("Saturday");
                break;
            case 6:
                dayOfTheWeek.setDayOfTheWeek("Sunday");
                break;

        }
    }

}
