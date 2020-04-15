package mx.volcanolabs.urmovie.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieVideoResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private List<MovieVideo> videos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<MovieVideo> getVideos() {
        return videos;
    }

    public void setVideos(List<MovieVideo> videos) {
        this.videos = videos;
    }
}
