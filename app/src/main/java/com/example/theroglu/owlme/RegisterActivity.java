package com.example.theroglu.owlme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    //name field for user to sign up
    private EditText mNameField;
    //email for user to sign up
    private EditText mEmailField;
    //password field for user to sign up
    private EditText mPasswordField;
    //button for registeration
    private Button   mRegisterButton;



    //creating a firebase variable.
    private FirebaseAuth mAuth;
    //creating a firebase database variable
    private DatabaseReference mDatabase;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //referencing to our firebase connection
        mAuth = FirebaseAuth.getInstance();
        //get reference means the root directory to our database in firebase

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");



        //referencing to our progress bar
       // mProgressBar = (ProgressBar) findViewById(R.id.progressBarRegister);



        //initializing our gui
        mNameField= (EditText) findViewById(R.id.nameFieldRegister);
        mEmailField= (EditText) findViewById(R.id.emailFieldRegister);
        mPasswordField= (EditText) findViewById(R.id.passwordFieldRegister);
        mRegisterButton= (Button) findViewById(R.id.registerButtonRegister);


        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });






    }

    //method for registering user
    private void startRegister() {
        final String name= mNameField.getText().toString().trim();
        String email= mEmailField.getText().toString().trim();
        String password= mPasswordField.getText().toString().trim();

        //checking for empty fields
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){


           //creates account at firebase database for an user given email and password.
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){

                        Toast.makeText(RegisterActivity.this,"Registration works",Toast.LENGTH_LONG).show();

                    //     mProgressBar.setVisibility(View.VISIBLE);

                        //user id is gathered once the user is signed up to the website.
                        String user_id=mAuth.getCurrentUser().getUid();

                        // Once user is registered to the Auth database we wanna create second user database in normal database area there
                        //for we  get the user id and write it into normal database.
                        DatabaseReference current_user_db =  mDatabase.child(user_id);

                        current_user_db.child("name").setValue(name);

                        //after that user is redirected to the main account activity.
                        Intent accountIntent = new Intent(RegisterActivity.this,UserAccountActivity.class);
                        accountIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(accountIntent);






                    }else{
                        // if signing up task is unsuccesfull we do make a error indication pretty much.
                        FirebaseAuthException e = (FirebaseAuthException )task.getException();
                        Toast.makeText(RegisterActivity.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.e("LoginActivity", "Failed Registration", e);

                    }
                }
            });

        }

    }
}
