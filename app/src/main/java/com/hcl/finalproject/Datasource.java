package com.hcl.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.os.Handler;
import android.util.Log;
import android.view.textclassifier.TextLinks;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class Datasource{

    private final OkHttpClient client = new OkHttpClient();
    private String data;
    private DataSourceCallBack dataSourceCallBack;
    private Context context;
    private final Handler handler;

    Datasource(DataSourceCallBack dataSourceCallBack, Context context) throws Exception {
        this.dataSourceCallBack = dataSourceCallBack;
        this.context = context;
        handler = new Handler(Looper.getMainLooper());
        getJSONData();
    }

    public String getData() {
        return data;
    }

    public void getJSONData() throws Exception{
        Request request = new Request.Builder()
                .url("http://jsonplaceholder.typicode.com/users")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try(ResponseBody responseBody = response.body()){
                    if(!response.isSuccessful()){
                        throw new IOException("Unexpected code " + response);
                    }
                    data = responseBody.string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dataSourceCallBack.callback();
                        }
                    });
                }
            }
        });
    }

    public void runOnUiThread(Runnable r){
        handler.post(r);
    }
}
