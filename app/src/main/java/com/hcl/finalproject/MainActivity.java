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

    //maybe add shared pref in order to fix orientation change bug?

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

    private MenuItem updateButton;

    private boolean mState = false ;

    private static int position;

    private static boolean setup = false;

    private static UserFragment userFragment;

    private UserProfileEditFragment userProfileEditFragment;

    private int activeFragment;

    private static boolean swapProfileEdit;

    private static boolean swapProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("ONCREATE", "ONCREATE");
        super.onCreate(null);
        setContentView(R.layout.activity_main);
        textHeader = (TextView) findViewById(R.id.main_header_text);
    }

    @Override
    protected void onStart(){
        Log.i("ONSTART", "ONSTART");
        super.onStart();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        SharedPreferences sharedPreferences = getSharedPreferences("LOGGED IN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Intent intent = getIntent();

        //update shared pref start up logic so that orientation transition will not overlap
        //(sharedPreferences.getString("EDIT", "FALSE").compareTo("FALSE") == 0 && sharedPreferences.getString("EDIT_IMAGE", "FALSE").compareTo("FALSE") == 0) &&
        if(acct != null && setup == false){
            Log.i("INSIDE SETUP", "SETUP");
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
            //needs to be reworked with fragments
            /*
            if(sharedPreferences.getString("EDIT", "FALSE").compareTo("TRUE") == 0){
                User updatedUser = (User) intent.getSerializableExtra("user_updated");
                users.set(0, updatedUser);
                replaceUserFragment();
                //setupUserFragment();
                editor.putString("EDIT", "FALSE");
                Log.i("ELSE", "user updated");
                Toast.makeText(this, "User successfully updated", Toast.LENGTH_LONG).show();
            }else if(sharedPreferences.getString("EDIT_IMAGE", "FALSE").compareTo("TRUE") == 0){
                Log.i("ELSE", "user image");
                editor.putString("EDIT_IMAGE", "FALSE");
                Toast.makeText(this, "User successfully updated", Toast.LENGTH_LONG).show();
            }else{
                Log.i("ELSE", "vanilla");
                replaceUserFragment();
                //setupUserFragment();
            }
            editor.commit();*/

            /*
            if(sharedPreferences.getString("swapProfileEdit", "FALSE").compareTo("TRUE") == 0){
                Log.i("swapProfileEdit", "TRUE");
                swapUserProfileEditFragment(position);
            }else if(sharedPreferences.getString("swapProfile", "FALSE").compareTo("TRUE") == 0){
                Log.i("swapProfile", "TRUE");
                swapUserProfileFragment(position);
            }else{
                replaceUserFragment();
            }*/
            Log.i("swap_profile_edit", String.valueOf(swapProfileEdit));
            Log.i("swap_profile", String.valueOf(swapProfile));

            if(swapProfileEdit){
                swapProfileEdit = false;
                Log.i("SWAP_USER_EDIT", "TRUE");
                swapUserProfileEditFragment(position);
                //setupUserFragment();
            }else if(swapProfile){
                Log.i("SWAP_USER", "TRUE");
                swapProfile = false;
                swapUserProfileFragment(position);
                //setupUserFragment();
            }else{
                Log.i("Users after orientation change", String.valueOf(users));
                replaceUserFragment();
                //attachUserFragment();
            }
            //add activeFragment number status and refresh on the active Fragment as well as manipulate fragment backstack so we can access the actual -1 instead of
            //2x active fragment
            //error caches the starting orientation fragment...ends up in 2x as end result when returning to starting orientation state
            //i.e. vertical recycler view -> horizontal recycler view -> vertical recycler view 2x
            //is there any better way than manipulating the fragment backstack? or is that the only way?
            /*
            switch(activeFragment){

            }*/
        }
    }


    @Override
    public void onBackPressed(){
        swapProfileEdit = false;
        swapProfile = false;
        finish();
        startActivity(getIntent());
    }
    
    private void setupRecyclerView() {

        /*
        recyclerView = findViewById(R.id.recycler_view);

        userAdapter = new UserAdapter(users, this, this);

        recyclerView.setAdapter(userAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));*/

    }

    private void attachUserFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.attach(fragmentManager.findFragmentByTag("user_frame"));
        fragmentTransaction.commit();
    }


    private void setupUserFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager.findFragmentByTag("user_frame") == null){
            userFragment = new UserFragment(users, this, this);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frame_layout_main, userFragment, "user_frame");
            fragmentTransaction.commit();
        }
    }

    private void replaceUserFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Log.i("user_frame null", String.valueOf(fragmentManager.findFragmentByTag("user_frame")));
        if(fragmentManager.findFragmentByTag("user_frame") == null){
            Log.i("user_frame null", "FALSE");
            userFragment = new UserFragment(users, this, this);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout_main, userFragment, "user_frame");
            fragmentTransaction.commit();
        }
    }

    /*
    private void replaceUserProfileEditFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        userFragment = new UserFragment(users, this, this);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_main, userFragment, "user_frame");
        fragmentTransaction.commit();
    }*/


    public void swapUserProfileEditFragment(int position){
        /*
        SharedPreferences sharedPreferences = getSharedPreferences("LOGGED IN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("swapProfileEdit", "TRUE");
        editor.commit();*/
        this.position = position;
        swapProfileEdit = true;
        mState = true;
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
        /*
        SharedPreferences sharedPreferences = getSharedPreferences("LOGGED IN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("swapProfile", "TRUE");
        editor.commit();*/
        this.position = position;
        swapProfile = true;
        textHeader.setText(R.string.header_detils);
        List<UserAttribute> userAttributeList = objectParser.objectToList(users.get(position));
        UserProfileFragment userProfileFragment = new UserProfileFragment(userAttributeList);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Log.i("swapUserProfileFragment", "BEFORE IF");
        Log.i("CONTENT OF FINDFRAGMENT BY TAG", String.valueOf(fragmentManager.findFragmentByTag("profile_frame")));

        if(fragmentManager.findFragmentByTag("profile_frame") == null){
            /*
            for(int i = fragmentManager.getBackStackEntryCount() - 1; i > 0; i--){
                if(!fragmentManager.getBackStackEntryAt(i).getName().equalsIgnoreCase("profile_frame")){
                    fragmentManager.popBackStack();
                }else{
                    break;
                }
            }*/
            Log.i("swapUserProfileFragment", "INSIDE IF NULL");
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //fragmentTransaction.detach(fragmentManager.findFragmentByTag("user_frame"));
            fragmentTransaction.replace(R.id.frame_layout_main,userProfileFragment, "profile_frame");
            fragmentTransaction.commit();
        }
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

        users.get(0).setName("Nick");

        users = objectParser.setUserProfileImages(users);


        /*
        recyclerView = findViewById(R.id.recycler_view);

        userAdapter = new UserAdapter(users, this, this);

        recyclerView.setAdapter(userAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));*/

        setupUserFragment();

    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        signOutButton = menu.findItem(R.id.sign_out_button);
        updateButton = menu.findItem(R.id.update_button);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        updateButton.setVisible(false);
        if(mState == true){
            signOutButton.setVisible(false);
            updateButton.setVisible(true);
        }
        return true;
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
        }else if(id == R.id.update_button){
            /*
            SharedPreferences sharedPreferences = getSharedPreferences("LOGGED IN", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("EDIT", "TRUE");
            editor.commit();*/
            users.set(0, objectParser.updateUser(users.get(0), userProfileEditFragment.getUserProfileEditAdapter().getUserAttributeList()));
            //replaceUserProfileEditFragment();
            mState = false;
            swapProfileEdit = false;
            //invalidateOptionsMenu();
            Toast.makeText(this, "User updated successfully", Toast.LENGTH_LONG).show();
            finish();
            startActivity(getIntent());

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
