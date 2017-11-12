package com.example.theroglu.owlme;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);

        // Button for moving to SignUp activity
        Button MainMenuSignUp= findViewById(R.id.MainMenuSignUpButton);
        MainMenuSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent is used to start up a new activity

                Intent SignUp= new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(SignUp);
            }
        });


    }
}
