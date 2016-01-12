package cz.example.slavk.movieapp.graphic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cz.example.slavk.movieapp.R;

/**
 * Created by Daniel Slavík on 12/01/2016.
 */
public final class SquareMenuAdapter extends BaseAdapter {
    private final List<Item> mItems = new ArrayList<Item>();
    private final LayoutInflater mInflater;

    public SquareMenuAdapter(Context context) {
        mInflater = LayoutInflater.from(context);

        /*mItems.add(new Item("Red",       R.drawable.red));
        mItems.add(new Item("Magenta",   R.drawable.magenta));
        mItems.add(new Item("Dark Gray", R.drawable.dark_gray));
        mItems.add(new Item("Gray",      R.drawable.gray));
        mItems.add(new Item("Green",     R.drawable.green));
        mItems.add(new Item("Cyan",      R.drawable.cyan));*/
    }

    // fill data
    public void setData(Map<String,Integer> data){
        mItems.clear();
        for (String key: data.keySet()) {
            mItems.add(new Item(key,data.get(key)));
        }
    }

    public void addData(Map<String,Integer> data){
        for (String key: data.keySet()) {
            mItems.add(new Item(key,data.get(key)));
        }
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mItems.get(i).drawableId;
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

        Item item = getItem(i);

        picture.setImageResource(item.drawableId);
        name.setText(item.name);

        return v;
    }

    private static class Item {
        public final String name;
        public final int drawableId;

        Item(String name, int drawableId) {
            this.name = name;
            this.drawableId = drawableId;
        }
    }
}
