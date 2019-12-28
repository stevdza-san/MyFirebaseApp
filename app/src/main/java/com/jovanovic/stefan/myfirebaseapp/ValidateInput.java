package com.jovanovic.stefan.myfirebaseapp;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

class ValidateInput {

    private Context context;
    private EditText email, password, repeatPassword;

    private String emailInput, passwordInput, repeatPasswordInput;

    ValidateInput(Context myContext, EditText myEmail){
        context = myContext;
        email = myEmail;
    }

    ValidateInput(Context myContext, EditText myEmail, EditText myPassword){
        context = myContext;
        email = myEmail;
        password = myPassword;
    }

    ValidateInput(Context myContext, EditText myEmail, EditText myPassword, EditText myRepeatPassword){
        context = myContext;
        email = myEmail;
        password = myPassword;
        repeatPassword = myRepeatPassword;
    }

    boolean validateEmail(){
        emailInput = email.getText().toString().trim();

        if(emailInput.isEmpty()){
            Toast.makeText(context, "Please enter your Email Address.", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            Toast.makeText(context, "Invalid Email Address.", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    boolean validatePassword(){
        passwordInput = password.getText().toString().trim();

        if(passwordInput.isEmpty()){
            Toast.makeText(context, "Please enter your Password.", Toast.LENGTH_SHORT).show();
            return false;
        }else if(passwordInput.length() < 8){
            Toast.makeText(context, "Password too Short.", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    boolean repeatPasswordValidation(){
        repeatPasswordInput = repeatPassword.getText().toString().trim();

        if(repeatPasswordInput.isEmpty()){
            Toast.makeText(context, "Fill out all fields.", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!repeatPasswordInput.equals(passwordInput)){
            Toast.makeText(context, "Passwords Don't match.", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

}
