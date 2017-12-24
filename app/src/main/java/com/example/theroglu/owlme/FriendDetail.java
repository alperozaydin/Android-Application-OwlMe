package com.example.theroglu.owlme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FriendDetail extends AppCompatActivity {

    private String username;



    //button for editing profile
    private Button EditProfileButton;
    //TextView for AboutMe
    private TextView AboutMeTextView;
    //TextView for Country;
    private TextView Country;
    //TextView for Gender
    private TextView GenderTextView;
    //TextView for Age;
    private  TextView AgeTextView;
    //button for messaing to user
    private  Button MessageToUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);

        final Intent intent=getIntent();
        username=intent.getStringExtra("username");



        //setting the AboutMe view in firebase AboutMeData
        AboutMeTextView= findViewById(R.id.aboutMeTExtViewMyProfile);

        Country = findViewById(R.id.CountryMyProfile);
        GenderTextView = findViewById(R.id.GenderMyProfile);
        AgeTextView=findViewById(R.id.AgeMyProfilee);
        MessageToUser=findViewById(R.id.MessageToUser);




        //creating a new child in the user account  called About me and updating the data we get from the user thru edit text called aboutMe
        DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference("Users");


        Country.setText(intent.getStringExtra("country"));
        AboutMeTextView.setText(intent.getStringExtra("aboutme"));
        GenderTextView.setText(intent.getStringExtra("gender"));

       AgeTextView.setText(String.valueOf(intent.getIntExtra("age",0)));


        //this is used to fetch data about user for the section of "languages"
        myRootRef.child(username).child("languages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                int x = 1;


                Map<String, Object> map = new HashMap<String, Object>();

                String[] values = new String[(int) snapshot.getChildrenCount()];


                for (DataSnapshot postSnapshot : snapshot.getChildren()) {


                    map.put(postSnapshot.getKey(), postSnapshot.getValue());

                    values[x - 1] = postSnapshot.getKey().toString() + " " + postSnapshot.getValue(String.class);
                    x = x + 1;


                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, values);
                ((ListView) findViewById(R.id.LanguagesListView)).setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });




        MessageToUser.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {



              startActivity(new Intent(getApplicationContext(),startChatWithUser.class));

            }
        });

    }

}
