package com.kokarjabali.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.kokarjabali.api.ApiHelper;
import com.kokarjabali.R;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    private ApiHelper api_call = new ApiHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        SharedPreferences data_log = getSharedPreferences("login", MODE_PRIVATE);

        String nama = data_log.getString("nama", "null");
        TextView namaV = findViewById(R.id.textView3);
        namaV.setText(nama);

        String SaldoAwal = data_log.getString("saldo_awal", "null");
        TextView SaldoAwalV = findViewById(R.id.textView5);
        SaldoAwalV.setText("Rp " + currencyFormat(SaldoAwal) + ",-");

        String cabang = data_log.getString("wilker", "null");
        TextView cabangV = findViewById(R.id.textView6);
        cabangV.setText("Cabang " + cabang);

        String gabung = data_log.getString("tgl_gabung", "null");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatedate = new SimpleDateFormat("dd MMMM yyyy");
        Date date;
        String dateTime;
        try {
            date  = format.parse(gabung);
            dateTime = formatedate.format(date);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        TextView gabungV =  findViewById(R.id.textView7);
        gabungV.setText("Bergabung Sejak " + dateTime);


        Button Keluar = findViewById(R.id.button6);
        Keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = data_log.edit();
                editor.clear().apply();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        Button info = findViewById(R.id.button5);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, InfoPinjamanActivity.class);
                intent.putExtra("id_user", data_log.getString("id", "null"));
                startActivity(intent);
                finish();

            }
        });



    }

    public static String currencyFormat(String amount) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getInstance(localeID);
        return formatRupiah.format(Double.parseDouble(amount));

    }
}