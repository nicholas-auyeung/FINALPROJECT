package com.hcl.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class ProfileActivity extends AppCompatActivity{

    private ObjectParser objectParser;

    private String loggedInUser;

    private RecyclerView attributeRecyclerView;

    private List<UserAttribute> userAttributeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_profile);
        super.onCreate(savedInstanceState);

        attributeRecyclerView = findViewById(R.id.recycler_view_profile);

        objectParser = new UserParser();

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user_selected");

        userAttributeList = objectParser.objectToList(user);

        SharedPreferences sharedPreferences = getSharedPreferences("LOGGED IN" , Context.MODE_PRIVATE);

        loggedInUser = sharedPreferences.getString("loggedInUser", "");

        if(user.getName().compareTo(loggedInUser) == 0) {
            UserProfileEditAdapter userProfileEditAdapter = new UserProfileEditAdapter(userAttributeList);
            attributeRecyclerView.setAdapter(userProfileEditAdapter);

        }else{
            UserProfileAdapter userProfileAdapter = new UserProfileAdapter(userAttributeList);
            attributeRecyclerView.setAdapter(userProfileAdapter);
            attributeRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.updateButton){


        }
        return super.onOptionsItemSelected(item);
    }



}