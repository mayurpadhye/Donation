package com.example.myapplication.donor.home.model;

import android.util.Log;
import com.example.myapplication.benificiary.registration.model.LocationModel;
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

public class HomeModel implements IHomeModel {
    private static final String TAG = "HomeModel";
    List<HomeModel> homeModelList=new ArrayList<>();
    String cat_id,cat_name,cat_image;

    public HomeModel(String cat_id, String cat_name, String cat_image) {
        this.cat_id = cat_id;
        this.cat_name = cat_name;
        this.cat_image = cat_image;
    }

    public HomeModel() {
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_image() {
        return cat_image;
    }

    public void setCat_image(String cat_image) {
        this.cat_image = cat_image;
    }

    @Override
    public void getAllCategory(final onCategoryRequestFinishedListener onCategoryRequestFinishedListener) {
        RetrofitClient retrofitClient = new RetrofitClient();
        RestInterface service = retrofitClient.getAPIClient(WebServiceURLs.DOMAIN_NAME);
        service.getAllCategory(new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {

                try {
                    JSONObject jsonObject = new JSONObject(jsonElement.toString());
                    String status=jsonObject.getString("status");
                    String message=jsonObject.getString("message");

                    if (status.equals("1"))
                    {
                        JSONArray category_deatils=jsonObject.getJSONArray("category_deatils");
                        for (int k=0;k<category_deatils.length();k++)
                        {
                            JSONObject j1=category_deatils.getJSONObject(k);
                            String cat_id=j1.getString("cat_id");
                            String cat_name=j1.getString("cat_name");
                            String cat_img=j1.getString("cat_img");
                            homeModelList.add(new HomeModel(cat_id,cat_name,cat_img));

                        }

                        onCategoryRequestFinishedListener.onCategoryRequestFinished(homeModelList);




                    }




                } catch (JSONException | NullPointerException e) {

                    e.printStackTrace();
                    onCategoryRequestFinishedListener.onCategoryRequestFailure(e);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "failure: "+error);
                onCategoryRequestFinishedListener.onCategoryRequestFailure(error);

            }
        });
    }
}
