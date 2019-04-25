package com.bangtiray.submitmovcatuiux.notification.upcoming;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.bangtiray.submitmovcatuiux.DetailMovie.Main2Activity;
import com.bangtiray.submitmovcatuiux.R;
import com.bangtiray.submitmovcatuiux.TMDbAPI.GlobalConnection;
import com.bangtiray.submitmovcatuiux.pojo.ItemFilm;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.google.gson.Gson;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Services extends GcmTaskService {
    public static String TASKING = "upcoming";
    Call<ItemUpcoming> upcomingCall;
    private GlobalConnection globalConnection = new GlobalConnection();

    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;
        if (taskParams.getTag().equals(TASKING)) {
            Retrieve();
            result = GcmNetworkManager.RESULT_SUCCESS;
        }
        return result;
    }


    private void Retrieve() {
        upcomingCall = globalConnection.getAPI().getUpcoming("en-US");
        upcomingCall.enqueue(new Callback<ItemUpcoming>() {
            @Override
            public void onResponse(Call<ItemUpcoming> call, Response<ItemUpcoming> response) {
                if (response.isSuccessful()) {
                    List<ItemFilm> films = response.body().getResults();
                    int sequence = new Random().nextInt(films.size());

                    ItemFilm film = films.get(sequence);
                    String title = films.get(sequence).getTitle();
                    String message = films.get(sequence).getOverview();
                    int notifId = 200;
                    Notifikasi(getApplicationContext(), title, message, notifId, film);
                }
            }

            @Override
            public void onFailure(Call<ItemUpcoming> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.Failed, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void Notifikasi(Context applicationContext, String title, String message, int notifId, ItemFilm film) {
        NotificationManager notificationManagerCompat = (NotificationManager) applicationContext.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(applicationContext, Main2Activity.class);
        intent.putExtra(Main2Activity.PARAM, new Gson().toJson(film));
        PendingIntent pendingIntent = PendingIntent.getActivity(applicationContext, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(applicationContext)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(applicationContext, android.R.color.transparent))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        notificationManagerCompat.notify(notifId, builder.build());
    }

}
