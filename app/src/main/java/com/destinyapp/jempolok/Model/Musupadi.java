package com.destinyapp.jempolok.Model;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.destinyapp.jempolok.API.ApiRequest;
import com.destinyapp.jempolok.API.RetroServer;
import com.destinyapp.jempolok.Activity.CheckLaporanActivity;
import com.destinyapp.jempolok.Activity.FormLaporanActivity;
import com.destinyapp.jempolok.Activity.MainActivity;
import com.destinyapp.jempolok.Adapter.AdapterKategori;
import com.destinyapp.jempolok.SharedPreferance.DB_Helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Musupadi {
    public String BASE_URL(){
        String BASE_URL = "https://jempol.destinyconsultant.tech";
        return BASE_URL;
    }
    public String AUTH(String auth){
        String authHeader = "Bearer "+auth;
        return authHeader;
    }
    public void Back(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
    public void ReyclerView3(RecyclerView rv,final Context ctx){
        rv.setHasFixedSize(true);
//        recyclerKategori.setLayoutManager(new LinearLayoutManager(FormLaporanActivity.this));
        rv.setLayoutManager(new GridLayoutManager(ctx, 3));
        DB_Helper dbHelper = new DB_Helper(ctx);
        AdapterKategori Adapter = new AdapterKategori(ctx,dbHelper.kategoriList(),rv);
//        Adapter.notifyDataSetChanged();
        rv.setAdapter(Adapter);
    }
    public void Login(final Context ctx,final String user,final String password){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        final DB_Helper dbHelper = new DB_Helper(ctx);
        Call<ResponseModel> login =api.login(user,password);
        login.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    if (response.body().getStatusCode().equals("000")){
                        dbHelper.Logout();
                        dbHelper.saveUser(user,password,response.body().getData().get(0).accessToken,response.body().getData().get(0).namaUser,response.body().getData().get(0).fotoUser,response.body().getData().get(0).levelUser,response.body().getData().get(0).statusUser);
                    }else{
                        Toast.makeText(ctx, response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(ctx, "Terjadi Kesalahan "+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
//                Toast.makeText(LoginActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public String getToday(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String thisDay = dateFormat.format(date);
        String today = dayName(thisDay,"dd/MM/yyyy");
        String HariIni = "Senin";
        if(today.equals("Monday")){
            HariIni = "Senin";
        }else if(today.equals("Tuesday")){
            HariIni = "Selasa";
        }else if(today.equals("Wednesday")){
            HariIni = "Rabu";
        }else if(today.equals("Thursday")){
            HariIni = "Kamis";
        }else if(today.equals("Friday")){
            HariIni = "Jumat";
        }else if(today.equals("Saturday")){
            HariIni = "Sabtu";
        }else if(today.equals("Sunday")){
            HariIni = "Minggu";
        }
        return HariIni;
    }
    public String MagicDateChange(String dates){
        String result = "";
        String year = dates.substring(0,4);
        String month = dates.substring(5,7);
        String day = dates.substring(8,10);
        String MONTH = "Januari";
        if (month.equals("01") || month.equals("1")){
            MONTH = "Januari";
        }else if (month.equals("02") || month.equals("2")){
            MONTH = "Februari";
        }else if (month.equals("03") || month.equals("3")){
            MONTH = "Maret";
        }else if (month.equals("04") || month.equals("4")){
            MONTH = "April";
        }else if (month.equals("05") || month.equals("5")){
            MONTH = "Mei";
        }else if (month.equals("06") || month.equals("6")){
            MONTH = "Juni";
        }else if (month.equals("07") || month.equals("7")){
            MONTH = "Juli";
        }else if (month.equals("08") || month.equals("8")){
            MONTH = "Agustus";
        }else if (month.equals("09") || month.equals("9")){
            MONTH = "September";
        }else if (month.equals("10")){
            MONTH = "Oktober";
        }else if (month.equals("11")){
            MONTH = "November";
        }else if (month.equals("12")){
            MONTH = "Desember";
        }
        result = day+"-"+MONTH+"-"+year;
        return result;

    }
    public static String thisDay(){
        DateFormat dateFormat1 = new SimpleDateFormat("dd");
        DateFormat dateFormat2 = new SimpleDateFormat("MM");
        DateFormat dateFormat3 = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String Day = dateFormat1.format(date);
        String month = dateFormat2.format(date);
        String Year = dateFormat3.format(date);
        String MONTH = "Januari";
        if (month.equals("01") || month.equals("1")){
            MONTH = "Januari";
        }else if (month.equals("02") || month.equals("2")){
            MONTH = "Februari";
        }else if (month.equals("03") || month.equals("3")){
            MONTH = "Maret";
        }else if (month.equals("04") || month.equals("4")){
            MONTH = "April";
        }else if (month.equals("05") || month.equals("5")){
            MONTH = "Mei";
        }else if (month.equals("06") || month.equals("6")){
            MONTH = "Juni";
        }else if (month.equals("07") || month.equals("7")){
            MONTH = "Juli";
        }else if (month.equals("08") || month.equals("8")){
            MONTH = "Agustus";
        }else if (month.equals("09") || month.equals("9")){
            MONTH = "September";
        }else if (month.equals("10")){
            MONTH = "Oktober";
        }else if (month.equals("11")){
            MONTH = "November";
        }else if (month.equals("12")){
            MONTH = "Desember";
        }
        return Day+"-"+MONTH+"-"+Year;
    }
    public static String dayName(String inputDate, String format){
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
    }
}
