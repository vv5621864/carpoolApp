package io.carpoolapp.screens;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import io.carpoolapp.R;
import io.carpoolapp.storage.RideDatabase;
import io.carpoolapp.storage.UserDatabase;
import io.carpoolapp.model.UserDetails;
import io.carpoolapp.storage.MyPrefrence;


public class SplashActivity extends AppCompatActivity {

    private Intent intent = null;
    private MyPrefrence myPrefrence = null;
    private UserDatabase userDatabase = null;
    private RideDatabase rideDatabase = null;
    private UserDetails userDetails = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
//        rideDatabase = new RideDatabase(this);
        userDatabase = new UserDatabase(this);

        myPrefrence = new MyPrefrence(this);
        if (!myPrefrence.isKeyExist(getString(R.string.isLogIn))) {
            myPrefrence.putValue(getString(R.string.isLogIn), getString(R.string.no));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String mobile = myPrefrence.getValue(getString(R.string.isLogIn));
                if (getString(R.string.no).compareTo(mobile) != 0) {
                    Cursor res = userDatabase.getUserDetails(mobile);
                    userDetails = UserDetails.getInstance();
                    while (res.moveToNext()) {
                        userDetails.setMobile(res.getString(0));
                        userDetails.setGender(res.getString(1));
                        userDetails.setName(res.getString(2));
                        userDetails.setEmail(res.getString(3));
                        userDetails.setPassword(res.getString(4));
                        userDetails.setYearOfBirth(res.getString(5));
                        userDetails.setProfileCreationTime(Long.parseLong(res.getString(6)));
                        userDetails.setProfileModificationTime(Long.parseLong(res.getString(7)));
                        userDetails.setProfileUri(res.getString(8));
                    }
                    res.close();
                    intent = new Intent(getApplicationContext(), HomeActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), PreLoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}
