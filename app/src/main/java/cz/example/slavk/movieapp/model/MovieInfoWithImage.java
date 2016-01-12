package cz.example.slavk.movieapp.model;

import android.graphics.Bitmap;

import cz.example.slavk.movieapp.http.HttpMethodProvider;

/**
 * A wrapper for MovieInfoDTO with the posture image downloaded
 * Created by Daniel Slavík on 12/01/2016.
 */
public class MovieInfoWithImage {

    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w370";

    private Bitmap image;
    private MovieInfoDTO movieInfo;

    public MovieInfoWithImage(MovieInfoDTO movieInfo) {
        this.image = HttpMethodProvider.downloadImageGET(IMAGE_BASE_URL + movieInfo.getPosterPath());
        this.movieInfo = movieInfo;
    }

    public Bitmap getImage() {
        return image;
    }

    public MovieInfoDTO getMovieInfo() {
        return movieInfo;
    }

    @Override
    public String toString() {
        return "MovieInfoWithImage{" +
                "image=" + image +
                ", movieInfo=" + movieInfo +
                '}';
    }
}
