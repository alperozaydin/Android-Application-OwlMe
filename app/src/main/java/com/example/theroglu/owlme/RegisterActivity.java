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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    //name field for user to sign up
    private EditText mNameField;
    //email for user to sign up
    private EditText mEmailField;
    //password field for user to sign up
    private EditText mPasswordField;
    //button for registeration
    private Button   mRegisterButton;
    //button for google registration
    private SignInButton mGoogleSigninButton;
    //Google signin integer for passing the value as positive
    private static final int RC_SIGN_IN=1;
    //creating google api client variable
    private GoogleApiClient mGoogleApiClient;
    //creating tag variable for google signin method
    private static final String TAG="Register_Activity";
    //creating the firebase auth listener for user activity change
    private FirebaseAuth.AuthStateListener mAuthListener;

    //creating a user class
    private User mUser;





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

        //setting the auth listener so that we'll understand if the user status is changed
        //if user is registered, then we'll redirect it to user account activity.
        mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){

                    startActivity(new Intent(RegisterActivity.this,UserAccountActivity.class));

                }
            }
        };



        //referencing to our progress bar
       // mProgressBar = (ProgressBar) findViewById(R.id.progressBarRegister);



        //initializing our gui
        mNameField= (EditText) findViewById(R.id.nameFieldRegister);
        mEmailField= (EditText) findViewById(R.id.emailFieldRegister);
        mPasswordField= (EditText) findViewById(R.id.passwordFieldRegister);
        mRegisterButton= (Button) findViewById(R.id.registerButtonRegister);
        mGoogleSigninButton= (SignInButton) findViewById(R.id.GoogleSignUpButtonRegister) ;


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //creating a google api client to connect.
        mGoogleApiClient= new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                                Toast.makeText(RegisterActivity.this,"Google Signin Didnt Work",Toast.LENGTH_LONG).show();

                            }
                        }
                )
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();



        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });


        //button activates the sign up function provided by Google Api.
        mGoogleSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });







    }

    //this method is used for registering and logging in the user using google account.
    private void signIn() {
        Intent signInIntent =   Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //method is used for  getting the result depending on the result from firebaseauth google function.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    //method is used to create account with Google account and then adds the user into normal database rather than Auth database.
    //We add users to both databases because in future users will have features like messages and personal pages
    //therefore for each user we should create a personal existince in normal database.
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {





        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {



                            Toast.makeText(RegisterActivity.this,"Registration Is Succesfull",Toast.LENGTH_LONG).show();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user=mAuth.getCurrentUser();

                            String databaseUserName=user.getDisplayName();
                            mUser=new User(databaseUserName);
                            //we replace whitespaces and remove them in order to eliminate errors in database for user table.
                          //  databaseUserName=databaseUserName.replace(" ","");
                            String name=mAuth.getCurrentUser().getDisplayName();

                            DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference().child("Users");

                            DatabaseReference userNameRef =  myRootRef.child(databaseUserName);

                            userNameRef.setValue(name);




                        //after that user is redirected to the main account activity.
                            Intent accountIntent = new Intent(RegisterActivity.this,UserAccountActivity.class);
                            accountIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(accountIntent);


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            // if signing up task is unsuccesfull we do make a error indication pretty much.
                            FirebaseAuthException e = (FirebaseAuthException )task.getException();
                            Toast.makeText(RegisterActivity.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();

                            Log.e("LoginActivity", "Failed Registration", e);

                        }


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


                        //getting a reference to the users in realtime database
                        DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference().child("Users");

                        //we add nodes as usernames for the users registered with the normal email
                        DatabaseReference userNameRef =  myRootRef.child(name);
                        //assigning the same name as an value
                        userNameRef.setValue(name);
                        //here in next 3 lines what happens is we set up a display name for the user as name
                        //because normally displayname is only generated for gmail and facebook users
                        //so name variable should be saved up as display name too, because of database queries in future.
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();

                        user.updateProfile(profileUpdates);






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
