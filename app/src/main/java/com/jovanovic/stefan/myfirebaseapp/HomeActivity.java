package com.jovanovic.stefan.myfirebaseapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    Button update_email_btn, update_password, logout_btn;
    TextView email_address_txt, id_txt, verified_account_txt;

    String myEmail, myID;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        update_email_btn = findViewById(R.id.update_email_btn);
        update_password = findViewById(R.id.update_password_btn);
        logout_btn = findViewById(R.id.logout_btn);
        email_address_txt = findViewById(R.id.email_address_txt);
        id_txt = findViewById(R.id.id_txt);
        verified_account_txt = findViewById(R.id.verified_account_txt);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if(mUser != null){
            myEmail = mUser.getEmail();
            myID = mUser.getUid();
            //Set Email and ID
            email_address_txt.setText(myEmail);
            id_txt.setText(myID);
            //Check if account is verified
            checkAccountVerification();
        }

        update_email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, UpdateEmailActivity.class);
                startActivity(intent);
            }
        });
        update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, UpdatePasswordActivity.class);
                startActivity(intent);
            }
        });
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                finish();
                Toast.makeText(HomeActivity.this, "Please log in to continue.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if(mUser != null){
            myEmail = mUser.getEmail();
            myID = mUser.getUid();
            //Set Email and ID
            email_address_txt.setText(myEmail);
            id_txt.setText(myID);
            //Check if account is verified
            checkAccountVerification();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Log out?");
        builder.setMessage("Are you sure you want to log out?");
        final AlertDialog dialog = builder.create();
        dialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mAuth.signOut();
                finish();
                Toast.makeText(HomeActivity.this, "Please log in to continue.", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void checkAccountVerification(){
        boolean verification = mUser.isEmailVerified();
        if(verification){
            verified_account_txt.setText("Account Verified");
            verified_account_txt.setTextColor(getResources().getColor(R.color.green));
        }
    }
}
