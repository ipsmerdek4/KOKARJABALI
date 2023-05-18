package com.kokarjabali.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.kokarjabali.R;

public class InfoPinjamanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pinjaman);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        Bundle DataX = getIntent().getExtras();
        String id_user = DataX.getString("id_user");

        Toast.makeText(this, id_user, Toast.LENGTH_SHORT).show();

    }
}