package com.example.boomertruong.popularnews.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BoomerTruong on 12/2/14.
 */
public class NewsInfo {

    public String IMG_URL;
    public String URL;
    public String TITLE;
    public String BYLINE;
    public List<String> thumbnails = new ArrayList<>();
    public NewsInfo (String title, String author,String url) {
        this.TITLE = title;
        this.BYLINE = author;
        this.URL = url;

    }

    public void addImage(String url) {
        thumbnails.add(url);
        if (IMG_URL == null)
            IMG_URL = url;
    }


    public NewsInfo (String img, String title, String auth,String url) {
        this.IMG_URL = img;
        this.TITLE = title;
        this.BYLINE = auth;
        this.URL = url;
    }

    public NewsInfo (String json) {

    }

    @Override
    public String toString() {
        return IMG_URL + " " + URL + " " + TITLE + " " + BYLINE;
    }
}
