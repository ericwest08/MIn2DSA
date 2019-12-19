package com.example.min2dsa;

import com.example.min2dsa.models.Museums;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface API {
    @GET("pag-ini/{pag-ini}/pag-fi/{pag-fi}")
    Call<Museums> getMuseums(@Path("pag-ini") int pagIni, @Path("pag-fi") int pagFi);

}
