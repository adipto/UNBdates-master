package com.example.augus.unbdates;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChooseProfilePic extends AppCompatActivity
{

    private ImageView mProfileImage;
    private Button mconfirm;
    private FloatingActionButton chooseProfileImage;
    private String userId;
    private String userGender;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;

    //Method to check usersex for matching
    public void checkUserSex()
    {
        final DatabaseReference mDatabaseReference  = FirebaseDatabase.getInstance().getReference();
        DatabaseReference busReference = mDatabaseReference.child("Users");
        final FirebaseUser mUser  = FirebaseAuth.getInstance().getCurrentUser();
        assert mUser != null;
        final String userid = mUser.getUid();

        busReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    long usercount = ds.getChildrenCount();
                    for(int i = 1; i <= usercount; i++){
                        if(ds.hasChild(userid)){
                            userGender = ds.getKey();
//                            String path = "/Users/" + userGender + "/" + userId ;--------------------------changed this line
                            try
                            {
                                mUserDatabase = FirebaseDatabase.getInstance().getReference();

                            }
                            catch (java.lang.NullPointerException e) {
                                Toast.makeText(ChooseProfilePic.this, userGender, Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_profile_pic);

        mProfileImage = (ImageView) findViewById(R.id.ProPic);
        chooseProfileImage = (FloatingActionButton) findViewById(R.id.addProfilePicBtn);
        mconfirm = (Button) findViewById(R.id.continueBtn);

        //Enabling the action bar, Overriding method outside this scope/class.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Calling checkUserSex method
        checkUserSex();

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

//        String path = "/Users/" + userGender + "/" + userId ;
        try
        {
            mUserDatabase = FirebaseDatabase.getInstance().getReference();

        }
        catch (java.lang.NullPointerException e) {
            Toast.makeText(ChooseProfilePic.this, userGender, Toast.LENGTH_SHORT).show();
        }

        //Firebase Storage

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        mProfileImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseImage();
            }
        });
        chooseProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                uploadImage();
            }
        });

    }

    //Immplementing choose pic and upload pic method
    private void chooseImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                mProfileImage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage()
    {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("profileImages").child(userId).child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(ChooseProfilePic.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            String downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                            Map userInfo = new HashMap();
                            userInfo.put("profileImageUrl", downloadUrl.toString());
                            mUserDatabase.child("Users").child(userId).updateChildren(userInfo);

                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ChooseProfilePic.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }


    public void toHomePage(View view){
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

}