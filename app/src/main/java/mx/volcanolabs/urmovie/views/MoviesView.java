package mx.volcanolabs.urmovie.views;

import mx.volcanolabs.urmovie.entities.MoviesResponse;

public interface MoviesView {
    void onMoviesRetrieved(MoviesResponse moviesResponse);
    void displayErrorScreen();
}
