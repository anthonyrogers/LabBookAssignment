package com.example.anthonyrogers.lab7book;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class BookListFragment extends Fragment {

    String[] array;
    Context parent;
    ListView listView;
    public BookListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.parent = context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

      listView = view.findViewById(R.id.List_View);
       ArrayAdapter adapter = new ArrayAdapter<>(parent, android.R.layout.simple_list_item_1,
               parent.getResources().getStringArray(R.array.bookArray));
        listView.setAdapter(adapter);
        return view;
    }


 /*   @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
             //something here
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
    }
}
