package com.hcl.finalproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class UserProfileAdapter extends RecyclerView.Adapter<UserProfileAdapter.UserProfileViewHolder> {

    private static List<UserAttribute> userAttributeList;


    @NonNull
    @Override
    public UserProfileAdapter.UserProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_profile_item, parent, false);
        return new UserProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserProfileAdapter.UserProfileViewHolder holder, int position) {
        holder.getAttribute().setText(userAttributeList.get(position).getAttribute());
        if(userAttributeList.get(position).getAttributeDetails().compareTo("FALSE") == 0){
            holder.getItem().setVisibility(View.GONE);
        }else{
            holder.getItem().setText(userAttributeList.get(position).getAttributeDetails());
        }
    }

    @Override
    public int getItemCount() {
        return userAttributeList.size();
    }

    public class UserProfileViewHolder extends RecyclerView.ViewHolder{

        private TextView attribute;
        private TextView item;

        public UserProfileViewHolder(@NonNull View view) {
            super(view);
            attribute = (TextView) view.findViewById(R.id.user_profile_attribute);
            item = (TextView) view.findViewById(R.id.user_profile_item);
        }

        public TextView getAttribute() {
            return attribute;
        }

        public TextView getItem() {
            return item;
        }
    }

    public UserProfileAdapter(List<UserAttribute> userAttributeList) {
        this.userAttributeList = userAttributeList;
    }
}
