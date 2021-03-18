package com.hcl.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private GoogleSignInClient mGoogleSignInClient;

    private Datasource dataSource;

    private User signInUser;

    private List<User> users = new ArrayList<>();

    private UserAdapter userAdapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.google_sign_out_button).setOnClickListener(this);

    }

    @Override
    protected void onStart(){
        super.onStart();

        try {
            dataSource = new Datasource(MainActivity.this, this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        SharedPreferences sharedPreferences = getSharedPreferences("LOGGED IN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(acct != null && sharedPreferences.getString("EDIT", "FALSE").compareTo("FALSE") == 0) {
            editor.putString("loggedInUser", acct.getDisplayName());
            editor.commit();
            signInUser = new User(acct.getId(), acct.getDisplayName(), acct.getEmail());
            users.add(signInUser);
        }else{
            editor.putString("EDIT", "FALSE");
            editor.commit();
            Intent intent = getIntent();
            User updatedUser = (User) intent.getSerializableExtra("user_updated");
            users.add(updatedUser);
            Toast.makeText(this, "User successfully updated", Toast.LENGTH_LONG).show();

        }

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

        users.addAll(Arrays.asList((gson.fromJson(data, User[].class))));

        recyclerView = findViewById(R.id.recycler_view);

        userAdapter = new UserAdapter(users, this);

        recyclerView.setAdapter(userAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}