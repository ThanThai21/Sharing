package com.esp.sharing.Helper;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.esp.sharing.R;

public class DialogHelper {

    public static void createMessageDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    public static void createEditProfileDialog(Context context, final TextView name, final TextView phone, final TextView location, final TextView description) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_profile, null);
        final EditText nameEdt = (EditText) view.findViewById(R.id.dialog_name_edt);
        final EditText phoneEdt = (EditText) view.findViewById(R.id.dialog_phone_edt);
        final EditText locationEdt = (EditText) view.findViewById(R.id.dialog_location_edt);
        final EditText descriptionEdt = (EditText) view.findViewById(R.id.dialog_description_edt);
        Button cancelBtn = (Button) view.findViewById(R.id.cancel_btn);
        Button confirmBtn = (Button) view.findViewById(R.id.confirm_btn);
        nameEdt.setText(name.getText().toString());
        phoneEdt.setText(phone.getText().toString());
        locationEdt.setText(location.getText().toString());
        descriptionEdt.setText(description.getText().toString());
        nameEdt.setSelection(nameEdt.getText().toString().length());
        phoneEdt.setSelection(phoneEdt.getText().toString().length());
        locationEdt.setSelection(locationEdt.getText().toString().length());
        descriptionEdt.setSelection(descriptionEdt.getText().toString().length());
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText(nameEdt.getText().toString());
                phone.setText(phoneEdt.getText().toString());
                location.setText(locationEdt.getText().toString());
                description.setText(descriptionEdt.getText().toString());
                dialog.cancel();
            }
        });
        Display display =((WindowManager)context.getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        int height=display.getHeight();
        dialog.getWindow().setLayout((19*width)/20,(19*height)/20);
    }

}
