package com.esp.sharing.Setting;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.esp.sharing.Helper.DialogHelper;
import com.esp.sharing.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PasswordFragment extends Fragment {


    private View rootView;
    private EditText oldPassword;
    private EditText newPassword;
    private EditText confirmPassword;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_password, container, false);
        oldPassword = (EditText) rootView.findViewById(R.id.old_pwd);
        newPassword = (EditText) rootView.findViewById(R.id.new_pwd);
        confirmPassword = (EditText) rootView.findViewById(R.id.confirm_pwd);
        button = (Button) rootView.findViewById(R.id.confirm_btn);
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = newPassword.getText().toString();
                if (!str.equals(s.toString())) {
                    confirmPassword.setBackgroundResource(R.drawable.bg_edt_wrong);
                } else {
                    confirmPassword.setBackgroundResource(R.drawable.bg_edt);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHelper.createMessageDialog(getContext(), "Đổi password", "Tính năng chưa xuất hiện");
            }
        });
        return rootView;
    }

}
