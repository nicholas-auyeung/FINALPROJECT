package com.hcl.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserProfileEditDetailsAdapter extends RecyclerView.Adapter<UserProfileEditDetailsAdapter.UserProfileEditDetailsViewHolder> {

    List<String> userProfileEditDetails;

    @NonNull
    @Override
    public UserProfileEditDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_profile_edit_detail_item, parent, false);
        return new UserProfileEditDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserProfileEditDetailsViewHolder holder, int position) {
        holder.getUserProfileEdit().setText(userProfileEditDetails.get(position));
    }

    @Override
    public int getItemCount() {
        return userProfileEditDetails.size();
    }

    public class UserProfileEditDetailsViewHolder extends RecyclerView.ViewHolder{

        EditText userProfileEdit;

        public UserProfileEditDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            userProfileEdit = itemView.findViewById(R.id.userProfileEdit);
        }

        public EditText getUserProfileEdit() {
            return userProfileEdit;
        }
    }

    public UserProfileEditDetailsAdapter(List<String> userProfileEditDetails){
        this.userProfileEditDetails = userProfileEditDetails;
    }
}
