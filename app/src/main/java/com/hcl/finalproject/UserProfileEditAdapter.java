package com.hcl.finalproject;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserProfileEditAdapter extends RecyclerView.Adapter<UserProfileEditAdapter.UserProfileEditViewHolder> {

    private List<UserAttribute> userAttributeList;

    @NonNull
    @Override
    public UserProfileEditAdapter.UserProfileEditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_profile_edit_item, parent, false);
        return new UserProfileEditViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserProfileEditAdapter.UserProfileEditViewHolder holder, int position) {
        holder.getAttribute().setText(userAttributeList.get(position).getAttribute());
        if(userAttributeList.get(position).getAttributeDetails().compareTo("FALSE") == 0){
            holder.getEditItem().setVisibility(View.GONE);
        }else{
            holder.editItem.setTag(position);
            holder.getEditItem().setText(userAttributeList.get(position).getAttributeDetails());
        }

    }

    @Override
    public int getItemCount() {
        return userAttributeList.size();
    }

    public class UserProfileEditViewHolder extends RecyclerView.ViewHolder{

        private TextView attribute;
        private EditText editItem;

        public UserProfileEditViewHolder(@NonNull View view) {
            super(view);
            attribute = (TextView) view.findViewById(R.id.user_edit_profile_attribute);
            editItem = (EditText) view.findViewById(R.id.user_edit_profile_item);
            editItem.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(editItem.getTag() != null){
                        userAttributeList.get((int) editItem.getTag()).setAttributeDetails(s.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        public TextView getAttribute() {
            return attribute;
        }

        public EditText getEditItem() {
            return editItem;
        }
    }

    public UserProfileEditAdapter(List<UserAttribute> userAttributeList) {
        this.userAttributeList = userAttributeList;
    }

    public List<UserAttribute> getUserAttributeList() {
        return userAttributeList;
    }
}