package com.example.android.demomoviedb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.android.demomoviedb.databinding.ActivityAuthenticationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Authentication extends AppCompatActivity {

    ActivityAuthenticationBinding mBinding;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        FirebaseApp.initializeApp(getApplicationContext());
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_authentication);
        mAuth = FirebaseAuth.getInstance();

        mBinding.btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });

        mBinding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignUp();
            }
        });

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){

                    startActivity(new Intent(Authentication.this, MainActivity.class));

                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthStateListener);

    }

    private void startSignIn(){
        String email = mBinding.etEmailLogin.getText().toString();
        String password = mBinding.etPasswordLogin.getText().toString();

        if(TextUtils.isEmpty(email)) {
            Toast.makeText(Authentication.this, "Email is missing", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(Authentication.this, "Password is missing", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {

                        Toast.makeText(Authentication.this, "Authentication failed", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }

    }

    private void startSignUp(){
        String email = mBinding.etEmailSignup.getText().toString();
        String password = mBinding.etPasswordSignup.getText().toString();
        String confirmPassword = mBinding.etConfirmPassword.getText().toString();

        if(TextUtils.isEmpty(email)) {
            Toast.makeText(Authentication.this, "Email is missing", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(Authentication.this, "Password is missing", Toast.LENGTH_SHORT).show();
        }
        else if(!password.equals(confirmPassword)){
            Toast.makeText(Authentication.this, "Confirm password again", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {

                        Toast.makeText(Authentication.this, "Authentication failed", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(Authentication.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

}
