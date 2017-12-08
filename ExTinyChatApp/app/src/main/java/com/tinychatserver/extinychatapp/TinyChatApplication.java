package com.tinychatserver.extinychatapp;

import android.app.Application;
import android.util.Log;


/**
 * Created by sreedhar on 12/5/17.
 */
public class TinyChatApplication extends Application {
    TinyPresenter.TinyPresenterBuilder tinyPresenterBuilder;
    String TAG = "TinyChatApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        //IMantraRepo offlineRepo = new MantraOfflineRepo(new MantraDatasource(this));
        //IMantraRepo offlineRepo = new MantraOfflineRepo(new MantraContentResolverDatasource(this));
        //IMantraRepo onlineRepo = new MantraOnlineRepo();

        //IMantraModel model = MantrasModel.MantraModelBuilder.getBuilder()
        //        .setOfflineRepo(offlineRepo)
        //        .setOnlineRepo(onlineRepo).build();
        ITinyChatModel model = new TinyChatModel();

        tinyPresenterBuilder = new TinyPresenter.TinyPresenterBuilder()
                .setModel(model);

    }

    TinyPresenter.TinyPresenterBuilder getPresenterBuilder() {
        return tinyPresenterBuilder;
    }
}