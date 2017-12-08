package com.tinychatserver.extinychatapp;

import java.util.List;

/**
 * Created by sreedhar on 12/4/17.
 */
public interface ITinyChatView {
    void displayHistory(List<TinyChatMessage> messageList);
    void displayProgress();
    void displayError(int error);
    void displayReveivedMessage(String msg);
    void displaySentMessageStatus(String msg);
    void displayServerConnectionStatus(boolean status);
}

