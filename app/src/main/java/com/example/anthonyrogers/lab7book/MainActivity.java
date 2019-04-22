package com.example.anthonyrogers.lab7book;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import edu.temple.audiobookplayer.AudiobookService;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements BookListFragment.OnFragmentInteractionListener,
                                                               BookDetailsFragment.AudioStatusSelectionDelegate {

    private ViewPager mViewPager;
    boolean singlePane;
    FragmentManager fm;
    MyViewPagerAdapter mMyViewPagerAdapter;
    BookDetailsFragment bdf;
    ArrayList<Book> list = new ArrayList<>();
    Button button;
    BookListFragment blf;

    // AudioService Properties
    AudiobookService.MediaControlBinder mAudioBinder;
    AudiobookService mAudioBookService;
    Boolean mIsAudioServiceBound = false;
    Handler mProgressHandler;

    String URL = "https://kamorris.com/lab/audlib/booksearch.php?search=";

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                getCurrentFragmentFromViewPager().updateSeekBarProgress(message.what);
            }
        };

        Thread t = new Thread() {
            @Override
            public void run() {

                URL BookurlJson;

                try {
                    BookurlJson = new URL("https://kamorris.com/lab/audlib/booksearch.php");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(BookurlJson.openStream()));

                    String response = "", tmpResponse;

                    tmpResponse = reader.readLine();
                    while (tmpResponse != null) {
                        response = response + tmpResponse;
                        tmpResponse = reader.readLine();
                    }

                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        String o = jsonArray.get(i).toString();
                        JSONObject obj = new JSONObject(o);

                        Book book = new Book();
                        book.title = obj.getString("title");
                        book.author = obj.getString("author");
                        book.published = obj.getInt("published");
                        book.id = obj.getInt("book_id");
                        book.coverURL = obj.getString("cover_url");
                        book.duration = obj.getInt("duration");
                        list.add(book);
                    }

                    Message message = new Message();
                    message.obj = list;
                    message.what = 0;

                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        t.start();
        if(singlePane) {
            final TextView textView = findViewById(R.id.txtBookEdit);
            button = findViewById(R.id.btnSearch);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Thread j = new Thread() {
                        @Override
                        public void run() {
                            URL BookurlJson;

                            try {
                                BookurlJson = new URL(URL + textView.getText().toString());
                                BufferedReader reader = new BufferedReader(
                                        new InputStreamReader(
                                                BookurlJson.openStream()));

                                String response = "", tmpResponse;

                                tmpResponse = reader.readLine();
                                while (tmpResponse != null) {
                                    response = response + tmpResponse;
                                    tmpResponse = reader.readLine();
                                }

                                JSONArray jsonArray = new JSONArray(response);
                                list = new ArrayList<Book>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    String o = jsonArray.get(i).toString();
                                    JSONObject obj = new JSONObject(o);

                                    Book book = new Book();
                                    book.title = obj.getString("title");
                                    book.author = obj.getString("author");
                                    book.published = obj.getInt("published");
                                    book.id = obj.getInt("book_id");
                                    book.coverURL = obj.getString("cover_url");
                                    book.duration = obj.getInt("duration");
                                    list.add(book);
                                }

                                Message message = new Message();
                                message.obj = list;
                                message.what = 0;

                                handler.sendMessage(message);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    j.start();
                }

            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindToAudioService();
    }

    @Override
    public void BookName(String nameOfBook) {

       for(int i = 0; i < list.size(); i++){
           if(list.get(i).title.equals(nameOfBook)){
               BookDetailsFragment df = BookDetailsFragment.newInstance(list.get(i));
               fm.beginTransaction().replace(R.id.frame2, df).addToBackStack(null).commit();
           }
        }
       // bdf.displayBook(nameOfBook);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {

            list = (ArrayList<Book>) msg.obj;

            //this check to see if the frame2 id is available and says true if it is and false if its not.
            singlePane = findViewById(R.id.frame2) == null;

            //this runs the fragment if the phone is in portrait mode
            if(singlePane){
                mViewPager = findViewById(R.id.view_pager);
                mMyViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), list);
                mViewPager.setAdapter(mMyViewPagerAdapter);
            }

            fm = getSupportFragmentManager();

            //this sets frame one to a list and frame 2 to a single object fragment that will change textviews
            if (!singlePane) {

                String curString;

                bdf = new BookDetailsFragment();

                ArrayList<String> arr = new ArrayList<>();
                blf = new BookListFragment();
                for(int j = 0; j<list.size(); j++){
                    arr.add(list.get(j).title);
                }
                blf.list = arr;
                fm.beginTransaction()
                        .replace(R.id.frame1, blf)
                        .commit();
                fm.beginTransaction()
                        .replace(R.id.frame2, bdf)
                        .commit();
            }
        }
    };

    // Interface Implementation for BookDetail's Audio Controls
    @Override
    public void playPauseStatusButtonPressed(BookDetailsFragment.AudioStatusSelection playPause, Book book, int position) {
        if (mAudioBinder != null) {
            switch (playPause) {
                case play:
                    if (position != 0) {
                        mAudioBinder.play(book.id, position);
                    } else {
                        mAudioBinder.play(book.id);
                    }
                case pause:
                    mAudioBinder.pause();
            }
        }
    }

    @Override
    public void stopButtonPressed(BookDetailsFragment.AudioStatusSelection stop) {
        if (mAudioBinder != null) {
            mAudioBinder.stop();
        }
    }

    @Override
    public void seekBarProgressBeingSet(int value) {
        if (mAudioBinder != null) {
            getCurrentFragmentFromViewPager().updateSeekBarProgress(value);
            mAudioBinder.seekTo(value);
        }
    }

    // Methods needed for service connection/binding to the audio service

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            Toast.makeText(getApplicationContext(), "Audio Service is connected", Toast.LENGTH_SHORT).show();
            mAudioBinder = (AudiobookService.MediaControlBinder) service;
            mAudioBinder.setProgressHandler(mProgressHandler);
            mIsAudioServiceBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            Toast.makeText(getApplicationContext(), "Service is disconnected", Toast.LENGTH_SHORT).show();
            mIsAudioServiceBound = false;
            mAudioBookService = null;
        }
    };

    void bindToAudioService() {
        bindService(new Intent(this, AudiobookService.class), mConnection, Context.BIND_AUTO_CREATE);
    }

    void unbindFromAudioService() {
        if (mIsAudioServiceBound) {
            unbindService(mConnection);
            mIsAudioServiceBound = false;
        }
    }

    private BookDetailsFragment getCurrentFragmentFromViewPager() {
        return (BookDetailsFragment) mMyViewPagerAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindFromAudioService();
    }
}
