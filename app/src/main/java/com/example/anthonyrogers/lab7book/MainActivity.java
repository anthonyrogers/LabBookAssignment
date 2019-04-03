package com.example.anthonyrogers.lab7book;

import android.os.Handler;
import android.os.Message;
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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class MainActivity extends AppCompatActivity implements BookListFragment.OnFragmentInteractionListener {

    private ViewPager mViewPager;
    boolean singlePane;
    FragmentManager fm;
    BookDetailsFragment bdf;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Thread t = new Thread() {
            @Override
            public void run() {

                URL BookurlJson;

                try {

                    BookurlJson = new URL("https://kamorris.com/lab/audlib/booksearch.php");

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
                    ArrayList<Book> booklist = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String o = jsonArray.get(i).toString();
                        JSONObject obj = new JSONObject(o);

                        Book book = new Book();
                        book.title = obj.getString("title");
                        book.author = obj.getString("author");
                        book.published = obj.getInt("published");
                        book.id = obj.getInt("book_id");
                        book.coverURL = obj.getString("cover_url");
                        booklist.add(book);
                    }

                    Message message = new Message();
                    message.obj = booklist;
                    message.what = 0;

                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        };
        t.start();
        
        //this grabs the the array of strings from android resources.
        final  String[] array = getResources().getStringArray(R.array.bookArray);
        bdf = new BookDetailsFragment();

        //this check to see if the frame2 id is available and says true if it is and false if its not.
        singlePane = findViewById(R.id.frame2) == null;


        //this runs the fragment if the phone is in portrait mode
        if(singlePane){
            mViewPager = findViewById(R.id.view_pager);
            mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), array));
        }
        fm = getSupportFragmentManager();

        //this sets frame one to a list and frame 2 to a single object fragment that will change textviews
        if (!singlePane) {

            fm.beginTransaction()
                    .replace(R.id.frame1, new BookListFragment())
                    .commit();

            fm.beginTransaction()
                    .replace(R.id.frame2, bdf)
                    .commit();
        }
    }

    @Override
    public void BookName(String nameOfBook) {

        //This creates a new Book Details Fragment Everytime a listview is clicked


        //TODO: unmark this if you would like to create a new fragment of BookDetailsFragment when
        //TODO: when viewing on a tablet or in landscape mode. Current we only create on instance and change textview
        // BookDetailsFragment df = BookDetailsFragment.newInstance(nameOfBook);
        // fm.beginTransaction().replace(R.id.frame2, df).addToBackStack(null).commit();

        bdf.displayBook(nameOfBook);
    }


    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {

            //int tfd = (int)msg.obj;
            ArrayList<Book> hey = (ArrayList<Book>) msg.obj;

            text.setText(hey.get(0).author);
        }
    };


}
