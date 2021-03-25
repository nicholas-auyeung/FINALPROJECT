package com.hcl.finalproject;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

public class CameraPermissionDialogFragment extends DialogFragment {

    private static final int PERMISSION_REQUEST_CAMERA = 1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstancedState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.request_camera_permission)
                .setPositiveButton(R.string.permission_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
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

    public CameraPermissionDialogFragment() {
    }

    public static CameraPermissionDialogFragment newInstance(){
        CameraPermissionDialogFragment frag = new CameraPermissionDialogFragment();
        return frag;
    }

    public static int getPermissionRequestCamera() {
        return PERMISSION_REQUEST_CAMERA;
    }
}
