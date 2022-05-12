package com.corporation8793.medicinal_herb.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.corporation8793.medicinal_herb.activity.main.ChitchatActivity;
import com.corporation8793.medicinal_herb.activity.main.DictionaryActivity;
import com.corporation8793.medicinal_herb.activity.main.EventActivity;
import com.corporation8793.medicinal_herb.activity.main.FarmActivity;
import com.corporation8793.medicinal_herb.activity.main.RecommendActivity;
import com.corporation8793.medicinal_herb.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.dictionaryBtn.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), DictionaryActivity.class);
            startActivity(intent);
        });

        binding.farmBtn.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), FarmActivity.class);
            startActivity(intent);
        });

        binding.chitchatBtn.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), ChitchatActivity.class);
            startActivity(intent);
        });

        binding.recommendBtn.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), RecommendActivity.class);
            startActivity(intent);
        });

        binding.eventBtn.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), EventActivity.class);
            startActivity(intent);
        });



//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}