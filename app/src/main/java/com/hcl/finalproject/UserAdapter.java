package com.hcl.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private static Context context;
    private static User[] user_array;

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.getTextView().setText(user_array[position].getUsername());
    }

    @Override
    public int getItemCount() {
        return user_array.length;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private CardView cardView;

        public UserViewHolder(@NonNull View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.user_text);
            cardView = (CardView) view.findViewById(R.id.user_card);
        }
        public TextView getTextView(){
            return textView;
        }
    }

    public UserAdapter(User[] dataSet, Context current){
        this.context = current;
        user_array = dataSet;
    }
}
