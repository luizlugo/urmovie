package mx.volcanolabs.urmovie.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MovieFavorite")
public class MovieFavorite {
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "movie_id")
    private String movie;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }
}
