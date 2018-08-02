package com.example.kampf.games.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GiantBombService {

    @GET("games/?api_key=395804244fdd9539db55a52a0c95943f00bf7f70&format=json&field_list=deck,image,name")
    Call<GbObjectsListResponse> getGames(@Query("limit") int limit, @Query("offset") int offset);

    @GET("game/{guid}/?api_key=395804244fdd9539db55a52a0c95943f00bf7f70&format=json&field_list=description")
    Call<GbSingleObjectResponse> getGameDetails(@Path("guid") String guid);

    @GET("search/?api_key=395804244fdd9539db55a52a0c95943f00bf7f70&format=json&field_list=name,image,deck,guid&resources=game")
    Call<GbObjectsListResponse> searchGames(@Query("query") String query, @Query("limit") int limit);
}
