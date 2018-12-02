package com.example.augus.unbdates;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.augus.unbdates.Matches.MatchesActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePage extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener FirebaseAuthStateListener;
    private FirebaseUser user;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        user = mAuth.getCurrentUser();
        userID = user.getUid();

        FirebaseAuthStateListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                {
//                    Toast.makeText(HomePage.this, "Sign in successful", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(HomePage.this, "Signed out", Toast.LENGTH_LONG).show();
                }
            }

        };
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loadProfile(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

    public void goToEdit(View view){
        Intent intent = new Intent(HomePage.this, EditInfo.class);
        startActivity(intent);
    }

    public void loadProfile(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            User myUser = new User();
            try {
                myUser.setBio(ds.child(userID).getValue(User.class).getBio());
                myUser.setProfileImageUrl(ds.child(userID).getValue(User.class).getProfileImageUrl());
                myUser.setName(ds.child(userID).getValue(User.class).getName());

                TextView nameView = (TextView) findViewById(R.id.nameView);
                nameView.setText(myUser.getName());

                TextView bioView = (TextView) findViewById(R.id.bioInfo);
                bioView.setText(myUser.getBio());

//                ImageView profileImageView = (ImageView) findViewById(R.id.profilePicView);
                Glide.with(HomePage.this).load(myUser.getProfileImageUrl()).into((ImageView)findViewById(R.id.profilePicView));
            } catch (NullPointerException e) {
                Toast.makeText(HomePage.this, "no object found", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(FirebaseAuthStateListener);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        mAuth.addAuthStateListener(FirebaseAuthStateListener);
    }


}
