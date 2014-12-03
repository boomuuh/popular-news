package com.example.boomertruong.popularnews;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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


    private onNewsArticleClickListener mItemClicker;
    private  NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_news_main);
        ButterKnife.inject(this);

         adapter = new NewsAdapter(this);
         mItemClicker = new onNewsArticleClickListener();
         mNewsList.setAdapter(adapter);
        mNewsList.setOnItemClickListener(mItemClicker);
        mNewsList.setOnScrollListener(new NewsFeedListener());
    }

    private class NewsFeedListener implements AbsListView.OnScrollListener {

        private int default_size = 20;
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
                    loading = false;
                    prev = totalItemCount;
                    count++;
                }

            if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + default_size)) {
                adapter.loadMore(count * default_size);
                loading = true;
            }
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
