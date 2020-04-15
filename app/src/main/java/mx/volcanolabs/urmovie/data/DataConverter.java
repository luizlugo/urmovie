package mx.volcanolabs.urmovie.data;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import mx.volcanolabs.urmovie.entities.Movie;

public class DataConverter implements Serializable {
    @TypeConverter
    public String fromMoviesList(List<Movie> movies) {
        if (movies == null) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Movie>>() {}.getType();
        return gson.toJson(movies, type);
    }

    @TypeConverter
    public List<Movie> toMoviesList(String moviesListString) {
        if (moviesListString == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Movie>>() {}.getType();
        return gson.fromJson(moviesListString, type);
    }
}
