package com.destinyapp.jempolok.API;

import com.destinyapp.jempolok.Model.ResponseModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
    @POST("report")
    Call<ResponseModel> Laporan(@Header("Authorization") String authHeader,
                                    @Part("namaReport") RequestBody namaReport,
                                    @Part MultipartBody.Part photo,
                                    @Part("deskripsiReport") RequestBody deskripsiReport,
                                    @Part("kegiatanPemeliharaan") RequestBody kegiatanPemeliharaan,
                                    @Part("lokasi") RequestBody lokasi,
                                    @Part("detailLokasi") RequestBody detailLokasi,
                                    @Part("kecamatanReport") RequestBody kecamatanReport,
                                    @Part("tanggalReport") RequestBody tanggalReport,
                                    @Part("alasanReject") RequestBody alasanReject);

    @Multipart
    @POST("report")
    Call<ResponseModel> Laporan2(@Header("Authorization") String authHeader,
                                 @Part("namaReport") RequestBody namaReport,
                                 @Part MultipartBody.Part photo,
                                 @Part MultipartBody.Part photo2,
                                 @Part("deskripsiReport") RequestBody deskripsiReport,
                                 @Part("kegiatanPemeliharaan") RequestBody kegiatanPemeliharaan,
                                 @Part("lokasi") RequestBody lokasi,
                                 @Part("detailLokasi") RequestBody detailLokasi,
                                 @Part("kecamatanReport") RequestBody kecamatanReport,
                                 @Part("tanggalReport") RequestBody tanggalReport,
                                 @Part("alasanReject") RequestBody alasanReject);

    @Multipart
    @POST("report")
    Call<ResponseModel> Laporan3(@Header("Authorization") String authHeader,
                                 @Part("namaReport") RequestBody namaReport,
                                 @Part MultipartBody.Part photo,
                                 @Part MultipartBody.Part photo2,
                                 @Part MultipartBody.Part photo3,
                                 @Part("deskripsiReport") RequestBody deskripsiReport,
                                 @Part("kegiatanPemeliharaan") RequestBody kegiatanPemeliharaan,
                                 @Part("lokasi") RequestBody lokasi,
                                 @Part("detailLokasi") RequestBody detailLokasi,
                                 @Part("kecamatanReport") RequestBody kecamatanReport,
                                 @Part("tanggalReport") RequestBody tanggalReport,
                                 @Part("alasanReject") RequestBody alasanReject);

    @Multipart
    @POST("report")
    Call<ResponseModel> Laporan4(@Header("Authorization") String authHeader,
                                 @Part("namaReport") RequestBody namaReport,
                                 @Part MultipartBody.Part photo,
                                 @Part MultipartBody.Part photo2,
                                 @Part MultipartBody.Part photo3,
                                 @Part MultipartBody.Part photo4,
                                 @Part("deskripsiReport") RequestBody deskripsiReport,
                                 @Part("kegiatanPemeliharaan") RequestBody kegiatanPemeliharaan,
                                 @Part("lokasi") RequestBody lokasi,
                                 @Part("detailLokasi") RequestBody detailLokasi,
                                 @Part("kecamatanReport") RequestBody kecamatanReport,
                                 @Part("tanggalReport") RequestBody tanggalReport,
                                 @Part("alasanReject") RequestBody alasanReject);

    @FormUrlEncoded
    @POST("report_assign_technician")
    Call<ResponseModel> AsignTechnician(@Header("Authorization") String authHeader,
                                        @Field("idReport") String idReport,
                                        @Field("statusReport") String statusReport,
                                        @Field("idTeknisi[]") List<String> idTeknisi);

    @GET("report")
    Call<ResponseModel> Report(@Header("Authorization") String authHeader);

    @GET("teknisi")
    Call<ResponseModel> Teknisi(@Header("Authorization") String authHeader);


    @GET("kecamatan")
    Call<ResponseModel> Kecamatan(@Header("Authorization") String authHeader);
}
