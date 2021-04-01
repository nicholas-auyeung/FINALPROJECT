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

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private GoogleSignInClient mGoogleSignInClient;

    private Datasource dataSource;

    private ObjectParser objectParser = new UserParser();;

    private User signInUser;

    private static List<User> users = new ArrayList<>();

    private UserAdapter userAdapter;

    private RecyclerView recyclerView;

    private TextView textHeader;

    private MenuItem signOutButton;

    private int position;

    private static boolean setup = false;

    private UserFragment userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        SharedPreferences sharedPreferences = getSharedPreferences("LOGGED IN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Intent intent = getIntent();

        //(sharedPreferences.getString("EDIT", "FALSE").compareTo("FALSE") == 0 && sharedPreferences.getString("EDIT_IMAGE", "FALSE").compareTo("FALSE") == 0) &&
        if(acct != null && setup == false){
            try {
                dataSource = new Datasource(this, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            editor.putString("loggedInUser", acct.getDisplayName());
            editor.commit();
            signInUser = new User(acct.getId(), acct.getDisplayName(), acct.getEmail());
            users.add(signInUser);
            setup = true;
        }else{
            if(sharedPreferences.getString("EDIT", "FALSE").compareTo("TRUE") == 0){
                User updatedUser = (User) intent.getSerializableExtra("user_updated");
                users.set(0, updatedUser);
                setupUserFragment();
                editor.putString("EDIT", "FALSE");
                Toast.makeText(this, "User successfully updated", Toast.LENGTH_LONG).show();
            }else if(sharedPreferences.getString("EDIT_IMAGE", "FALSE").compareTo("TRUE") == 0){
                editor.putString("EDIT_IMAGE", "FALSE");
                Toast.makeText(this, "User successfully updated", Toast.LENGTH_LONG).show();
            }else{
                setupUserFragment();
            }
            editor.commit();
        }
    }
    
    private void setupRecyclerView() {

        /*
        recyclerView = findViewById(R.id.recycler_view);

        userAdapter = new UserAdapter(users, this, this);

        recyclerView.setAdapter(userAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));*/

    }

    private void setupUserFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();
        userFragment = new UserFragment(users, this, this);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout_main, userFragment, "user_transaction");
        fragmentTransaction.commit();

    }

    public void swapUserProfileEditFragment(int position){
        textHeader.setText(R.string.header_edit_details);
        List<UserAttribute> userAttributeList = objectParser.objectToList(users.get(position));
        UserProfileEditFragment userProfileEditFragment = new UserProfileEditFragment(userAttributeList);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_main, userProfileEditFragment);
        fragmentTransaction.commit();
    }

    public void swapUserProfileFragment(int position){
        textHeader.setText(R.string.header_detils);
        List<UserAttribute> userAttributeList = objectParser.objectToList(users.get(position));
        UserProfileFragment userProfileFragment = new UserProfileFragment(userAttributeList);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_main,userProfileFragment);
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

        /*
        recyclerView = findViewById(R.id.recycler_view);

        userAdapter = new UserAdapter(users, this, this);

        recyclerView.setAdapter(userAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));*/

        setupUserFragment();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        signOutButton = menu.findItem(R.id.sign_out_button);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if(id == R.id.sign_out_button){
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            handleSignOut();
                        }
                    });
        }

        return super.onOptionsItemSelected(item);
    }

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
                SharedPreferences sharedPreferences = getSharedPreferences("LOGGED IN", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("EDIT_IMAGE", "TRUE");
                editor.commit();
                userFragment.dispatchTakePictureIntent();
            }else{
                Toast.makeText(this, "Permission was denied", Toast.LENGTH_LONG).show();
            }
        }
    }

}
