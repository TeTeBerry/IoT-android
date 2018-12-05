package com.example.application.water;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import iot.smart.water.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void choosen_admin(View view){
        startActivity(new Intent(this, admin_login.class));
    }

    public void choosen_user(View view){
        startActivity(new Intent(this, user_login.class));
    }

    public void choosen_admin_signup (View view){ startActivity(new Intent(this,admin_signup.class));}
}
