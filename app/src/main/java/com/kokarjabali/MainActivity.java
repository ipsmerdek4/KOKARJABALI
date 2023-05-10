package com.kokarjabali;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    ApiHelper api_call = new ApiHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] data_login = new String[2];
        data_login[0] = "anggota";
        data_login[1] = "123123";

        api_call.LOGIN_POST(
                MainActivity.this,
                "https://koperasijasaraharja.laradev.id/api/login",
                data_login
        );


    }
}