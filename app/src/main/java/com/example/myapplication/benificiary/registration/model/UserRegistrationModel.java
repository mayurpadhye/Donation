package com.example.myapplication.benificiary.registration.model;

import android.content.Context;
import android.util.Log;
import com.example.myapplication.donor.home.model.HomeModel;
import com.example.myapplication.network.RestInterface;
import com.example.myapplication.network.RetrofitClient;
import com.example.myapplication.network.WebServiceURLs;
import com.google.gson.JsonElement;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.List;

public class UserRegistrationModel implements IUserRegis {
    Context context;
    List<CategoryListModel> categoryListModelList = new ArrayList<>();
    private static final String TAG = "UserRegistrationModel";
    int sign_up_stat = 0;

    public UserRegistrationModel(Context context) {
        this.context = context;
    }

    @Override
    public void signUpUser(String name, String age, String cat_id, String location_id, String mobile_no, String pass, String role, final onRegistrationRequestListener onRegistrationRequestListener) {

        RetrofitClient retrofitClient = new RetrofitClient();
        RestInterface service = retrofitClient.getAPIClient(WebServiceURLs.DOMAIN_NAME);
        service.register_user(name, age, cat_id, location_id, mobile_no, pass, role, new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {

                try {
                    JSONObject jsonObject = new JSONObject(jsonElement.toString());
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    onRegistrationRequestListener.onRegistrationFinshedListener(status, message);

                } catch (JSONException | NullPointerException e) {
                    onRegistrationRequestListener.RegistrationFailureListener(e);
                    e.printStackTrace();

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "failure: " + error);
                onRegistrationRequestListener.RegistrationFailureListener(error);
            }
        });
    }

    @Override
    public void getCategoryList(final onCategoryListListener onCategoryListListener) {
        RetrofitClient retrofitClient = new RetrofitClient();
        RestInterface service = retrofitClient.getAPIClient(WebServiceURLs.DOMAIN_NAME);
        service.getAllCategory(new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {

                try {
                    JSONObject jsonObject = new JSONObject(jsonElement.toString());
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if (status.equals("1")) {
                        JSONArray category_deatils = jsonObject.getJSONArray("category_deatils");
                        for (int k = 0; k < category_deatils.length(); k++) {
                            JSONObject j1 = category_deatils.getJSONObject(k);
                            String cat_id = j1.getString("cat_id");
                            String cat_name = j1.getString("cat_name");
                            String cat_img = j1.getString("cat_img");
                            categoryListModelList.add(new CategoryListModel(cat_id, cat_name, cat_img));

                        }
                        categoryListModelList.add(0,new CategoryListModel("0", "Select Category", ""));
                        onCategoryListListener.onCategoryListFinished(categoryListModelList);


                    }


                } catch (JSONException | NullPointerException e) {

                    e.printStackTrace();
                    onCategoryListListener.onCategoryListFailure(e);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "failure: " + error);
               onCategoryListListener.onCategoryListFailure(error);

            }
        });
    }


}
