package com.example.myapplication.benificiary.request_donation.view;

public interface IDonationRequestView {
    public void setProgressVisibility(int visibility);
    public void getDonationRequestResult(String status);
    public void getDonationRequestFailureResult(Throwable t);
    public void setImageAdapter();
    public void notifyDataSetChanged();
    public void getValidationResult(int position);

}
