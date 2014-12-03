package com.example.boomertruong.popularnews;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boomertruong.popularnews.Utils.NewsAPIHandler;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class PopularNewsMain extends ActionBarActivity {

    private static final String TAG = "MAIN";

    @InjectView(R.id.popular_news_list)
    ListView mNewsList;

    @InjectView(R.id.main_title)
    TextView mTitle;
    private  NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_news_main);
        ButterKnife.inject(this);

         adapter = new NewsAdapter(this);
        mNewsList.setAdapter(adapter);
        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PopularNewsMain.this," " + adapter.getCount(),Toast.LENGTH_SHORT).show();
            }
        });
        Log.d(TAG,"onCreate()");



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_popular_news_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
