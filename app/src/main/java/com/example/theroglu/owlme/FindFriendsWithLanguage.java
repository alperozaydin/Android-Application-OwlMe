package com.example.theroglu.owlme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FindFriendsWithLanguage extends AppCompatActivity {


    //creating a firebase variable.
    private FirebaseAuth mAuth;
    private ListView listView;
    private FoundFriendsAdapter foundFriendsAdapter;
    private ArrayList<User> userslist=new ArrayList<User>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends_with_language);


        //referencing to our firebase connection
        mAuth = FirebaseAuth.getInstance();

        // we are getting the current user
        final String user=mAuth.getCurrentUser().getDisplayName().toString();
        //aboutMe variable is used to get text from the user for about their info





        //this is a list to save people's usernames having that language attribute
        final ArrayList<String > findPeople= new ArrayList<>();






        //getting the intent from MyHomeFragment because we need to extract the friends depending on the languages
        Intent i=getIntent();
        final String Language=i.getStringExtra("language");






        //creating a database referance to find users
        DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference("Users");




        //it checks everyusers attributes to control weather or not an user has that language as a child , if so
        //user is added to findPeople list
        myRootRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {




                    if(snapshot.child("languages").hasChild(Language)){


                       String a= snapshot.getKey().toString();


                       if(!snapshot.getKey().toString().equals(user)) {
                           User user1 = snapshot.getValue(User.class);
                           user1.setUsername(snapshot.getKey());

                           userslist.add(user1);

                       }


                    }








                }

                listView=findViewById(R.id.list_view);


                foundFriendsAdapter=new FoundFriendsAdapter(FindFriendsWithLanguage.this,userslist);

                listView.setAdapter(foundFriendsAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        User person= (User) listView.getItemAtPosition(position);
                        Intent goToDetail= new Intent(getApplicationContext(),FriendDetail.class);
                        String c=person.getCountry();
                        goToDetail.putExtra("username",person.getUsername().toString());
                        goToDetail.putExtra("aboutme",person.getAboutme().toString());
                        goToDetail.putExtra("country",person.getCountry().toString());
                        goToDetail.putExtra("gender",person.getGender().toString());
                        goToDetail.putExtra("age",person.getAge());
                        goToDetail.putStringArrayListExtra("usernames",findPeople);
                        startActivity(goToDetail);


                    }


                });





            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }





}
