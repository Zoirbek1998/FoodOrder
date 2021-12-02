package com.example.foodorder2.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodorder2.Activity.SignInActivity;
import com.example.foodorder2.Activity.SignUpActivity;
import com.example.foodorder2.R;


public class OnBoarding1Fragment extends Fragment {

TextView txtSkip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

     View view = inflater.inflate(R.layout.fragment_on_boarding1, container, false);

     txtSkip=view.findViewById(R.id.skip);
     txtSkip.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             startActivity(new Intent(getActivity(), SignUpActivity.class));
             getActivity().finish();
         }
     });

     return view;
    }
}