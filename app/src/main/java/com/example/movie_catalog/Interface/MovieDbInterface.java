package com.example.movie_catalog.Interface;

import com.example.movie_catalog.Model.MovieDB.MovieDb;
import com.example.movie_catalog.Model.TvShowDB.TvShowDb;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDbInterface {

    @GET("discover/movie")
    Call<MovieDb> getMovieDb(@Query("api_key") String apikey, @Query("language") String language);

    @GET("discover/tv")
    Call<TvShowDb> getTvShowDb(@Query("api_key") String apikey, @Query("language") String language);

    @GET("discover/movie")
    Call<MovieDb> getReleasedMoviesDb(@Query("api_key") String apikey , @Query("primary_release_date.gte") String date, @Query("primary_release_date.lte") String today);

    @GET("search/movie")
    Call<MovieDb> searchMovieDb(@Query("api_key") String apikey, @Query("language") String language,@Query("query") String query);

    @GET("search/tv")
    Call<TvShowDb> searchTvShowDb(@Query("api_key") String apikey, @Query("language") String language,@Query("query") String query);
}
