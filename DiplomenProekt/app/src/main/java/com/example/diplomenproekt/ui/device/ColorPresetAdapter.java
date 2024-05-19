package com.example.diplomenproekt.ui.device;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.content.Intent;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

import com.example.diplomenproekt.DeviceControlActivity;
import com.example.diplomenproekt.R;
import com.example.diplomenproekt.bluetooth.BluetoothLeService;

public class ColorPresetAdapter extends BaseAdapter {
    private ArrayList<Integer> colors;
    private LayoutInflater mInflator;
    private Activity mActivity;
    private BluetoothLeService mBluetoothLeService;
    private BluetoothGattCharacteristic mColorCharacteristic;
    private View.OnClickListener presetColorClick;

    public ColorPresetAdapter(Context applicationContext, ArrayList<Integer> colors, View.OnClickListener presetColorClick) {
        super();
        this.colors = colors;
//        this.mBluetoothLeService = mBluetoothLeService;
//        this.mColorCharacteristic = mColorCharacteristic;
        this.presetColorClick = presetColorClick;
        mInflator = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return colors.size();
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
        view = mInflator.inflate(R.layout.color_preset, null);
        ImageView preset_item = (ImageView) view.findViewById(R.id.preset_button);
        preset_item.setBackgroundColor(colors.get(i));
        preset_item.setOnClickListener(presetColorClick);
        return view;
    }
}