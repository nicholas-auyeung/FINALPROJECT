package com.hcl.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProfileActivity extends AppCompatActivity{

    private ObjectParser objectParser;

    private String loggedInUser;

    private RecyclerView attributeRecyclerView;

    private List<UserAttribute> userAttributeList;

    private UserProfileEditAdapter userProfileEditAdapter;

    private User user;

    private String mState = "";

    private MenuItem updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_profile);
        super.onCreate(savedInstanceState);
        attributeRecyclerView = findViewById(R.id.recycler_view_profile);

        objectParser = new UserParser();

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user_selected");

        userAttributeList = objectParser.objectToList(user);

        SharedPreferences sharedPreferences = getSharedPreferences("LOGGED IN" , Context.MODE_PRIVATE);

        loggedInUser = sharedPreferences.getString("loggedInUser", "");

        if(user.getName().compareTo(loggedInUser) == 0) {
            userProfileEditAdapter = new UserProfileEditAdapter(userAttributeList);
            attributeRecyclerView.setAdapter(userProfileEditAdapter);

        }else{
            mState = "HIDE_MENU";
            invalidateOptionsMenu();
            UserProfileAdapter userProfileAdapter = new UserProfileAdapter(userAttributeList);
            attributeRecyclerView.setAdapter(userProfileAdapter);
            attributeRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        updateButton = menu.findItem(R.id.updateButton);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if(mState.compareTo("HIDE_MENU") == 0){
            updateButton.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if(id == R.id.updateButton){
            Log.i("UPDATED", String.valueOf(userProfileEditAdapter.getUserAttributeList()));
            SharedPreferences sharedPreferences = getSharedPreferences("LOGGED IN", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("EDIT", "TRUE");
            editor.commit();
            user = objectParser.updateUser(user, userProfileEditAdapter.getUserAttributeList());
            Log.i("UPDATED USER", user.toString());
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("user_updated", user);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }



}