package mx.volcanolabs.urmovie.network;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import mx.volcanolabs.urmovie.data.AppDatabase;
import mx.volcanolabs.urmovie.entities.Movie;
import mx.volcanolabs.urmovie.entities.MoviesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesService {
    private RestApi mRestApi;

    public MoviesService() {
        mRestApi = NetClientInstance.getRestApi();
    }

    public void getTopRatedMovies(Integer page) {
        mRestApi
            .getTopRatedMovies(page)
            .enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    MoviesResponse moviesResponse = response.body();

                    if (moviesResponse != null) {
                        List<Movie> movies = moviesResponse.getMovies();

                        if (movies.size() > 0) {
                            // Add page number
                            for (Movie movie : movies) {
                                movie.setPageNumber(page);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                }
            });
    }

    public void getPopularMovies(Integer page) {
        mRestApi
            .getPopularMovies(page)
            .enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    MoviesResponse moviesResponse = response.body();

                    if (moviesResponse != null) {
                        List<Movie> movies = moviesResponse.getMovies();

                        if (movies.size() > 0) {
                            // Add page number
                            for (Movie movie : movies) {
                                movie.setPageNumber(page);
                            }

                        }
                    }
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                }
            });
    }

    /*public void getVideosForMovie(String movieId) {
        mRestApi
                .getMovieVideos(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void getReviewsForMovie(String movieId) {
        mRestApi
                .getMovieReviews(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }*/

}
