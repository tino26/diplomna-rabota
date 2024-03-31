package com.example.diplomenproekt.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.diplomenproekt.R;
import com.example.diplomenproekt.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

//        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();

        View contentView = inflater.inflate(R.layout.fragment_home, container, false);
        ListView listView = contentView.findViewById(R.id.items_container);
        return contentView;

//        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}