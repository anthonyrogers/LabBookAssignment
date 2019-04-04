package com.example.anthonyrogers.lab7book;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BookDetailsFragment extends Fragment {

    TextView textView;
    Book books = new Book();

    public BookDetailsFragment() {
    }

    public static BookDetailsFragment newInstance(Book bookObj) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("myBook", bookObj);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            books = getArguments().getParcelable("myBook");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_book_details, container, false);
        textView = view.findViewById(R.id.TextOnFrag);


        //TODO: create correct text view and photos after fixing error issues
       textView.setText("hey");
        return view;
    }

    public void displayBook(String title){
        TextView t =  this.getView().findViewById(R.id.TextOnFrag);
        t.setText(title);
    }
}
