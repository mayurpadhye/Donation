package com.example.myapplication.benificiary.registration.model;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
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

public class LocationModel implements ILocationModel{
    List<LocationModel> locationModelList=new ArrayList<>();
String loc_id,loc_name;
    private static final String TAG = "LocationModel";
    public LocationModel(String loc_id, String loc_name) {
        this.loc_id = loc_id;
        this.loc_name = loc_name;
    }

    public LocationModel()
    {

    }

    public String getLoc_id() {
        return loc_id;
    }

    public void setLoc_id(String loc_id) {
        this.loc_id = loc_id;
    }

    public String getLoc_name() {
        return loc_name;
    }

    public void setLoc_name(String loc_name) {
        this.loc_name = loc_name;
    }

    @Override
    public void getLocationResult(final onLocationFinishListener onLocationFinishListener) {


        RetrofitClient retrofitClient = new RetrofitClient();
        RestInterface service = retrofitClient.getAPIClient(WebServiceURLs.DOMAIN_NAME);
        service.GETLocation(new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {

                try {
                    JSONObject jsonObject = new JSONObject(jsonElement.toString());
                    String status=jsonObject.getString("status");
                    String message=jsonObject.getString("message");

                    if (status.equals("1"))
                    {
                        JSONArray location_deatils=jsonObject.getJSONArray("location_deatils");
                        for (int k=0;k<location_deatils.length();k++)
                        {
                            JSONObject j1=location_deatils.getJSONObject(k);
                            String loc_id=j1.getString("loc_id");
                            String loc_name=j1.getString("loc_name");
                            locationModelList.add(new LocationModel(loc_id,loc_name));

                        }
                        locationModelList.add(0,new LocationModel("0","Select Location"));
                        onLocationFinishListener.onLocationRequestFinish(locationModelList);




                    }




                } catch (JSONException | NullPointerException e) {

                    e.printStackTrace();
                    onLocationFinishListener.onLocationRequestFailure(e);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "failure: "+error);
             onLocationFinishListener.onLocationRequestFailure(error);

            }
        });
    }
}
