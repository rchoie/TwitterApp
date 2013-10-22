package com.codepath.apps.twitterapp;

import java.util.ArrayList;

import org.json.JSONArray;

import com.codepath.apps.twitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class TimelineActivity extends Activity {

	final int TIMELINE_COUNT = 25;
	public static final int REFRESH_CODE = 100;
	public static final int COMPOSE_CODE = 101;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		getHomeTimeline();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    	case R.id.compose:
	    		startActivityForResult(new Intent(this, ComposeActivity.class), TimelineActivity.COMPOSE_CODE);
	    		break;
	    		
	    	case R.id.refresh:
	    		getHomeTimeline();
	    		break;

	    	default:
	    		break;
	    }

	    return true;
	}

	private void getHomeTimeline() {
		TwitterClientApp.getRestClient().getHomeTimeline(TIMELINE_COUNT, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
				ListView lvTweets = (ListView) findViewById(R.id.lvTweets);
				TweetAdapter adapter = new TweetAdapter(getBaseContext(), tweets);
				lvTweets.setAdapter(adapter);
			}
		});
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == TimelineActivity.REFRESH_CODE) {
			if (resultCode == RESULT_OK) {
	    		getHomeTimeline();
			}
		}
		else if (requestCode == TimelineActivity.COMPOSE_CODE) {
			if (resultCode == RESULT_OK) {
				String tweet = (String) data.getStringExtra("tweet");
	    		TwitterClientApp.getRestClient().postTweet(tweet, new JsonHttpResponseHandler() {
	    			@Override
	    			public void onSuccess(JSONArray jsonTweets) {
	    	    		getHomeTimeline();
	    			}
	    		});
			}
		}
	}
	
}
