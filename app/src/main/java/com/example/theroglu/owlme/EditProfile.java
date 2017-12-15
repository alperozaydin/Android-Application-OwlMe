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

    //spinner for gender
    private Spinner GenderSpinner;

    //spinner for country
    private Spinner CountrySpinner;

    //edit text for age
    private EditText AgeEditText;


    //age for the user
    int Age;











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
        GenderSpinner=findViewById(R.id.GenderSpinner);
        CountrySpinner=findViewById(R.id.CountrySpinner);
        AgeEditText= findViewById(R.id.AgeInputEditText);











        editTextAboutMe= findViewById(R.id.aboutMeEditText);

        //referencing the button in layout
        saveProfilInfo= findViewById(R.id.SaveProfilInfo);

        //setting on click lister for the button
        saveProfilInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if age area is empty then we set it to zero otherwise parsing null to int is not possible
                if(AgeEditText.getText().toString().equals("")){
                    AgeEditText.setText("0");
                }



                //parsing age value to the int so that we can use it later
                Age= Integer.parseInt(AgeEditText.getText().toString());


                // we are getting the current user
                FirebaseUser user=mAuth.getCurrentUser();
                //aboutMe variable is used to get text from the user for about their info
                String aboutMe=editTextAboutMe.getText().toString();

                //we are reaching the user display name to reach database child
                String databaseUserName=user.getDisplayName();


                //creating a new child in the user account  called About me and updating the data we get from the user thru edit text called aboutMe
                if(!editTextAboutMe.getText().toString().equals("")){
                DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference().child("Users").child(databaseUserName).child("AboutMe");
                myRootRef.setValue(aboutMe);}


                // belove if conditions checks whether or not language preferences are empty, if so they re not saved.
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


                //if gender is not empty then we select and  save it to the database
                if(!GenderSpinner.getSelectedItem().equals("Choose") ){
                    DatabaseReference Gender = FirebaseDatabase.getInstance().getReference().child("Users").child(databaseUserName).child("Gender");
                    Gender.setValue(GenderSpinner.getSelectedItem().toString());



                }

                //if country is not empty then we select and  save it to the database
                if(!CountrySpinner.getSelectedItem().equals("Choose") ){
                    DatabaseReference Gender = FirebaseDatabase.getInstance().getReference().child("Users").child(databaseUserName).child("Country");
                    Gender.setValue(CountrySpinner.getSelectedItem().toString());



                }






                //if age is bigger than 18 and smaller than 100 we save the age to the database
               if( 18<= Age){

                    if(Age <=100 ){

                        DatabaseReference Age = FirebaseDatabase.getInstance().getReference().child("Users").child(databaseUserName).child("Age");
                        Age.setValue(Integer.parseInt(AgeEditText.getText().toString()));

                    }
               }














                startActivity(new Intent(EditProfile.this,UserPage.class));


            }
        });





    }





}
