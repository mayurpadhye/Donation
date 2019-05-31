package com.example.myapplication.donor.home.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.myapplication.R;
import com.example.myapplication.donor.home.model.HomeModel;
import com.example.myapplication.donor.home.model.IHomeModel;
import com.example.myapplication.donor.home.presenter.CategoryListAdapter;
import com.example.myapplication.donor.home.presenter.HomePresenterImpl;
import com.example.myapplication.donor.home.presenter.IHomePresenter;

import java.util.List;


public class DonorHomeFragment extends Fragment implements IHomeView {
    @BindView(R.id.p_bar)
    ProgressBar p_bar;
    @BindView(R.id.rv_category_list)
    RecyclerView rv_category_list;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    View v;
    IHomePresenter iHomePresenter;
    private OnFragmentInteractionListener mListener;

    public DonorHomeFragment() {
        // Required empty public constructor
    }


    public static DonorHomeFragment newInstance(String param1, String param2) {
        DonorHomeFragment fragment = new DonorHomeFragment();
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

        v = inflater.inflate(R.layout.fragment_donor_home, container, false);
        ButterKnife.bind(this, v);
        getActivity().setTitle("Home");
        rv_category_list.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        iHomePresenter = new HomePresenterImpl(getActivity(), this);
        iHomePresenter.requestCategoryData();
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
    public void getAllCategories(List<HomeModel> homeModelList) {
        CategoryListAdapter adapter=new CategoryListAdapter(homeModelList,getActivity());
        rv_category_list.setAdapter(adapter);
    }

    @Override
    public void setVisibility(int Visibility) {
        p_bar.setVisibility(Visibility);
    }

    @Override
    public void onCategoryRequestFailure(Throwable t) {

    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
