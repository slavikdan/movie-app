package cz.example.slavk.movieapp.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Class for transfering information about a movie
 * Created by Daniel Slavík on 12/01/2016.
 */
public class MovieInfoDTO implements Serializable {

    private String posterPath;
    private String overview;
    private Long id;
    private String title;
    private double popularity;
    private double voteAverage;

    public MovieInfoDTO(String posterPath, String overview, Long id, String title, double popularity, double voteAverage) {
        this.posterPath = posterPath;
        this.overview = overview;
        this.id = id;
        this.title = title;
        this.popularity = popularity;
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPopularity() {
        return popularity;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    @Override
    public String toString() {
        return "MovieInfoDTO{" +
                "posterPath='" + posterPath + '\'' +
                ", overview='" + overview + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", popularity=" + popularity +
                ", voteAverage=" + voteAverage +
                '}';
    }
}
