package com.example.holynskyi.firstapplication.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.holynskyi.firstapplication.R;

/**
 * Created by holynskyi on 15.08.17.
 */

public class CarsFragment extends Fragment {

    RecyclerView recyclerView;
    ViewGroup container;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.container = container;
        View view = inflater.inflate(R.layout.fragment_cars, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewMainCars);
        Log.d("RV car"," id = "+recyclerView.toString());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
      //  recyclerView = (RecyclerView) container.findViewById(R.id.recyclerViewMainCars);

    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

}

