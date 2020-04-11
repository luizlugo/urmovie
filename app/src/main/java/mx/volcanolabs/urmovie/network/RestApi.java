package mx.volcanolabs.urmovie.network;

import mx.volcanolabs.urmovie.Constants;
import mx.volcanolabs.urmovie.entities.MoviesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestApi {
    @GET("movie/popular?api_key="+ Constants.moviedbApiKey)
    Call<MoviesResponse> getPopularMovies(@Query("page") int page);

    @GET("movie/top_rated?api_key="+ Constants.moviedbApiKey)
    Call<MoviesResponse> getTopRatedMovies(@Query("page") int page);

    @GET("movie/{id}/videos?api_key="+ Constants.moviedbApiKey)
    Call<MoviesResponse> getMovieVideos(@Path("id") String movieId);

    @GET("movie/{id}/reviews?api_key="+ Constants.moviedbApiKey)
    Call<MoviesResponse> getMovieReviews(@Path("id") String movieId);
}
