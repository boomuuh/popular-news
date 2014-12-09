package com.example.boomertruong.popularnews;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by BoomerTruong on 12/8/14.
 */
public class HoneyWell extends Application {
    private static HoneyWell mHoneyWell;

    private static RequestQueue mRequestQueue;




    public synchronized static HoneyWell getInstance() {
        return mHoneyWell;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHoneyWell = this;
        mRequestQueue = Volley.newRequestQueue(this);
    }
}
