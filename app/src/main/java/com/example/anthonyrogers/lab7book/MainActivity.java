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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final  String[] array = getResources().getStringArray(R.array.bookArray);

        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), array));



      /*  PagerAdapter pageradapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return array.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return false;
            }


        };*/

      //  ViewPager viewPager = findViewById(R.id.view_pager);
      //  viewPager.setAdapter(pageradapter);
//        ArrayAdapter adapter = new ArrayAdapter<>(this,
//                R.layout.layout_list_view, array);
//
//        ListView listView = findViewById(R.id.main_list_view);
//        listView.setAdapter(adapter);


    }
}
