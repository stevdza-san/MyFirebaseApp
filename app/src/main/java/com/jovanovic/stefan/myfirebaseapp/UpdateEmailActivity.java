package com.jovanovic.stefan.myfirebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdateEmailActivity extends AppCompatActivity {

    ImageView back_arrow;
    EditText current_email, new_email;
    Button update_email;
    TextView send_verification_email_txt;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    ValidateInput validateInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);

        back_arrow = findViewById(R.id.back_arrow2);
        current_email = findViewById(R.id.current_email);
        new_email = findViewById(R.id.new_email);
        update_email = findViewById(R.id.update_email);
        send_verification_email_txt = findViewById(R.id.send_verification_email_txt);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        validateInput = new ValidateInput(UpdateEmailActivity.this, new_email);

        // Set Current Email Address
        setCurrentEmail();

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        update_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean emailVerified = validateInput.validateEmail();

                if(emailVerified && mUser != null){
                    String myNewEmail = new_email.getText().toString().trim();
                    mUser.updateEmail(myNewEmail);
                    Toast.makeText(UpdateEmailActivity.this, "Email Address Updated Successfully",
                            Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    //Wait 2 Second to read new Email from Firebase and then update EditText
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setCurrentEmail();
                        }
                    }, 3000);
                }else{
                    Toast.makeText(UpdateEmailActivity.this, "Invalid Email Address.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        send_verification_email_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUser.isEmailVerified()) {
                    Toast.makeText(UpdateEmailActivity.this, "Email Already Verified",
                            Toast.LENGTH_SHORT).show();
                }else{
                    mUser.sendEmailVerification();
                    Toast.makeText(UpdateEmailActivity.this, "Verification Email Sent!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setCurrentEmail(){
        if(mUser != null){
            current_email.setEnabled(true);
            current_email.setText(mUser.getEmail());
            current_email.setEnabled(false);
        }else{
            Toast.makeText(this, "Please log in to continue.", Toast.LENGTH_SHORT).show();
        }
    }
}
