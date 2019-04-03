package com.example.anthonyrogers.lab7book;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class MainActivity extends AppCompatActivity implements BookListFragment.OnFragmentInteractionListener {

private ViewPager mViewPager;
boolean singlePane;
FragmentManager fm;
BookDetailsFragment bdf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //this grabs the the array of strings from android resources.
        final  String[] array = getResources().getStringArray(R.array.bookArray);
        bdf = new BookDetailsFragment();

        //this check to see if the frame2 id is available and says true if it is and false if its not.
        singlePane = findViewById(R.id.frame2) == null;


        //this runs the fragment if the phone is in portrait mode
        if(singlePane){
            mViewPager = findViewById(R.id.view_pager);
            mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), array));
        }
        fm = getSupportFragmentManager();

        //this sets frame one to a list and frame 2 to a single object fragment that will change textviews
        if (!singlePane) {

            fm.beginTransaction()
                    .replace(R.id.frame1, new BookListFragment())
                    .commit();

            fm.beginTransaction()
                    .replace(R.id.frame2, bdf)
                    .commit();
        }
    }

    @Override
    public void BookName(String nameOfBook) {

        //This creates a new Book Details Fragment Everytime a listview is clicked


        //TODO: unmark this if you would like to create a new fragment of BookDetailsFragment when
        //TODO: when viewing on a tablet or in landscape mode. Current we only create on instance and change textview
        // BookDetailsFragment df = BookDetailsFragment.newInstance(nameOfBook);
        // fm.beginTransaction().replace(R.id.frame2, df).addToBackStack(null).commit();

        bdf.displayBook(nameOfBook);
    }


}
