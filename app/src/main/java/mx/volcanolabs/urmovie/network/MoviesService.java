package mx.volcanolabs.urmovie.network;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import mx.volcanolabs.urmovie.entities.MoviesResponse;

public class MoviesService {
    private RestApi mRestApi;

    public MoviesService() {
        mRestApi = NetClientInstance.getRestApi();
    }

    public Observable<MoviesResponse> getTopRatedMovies(Integer page) {
        return mRestApi
                .getTopRatedMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MoviesResponse> getPopularMovies(Integer page) {
        return mRestApi
                .getPopularMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
