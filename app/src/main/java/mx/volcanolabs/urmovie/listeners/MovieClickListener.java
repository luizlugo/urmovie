package mx.volcanolabs.urmovie.listeners;

import mx.volcanolabs.urmovie.entities.Movie;

public interface MovieClickListener {
    void onMovieClicked(Movie movie);
    void onLoadMoreItemsClicked();
}
