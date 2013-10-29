package com.codepath.apps.twitterapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.codepath.apps.twitterapp.fragments.UserTimelineFragment;
import com.codepath.apps.twitterapp.fragments.TweetsListFragment.OnClickListener;
import com.codepath.apps.twitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends FragmentActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		Intent i = getIntent();
		String screenName =  i.getStringExtra("screen_name");
		
		if (screenName == null) {
			loadProfileInfo();
		}
		else {
			loadUserProfileInfo(screenName);
		}
		
		loadUserTimeline(screenName);

	}
	
	private void loadProfileInfo() {
		
		TwitterClientApp.getRestClient().getMyInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				User user = User.fromJson(json);
				getActionBar().setTitle("@" + user.getScreenName());
				populateProfileHeader(user);
			}

		});		
	}

	private void loadUserProfileInfo(String screenName) {
		
		TwitterClientApp.getRestClient().getUserInfo(screenName, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonArray) {
				try {
					if (jsonArray.length() == 1) {
						User user = User.fromJson(jsonArray.getJSONObject(0));
						getActionBar().setTitle("@" + user.getScreenName());
						populateProfileHeader(user);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});	

	}
	
	private void loadUserTimeline(String screenName) {
		
		UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();		
		fts.replace(R.id.user_profile_frame_container, userTimelineFragment);
		fts.commit();
		
	}
	
	protected void populateProfileHeader(User user) {

		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

		tvName.setText(user.getName());
		tvTagline.setText(user.getTagline());
		tvFollowers.setText(user.getFollowersCount() + " Followers");
		tvFollowing.setText(user.getFriendsCount() + " Following");
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
	
	@Override
	public void onClickEvent(User user) {
		// Already in profile activity. do nothing.
	}
	
}
