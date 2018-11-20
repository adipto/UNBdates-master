package com.example.augus.unbdates;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpPage extends AppCompatActivity {

    //Declaring Variables for the buttons and EditText for SignUpPage xml
    private Button Bsubmit,BBack;
    private EditText mUserName,mUserEmail,mUserPassword,mBio;

    //Declaring Variables for Spinners
    Spinner mGenderSipinner,mCampusSpinner,mInterestedInSpinner,mAgeSpinner;
    ArrayAdapter<CharSequence> GenderAdapter;
    ArrayAdapter<CharSequence> CampusAdapter;
    ArrayAdapter<CharSequence> InterestedInAdapter;
    ArrayAdapter<CharSequence> AgeAdapter;
    //Firebase variable
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener FirebaseAuthStateListener;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        //Enabling the action bar, Overriding method outside this scope/class.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mAuth = FirebaseAuth.getInstance();
        FirebaseAuthStateListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null)
                {
                    Intent intent = new Intent(SignUpPage.this,ChooseProfilePic.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }

        };



        //Initializing the View Id
        Bsubmit =(Button) findViewById(R.id.Submit);
        mUserName = (EditText)findViewById(R.id.UserName);
        mUserEmail = (EditText)findViewById(R.id.UserEmail);
        mUserPassword = (EditText)findViewById(R.id.UserPassword);
        mBio = (EditText)findViewById(R.id.UserBio);
        mGenderSipinner = (Spinner) findViewById(R.id.GenderSpinner);
        mCampusSpinner = (Spinner) findViewById(R.id.CampusSpinner);
        mInterestedInSpinner = (Spinner) findViewById(R.id.InterestedInSpinner);
        mAgeSpinner = (Spinner) findViewById(R.id.AgeSpinner);

        //Initializing Array adapters
        GenderAdapter = ArrayAdapter.createFromResource(this,R.array.GenderArray, android.R.layout.simple_spinner_item);
        CampusAdapter = ArrayAdapter.createFromResource(this,R.array.CampusArray, android.R.layout.simple_spinner_item);
        InterestedInAdapter = ArrayAdapter.createFromResource(this,R.array.InterestedInArray, android.R.layout.simple_spinner_item);
        AgeAdapter = ArrayAdapter.createFromResource(this,R.array.AgeArray, android.R.layout.simple_spinner_item);


        // Specifying the layout to use when the list of choices appears
        GenderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CampusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        InterestedInAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        // Applying the adapter to the spinner
        mGenderSipinner.setAdapter(GenderAdapter);
        mCampusSpinner.setAdapter(CampusAdapter);
        mInterestedInSpinner.setAdapter(InterestedInAdapter);
        mAgeSpinner.setAdapter(AgeAdapter);

        //Displaying selection Storing data in firebase
        mGenderSipinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                Toast.makeText(SignUpPage.this,adapterView.getItemAtPosition(i)+" is selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        mCampusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                Toast.makeText(SignUpPage.this,adapterView.getItemAtPosition(i)+" is selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
       mInterestedInSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                Toast.makeText(SignUpPage.this,adapterView.getItemAtPosition(i)+" is selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
        mAgeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                Toast.makeText(SignUpPage.this,adapterView.getItemAtPosition(i)+" is selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        /*

        // Implemanting the Back Button
        BBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpPage.this,LoginPage.class);
                startActivity(intent);
                finish();
                return;

            }
        });
*/

        //Setting the onClick listener for buttons for Submit xml

        Bsubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               final String name = mUserName.getText().toString();
               final String email = mUserEmail.getText().toString();
               final String password = mUserPassword.getText().toString();
               final String bio = mBio.getText().toString();
               final String gender = mGenderSipinner.getSelectedItem().toString();
               final String campus = mCampusSpinner.getSelectedItem().toString();
               final String interest = mInterestedInSpinner.getSelectedItem().toString();
               final String age = mAgeSpinner.getSelectedItem().toString();


               mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUpPage.this, new OnCompleteListener<AuthResult>()
               {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task)
                   {
                       if(!task.isSuccessful())
                       {
                           Toast.makeText(SignUpPage.this, "Sign Up Error", Toast.LENGTH_SHORT).show();
                       }
                       else
                       {
                           //Storing information to the database
                           String user_id = mAuth.getCurrentUser().getUid();
                           DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(gender).child(user_id);

                           Map newpost = new HashMap();
                           newpost.put("Name",name);
                           newpost.put("Gender",gender);
                           newpost.put("Campus",campus);
                           newpost.put("InterestedIn",interest);
                           newpost.put("Age",age);
                           newpost.put("Bio",bio);
                           //newpost.put("ProfilePicURI","");

                         current_user_db.setValue(newpost);

                           Intent intent = new Intent(SignUpPage.this,ChooseProfilePic.class);
                           startActivity(intent);
                           finish();
                           return;

                       }
                   }
               });
            }
        });
    }

    //Implementing the back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == android.R.id.home)
        {
            Intent intent = new Intent(SignUpPage.this,LoginPage.class);
            startActivity(intent);
            finish();

        }

        return super.onOptionsItemSelected(item);
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



