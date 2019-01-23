package io.carpoolapp.screens;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.carpoolapp.R;

public class ProfileDetailsFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {


    @BindView(R.id.bioButton)
    TextView bioButton;
    @BindView(R.id.addPrefrenceButton)
    TextView addPrefrenceButton;
    @BindView(R.id.editMenuButton)
    ImageView editMenuButton;
    Unbinder unbinder;

    public ProfileDetailsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.bioButton, R.id.addPrefrenceButton, R.id.editMenuButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bioButton:
                break;
            case R.id.addPrefrenceButton:
                break;
            case R.id.editMenuButton:
                PopupMenu popup = new PopupMenu(getContext(), view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.edit_verification, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(this);
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.editMail: {
                Toast.makeText(getContext(), "Call Edit Email API", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.editPhone: {
                Toast.makeText(getContext(), "Call Edit Phone API", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        return true;
    }
}
