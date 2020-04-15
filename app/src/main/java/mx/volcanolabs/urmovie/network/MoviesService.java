package mx.volcanolabs.urmovie.network;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import mx.volcanolabs.urmovie.data.AppDatabase;
import mx.volcanolabs.urmovie.data.MovieDao;
import mx.volcanolabs.urmovie.data.MovieFavoriteDao;
import mx.volcanolabs.urmovie.entities.FavoriteMovie;
import mx.volcanolabs.urmovie.entities.Movie;
import mx.volcanolabs.urmovie.entities.MovieDetail;
import mx.volcanolabs.urmovie.entities.MovieFavorite;
import mx.volcanolabs.urmovie.entities.MovieReviewResponse;
import mx.volcanolabs.urmovie.entities.MovieVideoResponse;
import mx.volcanolabs.urmovie.entities.MoviesResponse;

public class MoviesService {
    private RestApi mRestApi;
    private static MoviesService sInstance;
    private AppDatabase appDatabase;
    public static final String TOP_RATED_TYPE = "topRated";
    public static final String POPULAR_TYPE = "popular";
    public static final String FAVORITES_TYPE = "favorites";

    public MoviesService(Context mContext) {
        mRestApi = NetClientInstance.getRestApi(mContext);
        appDatabase = AppDatabase.getInstance(mContext);
    }

    public static MoviesService getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MoviesService(context);
        }

        return sInstance;
    }

    public Observable<MoviesResponse> getTopRatedMovies(int page) {
        return Observable.just(appDatabase)
                .subscribeOn(Schedulers.io())
                .flatMap(appDatabase -> {
                    MoviesResponse cache = appDatabase.getMovieDao().getMoviesForPageAndType(page, TOP_RATED_TYPE);
                    return (cache != null) ? Observable.just(cache) : Observable.empty();
                })
                .switchIfEmpty(mRestApi.getTopRatedMovies(page))
                .flatMap(moviesResponse -> {
                    moviesResponse.setType(TOP_RATED_TYPE);
                    return Observable.just(moviesResponse);
                })
                .doOnNext(moviesResponse -> appDatabase.getMovieDao().insertMovieResponse(moviesResponse))
                .doOnNext(moviesResponse -> appDatabase.getMovieDao().insertMovies(moviesResponse.getMovies()))
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MoviesResponse> getPopularMovies(int page) {
        return Observable.just(appDatabase)
                .subscribeOn(Schedulers.io())
                .flatMap(appDatabase -> {
                    MoviesResponse cache = appDatabase.getMovieDao().getMoviesForPageAndType(page, POPULAR_TYPE);
                    return (cache != null) ? Observable.just(cache) : Observable.empty();
                })
                .switchIfEmpty(mRestApi.getPopularMovies(page))
                .flatMap(moviesResponse -> {
                    moviesResponse.setType(POPULAR_TYPE);
                    return Observable.just(moviesResponse);
                })
                .doOnNext(moviesResponse -> appDatabase.getMovieDao().insertMovieResponse(moviesResponse))
                .doOnNext(moviesResponse -> appDatabase.getMovieDao().insertMovies(moviesResponse.getMovies()))
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void favoriteMovie(int movieId) {
        Observable
                .just(appDatabase)
                .subscribeOn(Schedulers.io())
                .doOnNext(appDatabase -> appDatabase.getMovieFavoriteDao().favoriteMovie(new MovieFavorite(movieId)))
                .subscribe();
    }

    public void unFavoriteMovie(MovieFavorite movieFavorite) {
        Observable
                .just(appDatabase)
                .subscribeOn(Schedulers.io())
                .doOnNext(appDatabase -> appDatabase.getMovieFavoriteDao().unFavoriteMovie(movieFavorite))
                .subscribe();
    }

    public Observable<MovieDetail> getVideoData(int movieId) {
        Observable<MovieDetail> movieDetailObservable = Observable
                .just(appDatabase)
                .map(appDatabase -> {
                    MovieDao movieDao = appDatabase.getMovieDao();
                    MovieFavoriteDao movieFavoriteDao = appDatabase.getMovieFavoriteDao();
                    Movie movie = movieDao.getMovieById(movieId);
                    MovieFavorite movieFavorite = movieFavoriteDao.getMovieFavoriteForMovie(movieId);
                    return new MovieDetail(movie, movieFavorite);
                });

        Observable<MovieVideoResponse> movieVideoResponseObservable = mRestApi.getMovieVideos(movieId);
        Observable<MovieReviewResponse> movieReviewResponseObservable = mRestApi.getMovieReviews(movieId);

        return Observable.zip(movieDetailObservable, movieVideoResponseObservable, movieReviewResponseObservable,
                (movieDetail, movieVideoResponse, movieReviewsResponse) -> {
                    MovieDetail movieDetailFinal = new MovieDetail();
                    movieDetailFinal.setMovieFavorite(movieDetail.getMovieFavorite());
                    movieDetailFinal.setMovieVideos(movieVideoResponse.getVideos());
                    movieDetailFinal.setMovieReviews(movieReviewsResponse.getReviews());
                    movieDetailFinal.setMovie(movieDetail.getMovie());
                    return movieDetailFinal;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public LiveData<List<FavoriteMovie>> getFavoriteMovies() {
        return appDatabase.getMovieFavoriteDao().getFavoriteMovies();
    }
}
