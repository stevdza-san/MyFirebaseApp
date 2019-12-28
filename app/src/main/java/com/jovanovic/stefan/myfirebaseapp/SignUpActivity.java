package com.jovanovic.stefan.myfirebaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    EditText sign_up_email, sign_up_password, repeat_password;
    ImageView back_arrow;
    Button sign_up_btn;

    String email, password;

    ValidateInput validateInput;

    LoadingAnimation loadingAnimation;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sign_up_email = findViewById(R.id.sign_up_email);
        sign_up_password = findViewById(R.id.sign_up_password);
        repeat_password = findViewById(R.id.repeat_password);
        back_arrow = findViewById(R.id.back_arrow);
        sign_up_btn = findViewById(R.id.sign_up_btn);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Loading Animation
        loadingAnimation = new LoadingAnimation(SignUpActivity.this);

        validateInput = new ValidateInput(
                SignUpActivity.this,
                sign_up_email,
                sign_up_password,
                repeat_password);

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpNewAccount();
            }
        });
    }

    public void signUpNewAccount(){

        loadingAnimation.LoadingAnimationDialog();

        boolean emailVerified = validateInput.validateEmail();
        boolean passwordVerified = validateInput.validatePassword();
        boolean repeatPasswordVerified = validateInput.repeatPasswordValidation();

        if(emailVerified && passwordVerified && repeatPasswordVerified){

            email = sign_up_email.getText().toString().trim();
            password = sign_up_password.getText().toString().trim();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                                startActivity(intent);
                                loadingAnimation.dismissLoadingAnimation();
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignUpActivity.this, "Fatal Error", Toast.LENGTH_SHORT).show();
                                loadingAnimation.dismissLoadingAnimation();
                            }
                        }
                    });
        }else{
            loadingAnimation.dismissLoadingAnimation();
        }
    }

}
