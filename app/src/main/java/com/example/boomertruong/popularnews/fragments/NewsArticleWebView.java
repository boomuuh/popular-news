package com.example.boomertruong.popularnews.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;

import com.example.boomertruong.popularnews.R;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsArticleWebView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsArticleWebView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsArticleWebView extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String NEWS_ARTICLE_URL = "param1";



    private String mParam1;


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment NewsArticleWebView.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsArticleWebView newInstance(String param1) {
        NewsArticleWebView fragment = new NewsArticleWebView();
        Bundle args = new Bundle();
        args.putString(NEWS_ARTICLE_URL, param1);

        fragment.setArguments(args);
        return fragment;
    }

    public NewsArticleWebView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(NEWS_ARTICLE_URL);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_news_article_web_view, container, false);
       ButterKnife.inject(this,v);
        WebView wv = ButterKnife.findById(v,R.id.news_article_web_view);

        String url = getArguments().getString(NEWS_ARTICLE_URL, "https://www.google.com/");
        String html = "<html xmlns=\"" + url + "\"</html>";
       wv.setWebViewClient(new NewsWebViewClient());

        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
      /*  settings.setBuiltInZoomControls(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);*/

        wv.loadUrl(html);
        wv.loadData("", "text/html; charset=utf-8", "UTF-8");


        return v;
    }


    private class NewsWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {


            return true;

        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
