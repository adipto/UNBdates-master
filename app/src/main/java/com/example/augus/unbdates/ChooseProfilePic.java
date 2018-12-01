package com.example.augus.unbdates;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChooseProfilePic extends AppCompatActivity
{

    private ImageView mProfileImage;
    private Button mconfirm;
    private String userId;
    private String userGender;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;

    //New DATABASE IMPLEMENTATION
    final FirebaseUser mUser  = FirebaseAuth.getInstance().getCurrentUser();
    final String userid = mUser.getUid();
    final DatabaseReference mDatabaseReference  = FirebaseDatabase.getInstance().getReference();
    DatabaseReference busReference = mDatabaseReference.child("Users").child(userid);


/*
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
                            String path = "/Users/" + userGender + "/" + userId ;
                            try
                            {
                                mUserDatabase = FirebaseDatabase.getInstance().getReference(path);

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

    */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_profile_pic);

        mProfileImage = (ImageView) findViewById(R.id.ProPic);
        mconfirm = (Button) findViewById(R.id.ChangePic);

        //Enabling the action bar, Overriding method outside this scope/class.
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //New DATABASE IMPLEMENTATION

/*
        // Calling checkUserSex method
        checkUserSex();

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        String path = "/Users/" + userGender + "/" + userId ;
        try
        {
            mUserDatabase = FirebaseDatabase.getInstance().getReference(path);

        }
        catch (java.lang.NullPointerException e) {
            Toast.makeText(ChooseProfilePic.this, userGender, Toast.LENGTH_SHORT).show();
        }


        ValueEventListener EventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                //Nullpointer exception
                userGender = map.get("Gender").toString();
                Toast.makeText(ChooseProfilePic.this, userGender, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        mUserDatabase.addValueEventListener(EventListener);


*/



        //Firebase Sotrage

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


        mconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                uploadImage();

                Intent intent = new Intent(ChooseProfilePic.this,MatchPage.class);
                startActivity(intent);
                finish();
                return;

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

            final StorageReference filepath = FirebaseStorage.getInstance().getReference().child("profileImages").child(userid);
            Bitmap bitmap = null;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = filepath.putBytes(data);


            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful())
                    {
                        Uri downloadUri = task.getResult();
                        Map userInfo = new HashMap();
                        userInfo.put("profileImageUrl", downloadUri.toString());
                        busReference.updateChildren(userInfo);
                        finish();
                        return;

                    }
                    else
                        {
                            // Handle failures
                        }
                }
            });


        }
        else
            {
                finish();
            }
    }

/*
    //Implementing the back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
            int id = item.getItemId();

            if ( id == android.R.id.home )
            {
                finish();
                return true;
            }

        return super.onOptionsItemSelected(item);

    }
*/
}
