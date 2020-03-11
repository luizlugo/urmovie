package mx.volcanolabs.urmovie;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.volcanolabs.urmovie.entities.Movie;

import static mx.volcanolabs.urmovie.Constants.IMAGE_PATH;

public class MovieDetailActivity extends AppCompatActivity {
    @BindView(R.id.iv_movie)
    ImageView ivMovie;
    @BindView(R.id.tv_release_date_value)
    TextView tvReleaseDate;
    @BindView(R.id.tv_vote_average_value)
    TextView tvVoteAverage;
    @BindView(R.id.tv_plot_value)
    TextView tvPlot;

    public static final String MOVIE_PARAM = "MOVIE_PARAM";
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        if (getIntent() != null
                && getIntent().getExtras() != null
                && getIntent().getExtras().containsKey(MOVIE_PARAM)) {
            movie = (Movie)getIntent().getExtras().getSerializable(MOVIE_PARAM);
            setupUI();
        }
    }

    private void setupUI() {
        setTitle(movie.getTitle());
        Picasso.get().load(String.format(IMAGE_PATH, movie.getPosterPath())).into(ivMovie);
        tvReleaseDate.setText(movie.getParsedReleaseDate());
        tvVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
        tvPlot.setText(movie.getOverview());
    }
}
