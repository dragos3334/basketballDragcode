package com.example.dragos.basketballdragcode;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mEmail;
    private EditText mPass;
    private Button mRegister;
    private TextView mregis;
    private TextView OrganizerLogIn;
    private String Opassword;

    private ProgressDialog progressDial;

    private FirebaseAuth firebaseauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseauth=FirebaseAuth.getInstance();

        //tracks if the user is already loged in or not
        if(firebaseauth.getCurrentUser()!= null){
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        progressDial=new ProgressDialog(this);



        mRegister=(Button)findViewById(R.id.Lregister);
        mEmail=(EditText)findViewById(R.id.Lemail);
        mPass=(EditText)findViewById(R.id.Lpass);
        mregis=(TextView)findViewById(R.id.LTText);
        OrganizerLogIn=(TextView)findViewById(R.id.OrganizerLogIn);

        OrganizerLogIn.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mregis.setOnClickListener(this);
    }

    private  void userLogin(){
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
        progressDial.setMessage("Processing...");
        progressDial.show();

        firebaseauth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDial.dismiss();

                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(),main2.class));
                        }else{Toast.makeText(LoginActivity.this,"Password or email is incorrect",Toast.LENGTH_SHORT).show();}

                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v == mRegister){
            userLogin();
        }
        if(v == mregis){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
        if(v == OrganizerLogIn){
            request_Organizer_Password();
        }

    }

    private void request_Organizer_Password() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter access code:");

        final EditText input_field = new EditText(this);

        builder.setView(input_field);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Opassword = input_field.getText().toString();
                if(Opassword.equals("b514b")){
                    startActivity(new Intent(LoginActivity.this,main2.class));
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.show();
    }

}
