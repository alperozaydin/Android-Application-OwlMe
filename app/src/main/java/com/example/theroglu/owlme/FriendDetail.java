package com.example.theroglu.owlme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    //button for adding a user as a friend
    private  Button AddFriendButton;

    //current state of friendship status
    private String mCurrent_state;

    //database reference for friendship requests
    private DatabaseReference mFriendReqDatabase;

    private FirebaseUser mCurrentUser;

    private DatabaseReference mFriendDatabase;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);

        final Intent intent=getIntent();
        username=intent.getStringExtra("username");


        mFriendReqDatabase=FirebaseDatabase.getInstance().getReference().child("FriendReq");
        mFriendDatabase= FirebaseDatabase.getInstance().getReference().child("Friends");

        //setting the AboutMe view in firebase AboutMeData
        AboutMeTextView= findViewById(R.id.aboutMeTExtViewMyProfile);

        Country = findViewById(R.id.CountryMyProfile);
        GenderTextView = findViewById(R.id.GenderMyProfile);
        AgeTextView=findViewById(R.id.AgeMyProfilee);
        MessageToUser=findViewById(R.id.MessageToUser);
        AddFriendButton=findViewById(R.id.AddFriendsButton);

        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();



       mCurrent_state="not_friends";




        //creating a new child in the user account  called About me and updating the data we get from the user thru edit text called aboutMe
        DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference("Users");



        mFriendReqDatabase.child(mCurrentUser.getDisplayName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(username)){

                    String req_type=dataSnapshot.child(username).child("requestType").getValue().toString();

                    if(req_type.equals("received")){

                        mCurrent_state="req_received";
                        AddFriendButton.setText("Accept Friend Request");



                    } else if(req_type.equals("sent")){

                        mCurrent_state="requestSent";
                        AddFriendButton.setText("Cancel Friend Request");



                    } else {

                        mFriendDatabase.child(mCurrentUser.getDisplayName())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        if(dataSnapshot.hasChild(username)){

                                            mCurrent_state="friends";
                                            AddFriendButton.setText("Unfriend the User");

                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mFriendDatabase.child(mCurrentUser.getDisplayName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

              if ( dataSnapshot.hasChild(username)){

                  mCurrent_state="friends";
                  AddFriendButton.setText("Unfriend the User");

              }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Country.setText(intent.getStringExtra("country"));
        AboutMeTextView.setText(intent.getStringExtra("aboutme"));
        GenderTextView.setText(intent.getStringExtra("gender"));

       AgeTextView.setText(String.valueOf(intent.getIntExtra("age",0)));


       myRootRef.child(username).child("country").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // data available in snapshot.value()

                String countryy=snapshot.getValue(String.class);
                Country.setText(countryy);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

        //this is used to fetch data about user for the section of "age"
        myRootRef.child(username).child("age").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // data available in snapshot.value()

                if(snapshot.getValue(Integer.class) != null){

                    int age2= Integer.valueOf(snapshot.getValue(Integer.class));

                    String value= String.valueOf(age2);
                    AgeTextView.setText(value);

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


        //this is used to fetch data about user for the section of "gender"
        myRootRef.child(username).child("gender").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // data available in snapshot.value()

                String gender=snapshot.getValue(String.class);

                GenderTextView.setText(gender);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });



        //this is used to fetch data about user for the section of "aboutme"
        myRootRef.child(username).child("aboutme").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // data available in snapshot.value()

                String AboutMe=snapshot.getValue(String.class);
                AboutMeTextView.setText(AboutMe);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });




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

                 Intent intent=getIntent();
               String username=intent.getStringExtra("username").toString();
               String a= username.toString();


                Intent startchat= new Intent(getApplicationContext(),Chat.class);
                startchat.putExtra("username",a);
                startActivity(startchat);


            }
        });


        AddFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddFriendButton.setEnabled(false);


                // ------------------- IF NOT FRIENDS -------------------------

                if( mCurrent_state.equals("not_friends")){


                    Intent intent=getIntent();
                    final String username=intent.getStringExtra("username").toString();
                    final String kullanici=mCurrentUser.getDisplayName();

                    mFriendReqDatabase.child(mCurrentUser.getDisplayName().toString()).child(username).child("requestType")
                            .setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                mFriendReqDatabase.child(username).child(mCurrentUser.getDisplayName().toString()).child("requestType")
                                        .setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {


                                        mCurrent_state="requestSent";
                                        AddFriendButton.setText("Cancel Friend Request");




                                    }
                                });

                            } else{


                                Toast.makeText(getApplicationContext(),"Failed Sending Request",Toast.LENGTH_LONG).show();

                            }

                            AddFriendButton.setEnabled(true);

                        }
                    });

                }


                // -------------------  CANCEL REQUEST STATE


                if(mCurrent_state.equals("requestSent")){

                    mFriendReqDatabase.child(mCurrentUser.getDisplayName()).child(username).removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    mFriendReqDatabase.child(username).child(mCurrentUser.getDisplayName())
                                            .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            AddFriendButton.setEnabled(true);
                                            mCurrent_state="not_friends";
                                            AddFriendButton.setText("Add Friend");


                                        }
                                    });

                                }
                            });
                }



                //------------------------- REQUEST RECEIVED STATE----------------

                if (mCurrent_state.equals("req_received")){

                    final String currentDate= DateFormat.getDateTimeInstance().format(new Date());

                    mFriendDatabase.child(mCurrentUser.getDisplayName()).child(username).setValue(currentDate)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {



                                        mFriendDatabase.child(username).child(mCurrentUser.getDisplayName()).setValue(currentDate)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {


                                                        mFriendReqDatabase.child(mCurrentUser.getDisplayName()).child(username).removeValue()
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {

                                                                        mFriendReqDatabase.child(username).child(mCurrentUser.getDisplayName())
                                                                                .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid) {

                                                                                AddFriendButton.setEnabled(true);
                                                                                mCurrent_state="friends";
                                                                                AddFriendButton.setText("Unfriend the User");


                                                                            }
                                                                        });

                                                                    }
                                                                });



                                                    }
                                                });






                                }
                            });


                }



                // ------------------- IF USERS ARE FRIENDS


                if(mCurrent_state.equals("friends")){

                    mFriendDatabase.child(mCurrentUser.getDisplayName()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(username)){


                                mFriendDatabase.child(mCurrentUser.getDisplayName()).child(username).removeValue().
                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                mFriendDatabase.child(username).child(mCurrentUser.getDisplayName()).removeValue();

                                                AddFriendButton.setEnabled(true);
                                                mCurrent_state="not_friends";
                                                AddFriendButton.setText("Add Friend");



                                            }
                                        });





                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {


                        }
                    });



                }




            }
        });

    }

}
