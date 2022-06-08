package com.musicapps.tubidyeaskekuli;

import android.os.Handler;
import android.view.View;

public abstract class OnSingleClickListener implements View.OnClickListener {



    public abstract void onSingleClick(View v);

    @Override
    public final void onClick(View v) {
        v.setClickable(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                v.setClickable(true);

            }
        },5000);// set time as per your requirement
        onSingleClick(v);
    }






}