package com.example.anthonyrogers.lab7book;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class BookListFragment extends Fragment {

    Context parent;
    ListView listView;
    ArrayList<String> list = new ArrayList<>();

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

    //creates an array list and sets an event listener to the parents public method that is going to be there due
    //to it implementing an interface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

      listView = view.findViewById(R.id.List_View);
       ArrayAdapter adapter = new ArrayAdapter<>(parent, android.R.layout.simple_list_item_1,
               list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parentView, View view, int position, long id) {
                String bookName = (String) parentView.getItemAtPosition(position);
                ((OnFragmentInteractionListener) parent).BookName(bookName);
            }
        });

        return view;
    }

    public interface OnFragmentInteractionListener {
        void BookName(String nameOfBook);
    }
}
