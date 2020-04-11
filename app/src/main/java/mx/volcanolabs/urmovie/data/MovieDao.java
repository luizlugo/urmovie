package mx.volcanolabs.urmovie.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import mx.volcanolabs.urmovie.entities.Movie;

@Dao
public interface MovieDao {
    @Query("SELECT * from Movie")
    LiveData<List<Movie>> getMovies();

    @Insert
    void addMovies(Movie... movies);
}
