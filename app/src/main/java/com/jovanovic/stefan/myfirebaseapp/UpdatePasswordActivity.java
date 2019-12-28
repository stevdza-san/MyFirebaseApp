package com.jovanovic.stefan.myfirebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePasswordActivity extends AppCompatActivity {

    ImageView back_arrow;
    EditText new_password, repeat_password2;
    Button update_password;

    String myNewPassword, myRepeatPassword;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        back_arrow = findViewById(R.id.back_arrow3);
        new_password = findViewById(R.id.new_password);
        repeat_password2 = findViewById(R.id.repeat_password2);
        update_password = findViewById(R.id.update_password);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myNewPassword = new_password.getText().toString().trim();
                myRepeatPassword = repeat_password2.getText().toString().trim();

                if(myRepeatPassword.equals(myNewPassword)){
                    mUser.updatePassword(myNewPassword);
                    Toast.makeText(UpdatePasswordActivity.this, "Password Updated Successfully",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(UpdatePasswordActivity.this, "Passwords Don't match.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
