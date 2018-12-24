package io.carpoolapp.screens;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.carpoolapp.R;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle(getString(R.string.yourRides));
        loadFragment(new YourRidesFragment());
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rides: {
                getSupportActionBar().show();
                loadFragment(new YourRidesFragment());
                break;
            }
            case R.id.findRides: {
                getSupportActionBar().hide();
                break;
            }
            case R.id.createRide: {
                getSupportActionBar().hide();
                break;
            }
            case R.id.inbox: {
                getSupportActionBar().hide();
                break;
            }
            case R.id.profile: {
                getSupportActionBar().hide();
                break;
            }
        }
        return true;
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}
