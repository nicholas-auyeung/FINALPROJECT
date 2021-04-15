package com.hcl.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.util.LruCache;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity implements DataSourceCallBack{

    private GoogleSignInClient mGoogleSignInClient;

    private Datasource dataSource;

    private ObjectParser objectParser = new UserParser();;

    private User signInUser;

    private static List<User> users = new ArrayList<>();

    private TextView textHeader;

    private MenuItem signOutButton;

    private MenuItem updateButton;

    private MenuItem geoButton;

    private boolean mEditState = false;

    private boolean mProfileState = false;

    private boolean mMapState = false;

    private static int position;

    private static boolean setup = false;

    private static UserFragment userFragment;

    private UserProfileEditFragment userProfileEditFragment;

    private static boolean swapProfileEdit;

    private static boolean swapProfile;

    private static boolean swapMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_main);
        textHeader = (TextView) findViewById(R.id.main_header_text);
    }

    @Override
    protected void onStart(){
        super.onStart();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if(acct != null && setup == false){
            try {
                dataSource = new Datasource(this, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            signInUser = new User(acct.getId(), acct.getDisplayName(), acct.getEmail());
            users.add(signInUser);
            setup = true;
        }else{
            if(swapProfileEdit){
                swapProfileEdit = false;
                swapUserProfileEditFragment(position);
            }else if(swapProfile){
                swapProfile = false;
                swapUserProfileFragment(position);
            }else if(swapMaps){
                swapMaps = false;
                swapMapsFragment();
            }else{
                replaceUserFragment();
            }
        }
    }


    @Override
    public void onBackPressed(){
        if(swapMaps){
            swapMaps = false;
            mMapState = false;
            swapUserProfileFragment(position);
        }else{
            swapProfileEdit = false;
            swapProfile = false;
            finish();
            startActivity(getIntent());
        }
    }

    //fragment
    private void setupUserFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager.findFragmentByTag("user_frame") == null){
            userFragment = new UserFragment(users, this);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frame_layout_main, userFragment, "user_frame");
            fragmentTransaction.commit();
        }
    }

    private void replaceUserFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager.findFragmentByTag("user_frame") == null){
            userFragment = new UserFragment(users, this);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout_main, userFragment, "user_frame");
            fragmentTransaction.commit();
        }
    }

    public void swapUserProfileEditFragment(int position){
        this.position = position;
        swapProfileEdit = true;
        mEditState = true;
        invalidateOptionsMenu();
        textHeader.setText(R.string.header_edit_details);
        objectParser = new UserParser();
        List<UserAttribute> userAttributeList = objectParser.objectToList(users.get(position));
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager.findFragmentByTag("profile_edit_frame") == null){
            userProfileEditFragment = new UserProfileEditFragment(userAttributeList);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout_main, userProfileEditFragment, "profile_edit_frame");
            fragmentTransaction.commit();
        }
    }

    public void swapUserProfileFragment(int position){
        this.position = position;
        swapProfile = true;
        mProfileState = true;
        invalidateOptionsMenu();
        textHeader.setText(R.string.header_detils);
        List<UserAttribute> userAttributeList = objectParser.objectToList(users.get(position));
        UserProfileFragment userProfileFragment = new UserProfileFragment(userAttributeList);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager.findFragmentByTag("profile_frame") == null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout_main,userProfileFragment, "profile_frame");
            fragmentTransaction.commit();
        }
    }

    //maps
    public void swapMapsFragment(){
        mProfileState = false;
        swapProfile = false;
        mMapState = true;
        swapMaps = true;
        invalidateOptionsMenu();
        textHeader.setText("Geo");
        MapsFragment mapsFragment = new MapsFragment(users.get(position).getAddress().getGeo().getLat(), users.get(position).getAddress().getGeo().getLng(), users.get(position).getName());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_main, mapsFragment, "maps_frame");
        fragmentTransaction.commit();
    }


    public void handleSignOut(){
        Intent signOutIntent = new Intent(this, LoginActivity.class);
        startActivity(signOutIntent);
    }

    @Override
    public void callback() {
        String data = dataSource.getData();

        Gson gson = new Gson();

        users.addAll(Arrays.asList((gson.fromJson(data, User[].class))));

        users = objectParser.setUserProfileImages(users);

        setupUserFragment();

    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        signOutButton = menu.findItem(R.id.sign_out_button);
        updateButton = menu.findItem(R.id.update_button);
        geoButton = menu.findItem(R.id.geo_button);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        updateButton.setVisible(false);
        geoButton.setVisible(false);
        if(mEditState){
            signOutButton.setVisible(false);
            updateButton.setVisible(true);
        }else if(mProfileState){
            signOutButton.setVisible(false);
            geoButton.setVisible(true);
        }else if(mMapState){
            signOutButton.setVisible(false);
            geoButton.setVisible(false);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        switch (id){
            case(R.id.sign_out_button):
                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                handleSignOut();
                            }
                        });
                break;
            case (R.id.update_button):
                users.set(0, objectParser.updateUser(users.get(0), userProfileEditFragment.getUserProfileEditAdapter().getUserAttributeList()));
                mEditState = false;
                swapProfileEdit = false;
                Toast.makeText(this, "User updated successfully", Toast.LENGTH_LONG).show();
                finish();
                startActivity(getIntent());
                break;
            case(R.id.geo_button):
                swapMapsFragment();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //camera
    public void requestCameraPermissions(int position){
        this.position = position;
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
            FragmentManager fm = getSupportFragmentManager();
            CameraPermissionDialogFragment alertDialog  = CameraPermissionDialogFragment.newInstance();
            alertDialog.show(fm, "fragment_alert");
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CameraPermissionDialogFragment.getPermissionRequestCamera());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if(requestCode == CameraPermissionDialogFragment.getPermissionRequestCamera()){
            if(grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                userFragment.dispatchTakePictureIntent();
            }else{
                Toast.makeText(this, "Permission was denied", Toast.LENGTH_LONG).show();
            }
        }
    }

}
