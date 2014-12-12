package com.example.boomertruong.popularnews.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.boomertruong.popularnews.NewsAdapter;
import com.example.boomertruong.popularnews.R;


public class NewsListFragment extends ListFragment implements AdapterView.OnItemClickListener{

    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.setListAdapter(new NewsAdapter(getActivity()));
        getListView().setOnItemClickListener(this);
        getListView().setOnScrollListener(new NewsFeedListener());
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
                Log.d(TAG, "1 loading: " + loading + " firstVisibleItem: " + firstVisibleItem + " visibleItemCount: " + visibleItemCount + " totalItemCount: " + totalItemCount);
                loading = false;
                prev = totalItemCount;
                count++;
            }

            if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + default_size)) {
                Log.d(TAG,"2 loading: " + loading + " firstVisibleItem: " + firstVisibleItem + " visibleItemCount: " + visibleItemCount + " totalItemCount: " + totalItemCount);
                ((NewsAdapter) getListAdapter()).loadMore(count * default_size);
                loading = true;
            }
        }

        public void reset() {
            count  = prev = 0;
            loading = true;
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Adapter adapter = getListAdapter();
        if (adapter != null && adapter instanceof NewsAdapter) {
            NewsAdapter mNewsAdapter = (NewsAdapter) adapter;
            String url = mNewsAdapter.getWebPage(position);
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(url)));
        } else {
            Toast.makeText(getActivity(),"Uh OH! empty adapter",Toast.LENGTH_LONG).show();
        }
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
