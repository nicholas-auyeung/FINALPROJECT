package com.hcl.finalproject;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class UserFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    RecyclerView userRecyclerView;
    private UserAdapter userAdapter;
    private static List<User> userList;
    private static MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        userRecyclerView = view.findViewById(R.id.recycler_view_user);
        userAdapter = new UserAdapter(userList, mainActivity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        userRecyclerView.setLayoutManager(linearLayoutManager);
        userRecyclerView.setAdapter(userAdapter);
        return view;
    }

    public UserFragment(List<User> userList, MainActivity mainActivity) {
        this.userList = userList;
        this.mainActivity = mainActivity;
    }

    public UserFragment() {
        super();
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try{
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }catch (ActivityNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, requestCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            userAdapter.getCameraProfileImageInCache(imageBitmap);
        }
    }
}