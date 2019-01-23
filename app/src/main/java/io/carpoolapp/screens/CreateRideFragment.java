package io.carpoolapp.screens;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.carpoolapp.R;
import io.carpoolapp.constants.Constants;
import io.carpoolapp.model.RideDetails;
import io.carpoolapp.model.UserDetails;
import io.carpoolapp.storage.RideDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateRideFragment extends Fragment {

    private RideDetails rideDetails = null;
    private RideDatabase rideDatabase = null;

    @BindView(R.id.pickup)
    TextView pickup;
    @BindView(R.id.drop)
    TextView drop;
    @BindView(R.id.dateTime)
    TextView dateTime;
    @BindView(R.id.seats)
    TextView seats;
    @BindView(R.id.createRide)
    Button createRide;

    public CreateRideFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_ride, container, false);
        ButterKnife.bind(this, view);
        Constants.ACTIVITY_NAME = getActivity().getIntent().getStringExtra("NAME");
        createRide.setText(Constants.ACTIVITY_NAME);
        rideDatabase = new RideDatabase(getContext());
        rideDetails = RideDetails.getInstance();
        pickup.setText(rideDetails.getPickAddress());
        drop.setText(rideDetails.getDropAddress());
        dateTime.setText(rideDetails.getDate() + " " + rideDetails.getTime());
        seats.setText("" + rideDetails.getSeats());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.createRide)
    public void onViewClicked() {
        if (Constants.ACTIVITY_NAME.equalsIgnoreCase(getString(R.string.offerRide))) {
            rideDetails.setRideId(String.valueOf(System.currentTimeMillis()));
            boolean isCreatedRide = rideDatabase.createRide();
            if (isCreatedRide) {
                Toast.makeText(getContext(), "Ride created", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "Ride not created", Toast.LENGTH_SHORT).show();
            }
        } else {
            rideDatabase.findRide();
        }

    }
}
