package com.example.myapplication.donor.donation_history.view;

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

import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.myapplication.R;
import com.example.myapplication.RecyclerViewClickListener;
import com.example.myapplication.donor.donation_history.model.DonationHistoryModel;
import com.example.myapplication.donor.donation_history.presenter.DonationHistoryAdapter;
import com.example.myapplication.donor.donation_history.presenter.DonationHistoryPresenterImpl;
import com.example.myapplication.donor.donation_history.presenter.IDonationHistoryPresenter;

import java.util.List;

public class DonationHistoryFragment extends Fragment implements IDonationHistoryView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    @BindView(R.id.rv_donation_history)
    RecyclerView rv_donation_history;
    @BindView(R.id.p_br)
    ProgressBar p_br;
    @BindView(R.id.tv_total_amount)
    TextView tv_total_amount;

IDonationHistoryPresenter iDonationHistoryPresenter;
    View v;
    public DonationHistoryFragment() {
        // Required empty public constructor
    }


    public static DonationHistoryFragment newInstance(String param1, String param2) {
        DonationHistoryFragment fragment = new DonationHistoryFragment();
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

        v= inflater.inflate(R.layout.fragment_donation_history, container, false);
        getActivity().setTitle("Donation History");
        ButterKnife.bind(this,v);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv_donation_history.setLayoutManager(mLayoutManager);
        rv_donation_history.setItemAnimator(new DefaultItemAnimator());
        iDonationHistoryPresenter=new DonationHistoryPresenterImpl(this);
        iDonationHistoryPresenter.requestDonationHistory();
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
    public void getDonationHistory(String total_amount,List<DonationHistoryModel> donationHistoryModelList) {
        DonationHistoryAdapter adapter=new DonationHistoryAdapter(donationHistoryModelList, getActivity(), new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        });
        rv_donation_history.setAdapter(adapter);
        tv_total_amount.setText("Total Amount : "+total_amount);
    }

    @Override
    public void donationHistoryFailure(Throwable t) {

    }

    @Override
    public void setProgressVisibility(int visibility) {
        p_br.setVisibility(visibility);
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
