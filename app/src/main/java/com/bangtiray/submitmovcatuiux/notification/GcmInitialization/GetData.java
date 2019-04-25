package com.bangtiray.submitmovcatuiux.notification.GcmInitialization;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.bangtiray.submitmovcatuiux.DetailMovie.Main2Activity;
import com.bangtiray.submitmovcatuiux.R;
import com.bangtiray.submitmovcatuiux.TMDbAPI.GlobalConnection;
import com.bangtiray.submitmovcatuiux.notification.upcoming.ItemUpcoming;
import com.bangtiray.submitmovcatuiux.pojo.ItemFilm;
import com.google.gson.Gson;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ahmad Satiri on 2/14/2018.
 */

public class GetData {
    private List<ItemFilm> filmList;
    Context context;
    Call<ItemUpcoming> upcomingCall;
    private GlobalConnection globalConnection = new GlobalConnection();
    public GetData(Context context,List<ItemFilm> filmList) {
        this.filmList = filmList;
        this.context = context;
        Retrieve();
    }

    private void Retrieve() {
        upcomingCall=globalConnection.getAPI().getUpcoming("en-US");
        upcomingCall.enqueue(new Callback<ItemUpcoming>() {
            @Override
            public void onResponse(Call<ItemUpcoming> call, Response<ItemUpcoming> response) {
                if(response.isSuccessful()){
                    List<ItemFilm> films = response.body().getResults();
                    int sequence = new Random().nextInt(films.size());

                    ItemFilm film = films.get(sequence);
                    String title = films.get(sequence).getTitle();
                    String message = films.get(sequence).getOverview();
                    int notifId = 200;
                    Notifikasi(context.getApplicationContext(), title, message, notifId, film);
                }
            }

            @Override
            public void onFailure(Call<ItemUpcoming> call, Throwable t) {

            }
        });

//        String Url= BuildConfig.API_URL_OPTION + "upcoming?api_key=" + BuildConfig.API_KEY + BuildConfig.API_LANG;
//        SyncHttpClient client = new SyncHttpClient();
//        client.get(Url, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
////                try {
////                    String res = new String(responseBody);
////                    JSONObject jo = new JSONObject(res);
////                    JSONArray ja = jo.getJSONArray("results");
////                    for (int i = 0; i < ja.length(); i++) {
////                        JSONObject jo_if = ja.getJSONObject(i);
////                        ItemFilm itFilms = new ItemFilm(jo_if);
////                        filmList.add(itFilms);
////                    }
////                } catch (Exception ex) {
////                    ex.printStackTrace();
////
////                }
//                filmList = responseBody.getResults()
//                int sequence = new Random().nextInt(filmList.size());
//                String res= new String(responseBody);
//                ItemFilm film = filmList
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//
//            }
//        });

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
