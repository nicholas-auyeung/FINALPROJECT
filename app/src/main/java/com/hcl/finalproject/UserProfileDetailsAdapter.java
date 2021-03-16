package com.hcl.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserProfileDetailsAdapter extends RecyclerView.Adapter<UserProfileDetailsAdapter.UserProfileDetailsViewHolder> {

    List<String> userProfileDetails;

    @NonNull
    @Override
    public UserProfileDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_profile_detail_item, parent, false);
        return new UserProfileDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserProfileDetailsViewHolder holder, int position) {
        holder.getUserProfileText().setText(userProfileDetails.get(position));
    }

    @Override
    public int getItemCount() {
        return userProfileDetails.size();
    }


    public class UserProfileDetailsViewHolder extends RecyclerView.ViewHolder {

        TextView userProfileText;

        public UserProfileDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            userProfileText = itemView.findViewById(R.id.userProfileText);
        }

        public TextView getUserProfileText() {
            return userProfileText;
        }
    }

    public UserProfileDetailsAdapter(List<String> userProfileDetails){
        this.userProfileDetails = userProfileDetails;
    }
}
