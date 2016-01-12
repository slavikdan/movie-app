package cz.example.slavk.movieapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import cz.example.slavk.movieapp.model.MovieInfoDTO;
import cz.example.slavk.movieapp.model.MovieInfoWithImage;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();

    private static MovieInfoDTO movieInfo;
    private static TextView mTitle;
    private static TextView mReleaseDate;
    private static TextView mAverageVote;
    private static TextView mOverview;
    private static ImageView mPoster;
    private final static String DOUBLE_FORMAT_PATTERN = "%1$,.2f";
    //TODO move format to settings
    private static final DateFormat DATE_FORMATER = new SimpleDateFormat("yyyy/MM/dd");

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        mPoster = (ImageView) rootView.findViewById(R.id.movie_poster_view);
        mTitle = (TextView) rootView.findViewById(R.id.movie_title);
        mReleaseDate = (TextView) rootView.findViewById(R.id.movie_release_date);
        mAverageVote = (TextView) rootView.findViewById(R.id.movie_average_vote);
        mOverview = (TextView) rootView.findViewById(R.id.movie_overview);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(MovieInfoDTO.class.getSimpleName())) {
            movieInfo = (MovieInfoDTO) getActivity().getIntent().getSerializableExtra(MovieInfoDTO.class.getSimpleName());
            new AsyncDataResolver().execute(movieInfo);
            Log.d(LOG_TAG, "Extra data: " + movieInfo);
            mTitle.setText(movieInfo.getTitle());
            mAverageVote.setText(String.format(DOUBLE_FORMAT_PATTERN, movieInfo.getVoteAverage()));
            mReleaseDate.setText(DATE_FORMATER.format(movieInfo.getReleaseDate()));
            mOverview.setText(movieInfo.getOverview());
        }

        return rootView;
    }

    private static class AsyncDataResolver extends AsyncTask<MovieInfoDTO, Void, MovieInfoWithImage>{

        @Override
        protected void onPostExecute(MovieInfoWithImage movieInfoWithImage) {
            super.onPostExecute(movieInfoWithImage);
            mPoster.setImageBitmap(movieInfoWithImage.getImage());
        }

        @Override
        protected MovieInfoWithImage doInBackground(MovieInfoDTO... params) {
            return new MovieInfoWithImage(params[0]);
        }
    }
}
