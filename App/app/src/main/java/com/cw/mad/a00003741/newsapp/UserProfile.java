package com.cw.mad.a00003741.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.cw.mad.a00003741.newsapp.R.id.pass;

public class UserProfile extends AppCompatActivity {

    TextView txtUsername;
    TextView txtPass;
    TextView txtRepass;
    Button save;
    Db db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Profile");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        db = new Db(this);

        txtUsername = (TextView)findViewById(R.id.username);
        txtPass = (TextView)findViewById(pass);
        txtRepass = (TextView)findViewById(R.id.re_pass);
        save = (Button)findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUsername.getText().toString();
                String password = txtPass.getText().toString();
                String re_password = txtRepass.getText().toString();

                if(username.trim().equals("")) {
                    Toast.makeText(UserProfile.this, "Please enter your username", Toast.LENGTH_SHORT).show();
                    return;

                }else if(password.trim().equals("") || re_password.trim().equals("")){
                    Toast.makeText(UserProfile.this, "Plaese enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!password.equals(re_password)){
                    Toast.makeText(UserProfile.this,"Passwords do not match",Toast.LENGTH_SHORT).show();
                    return;
                } else{
                    db.update(username,password,Data.userId);
                    Toast.makeText(UserProfile.this,"Account updated",Toast.LENGTH_SHORT).show();
                    Data.username = username;
                    Data.password = password;
                    return;
                }
            }
        });

        txtUsername.setText(Data.username);
        txtPass.setText(Data.password);
        txtRepass.setText(Data.password);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
