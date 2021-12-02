package com.example.foodorder2.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodorder2.Activity.SignInActivity;
import com.example.foodorder2.Activity.SignUpActivity;
import com.example.foodorder2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class OnBoarding3Fragment extends Fragment {

FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_on_boarding3, container, false);

        fab=view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SignUpActivity.class));
                getActivity().finish();
            }
        });
        return view;
    }
}