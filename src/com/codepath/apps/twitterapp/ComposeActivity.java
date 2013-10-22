package com.codepath.apps.twitterapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class ComposeActivity extends Activity {

	EditText etTweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);		
		etTweet = (EditText) findViewById(R.id.etTweet);
		
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
