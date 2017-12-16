package com.example.theroglu.owlme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RemoveAccountActivity extends AppCompatActivity {


    //creating a firebase variable.
    private FirebaseAuth mAuth;
    //creating a button for removing the account
    private Button RemoveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_account);

        //referencing to our firebase connection
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user=mAuth.getCurrentUser();

        RemoveAccount=findViewById(R.id.removeAccountButtonActivity);
        RemoveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //here we delete the user from Auth database where only users are stored
                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            //we are getting the display name of the user
                            final String databaseUserName=user.getDisplayName();
                            //removing the user also from the realtime database
                            DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference().child("Users").child(databaseUserName);
                            myRootRef.setValue(null);



                            Toast.makeText(getApplicationContext(),"Account has been succesfully deleted from the system",Toast.LENGTH_LONG).show();


                            // we are redirecting user to the sign up page so that they can sign up again.
                            startActivity(new Intent(getApplicationContext(),SignInActivity.class));





                        }else{
                            //if removal is not succesfull we are giving an error message.
                            Toast.makeText(getApplicationContext(),"Account could not be deleted",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
