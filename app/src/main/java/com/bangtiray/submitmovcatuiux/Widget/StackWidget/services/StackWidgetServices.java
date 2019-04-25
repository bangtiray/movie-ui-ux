package com.bangtiray.submitmovcatuiux.Widget.StackWidget.services;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.bangtiray.submitmovcatuiux.Widget.StackWidget.remoteviews.StackRemoteVF;

/**
 * Created by Ahmad Satiri on 2/14/2018.
 */

public class StackWidgetServices extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteVF(this.getApplicationContext(), intent);
    }
}
