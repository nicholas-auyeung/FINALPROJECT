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

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private ObjectParser objectParser;

    private String loggedInUser;

    private String[] userProfileAttributes;

    private RecyclerView attributeRecyclerView;

    private List<UserAttribute> userAttributeList;

    private UserProfileAdapter userProfileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        objectParser = new UserParser();

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user_selected");

        userAttributeList = objectParser.objectToList(user);

        attributeRecyclerView = findViewById(R.id.attributeRecyclerView);

        SharedPreferences sharedPreferences = getSharedPreferences("LOGGED IN" , Context.MODE_PRIVATE);

        loggedInUser = sharedPreferences.getString("loggedInUser", "");

        if(user.getName().compareTo(loggedInUser) == 0) {
            userProfileAdapter = new UserProfileAdapter(userAttributeList, true);
        }else{
            userProfileAdapter = new UserProfileAdapter(userAttributeList, false);
        }
        attributeRecyclerView.setAdapter(userProfileAdapter);
        attributeRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }
}