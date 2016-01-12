package cz.example.slavk.movieapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import cz.example.slavk.movieapp.graphic.adapter.SquareMenuAdapter;
import cz.example.slavk.movieapp.http.HttpMethodProvider;
import cz.example.slavk.movieapp.model.MovieInfoDTO;
import cz.example.slavk.movieapp.model.parser.MovieJsonDataParser;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static SquareMenuAdapter adapter;

    public MainActivityFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        new MovieDataProviderAsync().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);

        adapter = new SquareMenuAdapter(getContext());
        gridView.setAdapter(adapter);

        return rootView;
    }


    private static class MovieDataProviderAsync extends AsyncTask<Void, Void, List<MovieInfoDTO>> {

        private static final String LOG_CLASS_NAME = MovieDataProviderAsync.class.getSimpleName();

        private static final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie";
        private final String API_KEY_PARAM = "api_key";

        @Override
        protected void onPostExecute(List<MovieInfoDTO> movieInfoDTOs) {
            super.onPostExecute(movieInfoDTOs);
            adapter.setData(movieInfoDTOs);
        }

        @Override
        protected List<MovieInfoDTO> doInBackground(Void... params) {

            String preparedUrl = prepareUrl();
            Log.d(LOG_CLASS_NAME, "prepared url: " + preparedUrl);

            //send request to the server
            String jsonResponse = HttpMethodProvider.downloadJsonGET(preparedUrl);
            //return parsed data
            try {
                return MovieJsonDataParser.getMovieDataFromJson(jsonResponse);
            } catch (JSONException e) {
                Log.e(LOG_CLASS_NAME, "Unexpect JSON format", e);
                return null;
            }
        }

        private String prepareUrl(){
            Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendPath(getPathAccordingToPreferences())
                    .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIE_DB_API_KEY)
                    .build();
            return builtUri.toString();
        }

        // select a correct path according to user a movie list preference
        private String getPathAccordingToPreferences() {
            return "upcoming";
        }
    }

}
