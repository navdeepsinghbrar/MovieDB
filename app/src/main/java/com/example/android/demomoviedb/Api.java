package com.example.android.demomoviedb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.android.demomoviedb.databinding.ActivityApiBinding;
import com.google.firebase.auth.FirebaseAuth;

public class Api extends AppCompatActivity {

    public static String API_KEY;
    ActivityApiBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_api);

        mBinding.btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API_KEY = mBinding.etApiKey.getText().toString();

                startActivity(new Intent(Api.this, MainActivity.class));
            }
        });

        mBinding.btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Api.this, Authentication.class));
            }
        });

    }
}
