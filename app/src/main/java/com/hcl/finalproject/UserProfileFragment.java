package com.hcl.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class UserProfileFragment extends Fragment {

    RecyclerView attributeRecyclerView;
    private List<UserAttribute> userAttributeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        attributeRecyclerView = view.findViewById(R.id.recycler_view_profile);
        UserProfileAdapter userProfileAdapter = new UserProfileAdapter(userAttributeList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        attributeRecyclerView.setLayoutManager(linearLayoutManager);
        attributeRecyclerView.setAdapter(userProfileAdapter);
        attributeRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    public UserProfileFragment(List<UserAttribute> userAttributeList) {
        this.userAttributeList = userAttributeList;
    }
}