package com.example.diplomenproekt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ItemListAdapter extends BaseAdapter {
    Context context;
    private final List<String> titles;
    LayoutInflater inflater;

    public ItemListAdapter(Context applicationContext, List<String> titles) {
        super();
        this.context = context;
        this.titles = titles;
        inflater = (LayoutInflater.from(applicationContext));
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
    public View getView(int position, View card_view, ViewGroup parent) {
        card_view = inflater.inflate(R.layout.item_card_view, null);
        TextView textView = (TextView) card_view.findViewById(R.id.device_name);
        textView.setText(titles.get(position));
        return card_view;
    }
}
