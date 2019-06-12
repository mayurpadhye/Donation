package com.example.myapplication.donor.home.view;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import butterknife.ButterKnife;
import com.example.myapplication.R;
import com.example.myapplication.donor.donation.view.DonationFragment;
import com.example.myapplication.donor.donation_history.view.DonationHistoryFragment;
import com.example.myapplication.donor.DonorProfileFragment;


public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, DonationHistoryFragment.OnFragmentInteractionListener,DonorHomeFragment.OnFragmentInteractionListener, DonorProfileFragment.OnFragmentInteractionListener,DonationFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        BottomNavigationView mView = findViewById(R.id.customBottomBar);
        mView.inflateMenu(R.menu.bottom_menu);
        //getting bottom navigation view and attaching the listene
        mView.setOnNavigationItemSelectedListener(HomeActivity.this);
        getSupportFragmentManager().beginTransaction().replace(R.id.ll_home_main,new DonorHomeFragment()).commit();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

      @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorites:
                getSupportFragmentManager().beginTransaction().replace(R.id.ll_home_main,new DonorHomeFragment()).commit();
                break;
            case R.id.action_donation:
                getSupportFragmentManager().beginTransaction().replace(R.id.ll_home_main,new DonationFragment()).commit();
                break;
            case R.id.action_music:
                getSupportFragmentManager().beginTransaction().replace(R.id.ll_home_main,new DonationHistoryFragment()).commit();
                break;
            case R.id.action_my_account:
                getSupportFragmentManager().beginTransaction().replace(R.id.ll_home_main,new DonorProfileFragment()).commit();
                break;
        }
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
