package com.kokarjabali;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {


    ApiHelper api_call = new ApiHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button Login = findViewById(R.id.button);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String[] data_login = new String[2];
                data_login[0] = "anggota";
                data_login[1] = "123123";

                api_call.LOGIN_POST(
                    LoginActivity.this,
                        " https://koperasijasaraharja.laradev.id/api/login",
                        data_login
                );
            }
        });


    }
}