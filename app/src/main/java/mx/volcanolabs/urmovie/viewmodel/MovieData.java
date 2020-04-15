package mx.volcanolabs.urmovie.viewmodel;

import java.util.ArrayList;
import java.util.List;

import mx.volcanolabs.urmovie.entities.Movie;

public class MovieData {
    private int totalPages;
    private List<Movie> movies = new ArrayList<>();
    private int currentPage;

    public MovieData() {}

    public MovieData(int totalPages, List<Movie> movies, int currentPage) {
        this.totalPages = totalPages;
        this.movies = movies;
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
