package com.kokarjabali.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences data_log = getSharedPreferences("login", MODE_PRIVATE);

        if (data_log.getBoolean("logged_in", false) != false){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


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
                                String id = result[0];
                                String nama = result[1];
                                String saldo_awal = result[2];
                                String saldo_akhir = result[3];
                                String wilker = result[4];
                                String bergabung_sejak = result[5];
                                String simpanan_pokok = result[6];
                                String simpanan_wajib = result[7];
                                String simpanan_sukarela = result[8];
                                String no_hp = result[9];
                                String no_rek = result[10];

                                SharedPreferences.Editor editor = data_log.edit();
                                editor.putBoolean("logged_in", true);
                                editor.putString("id", id);
                                editor.putString("nama", nama);
                                editor.putString("saldo_awal", saldo_awal);
                                editor.putString("saldo_akhir", saldo_akhir);
                                editor.putString("wilker", wilker);
                                editor.putString("bergabung_sejak", bergabung_sejak);
                                editor.putString("simpanan_pokok", simpanan_pokok);
                                editor.putString("simpanan_wajib", simpanan_wajib);
                                editor.putString("simpanan_sukarela", simpanan_sukarela);
                                editor.putString("no_hp", no_hp);
                                editor.putString("no_rek", no_rek);
                                editor.apply();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onError(String result) {
                                Toast.makeText(LoginActivity.this, "Error : "  + result, Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });


    }
}