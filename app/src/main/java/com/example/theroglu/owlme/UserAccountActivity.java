package com.example.theroglu.owlme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserAccountActivity extends AppCompatActivity {

    //button for logging out the user

    private Button mLogOutAccount;

    //firebase authentication for users
    private FirebaseAuth mAuth;
    //firebase auth listener
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);



        mLogOutAccount = findViewById(R.id.LogOutButton);
        mLogOutAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                        mAuth = FirebaseAuth.getInstance();
                        mAuth.signOut();
                LoginManager.getInstance().logOut();
                        startActivity(new Intent(UserAccountActivity.this,MainActivity.class));


            }
        });
    }



    @Override
    protected void onStop() {
        super.onStop();

        mAuth.removeAuthStateListener(mAuthListener);

    }


}

