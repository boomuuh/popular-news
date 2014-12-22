package com.example.boomertruong.popularnews;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.boomertruong.popularnews.fragments.NewsArticleWebView;
import com.example.boomertruong.popularnews.fragments.NewsListFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class PopularNewsMain extends ActionBarActivity implements NewsListFragment.OnFragmentInteractionListener {

    private static final String TAG = "MAIN";
    private static final String NEWS_WEB_VIEW_FRAGMENT = "news_article_web_view_frag";
   /* @InjectView(R.id.popular_news_list)
    ListView mNewsList;
    */

    @InjectView(R.id.web_view_container)
    FrameLayout mWebContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_news_main);
        ButterKnife.inject(this);

    }

    @Override
    public void onBackPressed() {
        Log.d(TAG,"onBackPressed() backstack count: " + getFragmentManager().getBackStackEntryCount());
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }else{
            super.onBackPressed();
        }

    }

    @Override
    public void onFragmentInteraction(String uri) {
        Log.d(TAG,"Adding WebView Fragment");
        NewsArticleWebView wv = NewsArticleWebView.newInstance(uri);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.web_view_container, wv, "NEWS_WEB_VIEW");
        ft.addToBackStack(null);
        ft.commit();

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
