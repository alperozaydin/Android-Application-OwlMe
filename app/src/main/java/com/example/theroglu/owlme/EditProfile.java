package com.example.theroglu.owlme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfile extends AppCompatActivity {




    //it will be used to save changes in profile
    private Button saveProfilInfo;
    //it will be used to set string for about me section
    private EditText editTextAboutMe;







    //firebase authentication for users
    private FirebaseAuth mAuth;
    //firebase auth listener
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //referencing to our firebase connection
        mAuth = FirebaseAuth.getInstance();





        editTextAboutMe= findViewById(R.id.aboutMeEditText);

        //referencing the button in layout
        saveProfilInfo= findViewById(R.id.SaveProfilInfo);

        //setting on click lister for the button
        saveProfilInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // we are getting the current user
                FirebaseUser user=mAuth.getCurrentUser();
                //aboutMe variable is used to get text from the user for about their info
                String aboutMe=editTextAboutMe.getText().toString();

                //we are reaching the user display name to reach database child
                String databaseUserName=user.getDisplayName();


                //creating a new child in the user account  called About me and updating the data we get from the user thru edit text called aboutMe
                DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference().child("Users").child(databaseUserName).child("AboutMe");
                myRootRef.setValue(aboutMe);


            }
        });


    }
}
