package com.example.theroglu.owlme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


     private Button MainMenuSignUp;

     //Firebase Authectication variable
     private FirebaseAuth mAuth;
     private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Button for moving to SignUp activity
        MainMenuSignUp= findViewById(R.id.MainMenuSignUpButton);
        MainMenuSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent is used to start up a new activity

                Intent SignUp= new Intent(MainActivity.this,SignInActivity.class);
                startActivity(SignUp);
            }
        });


    }


}
