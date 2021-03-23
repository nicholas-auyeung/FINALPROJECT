package com.hcl.finalproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
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

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Picasso.get().load(userList.get(position).getImageUri()).into(holder.getUserImage());
        holder.getUserNameText().setText(userList.get(position).getName());
    }

    @Override
    public int getItemCount() {

        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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
                    mainActivity.requestCameraPermissions();
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
        userList = dataSet;
        this.mainActivity = mainActivity;
    }
}
