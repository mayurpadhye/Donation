package com.example.myapplication.benificiary.profile.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.myapplication.ChooseRoleActivity;
import com.example.myapplication.R;
import com.example.myapplication.benificiary.profile.presenter.IProfilePresenter;
import com.example.myapplication.benificiary.profile.presenter.ProfilePresenter;
import com.example.myapplication.helperClass.PrefManager;

public class MyAccountFragment extends Fragment implements IAccountView{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    View v;
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.tv_full_name)
            TextView tv_ful_name;
    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_mobile_no)
    TextView tv_mobile_no;
    @BindView(R.id.tv_age)
    TextView tv_age;
    @BindView(R.id.tv_cat)
    TextView tv_cat;

    @BindView(R.id.tv_logout)
            TextView tv_logout;

    IProfilePresenter iProfilePresenter;
    public MyAccountFragment() {
        // Required empty public constructor
    }


    public static MyAccountFragment newInstance(String param1, String param2) {
        MyAccountFragment fragment = new MyAccountFragment();
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

        v= inflater.inflate(R.layout.fragment_my_account, container, false);
        ButterKnife.bind(this,v);
        getActivity().setTitle("My Profile");
        iProfilePresenter=new ProfilePresenter(getActivity(),this);
        iProfilePresenter.requestProfileData();
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChooseRoleActivity.class));
                getActivity().finish();
                PrefManager.getInstance(getActivity()).Logout();
            }
        });
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
    public void setVisibility(int visibility) {

    }

    @Override
    public void getProfileData(String full_name, String age, String mobile_no, String profile_pic, String total_amount, String loc_name, String cat_name) {
        tv_cat.append(cat_name);
        tv_age.append(age+" Years");
        tv_email.setText("");
        tv_location.append(loc_name);
        tv_ful_name.setText(full_name);
        tv_mobile_no.setText(mobile_no);
    }

    @Override
    public void getProfileDataFailure(Throwable t) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
