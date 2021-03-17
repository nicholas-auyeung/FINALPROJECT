package com.hcl.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class UserProfileAdapter extends RecyclerView.Adapter<UserProfileAdapter.UserProfileViewHolder> {

    private List<UserAttribute> userAttributeList;
    private Boolean edit;

    @NonNull
    @Override
    public UserProfileAdapter.UserProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_profile_item, parent, false);
        return new UserProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserProfileAdapter.UserProfileViewHolder holder, int position) {

        UserAttribute userAttribute = userAttributeList.get(position);
        String userAttributeName = userAttribute.getAttribute();
        List<String> userAttributeDetails = userAttribute.getAttributeDetails();

        holder.getUserProfileTextView().setText(userAttributeName);

        if(edit) {
            UserProfileEditDetailsAdapter userProfileEditDetailsAdapter = new UserProfileEditDetailsAdapter(userAttributeDetails);
            holder.userProfileRecyclerView.setAdapter(userProfileEditDetailsAdapter);
        }else{
            UserProfileDetailsAdapter userProfileDetailsAdapter = new UserProfileDetailsAdapter(userAttributeDetails);
            holder.userProfileRecyclerView.setAdapter(userProfileDetailsAdapter);
        }


    }

    @Override
    public int getItemCount() {
        return userAttributeList.size();
    }

    public static class UserProfileViewHolder extends RecyclerView.ViewHolder{

        TextView userProfileTextView;
        RecyclerView userProfileRecyclerView;

        public UserProfileViewHolder(@NonNull View view) {
            super(view);
            userProfileTextView = view.findViewById(R.id.userProfileHeader);
            userProfileRecyclerView = view.findViewById(R.id.userProfileRecyclerView);
        }

        public TextView getUserProfileTextView(){
            return userProfileTextView;
        }
    }

    public UserProfileAdapter(List<UserAttribute> userAttributeList, Boolean edit){
        this.edit = edit;
        this.userAttributeList = userAttributeList;
    }
}
