package io.carpoolapp.screens;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.carpoolapp.R;
import io.carpoolapp.model.RideDetails;
import io.carpoolapp.model.UserDetails;

public class CurrentRidesFragment extends Fragment {

    @BindView(R.id.going)
    TextView going;
    @BindView(R.id.travel)
    TextView travel;
    @BindView(R.id.offerRide)
    Button offerRide;
    @BindView(R.id.findRide)
    Button findRide;
    Unbinder unbinder;
    private View view = null;

    public CurrentRidesFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String activeRide = UserDetails.getInstance().getActiveRide();
        if (activeRide.equalsIgnoreCase("")) {
            view = inflater.inflate(R.layout.fragment_current_rides, container, false);
        } else {

        }
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.offerRide, R.id.findRide})
    public void onViewClicked(View view) {
        Intent intent = new Intent(getContext(), RideEdit.class);
        switch (view.getId()) {
            case R.id.offerRide:
                intent.putExtra("NAME", getString(R.string.offerRide));
                startActivity(intent);
                break;
            case R.id.findRide:
                intent.putExtra("NAME", getString(R.string.findRide));
                startActivity(intent);
                break;
        }
    }
}
