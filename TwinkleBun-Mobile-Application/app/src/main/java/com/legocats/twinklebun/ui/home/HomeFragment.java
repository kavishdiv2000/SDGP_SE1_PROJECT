package com.legocats.twinklebun.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.legocats.twinklebun.MultiplicationList;
import com.legocats.twinklebun.PastPaperActivity;
import com.legocats.twinklebun.ReviseHubMain;
import com.legocats.twinklebun.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final CardView cardSchol = binding.cardScholarship;
        final CardView cardMul = binding.cardMultiply;
        final CardView cardRevise = binding.cardRevisehub;


        cardSchol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardSchol.setCardElevation(1);
                Intent intent = new Intent(getActivity(), PastPaperActivity.class);
                startActivity(intent);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cardSchol.setCardElevation(8);
                    }
                },1000);

            }
        });


        cardMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardMul.setCardElevation(1);
                Intent intent = new Intent(getActivity(), MultiplicationList.class);
                startActivity(intent);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cardMul.setCardElevation(8);
                    }
                },1000);

            }
        });

        cardRevise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardRevise.setCardElevation(1);
                Intent intent = new Intent(getActivity(), ReviseHubMain.class);
                startActivity(intent);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cardRevise.setCardElevation(8);
                    }
                },1000);

            }
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