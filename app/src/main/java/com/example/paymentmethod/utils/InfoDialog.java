package com.example.paymentmethod.utils;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.paymentmethod.R;

public class InfoDialog {

    private Activity mActivity;

    public InfoDialog(Activity activity) {
        mActivity = activity;
    }

    public void showDialog(boolean isNoInternet, String errorStr) {
        Dialog dialog = new Dialog(mActivity);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.base_dialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        Button okayBtn = dialog.findViewById(R.id.id_dialog_btn_okay);
        Button cancelBtn = dialog.findViewById(R.id.id_dialog_btn_cancel);
        TextView dialogText = dialog.findViewById(R.id.id_dialog_text);
        ImageView dialogIcon = dialog.findViewById(R.id.id_dialog_icon);

        if (isNoInternet) {
            dialogText.setText("No Internet!\nPlease check your internet connection");
            dialogIcon.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_no_signal));
        } else {
            dialogText.setText("Something went wrong!\nError: " + errorStr);
            dialogIcon.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_server));
        }

        okayBtn.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.teal_700));
        cancelBtn.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.red));

        okayBtn.setOnClickListener(v -> dialog.dismiss());

        cancelBtn.setOnClickListener(v -> dialog.dismiss());
    }
}
