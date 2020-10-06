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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.destinyapp.jempolok.Activity.CheckLaporanActivity;
import com.destinyapp.jempolok.Activity.FormLaporanActivity;
import com.destinyapp.jempolok.Activity.MainActivity;
import com.destinyapp.jempolok.Model.Musupadi;
import com.destinyapp.jempolok.R;
import com.destinyapp.jempolok.SharedPreferance.DB_Helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    LinearLayout laporanKerusakan,checklaporan;
    LinearLayout Pelaksana,Pengguna;
    String user,password,token,nama,foto,level,status;
    DB_Helper dbHelper;
    ImageView ivHeader;
    TextView tvHeader,tvTgl;
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        laporanKerusakan = view.findViewById(R.id.linearLaporanKerusakan);
        checklaporan = view.findViewById(R.id.linearCheckLaporan);
        Pelaksana = view.findViewById(R.id.LinearPelaksana);
        Pengguna = view.findViewById(R.id.LinearPengguna);
        ivHeader = view.findViewById(R.id.ivHeader);
        tvHeader = view.findViewById(R.id.tvHeader);
        tvTgl = view.findViewById(R.id.tvTgl);
        dbHelper = new DB_Helper(getActivity());
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
        }
        if (level.equals("pengguna")){
            Pelaksana.setVisibility(View.GONE);
            Pengguna.setVisibility(View.VISIBLE);
        }else if(level.equals("pelaksana")){
            Pengguna.setVisibility(View.GONE);
            Pelaksana.setVisibility(View.VISIBLE);
        }
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        if (hour > 4 && hour < 11){
            tvHeader.setText("Selamat Pagi, "+nama);
            ivHeader.setImageResource(R.drawable.morning);
        }else if(hour >= 11 && hour <15){
            tvHeader.setText("Selamat Siang, "+nama);
            ivHeader.setImageResource(R.drawable.afternoon);
        }else if(hour >= 15 && hour <18){
            tvHeader.setText("Selamat Sore, "+nama);
            ivHeader.setImageResource(R.drawable.evening);
        }else{
            tvHeader.setText("Selamat Malam, "+nama);
            ivHeader.setImageResource(R.drawable.night);
        }
        Musupadi musupadi = new Musupadi();
        tvTgl.setText(musupadi.getToday()+", "+musupadi.thisDay());
        laporanKerusakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FormLaporanActivity.class);
                startActivity(intent);
            }
        });
        checklaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CheckLaporanActivity.class);
                startActivity(intent);            }
        });
    }
}