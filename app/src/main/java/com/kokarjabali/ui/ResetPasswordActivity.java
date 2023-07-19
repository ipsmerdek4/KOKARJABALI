package com.kokarjabali.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kokarjabali.R;
import com.kokarjabali.api.ApiHelper;
import com.kokarjabali.api.Base_url;

import org.w3c.dom.Text;

public class ResetPasswordActivity extends AppCompatActivity {


    ApiHelper api_call = new ApiHelper();
    Base_url base_url = new Base_url();
    String id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SharedPreferences data_log = getSharedPreferences("login", MODE_PRIVATE);



        Bundle DataX = getIntent().getExtras();
        id_user = DataX.getString("id_user");

        Button kembali = findViewById(R.id.button2);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPasswordActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button simpan = findViewById(R.id.button3);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView oldpassword = findViewById(R.id.editTextText);
                String oldpass = oldpassword.getText().toString();

                TextView newpassword = findViewById(R.id.editTextText2);
                String newpass = newpassword.getText().toString();

                TextView comfpassword = findViewById(R.id.editTextText3);
                String comfpass = comfpassword.getText().toString();


                String[] dataX = new String[4];
                dataX[0] = oldpass;
                dataX[1] = newpass;
                dataX[2] = comfpass;
                dataX[3] = id_user;

                api_call.RESET_Password(
                        ResetPasswordActivity.this,
                        base_url.uri() + "api/password",
                        dataX,
                        new ApiHelper.VolleyCallback() {
                            @Override
                            public void onSuccess(String[] result) {
                                Toast.makeText(ResetPasswordActivity.this, result[0], Toast.LENGTH_SHORT).show();

                                SharedPreferences.Editor editor = data_log.edit();
                                editor.clear().apply();

                                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();

//                                Intent intent = new Intent(ResetPasswordActivity.this, MainActivity.class);
//                                startActivity(intent);
//                                finish();

                            }

                            @Override
                            public void onError(String result) {
                                Toast.makeText(ResetPasswordActivity.this, result, Toast.LENGTH_SHORT).show();
        //
        //                                Intent intent = new Intent(PengajuanPinjamanActivity.this, MainActivity.class);
        //                                startActivity(intent);
        //                                finish();
                            }
                        });
//




            }
        });



    }
}