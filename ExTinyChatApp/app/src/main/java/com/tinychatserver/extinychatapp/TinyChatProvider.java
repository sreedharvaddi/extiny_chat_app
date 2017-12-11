package com.tinychatserver.extinychatapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by sreedhar on 12/10/17.
 */
public class TinyChatProvider extends ContentProvider {
    static String AUTHORITY = "com.example.TinyChatProvider";
    static String MESSAGE_LIST = "message_list";
    static String MESSAGE = "message";
    static Uri MESSAGE_LIST_CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/"+MESSAGE_LIST);
    static Uri MESSAGE_CONTENT_URI = Uri.parse("conent://"+AUTHORITY+"/"+MESSAGE);

    static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        matcher.addURI(AUTHORITY, MESSAGE_LIST, 100);
        matcher.addURI(AUTHORITY, MESSAGE, 101);
    }

    TinyChatDatabaseHelper mChatDatabaseHelper;
    SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        mChatDatabaseHelper = new TinyChatDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        database = mChatDatabaseHelper.getReadableDatabase();
        return database.query(TinyChatDatabaseHelper.TINY_CHAT_TAB, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri)) {
            case 100:
                return "vnd.android.cursor.dir/vnd.com.example.provider.TINY_CHAT_TAB";
            case 101:
                return "vnd.android.cursor.item/vnd.com.example.provider.count";
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        database = mChatDatabaseHelper.getWritableDatabase();
        long insert_id = database.insert(TinyChatDatabaseHelper.TINY_CHAT_TAB, null, values);
        return uri.withAppendedPath(uri, "/"+insert_id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        database = mChatDatabaseHelper.getWritableDatabase();
        return database.delete(TinyChatDatabaseHelper.TINY_CHAT_TAB, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        database = mChatDatabaseHelper.getWritableDatabase();
        return database.update(TinyChatDatabaseHelper.TINY_CHAT_TAB, values, selection, selectionArgs);
    }
}
