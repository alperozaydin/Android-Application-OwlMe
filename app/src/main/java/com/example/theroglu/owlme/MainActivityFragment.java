package com.example.theroglu.owlme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by theroglu on 12.11.17.
 */

public class MainActivityFragment extends Fragment {

    @Override
    // Fragment for creating main page

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main_fragment, container, false);
    }
}
