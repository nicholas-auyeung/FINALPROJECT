package com.hcl.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private ObjectParser objectParser;

    String[] userProfileAttributes;

    RecyclerView attributeRecyclerView;

    List<UserAttribute> userAttributeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        objectParser = new UserParser();

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user_selected");

        //add google logic

        userAttributeList = objectParser.objectToList(user);

        attributeRecyclerView = findViewById(R.id.attributeRecyclerView);
        UserProfileAdapter userProfileAdapter = new UserProfileAdapter(userAttributeList);
        attributeRecyclerView.setAdapter(userProfileAdapter);
        attributeRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }
}