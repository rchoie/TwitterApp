package com.codepath.apps.twitterapp;

import org.json.JSONObject;

import com.codepath.apps.twitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ComposeActivity extends Activity {

	EditText etTweet;
	TextView tvScreenName;
	ImageView ivImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);		
		etTweet = (EditText) findViewById(R.id.etTweet);
		tvScreenName = (TextView) findViewById(R.id.tvScreenName);
		ivImage = (ImageView) findViewById(R.id.ivImage);
		
		TwitterClientApp.getRestClient().getMyInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				User user = User.fromJson(json);
				tvScreenName.setText("@" + user.getScreenName());
				ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivImage);
			}

		});	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}

	public void onCancel(View v) {
		finish();
	}
	
	public void onTweet(View v) {
		Intent i = getIntent();
        String tweet = etTweet.getText().toString();
        if (tweet.length() > 0) {
        	i.putExtra("tweet", tweet);
        	setResult(RESULT_OK, i);
        	finish();
        }
	}
}
