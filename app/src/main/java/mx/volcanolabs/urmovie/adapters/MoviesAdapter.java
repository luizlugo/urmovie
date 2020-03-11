package mx.volcanolabs.urmovie.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mx.volcanolabs.urmovie.R;
import mx.volcanolabs.urmovie.entities.Movie;
import mx.volcanolabs.urmovie.listeners.MovieClickListener;

import static mx.volcanolabs.urmovie.Constants.IMAGE_PATH;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    List<Movie> movies;
    private MovieClickListener listener;
    private int currentPage = 0;
    private int totalPages = 0;

    public MoviesAdapter(MovieClickListener listener, int totalPages) {
        this.listener = listener;
        this.totalPages = totalPages;
    }

    public void setData(List<Movie> movies, int currentPage) {
        this.movies = movies;
        this.currentPage = currentPage;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        if (position < movies.size()) {
            holder.bind(movies.get(position));
        } else {
            // Define click listener to retrieve more movies
            holder.bind();
        }
    }

    @Override
    public int getItemCount() {
        if (movies != null && movies.size() > 0) {
            if (currentPage < totalPages) {
                return movies.size() + 1;
            } else if (currentPage == totalPages) {
                return movies.size();
            }
        }

        return 0;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvLoadMore;
        ImageView ivMovie;
        boolean isMovie;

        public MovieViewHolder(View view) {
            super(view);
            this.ivMovie = view.findViewById(R.id.iv_movie);
            this.tvLoadMore = view.findViewById(R.id.tv_load_more);
            view.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            Picasso.get().load(String.format(IMAGE_PATH, movie.getPosterPath())).into(ivMovie);
            ivMovie.setVisibility(View.VISIBLE);
            tvLoadMore.setVisibility(View.GONE);
            isMovie = true;
        }

        public void bind() {
            ivMovie.setVisibility(View.GONE);
            tvLoadMore.setVisibility(View.VISIBLE);
            isMovie = false;
        }

        @Override
        public void onClick(View v) {
            if (isMovie) {
                listener.onMovieClicked(movies.get(getAdapterPosition()));
            } else {
                listener.onLoadMoreItemsClicked();
            }
        }
    }
}
