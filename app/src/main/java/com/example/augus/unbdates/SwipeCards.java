package com.example.augus.unbdates;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class SwipeCards extends AppCompatActivity {

    private Cards card_data[];
    private ArrayAdapter arrayAdapter;
    private int i;

    ListView listView;
    List<Cards> rowItems;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_cards);

        //Calling Methods for UserSex
        checkUserSex();

        rowItems = new ArrayList<Cards>();

        arrayAdapter = new ArrayAdapter(this, R.layout.item, rowItems );


        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Toast.makeText(SwipeCards.this,"left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(SwipeCards.this,"right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(SwipeCards.this,"click", Toast.LENGTH_SHORT).show();
            }
        });

    }


    //Declaring variable for methods
    private String UserSex;
    private String OppositeUserSex;
    //Method to check usersex for matching
    public void checkUserSex()
    {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference male_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Male");
        male_db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                if(dataSnapshot.getKey().equals(user.getUid()))
                {
                    UserSex = "Male";
                    OppositeUserSex = "Female";
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot)
            {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });


        DatabaseReference female_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Female");
        female_db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                if(dataSnapshot.getKey().equals(user.getUid()))
                {
                    UserSex = "Female";
                    OppositeUserSex = "Male";
                    getOppositeUserSex();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot)
            {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });

    }


    //Method to Get OppositeUserSex for matching
    public void getOppositeUserSex()
    {
        DatabaseReference OppositeUserSex_db = FirebaseDatabase.getInstance().getReference().child("Users").child(OppositeUserSex);
        OppositeUserSex_db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                  if(dataSnapshot.exists())
                  {
                      Cards item = new Cards(dataSnapshot.getKey(),dataSnapshot.child("Name").getValue().toString());
                      rowItems.add(item);
                      arrayAdapter.notifyDataSetChanged();

                  }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot)
            {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });
    }

}


