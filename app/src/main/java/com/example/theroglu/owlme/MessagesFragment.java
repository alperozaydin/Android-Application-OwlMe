package com.example.theroglu.owlme;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {


    public MessagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v= inflater.inflate(R.layout.fragment_messages, container, false);

        // Inflate the layout for this fragment

        //TODO
        //We need to put chat code here!
        Intent myIntent = new Intent(getActivity(), Users.class);
        startActivity(myIntent);




        return v;

    }

}
