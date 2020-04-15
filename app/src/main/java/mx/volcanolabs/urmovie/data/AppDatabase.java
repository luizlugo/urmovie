package mx.volcanolabs.urmovie.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import mx.volcanolabs.urmovie.entities.Movie;
import mx.volcanolabs.urmovie.entities.MovieFavorite;
import mx.volcanolabs.urmovie.entities.MoviesResponse;

@Database(entities = {Movie.class, MovieFavorite.class, MoviesResponse.class}, version = 2, exportSchema = false)
@TypeConverters(DataConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "urmovie";
    private static final Object LOCK = new Object();
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
            }
        }
        return sInstance;
    }

    public abstract MovieDao getMovieDao();

    public abstract MovieFavoriteDao getMovieFavoriteDao();
}
