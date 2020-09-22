package com.destinyapp.jempolok.API;

import com.destinyapp.jempolok.Model.ResponseModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiRequest {
    @FormUrlEncoded
    @POST("auth/login")
    Call<ResponseModel> login(@Field("username") String username,
                            @Field("password") String password);

    @Multipart
    @POST("Bukti")
    Call<ResponseModel> UploadBukti(@Part("namaReport") RequestBody namaReport,
                                    @Part MultipartBody.Part photo,
                                    @Part("deskripsiReport") RequestBody deskripsiReport,
                                    @Part("kegiatanPemeliharaan") RequestBody kegiatanPemeliharaan,
                                    @Part("lokasi") RequestBody lokasi,
                                    @Part("kecamatanReport") RequestBody kecamatanReport,
                                    @Part("tanggalReport") RequestBody tanggalReport,
                                    @Part("alasanReject") RequestBody alasanReject);
}
