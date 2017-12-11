package com.tinychatserver.extinychatapp;

import java.util.List;

/**
 * Created by sreedhar on 12/10/17.
 */
public interface ITinyChatRepo {

    static final int SYNCED = 1;
    static final int NOT_SYNCED = 0;
    void saveMessage(TinyChatMessage message);
    List<TinyChatMessage> getMessages(long startTime, long endTime);
}
