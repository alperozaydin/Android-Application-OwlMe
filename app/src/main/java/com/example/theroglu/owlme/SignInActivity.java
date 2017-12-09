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
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
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
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class SignInActivity extends AppCompatActivity {


    //email text box for firebase login
    private EditText mEmailField;
    //password text box for firebase login
    private EditText mPasswordField;
    //buton tex box for firebase login
    private Button   mSignUpButton;
    //button for registration
    private Button  mRegisterButton;

    //login button for facebook
    private  LoginButton loginButton;

    //button for google registration
    private SignInButton mGoogleSigninButton;
    //Google signin integer for passing the value as positive
    private static final int RC_SIGN_IN=1;
    //creating google api client variable
    private GoogleApiClient mGoogleApiClient;
    //creating tag variable for google signin method
    private static final String TAG="SignIn_Activity";
    //creating the firebase auth listener for user activity change







      private User mUser;




    //callback for facebook login
    private CallbackManager callbackManager;

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
                    startActivity(new Intent(SignInActivity.this,UserAccountActivity.class));



                }

            }
        };


        //referencing our buttons with the ones in activity_sign_up.xml file
        mEmailField=(EditText)findViewById(R.id.EmailField);
        mPasswordField=(EditText) findViewById(R.id.PasswordField);
        mSignUpButton= (Button) findViewById(R.id.loginButton);
        mRegisterButton= (Button)  findViewById(R.id.RegisterButtonSignUp);
        loginButton=(LoginButton) findViewById(R.id.login_buttonFacebook) ;
        mGoogleSigninButton= (SignInButton) findViewById(R.id.googleSigninButtonSignIn) ;



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

                                Toast.makeText(SignInActivity.this,"Google Signin Didnt Work",Toast.LENGTH_LONG).show();

                            }
                        }
                )
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();


        //button activates the sign up function provided by Google Api.
        mGoogleSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });





        //lets us to take email and public profile from facebook account
        callbackManager =CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email"));

        //lets facebook users have access token for their account
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"Facebook loogin doesnt work",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException error) {

                Toast.makeText(getApplicationContext(),"Facebook login doesnt work",Toast.LENGTH_LONG).show();

            }

        });


        // Redirects user to registration page
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RegisterIntent=new Intent(SignInActivity.this,RegisterActivity.class);
                startActivity(RegisterIntent);
            }
        });


        //Button lets the login process start.
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //calls sign in function to let user login
                startSignIn();

            }
        });





    }


    //this method is used for registering and logging in the user using google account.
    private void signIn() {
        Intent signInIntent =   Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //method is used for  getting the result depending on the result from firebaseauth google function.
  //  @Override
  //  public void onActivityResult(int requestCode, int resultCode, Intent data) {
   //     super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
  //      if (requestCode == RC_SIGN_IN) {
   //         Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
  //          try {
                // Google Sign In was successful, authenticate with Firebase
  //              GoogleSignInAccount account = task.getResult(ApiException.class);
  //              firebaseAuthWithGoogle(account);
  //          } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
   //             Log.w(TAG, "Google sign in failed", e);
                // ...
  //          }
   //     }
 //   }

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

                            Toast.makeText(SignInActivity.this,"Registration Is Succesfull",Toast.LENGTH_LONG).show();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            //getting current users account
                            FirebaseUser user=mAuth.getCurrentUser();

                            //getting the display name of the current user to store them in our real time database

                            String databaseUserName=user.getDisplayName();


                            //creating a child called users
                            DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference().child("Users");
                            //adding a new user using their display name
                            DatabaseReference userNameRef =  myRootRef.child(databaseUserName);
                           //value is also set to user display name however it doenst have to be so
                            userNameRef.setValue(databaseUserName);




                            //after that user is redirected to the main account activity.
                            Intent accountIntent = new Intent(SignInActivity.this,UserAccountActivity.class);
                            accountIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(accountIntent);


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            // if signing up task is unsuccesfull we do make a error indication pretty much.
                            FirebaseAuthException e = (FirebaseAuthException )task.getException();
                            Toast.makeText(SignInActivity.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();

                            Log.e("LoginActivity", "Failed Registration", e);

                        }


                    }
                });



    }




    // creating an account for facebook users
    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential= FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    FirebaseUser user=mAuth.getCurrentUser();

                    String databaseUserName=user.getDisplayName();

                    //we replace whitespaces and remove them in order to eliminate errors in database for user table.
                  //   databaseUserName=databaseUserName.replace(" ","");
                    String name=mAuth.getCurrentUser().getDisplayName();

                    DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference().child("Users");

                    DatabaseReference userNameRef =  myRootRef.child(databaseUserName);

                    userNameRef.setValue(name);
                }

                if(!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"facebook login didnt work",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    // manages facebook login for login activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);

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

    @Override
    protected void onStop() {
        super.onStop();

        mAuth.removeAuthStateListener(mAuthListener);

    }

    //function for sign in  process
    private void startSignIn(){

        //getting the email from the user
        String email= mEmailField.getText().toString();
        //getting the password from the user
        String password=mPasswordField.getText().toString();


         if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

             Toast.makeText(SignInActivity.this, "Fields are empty", Toast.LENGTH_LONG).show();


         }else{

             //deals with the sign up&in process, if task is unscesfull makes a toast error.

             mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {

                     if (!task.isSuccessful()) {

                         Toast.makeText(SignInActivity.this, "Sign In is not succesfull", Toast.LENGTH_LONG).show();


                     }

                 }
             });

         }
    }
}
