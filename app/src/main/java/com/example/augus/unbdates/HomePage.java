package com.example.augus.unbdates;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ImageView profilePic = (ImageView)findViewById(R.id.profilePic);
//        if(user!=null FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()
        profilePic.setImageURI(Uri.parse("@drawable/demo.jpg"));

        TextView bioTextView = (TextView) findViewById(R.id.bioInfo);
        bioTextView.setText("Live life to the fullest\nFreddy Born and Raised\nXOXO\nUNB class of 2020");

    }

    public void logOutAction(View view){
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

}
