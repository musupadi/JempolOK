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
    Call<ResponseModel> login(@Header("Authorization") String authHeader,
                            @Field("username") String kuncifaba,
                            @Field("password") String nama_param);

    @Multipart
    @POST("Bukti")
    Call<ResponseModel> UploadBukti(@Part("id_user") RequestBody id_user,
                                    @Part MultipartBody.Part bukti);
}
