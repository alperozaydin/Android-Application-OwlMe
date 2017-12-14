package com.example.theroglu.owlme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfile extends AppCompatActivity {




    //it will be used to save changes in profile
    private Button saveProfilInfo;
    //it will be used to set string for about me section
    private EditText editTextAboutMe;
    //spinner for language selection , language1
    private Spinner LanguageSpinner1;
    //spinner for language level, level1
    private Spinner LanguageLevelSpinner1;

    //spinner for language selection , language1
    private Spinner LanguageSpinner2;
    //spinner for language level, level1
    private Spinner LanguageLevelSpinner2;

    //spinner for language selection , language1
    private Spinner LanguageSpinner3;
    //spinner for language level, level1
    private Spinner LanguageLevelSpinner3;

    //spinner for language selection , language1
    private Spinner LanguageSpinner4;
    //spinner for language level, level1
    private Spinner LanguageLevelSpinner4;










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


        LanguageSpinner1 = findViewById(R.id.Language1);
        LanguageLevelSpinner1= findViewById(R.id.LanguageLevel1);
        LanguageSpinner2 = findViewById(R.id.Language2);
        LanguageLevelSpinner2= findViewById(R.id.LanguageLevel2);
        LanguageSpinner3 = findViewById(R.id.Language3);
        LanguageLevelSpinner3= findViewById(R.id.LanguageLevel3);
        LanguageSpinner4 = findViewById(R.id.Language4);
        LanguageLevelSpinner4= findViewById(R.id.LanguageLevel4);








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

                if(!LanguageSpinner1.getSelectedItem().equals("Choose") && !LanguageLevelSpinner1.getSelectedItem().equals("Choose")) {


                DatabaseReference language1 = FirebaseDatabase.getInstance().getReference().child("Users").child(databaseUserName).child("Languages").child(LanguageSpinner1.getSelectedItem().toString()) ;
                language1.setValue(LanguageLevelSpinner1.getSelectedItem().toString());


                }


                if(!LanguageSpinner2.getSelectedItem().equals("Choose") && !LanguageLevelSpinner2.getSelectedItem().equals("Choose")){
                    DatabaseReference language2 = FirebaseDatabase.getInstance().getReference().child("Users").child(databaseUserName).child("Languages").child(LanguageSpinner2.getSelectedItem().toString()) ;
                    language2.setValue(LanguageLevelSpinner2.getSelectedItem().toString());




                }


                if(!LanguageSpinner3.getSelectedItem().equals("Choose") && !LanguageLevelSpinner3.getSelectedItem().equals("Choose")){
                    DatabaseReference language3 = FirebaseDatabase.getInstance().getReference().child("Users").child(databaseUserName).child("Languages").child(LanguageSpinner3.getSelectedItem().toString()) ;
                    language3.setValue(LanguageLevelSpinner3.getSelectedItem().toString());




                }

                if(!LanguageSpinner4.getSelectedItem().equals("Choose") && !LanguageLevelSpinner4.getSelectedItem().equals("Choose")){
                    DatabaseReference language4 = FirebaseDatabase.getInstance().getReference().child("Users").child(databaseUserName).child("Languages").child(LanguageSpinner4.getSelectedItem().toString()) ;
                    language4.setValue(LanguageLevelSpinner4.getSelectedItem().toString());



                }









                startActivity(new Intent(EditProfile.this,UserPage.class));


            }
        });





    }





}
