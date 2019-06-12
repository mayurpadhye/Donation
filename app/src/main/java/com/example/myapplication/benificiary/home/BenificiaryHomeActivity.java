package com.example.myapplication.benificiary.home;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.myapplication.R;
import com.example.myapplication.benificiary.home.view.HomeFragment;
import com.example.myapplication.benificiary.profile.view.MyAccountFragment;
import com.example.myapplication.benificiary.request_donation.view.DonationRequstFragment;

public class BenificiaryHomeActivity extends AppCompatActivity implements  BottomNavigationView.OnNavigationItemSelectedListener, MyAccountFragment.OnFragmentInteractionListener, HomeFragment.OnFragmentInteractionListener, DonationRequstFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benificiary_home);
        BottomNavigationView mView = findViewById(R.id.customBottomBar);
        mView.inflateMenu(R.menu.bottom_user_home);
        //getting bottom navigation view and attaching the listener
        mView.setOnNavigationItemSelectedListener(BenificiaryHomeActivity.this);
        getSupportFragmentManager().beginTransaction().replace(R.id.ll_main,new HomeFragment()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorites:
                getSupportFragmentManager().beginTransaction().replace(R.id.ll_main,new HomeFragment()).commit();
                break;
            case R.id.action_donation:
                getSupportFragmentManager().beginTransaction().replace(R.id.ll_main,new DonationRequstFragment()).commit();
                break;
            case R.id.action_music1:
getSupportFragmentManager().beginTransaction().replace(R.id.ll_main,new MyAccountFragment()).commit();
                break;
        }
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
