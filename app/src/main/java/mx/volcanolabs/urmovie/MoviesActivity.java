package mx.volcanolabs.urmovie;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mx.volcanolabs.urmovie.adapters.MoviesAdapter;
import mx.volcanolabs.urmovie.adapters.MoviesItemDecorator;
import mx.volcanolabs.urmovie.entities.FavoriteMovie;
import mx.volcanolabs.urmovie.entities.Movie;
import mx.volcanolabs.urmovie.listeners.MovieClickListener;
import mx.volcanolabs.urmovie.network.MoviesService;
import mx.volcanolabs.urmovie.viewmodel.MovieData;
import mx.volcanolabs.urmovie.viewmodel.MovieViewModel;

public class MoviesActivity extends AppCompatActivity implements MovieClickListener {
    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;
    @BindView(R.id.vg_no_internet)
    ViewGroup vgNoInternet;
    @BindView(R.id.loading_indicator)
    ProgressBar loadingIndicator;

    private MoviesAdapter adapter;
    private List<Movie> movies = new ArrayList<>();
    private List<Movie> favoriteMovies = new ArrayList<>();
    private int currentPage = 1;
    private int currentCategory = R.id.popular;
    MovieViewModel movieViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.card_margin);
        rvMovies.setLayoutManager(new GridLayoutManager(this, numberOfColumns(), LinearLayoutManager.VERTICAL, false));
        rvMovies.addItemDecoration(new MoviesItemDecorator(numberOfColumns(), spacingInPixels, true));
        movieViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MovieViewModel.class);
        movieViewModel.getMovieData().observe(this, this::onMoviesRetrieved);
        movieViewModel.getFavoriteMoviesObservable().observe(this, this::onFavoriteMoviesRetrieved);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (currentCategory != item.getItemId()) {
            switch (item.getItemId()) {
                case R.id.popular:
                    loadPopularMovies();
                    break;
                case R.id.top_rated:
                    loadTopRatedMovies();
                    break;
                case R.id.favorites:
                    loadFavorites();
                    break;
            }
            currentCategory = item.getItemId();
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadPopularMovies() {
        setTitle(R.string.popular);
        currentPage = 1;
        adapter = null;
        movies.clear();
        movieViewModel.getMoviesForPage(currentPage, MoviesService.POPULAR_TYPE);
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    private void loadTopRatedMovies() {
        setTitle(R.string.top_rated);
        currentPage = 1;
        adapter = null;
        movies.clear();
        movieViewModel.getMoviesForPage(currentPage, MoviesService.TOP_RATED_TYPE);
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    private void loadFavorites() {
        setTitle(R.string.favorites_option);
        currentPage = 1;
        adapter = null;
        movies.clear();
        loadingIndicator.setVisibility(View.VISIBLE);
        onMoviesRetrieved(new MovieData(1, favoriteMovies, 1));
    }

    public void onMoviesRetrieved(MovieData movieData) {
        this.movies = movieData.getMovies();

        if (adapter == null) {
            adapter = new MoviesAdapter(this, movieData.getTotalPages());
            rvMovies.setAdapter(adapter);
        }

        adapter.setData(this.movies, movieData.getCurrentPage());
        loadingIndicator.setVisibility(View.GONE);
        currentPage = movieData.getCurrentPage();
    }

    public void onFavoriteMoviesRetrieved(List<FavoriteMovie> favoriteMovies) {
        List<Movie> movies = new ArrayList<>();

        if (favoriteMovies != null && favoriteMovies.size() > 0) {
            for (FavoriteMovie favoriteMovie : favoriteMovies) {
                movies.add(favoriteMovie.movie);
            }
            this.favoriteMovies = movies;
        }

        if (currentCategory == R.id.favorites) {
            adapter.setData(movies, 1);
        }
    }

    @Override
    public void onMovieClicked(Movie movie) {
        Intent movieIntent = new Intent(this, MovieDetailActivity.class);
        movieIntent.putExtra(MovieDetailActivity.MOVIE_PARAM, movie.getId());
        startActivity(movieIntent);
    }

    @Override
    public void onLoadMoreItemsClicked() {
        currentPage++;

        if (currentCategory == R.id.popular) {
            movieViewModel.getMoviesForPage(currentPage, MoviesService.POPULAR_TYPE);
        } else {
            movieViewModel.getMoviesForPage(currentPage, MoviesService.TOP_RATED_TYPE);
        }
    }

    @OnClick(R.id.btn_retry)
    public void onBtnRetryClicked() {
        switch (currentCategory) {
            case R.id.popular:
                movieViewModel.getMoviesForPage(currentPage, MoviesService.POPULAR_TYPE);
                break;
            case R.id.top_rated:
                movieViewModel.getMoviesForPage(currentPage, MoviesService.TOP_RATED_TYPE);
                break;
        }
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the item
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2; //to keep the grid aspect
        return nColumns;
    }
}
