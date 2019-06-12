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
                       @Field("ifsc_code") String ifsc_code,
                       @Field("acc_number") String acc_number,
                       @Field("bank_name") String bank_name,
                       @Field("account_name") String account_name,
                       @Field("perment_address") String perment_address,
                       @Field("password") String password,
                       @Field("role") String role,
                       Callback<JsonElement> callback);



  @FormUrlEncoded
  @POST(WebServiceURLs.LOGIN_USER)
  void user_login(@Field("mobile_number") String mobile_number,
                     @Field("password") String password,
                     @Field("role") String role,
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


  @FormUrlEncoded
  @POST(WebServiceURLs.CHANGE_DONATION_STATUS)
  void change_donation_status(@Field("details_id") String details_id,
                        Callback<JsonElement> callback);




  @FormUrlEncoded
  @POST(WebServiceURLs.REGISTER_DONOR)
  void register_donor_user(@Field("full_name") String full_name,@Field("age") String age,
                           @Field("mobile_number") String mobile_number,
                           @Field("role") String role,
                           @Field("password") String password,
                           @Field("location_id") String location_id,
                           @Field("category_id") String category_id,

                           Callback<JsonElement> callback);
/*


  @FormUrlEncoded
  @POST(WebServiceURLs.ADD_DONATION_DETAILS)
  void add_donation_details(@Field("loct_id") String full_name,@Field("age") String age,
                           @Field("cate_id") String mobile_number,
                           @Field("donated_amount") String role,
                           @Field("donted_id") String password,
                           @Field("beneficary_id") String location_id,
                           @Field("mode_payment") String category_id,

                           Callback<JsonElement> callback);
*/



  @FormUrlEncoded
  @POST(WebServiceURLs.GET_CAT_WISE_USER)
  void get_category_wise_user(@Field("loc_id") String loc_id,@Field("cat_id") String cat_id,Callback<JsonElement> callback);
  @FormUrlEncoded
  @POST(WebServiceURLs.GET_DONATION_DETAILS)
  void getDonationDetails(@Field("user_id") String loc_id,Callback<JsonElement> callback);


  @POST(WebServiceURLs.REQUEST_DONATION)
  void request_donation(
          @Body MultipartTypedOutput attachments,  Callback<JsonElement> callback);

  @POST(WebServiceURLs.ADD_DONATION_DETAILS)
  void add_donation_details(
          @Body MultipartTypedOutput attachments,  Callback<JsonElement> callback);
}
