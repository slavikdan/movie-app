package cz.example.slavk.movieapp.model.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.example.slavk.movieapp.http.HttpMethodProvider;
import cz.example.slavk.movieapp.model.MovieInfoDTO;

/**
 * Created by Daniel Slavík on 12/01/2016.
 */
public class MovieJsonDataParser {

    private static final String RESULT_KEY = "results";
    private static final String POSTER_PATH_KEY = "poster_path";
    private static final String OVERVIEW_KEY = "overview";
    private static final String ID_KEY = "id";
    private static final String TITLE_KEY = "original_title";
    private static final String VOTE_AVERAGE_KEY = "vote_average";
    private static final String POPULARITY_KEY = "popularity";

    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w370";

    public static List<MovieInfoDTO> getMovieDataFromJson(String json) throws JSONException {
        List<MovieInfoDTO> movieList = new ArrayList<>();

        JSONArray results = new JSONObject(json).getJSONArray(RESULT_KEY);
        for (int i = 0; i < results.length(); i++) {
            movieList.add(parseMovie(results.getJSONObject(i)));
        }

        return movieList;
    }

    private static MovieInfoDTO parseMovie(JSONObject jsonMovie) throws JSONException {

        String url = IMAGE_BASE_URL + jsonMovie.getString(POSTER_PATH_KEY);
        return new MovieInfoDTO(
                url,
                jsonMovie.getString(OVERVIEW_KEY),
                jsonMovie.getLong(ID_KEY),
                jsonMovie.getString(TITLE_KEY),
                jsonMovie.getDouble(POPULARITY_KEY),
                jsonMovie.getDouble(VOTE_AVERAGE_KEY),
                HttpMethodProvider.downloadImageGET(url)
                );
    }
}
