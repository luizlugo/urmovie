package mx.volcanolabs.urmovie.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import mx.volcanolabs.urmovie.data.DataConverter;

@Entity(tableName = "MovieResponse")
public class MoviesResponse {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("page")
    private int page;
    @TypeConverters(DataConverter.class)
    @SerializedName("results")
    private List<Movie> movies;
    @ColumnInfo(name = "total_results")
    @SerializedName("total_results")
    private int totalResults;
    @ColumnInfo(name = "total_pages")
    @SerializedName("total_pages")
    private int totalPages;
    @ColumnInfo(name = "type")
    private String type;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
