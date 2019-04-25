package com.bangtiray.submitmovcatuiux;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bangtiray.submitmovcatuiux.notification.AlarmReceiver;
import com.bangtiray.submitmovcatuiux.notification.upcoming.Tasking;
import com.google.android.gms.gcm.GcmNetworkManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServicesFragment extends Fragment implements View.OnClickListener{

    Button buttonDaily,buttonDailyCancel, ButtonUpcoming,ButtonUpcomingCancel;
    private AlarmReceiver alarmReceiver = new AlarmReceiver();
    private Tasking TaskingUpcoming;


    public ServicesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview =inflater.inflate(R.layout.fragment_services, container, false);
        buttonDaily=(Button)rootview.findViewById(R.id.buttonDaily);
        buttonDailyCancel=(Button)rootview.findViewById(R.id.buttonDailyCancel);
        ButtonUpcoming=(Button)rootview.findViewById(R.id.ButtonUpcoming);
        ButtonUpcomingCancel=(Button)rootview.findViewById(R.id.ButtonUpcomingCancel);
        buttonDaily.setOnClickListener(this);
        buttonDailyCancel.setOnClickListener(this);
        ButtonUpcoming.setOnClickListener(this);
        ButtonUpcomingCancel.setOnClickListener(this);
        TaskingUpcoming= new Tasking(getActivity());
        return rootview;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonDaily:
                alarmReceiver.setRepeatingAlarm(getActivity(), AlarmReceiver.TYPE_REPEATING,"07:00",getString(R.string.greating_daily));
                Toast.makeText(getActivity(), getString(R.string.setDaily),Toast.LENGTH_LONG).show();
                break;
            case R.id.buttonDailyCancel:
                alarmReceiver.cancelAlarm(getActivity(), alarmReceiver.TYPE_REPEATING);
                Toast.makeText(getActivity(), getString(R.string.setcancelDaily),Toast.LENGTH_LONG).show();
                break;
            case R.id.ButtonUpcoming:
                TaskingUpcoming.PeriodicTasking();
                Toast.makeText(getActivity(), getString(R.string.setUpcoming),Toast.LENGTH_LONG).show();
                break;
            case R.id.ButtonUpcomingCancel:
                TaskingUpcoming.cancelPeriodicTasking();
                Toast.makeText(getActivity(), getString(R.string.setcancelUpcoming),Toast.LENGTH_LONG).show();
                break;

        }
    }
}
