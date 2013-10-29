package com.codepath.apps.twitterapp.fragments;

import java.util.ArrayList;

import com.codepath.apps.twitterapp.R;
import com.codepath.apps.twitterapp.TweetAdapter;
import com.codepath.apps.twitterapp.models.Tweet;
import com.codepath.apps.twitterapp.models.User;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class TweetsListFragment extends Fragment {

	private OnClickListener listener;
	TweetAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
		return inf.inflate(R.layout.fragment_tweets_list, parent, false);
	}
	
	public interface OnClickListener {
		public void onClickEvent(User user);
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof OnClickListener) {
			listener = (OnClickListener) activity;
		}
		else {
			throw new ClassCastException("Must implement OnClickListener interface");
		}
	}
	
	public void onDetach() {
		super.onDetach();
		listener = null;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ArrayList<Tweet> tweets = new  ArrayList<Tweet>();
		adapter = new TweetAdapter(getActivity(), tweets);
		ListView lvTweets = (ListView) getActivity().findViewById(R.id.lvTweets);
		lvTweets.setAdapter(adapter);
		lvTweets.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position,
					long rowId) {
				Tweet t = (Tweet) adapter.getItemAtPosition(position);
				listener.onClickEvent(t.getUser());
			}
		});

	}
	
	public TweetAdapter getAdapter() {
		return adapter;
	}
}
