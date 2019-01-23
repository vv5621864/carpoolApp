package io.carpoolapp.screens;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.carpoolapp.R;
import io.carpoolapp.model.RideDetails;

public class DatePickerFragment extends Fragment {

    @BindView(R.id.datePcker)
    DatePicker datePcker;
    @BindView(R.id.next)
    ImageView next;
    private int mYear, mMonth, mDay, mHour, mMinute;

    public DatePickerFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date_picker, container, false);
        ((RideEdit) getActivity()).setToolbarTitle(getString(R.string.selectDate));
        ButterKnife.bind(this, view);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePcker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.next)
    public void onViewClicked() {
        mYear = datePcker.getYear();
        mMonth = datePcker.getMonth();
        mDay = datePcker.getDayOfMonth();
        RideDetails.getInstance().setDate(mYear + "-" + (mMonth + 1) + "-" + mDay);
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(getClass().getName()).replace(R.id.pickFrameLayout, new TimePickerFragment(), "TimePicker").commit();
    }
}
