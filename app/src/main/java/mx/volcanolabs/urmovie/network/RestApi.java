package mx.volcanolabs.urmovie.network;

import io.reactivex.rxjava3.core.Observable;
import mx.volcanolabs.urmovie.Constants;
import mx.volcanolabs.urmovie.entities.MoviesResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApi {
    @GET("movie/popular?api_key="+ Constants.moviedbApiKey)
    Observable<MoviesResponse> getPopularMovies(@Query("page") int page);

    @GET("movie/top_rated?api_key="+ Constants.moviedbApiKey)
    Observable<MoviesResponse> getTopRatedMovies(@Query("page") int page);
}
