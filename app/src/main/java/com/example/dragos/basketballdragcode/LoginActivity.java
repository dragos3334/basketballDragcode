package com.example.dragos.basketballdragcode;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
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

    private EditText emailET;
    private EditText passwordET;
    private Button loginButton;
    private TextView registerButton;
    private TextView organizerLogInButton;
    private TextView forgotPassword;
    private ProgressDialog progressDial;
    private FirebaseAuth firebaseauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseauth=FirebaseAuth.getInstance();

        if(firebaseauth.getCurrentUser()!= null){
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        progressDial=new ProgressDialog(this);
        loginButton =(Button)findViewById(R.id.Lregister);
        emailET =(EditText)findViewById(R.id.Lemail);
        passwordET =(EditText)findViewById(R.id.Lpass);
        registerButton =(TextView)findViewById(R.id.LTText);
        organizerLogInButton =(TextView)findViewById(R.id.OrganizerLogIn);
        forgotPassword=(TextView)findViewById(R.id.forget_password);

        forgotPassword.setOnClickListener(this);
        organizerLogInButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    private  void userLogin(){
        String email= emailET.getText().toString().trim();
        String password= passwordET.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDial.setMessage("Processing...");
        progressDial.show();

        firebaseauth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDial.dismiss();

                        if(task.isSuccessful()){

                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActicity2.class));
                        }else{Toast.makeText(LoginActivity.this,"Password or email is incorrect",Toast.LENGTH_SHORT).show();}

                    }});}

    @Override
    public void onClick(View v) {
        if(v == loginButton){
            userLogin();
        }
        if(v == registerButton){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
        if(v == organizerLogInButton){
            request_Organizer_Password();
        }
        if(v == forgotPassword){
            if(emailET.getText().toString().equals("")){
            emailET.getBackground().setColorFilter(ContextCompat.getColor(LoginActivity.this,R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
            Toast.makeText(LoginActivity.this, "Enter an email adress", Toast.LENGTH_LONG).show();}
            else {
            resetEmailPassword();}
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
               String password = input_field.getText().toString();
                if(password.equals("b514b")){
                    startActivity(new Intent(LoginActivity.this,MainActicity2.class));
                }}});

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.show();
    }

    private void resetEmailPassword() {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(emailET.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("An email, to help you reset your passsword, has been sent to this adress: "+ emailET.getText().toString());
                            builder.show();
                        }
                        else {Toast.makeText(LoginActivity.this, "Invalid email adress", Toast.LENGTH_LONG).show();}
                    }});}

}
