package com.developers.stable.mytestapp.data.network;

import com.developers.stable.mytestapp.models.ModelAnimal;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("api.php")
    Call<ModelAnimal> getData(@Query("query") String queryText);//cat or dog

}
