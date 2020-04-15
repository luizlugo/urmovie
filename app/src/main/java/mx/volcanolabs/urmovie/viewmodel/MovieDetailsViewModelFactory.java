package mx.volcanolabs.urmovie.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MovieDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application mApplication;
    private int movieId;

    public MovieDetailsViewModelFactory(Application application, int movieId) {
        mApplication = application;
        this.movieId = movieId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieDetailsViewModel(mApplication, movieId);
    }
}
