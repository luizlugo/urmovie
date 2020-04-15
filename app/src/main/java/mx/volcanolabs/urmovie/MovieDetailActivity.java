package mx.volcanolabs.urmovie;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.volcanolabs.urmovie.adapters.MovieReviewsAdapter;
import mx.volcanolabs.urmovie.adapters.MovieVideosAdapter;
import mx.volcanolabs.urmovie.entities.Movie;
import mx.volcanolabs.urmovie.entities.MovieDetail;
import mx.volcanolabs.urmovie.entities.MovieFavorite;
import mx.volcanolabs.urmovie.entities.MovieVideo;
import mx.volcanolabs.urmovie.listeners.MovieDetailClickListener;
import mx.volcanolabs.urmovie.viewmodel.MovieDetailsViewModel;
import mx.volcanolabs.urmovie.viewmodel.MovieDetailsViewModelFactory;

import static mx.volcanolabs.urmovie.Constants.IMAGE_PATH;
import static mx.volcanolabs.urmovie.Constants.YOUTUBE_VIDEO_PATH;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailClickListener {
    @BindView(R.id.iv_movie)
    ImageView ivMovie;
    @BindView(R.id.tv_release_date_value)
    TextView tvReleaseDate;
    @BindView(R.id.tv_vote_average_value)
    TextView tvVoteAverage;
    @BindView(R.id.tv_plot_value)
    TextView tvPlot;
    @BindView(R.id.rv_trailers)
    RecyclerView rvTrailers;
    @BindView(R.id.rv_reviews)
    RecyclerView rvReviews;
    @BindView(R.id.vg_content)
    ViewGroup vgContent;
    @BindView(R.id.loading_indicator)
    ProgressBar progressBar;

    public static final String MOVIE_PARAM = "MOVIE_PARAM";
    private MovieDetailsViewModel viewModel;
    private MovieVideosAdapter movieVideosAdapter;
    private MovieReviewsAdapter movieReviewsAdapter;
    private MovieDetail movieDetail;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        if (getIntent() != null
                && getIntent().getExtras() != null
                && getIntent().getExtras().containsKey(MOVIE_PARAM)) {
            int movieId = (int) getIntent().getExtras().getSerializable(MOVIE_PARAM);
            MovieDetailsViewModelFactory movieDetailsViewModelFactory = new MovieDetailsViewModelFactory(getApplication(), movieId);
            viewModel = new ViewModelProvider(this, movieDetailsViewModelFactory).get(MovieDetailsViewModel.class);
            viewModel.getMovieVideosObservable().observe(this, this::onMovieDataRetrieved);
        }

        rvTrailers.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rvReviews.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        vgContent.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void setupUI(Movie movie) {
        setTitle(movie.getTitle());
        Picasso.get().load(String.format(IMAGE_PATH, movie.getPosterPath())).into(ivMovie);
        tvReleaseDate.setText(movie.getParsedReleaseDate());
        tvVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
        tvPlot.setText(movie.getOverview());
    }

    private void onMovieDataRetrieved(MovieDetail movieDetail) {
        setupUI(movieDetail.getMovie());

        if (movieDetail.getMovieVideos() != null && movieDetail.getMovieVideos().size() > 0) {
            if (movieVideosAdapter == null) {
                movieVideosAdapter = new MovieVideosAdapter(this);
                rvTrailers.setAdapter(movieVideosAdapter);
            }
            movieVideosAdapter.setData(movieDetail.getMovieVideos());
        }

        if (movieDetail.getMovieReviews() != null && movieDetail.getMovieReviews().size() > 0) {
            if (movieReviewsAdapter == null) {
                movieReviewsAdapter = new MovieReviewsAdapter();
                rvReviews.setAdapter(movieReviewsAdapter);
            }
            movieReviewsAdapter.setData(movieDetail.getMovieReviews());
        }

        if (movieDetail.getMovieFavorite() != null) {
            MenuItem item = menu.findItem(R.id.btn_favorite);
            item.setChecked(true);
            item.setIcon(R.drawable.ic_favorited);
        }

        vgContent.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        this.movieDetail = movieDetail;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.movie_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btn_favorite) {

            if (item.isChecked()) {
                viewModel.unFavoriteMovie(movieDetail.getMovieFavorite());
            } else {
                viewModel.favoriteMovie(movieDetail.getMovie().getId());
                movieDetail.setMovieFavorite(new MovieFavorite(movieDetail.getMovie().getId()));
            }

            item.setChecked(!item.isChecked());
            item.setIcon(item.isChecked() ? R.drawable.ic_favorited : R.drawable.ic_favorite);
            return true;
        }
        return false;
    }

    @Override
    public void onVideoClicked(MovieVideo movieVideo) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(String.format(YOUTUBE_VIDEO_PATH, movieVideo.getKey())));
        try {
            startActivity(webIntent);
        } catch (ActivityNotFoundException ex) {
        }
    }
}
