package com.example.myapplication.donor.donation.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.myapplication.R;
import com.example.myapplication.benificiary.registration.model.CategoryListModel;
import com.example.myapplication.benificiary.registration.model.LocationModel;
import com.example.myapplication.benificiary.registration.presenter.CategorySpinnerAdapter;
import com.example.myapplication.benificiary.registration.presenter.LocationSpinnerAdapter;
import com.example.myapplication.benificiary.registration.view.RegistrationActivity;
import com.example.myapplication.benificiary.request_donation.EventImagesAdapter;
import com.example.myapplication.donor.donation.model.UserListModel;
import com.example.myapplication.donor.donation.presenter.DonationPresenterImpl;
import com.example.myapplication.donor.donation.presenter.IDonationPresenter;
import com.example.myapplication.donor.donation.presenter.UserListAdapter;
import com.example.myapplication.helperClass.PrefManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.donor.donation.presenter.DonationPresenterImpl.imageFiles;
import static com.example.myapplication.donor.donation.presenter.DonationPresenterImpl.image_thumbnails;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DonationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DonationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DonationFragment extends Fragment implements IDonationView,View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    View v;
    @BindView(R.id.p_bar)
    ProgressBar p_bar;
    @BindView(R.id.sp_cat)
    Spinner sp_cat;
    @BindView(R.id.sp_location)
    Spinner sp_location;
    @BindView(R.id.sp_users)
    Spinner sp_users;
    @BindView(R.id.tv_required_amount)
    TextView tv_required_amount;
    @BindView(R.id.et_donation_amt)
    EditText et_donation_amt;
    @BindView(R.id.et_mode_of_payment)
    EditText et_mode_of_payment;
    @BindView(R.id.iv_doc_image)
            ImageView iv_doc_image;
    @BindView(R.id.btn_upload)
            Button btn_upload;
    @BindView(R.id.btn_submit)
    Button btn_submit;

@BindView(R.id.rv_images)
    RecyclerView rv_images;
    LinearLayoutManager llm_images;
@BindView(R.id.et_ref_no)
        EditText et_ref_no;

    IDonationPresenter iDonationPresenter;
    String cat_id = "";
    String loc_id = "";
    String user_id = "";
    String getTotal_amount = "";
    EventImagesAdapter adapter;
    public DonationFragment() {
        // Required empty public constructor
    }

    public static DonationFragment newInstance(String param1, String param2) {
        DonationFragment fragment = new DonationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (image_thumbnails.size()>0)
            {
                rv_images.setVisibility(View.VISIBLE);
                iv_doc_image.setVisibility(View.GONE);
            }
            else
            {rv_images.setVisibility(View.GONE);
                iv_doc_image.setVisibility(View.VISIBLE);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

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
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_donation, container, false);
        ButterKnife.bind(this, v);
        iDonationPresenter = new DonationPresenterImpl(getActivity(), this,getActivity());
        iDonationPresenter.getCategory();
        iDonationPresenter.getLocation();
        llm_images = new LinearLayoutManager(getActivity());
        llm_images.setOrientation(LinearLayoutManager.HORIZONTAL);
        btn_upload.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        return v;
    }

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
    public void setProgressVisibilty(int visibility) {
        p_bar.setVisibility(visibility);
    }

    @Override
    public void getCategoryList(final List<CategoryListModel> categoryListModelsList) {

        CategorySpinnerAdapter adapter = new CategorySpinnerAdapter(getActivity(), R.layout.row_location, R.id.tv_loc, categoryListModelsList);
        sp_cat.setPrompt("Select Category");
        sp_cat.setAdapter(adapter);

        sp_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    cat_id = categoryListModelsList.get(position).getCat_id();


                } else {
                    cat_id = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void getLocationList(final List<LocationModel> locationModelList) {

        LocationSpinnerAdapter adapter = new LocationSpinnerAdapter(getActivity(), R.layout.row_location, R.id.tv_loc, locationModelList);
        sp_location.setPrompt("Select Location");
        sp_location.setAdapter(adapter);
        sp_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    loc_id = locationModelList.get(position).getLoc_id();
                    if (!cat_id.equals("") && !loc_id.equals(""))
                    {
                        iDonationPresenter.getUserList(loc_id,cat_id);
                    }
                    else
                        Toast.makeText(getActivity(), "Please Select Category and Location", Toast.LENGTH_SHORT).show();

                } else {
                    loc_id = "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getValidationResult(int position) {
        switch (position)
        {
            case 1 :
                Toast.makeText(getActivity(), "Please Select Category", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(getActivity(), "Please Select Location", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(getActivity(), "Please Select Beneficiary", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                et_donation_amt.setError("Enter Donation Amount");
                break;
            case 5:
                et_mode_of_payment.setError("Enter Mode of payment");
                break;
            case 6:
                Toast.makeText(getActivity(), "Please Select Image to upload", Toast.LENGTH_SHORT).show();
                break;
            case 7:
                et_ref_no.setError("Enter Reference Number");
                break;
            case 8:
                iDonationPresenter.submitDonationForm(cat_id,loc_id,user_id,et_donation_amt.getText().toString().trim(),PrefManager.getInstance(getActivity()).getUserId(),et_mode_of_payment.getText().toString().trim(),imageFiles,et_ref_no.getText().toString());
                break;

        }

    }

    @Override
    public void getDonationFormResult(String status, String message) {
        Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getDoantionFormFailure(Throwable t) {
        Toast.makeText(getActivity(), ""+t, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getUserList(final List<UserListModel> userListModelList) {
        UserListAdapter adapter = new UserListAdapter(getActivity(), R.layout.row_user_list, R.id.tv_user, userListModelList);
        sp_users.setPrompt("Select Location");
        sp_users.setAdapter(adapter);
        sp_users.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    user_id = userListModelList.get(position).getUser_id();
                    getTotal_amount=userListModelList.get(position).getTotal_amount();
                    tv_required_amount.setText("Total Amount Required : \u20B9 "+getTotal_amount);

                } else {
                    user_id = "";
                    tv_required_amount.append("\u20B9 0");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    } @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode == RESULT_OK) {
            iDonationPresenter.getOnActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void setImageAdapter() {
        adapter = new EventImagesAdapter(getActivity(), getActivity(), image_thumbnails, "create");
        rv_images.setLayoutManager(llm_images);
        rv_images.setAdapter(adapter);
        if (image_thumbnails.size()>0)
        {
            rv_images.setVisibility(View.VISIBLE);
            iv_doc_image.setVisibility(View.GONE);
        }
        else
        {
            rv_images.setVisibility(View.GONE);
            iv_doc_image.setVisibility(View.VISIBLE);
        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_upload:
                iDonationPresenter.selectImage();
                break;
            case R.id.btn_submit:

                if (PrefManager.getInstance(getActivity()).IS_LOGIN())
                iDonationPresenter.validateData(cat_id,loc_id,user_id,et_donation_amt.getText().toString().trim(), PrefManager.getInstance(getActivity()).getUserId(),et_mode_of_payment.getText().toString().trim(),imageFiles,et_ref_no.getText().toString().trim());
                else
                    Toast.makeText(getActivity(), "Please Login to continue", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
