package com.destinyapp.jempolok.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.destinyapp.jempolok.API.ApiRequest;
import com.destinyapp.jempolok.API.RetroServer;
import com.destinyapp.jempolok.Model.ResponseModel;
import com.destinyapp.jempolok.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    RelativeLayout back;
    EditText Email,Token,Password1,Password2;
    Button Verif,Submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Email = findViewById(R.id.etEmail);
        Token = findViewById(R.id.etToken);
        Password1 = findViewById(R.id.etPassword1);
        Password2 = findViewById(R.id.etPassword2);
        Verif = findViewById(R.id.btnVerif);
        Submit = findViewById(R.id.btnSubmit);
        back = findViewById(R.id.relativeBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Verif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerifEmail();
            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePassword();
            }
        });
    }
    private void VerifEmail(){
        final ProgressDialog pd = new ProgressDialog(ForgotPasswordActivity.this);
        pd.setMessage("Mengirim Token Ke Email Pengguna");
        pd.show();
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> email = api.VerifikasiMail(Email.getText().toString());
        email.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    pd.hide();
                    Toast.makeText(ForgotPasswordActivity.this, response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    pd.hide();
                    Log.i("Error",e.toString());
                    Toast.makeText(ForgotPasswordActivity.this, "Terjadi Kesalahan "+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                pd.hide();
                Toast.makeText(ForgotPasswordActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void ChangePassword(){
        final ProgressDialog pd = new ProgressDialog(ForgotPasswordActivity.this);
        pd.setMessage("Mencoba Mengubah Password");
        pd.show();
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> email = api.ChangePassword(Email.getText().toString(),Token.getText().toString(),Password1.getText().toString(),Password2.getText().toString());
        email.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    pd.hide();
                    Toast.makeText(ForgotPasswordActivity.this, response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    pd.hide();
                    Log.i("Error",e.toString());
                    Toast.makeText(ForgotPasswordActivity.this, "Terjadi Kesalahan "+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                pd.hide();
                Toast.makeText(ForgotPasswordActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}