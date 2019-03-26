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
BookListFragment blf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final  String[] array = getResources().getStringArray(R.array.bookArray);
        blf = new BookListFragment();

        singlePane = findViewById(R.id.frame2) == null;

        if(singlePane){
            mViewPager = findViewById(R.id.view_pager);
            mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), array));
        }



        fm = getSupportFragmentManager();

        if (!singlePane) {

            fm.beginTransaction()
                    .replace(R.id.frame1, new BookListFragment())
                    .commit();

            fm.beginTransaction()
                    .replace(R.id.frame2, new BookDetailsFragment())
                    .commit();
        }
    }



}
