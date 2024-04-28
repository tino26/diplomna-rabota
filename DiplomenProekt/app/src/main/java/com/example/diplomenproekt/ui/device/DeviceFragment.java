package com.example.diplomenproekt.ui.device;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diplomenproekt.R;
import com.example.diplomenproekt.databinding.FragmentDeviceBinding;
import com.rtugeek.android.colorseekbar.ColorSeekBar;
import com.rtugeek.android.colorseekbar.OnColorChangeListener;

public class DeviceFragment extends Fragment {

    private DeviceViewModel mViewModel;
    private FragmentDeviceBinding binding;


    public static DeviceFragment newInstance() {
        return new DeviceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentDeviceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ColorSeekBar colorSeekBar = root.findViewById(R.id.color_seek_bar);

        colorSeekBar.setOnColorChangeListener(new OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int progress, int color) {
                int currentRed = Color.red(color); 
                int currentGreen = Color.green(color);
                int currentBlue = Color.blue(color);
            }
        });

        return root;
    }


//    private boolean connectToNetwork(String networkSSID, String networkPass, String networkCapabilities) {
//        WifiManager wifiManager = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
//        WifiNetworkSpecifier wifiConfig = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
//            wifiConfig = new WifiNetworkSpecifier.Builder()
//                    .setSsid(networkSSID)
//                    .setWpa2Passphrase(networkPass)
//                    .build();
//        }
//
//        wifiConfig.SSID = String.format("\"%s\"", networkSSID);
//
//        if (!wifiManager.isWifiEnabled()) {
//            wifiManager.setWifiEnabled(true);
//        }
//
//        if (networkCapabilities.toUpperCase().contains("WEP")) { // WEP Network.
//            Toast.makeText(this, "WEP Network", Toast.LENGTH_SHORT).show();
//
//            wifiConfig.wepKeys[0] = String.format("\"%s\"", networkPass);
//            ;
//            wifiConfig.wepTxKeyIndex = 0;
//            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
//            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
//        } else if (networkCapabilities.toUpperCase().contains("WPA")) { // WPA Network
//            Toast.makeText(this, "WPA Network", Toast.LENGTH_SHORT).show();
//            wifiConfig.preSharedKey = String.format("\"%s\"", networkPass);
//            ;
//        } else { // OPEN Network.
//            Toast.makeText(this, "OPEN Network", Toast.LENGTH_SHORT).show();
//            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
//        }
//
//
//        int netId = wifiManager.addNetwork(wifiConfig);//
//        wifiManager.disconnect();
//        wifiManager.enableNetwork(netId, true);
//        return wifiManager.reconnect();
//
//    }

}