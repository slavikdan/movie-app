package cz.example.slavk.movieapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import cz.example.slavk.movieapp.graphic.adapter.SquareMenuAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);

        SquareMenuAdapter adapter = new SquareMenuAdapter(getActivity());

        //TODO add real data to adapter
        adapter.setData(getDataForMenu());

        gridView.setAdapter(adapter);


        return rootView;
    }

    private Map<String, Integer> getDataForMenu() {
        Map<String,Integer> data = new LinkedHashMap<>();
        data.put("test 1", R.mipmap.ic_launcher);
        data.put("test 2", R.mipmap.ic_launcher);
        data.put("test 3", R.mipmap.ic_launcher);
        data.put("test 4", R.mipmap.ic_launcher);
        data.put("test 5", R.mipmap.ic_launcher);
        data.put("test 6", R.mipmap.ic_launcher);
        data.put("test 7", R.mipmap.ic_launcher);
        data.put("test 8", R.mipmap.ic_launcher);
        data.put("test 9", R.mipmap.ic_launcher);
        data.put("test 10", R.mipmap.ic_launcher);
        data.put("test 11", R.mipmap.ic_launcher);
        data.put("test 12", R.mipmap.ic_launcher);
        return data;
    }
}
