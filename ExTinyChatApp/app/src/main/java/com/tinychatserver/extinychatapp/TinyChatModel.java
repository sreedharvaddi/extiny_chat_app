package com.tinychatserver.extinychatapp;

import java.util.Calendar;
import java.util.List;

/**
 * Created by sreedhar on 12/5/17.
 */
public class TinyChatModel implements ITinyChatModel, ITinyChatClientSocket.IClientSocketCallbacks {

    ITinyChatClientSocket tinyChatClientSocket;
    long timeSince;
    private ICallbacks presenterCallbacks;

    public TinyChatModel() {

    }
    @Override
    public void fetchChatHistory() {
        if (tinyChatClientSocket != null) {
            tinyChatClientSocket.sendMessage("{\"command\":\"history\",\"client_time\":" + Calendar.getInstance().getTimeInMillis() + ",\"since\":" + timeSince + "}\\n");
        }
    }

    @Override
    public void connectServer() {
        if (tinyChatClientSocket == null) {
            tinyChatClientSocket = new TinyChatClientSocket();
            tinyChatClientSocket.register(this);
        }
    }

    @Override
    public void disconnectServer() {
        if (tinyChatClientSocket != null) {
            tinyChatClientSocket.clear();
            tinyChatClientSocket = null;
        }
    }

    @Override
    public void sendMessage(TinyChatMessage msg) {
        if (tinyChatClientSocket != null) {
            tinyChatClientSocket.sendMessage(msg.toJsonString());
        }
    }

    @Override
    public void onHistory(List<TinyChatMessage> messageList) {
        presenterCallbacks.onFetchChatHistory(0, messageList);
        timeSince = Calendar.getInstance().getTimeInMillis();

    }

    @Override
    public void onReceivedMessage(TinyChatMessage message) {
        presenterCallbacks.onReceiveMessage(message.getMsg());
        timeSince = Calendar.getInstance().getTimeInMillis();

    }

    @Override
    public void onSendSuccess(TinyChatMessage message) {
        presenterCallbacks.onSendMessage(0, message.getMsg());
        timeSince = Calendar.getInstance().getTimeInMillis();
    }

    @Override
    public void onConnection(int status) {
        presenterCallbacks.onConnectServer(status);
        timeSince = Calendar.getInstance().getTimeInMillis();
    }

    @Override
    public void onDisconnection() {
        presenterCallbacks.onDisconnectServer();
        timeSince = Calendar.getInstance().getTimeInMillis();
    }

    @Override
    public void register(ICallbacks callbacks) {
        this.presenterCallbacks = callbacks;
    }
}
