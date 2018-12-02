package com.example.augus.unbdates;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatPage extends AppCompatActivity {


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener FirebaseAuthStateListener;
    private FirebaseUser user;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);



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
                    Toast.makeText(ChatPage.this, "Sign in succesful", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(ChatPage.this, "Signed out", Toast.LENGTH_LONG).show();
                }
            }

        };
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                loadMessages(dataSnapshot);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //take

        //layout all messages


        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.sendMessageButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textField = (EditText)findViewById(R.id.messageText);
                textField.setText("");
            }
        });
    }

    private void loadMessages(DataSnapshot dataSnapshot) {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.message_Relative_Layout);
        ListView listOfMessages = (ListView)findViewById(R.id.listOfMessages);

        for(DataSnapshot ds : dataSnapshot.getChildren()){
        }
    }

}
