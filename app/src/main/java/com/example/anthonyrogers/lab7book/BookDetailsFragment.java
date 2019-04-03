package com.example.anthonyrogers.lab7book;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BookDetailsFragment extends Fragment {

    private static final String Book_Name = "bookName";
    private String bookName;
    TextView textView;



    public BookDetailsFragment() {
    }


    public static BookDetailsFragment newInstance(String param1) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putString(Book_Name, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookName = getArguments().getString(Book_Name);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_book_details, container, false);
        textView = view.findViewById(R.id.TextOnFrag);

       textView.setText(bookName);

        return view;
    }


    public void displayBook(String title){
        TextView t =  this.getView().findViewById(R.id.TextOnFrag);
        t.setText(title);
    }
}
