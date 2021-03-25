package com.hcl.finalproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.Transliterator;
import android.media.Image;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private static Context context;
    private static List<User> userList;
    private static MainActivity mainActivity;
    private static boolean cachedExists = false;
    private static LruCache<String, Bitmap> memoryCache;
    private static int position;
    private Bitmap imageBitmap;

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Log.i("User List SIZE", String.valueOf(userList.size()));
        Log.i("USER LIST CONTENT", String.valueOf(userList));
        Log.i("User Last", userList.get(userList.size() - 1).toString());

        if(userList.get(position).getImageCached() == true){
            holder.getUserImage().setImageBitmap(getBitmapFromMemCache("profile_image" + position));
        }else{
            try {
                Picasso.get().load(userList.get(position).getImageUri()).into(holder.getUserImage());
            }catch(Exception e){
                e.getStackTrace();
            }
        }
        holder.getUserNameText().setText(userList.get(position).getName());
        this.position = position;
    }

    @Override
    public int getItemCount() {

        return userList.size();
    }


    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView userImage;
        private TextView userNameText;
        private CardView userCard;

        public UserViewHolder(@NonNull View view) {
            super(view);
            userImage = (ImageView) view.findViewById(R.id.user_profile_img);
            userNameText = (TextView) view.findViewById(R.id.user_text);
            userCard = (CardView) view.findViewById(R.id.user_card);
            view.findViewById(R.id.user_card).setOnClickListener(this);
            view.findViewById(R.id.user_profile_img).setOnClickListener(this);
        }

        public ImageView getUserImage() {
            return userImage;
        }

        public TextView getUserNameText() {
            return userNameText;
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.user_card:
                    viewProfile();
                    break;
                case R.id.user_profile_img:
                    UserAdapter.this.position = getAdapterPosition();
                    Log.i("Click position", String.valueOf(UserAdapter.this.position));
                    mainActivity.requestCameraPermissions(getAdapterPosition());
            }
        }

        public void viewProfile(){
            Intent intent = new Intent(context, ProfileActivity.class);
            intent.putExtra("user_selected", userList.get(getAdapterPosition()));
            context.startActivity(intent);
        }


    }

    public UserAdapter(List<User> dataSet, Context current, MainActivity mainActivity){
        this.context = current;
        this.mainActivity = mainActivity;
        userList = dataSet;
        if(cachedExists == false){
            setupCache();
            cachedExists = true;
        }
    }

    private void setupCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        memoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap){
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public Bitmap getBitmapFromMemCache(String key){
        return memoryCache.get(key);
    }

    public void addBitMapToMemoryCache(String key, Bitmap bitmap){
        if(getBitmapFromMemCache(key) == null){
            memoryCache.put(key, bitmap);
        }
    }

    public void getCameraProfileImageInCache(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
        if(memoryCache.get("profile_image" + position) != null){
            memoryCache.remove("profile_image" + position);
        }
        userList.get(position).setImageCached(true);
        addBitMapToMemoryCache("profile_image" + position, imageBitmap);
        Log.i("POSITION INSIDE CACHE", String.valueOf(position));
        notifyDataSetChanged();
    }

}
