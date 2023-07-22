package com.kokarjabali.ui;

import static android.graphics.Color.parseColor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.kokarjabali.R;
import com.kokarjabali.api.ApiHelper;
import com.kokarjabali.api.Base_url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PengajuanPinjamanActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


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


        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);




        api_call.JANGKAWAKTU_GET(
                this,
                base_url.uri() + "api/pinjaman/jangka-waktu?user_id=" + id_user,
                new ApiHelper.VolleyCallback() {
                    @Override
                    public void onSuccess(String[] result) {

                        // Spinner Drop down elements
                        List<String> categories = new ArrayList<String>();
                            categories.add("-- Pilih Tahun Pengajuan --");

                        try {
                            JSONArray jsonArray = new JSONArray(result[1]);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                categories.add(jsonArray.getString(i));
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                        // Creating adapter for spinner
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(PengajuanPinjamanActivity.this, android.R.layout.simple_spinner_item, categories);

                        // Drop down layout style - list view with radio button
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // attaching data adapter to spinner
                        spinner.setAdapter(dataAdapter);

                        String selection = "-- Pilih Tahun Pengajuan --";

                        int spinnerPosition = dataAdapter.getPosition(selection);

                        // on below line we are setting selection for our spinner to spinner position.
                        spinner.setSelection(spinnerPosition);


                    }

                    @Override
                    public void onError(String result) {
                        Toast.makeText(PengajuanPinjamanActivity.this, "Error " + result , Toast.LENGTH_SHORT).show();

//
//                        Intent intent = new Intent(MutasiSaldoActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();

                    }
                });



        TextInputEditText jml_pengjuan_pinjaman = findViewById(R.id.textInputEditText2);
        jml_pengjuan_pinjaman.setInputType(InputType.TYPE_CLASS_NUMBER);
//

//        TextInputEditText jngka_waktu_pinjaman = findViewById(R.id.textInputEditText3);
//        jngka_waktu_pinjaman.setInputType(InputType.TYPE_CLASS_NUMBER);

//
        Button batal = findViewById(R.id.button4);
        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(PengajuanPinjamanActivity.this, String.valueOf(spinner.getSelectedItem()), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(PengajuanPinjamanActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

//
        Button kirim = findViewById(R.id.button5);
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text1 = jml_pengjuan_pinjaman.getText().toString();
                String text2 = String.valueOf(spinner.getSelectedItem());

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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
//        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

    }
}