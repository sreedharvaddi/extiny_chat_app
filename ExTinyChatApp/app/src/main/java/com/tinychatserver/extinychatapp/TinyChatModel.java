package com.tinychatserver.extinychatapp;

import java.util.Calendar;
import java.util.List;

/**
 * Created by sreedhar on 12/5/17.
 */
public class TinyChatModel implements ITinyChatModel, ITinyChatClientSocket.IClientSocketCallbacks {

    private static final int NOT_SYNCED = 0;
    private static final int SYNCED = 1;
    ITinyChatClientSocket tinyChatClientSocket;
    ITinyChatRepo tinyChatRepo;
    long timeSince;
    private ICallbacks presenterCallbacks;

    public TinyChatModel() {

    }

    public TinyChatModel(TinyChatRepo repo) {
        tinyChatRepo = repo;
    }

    @Override
    public void fetchChatHistory() {
        if (tinyChatClientSocket != null && tinyChatClientSocket.isConnected()) {
            tinyChatClientSocket.sendMessage("{\"command\":\"history\",\"client_time\":" + Calendar.getInstance().getTimeInMillis() + ",\"since\":" + timeSince + "}\\n");
        }
        else {
            List<TinyChatMessage> messageList = tinyChatRepo.getMessages(timeSince, Calendar.getInstance().getTimeInMillis());
            presenterCallbacks.onFetchChatHistory(0, messageList);
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
        if (tinyChatClientSocket != null && tinyChatClientSocket.isConnected()) {
            tinyChatClientSocket.sendMessage(msg.toJsonString());
        }
        else {
            msg.setSynced(NOT_SYNCED);
            tinyChatRepo.saveMessage(msg);
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
        message.setSynced(SYNCED);
        tinyChatRepo.saveMessage(message);
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
