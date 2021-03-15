package com.hcl.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;

import java.util.concurrent.Semaphore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private GoogleSignInClient mGoogleSignInClient;

    Datasource dataSource;

    User[] users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            DataLock.getInstance().setObj(Thread.currentThread());
            dataSource = new Datasource(this);
            DataLock.getInstance().getJSONLock();
            Log.i("MYSTRING", dataSource.getMydata());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("MYSTRING 2", dataSource.getMydata());
        String data = dataSource.getData();
        Log.i("JSON", data);
        Gson gson = new Gson();
        users = gson.fromJson(data, User[].class);
        for(User u : users){
            Log.i("USER", u.toString());
        }

        findViewById(R.id.google_sign_out_button).setOnClickListener(this);

    }

    @Override
    protected void onStart(){
        super.onStart();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    @Override
    public void onClick(View v) {
        signOut();
    }

    public void signOut(){
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        handleSignOut();
                    }
                });
    }

    public void handleSignOut(){
        Intent signOutIntent = new Intent(this, LoginActivity.class);
        startActivity(signOutIntent);
    }

}