



package com.example.dragos.basketballdragcode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmail;
    private EditText mPass;
    private Button mRegister;
    private TextView mText;
    private EditText userName;
    private EditText phoneNUmber;
    private Firebase firebase;
    private Player player;
    private CircleImageView profilPicture;
    public final static String DB_url = "https://basketball514-6f71d.firebaseio.com/all_Players";
    private Bitmap bmp;
    private StorageReference storageRef;
    private ImageView chekButton;
    private ImageView rotateButton;
    private CircleImageView editIcon;
    private String downloadUri;
    private Picasso picasso;
    private ProgressBar progressBar;


    private ProgressDialog progressDial;

    private FirebaseAuth firebaseauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://basketball514-6f71d.appspot.com/");


        firebaseauth = FirebaseAuth.getInstance();
        if (firebaseauth.getCurrentUser() != null) {
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(), MainActicity2.class));
        }

        progressDial = new ProgressDialog(this);

        mRegister = (Button) findViewById(R.id.register);
        mEmail = (EditText) findViewById(R.id.email);
        mPass = (EditText) findViewById(R.id.pass);
        mText = (TextView) findViewById(R.id.TText);
        userName = (EditText) findViewById(R.id.User_Name);
        phoneNUmber = (EditText) findViewById(R.id.Phone_Number);
        firebase = new Firebase(MainActivity.DB_url);
        profilPicture = (CircleImageView) findViewById(R.id.profilPic);
        rotateButton=(ImageView)findViewById(R.id.arrow_rotate);
        chekButton=(ImageView)findViewById(R.id.chek_button);
        editIcon=(CircleImageView)findViewById(R.id.editPic);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        picasso= new Picasso.Builder(this).listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        }).build();



        rotateButton.setOnClickListener(this);
        chekButton.setOnClickListener(this);
        profilPicture.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mText.setOnClickListener(this);
    }


    private void registeruser() {

        String email = mEmail.getText().toString().trim();
        String password = mPass.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            //stopping the function executing further
            return;
        }
        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        //if validation is ok
        // we will  first swhow a progrees bar
        progressDial.setMessage("Processing...");
        progressDial.show();

        firebaseauth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //user is succesfully registerded and logedin
                            //we will start thie profile activity here
                            //right now lets just display a toast only
                            createNewUser(task.getResult().getUser());


                        } else {
                            Toast.makeText(MainActivity.this, "could not register, please try again", Toast.LENGTH_LONG).show();
                            progressDial.dismiss();
                        }
                    }
                });
    }

    private void createNewUser(FirebaseUser userFromRegistration) {
        String username = userName.getText().toString();
        String phoneNumber = phoneNUmber.getText().toString();
        String userID = userFromRegistration.getUid();


        player = new Player();

        player.setName(username);
        player.setphoneNumber(phoneNumber.substring(0,3)+"-"+phoneNumber.substring(3,6)+"-"+phoneNumber.substring(6,10));
        player.setUserId(userID);




        profilPicture.buildDrawingCache();
        bmp = profilPicture.getDrawingCache();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        StorageReference imagesRef = storageRef.child("Profile_Pics/" + username);
        imagesRef.putBytes(data);

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                downloadUri =downloadUrl.toString();
                player.setProfifPic(downloadUri);
                firebase.push().setValue(player);

                firebase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        startActivity(new Intent(getApplicationContext(), MainActicity2.class));
                        progressDial.dismiss();
                        finish();
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });
    }



    @Override
    public void onClick(View v) {
        if (v == mRegister) {

            if(userName.getText().toString().equals("")){
                userName.getBackground().setColorFilter(ContextCompat.getColor(MainActivity.this,R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
            }else if(phoneNUmber.getText().toString().equals("")){
                phoneNUmber.getBackground().setColorFilter(ContextCompat.getColor(MainActivity.this,R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
            }else if(phoneNUmber.getText().toString().length()<10){
                Toast.makeText(MainActivity.this, "10 digits are necessary for phone number", Toast.LENGTH_LONG).show();
                phoneNUmber.getBackground().setColorFilter(ContextCompat.getColor(MainActivity.this,R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
            }else if(mPass.getText().toString().length()<7){
                Toast.makeText(MainActivity.this, "7 digits minimum for password", Toast.LENGTH_LONG).show();
                mPass.getBackground().setColorFilter(ContextCompat.getColor(MainActivity.this,R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
            }
            else {registeruser();}

        }
        if (v == mText) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        if (v == profilPicture) {
            if(isOnLine()){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);}
            else {
                Toast.makeText(MainActivity.this, "Network isn't available", Toast.LENGTH_LONG).show();
            }

        }
        if (v == rotateButton) {


            Matrix matrix = new Matrix();

            matrix.postRotate(90);


            Bitmap rotatedBitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
            bmp = rotatedBitmap;
            profilPicture.setImageBitmap(bmp);

        }
        if (v == chekButton) {
            rotateButton.setVisibility(View.GONE);
            chekButton.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            final Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            progressBar.setVisibility(View.VISIBLE);
            profilPicture.setVisibility(View.INVISIBLE);
            editIcon.setVisibility(View.INVISIBLE);
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            final StorageReference imagesRef = storageRef.child("Profile_Pic");
            imagesRef.putFile(selectedImage);

            UploadTask uploadTask = imagesRef.putFile(selectedImage);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                   picasso.load(downloadUrl).noPlaceholder().centerCrop().fit()
                            .into(profilPicture,new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    profilPicture.setVisibility(View.VISIBLE);
                                    profilPicture.buildDrawingCache();
                                    bmp = profilPicture.getDrawingCache();
                                    imagesRef.delete();
                                    rotateButton.setVisibility(View.VISIBLE);
                                    chekButton.setVisibility(View.VISIBLE);
                                    editIcon.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError() {
                                    try {
                                        bmp= getBitmapFromUri(selectedImage);
                                        rotateButton.setVisibility(View.VISIBLE);
                                        imagesRef.delete();
                                        chekButton.setVisibility(View.VISIBLE);
                                        editIcon.setVisibility(View.GONE);
                                        progressBar.setVisibility(View.GONE);
                                        profilPicture.setVisibility(View.VISIBLE);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    profilPicture.setImageBitmap(bmp);

                                }
                            });



                }
            });


        }
    }


    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    protected boolean isOnLine(){
        ConnectivityManager cm =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo=cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}

