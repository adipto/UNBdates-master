package com.example.augus.unbdates;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class HomePage extends AppCompatActivity {

    private FirebaseDatabase myDB;
    private DatabaseReference dbRef;
    private FirebaseAuth mAuth;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        myDB = FirebaseDatabase.getInstance();
        dbRef = myDB.getReference();
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loadProfile(dataSnapshot);
//                loadBio(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void logOutAction(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }
    public void gotToChat(View view){
        Intent intent = new Intent(this, ChatPage.class);
        startActivity(intent);
    }

    public void goToMatch(View view){
        Intent intent  = new Intent(this, MatchPage.class);
        startActivity(intent);
    }

    public void change(View view){
        Map userInfo = new HashMap();
        userInfo.put("test1", "test1");

        FirebaseDatabase.getInstance().getReference().child("Users").child("Male").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(userInfo);
    }



    public void loadProfile(DataSnapshot ds){
            for(DataSnapshot d : ds.getChildren()){
                User myUser = new User();
                //myUser.setProfileImageUrl(d.child("Users").child(userId).getValue(User.class).getProfileImageUrl()); COMENT BACK IN AFTER TESTING!
                myUser.setBio(d.child("Users").child(userId).getValue(User.class).getBio());
                TextView bioTextView = (TextView) findViewById(R.id.bioInfo);
                bioTextView.setText(myUser.getBio());
            }


        //actually load image onto empty image view
//        ImageView profilePic = (ImageView)findViewById(R.id.profilePic);
//        try {
//            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse("com.google.android.gms.tasks.zzu@27b86b5"));
//            profilePic.setImageBitmap(bitmap);
//        }catch(Exception e){
//            Toast.makeText(HomePage.this, e.toString(), Toast.LENGTH_LONG).show();
//        }
    }


}
