package com.tinychatserver.extinychatapp;

import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by sreedhar on 12/4/17.
 */
public class TinyPresenter implements ITinyChatModel.ICallbacks {
    private static final String TAG = "TinyPresenter";
    Context context;
    ITinyChatView tinyChatView;
    ITinyChatModel chatModel;
    static final int SUCCESS = 0;
    static final int FAILUER = -1;
    static final int CONNECTED = 200;
    static final int DISCONNECTED = -200;

    TinyPresenter(Context ctx, ITinyChatView view) {
        this.context = ctx;
        this.tinyChatView = view;
    }

    TinyPresenter() {

    }

    TinyPresenter(ITinyChatModel model) {
        chatModel = model;
    }

    public void connectServer() {
        Log.d(TAG, "connectServer()");
        chatModel.connectServer();
    }

    public void disconnectServer() {
        chatModel.disconnectServer();
    }

    public void getChatHistory() {
        chatModel.fetchChatHistory();
    }

    public void sendMessage(String msg) {
        chatModel.sendMessage(new TinyChatMessage(msg));
    }
    @Override
    public void onFetchChatHistory(int status, List<TinyChatMessage> messageList) {
        if (status == SUCCESS) {
            tinyChatView.displayHistory(messageList);
        }
        else {
            tinyChatView.displayError(FAILUER);
        }
    }

    @Override
    public void onConnectServer(int status) {
        Log.d(TAG, "onConnectServer "+status);
        tinyChatView.displayServerConnectionStatus(status == CONNECTED);
    }

    @Override
    public void onDisconnectServer() {
        tinyChatView.displayServerConnectionStatus(false);
    }

    @Override
    public void onSendMessage(int status, String msg) {
        if (status == SUCCESS) {
            tinyChatView.displaySentMessageStatus("delivered");
        }
        else {
            tinyChatView.displaySentMessageStatus("failed");
        }
    }

    @Override
    public void onReceiveMessage(String msg) {
        if (msg != null && !msg.isEmpty()) {
            tinyChatView.displayReveivedMessage(msg);
        }
    }


    public static class TinyPresenterBuilder {
        ITinyChatModel model;
        ITinyChatView view;
        public TinyPresenterBuilder setModel(ITinyChatModel model) {
            this.model = model;
            return this;
        }
        public TinyPresenterBuilder setView(ITinyChatView view) {
            this.view = view;
            return this;
        }
        TinyPresenter build() {
            if (model != null) {
                TinyPresenter presenter = new TinyPresenter(model);
                model.register(presenter);
                presenter.tinyChatView = view;
                return presenter;
            }
            return null;
        }
    }
}
