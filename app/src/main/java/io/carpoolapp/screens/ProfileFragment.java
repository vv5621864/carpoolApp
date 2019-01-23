package io.carpoolapp.screens;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.myhexaville.smartimagepicker.ImagePicker;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.carpoolapp.R;
import io.carpoolapp.adapter.ProfilePageAdapter;
import io.carpoolapp.storage.UserDatabase;
import io.carpoolapp.model.UserDetails;
import io.carpoolapp.storage.MyPrefrence;

public class ProfileFragment extends Fragment implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {


    @BindView(R.id.circleImageView)
    CircleImageView circleImageView;
    @BindView(R.id.editMenuButton)
    ImageView editMenuButton;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.tabProfile)
    TabLayout tabProfile;
    @BindView(R.id.profileViewPager)
    ViewPager profileViewPager;

    private Uri imageUri = null;
    private ImagePicker imagePicker = null;
    private UserDatabase userDatabase = null;

    public ProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ((HomeActivity) getActivity()).setCheckItem(4);
        ((HomeActivity) getActivity()).getSupportActionBar().show();
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.profile));
        ButterKnife.bind(this, view);
        MyPrefrence myPrefrence = new MyPrefrence(getContext());
        String mobile = myPrefrence.getValue("isLogIn");
        userName.setText(UserDetails.getInstance().getName());
        userDatabase = new UserDatabase(getContext());
        circleImageView.setOnClickListener(this::onClick);
        circleImageView.setImageURI(userDatabase.getProfileUri(mobile));
        editMenuButton.setOnClickListener(this);
        tabProfile.addTab(tabProfile.newTab().setText(getString(R.string.details)), 0);
        tabProfile.addTab(tabProfile.newTab().setText(getString(R.string.account)), 1);
        tabProfile.setTabGravity(TabLayout.GRAVITY_FILL);
        ProfilePageAdapter profilePageAdapter = new ProfilePageAdapter(getActivity().getSupportFragmentManager(), tabProfile.getTabCount());
        profileViewPager.setAdapter(profilePageAdapter);
        profileViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabProfile));
        tabProfile.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                profileViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.editMenuButton: {
                PopupMenu popup = new PopupMenu(getContext(), v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.edit_profile_menu, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(this);
                break;
            }
            case R.id.circleImageView: {
                selectProfilePhoto();
                break;
            }
        }

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.editPhoto: {
                selectProfilePhoto();
                break;
            }
            case R.id.editDetails: {
                Toast.makeText(getContext(), "Edit details", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode, requestCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.handlePermission(requestCode, grantResults);
    }

    public void selectProfilePhoto() {
        imagePicker = new ImagePicker(getActivity(),
                this, imageUri -> {
            Bitmap bitmap = null;
            MyPrefrence myPrefrence = new MyPrefrence(getContext());
            userDatabase.addProfilePhoto(myPrefrence.getValue("isLogIn"), imageUri.toString());
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            UserDetails.getInstance().setProfileUri(imageUri.toString());
            circleImageView.setImageBitmap(bitmap);
        }).setWithImageCrop(1, 1);
        imagePicker.choosePicture(true);
    }
}
