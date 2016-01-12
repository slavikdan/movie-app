package cz.example.slavk.movieapp.model.parser;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import cz.example.slavk.movieapp.http.HttpMethodProvider;
import cz.example.slavk.movieapp.model.MovieInfoDTO;

/**
 * Created by Daniel Slavík on 12/01/2016.
 */
public class MovieJsonDataParser {

    private static final String LOG_TAG = MovieJsonDataParser.class.getSimpleName();

    private static final String RESULT_KEY = "results";
    private static final String POSTER_PATH_KEY = "poster_path";
    private static final String OVERVIEW_KEY = "overview";
    private static final String ID_KEY = "id";
    private static final String TITLE_KEY = "original_title";
    private static final String VOTE_AVERAGE_KEY = "vote_average";
    private static final String POPULARITY_KEY = "popularity";
    private static final String RELEASE_DATE_KEY = "release_date";
    private static final DateFormat DATE_FORMATER = new SimpleDateFormat("yyyy-MM-dd");

    public static List<MovieInfoDTO> getMovieDataFromJson(String json) throws JSONException {
        List<MovieInfoDTO> movieList = new ArrayList<>();

        JSONArray results = new JSONObject(json).getJSONArray(RESULT_KEY);
        for (int i = 0; i < results.length(); i++) {
            movieList.add(parseMovie(results.getJSONObject(i)));
        }

        return movieList;
    }

    private static MovieInfoDTO parseMovie(JSONObject jsonMovie) throws JSONException {

        Date releaseDate = null;
        try {
            releaseDate = DATE_FORMATER.parse(jsonMovie.getString(RELEASE_DATE_KEY));
        } catch (ParseException e) {
            Log.e(LOG_TAG,"Unable to parse release date", e);
        }

        return new MovieInfoDTO(
                jsonMovie.getString(POSTER_PATH_KEY),
                jsonMovie.getString(OVERVIEW_KEY),
                jsonMovie.getLong(ID_KEY),
                jsonMovie.getString(TITLE_KEY),
                jsonMovie.getDouble(POPULARITY_KEY),
                jsonMovie.getDouble(VOTE_AVERAGE_KEY),
                releaseDate
                );
    }
}
