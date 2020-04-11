package mx.volcanolabs.urmovie.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import mx.volcanolabs.urmovie.data.AppDatabase;
import mx.volcanolabs.urmovie.entities.Movie;

public class MovieViewModel extends AndroidViewModel {
    private LiveData<List<Movie>> movies;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(this.getApplication());
        movies = appDatabase.getMovieDao().getMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}
