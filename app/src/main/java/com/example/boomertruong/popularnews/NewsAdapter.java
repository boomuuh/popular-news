package com.example.boomertruong.popularnews;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.boomertruong.popularnews.Data.NewsInfo;
import com.example.boomertruong.popularnews.Data.Results;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by BoomerTruong on 12/2/14.
 */
public class NewsAdapter extends ArrayAdapter<NewsInfo> {

    private List<NewsInfo> mList;
    private Context mContext;
    public NewsAdapter(Context context) {
        super(context, R.layout.news_article_layout);

        mList = new ArrayList<>();
        this.mContext = context;
        new NewsAsyncTask().execute("http://api.nytimes.com/svc/mostpopular/v2/mostemailed/all-sections/1.json?api-key=fa5723452d7d2454cf24a2a3d920012c:10:66680873&offset=0");
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    static class NewsHolder {
        @InjectView(R.id.icon)
        ImageView img;

        @InjectView(R.id.first_line)
        TextView t1;

        @InjectView(R.id.second_line)
        TextView t2;

        NewsHolder(View v) {
            ButterKnife.inject(this,v);

        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("ADAPTER","GETVIEW");
        NewsHolder nh;
        if (convertView != null) {
            nh = (NewsHolder) convertView.getTag();

        } else {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.news_article_layout,parent,false);
            nh = new NewsHolder(convertView);
            convertView.setTag(nh);
        }
        NewsInfo ni = mList.get(position);

        Picasso.with(mContext)
                .load(ni.IMG_URL)
                .error(R.drawable.ic_launcher)
                .placeholder(R.drawable.ic_launcher)
                .fit()
                .into(nh.img);
        nh.t1.setText(ni.TITLE);
        nh.t2.setText(ni.BYLINE);


        return convertView;
    }



        private static final String TAG = "NewsAdapter";




        private static final String OFFSET = "&offset=";




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
                if (inputStream != null)
                    result = convertInputStreamToString(inputStream);
                else
                    result = "Did not work!";

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }


            return result;
        }

        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while ((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;

        }

        private class NewsAsyncTask extends AsyncTask<String, Void, String> {



            @Override
            protected void onPostExecute(String s) {
                JsonParser parser = new JsonParser();
                JsonArray results = parser.parse(s)
                                    .getAsJsonObject().get("results").getAsJsonArray();
                for (JsonElement item : results) {
                    final JsonObject gobj = item.getAsJsonObject();
                    final String html  = gobj.get("url").getAsString();
                    final String  by   = gobj.get("byline").getAsString();
                    final String title = gobj.get("title").getAsString();
                    NewsInfo i = new NewsInfo(title,by,html);
                    final JsonElement media  = gobj.get("media");
                    if (media.isJsonArray()) {
                        JsonArray media_array = media.getAsJsonArray().get(0).getAsJsonObject().getAsJsonArray("media-metadata");
                        for (JsonElement e : media_array)
                            i.addImage(e.getAsJsonObject().get("url").getAsString());

                    } else {
                        String t = "http://graphics8.nytimes.com/images/2014/12/07/books/review/07notables-1/07notables-1-thumbStandard.jpg";

                        i.addImage(t);
                    }


                    mList.add(i);

                }
               /* try {
                    JSONObject jobj = new JSONObject(s);
                    JSONArray jarr = jobj.getJSONArray("results");
                    final int size = jarr.length();

                    for (int i = 0; i < size; ++i) {
                        final JSONObject temp = parser.jarr.getJSONObject(i);


                        Log.i(TAG, temp.toString() + " temp");


                       mList.add(new NewsInfo("http://graphics8.nytimes.com/images/2014/12/07/books/review/07notables-1/07notables-1-thumbStandard.jpg", temp.getString("byline"), temp.getString("title"), temp.getString("url")));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

                 notifyDataSetChanged();
            }


            @Override
            protected String doInBackground(String... params) {
                return getArticles(params[0]);
            }
        }


}
