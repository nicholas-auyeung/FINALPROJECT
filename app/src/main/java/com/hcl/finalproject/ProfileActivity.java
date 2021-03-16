package com.hcl.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.io.Serializable;

public class ProfileActivity extends AppCompatActivity {

    private ObjectParser objectParser;

    String[] userProfileAttributes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        objectParser = new UserParser();

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user_selected");
        


    }
}