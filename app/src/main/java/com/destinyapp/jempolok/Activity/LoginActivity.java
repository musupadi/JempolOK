package com.destinyapp.jempolok.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.destinyapp.jempolok.API.ApiRequest;
import com.destinyapp.jempolok.API.RetroServer;
import com.destinyapp.jempolok.Model.Musupadi;
import com.destinyapp.jempolok.Model.ResponseModel;
import com.destinyapp.jempolok.R;
import com.destinyapp.jempolok.Services;
import com.destinyapp.jempolok.SharedPreferance.DB_Helper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText user,password;
    Musupadi musupadi = new Musupadi();
    DB_Helper dbHelper = new DB_Helper(this);
    LinearLayout available,loading;
    TextView forgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.btnLogin);
        user = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        forgotPassword = findViewById(R.id.tvForgotPassword);
        available = findViewById(R.id.linearAvailable);
        loading = findViewById(R.id.linearLoading);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Checker(user,password).equals("ok")){
                    Logic();
                }else{
                    Toast.makeText(LoginActivity.this, Checker(user,password), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private String Checker(EditText Username,EditText Password){
        String Check = "ok";
        if(Username.getText().toString().isEmpty()){
            Check = "Username Tidak Boleh Kosong";
        }else if(Password.getText().toString().isEmpty()){
            Check = "Password Tidak Boleh Kosong";
        }
        return Check;
    }
    private void Logic(){
        user.setEnabled(false);
        password.setEnabled(false);
        available.setAlpha((float) 0.5);
        loading.setVisibility(View.VISIBLE);
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> login =api.login(user.getText().toString(),password.getText().toString());
        login.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    if (response.body().getStatusCode().equals("000")){
                        if (response.body().getData().get(0).getLevelUser().equals("admin")||response.body().getData().get(0).getLevelUser().equals("super admin")){
                            Toast.makeText(LoginActivity.this, "User Admin hanya bisa login di Web", Toast.LENGTH_SHORT).show();
                        }else{
                            dbHelper.saveUser(user.getText().toString(),password.getText().toString(),response.body().getData().get(0).accessToken,response.body().getData().get(0).namaUser,response.body().getData().get(0).fotoUser,response.body().getData().get(0).levelUser,response.body().getData().get(0).statusUser);
                            musupadi.Back(LoginActivity.this);
                            finish();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                    }
                    available.setAlpha((float) 1.0);
                    loading.setVisibility(View.GONE);
                    user.setEnabled(true);
                    password.setEnabled(true);
                }catch (Exception e){
                    Toast.makeText(LoginActivity.this, "Terjadi Kesalahan "+e.toString(), Toast.LENGTH_SHORT).show();
                    available.setAlpha((float) 1.0);
                    loading.setVisibility(View.GONE);
                    user.setEnabled(true);
                    password.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
//                Toast.makeText(LoginActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                Toast.makeText(LoginActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
                user.setEnabled(true);
                password.setEnabled(true);
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("Keluar Dari Aplikasi ?")
                .setCancelable(false)
                .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                //Set your icon here
                .setTitle("Perhatian !!!")
                .setIcon(R.drawable.ic_baseline_close_24_red);
        AlertDialog alert = builder.create();
        alert.show();

    }
}