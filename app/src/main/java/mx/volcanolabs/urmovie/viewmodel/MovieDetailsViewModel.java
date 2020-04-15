package mx.volcanolabs.urmovie.viewmodel;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableObserver;
import mx.volcanolabs.urmovie.entities.MovieDetail;
import mx.volcanolabs.urmovie.entities.MovieFavorite;
import mx.volcanolabs.urmovie.network.MoviesService;

public class MovieDetailsViewModel extends ViewModel {
    private MutableLiveData<MovieDetail> movieDetailObservable = new MutableLiveData<>();
    private MoviesService moviesService;
    private int movieId;

    public MovieDetailsViewModel(Application application, int movieId) {
        moviesService = new MoviesService(application);
        this.movieId = movieId;
        getVideoData();
    }

    public MutableLiveData<MovieDetail> getMovieVideosObservable() {
        return movieDetailObservable;
    }

    public void favoriteMovie(int movieId) {
        moviesService.favoriteMovie(movieId);
    }

    public void unFavoriteMovie(MovieFavorite movieFavorite) {
        moviesService.unFavoriteMovie(movieFavorite);
    }

    private void getVideoData() {
        moviesService.getVideoData(movieId).subscribe(new DisposableObserver<MovieDetail>() {
            @Override
            public void onNext(@NonNull MovieDetail movieDetail) {
                movieDetailObservable.setValue(movieDetail);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
