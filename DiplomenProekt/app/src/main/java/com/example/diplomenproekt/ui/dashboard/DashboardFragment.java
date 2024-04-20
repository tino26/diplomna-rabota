package com.example.diplomenproekt.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.diplomenproekt.R;
import com.example.diplomenproekt.authentication.UserSessionManager;
import com.example.diplomenproekt.databinding.FragmentDashboardBinding;
import com.example.diplomenproekt.bluetooth.FindBLEdevice;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private FindBLEdevice findBLEdevice;

    UserSessionManager session;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        session = new UserSessionManager(getContext());
        findBLEdevice = new FindBLEdevice();
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button scan_button = (Button) root.findViewById(R.id.scan_button);

        scan_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                session.logoutUser();
                findBLEdevice.scanLeDevice(getContext());
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}