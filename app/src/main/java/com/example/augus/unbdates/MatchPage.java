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
import java.util.Map;


public class MatchPage extends AppCompatActivity {

    public static MyAppAdapter myAppAdapter;
    public static ViewHolder viewHolder;
    private ArrayList<Data> array;
    private SwipeFlingAdapterView flingContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_page);

        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        //Enabling the action bar, Overriding method outside this scope/class.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Calling Method
        //checkUserSex();

        array = new ArrayList<>();

          /*
        array.add(new Data("http://www.androidtutorialpoint.com/wp-content/uploads/2016/11/Katrina-Kaif.jpg", "Hi I am Katrina Kaif. Wanna chat with me ?. \n" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));

        array.add(new Data("http://www.androidtutorialpoint.com/wp-content/uploads/2016/11/Emma-Watson.jpg", "Hi I am Emma Watson. Wanna chat with me ? \n" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
        array.add(new Data("http://www.androidtutorialpoint.com/wp-content/uploads/2016/11/Scarlett-Johansson.jpg", "Hi I am Scarlett Johansson. Wanna chat with me ? \n" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
        array.add(new Data("http://www.androidtutorialpoint.com/wp-content/uploads/2016/11/Priyanka-Chopra.jpg", "Hi I am Priyanka Chopra. Wanna chat with me ? \n" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
        array.add(new Data("http://www.androidtutorialpoint.com/wp-content/uploads/2016/11/Deepika-Padukone.jpg", "Hi I am Deepika Padukone. Wanna chat with me ? \n" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
        array.add(new Data("http://www.androidtutorialpoint.com/wp-content/uploads/2016/11/Anjelina-Jolie.jpg", "Hi I am Anjelina Jolie. Wanna chat with me ? \n" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
        array.add(new Data("http://www.androidtutorialpoint.com/wp-content/uploads/2016/11/Aishwarya-Rai.jpg", "Hi I am Aishwarya Rai. Wanna chat with me ? \n" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
*/

        myAppAdapter = new MyAppAdapter(array, MatchPage.this);
        flingContainer.setAdapter(myAppAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                array.remove(0);
                myAppAdapter.notifyDataSetChanged();
            }

            @Override
            public void onRightCardExit(Object dataObject) {

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

    //Implementing the back button
    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        /*
        int id = item.getItemId();
        if(id == android.R.id.home)
        {
            Intent intent = new Intent(MatchPage.this,LoginPage.class);
            startActivity(intent);
            finish();
        }
        ////***
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }



    }
*/
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

            Glide.with(MatchPage.this).load(parkingList.get(position).getImagePath()).into(viewHolder.cardImage);

            return rowView;
        }
    }


    //Checking for Users in the DATABASE

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

    //Declaring variables for OppositeUserSex method



    //Method to Get OppositeUserSex for matching
    public void getOppositeUserSex()
    {
        DatabaseReference OppositeUserSex_db = FirebaseDatabase.getInstance().getReference().child("Users").child(OppositeUserSex);
        OppositeUserSex_db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                String name="";
                String Description= "";
                String ImageURL= "";
                if(dataSnapshot.exists()&& dataSnapshot.getChildrenCount()>0)
                {
                    //Need to pass in all the information as required by Data,check data class
                    //Cards item = new Cards(dataSnapshot.getKey(),dataSnapshot.child("Name").getValue().toString());
                    //Data userdata = new Data();


                        long usercount = dataSnapshot.getChildrenCount();
                        for(int i = 1; i <= usercount; i++)
                        {
                            Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                            if(map.get("Name")!=null){
                                name = map.get("Name").toString();
                            }
                            if(map.get("Bio")!=null){
                                Description = map.get("Bio").toString();
                            }
                            if(map.get("ProfileImageUrl")!=null){
                                ImageURL = map.get("ProfileImageUrl").toString();
                            }
                            array.add(new Data(ImageURL,Description,name));
                        }





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
