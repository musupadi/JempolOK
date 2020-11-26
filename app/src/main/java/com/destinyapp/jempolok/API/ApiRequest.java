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
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRequest {
    @FormUrlEncoded
    @POST("auth/login")
    Call<ResponseModel> login(@Field("username") String username,
                            @Field("password") String password);

    @FormUrlEncoded
    @POST("testmail")
    Call<ResponseModel> VerifikasiMail(@Field("emailTaget") String emailTaget);

    @FormUrlEncoded
    @POST("changepassword")
    Call<ResponseModel> ChangePassword(@Field("email") String email,
                                       @Field("token") String token,
                                       @Field("email") String newPassword,
                                       @Field("email") String confirmPassword);

    @FormUrlEncoded
    @POST("changepasswordonlogin")
    Call<ResponseModel> ChangePasswordLogin(@Header("Authorization") String authHeader,
                                            @Field("lastPassword") String lastPassword,
                                            @Field("newPassword") String newPassword,
                                            @Field("confirmPassword") String confirmPassword);

    @Multipart
    @POST("profil")
    Call<ResponseModel> ChangeProfile(@Header("Authorization") String authHeader,
                                @Part("namaUser") RequestBody namaUser,
                                @Part MultipartBody.Part fotoUser);




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
                                    @Part("alasanReport") RequestBody alasanReport,
                                    @Query("kegiatan[]") ArrayList<String> kegiatan,
                                    @Query("kategori[]") ArrayList<String> kategori,
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
                                 @Part("alasanReport") RequestBody alasanReport,
                                 @Query("kegiatan[]") ArrayList<String> kegiatan,
                                 @Query("kategori[]") ArrayList<String> kategori,
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
                                 @Part("alasanReport") RequestBody alasanReport,
                                 @Query("kegiatan[]") ArrayList<String> kegiatan,
                                 @Query("kategori[]") ArrayList<String> kategori,
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
                                 @Part("alasanReport") RequestBody alasanReport,
                                 @Query("kegiatan[]") ArrayList<String> kegiatan,
                                 @Query("kategori[]") ArrayList<String> kategori,
                                 @Part("alasanReject") RequestBody alasanReject);

    @FormUrlEncoded
    @POST("report_assign")
    Call<ResponseModel> AsignTechnician(@Header("Authorization") String authHeader,
                                        @Field("idReport") String idReport,
                                        @Field("statusReport") String statusReport,
                                        @Field("idTeknisi[]") List<String> idTeknisi);

    @Multipart
    @POST("report_assign")
    Call<ResponseModel> AssignSucces(@Header("Authorization") String authHeader,
                                     @Part("idReport") RequestBody idReport,
                                     @Part("statusReport") RequestBody statusReport,
                                     @Part MultipartBody.Part photo,
                                     @Query("idTeknisi[]") ArrayList<String> idTeknisi,
                                     @Query("scoreId[]") ArrayList<String> scoreId,
                                     @Query("isiReview[]") ArrayList<String> isiReview);

    @Multipart
    @POST("report_assign")
    Call<ResponseModel> AssignSucces(@Header("Authorization") String authHeader,
                                     @Part("idReport") RequestBody idReport,
                                     @Part("statusReport") RequestBody statusReport,
                                     @Part MultipartBody.Part photo,
                                     @Part MultipartBody.Part photo2,
                                     @Query("idTeknisi[]") ArrayList<String> idTeknisi,
                                     @Query("scoreId[]") ArrayList<String> scoreId,
                                     @Query("isiReview[]") ArrayList<String> isiReview);

    @Multipart
    @POST("report_assign")
    Call<ResponseModel> AssignSucces(@Header("Authorization") String authHeader,
                                     @Part("idReport") RequestBody idReport,
                                     @Part("statusReport") RequestBody statusReport,
                                     @Part MultipartBody.Part photo,
                                     @Part MultipartBody.Part photo2,
                                     @Part MultipartBody.Part photo3,
                                     @Query("idTeknisi[]") ArrayList<String> idTeknisi,
                                     @Query("scoreId[]") ArrayList<String> scoreId,
                                     @Query("isiReview[]") ArrayList<String> isiReview);

    @Multipart
    @POST("report_assign")
    Call<ResponseModel> AssignSucces(@Header("Authorization") String authHeader,
                                     @Part("idReport") RequestBody idReport,
                                     @Part("statusReport") RequestBody statusReport,
                                     @Part MultipartBody.Part photo,
                                     @Part MultipartBody.Part photo2,
                                     @Part MultipartBody.Part photo3,
                                     @Part MultipartBody.Part photo4,
                                     @Query("idTeknisi[]") ArrayList<String> idTeknisi,
                                     @Query("scoreId[]") ArrayList<String> scoreId,
                                     @Query("isiReview[]") ArrayList<String> isiReview);

    @FormUrlEncoded
    @POST("report_assign")
    Call<ResponseModel> Asign(@Header("Authorization") String authHeader,
                                        @Field("idReport") String idReport,
                                        @Field("statusReport") String statusReport);

    @FormUrlEncoded
    @POST("notif")
    Call<ResponseModel> PostNotif(@Header("Authorization") String authHeader,
                              @Field("idnotif") String idReport);

    //GET
    @GET("notif")
    Call<ResponseModel> GetNotification(@Header("Authorization") String authHeader);

    @GET("report")
    Call<ResponseModel> Report(@Header("Authorization") String authHeader);

    @GET("report?")
    Call<ResponseModel> ReportIDS(@Header("Authorization") String authHeader,
                                  @Query("idReport") String idReport);

    @GET("teknisi")
    Call<ResponseModel> Teknisi(@Header("Authorization") String authHeader);

    @GET("kategori")
    Call<ResponseModel> Kategori(@Header("Authorization") String authHeader);

    @GET("kegiatan")
    Call<ResponseModel> Kegiatan(@Header("Authorization") String authHeader);

    @GET("kecamatan")
    Call<ResponseModel> Kecamatan(@Header("Authorization") String authHeader);
}
