package mx.volcanolabs.urmovie.network;

import io.reactivex.rxjava3.core.Observable;
import mx.volcanolabs.urmovie.models.MoviesResponse;

public class MoviesService {
    RestApi mRestApi;

    public MoviesService() {
        mRestApi = NetClientInstance.getRestApi();
    }

    private Observable<MoviesResponse> getTopRatedMovies(Integer page) {
        return mRestApi
                .getTopRatedMovies(page);
    }

    private Observable<MoviesResponse> getPopularMovies(Integer page) {
        return mRestApi
                .getPopularMovies(page);
    }
}
