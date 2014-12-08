package com.example.boomertruong.popularnews;

import android.app.FragmentManager;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.boomertruong.popularnews.fragments.NewsArticleWebView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class PopularNewsMain extends ActionBarActivity implements NewsArticleWebView.OnFragmentInteractionListener {

    private static final String TAG = "MAIN";
    private static final String NEWS_WEB_VIEW_FRAGMENT = "news_article_web_view_frag";
    @InjectView(R.id.popular_news_list)
    ListView mNewsList;

    private NetworkChangeReceiver mNetworkListener;
    private onNewsArticleClickListener mItemClicker;
    private NewsFeedListener mScrollListener;
    private  NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_news_main);
        ButterKnife.inject(this);

         adapter = new NewsAdapter(this);
         mItemClicker = new onNewsArticleClickListener();
        mScrollListener = new NewsFeedListener();
         mNewsList.setAdapter(adapter);
        mNewsList.setOnItemClickListener(mItemClicker);
        mNewsList.setOnScrollListener(mScrollListener);
    }

    @Override
    protected void onResume() {
        if (mNetworkListener == null)
            mNetworkListener = new NetworkChangeReceiver();

        registerReceiver(mNetworkListener,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(mNetworkListener);
        super.onPause();
    }

    public class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            ConnectivityManager cm =(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE );
            NetworkInfo activeNetInfo = cm.getActiveNetworkInfo();
            if (activeNetInfo != null && activeNetInfo.isConnected()) {
                mScrollListener.reset();
                adapter.loadMore(0);
            }


        }
    }


    private class NewsFeedListener implements AbsListView.OnScrollListener {
        private static final String TAG = "NewsFeedListener";
        private static final int default_size = 20;
        private int count = 0;
        private int prev  = 0;
        private boolean loading = true;

        public NewsFeedListener() {}

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (loading && totalItemCount > prev) {
                    Log.d(TAG,"1 loading: " + loading + " firstVisibleItem: " + firstVisibleItem + " visibleItemCount: " + visibleItemCount + " totalItemCount: " + totalItemCount);
                    loading = false;
                    prev = totalItemCount;
                    count++;
                }

            if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + default_size)) {
                Log.d(TAG,"2 loading: " + loading + " firstVisibleItem: " + firstVisibleItem + " visibleItemCount: " + visibleItemCount + " totalItemCount: " + totalItemCount);
                adapter.loadMore(count * default_size);
                loading = true;
            }
        }

        public void reset() {
            count  = prev = 0;
            loading = true;
        }
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class onNewsArticleClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String url = adapter.getWebPage(position);



               Uri uri = Uri.parse(url);
               startActivity(new Intent(Intent.ACTION_VIEW,uri));
           /* FragmentManager fm = getFragmentManager();
            NewsArticleWebView f = (NewsArticleWebView) fm.findFragmentByTag(NEWS_WEB_VIEW_FRAGMENT);
            if (f == null) {
               f =  NewsArticleWebView.newInstance(url);

            }else {
                fm.beginTransaction().remove(f).commit();
            }

            fm.beginTransaction().add(f,NEWS_WEB_VIEW_FRAGMENT).commit();*/
        }


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
