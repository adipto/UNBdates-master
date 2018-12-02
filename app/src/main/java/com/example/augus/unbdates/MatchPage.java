package com.example.augus.unbdates;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.augus.unbdates.Matches.MatchesActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MatchPage extends AppCompatActivity {

    public static MyAppAdapter myAppAdapter;
    public static ViewHolder viewHolder;
    private ArrayList<Data> array;
    private SwipeFlingAdapterView flingContainer;

    private FirebaseAuth mAuth;

    private String currentUId;

    private DatabaseReference usersDb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_page);

        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        //Enabling the action bar, Overriding method outside this scope/class.
        getSupportActionBar().setTitle("Match Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuth = FirebaseAuth.getInstance();
        currentUId = mAuth.getCurrentUser().getUid();

        //Calling Method
        checkUserSex();

        array = new ArrayList<>();

        myAppAdapter = new MyAppAdapter(array, MatchPage.this);
        flingContainer.setAdapter(myAppAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override
            public void onLeftCardExit(Object dataObject) {


                Object dataObject1 = dataObject;
                Data obj = array.get((Integer) dataObject1);
                String userId = obj.getUserid();
                //NEW DATABASE IMPLEMENTATION
                usersDb.child(userId).child("connections").child("nope").child(currentUId).setValue(true);

                //usersDb.child(OppositeUserSex).child(userId).child("connections").child("nope").child(currentUId).setValue(true);
                Toast.makeText(MatchPage.this, "Left", Toast.LENGTH_SHORT).show();
                array.remove(0);
                myAppAdapter.notifyDataSetChanged();
            }

            @Override
            public void onRightCardExit(Object dataObject) {

                Object dataObject1 = dataObject;
                Data obj = array.get((Integer) dataObject1);
                String userId = obj.getUserid();
                //NEW DATABASE IMPLEMENTATION
                usersDb.child(userId).child("connections").child("yeps").child(currentUId).setValue(true);
                //usersDb.child(OppositeUserSex).child(userId).child("connections").child("yeps").child(currentUId).setValue(true);
                isConnectionMatch(userId);
                Toast.makeText(MatchPage.this, "Right", Toast.LENGTH_SHORT).show();


                array.remove(0);
                myAppAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);

                myAppAdapter.notifyDataSetChanged();
            }
        });
    }

    private void isConnectionMatch(String userId)
    {
        //New Database Implementation
        DatabaseReference currentUserConnectionsDb = usersDb.child(currentUId).child("connections").child("yeps").child(userId);
        //DatabaseReference currentUserConnectionsDb = usersDb.child(UserSex).child(currentUId).child("connections").child("yeps").child(userId);
        currentUserConnectionsDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Toast.makeText(MatchPage.this, "new Connection", Toast.LENGTH_LONG).show();

                    //New Database Implementation
                    usersDb.child(dataSnapshot.getKey()).child("connections").child("matches").child(currentUId).setValue(true);
                    usersDb.child(currentUId).child("connections").child("matches").child(dataSnapshot.getKey()).setValue(true);
                    //usersDb.child(OppositeUserSex).child(dataSnapshot.getKey()).child("connections").child("matches").child(currentUId).setValue(true);
                    //usersDb.child(UserSex).child(currentUId).child("connections").child("matches").child(dataSnapshot.getKey()).setValue(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    //Declaring new class

    //Class for holding the View

    public static class ViewHolder
    {
        public static FrameLayout background;
        public TextView DataText;
        public ImageView cardImage;


    }

    public class MyAppAdapter extends BaseAdapter {


        public List<Data> parkingList;
        public Context context;

        private MyAppAdapter(List<Data> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;

            if (rowView == null) {

                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.item, parent, false);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.DataText = (TextView) rowView.findViewById(R.id.bookText);
                viewHolder.background = (FrameLayout) rowView.findViewById(R.id.background);
                viewHolder.cardImage = (ImageView) rowView.findViewById(R.id.cardImage);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.DataText.setText(parkingList.get(position).getDescription() + "");
            //viewHolder.DataText.setText(parkingList.get(position).getName() + "");
            //Checking Image if default or not
            /*
            switch(parkingList.get(position).getProfileImageUrl())
            {
                case "default":

                    Glide.with(MatchPage.this).load(R.mipmap.ic_launcher).into(viewHolder.cardImage);
                    break;
                default:

                    Glide.with(MatchPage.this).load(parkingList.get(position).getImagePath()).into(viewHolder.cardImage);
                    break;
            }
            */
            Glide.with(MatchPage.this).load(parkingList.get(position).getImagePath()).into(viewHolder.cardImage);

            return rowView;
        }
    }


    //Checking for Users in the DATABASE

    //New IMPLEMENTATION OF DATABASE

    private String userSex;
    private String oppositeUserSex;
    public void checkUserSex(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userDb = usersDb.child(user.getUid());
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if (dataSnapshot.child("Gender").getValue() != null){
                        userSex = dataSnapshot.child("Gender").getValue().toString();
                        switch (userSex){
                            case "Male":
                                oppositeUserSex = "Female";
                                break;
                            case "Female":
                                oppositeUserSex = "Male";
                                break;
                        }
                        getOppositeUserSex();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



/*
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
*/
    //Declaring variables for OppositeUserSex method



    //Method to Get OppositeUserSex for matching
    public void getOppositeUserSex()
    {

        //NEW IMPLEMENTATION OF DATABASE


        //DatabaseReference OppositeUserSex_db = FirebaseDatabase.getInstance().getReference().child("Users").child(OppositeUserSex);
        //// oppositeUserSex_db was changed to userDb

        usersDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                String name="";
                String Description= "";
                String ImageURL= "";
                String UserID = "";
                /////Adding a new condition, the last condition for checking sex
                if(dataSnapshot.exists() && !dataSnapshot.child("connections").child("nope").hasChild(currentUId) && !dataSnapshot.child("connections").child("yeps").hasChild(currentUId)&& dataSnapshot.child("Gender").getValue().toString().equals(oppositeUserSex))
                {
                    //Checking for default profilePicUrl

                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("Name")!=null){
                        name = map.get("Name").toString();
                    }
                    if(map.get("Bio")!=null){
                        Description = map.get("Bio").toString();
                    }
                    if(map.get("profileImageUrl")!=null){
                        ImageURL = map.get("profileImageUrl").toString();
                    }
                    if(map.get("UserID")!=null){
                        UserID = map.get("UserID").toString();
                    }

                    array.add(new Data(ImageURL,Description,name,UserID));
                    myAppAdapter.notifyDataSetChanged();
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
    public void goToMatches(View view){
        Intent intent = new Intent(MatchPage.this, MatchesActivity.class);
        startActivity(intent);
    }
}
