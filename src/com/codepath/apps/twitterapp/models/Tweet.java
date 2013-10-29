package com.codepath.apps.twitterapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.ArrayList;

@Table(name = "Tweet")
public class Tweet extends BaseModel {
	
	@Column(name = "userId")
	String userId;
	@Column(name = "userHandle")
	String userHandle;
	@Column(name = "userProfileImage")
	String userProfileImage;
	@Column(name = "body")
	String body;
	
    private User user;

    public Tweet() {
    	super();
    }
    
    public User getUser() {
        return user;
    }

    public String getBody() {
        return getString("text");
    }

    public long getUserId() {
        return getLong("id");
    }

    public boolean isFavorited() {
        return getBoolean("favorited");
    }

    public boolean isRetweeted() {
        return getBoolean("retweeted");
    }

    public static Tweet fromJson(JSONObject jsonObject) {
    //public Tweet(JSONObject jsonObject) {

    	//super();
    	
        Tweet tweet = new Tweet();
        
    	try {
            tweet.jsonObject = jsonObject;
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
            
            tweet.userId = String.valueOf(tweet.user.getUserId());
            tweet.userHandle = tweet.user.getScreenName();
            tweet.userProfileImage = tweet.user.getProfileImageUrl();
            tweet.body = tweet.jsonObject.getString("text");
            
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return tweet;
    }

    public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            //Tweet tweet = new Tweet(tweetJson);
            Tweet tweet = Tweet.fromJson(tweetJson);

            if (tweet != null) {
            	//tweet.save();
                tweets.add(tweet);
            }
        }

        return tweets;
    }
}