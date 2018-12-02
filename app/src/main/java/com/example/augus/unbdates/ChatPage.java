package com.example.augus.unbdates;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;

public class ChatPage extends AppCompatActivity implements ChatRecycler.ItemClickListener {


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
//
//        mAuth = FirebaseAuth.getInstance();
//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        myRef = mFirebaseDatabase.getReference();
//        user = mAuth.getCurrentUser();
//        userID = user.getUid();
        //take

        //layout all messages

        ChatRecycler adapter;

                // data to populate the RecyclerView with
                ArrayList<String> chats = new ArrayList<>();
                chats.add("Hey");
                chats.add("How's it going?");

                // set up the RecyclerView
                RecyclerView recyclerView = findViewById(R.id.messageList);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                adapter = new ChatRecycler(this, chats);
//                adapter.setClickListener(this); option for delete message
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onItemClick(View view, int position) {
            }

}
