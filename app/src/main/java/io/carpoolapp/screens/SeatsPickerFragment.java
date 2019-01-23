package io.carpoolapp.screens;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.carpoolapp.R;
import io.carpoolapp.constants.Constants;
import io.carpoolapp.model.RideDetails;

public class SeatsPickerFragment extends Fragment {


    @BindView(R.id.seatsPicker)
    NumberPicker seatsPicker;
    @BindView(R.id.next)
    ImageView next;

    public SeatsPickerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seats_picker, container, false);
        ButterKnife.bind(this, view);
        ((RideEdit) getActivity()).setToolbarTitle(getString(R.string.selectSeats));
        seatsPicker.setMinValue(1);
        seatsPicker.setMaxValue(100);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.next)
    public void onViewClicked() {
        RideDetails.getInstance().setSeats(seatsPicker.getValue());
        RideDetails.getInstance().setStatus(Constants.INITIATED);
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(getClass().getName()).replace(R.id.pickFrameLayout, new CreateRideFragment(), "find or create ride").commit();
    }
}
