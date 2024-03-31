package com.example.diplomenproekt.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.diplomenproekt.ItemListAdapter;
import com.example.diplomenproekt.R;
import com.example.diplomenproekt.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        View contentView = inflater.inflate(R.layout.fragment_home, container, false);
        GridView listView = root.findViewById(R.id.items_container);
        List<String> list = new ArrayList<>();
        for(int i=0;i<4;i++)
            list.add("Item "+i);

        ItemListAdapter listAdapter = new ItemListAdapter(getContext(), list);
        listView.setAdapter(listAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}