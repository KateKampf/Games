package com.example.kampf.games.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GiantBombService {

    @GET("games/?api_key=395804244fdd9539db55a52a0c95943f00bf7f70&format=json&field_list=deck,image,name")
    Call<GbObjectsListResponse> getGames(@Query("limit") int limit, @Query("offset") int offset);


}
