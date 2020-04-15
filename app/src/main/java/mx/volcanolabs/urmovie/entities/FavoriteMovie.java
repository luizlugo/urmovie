package mx.volcanolabs.urmovie.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

public class FavoriteMovie {
    @Embedded public MovieFavorite movieFavorite;
    @Relation(
            parentColumn = "movie_id",
            entityColumn = "id"
    )
    public Movie movie;
}
