package com.hcl.finalproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private static Context context;
    private static List<User> userList;

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.getTextView().setText(userList.get(position).getName());
    }

    @Override
    public int getItemCount() {

        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textView;
        private CardView cardView;

        public UserViewHolder(@NonNull View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.user_text);
            cardView = (CardView) view.findViewById(R.id.user_card);
            view.findViewById(R.id.user_card).setOnClickListener(this);
        }
        public TextView getTextView(){
            return textView;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ProfileActivity.class);
            intent.putExtra("user_selected", userList.get(getAdapterPosition()));
            context.startActivity(intent);

        }
    }

    public UserAdapter(List<User> dataSet, Context current){
        this.context = current;
        userList = dataSet;
    }
}
