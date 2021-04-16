package com.hcl.finalproject;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LocationPermissionDialogFragment extends DialogFragment {

    private static final int PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 2;

    @Override
    public Dialog onCreateDialog(Bundle savedInstancedState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.request_location_permission)
                .setPositiveButton(R.string.permission_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_ACCESS_FINE_LOCATION);
                    }
                })
                .setNegativeButton(R.string.permission_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                        dismiss();
                    }
                });

        return builder.create();
    }

    public LocationPermissionDialogFragment() {
    }

    public static LocationPermissionDialogFragment newInstance(){
        LocationPermissionDialogFragment frag = new LocationPermissionDialogFragment();
        return frag;
    }

    public static int getPermissionRequestAccessFineLocation() {
        return PERMISSION_REQUEST_ACCESS_FINE_LOCATION;
    }
}