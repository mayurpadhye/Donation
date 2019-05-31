package com.example.myapplication.network;


import com.google.gson.JsonElement;
import retrofit.Callback;
import retrofit.http.*;
import retrofit.mime.MultipartTypedOutput;

/**
 * Rest Interface -> to access web_services using Retrofit
 */

public interface RestInterface {



  @FormUrlEncoded
    @POST(WebServiceURLs.REGISTER_USER)
    void register_user(@Field("full_name") String full_name,@Field("age") String age,
                       @Field("category_id") String category_id,
                       @Field("location_id") String location_id,
                       @Field("mobile_number") String mobile_number,
                       @Field("password") String password,
                       @Field("role") String role,
                       Callback<JsonElement> callback);

  @FormUrlEncoded
  @POST(WebServiceURLs.LOGIN_USER)
  void user_login(@Field("mobile_number") String mobile_number,
                     @Field("password") String password,
                     Callback<JsonElement> callback);

  @FormUrlEncoded
  @POST(WebServiceURLs.GET_BENIFICIARY_LIST)
  void getBenificiaryList(@Field("location_id") String location_id,
                  @Field("category_id") String category_id,
                  Callback<JsonElement> callback);



    @GET(WebServiceURLs.GET_LOCATION)
    void GETLocation(Callback<JsonElement> callback);

  @GET(WebServiceURLs.GET_ALL_CATEGORY)
  void getAllCategory(Callback<JsonElement> callback);

  @FormUrlEncoded
  @POST(WebServiceURLs.GET_USER_PROFILE)
  void get_user_profile(@Field("user_id") String user_id,
                        Callback<JsonElement> callback);

  @POST(WebServiceURLs.REQUEST_DONATION)
  void request_donation(
          @Body MultipartTypedOutput attachments,  Callback<JsonElement> callback);


}
