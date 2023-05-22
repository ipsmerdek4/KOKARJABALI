package com.kokarjabali.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kokarjabali.R;
import com.kokarjabali.api.ApiHelper;
import com.kokarjabali.api.Base_url;

public class InfoPinjamanActivity extends AppCompatActivity {



    ApiHelper api_call = new ApiHelper();
    Base_url base_url = new Base_url();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pinjaman);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        Bundle DataX = getIntent().getExtras();
        String id_user = DataX.getString("id_user");

        api_call.PINJAMAN_GET(
                InfoPinjamanActivity.this,
                base_url.uri() + "api/pinjaman?user_id=" + id_user,
                new ApiHelper.VolleyCallback() {
                    @Override
                    public void onSuccess(String[] result) {

                                TextView nama = findViewById(R.id.textView11);
                                nama.setText(DataX.getString("nama"));

                                TextView pokok = findViewById(R.id.textView13);
                                pokok.setText(result[1]);

                                TextView jangkawaktu = findViewById(R.id.textView14);
                                jangkawaktu.setText(result[2]);

                                TextView bunga = findViewById(R.id.textView15);
                                bunga.setText(result[3]);

                                TextView jmlpinjaman = findViewById(R.id.textView16);
                                jmlpinjaman.setText(result[4]);

                                TextView cicilanbulan = findViewById(R.id.textView17);
                                cicilanbulan.setText(result[5]);

                                TextView tglpinjam = findViewById(R.id.textView18);
                                tglpinjam.setText(result[6]);

                                TextView jatuhtempo = findViewById(R.id.textView19);
                                jatuhtempo.setText(result[7]);

                                TextView cicilanke = findViewById(R.id.textView20);
                                cicilanke.setText(result[8]);

                                TextView sisapinjam = findViewById(R.id.textView21);
                                sisapinjam.setText(result[9]);

                    }

                    @Override
                    public void onError(String result) {
                        Toast.makeText(InfoPinjamanActivity.this, "Error " + result , Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(InfoPinjamanActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });


//                Toast.makeText(this, id_user, Toast.LENGTH_SHORT).show();


        Button back = findViewById(R.id.button6);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoPinjamanActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });




    }
}