package com.bangtiray.submitmovcatuiux.TMDbAPI;

import com.bangtiray.submitmovcatuiux.notification.upcoming.ItemUpcoming;
import com.bangtiray.submitmovcatuiux.pojo.CallItemDetailByID;
import com.bangtiray.submitmovcatuiux.pojo.ItemDetailByID;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ahmad Satiri on 2/14/2018.
 */

public interface CallAPI {
    @GET("movie/upcoming")
    Call<ItemUpcoming> getUpcoming(@Query("language") String lang);

    @GET("movie/{movie_id}")
    Call<ItemDetailByID> getDetailById(@Path("movie_id") String movie_id, @Query("language") String language);
}
