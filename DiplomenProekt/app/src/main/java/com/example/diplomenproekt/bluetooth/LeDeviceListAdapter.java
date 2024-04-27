package com.example.diplomenproekt.bluetooth;

import static android.content.Context.RECEIVER_EXPORTED;
import static androidx.core.content.ContextCompat.registerReceiver;

import java.util.ArrayList;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.DownloadManager;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.example.diplomenproekt.DeviceControlActivity;
import com.example.diplomenproekt.R;

public class LeDeviceListAdapter extends BaseAdapter {
    // Adapter for holding devices found through scanning.

    private ArrayList<BluetoothDevice> mLeDevices;
    private LayoutInflater mInflator;
    private Activity mActivity;

    public LeDeviceListAdapter(Activity activity) {
        super();
        mActivity = activity;
        mLeDevices = new ArrayList<BluetoothDevice>();
        mInflator = mActivity.getLayoutInflater();
    }

    public void addDevice(BluetoothDevice device) {
        if (!mLeDevices.contains(device)) {
            mLeDevices.add(device);
        }
    }

    public BluetoothDevice getDevice(int position) {
        return mLeDevices.get(position);
    }

    public void clear() {
        mLeDevices.clear();
    }

    public int getCount() {
        return mLeDevices.size();
    }

    public Object getItem(int i) {
        return mLeDevices.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        // General ListView optimization code.
        if (view == null) {
            view = mInflator.inflate(R.layout.discovered_new_device_view, null);
            viewHolder = new ViewHolder();
            viewHolder.pairButton = (Button) view.findViewById(R.id.pair_button);
            viewHolder.deviceName = (TextView) view.findViewById(R.id.new_device_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        BluetoothDevice device = mLeDevices.get(i);
        if (ActivityCompat.checkSelfPermission(mActivity.getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 0);
        }

        final String deviceName = device.getName();
        final String deviceAddress = device.getAddress();

		if (deviceName != null && deviceName.length() > 0) {
            viewHolder.deviceName.setText(deviceName);
        } else {
            viewHolder.deviceName.setText(R.string.unknown_device);
        }

        viewHolder.pairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DeviceControlActivity.class);
                intent.putExtra("deviceName", deviceName);
                intent.putExtra("deviceAddress", deviceAddress);
                view.getContext().startActivity(intent);
            }
        });

		return view;
	}

	class ViewHolder {
		TextView deviceName;
        Button pairButton;
	}
}
