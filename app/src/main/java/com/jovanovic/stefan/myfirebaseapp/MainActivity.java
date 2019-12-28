package com.jovanovic.stefan.myfirebaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText sign_in_email, sign_in_password;
    TextView create_account_txt;
    Button sign_in;

    String email, password;

    ValidateInput validateInput;

    LoadingAnimation loadingAnimation;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sign_in_email = findViewById(R.id.sign_in_email);
        sign_in_password = findViewById(R.id.sign_in_password);
        create_account_txt = findViewById(R.id.create_account_txt);
        sign_in = findViewById(R.id.sign_in_btn);

        validateInput = new ValidateInput(
                MainActivity.this,
                sign_in_email,
                sign_in_password);

        // Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Loading Animation
        loadingAnimation = new LoadingAnimation(MainActivity.this);

        create_account_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInAccount();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser != null){
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Please log in to continue.", Toast.LENGTH_SHORT).show();
        }
    }

    public void signInAccount(){

        loadingAnimation.LoadingAnimationDialog();

        boolean emailVerified = validateInput.validateEmail();
        boolean passwordVerified = validateInput.validatePassword();

        if(emailVerified && passwordVerified){

            email = sign_in_email.getText().toString().trim();
            password = sign_in_password.getText().toString().trim();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(intent);
                                loadingAnimation.dismissLoadingAnimation();

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(MainActivity.this, "Fatal Error.", Toast.LENGTH_SHORT).show();
                                loadingAnimation.dismissLoadingAnimation();
                            }
                        }
                    });
        }else{
            loadingAnimation.dismissLoadingAnimation();
        }
    }

}
