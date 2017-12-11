package com.tinychatserver.extinychatapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sreedhar on 12/10/17.
 */
public class TinyChatRepo implements ITinyChatRepo {

    ContentResolver contentResolver;
    private String[] allColomns = {
            TinyChatDatabaseHelper.CLIENT_TIME,
            TinyChatDatabaseHelper.SERVER_TIME,
            TinyChatDatabaseHelper.MESSAGE,
            TinyChatDatabaseHelper.SYNCED
    };

    TinyChatRepo(Context ctx) {
        contentResolver = ctx.getContentResolver();
    }
    @Override
    public void saveMessage(TinyChatMessage message) {
        if (message.getSynced() == ITinyChatRepo.SYNCED) {
            contentResolver.update(TinyChatProvider.MESSAGE_CONTENT_URI, fromMessage(message),
                    "where "+TinyChatDatabaseHelper.CLIENT_TIME+ " = ?", new String[] {""+message.getClientTime()});
        }
        contentResolver.insert(TinyChatProvider.MESSAGE_CONTENT_URI, fromMessage(message));
    }

    @Override
    public List<TinyChatMessage> getMessages(long startTime, long endTime) {
        Cursor cursor = contentResolver.query(TinyChatProvider.MESSAGE_LIST_CONTENT_URI,
                allColomns, "where "+TinyChatDatabaseHelper.CLIENT_TIME+" >= "+startTime+" and "+TinyChatDatabaseHelper.CLIENT_TIME+" <= "+endTime,
                new String[]{TinyChatDatabaseHelper.CLIENT_TIME},
                " order by "+TinyChatDatabaseHelper.CLIENT_TIME);
        List<TinyChatMessage> list = new ArrayList<>();
        while(!cursor.isLast()) {
            list.add(makeMessageCursor(cursor));
        }
        return list;
    }

    private ContentValues fromMessage(TinyChatMessage message) {
        ContentValues values = new ContentValues();
        values.put(TinyChatDatabaseHelper.CLIENT_TIME, message.getClientTime());
        values.put(TinyChatDatabaseHelper.SERVER_TIME, message.getServerTime());
        values.put(TinyChatDatabaseHelper.MESSAGE, message.getMsg());
        values.put(TinyChatDatabaseHelper.SYNCED, message.getSynced());
        return values;
    }
    private TinyChatMessage makeMessageCursor(Cursor cursor) {
        TinyChatMessage message = new TinyChatMessage();
        message.setMsg(cursor.getString(cursor.getColumnIndex(TinyChatDatabaseHelper.MESSAGE)));
        message.setClientTime(cursor.getLong(cursor.getColumnIndex(TinyChatDatabaseHelper.CLIENT_TIME)));
        message.setServerTime(cursor.getLong(cursor.getColumnIndex(TinyChatDatabaseHelper.SERVER_TIME)));
        message.setSynced(cursor.getInt(cursor.getColumnIndex(TinyChatDatabaseHelper.SYNCED)));
        return message;
    }
}
