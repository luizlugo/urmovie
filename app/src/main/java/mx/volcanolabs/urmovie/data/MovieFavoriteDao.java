package mx.volcanolabs.urmovie.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import mx.volcanolabs.urmovie.entities.FavoriteMovie;
import mx.volcanolabs.urmovie.entities.MovieFavorite;

@Dao
public interface MovieFavoriteDao {
    @Query("SELECT * FROM MovieFavorite")
    LiveData<List<FavoriteMovie>> getFavoriteMovies();

    @Insert
    void favoriteMovie(MovieFavorite movieFavorite);

    @Query("SELECT * FROM MovieFavorite WHERE movie_id=:id")
    MovieFavorite getMovieFavoriteForMovie(int id);

    @Delete
    void unFavoriteMovie(MovieFavorite movieFavorite);
}
