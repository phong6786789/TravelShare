package com.duan.travelshare.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.duan.travelshare.R;

public class InfoDialog {
    Activity activity;
    Dialog dialog;

    public InfoDialog() {
    }

    public InfoDialog(Activity activity) {
        this.activity = activity;
    }

    public void toastInfo(String text) {
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.show);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        TextView textInfo = dialog.findViewById(R.id.tvInfo);
        textInfo.setText(text);
        dialog.show();

    }

    public void dismisToast() {
        dialog.dismiss();
    }
}
