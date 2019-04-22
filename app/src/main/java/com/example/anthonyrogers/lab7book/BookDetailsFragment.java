package com.example.anthonyrogers.lab7book;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class BookDetailsFragment extends Fragment {

    // Interface for fragment-activity communication
    AudioStatusSelectionDelegate delegate;
    Boolean isPlayingAudio;
    SeekBar mSeekBar;

    TextView textViewAuthor;
    TextView BookName;
    ImageView Image;
    Book books = new Book();
    Book currentBook;

    public BookDetailsFragment() {}

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_details, container, false);

        final ImageButton playPauseButton = view.findViewById(R.id.playPauseButton);
        ImageButton stopButton = view.findViewById(R.id.stopButton);
        mSeekBar = view.findViewById(R.id.seekBar);
        mSeekBar.setMax(books.duration);
        delegate = (AudioStatusSelectionDelegate) getContext();
        isPlayingAudio = false;

        textViewAuthor = view.findViewById(R.id.TextOnFrag);
        BookName = view.findViewById(R.id.lblBookTitle);
        Image = view.findViewById(R.id.imageView4);
        String imageUri = books.coverURL;
        ImageView ivBasicImage = Image;
        Picasso.with(this.getContext()).load(imageUri).into(ivBasicImage);

        //TODO: create correct text view and photos after fixing error issues
        textViewAuthor.setText(books.author);
        BookName.setText(books.title);

        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isPlayingAudio) {
                    delegate.playPauseStatusButtonPressed(AudioStatusSelection.play, books, mSeekBar.getProgress());
                    Log.e("crrent seek status", String.valueOf(mSeekBar.getProgress()));
                    playPauseButton.setImageResource(R.drawable.pause2x);
                    isPlayingAudio = true;
                } else {
                    delegate.playPauseStatusButtonPressed(AudioStatusSelection.pause, books, mSeekBar.getProgress());
                    playPauseButton.setImageResource(R.drawable.play2x);
                    isPlayingAudio = false;
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delegate.stopButtonPressed(AudioStatusSelection.stop);
                playPauseButton.setImageResource(R.drawable.play2x);
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                delegate.seekBarProgressBeingSet(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        return view;
    }

    public void displayBook(String title) {
        //TextView t =  this.getView().findViewById(R.id.TextOnFrag);
        // t.setText(title);
    }

    public void updateSeekBarProgress(int value) {
        if (mSeekBar != null) {
            mSeekBar.setProgress(value);
        }
    }

    interface AudioStatusSelectionDelegate {
        void playPauseStatusButtonPressed(AudioStatusSelection playPause, Book book, int position);
        void stopButtonPressed(AudioStatusSelection stop);
        void seekBarProgressBeingSet(int value);
    }

    enum AudioStatusSelection {
        play, pause, stop
    }
}
