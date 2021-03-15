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
        holder.getTextView().setText(user_array[position].getName());
    }

    @Override
    public int getItemCount() {
        return user_array.length;
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
            intent.putExtra("user_selected", user_array[getAdapterPosition()]);
            context.startActivity(intent);

        }
    }

    public UserAdapter(User[] dataSet, Context current){
        this.context = current;
        user_array = dataSet;
    }
}
