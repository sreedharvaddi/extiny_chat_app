package com.tinychatserver.extinychatapp;


import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by sreedhar on 12/6/17.
 */
public class TinyChatClientSocket implements ITinyChatClientSocket , Runnable {

    private static final String TAG = "TinyChatClientSocket";
    IClientSocketCallbacks callback;

    Socket mClientSocket;
    PrintWriter mPrintWriter;
    BufferedReader mBuffReader;
    TinyChatClientSocket() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mClientSocket = new Socket(InetAddress.getByName("52.91.109.76"), 1234);
                    mPrintWriter = new PrintWriter(new OutputStreamWriter(mClientSocket.getOutputStream()));
                    mBuffReader = new BufferedReader(new InputStreamReader(mClientSocket.getInputStream()));
                    if (mClientSocket.isConnected()) {
                        callback.onConnection(200);
                    }
                    else {
                        callback.onConnection(-200);
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "recever thread ");
                            if (mClientSocket != null && mClientSocket.isConnected()) {
                                Log.d(TAG, "recever thread mClientSocket connection "+mClientSocket.isConnected());
                                while (!Thread.currentThread().isInterrupted()) {
                                    String line;
                                    try {
                                        while ((line = mBuffReader.readLine()) != null) {
                                            Log.d(TAG, " msg "+line);
                                            parseMessage(line);
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    @Override
    public void sendMessage(final String s) {
        Log.d(TAG, "sendMessage()"+s);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mClientSocket != null && mClientSocket.isConnected()) {
                    mPrintWriter.write(s);
                    mPrintWriter.flush();
                    callback.onSendSuccess(new TinyChatMessage(s));
                }
            }
        }).start();

    }


    @Override
    public void connect() {
        Log.d(TAG, "connect()");
    }

    @Override
    public void clear() {
        Log.d(TAG, "clear()");
        try {
            mClientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register(IClientSocketCallbacks callbacks) {
        this.callback = callbacks;
    }

    @Override
    public void run() {
        Log.d(TAG, "recever thread ");
        if (mClientSocket != null && mClientSocket.isConnected()) {
            Log.d(TAG, "recever thread mClientSocket connection "+mClientSocket.isConnected());
            while (!Thread.currentThread().isInterrupted()) {
                String line;
                try {
                    while ((line = mBuffReader.readLine()) != null) {
                        Log.d(TAG, " msg "+line);
                        parseMessage(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void parseMessage(String line) {
        Gson gson = new Gson();
        TinyChatMessage message = gson.fromJson(line, TinyChatMessage.class);
        callback.onReceivedMessage(message);
    }
}
