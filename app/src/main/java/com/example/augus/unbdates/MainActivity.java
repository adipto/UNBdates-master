package com.example.augus.unbdates;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.augus.unbdates.MESSAGE";//------------augus-----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void openChat(View view){
        Intent intent = new Intent(this, DisplayChatActivity.class);
        startActivity(intent);
    }
    public void openMatch(View view){
        Intent intent = new Intent(this, DisplayMatchActivity.class);
        startActivity(intent);
    }
}
