package com.bangtiray.submitmovcatuiux.TMDbAPI;

import com.bangtiray.submitmovcatuiux.BuildConfig;
import com.bangtiray.submitmovcatuiux.notification.upcoming.ItemUpcoming;
import com.bangtiray.submitmovcatuiux.pojo.ItemFilm;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ahmad Satiri on 1/24/2018.
 */

public class GlobalConnection {

//    public static ArrayList<ItemFilm> JSONdata(String TMDbAPIURL) {
//        final ArrayList<ItemFilm> itemFilms = new ArrayList<>();
//        SyncHttpClient syncHttpClient = new SyncHttpClient();
//        syncHttpClient.get(TMDbAPIURL, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                try {
//                    String res= new String(responseBody);
//                    JSONObject jo = new JSONObject(res);
//                    JSONArray ja = jo.getJSONArray("results");
//                    for (int i = 0; i < ja.length(); i++) {
//                        JSONObject jo_if = ja.getJSONObject(i);
//                        ItemFilm itFilms = new ItemFilm(jo_if);
//                        itemFilms.add(itFilms);
//                    }
//                }catch (Exception ex){
//                    ex.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//
//            }
//        });
//        return itemFilms;
//    }

    private CallAPI apiCall;

    public GlobalConnection() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        HttpUrl httpUrl = original.url()
                                .newBuilder()
                                .addQueryParameter("api_key", BuildConfig.API_KEY)
                                .build();

                        original = original.newBuilder()
                                .url(httpUrl)
                                .build();

                        return chain.proceed(original);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiCall = retrofit.create(CallAPI.class);
    }

    public CallAPI getAPI() {
        return apiCall;
    }


    public static String setApiByID(String id_film) {
        //+"callback=test"
        String url;
        url = BuildConfig.API_URL_OPTION + id_film + "?api_key=" + BuildConfig.API_KEY + BuildConfig.API_LANG;
        return url;
    }


}
