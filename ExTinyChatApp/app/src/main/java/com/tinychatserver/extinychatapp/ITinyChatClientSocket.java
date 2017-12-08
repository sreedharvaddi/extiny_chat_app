package com.tinychatserver.extinychatapp;

import java.util.List;

/**
 * Created by sreedhar on 12/7/17.
 */
public interface ITinyChatClientSocket {

    void sendMessage(String s);

    void connect();

    void clear();

    void register(IClientSocketCallbacks callbacks);


    interface IClientSocketCallbacks {
        void onHistory(List<TinyChatMessage> messageList);

        void onReceivedMessage(TinyChatMessage message);

        void onSendSuccess(TinyChatMessage message);

        void onConnection(int status);

        void onDisconnection();
    }
}
