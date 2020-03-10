package mx.volcanolabs.urmovie;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.volcanolabs.urmovie.adapters.MoviesAdapter;
import mx.volcanolabs.urmovie.entities.Movie;
import mx.volcanolabs.urmovie.presenter.MoviesPresenter;
import mx.volcanolabs.urmovie.views.MoviesView;

public class MoviesActivity extends AppCompatActivity implements MoviesView {
    MoviesPresenter presenter;
    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;
    private MoviesAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);
        presenter = MoviesPresenter.getInstance();
        adapter = new MoviesAdapter();
        rvMovies.setAdapter(adapter);
        rvMovies.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onMoviesRetrieved(List<Movie> movies) {
        adapter.setData(movies);
    }
}
