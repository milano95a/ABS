package com.cw.mad.a00003741.newsapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentSignIn extends Fragment {

    TextInputEditText inputUsername;
    TextInputEditText inputPassword;
    Button signin;

    Db db;
    int id;
    String username;
    String password;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_in,container,false);

        inputUsername= (TextInputEditText)rootView.findViewById(R.id.sign_in_username);
        inputPassword = (TextInputEditText)rootView.findViewById(R.id.sign_in_pass);
        signin = (Button)rootView.findViewById(R.id.btn_sign_in);

        db = new Db(getActivity());


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> userNames = new ArrayList<String>();
                ArrayList<String> passwords = new ArrayList<String>();
                ArrayList<Integer> ids = new ArrayList<Integer>();

                username = inputUsername.getText().toString();
                password = inputPassword.getText().toString();

                Cursor cursor = db.read();

                while (cursor.moveToNext()){
                    ids.add(cursor.getInt(0));
                    userNames.add(cursor.getString(1));
                    passwords.add(cursor.getString(2));
                }

                for(int i = 0, k = userNames.size(); i < k; i++){
                    if(userNames.get(i).equals(username)){
                        if(passwords.get(i).equals(password)){
                            logIn(ids.get(i),username,password);
                            return;
                        }
                    }
                }

                Toast.makeText(getActivity(),"User not found",Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

    public void logIn(int id, String username, String password){
        Data.userId = id;
        Data.username = username;
        Data.password = password;

        ActivityMain activityMain = new ActivityMain();
        Intent i = new Intent(getActivity(),ActivityMain.class);
        getActivity().finish();
        startActivity(i);
    }
}
