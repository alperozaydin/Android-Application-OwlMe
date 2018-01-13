package com.example.theroglu.owlme;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {


    private ListView mListView;
    private ListView mRequests;
    private DatabaseReference mFriendsDatabase;
    private FirebaseUser mCurrentUser;
    //this is a list to save people's usernames having that language attribute
    final ArrayList<String > findPeople= new ArrayList<>();

    //database reference for friendship requests
    private DatabaseReference mFriendReqDatabase;




    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v= inflater.inflate(R.layout.fragment_friends, container, false);

        mListView= v.findViewById(R.id.FriendsListView);
        mRequests=v.findViewById(R.id.FriendRequestsListView);

        mFriendsDatabase= FirebaseDatabase.getInstance().getReference().child("Friends");

        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();

        mFriendReqDatabase=FirebaseDatabase.getInstance().getReference().child("FriendReq");

        mFriendReqDatabase.child(mCurrentUser.getDisplayName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                int x = 1;


                Map<String, Object> map = new HashMap<String, Object>();

                String[] values = new String[(int) dataSnapshot.getChildrenCount()];


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {


                    if(!postSnapshot.toString().equals(mCurrentUser.getDisplayName()) &&  !postSnapshot.getValue().equals("sent")) {
                        values[x - 1] = postSnapshot.getKey().toString();
                        x = x + 1;

                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, values);
                ((ListView) v.findViewById(R.id.FriendRequestsListView)).setAdapter(adapter);



                v.findViewById(R.id.FriendRequestLinearLayout).setVisibility(View.VISIBLE);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

         mRequests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                 String username= mRequests.getItemAtPosition(position).toString();

                 Intent FriendDetail= new Intent(getApplicationContext(), com.example.theroglu.owlme.FriendDetail.class);
                 FriendDetail.putExtra("username",username);
                 startActivity(FriendDetail);


             }
         });









        mFriendsDatabase.child(mCurrentUser.getDisplayName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                int x = 1;


                Map<String, Object> map = new HashMap<String, Object>();

                String[] values = new String[(int) dataSnapshot.getChildrenCount()];


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {


                    map.put(postSnapshot.getKey(), postSnapshot.getValue());

                    values[x - 1] = postSnapshot.getKey().toString();
                    x = x + 1;


                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, values);
                ((ListView) v.findViewById(R.id.FriendsListView)).setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }




        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String username= mListView.getItemAtPosition(position).toString();

                Intent FriendDetail= new Intent(getApplicationContext(), com.example.theroglu.owlme.FriendDetail.class);
                FriendDetail.putExtra("username",username);
                startActivity(FriendDetail);


            }
        });









        return v;
    }

}
