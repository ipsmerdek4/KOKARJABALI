package com.kokarjabali.ui;

import static android.graphics.Color.parseColor;

import static com.android.volley.VolleyLog.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kokarjabali.R;
import com.kokarjabali.api.ApiHelper;
import com.kokarjabali.api.Base_url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MutasiSaldoActivity extends AppCompatActivity {


    ApiHelper api_call = new ApiHelper();
    Base_url base_url = new Base_url();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutasi_saldo);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Bundle DataX = getIntent().getExtras();
        String id_user = DataX.getString("id_user");
        String nama = DataX.getString("nama");

        TextView namav = findViewById(R.id.textView11);
        namav.setText(nama);

        api_call.MUTASI_GET(
                this,
                base_url.uri() + "api/mutasi?user_id=" + id_user,
                new ApiHelper.VolleyCallback() {
                    @Override
                    public void onSuccess(String[] result) {

                        try {
                            JSONArray jsonArray = new JSONArray(result[1]);


                            LinearLayout tableLayout = findViewById(R.id.table_1);


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = new JSONObject(jsonArray.getString(i));

                                LinearLayout.LayoutParams p_tRow = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                if (i > 0 ){
                                    p_tRow.setMargins(0,20,0,0);
                                }else{
                                    p_tRow.setMargins(0,0,0,0);
                                }
                                LinearLayout tRow = new LinearLayout(MutasiSaldoActivity.this);
                                tRow.setOrientation(LinearLayout.VERTICAL);
                                tRow.setPadding(0,0,0,0);
                                tRow.setLayoutParams(p_tRow);
                                tRow.setBackgroundResource(R.drawable.background_border2);





                                LinearLayout.LayoutParams paramstxt1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                                paramstxt1.setMargins(0,0,0,0);
                                TextView taggal = new TextView(MutasiSaldoActivity.this);
                                taggal.setLayoutParams(paramstxt1);
//                                Syn.setId(i);
                                taggal.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                                taggal.setTypeface(null, Typeface.BOLD);
                                taggal.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                taggal.setPadding(0,15,0,15);
                                taggal.setTextColor(parseColor("#ffffff"));

                                String dtStart = jsonObject.getString("tanggal") ;
                                String date_after = formateDateFromstring("yyyy-MM-dd", "dd MMMM yyyy", dtStart);

                                taggal.setText(date_after);

                                tRow.addView(taggal);


                                LinearLayout.LayoutParams boxparam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                LinearLayout box = new LinearLayout(MutasiSaldoActivity.this);
                                box.setOrientation(LinearLayout.HORIZONTAL);
                                box.setPadding(0,0,0,0);
                                box.setLayoutParams(boxparam);
                                box.setPadding(0,15,0,15);
                                box.setBackgroundResource(R.drawable.background_border);



                                LinearLayout.LayoutParams paramstxt21 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                                paramstxt21.setMargins(0,0,0,0);
                                TextView taggal21 = new TextView(MutasiSaldoActivity.this);
                                taggal21.setLayoutParams(paramstxt21);
//                                Syn.setId(i);
                                taggal21.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                                taggal21.setTypeface(null, Typeface.BOLD);
                                taggal21.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                taggal21.setTextColor(parseColor("#8c8c8c"));
                                taggal21.setText( jsonObject.getString("keterangan") );
                                box.addView(taggal21);


                                LinearLayout.LayoutParams paramstxt22 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                                paramstxt22.setMargins(0,0,0,0);
                                TextView taggal22 = new TextView(MutasiSaldoActivity.this);
                                taggal22.setLayoutParams(paramstxt22);
//                                Syn.setId(i);
                                taggal22.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                                taggal22.setTypeface(null, Typeface.BOLD);
                                taggal22.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                taggal22.setTextColor(parseColor("#8c8c8c"));
                                taggal22.setText( jsonObject.getString("nominal_formatted") + "\n" + jsonObject.getString("jam_formatted") );
                                box.addView(taggal22);



                                tRow.addView(box);
                                tableLayout.addView(tRow);

                            }



                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }





                    }

                    @Override
                    public void onError(String result) {
                        Toast.makeText(MutasiSaldoActivity.this, "Error " + result , Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(MutasiSaldoActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });


        Button Saldo = findViewById(R.id.button9);
        Saldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MutasiSaldoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        Button Pinjaman = findViewById(R.id.button10);
        Pinjaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                api_call.PINJAMAN_GET(
                        MutasiSaldoActivity.this,
                        base_url.uri() + "api/pinjaman?user_id=" + id_user,
                        new ApiHelper.VolleyCallback() {
                            @Override
                            public void onSuccess(String[] result) {

                                //dialog
                                Dialog dialog = new Dialog(MutasiSaldoActivity.this);
                                dialog.setContentView(R.layout.costume_dialog_1);
                                dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.backgroud_dialog));
                                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                dialog.setCancelable(false);
                                dialog.getWindow().getAttributes().windowAnimations = R.style.animation;


                                if (result[12].equals("0")){
                                    Toast.makeText(MutasiSaldoActivity.this, "Anda Tidak Memiliki Pinjaman" , Toast.LENGTH_SHORT).show();


                                }else{
                                    if (result[13].equals("MENUNGGU VALIDASI SEKRETARIS")) {
                                        Toast.makeText(MutasiSaldoActivity.this, "MENUNGGU VALIDASI SEKRETARIS.", Toast.LENGTH_SHORT).show();

                                    } else if (result[13].equals("MENUNGGU VALIDASI KETUA")) {
                                        Toast.makeText(MutasiSaldoActivity.this, "MENUNGGU VALIDASI KETUA.", Toast.LENGTH_SHORT).show();
//
                                    }else {

                                        Intent intent = new Intent(MutasiSaldoActivity.this, InfoPinjamanActivity.class);
                                        intent.putExtra("id_user", id_user);
                                        intent.putExtra("nama", nama);
                                        startActivity(intent);
                                        finish();

                                    }
                                }

                            }

                            @Override
                            public void onError(String result) {
                                Toast.makeText(MutasiSaldoActivity.this, "Error " + result , Toast.LENGTH_SHORT).show();

                            }
                        });

            }
        });


    }

    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate){

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {

//            Toast.makeText(MutasiSaldoActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
//            LOGE(TAG, "ParseException - dateFormat");
        }

        return outputDate;

    }
}