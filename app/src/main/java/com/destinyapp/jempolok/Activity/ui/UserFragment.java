package com.destinyapp.jempolok.Activity.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.destinyapp.jempolok.Activity.HomeActivity;
import com.destinyapp.jempolok.Activity.LoginActivity;
import com.destinyapp.jempolok.Model.Musupadi;
import com.destinyapp.jempolok.R;
import com.destinyapp.jempolok.SharedPreferance.DB_Helper;

public class UserFragment extends Fragment {
    String user,password,token,nama,foto,level,status;
    ImageView Profile;
    TextView Username,Nama,Level;
    Musupadi musupadi;
    Button EditProfile,ChangePassword;
    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Profile = view.findViewById(R.id.ivProfile);
        Username = view.findViewById(R.id.tvUsername);
        Nama = view.findViewById(R.id.tvNama);
        Level = view.findViewById(R.id.tvLevel);
        musupadi = new Musupadi();
        final DB_Helper dbHelper = new DB_Helper(getActivity());
        Cursor cursor = dbHelper.checkUser();
        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                user = cursor.getString(0);
                password = cursor.getString(1);
                token = cursor.getString(2);
                nama = cursor.getString(3);
                foto = cursor.getString(4);
                level = cursor.getString(5);
                status = cursor.getString(6);
            }
            String URL = musupadi.BASE_URL();
            Glide.with(getActivity())
                    .load(URL+foto)
                    .into(Profile);
            Username.setText(user);
            Nama.setText(nama);
            Level.setText(level);
        }

    }
}