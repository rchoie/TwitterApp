package com.codepath.apps.twitterapp;

import org.scribe.builder.api.Api;
//import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
    public static final String REST_URL = "https://api.twitter.com/1.1";
    public static final String REST_CONSUMER_KEY = "k95ERTNxWgFbGgH0Umi2g";
    public static final String REST_CONSUMER_SECRET = "xgGEcOAnnbLC0odKypG0oSVPo7iivwpyOhq5J1acNM";
    public static final String REST_CALLBACK_URL = "oauth://twitterapp";
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    public void getHomeTimeline(int count, AsyncHttpResponseHandler handler) {
  	  String apiUrl = getApiUrl("statuses/home_timeline.json");
  	  RequestParams params = new RequestParams();
  	  params.put("count", String.valueOf(count));
  	  getClient().get(apiUrl, params, handler);
    }
    
    public void getMentionsTimeline(AsyncHttpResponseHandler handler) {
    	  String apiUrl = getApiUrl("statuses/mentions_timeline.json");
    	  getClient().get(apiUrl, null, handler);
      }
    
    public void postTweet(String body, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", body);
        getClient().post(apiUrl, params, handler);
    }
    
    public void getUserTimeline(String screenName, AsyncHttpResponseHandler handler) {
    	String apiUrl = getApiUrl("statuses/user_timeline.json");
        if (screenName != null) {
            RequestParams params = new RequestParams();
        	params.put("screen_name", screenName);
          	getClient().get(apiUrl, params, handler);
        }
        else {
        	getClient().get(apiUrl, null, handler);
        }
    }
    
    public void getMyInfo(AsyncHttpResponseHandler handler) {
    	String apiUrl = getApiUrl("account/verify_credentials.json");
      	getClient().get(apiUrl, null, handler);
    }    
   
    public void getUserInfo(String screenName, AsyncHttpResponseHandler handler) {
    	String apiUrl = getApiUrl("users/lookup.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", screenName);
      	getClient().get(apiUrl, params, handler);
    } 
}