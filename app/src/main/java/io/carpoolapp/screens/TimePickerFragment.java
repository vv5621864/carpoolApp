package io.carpoolapp.screens;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TimePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.carpoolapp.R;
import io.carpoolapp.model.RideDetails;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends Fragment implements TimePicker.OnTimeChangedListener {


    @BindView(R.id.timePicker)
    TimePicker timePicker;
    @BindView(R.id.next)
    ImageView next;

    private int hourOfDay = 0, minute = 0;

    public TimePickerFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time_picker, container, false);
        ButterKnife.bind(this, view);
        ((RideEdit) getActivity()).setToolbarTitle("Select Time");
        hourOfDay = timePicker.getHour();
        minute = timePicker.getMinute();
        timePicker.setOnTimeChangedListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        this.hourOfDay = hourOfDay;
        this.minute = minute;

    }

    @OnClick(R.id.next)
    public void onViewClicked() {
        if (hourOfDay < 12) {
            RideDetails.getInstance().setTime(hourOfDay + ":" + minute + ":00 AM");
        } else {
            hourOfDay = hourOfDay - 12;
            RideDetails.getInstance().setTime(hourOfDay + ":" + minute + ":00 PM");
        }
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(getClass().getName()).replace(R.id.pickFrameLayout, new SeatsPickerFragment(), "SeatsPicker").commit();
    }
}
