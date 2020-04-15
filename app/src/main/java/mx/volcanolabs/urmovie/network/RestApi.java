package mx.volcanolabs.urmovie.network;

import io.reactivex.rxjava3.core.Observable;
import mx.volcanolabs.urmovie.Constants;
import mx.volcanolabs.urmovie.entities.MovieReviewResponse;
import mx.volcanolabs.urmovie.entities.MovieVideoResponse;
import mx.volcanolabs.urmovie.entities.MoviesResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestApi {
    @GET("movie/popular?api_key="+ Constants.moviedbApiKey)
    Observable<MoviesResponse> getPopularMovies(@Query("page") int page);

    @GET("movie/top_rated?api_key="+ Constants.moviedbApiKey)
    Observable<MoviesResponse> getTopRatedMovies(@Query("page") int page);

    @GET("movie/{id}/videos?api_key="+ Constants.moviedbApiKey)
    Observable<MovieVideoResponse> getMovieVideos(@Path("id") int movieId);

    @GET("movie/{id}/reviews?api_key="+ Constants.moviedbApiKey)
    Observable<MovieReviewResponse> getMovieReviews(@Path("id") int movieId);
}
