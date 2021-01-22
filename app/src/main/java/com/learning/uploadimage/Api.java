package com.learning.uploadimage;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

interface Api {

    @FormUrlEncoded
    @POST("image.php")
    Call<ResponseModel> uploadImage (@Field("status") String status,@Field("message") String message);



}
