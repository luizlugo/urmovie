package mx.volcanolabs.urmovie.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import mx.volcanolabs.urmovie.entities.Movie;
import mx.volcanolabs.urmovie.entities.MoviesResponse;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM Movie WHERE id=:id")
    Movie getMovieById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovieResponse(MoviesResponse moviesResponse);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<Movie> movies);

    @Query("SELECT * FROM MovieResponse where page=:page_number and type=:type")
    MoviesResponse getMoviesForPageAndType(int page_number, String type);
}
