package com.tinychatserver.extinychatapp;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ITinyChatView {

    Button b_send;
    EditText et_message;
    TextView tv_response;
    TinyPresenter presenter;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tiny_chat_activity_main);

        b_send = (Button) findViewById(R.id.b_send);
        et_message = (EditText) findViewById(R.id.et_message);
        tv_response = (TextView) findViewById(R.id.tv_response);

        //tv_response.setMovementMethod(new ScrollingMovementMethod());

        presenter = ((TinyChatApplication) getApplicationContext()).getPresenterBuilder().setView(this).build();
        b_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.d("test", "<><> Button Clicked with msg: " + et_message.getText().toString());
                presenter.sendMessage(et_message.getText().toString());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //presenter.register(this);
        presenter.connectServer();
    }

    @Override
    public void onStop() {
        super.onStop();
        //presenter.unregister(this);
        presenter.disconnectServer();
    }

    @Override
    public void displayHistory(List<TinyChatMessage> messageList) {
        for (int i = 0; i < messageList.size(); i++) {
        }
    }

    @Override
    public void displayProgress() {

    }

    @Override
    public void displayError(int error) {

    }

    @Override
    public void displayReveivedMessage(final String msg) {
        Log.d(TAG, "displayReceived Message "+msg);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_response.append(msg);
            }
        });
    }

    @Override
    public void displaySentMessageStatus(String msg) {

    }

    @Override
    public void displayServerConnectionStatus(final boolean status) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Connection status "+status, Toast.LENGTH_LONG).show();
                presenter.getChatHistory();
            }
        });
    }
}
