package com.kokarjabali.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kokarjabali.api.ApiHelper;
import com.kokarjabali.R;
import com.kokarjabali.api.Base_url;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    ApiHelper api_call = new ApiHelper();
    Base_url base_url = new Base_url();

    @SuppressLint("SetTextI18n")
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
        String n_saldo;
        if (SaldoAwal.equals("null")) {
            n_saldo = "0";
        }else{
            n_saldo = SaldoAwal;
        }
        SaldoAwalV.setText("Rp " + currencyFormat(n_saldo) +  ",-");

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



        Button info = findViewById(R.id.button5);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                api_call.PINJAMAN_GET(
                        MainActivity.this,
                        base_url.uri() + "api/pinjaman?user_id=" + data_log.getString("id", "null"),
                        new ApiHelper.VolleyCallback() {
                            @Override
                            public void onSuccess(String[] result) {

                                //dialog
                                Dialog dialog = new Dialog(MainActivity.this);
                                dialog.setContentView(R.layout.costume_dialog_1);
                                dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.backgroud_dialog));
                                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                dialog.setCancelable(false);
                                dialog.getWindow().getAttributes().windowAnimations = R.style.animation;


                                if (result[12].equals("0")){
                                    Toast.makeText(MainActivity.this, "Anda Tidak Memiliki Pinjaman" , Toast.LENGTH_SHORT).show();

//                                    dialog.show();
//
//                                    TextView viewtext = dialog.findViewById(R.id.textView);
//                                    viewtext.setText("Anda Belum Memiliki Pinjaman");
//
//                                    Button tutup = dialog.findViewById(R.id.btn_cancel);
//                                    tutup.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            dialog.dismiss();
//
//                                        }
//                                    });

                                }else{
                                    if (result[13].equals("MENUNGGU VALIDASI SEKRETARIS")) {
                                        Toast.makeText(MainActivity.this, "MENUNGGU VALIDASI SEKRETARIS.", Toast.LENGTH_SHORT).show();

//                                        dialog.show();
//
//                                        TextView viewtext = dialog.findViewById(R.id.textView);
//                                        viewtext.setText("MENUNGGU VALIDASI SEKRETARIS.");
//
//
//                                        Button tutup = dialog.findViewById(R.id.btn_cancel);
//                                        tutup.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//                                                dialog.dismiss();
//
//                                            }
//                                        });
                                    } else if (result[13].equals("MENUNGGU VALIDASI KETUA")) {
                                        Toast.makeText(MainActivity.this, "MENUNGGU VALIDASI KETUA.", Toast.LENGTH_SHORT).show();
//                                        dialog.show();
//
//                                        TextView viewtext = dialog.findViewById(R.id.textView);
//                                        viewtext.setText("MENUNGGU VALIDASI KETUA.");
//                                        viewtext.setPadding(70,150,70,150);
//                                        viewtext.setTextSize(24);
//
//                                        Button tutup = dialog.findViewById(R.id.btn_cancel);
//                                        tutup.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//                                                dialog.dismiss();
//
//                                            }
//                                        });
                                    }else {

                                        Intent intent = new Intent(MainActivity.this, InfoPinjamanActivity.class);
                                        intent.putExtra("id_user", data_log.getString("id", "null"));
                                        intent.putExtra("nama", data_log.getString("nama", "null"));
                                        startActivity(intent);
                                        finish();

                                    }
                                }

                            }

                            @Override
                            public void onError(String result) {
                                Toast.makeText(MainActivity.this, "Error " + result , Toast.LENGTH_SHORT).show();

                            }
                        });


            }
        });









        Button pengajuan = findViewById(R.id.button4);
        pengajuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PengajuanPinjamanActivity.class);
                intent.putExtra("id_user", data_log.getString("id", "null"));
                intent.putExtra("nama", data_log.getString("nama", "null"));
                startActivity(intent);
                finish();
            }
        });




        //*********************************************************************************
//
//        api_call.PINJAMAN_GET(
//                MainActivity.this,
//                base_url.uri() + "api/pinjaman?user_id=" + data_log.getString("id", "null"),
//                new ApiHelper.VolleyCallback() {
//                    @Override
//                    public void onSuccess(String[] result) {
//
//                        String[] pecah_tgl_server = result[10].split("-");
//                        String jngkawaktu = result[11];
//
//                        Calendar calendar = Calendar.getInstance();
//                        int year = calendar.get(Calendar.YEAR);
//                        int mounts = calendar.get(Calendar.MONTH) + 1;
//                        int days = calendar.get(Calendar.DAY_OF_MONTH);
//
//                        for (int i = 1; i <= Integer.parseInt(jngkawaktu); i++) {
//                            Calendar calendar_db = Calendar.getInstance();
//                            calendar_db.set(Calendar.MONTH, Integer.parseInt(pecah_tgl_server[1]));
//                            calendar_db.set(Calendar.YEAR, Integer.parseInt(pecah_tgl_server[0]));
//                            calendar_db.set(Calendar.DAY_OF_MONTH,  Integer.parseInt(pecah_tgl_server[2]));
//                            calendar_db.add(Calendar.MONTH, i-1);
//
//                            SimpleDateFormat f_tgl_db = new SimpleDateFormat("dd");
//                            SimpleDateFormat f_bulan_db = new SimpleDateFormat("MM");
//                            SimpleDateFormat f_tahun_db = new SimpleDateFormat("yyyy");
//
//                            String tgl_db = f_tgl_db.format(calendar_db.getTime());
//                            String bulan_db = f_bulan_db.format(calendar_db.getTime());
//                            String tahun_db = f_tahun_db.format(calendar_db.getTime());
//
//                            for (int j = 1; j <= 7; j++) {
//                                Calendar calendar_db_day = Calendar.getInstance();
//                                calendar_db_day.set(Calendar.MONTH, Integer.parseInt(bulan_db));
//                                calendar_db_day.set(Calendar.YEAR, Integer.parseInt(tahun_db));
//                                calendar_db_day.set(Calendar.DAY_OF_MONTH, Integer.parseInt(tgl_db));
//                                calendar_db_day.add(Calendar.DAY_OF_MONTH, -j);
//
//                                SimpleDateFormat f_tgl_db_day = new SimpleDateFormat("dd");
////                                SimpleDateFormat f_bulan_db_day = new SimpleDateFormat("MM");
////                                SimpleDateFormat f_tahun_db_day = new SimpleDateFormat("yyyy");
//
//                                int tgl_db_day = Integer.parseInt(f_tgl_db_day.format(calendar_db_day.getTime()));
//                                int bulan_db_day = Integer.parseInt(bulan_db);
//                                int tahun_db_day = Integer.parseInt(tahun_db);
//
//                                if (tgl_db_day == days & bulan_db_day == mounts & tahun_db_day == year ) {
//
//                                        //tampilkan dialog disini
////                                        Log.d("tes", String.valueOf(tgl_db_day));
//
//
//                                }
//                            }
//
//
//
//                        }
//
//
//
//
////                        @SuppressLint("SimpleDateFormat")
////                        SimpleDateFormat format = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
////                        SimpleDateFormat formatedate = new SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss");
////                        Date date_db_n;
////                        String dateTime_db_n;
////                        try {
////                            date_db_n  = format.parse(date_server);
////                            dateTime_db_n = formatedate.format(date_db_n);
////
////                        } catch (ParseException e) {
////                            throw new RuntimeException(e);
////                        }
//
////                        int date_server_nn = Integer.parseInt(dateTime_db_n);
//
////                        int date_time_now = (int) (new Date().getTime()/1000);
//
//                    }
//
//                    @Override
//                    public void onError(String result) {
//                        Toast.makeText(MainActivity.this, "Error " + result , Toast.LENGTH_SHORT).show();
//                    }
//                });




        //*********************************************************************

        api_call.Notifikasi_Auto_Debit_GET(
                MainActivity.this,
                base_url.uri() + "/api/pinjaman/notifikasi?user_id=" + data_log.getString("id", "null"),
                new ApiHelper.VolleyCallback() {
                    @Override
                    public void onSuccess(String[] result) {
                        if (result[0].equals("")){
//                            Toast.makeText(MainActivity.this, "KOSONG", Toast.LENGTH_SHORT).show();
                        }else {

                            //dialog
                            Dialog dialog = new Dialog(MainActivity.this);
                            dialog.setContentView(R.layout.costume_dialog_1);
                            dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.backgroud_dialog));
                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            dialog.setCancelable(false);
                            dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

                            dialog.show();

                            TextView viewtext = dialog.findViewById(R.id.textView);
                            viewtext.setText(result[0]);

                            Button tutup = dialog.findViewById(R.id.btn_cancel);
                            tutup.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();

                                }
                            });


//                            Toast.makeText(MainActivity.this, result[0], Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(String result) {

                    }
                }

        );



        //*********************************************************


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






    }

    public static String currencyFormat(String amount) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getInstance(localeID);
        return formatRupiah.format(Double.parseDouble(amount));

    }
}