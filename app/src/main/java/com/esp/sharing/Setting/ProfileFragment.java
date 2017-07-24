package com.esp.sharing.Setting;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.esp.sharing.Helper.DialogHelper;
import com.esp.sharing.R;

public class ProfileFragment extends Fragment {

    private View rootView;
    private View infoView;

    private TextView nameTextView;
    private TextView phoneTextView;
    private TextView locationTextView;
    private TextView descriptionTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initView(inflater, container);

        listener();

        return rootView;
    }

    private void initView(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        infoView = rootView.findViewById(R.id.profile_info_panel);

        nameTextView = (TextView) rootView.findViewById(R.id.user_name_tv);
        phoneTextView = (TextView) rootView.findViewById(R.id.user_phone_tv);
        locationTextView = (TextView) rootView.findViewById(R.id.user_location_tv);
        descriptionTextView = (TextView) rootView.findViewById(R.id.user_description_tv);
    }

    public void listener() {
        infoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHelper.createEditProfileDialog(getContext(), nameTextView, phoneTextView, locationTextView, descriptionTextView);
            }
        });
    }
}
