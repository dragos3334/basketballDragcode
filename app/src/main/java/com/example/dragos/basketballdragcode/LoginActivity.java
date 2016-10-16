package com.example.dragos.basketballdragcode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
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
    private TextView mText;
    private TextView mregis;

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
            startActivity(new Intent(getApplicationContext(),SetPlayerInfoActivity.class));
        }

        progressDial=new ProgressDialog(this);



        mRegister=(Button)findViewById(R.id.Lregister);
        mEmail=(EditText)findViewById(R.id.Lemail);
        mPass=(EditText)findViewById(R.id.Lpass);
        mText=(TextView)findViewById(R.id.LTText);
        mregis=(TextView)findViewById(R.id.LTText);

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
        progressDial.setMessage("We are now fucking your mom please wait...");
        progressDial.show();

        firebaseauth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDial.dismiss();

                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(),SetPlayerInfoActivity.class));
                        }

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

    }
}
