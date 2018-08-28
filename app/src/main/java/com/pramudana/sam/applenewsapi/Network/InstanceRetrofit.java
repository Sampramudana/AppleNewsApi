package com.pramudana.sam.applenewsapi.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sampramudana on 8/28/18.
 */

public class InstanceRetrofit {
    private static final String Weburl = "http://muslimsalat.com/tangerang/";

    private static Retrofit setInit() {
        return new Retrofit.Builder()
                .baseUrl(Weburl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static ApiService getInstance() {
        return setInit().create(ApiService.class);
    }
}
