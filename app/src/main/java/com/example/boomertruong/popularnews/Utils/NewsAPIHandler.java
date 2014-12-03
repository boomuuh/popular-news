package com.example.boomertruong.popularnews.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.boomertruong.popularnews.Data.NewsInfo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by BoomerTruong on 12/2/14.
 */
public class NewsAPIHandler {

    private static final String TAG = "NewsAPIHandler";

    private int MAX_NEWS_LIST;
    private String API_URL;


    private List<NewsInfo> list;
    private static final String HTTP_TYPE        = "http://";
    private static final String BASE_NEW_URL     = "api.nytimes.com/svc";
    private static final String MOST_POPULAR     = "mostpopular";
    private static final String API_NEWS_VERSION = "v2";
    private static final String NEWS_CATEGORY    = "mostemailed";
    private static final String SECTION          = "all-sections";
    private static final String FORMAT_TYPE      = "1.json";
    private static final String NEWS_API_KEY     = "?api-key=fa5723452d7d2454cf24a2a3d920012c:10:66680873";
    private static final String OFFSET           = "&offset=";


    public NewsAPIHandler() {
        list = new ArrayList<>();
        this.MAX_NEWS_LIST = 0;
        this.API_URL = getNewsURL(0);
        new NewsAsyncTask().execute("http://api.nytimes.com/svc/mostpopular/v2/mostemailed/all-sections/1.json?api-key=fa5723452d7d2454cf24a2a3d920012c:10:66680873&offset=0");
    }

    public List<NewsInfo> getArticles() {
        return list;
    }
    private String getNewsURL(int offset) {
        return    HTTP_TYPE +  BASE_NEW_URL + "/"
                + MOST_POPULAR + "/"
                + API_NEWS_VERSION + "/"
                + NEWS_CATEGORY + "/"
                + SECTION + "/"
                + FORMAT_TYPE + "/"
                + NEWS_API_KEY
                + OFFSET + offset;

    }

    private String getArticles(String url) {


        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }


        return result;
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

  /*  public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
*/






    private class NewsAsyncTask extends AsyncTask<String,Void,String> {

        List<NewsInfo> mlist;
        @Override
        protected void onPreExecute() {
            mlist = list;
        }
        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jobj = new JSONObject(s);
                JSONArray  jarr = jobj.getJSONArray("results");
                final int size = jarr.length();
                for (int i = 0; i < size; ++i) {
                    final JSONObject temp = jarr.getJSONObject(i);


                    Log.i(TAG,temp.toString() + " temp");


                    mlist.add(new NewsInfo("http:\\/\\/graphics8.nytimes.com\\/images\\/2014\\/12\\/07\\/books\\/review\\/07notables-1\\/07notables-1-thumbStandard.jpg",temp.getString("byline"), temp.getString("title"),temp.getString("url")));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }



        @Override
        protected String doInBackground(String... params) {
            return getArticles(params[0]);
        }
    }
}
