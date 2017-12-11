package com.tinychatserver.extinychatapp;

import java.util.List;

/**
 * Created by sreedhar on 12/4/17.
 */
public interface ITinyChatModel {
    void fetchChatHistory();
    void connectServer();
    void disconnectServer();
    void sendMessage(TinyChatMessage msg);
    void register(ICallbacks callbacks);
    interface ICallbacks {
        void onFetchChatHistory(int status, List<TinyChatMessage> messageList);
        void onConnectServer(int status);
        void onDisconnectServer();
        void onSendMessage(int status, String msg);
        void onReceiveMessage(String msg);
    }
}
