package com.bangtiray.submitmovcatuiux;


import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bangtiray.submitmovcatuiux.notification.AlarmReceiver;
import com.bangtiray.submitmovcatuiux.notification.upcoming.Tasking;

import butterknife.BindString;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends PreferenceFragment {


    @BindString(R.string.key_reminder)
    String reminder;
    @BindString(R.string.key_upcoming)
    String upcoming;

    private AlarmReceiver alarmReceiver = new AlarmReceiver();
    private Tasking tasking;

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        ButterKnife.bind(this, getActivity());
        tasking = new Tasking(getActivity());
        findPreference(reminder).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String id = preference.getKey();
                boolean Active = (boolean) newValue;
                if (id.equals(reminder)) {
                    if (Active) {
                        alarmReceiver.setRepeatingAlarm(getActivity(), AlarmReceiver.TYPE_REPEATING, "19:07", getString(R.string.greating_daily));
                        Toast.makeText(getActivity(), getString(R.string.setDaily), Toast.LENGTH_LONG).show();
                    } else {
                        alarmReceiver.cancelAlarm(getActivity(), alarmReceiver.TYPE_REPEATING);
                        Toast.makeText(getActivity(), getString(R.string.setcancelDaily), Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            }
        });

        findPreference(upcoming).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String id = preference.getKey();
                boolean Active = (boolean) newValue;
                if (id.equals(upcoming)) {
                    if (Active) {
                        tasking.PeriodicTasking();
                        Toast.makeText(getActivity(), getString(R.string.setUpcoming), Toast.LENGTH_LONG).show();
                    } else {
                        tasking.cancelPeriodicTasking();
                        Toast.makeText(getActivity(), getString(R.string.setcancelUpcoming), Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            }
        });
    }


}
