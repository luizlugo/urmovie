package mx.volcanolabs.urmovie.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.observers.DisposableObserver;
import mx.volcanolabs.urmovie.entities.FavoriteMovie;
import mx.volcanolabs.urmovie.entities.Movie;
import mx.volcanolabs.urmovie.entities.MoviesResponse;
import mx.volcanolabs.urmovie.network.MoviesService;
import timber.log.Timber;

public class MovieViewModel extends AndroidViewModel {
    private MutableLiveData<MovieData> movieDataObservable;
    private LiveData<List<FavoriteMovie>> favoriteMoviesObservable;
    private MoviesService moviesService;
    private MovieData movieData = new MovieData();

    public MovieViewModel(Application application) {
        super(application);
        moviesService = MoviesService.getInstance(application);

        if (movieDataObservable == null) {
            movieDataObservable = new MutableLiveData<>();
            // Load popular movies by default
            getMoviesForPage(1, MoviesService.POPULAR_TYPE);
        }

        if (favoriteMoviesObservable == null) {
            favoriteMoviesObservable = moviesService.getFavoriteMovies();
        }
    }

    public void getMoviesForPage(int page, String type) {
        if (type.equals(MoviesService.POPULAR_TYPE)) {
            getPopularMoviesForPage(page);
        } else if (type.equals(MoviesService.TOP_RATED_TYPE)) {
            getTopRatedMoviesForPage(page);
        }
    }

    public LiveData<MovieData> getMovieData() {
        return movieDataObservable;
    }

    public LiveData<List<FavoriteMovie>> getFavoriteMoviesObservable() {
        return favoriteMoviesObservable;
    }

    private void getPopularMoviesForPage(int page) {
        moviesService.getPopularMovies(page).subscribe(new DisposableObserver<MoviesResponse>() {
            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull MoviesResponse moviesResponse) {
                if (moviesResponse.getMovies().size() > 0) {
                    movieData.setCurrentPage(page);
                    List<Movie> actualMovies = movieData.getMovies();
                    actualMovies.addAll(moviesResponse.getMovies());
                    movieData.setMovies(actualMovies);
                    movieData.setTotalPages(moviesResponse.getTotalPages());
                }
                movieDataObservable.setValue(movieData);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Timber.e("On error retrieving popular movies " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void getTopRatedMoviesForPage(int page) {
        moviesService.getTopRatedMovies(page).subscribe(new DisposableObserver<MoviesResponse>() {
            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull MoviesResponse moviesResponse) {
                if (moviesResponse.getMovies().size() > 0) {
                    movieData.setCurrentPage(page);
                    List<Movie> actualMovies = movieData.getMovies();
                    actualMovies.addAll(moviesResponse.getMovies());
                    movieData.setMovies(actualMovies);
                    movieData.setTotalPages(moviesResponse.getTotalPages());
                }
                movieDataObservable.setValue(movieData);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Timber.e("On error retrieving top rated movies " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
