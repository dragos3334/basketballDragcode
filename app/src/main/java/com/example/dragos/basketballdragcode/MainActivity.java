package com.example.dragos.basketballdragcode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mEmail;
    private EditText mPass;
    private Button mRegister;
    private TextView mText;
    private EditText userName;
    private EditText phoneNUmber;
    private Firebase firebase;
    private Player player;
    public final static String DB_url="https://basketball514-6f71d.firebaseio.com/all_Players";


    private ProgressDialog progressDial;

    private FirebaseAuth firebaseauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseauth=FirebaseAuth.getInstance();
        if(firebaseauth.getCurrentUser()!= null){
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(),ProfilActivity.class));
        }

        progressDial=new ProgressDialog(this);

        mRegister=(Button)findViewById(R.id.register);
        mEmail=(EditText)findViewById(R.id.email);
        mPass=(EditText)findViewById(R.id.pass);
        mText=(TextView)findViewById(R.id.TText);
        userName=(EditText) findViewById(R.id.User_Name);
        phoneNUmber=(EditText)findViewById(R.id.Phone_Number);
        firebase= new Firebase(MainActivity.DB_url);


        mRegister.setOnClickListener(this);
        mText.setOnClickListener(this);
    }

    private void registeruser(){

        String email=mEmail.getText().toString().trim();
        String password=mPass.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            //stopping the function executing further
            return;
        }
        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        //if validation is ok
        // we will  first swhow a progrees bar
        progressDial.setMessage("We are now fucking your mom please wait...");
        progressDial.show();

        firebaseauth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //user is succesfully registerded and logedin
                            //we will start thie profile activity here
                            //right now lets just display a toast only
                            createNewUser(task.getResult().getUser());
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfilActivity.class));

                            progressDial.dismiss();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"could not register, please try again",Toast.LENGTH_LONG).show();
                            progressDial.dismiss();
                        }
                    }
                });
    }

    private void createNewUser(FirebaseUser userFromRegistration) {
        String username = userName.getText().toString();
        String phoneNumber = phoneNUmber.getText().toString();
        String userID= userFromRegistration.getUid();

       player = new Player();

        player.setName(username);
        player.setphoneNumber(phoneNumber);
        player.setUserId(userID);

        firebase.push().setValue(player);
    }

    @Override
    public void onClick(View v) {
        if(v == mRegister)
        {
            registeruser();
        }
        if(v == mText)
        {
            startActivity(new Intent(this,LoginActivity.class));
        }
    }
}