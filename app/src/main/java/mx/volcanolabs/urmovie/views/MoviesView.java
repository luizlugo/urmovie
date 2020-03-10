package mx.volcanolabs.urmovie.views;

import java.util.List;

import mx.volcanolabs.urmovie.entities.Movie;

public interface MoviesView {
    void onMoviesRetrieved(List<Movie> movies);
}
