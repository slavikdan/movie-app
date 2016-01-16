package cz.example.slavk.movieapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferencesFactory;

import cz.example.slavk.movieapp.graphic.adapter.SquareMenuAdapter;
import cz.example.slavk.movieapp.http.HttpMethodProvider;
import cz.example.slavk.movieapp.model.MovieInfoDTO;
import cz.example.slavk.movieapp.model.MovieInfoWithImage;
import cz.example.slavk.movieapp.model.parser.MovieJsonDataParser;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String TAG_LOG_CLASS = MainActivityFragment.class.getSimpleName();
    private static SquareMenuAdapter adapter;

    public MainActivityFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Log.d(TAG_LOG_CLASS,sharedPref.getAll().toString());
        String preferredOrder = sharedPref.getString(
                getString(R.string.pref_order_key),
                getString(R.string.pref_order_default_value));

        new MovieDataProviderAsync().execute(preferredOrder);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);

        adapter = new SquareMenuAdapter(getContext());

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieInfoWithImage data = (MovieInfoWithImage) parent.getItemAtPosition(position);
                Intent detailIntent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(MovieInfoDTO.class.getSimpleName(), data.getMovieInfo());
                startActivity(detailIntent);
            }
        });

        return rootView;
    }

    private static class MovieDataProviderAsync extends AsyncTask<String, Void, List<MovieInfoDTO>> {

        private static final String LOG_CLASS_NAME = MovieDataProviderAsync.class.getSimpleName();

        private static final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie";
        private final String API_KEY_PARAM = "api_key";

        @Override
        protected void onPostExecute(List<MovieInfoDTO> movieInfoDTOs) {
            super.onPostExecute(movieInfoDTOs);
            adapter.setData(movieInfoDTOs);
        }

        @Override
        protected List<MovieInfoDTO> doInBackground(String... params) {

            String preparedUrl = prepareUrl(params[0]);
            Log.d(LOG_CLASS_NAME, "prepared url: " + preparedUrl);

            //send request to the server
            String jsonResponse = HttpMethodProvider.downloadJsonGET(preparedUrl);
            //return parsed data
            List<MovieInfoWithImage> result = new ArrayList<>();
            try {
                return  MovieJsonDataParser.getMovieDataFromJson(jsonResponse);
            } catch (JSONException e) {
                Log.e(LOG_CLASS_NAME, "unexpected JSON format", e);
                return null;
            }
        }

        private String prepareUrl(String urlpath){
            Log.d(TAG_LOG_CLASS, "preferred order: " + urlpath);
            Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendPath(urlpath)
                    .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIE_DB_API_KEY)
                    .build();
            return builtUri.toString();
        }

    }

}
