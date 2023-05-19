package com.kokarjabali.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.kokarjabali.R;
import com.kokarjabali.api.ApiHelper;
import com.kokarjabali.api.Base_url;

public class PengajuanPinjamanActivity extends AppCompatActivity {


    ApiHelper api_call = new ApiHelper();
    Base_url base_url = new Base_url();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengajuan_pinjaman);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Bundle DataX = getIntent().getExtras();
        String id_user = DataX.getString("id_user");

        TextView NAma = findViewById(R.id.textView11);
        NAma.setText(DataX.getString("nama"));


        TextInputEditText jml_pengjuan_pinjaman = findViewById(R.id.textInputEditText2);
        jml_pengjuan_pinjaman.setInputType(InputType.TYPE_CLASS_NUMBER);


        TextInputEditText jngka_waktu_pinjaman = findViewById(R.id.textInputEditText3);
        jngka_waktu_pinjaman.setInputType(InputType.TYPE_CLASS_NUMBER);


        Button batal = findViewById(R.id.button4);
        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PengajuanPinjamanActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        Button kirim = findViewById(R.id.button5);
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text1 = jml_pengjuan_pinjaman.getText().toString();
                String text2 = jngka_waktu_pinjaman.getText().toString();

                String[] dataX = new String[3];
                dataX[0] = id_user;
                dataX[1] = text1;
                dataX[2] = text2;


                api_call.PENGJUAN_PINJAMAN_POST(
                        PengajuanPinjamanActivity.this,
                        base_url.uri() + "api/pinjaman",
                        dataX,
                        new ApiHelper.VolleyCallback() {
                            @Override
                            public void onSuccess(String[] result) {
                                Toast.makeText(PengajuanPinjamanActivity.this, result[0], Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(PengajuanPinjamanActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onError(String result) {
                                Toast.makeText(PengajuanPinjamanActivity.this, result, Toast.LENGTH_SHORT).show();
//
//                                Intent intent = new Intent(PengajuanPinjamanActivity.this, MainActivity.class);
//                                startActivity(intent);
//                                finish();
                            }
                        });


            }
        });
    }






}