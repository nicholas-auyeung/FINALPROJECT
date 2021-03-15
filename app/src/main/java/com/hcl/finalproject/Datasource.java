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
    private User[] users;
    private Context context;
    private String mydata = "My String";
    private String data;

    Datasource(Context context) throws Exception {
        this.context = context;
        getJSONData();
    }

    public User[] getUsers() {

        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public String getMydata() {
        return mydata;
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

                                    mydata = "IN RESPONSE";
                                    data = responseBody.string();

                                    DataLock.getInstance().releaseJSONLock();

                                    /*
                                    Gson gson = new Gson();
                                    users = gson.fromJson(responseBody.string(), User[].class);*/

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
    }


}
