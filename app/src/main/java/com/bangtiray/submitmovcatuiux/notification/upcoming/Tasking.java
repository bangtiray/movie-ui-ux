package com.bangtiray.submitmovcatuiux.notification.upcoming;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

/**
 * Created by Ahmad Satiri on 2/14/2018.
 */

public class Tasking {
    private GcmNetworkManager gcmNetworkManager;
    long period = 3 * 60 * 1000;
    long flex=10;
    public Tasking(Context c){
        gcmNetworkManager = GcmNetworkManager.getInstance(c);
    }

    public void PeriodicTasking(){
        Task periodTask = new PeriodicTask.Builder()
                .setService(Services.class)
                .setPeriod(10)
                .setFlex(10)
                .setTag(Services.TASKING)
                .setPersisted(true)
                .build();
        gcmNetworkManager.schedule(periodTask);
    }

    public void cancelPeriodicTasking(){
        if(gcmNetworkManager != null){
            gcmNetworkManager.cancelTask(Services.TASKING, Services.class);
        }
    }
}
