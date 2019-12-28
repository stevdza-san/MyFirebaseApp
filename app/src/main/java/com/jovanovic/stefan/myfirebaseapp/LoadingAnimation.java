package com.jovanovic.stefan.myfirebaseapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

class LoadingAnimation {

    private Activity activity;
    private AlertDialog dialog;

    LoadingAnimation(Activity myActivity){
        activity = myActivity;
    }

    void LoadingAnimationDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    void dismissLoadingAnimation(){
        dialog.dismiss();
    }
}
