package com.example.myapplication.benificiary.home.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.myapplication.R;
import com.example.myapplication.RecyclerViewClickListener;
import com.example.myapplication.benificiary.home.model.HomeFragmentModel;
import com.example.myapplication.benificiary.home.presenter.DonationDetailsAdapter;
import com.example.myapplication.benificiary.home.presenter.DonationDetailsPresenterImpl;
import com.example.myapplication.benificiary.home.presenter.IDonationDetails;
import com.example.myapplication.donor.benificiary_list.presenter.BenificiaryListAdapter;
import com.example.myapplication.donor.benificiary_list.view.BenificiaryListActivity;
import com.example.myapplication.helperClass.PrefManager;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements IHomeFragmentView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View v;
    private String mParam1;
    private String mParam2;
    int positi = 0;
    List<HomeFragmentModel> homeFragmentModelList1 = new ArrayList<>();
    IDonationDetails iDonationDetails;
    @BindView(R.id.rv_donation_details)
    RecyclerView rv_donation_details;
    @BindView(R.id.ll_no_record)
    LinearLayout ll_no_record;
    @BindView(R.id.p_bar)
    ProgressBar p_bar;

    RecyclerViewClickListener listener;
    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);
        getActivity().setTitle("Home");
        iDonationDetails = new DonationDetailsPresenterImpl(getActivity(), this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv_donation_details.setLayoutManager(mLayoutManager);
        rv_donation_details.setItemAnimator(new DefaultItemAnimator());
        iDonationDetails.getDonationDetails();
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
    public void setProgressIndiactior(int visibility) {
        p_bar.setVisibility(visibility);
    }

    @Override
    public void getDonationList(final List<HomeFragmentModel> homeFragmentModelList) {

        if (homeFragmentModelList.size() > 0) {
            homeFragmentModelList1.addAll(homeFragmentModelList);
            DonationDetailsAdapter adapter = new DonationDetailsAdapter(homeFragmentModelList1, getActivity(), listener = new RecyclerViewClickListener() {
                @Override
                public void onClick(View view, int position) {
                    switch (view.getId()) {
                        case R.id.btn_status:
                            if (homeFragmentModelList1.get(position).getDonation_status().equals("0")) {
                                positi = position;
                                iDonationDetails.changeDonationStatus(homeFragmentModelList1.get(position).getDt_id(), "");
                            } else {

                            }


                            break;

                    }

                }
            });
            rv_donation_details.setAdapter(adapter);
            ll_no_record.setVisibility(View.GONE);
        } else {
            ll_no_record.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void changeStatusResult(String status, String message) {
        if (status.equals("1")) {
            homeFragmentModelList1.get(positi).setDonation_status("1");
            rv_donation_details.getAdapter().notifyDataSetChanged();
            Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void changeStatusListenerFailure(Throwable t) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
