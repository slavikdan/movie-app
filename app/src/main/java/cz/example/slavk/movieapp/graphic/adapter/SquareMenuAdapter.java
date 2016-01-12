package cz.example.slavk.movieapp.graphic.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cz.example.slavk.movieapp.R;
import cz.example.slavk.movieapp.model.MovieInfoDTO;

/**
 * Created by Daniel Slavík on 12/01/2016.
 */
public final class SquareMenuAdapter extends BaseAdapter {
    private final List<MovieInfoDTO> mItems;
    private final LayoutInflater mInflater;

    public SquareMenuAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mItems = new ArrayList<MovieInfoDTO>();
    }

    // fill data
    public void setData(List<MovieInfoDTO> data){
        mItems.clear();
        addData(data);
    }

    public void addData(List<MovieInfoDTO> data){
        mItems.addAll(data);
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public MovieInfoDTO getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mItems.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ImageView picture;
        TextView name;

        if (v == null) {
            v = mInflater.inflate(R.layout.grid_item, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
        }

        picture = (ImageView) v.getTag(R.id.picture);
        name = (TextView) v.getTag(R.id.text);

        MovieInfoDTO item = getItem(i);

        picture.setImageBitmap(item.getImage());
        name.setText(item.getTitle());

        return v;
    }
}
