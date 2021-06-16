package com.omninos.nani.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omninos.nani.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutTwoFragment extends Fragment {


    public AboutTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_two, container, false);
    }

}
