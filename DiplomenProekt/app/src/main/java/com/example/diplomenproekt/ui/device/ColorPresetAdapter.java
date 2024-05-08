package com.example.diplomenproekt.ui.device;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

import com.example.diplomenproekt.R;

public class ColorPresetAdapter extends BaseAdapter {
    Context context;
    List<ColorInt> colors[];
    LayoutInflater inflater;

    public ColorPresetAdapter(Context applicationContext, List<ColorInt> colors) {
        this.context = applicationContext;
        this.colors = new List[]{colors};
        inflater = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return colors.length;
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.color_preset, null);
        ImageView preset_item = (ImageView) view.findViewById(R.id.preset_button);
        preset_item.setBackgroundColor(colors[i]);
        return view;
    }
}
