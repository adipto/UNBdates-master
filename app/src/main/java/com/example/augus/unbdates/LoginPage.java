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
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {


    //Declaring Variables for the buttons and EditText for LogIn xml
    private Button BLogIn,BSignUp;
    private EditText mEmail,mPassword;

    //Firebase variable
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener FirebaseAuthStateListener;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        //Authentication
        mAuth = FirebaseAuth.getInstance();
        FirebaseAuthStateListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if(user != null)
                        {
                            Intent intent = new Intent(LoginPage.this,ChooseProfilePic.class);
                            startActivity(intent);
                            finish();
                            return;
                        }
            }
        };
        //Initializing the View Id
        BLogIn = (Button) findViewById(R.id.LogIn);
        BSignUp = (Button) findViewById(R.id.SignUp);
        mEmail = (EditText)findViewById(R.id.Email);
        mPassword = (EditText)findViewById(R.id.Password);
        //Setting the onClick listener for buttons for LogIn xml


        BLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(!task.isSuccessful())
                        {
                            Toast.makeText(LoginPage.this, "Sign In Error", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            //Debugging need to change the intent later

                            Intent intent = new Intent(LoginPage.this,HomePage.class);//changed from choose profile to homepage
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }
                });
            }
        });

        BSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this,SignUpPage.class);
                startActivity(intent);
                finish();
                return;
            }
        });

    }

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
