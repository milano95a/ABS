package com.cw.mad.a00003741.newsapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentSignUp extends Fragment{

    Button btnSignUp;
    TextInputEditText inputUsername;
    TextInputEditText inputPass;
    TextInputEditText inputRePass;

    Db db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up,container,false);

        db = new Db(getActivity());

        inputUsername = (TextInputEditText)rootView.findViewById(R.id.sign_up_username);
        inputPass = (TextInputEditText)rootView.findViewById(R.id.sign_up_pass);
        inputRePass = (TextInputEditText)rootView.findViewById(R.id.sign_up_re_pass);
        btnSignUp = (Button)rootView.findViewById(R.id.btn_sign_up);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(),username.getText().toString() + pass.getText().toString() + re_pass.getText().toString(),Toast.LENGTH_SHORT).show();

                String username = inputUsername.getText().toString();
                String password = inputPass.getText().toString();
                String re_password = inputRePass.getText().toString();


                Cursor cursor = db.read();
                ArrayList<String> list = new ArrayList<String>();

                while (cursor.moveToNext()){
                    list.add(cursor.getString(1));
                }

                if(username.trim().equals("")) {
                    Toast.makeText(getActivity(), "Please enter your username", Toast.LENGTH_SHORT).show();
                    return;
                }else if(password.trim().equals("") || re_password.trim().equals("")){
                    Toast.makeText(getActivity(), "Plaese enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.equals(re_password)){
                    for(int i = 0, k = list.size(); i < k; i++){
                        if(list.get(i).equals(username)){
                            Log.v("------" + list.get(i), username);
                            Toast.makeText(getActivity(),"Username already exists",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    signedUp(username,password);
                }else{
                    Toast.makeText(getActivity(),"Passwords do not match",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        return rootView;
    }

    public void signedUp(String username, String password){
        Toast.makeText(getActivity(),"You have signed up", Toast.LENGTH_SHORT).show();
        db.insert(username,password);

        inputUsername.setText("");
        inputPass.setText("");
        inputRePass.setText("");
    }
}
