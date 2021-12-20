package com.example.try4;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class MoreFragment extends Fragment {
    ImageButton ipark,ihelp,icancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_more, container, false);

        ipark = view.findViewById(R.id.park);
        ihelp = view.findViewById(R.id.helpbtn);
        icancel = view.findViewById(R.id.canclebtn);

        ipark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),your_vehichle.class);
                startActivity(intent);
            }
        });

        icancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),home_page.class);
                startActivity(intent);
            }
        });
        return  view;
    }
}