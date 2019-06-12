package com.example.myapplication.benificiary.request_donation.view;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.*;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.myapplication.R;
import com.example.myapplication.benificiary.request_donation.EventImagesAdapter;
import com.example.myapplication.benificiary.request_donation.ImageVideoData;
import com.example.myapplication.benificiary.request_donation.presenter.DonationRequestPresenter;
import com.example.myapplication.benificiary.request_donation.presenter.IDonationRequestPresenter;
import com.example.myapplication.helperClass.CustomFileUtils;
import com.example.myapplication.helperClass.CustomPermissions;
import com.example.myapplication.helperClass.CustomUtils;
import com.iceteck.silicompressorr.SiliCompressor;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.benificiary.request_donation.presenter.DonationRequestPresenter.imageFiles;
import static com.example.myapplication.benificiary.request_donation.presenter.DonationRequestPresenter.image_thumbnails;
import static com.example.myapplication.helperClass.CustomUtils.IMAGE_LIMIT;

public class DonationRequstFragment extends Fragment implements View.OnClickListener, IDonationRequestView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    @BindView(R.id.et_amount)
    EditText et_amount;
    @BindView(R.id.et_desc)
    EditText et_desc;
    @BindView(R.id.btn_upload)
    Button btn_upload;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.rv_images)
    RecyclerView rv_images;
    @BindView(R.id.p_bar)
    ProgressBar p_bar;

    LinearLayoutManager llm_images;

    IDonationRequestPresenter iDonationRequestPresenter;
    EventImagesAdapter adapter;
    private OnFragmentInteractionListener mListener;

    public DonationRequstFragment() {
        // Required empty public constructor
    }


    public static DonationRequstFragment newInstance(String param1, String param2) {
        DonationRequstFragment fragment = new DonationRequstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_donation_requst, container, false);
        ButterKnife.bind(this, v);
        getActivity().setTitle("Request Donation");

        llm_images = new LinearLayoutManager(getActivity());
        llm_images.setOrientation(LinearLayoutManager.HORIZONTAL);
        //adapter = new EventImagesAdapter(getActivity(), getActivity(), image_thumbnails, "create");
        iDonationRequestPresenter = new DonationRequestPresenter(getActivity(), this, getActivity());
        btn_submit.setOnClickListener(this);
        btn_upload.setOnClickListener(this);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                iDonationRequestPresenter.validateData(et_amount.getText().toString(), et_desc.getText().toString(), imageFiles);
                break;
            case R.id.btn_upload:
                CustomUtils.hideKeyboard(v, getActivity().getApplicationContext());
                iDonationRequestPresenter.selectImage();
                break;
        }

    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode == RESULT_OK) {
            iDonationRequestPresenter.getOnActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void setProgressVisibility(int visibility) {
        p_bar.setVisibility(visibility);
    }

    @Override
    public void getDonationRequestResult(String status) {
        Toast.makeText(getActivity(), "" + status, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getDonationRequestFailureResult(Throwable t) {
        Toast.makeText(getActivity(), "" + t, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setImageAdapter() {
        adapter = new EventImagesAdapter(getActivity(), getActivity(), image_thumbnails, "create");
        rv_images.setLayoutManager(llm_images);
        rv_images.setAdapter(adapter);
    }

    @Override
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getValidationResult(int position) {

        switch (position)
        {
            case 1:
                et_amount.setError("Please Enter Donation Amount");
                break;
            case 2:
                et_desc.setError("Please Enter Description about donation");
                 break;
            case 3:
                Toast.makeText(getActivity(), "Please Select Bill/Recipt Image", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                iDonationRequestPresenter.requestDonation(et_amount.getText().toString(), et_desc.getText().toString(), imageFiles);
                break;
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
