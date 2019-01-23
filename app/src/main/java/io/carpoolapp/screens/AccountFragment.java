package io.carpoolapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.carpoolapp.R;
import io.carpoolapp.storage.MyPrefrence;

public class AccountFragment extends Fragment {

    @BindView(R.id.logout)
    Button logout;
    Unbinder unbinder;

    public AccountFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.logout)
    public void onViewClicked() {
        MyPrefrence myPrefrence = new MyPrefrence(getContext());
        myPrefrence.putValue(getString(R.string.isLogIn), getString(R.string.no));
        Intent intent = new Intent(getContext(), PreLoginActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}
