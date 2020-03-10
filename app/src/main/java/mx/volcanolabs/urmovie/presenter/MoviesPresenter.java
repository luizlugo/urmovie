package mx.volcanolabs.urmovie.presenter;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableObserver;
import mx.volcanolabs.urmovie.entities.MoviesResponse;
import mx.volcanolabs.urmovie.network.MoviesService;
import mx.volcanolabs.urmovie.views.MoviesView;
import timber.log.Timber;

public class MoviesPresenter {
    private static MoviesPresenter instance;
    private MoviesView view;
    private MoviesService moviesService;
    private int currentPage = 1;

    public MoviesPresenter() {
        moviesService = new MoviesService();
    }

    public static MoviesPresenter getInstance() {
        if (instance == null) {
            instance = new MoviesPresenter();
        }
        return instance;
    }

    public void setView(MoviesView view) {
        this.view = view;
    }

    public void init() {
        this.getMovies();
    }

    public void getMovies() {
        moviesService.getPopularMovies(currentPage).subscribe(new DisposableObserver<MoviesResponse>() {
            @Override
            public void onNext(@NonNull MoviesResponse moviesResponse) {
                if (view != null) {
                    view.onMoviesRetrieved(moviesResponse.getMovies());
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Timber.i("Error when requesting movies");
            }

            @Override
            public void onComplete() {
            }
        });
    }
}
