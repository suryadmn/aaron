package com.simpleMan.aaron;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    String BASE_URL = "https://aaron-app.000webhostapp.com/";

    @GET("load")
    Call<List<Model>> getModel();

}
