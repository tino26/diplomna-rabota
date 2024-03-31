package com.example.diplomenproekt;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ItemListAdapter extends BaseAdapter {
    private final List<String> titles;

    public ItemListAdapter(List<String> titles) {
        super();
        this.titles = titles;
    }
    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return titles.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(convertView.getContext());
        textView.setText(titles.get(position));
        return textView;
    }
}
