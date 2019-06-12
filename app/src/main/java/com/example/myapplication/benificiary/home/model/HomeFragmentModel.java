package com.example.myapplication.benificiary.home.model;

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

public class HomeFragmentModel implements IHomeFragmentModel {

    String dt_id,loct_id,cate_id,donated_amount,donted_id,beneficary_id,mode_payemnt,recp_img,donation_status,donation_date,approved_date,ref_number;
    String donermobno,donerfullname,loc_name,cat_name;
List<HomeFragmentModel> homeFragmentModelList=new ArrayList<>();
    public HomeFragmentModel() {
    }

    public HomeFragmentModel(String dt_id, String loct_id, String cate_id, String donated_amount, String donted_id, String beneficary_id, String mode_payemnt, String recp_img, String donation_status, String donation_date, String approved_date, String ref_number, String donermobno, String donerfullname, String loc_name, String cat_name) {
        this.dt_id = dt_id;
        this.loct_id = loct_id;
        this.cate_id = cate_id;
        this.donated_amount = donated_amount;
        this.donted_id = donted_id;
        this.beneficary_id = beneficary_id;
        this.mode_payemnt = mode_payemnt;
        this.recp_img = recp_img;
        this.donation_status = donation_status;
        this.donation_date = donation_date;
        this.approved_date = approved_date;
        this.ref_number = ref_number;
        this.donermobno = donermobno;
        this.donerfullname = donerfullname;
        this.loc_name = loc_name;
        this.cat_name = cat_name;
    }

    @Override
    public void getDonationDetails(String user_id, final onDonationDetailListener onDonationDetailListener) {
        RetrofitClient retrofitClient = new RetrofitClient();
        RestInterface service = retrofitClient.getAPIClient(WebServiceURLs.DOMAIN_NAME);
        service.getDonationDetails(user_id,new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonElement.toString());
                    String status=jsonObject.getString("status");
                    if (status.equals("1"))
                    {
                        JSONArray donation_deatils=jsonObject.getJSONArray("donation_deatils");
                        for (int i=0;i<donation_deatils.length();i++)
                        {
                            JSONObject j1=donation_deatils.getJSONObject(i);
                            String dt_id=j1.getString("dt_id");
                            String loct_id=j1.getString("loct_id");
                            String cate_id=j1.getString("cate_id");
                            String donated_amount=j1.getString("donated_amount");
                            String donted_id=j1.getString("donted_id");
                            String beneficary_id=j1.getString("beneficary_id");
                            String mode_payemnt=j1.getString("mode_payemnt");
                            String recp_img=j1.getString("recp_img");
                            String donation_status=j1.getString("donation_status");
                            String donation_date=j1.getString("donation_date");
                            String approved_date=j1.getString("approved_date");
                            String ref_number=j1.getString("ref_number");
                            String donermobno=j1.getString("donermobno");
                            String donerfullname=j1.getString("donerfullname");
                            String loc_name=j1.getString("loc_name");
                            String cat_name=j1.getString("cat_name");
                            homeFragmentModelList.add(new HomeFragmentModel(dt_id,loct_id,cate_id,donated_amount,donted_id,beneficary_id,mode_payemnt
                            ,recp_img,donation_status,donation_date,approved_date,ref_number,donermobno,donerfullname,loc_name,cat_name));
                        }
                        onDonationDetailListener.onDonationDetailsFinished(status,homeFragmentModelList);
                    }
                    else
                    {
                        onDonationDetailListener.onDonationDetailsFinished(status,homeFragmentModelList);
                    }


                } catch (JSONException | NullPointerException e) {
                    e.printStackTrace();
                    onDonationDetailListener.onDonationFailuer(e);

                }
            }

            @Override
            public void failure(RetrofitError error) {
               onDonationDetailListener.onDonationFailuer(error);
            }
        });
    }

    @Override
    public void changeStatus(String user_id, final onChangeStatusListener onChangeStatusListener) {

        RetrofitClient retrofitClient = new RetrofitClient();
        RestInterface service = retrofitClient.getAPIClient(WebServiceURLs.DOMAIN_NAME);
        service.change_donation_status(user_id,new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonElement.toString());
                    String status=jsonObject.getString("status");
                    String message=jsonObject.getString("message");
                    onChangeStatusListener.onChangeStatusFinished(status,message);


                } catch (JSONException | NullPointerException e) {
                    e.printStackTrace();
                    onChangeStatusListener.onChangeStatusFailure(e);

                }
            }

            @Override
            public void failure(RetrofitError error) {
                onChangeStatusListener.onChangeStatusFailure(error);;
            }
        });


    }

    public String getDt_id() {
        return dt_id;
    }

    public void setDt_id(String dt_id) {
        this.dt_id = dt_id;
    }

    public String getLoct_id() {
        return loct_id;
    }

    public void setLoct_id(String loct_id) {
        this.loct_id = loct_id;
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getDonated_amount() {
        return donated_amount;
    }

    public void setDonated_amount(String donated_amount) {
        this.donated_amount = donated_amount;
    }

    public String getDonted_id() {
        return donted_id;
    }

    public void setDonted_id(String donted_id) {
        this.donted_id = donted_id;
    }

    public String getBeneficary_id() {
        return beneficary_id;
    }

    public void setBeneficary_id(String beneficary_id) {
        this.beneficary_id = beneficary_id;
    }

    public String getMode_payemnt() {
        return mode_payemnt;
    }

    public void setMode_payemnt(String mode_payemnt) {
        this.mode_payemnt = mode_payemnt;
    }

    public String getRecp_img() {
        return recp_img;
    }

    public void setRecp_img(String recp_img) {
        this.recp_img = recp_img;
    }

    public String getDonation_status() {
        return donation_status;
    }

    public void setDonation_status(String donation_status) {
        this.donation_status = donation_status;
    }

    public String getDonation_date() {
        return donation_date;
    }

    public void setDonation_date(String donation_date) {
        this.donation_date = donation_date;
    }

    public String getApproved_date() {
        return approved_date;
    }

    public void setApproved_date(String approved_date) {
        this.approved_date = approved_date;
    }

    public String getRef_number() {
        return ref_number;
    }

    public void setRef_number(String ref_number) {
        this.ref_number = ref_number;
    }

    public String getDonermobno() {
        return donermobno;
    }

    public void setDonermobno(String donermobno) {
        this.donermobno = donermobno;
    }

    public String getDonerfullname() {
        return donerfullname;
    }

    public void setDonerfullname(String donerfullname) {
        this.donerfullname = donerfullname;
    }

    public String getLoc_name() {
        return loc_name;
    }

    public void setLoc_name(String loc_name) {
        this.loc_name = loc_name;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }
}
