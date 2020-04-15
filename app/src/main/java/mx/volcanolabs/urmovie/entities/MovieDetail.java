package mx.volcanolabs.urmovie.entities;

import java.util.List;

public class MovieDetail {
    private Movie movie;
    private List<MovieVideo> movieVideos;
    private List<MovieReview> movieReviews;
    private MovieFavorite movieFavorite;

    public MovieDetail() {}

    public MovieDetail(Movie movie, MovieFavorite movieFavorite) {
        this.movie = movie;
        this.movieFavorite = movieFavorite;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<MovieVideo> getMovieVideos() {
        return movieVideos;
    }

    public void setMovieVideos(List<MovieVideo> movieVideos) {
        this.movieVideos = movieVideos;
    }

    public List<MovieReview> getMovieReviews() {
        return movieReviews;
    }

    public void setMovieReviews(List<MovieReview> movieReviews) {
        this.movieReviews = movieReviews;
    }

    public MovieFavorite getMovieFavorite() {
        return movieFavorite;
    }

    public void setMovieFavorite(MovieFavorite movieFavorite) {
        this.movieFavorite = movieFavorite;
    }
}
