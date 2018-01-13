package com.example.theroglu.owlme;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by theroglu on 21.12.17.
 */

public class FoundFriendsAdapter extends BaseAdapter {

    //getting the context
    private Context context;
    //arraylist for keeping the found users depending on the selection
    private ArrayList<User> usersList;

    User user;



    public FoundFriendsAdapter(Context context,ArrayList<User> users){
        this.context=context;
        this.usersList=users;

    }






    @Override
    public int getCount() {
        return usersList.size();
    }

    @Override
    public Object getItem(int position) {
        return usersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView= View.inflate(context,R.layout.list_friends_items,null);
        }


        TextView username= convertView.findViewById(R.id.userNameOfTheUser);
        TextView country=convertView.findViewById(R.id.countryOfTheUser);
        TextView age=convertView.findViewById(R.id.ageOfTheUser);
        TextView aboutMe=convertView.findViewById(R.id.aboutmeOfTheUser);
        TextView gender=convertView.findViewById(R.id.genderOfTheUser);

        User user=usersList.get(position);



        country.setText(user.getCountry());
     //   age.setText(user.getAge());
        aboutMe.setText(user.getAboutme());

        age.setText(String.valueOf(user.getAge()));

        gender.setText(user.getGender());

        username.setText(user.getUsername());


        return convertView;
    }
}
