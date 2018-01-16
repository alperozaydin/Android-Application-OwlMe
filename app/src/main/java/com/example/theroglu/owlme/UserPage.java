package com.example.theroglu.owlme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.net.URI;

public class UserPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                   MyProfile.OnFragmentInteractionListener,
                    SettingsFragment.OnFragmentInteractionListener{



    //firebase authentication for users
    private FirebaseAuth mAuth;
    //firebase auth listener
    private FirebaseAuth.AuthStateListener mAuthListener;










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //referencing the current firebase situation
        mAuth=FirebaseAuth.getInstance();




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //getting the reference of headerView
        View header=navigationView.getHeaderView(0);

        //creating a textview to set text username in drawer menu
        TextView username= header.findViewById(R.id.NavigationDrawerUsername);

        //creating a textview to set text email in drawer menu
        TextView email =header.findViewById(R.id.NavigationDrawerEmail);

        //creating a imageview to set image  in drawer menu
        //ImageView profilPic = header.findViewById(R.id.NavigationDrawerimageView);


        //getting the information of current user
        FirebaseUser user=mAuth.getCurrentUser();

        //setting text for the navigation menu
        username.setText(user.getDisplayName());

        //setting email for the navigation menu
        email.setText(user.getEmail());


        MyHomeFragment myHomeFragment=new MyHomeFragment();
        FragmentManager manager= getSupportFragmentManager();

        manager.beginTransaction().replace(R.id.relative_layout_for_myprofile_fragment,myHomeFragment,myHomeFragment.getTag()).commit();





    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_page, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            MyHomeFragment myHomeFragment=new MyHomeFragment();
            FragmentManager manager= getSupportFragmentManager();

            manager.beginTransaction().replace(R.id.relative_layout_for_myprofile_fragment,myHomeFragment,myHomeFragment.getTag()).commit();


        } else if (id == R.id.nav_profile) {

            //creating MyProfile Fragment, realize that we re not creating Activites because this menu requires only fragments.

            MyProfile myProfile = new MyProfile();


            FragmentManager manager= getSupportFragmentManager();

            manager.beginTransaction().replace(R.id.relative_layout_for_myprofile_fragment,myProfile,myProfile.getTag()).commit();



        } else if (id == R.id.nav_messages) {

            //creating messagesFragment , realize that we re not creating Activites because this menu requires only fragments.

            MessagesFragment messagesFragment=new MessagesFragment();
            FragmentManager manager= getSupportFragmentManager();

            manager.beginTransaction().replace(R.id.relative_layout_for_myprofile_fragment,messagesFragment,messagesFragment.getTag()).commit();


        } else if (id == R.id.nav_friends) {

            //creating friendsFragment, realize that we re not creating Activites because this menu requires only fragments.

            FriendsFragment friendsFragment=new FriendsFragment();
            FragmentManager manager= getSupportFragmentManager();

            manager.beginTransaction().replace(R.id.relative_layout_for_myprofile_fragment,friendsFragment,friendsFragment.getTag()).commit();




        } else if (id == R.id.nav_settings) {

            //creating settingsFragment, realize that we re not creating Activites because this menu requires only fragments.

            SettingsFragment settingsFragment= new SettingsFragment();

            FragmentManager manager= getSupportFragmentManager();

            manager.beginTransaction().replace(R.id.relative_layout_for_myprofile_fragment,settingsFragment,settingsFragment.getTag()).commit();



        }

        else if (id == R.id.LogOutUserPage) {

            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            LoginManager.getInstance().logOut();
            startActivity(new Intent(UserPage.this,MainActivity.class));

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
