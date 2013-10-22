package com.codepath.apps.twitterapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyAppDatabase extends SQLiteOpenHelper {

	// Manually updated version number
	private static final int DATABASE_VERSION = 1;

	public MyAppDatabase(Context context) {
		super(context, "database.db", null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// A real user's table would probably have more fields
		db.execSQL("CREATE TABLE TWEETS (name text, body text, userId text, userHandle text, userProfileImage text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// User's device has an old schema.  Simplest solution is to wipe
		// the table and start over.
		db.execSQL("DROP TABLE IF EXISTS TWEETS");
		onCreate(db);
	}

}