package io.carpoolapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.carpoolapp.R;

public class RideEdit extends FragmentActivity {

    @BindView(R.id.pickFrameLayout)
    FrameLayout pickFrameLayout;
    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_edit);
        ButterKnife.bind(this);
        Bundle bundle = new Bundle();
        bundle.putString("pickDest", "Pickup Location");
        PickLocationFragment pickLocationFragment = new PickLocationFragment();
        pickLocationFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.pickFrameLayout, pickLocationFragment, "pickup").commit();
    }

    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentById(R.id.pickFrameLayout).getTag().equalsIgnoreCase("pickup")) {
            finish();
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            super.onBackPressed();
        }
    }
}