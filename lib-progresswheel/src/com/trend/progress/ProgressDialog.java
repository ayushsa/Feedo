package com.trend.progress;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.provider.SyncStateContract;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.trend.progress.ProgressWheel;

public class ProgressDialog {

    Dialog d;
    Context con;
    TextView tvText;
    private ProgressWheel progressWheel;
    RelativeLayout rlMain;

    public ProgressDialog(Context con) {
        this.con = con;
        d = new Dialog(con, R.style.CustomDialog);
        d.setContentView(R.layout.lprogress);
        tvText = (TextView) d.findViewById(R.id.tvText);
        d.setCancelable(false);
        d.setCanceledOnTouchOutside(false);
        progressWheel = (ProgressWheel) d.findViewById(R.id.progress_wheel);
        progressWheel.setBarColor(Color.WHITE);
        rlMain = (RelativeLayout) d.findViewById(R.id.rlParent);
    }

    public void setTranslucent() {
        rlMain.setBackgroundResource(R.color.translucent_black);
    }

    public void setCancelable(final OnCancel onCancel) {
        setCancelable(true);
        d.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                onCancel.onCancelDone();
            }
        });
    }

    public void setCancelable(boolean status) {
        d.setCancelable(status);
    }

    public void setCanceledOnTouchOutside(boolean status) {
        d.setCanceledOnTouchOutside(status);
    }

    public void setBarColor(int color) {
        progressWheel.setBarColor(color);
    }

    public void setBarWidth(int width) {
        progressWheel.setBarWidth(width);
    }

    public void setTitle(String msg)
    {
        tvText.setText(msg);
        tvText.setTextColor(Color.parseColor("#ffff1900"));
    }

    public void show()
    {
        if (!d.isShowing())
        {
            d.show();
        }
    }

    public boolean isShowing() {
        if (d.isShowing()) {
            return true;
        } else {
            return false;
        }
    }

    public void hide() {
        d.hide();
    }

    public void dismiss() {
        if (d != null ) {
            d.dismiss();
        }
    }


    public interface OnCancel {
        void onCancelDone();
    }
}
