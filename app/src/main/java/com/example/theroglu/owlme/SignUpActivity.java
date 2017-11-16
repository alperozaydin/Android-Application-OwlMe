package com.example.theroglu.owlme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    //email text box for firebase login
    private EditText mEmailField;
    //password text box for firebase login
    private EditText mPasswordField;
    //buton tex box for firebase login
    private Button   mSignUpButton;
    //firebase authentication for users
    private FirebaseAuth mAuth;
    //firebase auth listener
    private FirebaseAuth.AuthStateListener mAuthListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //referencing our Firebase variable with the application
        mAuth=FirebaseAuth.getInstance();

        // creating a listener for firebaseauth
        mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


                if(firebaseAuth.getCurrentUser() != null){

                    //if user is logged in we redirect him to useraccount page
                    startActivity(new Intent(SignUpActivity.this,UserAccountActivity.class));



                }

            }
        };


        //referencing our buttons with the ones in activity_sign_up.xml file
        mEmailField=(EditText)findViewById(R.id.EmailField);
        mPasswordField=(EditText) findViewById(R.id.PasswordField);
        mSignUpButton= (Button) findViewById(R.id.loginButton);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //calls sign in function to let user login
                startSignIn();

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

    }

    //function for sign in  process
    private void startSignIn(){

        //getting the email from the user
        String email= mEmailField.getText().toString();
        //getting the password from the user
        String password=mPasswordField.getText().toString();


         if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

             Toast.makeText(SignUpActivity.this, "Fields are empty", Toast.LENGTH_LONG).show();


         }else{

             //deals with the sign up&in process, if task is unscesfull makes a toast error.

             mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {

                     if (!task.isSuccessful()) {

                         Toast.makeText(SignUpActivity.this, "Sign In is not succesfull", Toast.LENGTH_LONG).show();


                     }

                 }
             });

         }
    }
}
