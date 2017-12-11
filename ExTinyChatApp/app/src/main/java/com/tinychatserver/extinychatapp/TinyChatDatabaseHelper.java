package com.tinychatserver.extinychatapp;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sreedhar on 12/10/17.
 */
public class TinyChatDatabaseHelper extends SQLiteOpenHelper {

    public static final String TINY_CHAT_TAB = "TINY_CHAT_TAB";
    public static final String CLIENT_TIME = "client_time";
    public static final String SERVER_TIME = "server_time";
    public static final String MESSAGE = "msg";
    public static final String SYNCED = "synced";

    private static final String CREATE_TABLE = "create table " + TINY_CHAT_TAB
            + "( "+CLIENT_TIME+" number primary key , "
            + MESSAGE + " text ,"
            + SYNCED + " boolean ,"
            + SERVER_TIME + " number )";

    public TinyChatDatabaseHelper(Context context) {

        super(context, "tiny_messages_db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP table if exists "+TINY_CHAT_TAB);
        onCreate(db);
    }
}
