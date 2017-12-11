package com.tinychatserver.extinychatapp;

import java.util.Calendar;

/**
 * Created by sreedhar on 12/4/17.
 */
public class TinyChatMessage {
    String msg;
    long clientTime;
    long serverTime;
    private int synced;

    public TinyChatMessage() {
    }

    public TinyChatMessage(String msg) {
        this.msg =  msg;
        this.clientTime = Calendar.getInstance().getTimeInMillis();
    }

    long getClientTime() {
        return clientTime;
    }

    long getServerTime() {
        return serverTime;
    }

    void setClientTime(long clientTime) {
        this.clientTime = clientTime;
    }

    void setMsg(String msg) {
        this.msg = msg;
    }

    void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }
    void parse(String json) {

    }
    String toJsonString() {
        String str =  "{\"msg\" :\""+ msg +"\",\"client_time\":"+clientTime;
        if (serverTime > 0) {
            str = str +",\"server\":"+serverTime;
        }
        str = str+"}";
        return str;
    }

    public String getMsg() {
        return msg;
    }

    public void setSynced(int synced) {
        this.synced = synced;
    }

    public int getSynced() {
        return synced;
    }

    @Override
    public String toString() {
        return msg+" is "+(new String[]{"not yet deliverd", "deliverd"}[synced]);
    }
}
