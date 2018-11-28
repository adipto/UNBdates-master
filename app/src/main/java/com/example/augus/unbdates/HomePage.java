package com.example.augus.unbdates;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    public void logOutAction(View view){
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }
    public void gotToChat(View view){
        Intent intent = new Intent(this, ChatPage.class);
        startActivity(intent);
    }
}
