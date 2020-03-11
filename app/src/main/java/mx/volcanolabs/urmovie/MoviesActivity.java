package mx.volcanolabs.urmovie;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
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
import mx.volcanolabs.urmovie.entities.Movie;
import mx.volcanolabs.urmovie.entities.MoviesResponse;
import mx.volcanolabs.urmovie.listeners.MovieClickListener;
import mx.volcanolabs.urmovie.presenter.MoviesPresenter;
import mx.volcanolabs.urmovie.views.MoviesView;

public class MoviesActivity extends AppCompatActivity implements MoviesView, MovieClickListener {
    MoviesPresenter presenter;
    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;
    @BindView(R.id.vg_no_internet)
    ViewGroup vgNoInternet;
    @BindView(R.id.loading_indicator)
    ProgressBar loadingIndicator;

    private MoviesAdapter adapter;
    private List<Movie> movies = new ArrayList<>();
    private int currentPage = 1;
    private int currentCategory = R.id.popular;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.card_margin);
        presenter = MoviesPresenter.getInstance();
        rvMovies.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
        rvMovies.addItemDecoration(new MoviesItemDecorator(2, spacingInPixels, true));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        loadPopularMovies();
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
        presenter.loadPopularMoviesForPage(currentPage);
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    private void loadTopRatedMovies() {
        setTitle(R.string.top_rated);
        currentPage = 1;
        adapter = null;
        movies.clear();
        presenter.loadTopRatedMoviesForPage(currentPage);
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMoviesRetrieved(MoviesResponse moviesResponse) {
        hideErrorScreen();
        this.movies.addAll(moviesResponse.getMovies());

        if (adapter == null) {
            adapter = new MoviesAdapter(this, moviesResponse.getTotalPages());
            rvMovies.setAdapter(adapter);
        }

        adapter.setData(this.movies, currentPage);
        loadingIndicator.setVisibility(View.GONE);
    }

    private void hideErrorScreen() {
        rvMovies.setVisibility(View.VISIBLE);
        vgNoInternet.setVisibility(View.GONE);
    }

    @Override
    public void displayErrorScreen() {
        rvMovies.setVisibility(View.GONE);
        vgNoInternet.setVisibility(View.VISIBLE);
        loadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void onMovieClicked(Movie movie) {
        // no-op
    }

    @Override
    public void onLoadMoreItemsClicked() {
        currentPage++;

        if (currentCategory == R.id.popular) {
            presenter.loadPopularMoviesForPage(currentPage);
        } else {
            presenter.loadTopRatedMoviesForPage(currentPage);
        }
    }

    @OnClick(R.id.btn_retry)
    public void onBtnRetryClicked() {
        switch (currentCategory) {
            case R.id.popular:
                presenter.loadPopularMoviesForPage(currentPage);
                break;
            case R.id.top_rated:
                presenter.loadTopRatedMoviesForPage(currentPage);
                break;
        }
    }
}
