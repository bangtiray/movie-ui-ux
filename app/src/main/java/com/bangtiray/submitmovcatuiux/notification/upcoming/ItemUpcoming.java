package com.bangtiray.submitmovcatuiux.notification.upcoming;

import com.bangtiray.submitmovcatuiux.pojo.ItemFilm;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ahmad Satiri on 2/14/2018.
 */

public class ItemUpcoming {
    @SerializedName("results")
    private List<ItemFilm> results;

    public List<ItemFilm> getResults() {
        return results;
    }

    public void setResults(List<ItemFilm> results) {
        this.results = results;
    }
}
