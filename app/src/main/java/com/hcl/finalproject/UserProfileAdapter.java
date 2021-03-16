package com.hcl.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class UserProfileAdapter extends RecyclerView.Adapter<UserProfileAdapter.UserProfileViewHolder> {

    private static Context context;

    private static String[] user_profile_array;

    @NonNull
    @Override
    public UserProfileAdapter.UserProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_profile_item, parent, false);
        return new UserProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserProfileAdapter.UserProfileViewHolder holder, int position) {
        holder.getTextView().setText(user_profile_array[position]);
    }

    @Override
    public int getItemCount() {
        return user_profile_array.length;
    }

    public static class UserProfileViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public UserProfileViewHolder(@NonNull View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.user_profile_text);
        }

        public TextView getTextView(){
            return textView;
        }
    }

    public UserProfileAdapter(String[] dataSet, Context current){
        this.context = current;
        user_profile_array = dataSet;
    }
}
