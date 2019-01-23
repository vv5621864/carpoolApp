package io.carpoolapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.carpoolapp.R;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle(getString(R.string.yourRides));
        loadFragment(new YourRidesFragment(), getString(R.string.rides), 0);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rides: {
                loadFragment(new YourRidesFragment(), getString(R.string.rides), 1);
                break;
            }
            case R.id.findRides: {
                Intent intent1 = new Intent(this, RideEdit.class);
                intent1.putExtra("NAME", getString(R.string.findRide));
                startActivity(intent1);
                break;
            }
            case R.id.createRide: {
                Intent intent1 = new Intent(this, RideEdit.class);
                intent1.putExtra("NAME", getString(R.string.offerRide));
                startActivity(intent1);
                break;
            }
            case R.id.inbox: {
                break;
            }
            case R.id.profile: {
                loadFragment(new ProfileFragment(), getString(R.string.profile), 1);
                break;
            }
        }
        return true;
    }

    public void loadFragment(Fragment fragment, String fragmentName, int i) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (i == 0) {
            fragmentTransaction.add(R.id.frameLayout, fragment, getString(R.string.yourRides));
        } else {
            fragmentTransaction.replace(R.id.frameLayout, fragment, fragmentName);
        }
        fragmentTransaction.commit();
    }

    public void setCheckItem(int index) {
        navigation.getMenu().getItem(index).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        int id = navigation.getSelectedItemId();
        if (id != R.id.rides) {
            loadFragment(new YourRidesFragment(), getString(R.string.rides), 1);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
