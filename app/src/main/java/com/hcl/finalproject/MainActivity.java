package com.hcl.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private Datasource dataSource;

    private User[] users;

    private UserAdapter userAdapter;

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            dataSource = new Datasource(MainActivity.this, this);
        } catch (Exception e) {
            e.printStackTrace();
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




    public void setRecyclerView(){

        String data = dataSource.getData();

        Gson gson = new Gson();

        users = gson.fromJson(data, User[].class);

        recyclerView = findViewById(R.id.recycler_view);

        userAdapter = new UserAdapter(users, this);

        recyclerView.setAdapter(userAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}