package com.example.theroglu.owlme;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyHomeFragment extends Fragment {

    //adding buttons for spinners and buttons
    private Spinner CountrySpinner;
    private Spinner LanguageSpinner;
    private Button  FindFriendswithCountry;
    private Button  FindriendswithLanguage;




    public MyHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_my_home, container, false);



        LanguageSpinner=v.findViewById(R.id.LanguageSelectSpinner);
        CountrySpinner=v.findViewById(R.id.CountrySelectSpinner);
        FindFriendswithCountry=v.findViewById(R.id.CountrySelectButtonHome);
        FindriendswithLanguage=v.findViewById(R.id.LanguageSelectButtonHome);


        //functions are happening when we click on country button
        FindFriendswithCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //get the selected country
                String country=CountrySpinner.getSelectedItem().toString();
                //create a new intent to find friends thru langauges
                Intent FindFriendsWithCountry= new Intent(getActivity(), com.example.theroglu.owlme.FindFriendsWithCountry.class);
                //put the selected language as an extra string so that u can search it in the next activity
                FindFriendsWithCountry.putExtra("country",country);
                //start the searching activity
                startActivity(FindFriendsWithCountry);





            }
        });


        //button functions when button is clicked for the FindFriendswithLanguage button
        FindriendswithLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //get the selected language
                String language=LanguageSpinner.getSelectedItem().toString();
                //create a new intent to find friends thru langauges
                Intent FindFriendsWithLanguage= new Intent(getActivity(), com.example.theroglu.owlme.FindFriendsWithLanguage.class);
                //put the selected language as an extra string so that u can search it in the next activity
                FindFriendsWithLanguage.putExtra("language",language);
                //start the searching activity
                startActivity(FindFriendsWithLanguage);




            }
        });















        return v;
    }

}
