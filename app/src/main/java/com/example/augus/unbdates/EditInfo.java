package com.example.augus.unbdates;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class EditInfo extends AppCompatActivity {

    private FirebaseAuth myAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
    }


    public void confirmChanges(View view) {
        Button Bsubmit =(Button) findViewById(R.id.Submit);
        EditText mBio = (EditText)findViewById(R.id.newBioView);
        EditText mUserName = (EditText)findViewById(R.id.newUsernameView);

        final String name = mUserName.getText().toString();
        final String bio = mBio.getText().toString();

                DatabaseReference userDB;
                String userID= myAuth.getCurrentUser().getUid();
                userDB = FirebaseDatabase.getInstance().getReference("Users").child(userID);

                Map changes = new HashMap();
                changes.put("Name",name);
                changes.put("Bio",bio);
//                    newpost.put("profileImageUrl","default");

                userDB.updateChildren(changes);
                Toast.makeText(EditInfo.this, "Changes confirmed!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditInfo.this, HomePage.class);
                startActivity(intent);

    }


}
