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

public class UserProfileEditFragment extends Fragment {

    RecyclerView editAttributeRecyclerView;
    private List<UserAttribute> userAttributeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile_edit, container, false);
        editAttributeRecyclerView = view.findViewById(R.id.recycler_view_profile_edit);
        UserProfileEditAdapter userProfileEditAdapter = new UserProfileEditAdapter(userAttributeList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        editAttributeRecyclerView.setLayoutManager(linearLayoutManager);
        editAttributeRecyclerView.setAdapter(userProfileEditAdapter);
        editAttributeRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    public UserProfileEditFragment(List<UserAttribute> userAttributeList) {
        this.userAttributeList = userAttributeList;
    }
}