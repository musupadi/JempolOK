package com.destinyapp.jempolok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText user,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.btnLogin);
        user = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Checker(user,password).equals("ok")){
                    if (user.getText().toString().equals("destiny") && password.getText().toString().equals("123")){
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "Email atau Password Salah", Toast.LENGTH_SHORT).show();
                    }
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
}