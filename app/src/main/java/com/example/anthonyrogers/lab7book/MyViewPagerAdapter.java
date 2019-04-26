package com.example.anthonyrogers.lab7book;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;

public class MyViewPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<Book> array = new ArrayList<>();
    BookDetailsFragment mCurrentBookDetail;

    public MyViewPagerAdapter(FragmentManager fm, ArrayList<Book> arrayOfBooks) {
        super(fm);
        array.addAll(arrayOfBooks);
        notifyDataSetChanged();
    }

    //this is for the view pager. This creates a new instance of bookDetailsFragment using the factory
    //method inside of the the fragment
    @Override
    public Fragment getItem(int i) {
        mCurrentBookDetail = BookDetailsFragment.newInstance(array.get(i));
        return mCurrentBookDetail;
    }

    @Override
    public int getCount()
    {
        return array.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}

