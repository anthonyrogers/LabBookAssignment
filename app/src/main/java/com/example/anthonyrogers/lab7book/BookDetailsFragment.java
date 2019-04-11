package com.example.anthonyrogers.lab7book;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class BookDetailsFragment extends Fragment {

    TextView textViewAuthor;
    TextView BookName;
    ImageView Image;
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
        textViewAuthor = view.findViewById(R.id.TextOnFrag);
        BookName = view.findViewById(R.id.lblBookTitle);
        Image = view.findViewById(R.id.imageView4);

        String imageUri = books.coverURL;
        ImageView ivBasicImage = Image;
        Picasso.with(this.getContext()).load(imageUri).into(ivBasicImage);



        //TODO: create correct text view and photos after fixing error issues
      textViewAuthor.setText(books.author);
       BookName.setText(books.title);




        return view;
    }


    public void displayBook(String title){
        //TextView t =  this.getView().findViewById(R.id.TextOnFrag);
       // t.setText(title);


    }
}
