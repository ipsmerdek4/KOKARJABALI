package com.kokarjabali.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kokarjabali.api.ApiHelper;
import com.kokarjabali.api.Base_url;
import com.kokarjabali.R;

public class LoginActivity extends AppCompatActivity {


    ApiHelper api_call = new ApiHelper();
    Base_url base_url = new Base_url();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button Login = findViewById(R.id.button);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText username = findViewById(R.id.txt_username);
                EditText passoword = findViewById(R.id.txt_pass);


                String[] data_login = new String[2];
                data_login[0] = username.getText().toString();
                data_login[1] = passoword.getText().toString();

                api_call.LOGIN_POST(
                        LoginActivity.this,
                        base_url.uri() + "api/login",
                        data_login,
                        new ApiHelper.VolleyCallback() {
                            @Override
                            public void onSuccess(String[] result) {
                                Toast.makeText(LoginActivity.this, result[0], Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(String result) {
                                Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });


    }
}