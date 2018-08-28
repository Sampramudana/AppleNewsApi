package com.pramudana.sam.applenewsapi.Network;

import com.pramudana.sam.applenewsapi.Model.ResponseNewsApi;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sampramudana on 8/28/18.
 */

public interface ApiService {

    @GET("everything?q=apple&apiKey=795f5a13a92a46b1bfdd25f8d1307c23")
    Call<ResponseNewsApi> readNewsApi();
}
