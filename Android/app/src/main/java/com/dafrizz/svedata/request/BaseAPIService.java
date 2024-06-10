package com.dafrizz.svedata.request;

import com.dafrizz.svedata.model.BaseResponse;
import com.dafrizz.svedata.model.Card;
import com.dafrizz.svedata.model.DeckCards;
import com.dafrizz.svedata.model.DeckList;
import com.dafrizz.svedata.model.ListList;
import com.dafrizz.svedata.model.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface BaseAPIService {
    @FormUrlEncoded
    @POST("user/login")
    Call<BaseResponse<User>> login(
        @Field("email") String email,
        @Field("password") String password
    );
    @FormUrlEncoded
    @POST("user/register")
    Call<BaseResponse<User>> register(
        @Field("username") String username,
        @Field("email") String email,
        @Field("password") String password
    );
    @GET("/decks")
    Call<BaseResponse<List<DeckList>>> getAllDecks();
    @GET("/list")
    Call<BaseResponse<List<ListList>>> getAllLists();
    @GET("/cards")
    Call<BaseResponse<List<Card>>> getAllCards();
    @FormUrlEncoded
    @POST("/cards/filtered")
    Call<BaseResponse<List<Card>>> getCardsFiltered(
            @FieldMap Map<String, String> filters
    );

}
