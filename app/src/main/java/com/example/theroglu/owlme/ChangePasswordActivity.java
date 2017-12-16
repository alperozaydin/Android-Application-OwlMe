package com.example.theroglu.owlme;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.BatchUpdateException;

public class ChangePasswordActivity extends AppCompatActivity {


    //creating a firebase variable.
    private FirebaseAuth mAuth;
    private Button ChangePassword;
    private EditText ChangePasswordEditText;
    private EditText ChangePasswordEditText2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        //referencing to our firebase connection
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user=mAuth.getCurrentUser();






        ChangePasswordEditText=findViewById(R.id.NewPasswordEditText);
        ChangePasswordEditText2=findViewById(R.id.NewPasswordEditText2);



        ChangePassword= findViewById(R.id.ChangePasswordButtonPasswordActivity);
        ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //referencing the buttons from the layout file
                String password1=ChangePasswordEditText.getText().toString();
                String password2=ChangePasswordEditText2.getText().toString();

                //checking whether both passwords are actually the same from both editext boxes.
                if(password1.equals(password2)) {


                    user.updatePassword(password1);
                    Toast.makeText(getApplicationContext(),"Password has been changed!",Toast.LENGTH_LONG).show();

                    startActivity(new Intent(getApplicationContext(),UserPage.class));


                }else{
                    Toast.makeText(getApplicationContext(),"Passwords are not the same",Toast.LENGTH_LONG).show();
                }








            }
        });





    }
}
