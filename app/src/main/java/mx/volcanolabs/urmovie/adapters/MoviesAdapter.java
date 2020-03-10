package mx.volcanolabs.urmovie.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mx.volcanolabs.urmovie.R;
import mx.volcanolabs.urmovie.entities.Movie;

import static mx.volcanolabs.urmovie.Constants.IMAGE_PATH;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    List<Movie> movies;
    private final int LOAD_MORE = 1;
    private final int MOVIE_ITEM = 0;

    public void setData(List<Movie> movies) {
        this.movies = movies;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == LOAD_MORE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more_list_item, parent, false);
        } else  {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        }

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        if (position < movies.size()) {
            holder.bind(movies.get(position));
        } else {
            // Define click listener to retrieve more movies
        }
    }

    @Override
    public int getItemCount() {
        if (movies != null && movies.size() > 0) {
            return movies.size() + 1;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == movies.size() + 1) {
            return LOAD_MORE;
        }
        return MOVIE_ITEM;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        FrameLayout vgLoadMore;
        ImageView ivMovie;

        public MovieViewHolder(View view) {
            super(view);
            this.ivMovie = view.findViewById(R.id.iv_movie);
            this.vgLoadMore = view.findViewById(R.id.vg_load_more);
        }

        public void bind(Movie movie) {
            Picasso.get().load(String.format(IMAGE_PATH, movie.getPosterPath())).into(ivMovie);
        }
    }
}
