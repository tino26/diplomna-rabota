package com.example.diplomenproekt.bluetooth;

import java.util.ArrayList;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.example.diplomenproekt.MainActivity;
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
//            viewHolder.deviceAddress = (TextView) view
//                    .findViewById(R.id.new_device_name);
            viewHolder.deviceName = (TextView) view
                    .findViewById(R.id.new_device_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        BluetoothDevice device = mLeDevices.get(i);
        if (ActivityCompat.checkSelfPermission(mActivity.getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 0);
        }

        final String deviceName = device.getName();
		if (deviceName != null && deviceName.length() > 0) {
            viewHolder.deviceName.setText(deviceName);
        }
//		else
//			viewHolder.deviceName.setText(R.string.unknown_device);
//		viewHolder.deviceAddress.setText(device.getAddress());

		return view;
	}

	class ViewHolder {
		TextView deviceName;
		TextView deviceAddress;
	}
}
