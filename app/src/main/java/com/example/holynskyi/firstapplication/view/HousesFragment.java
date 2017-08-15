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

public class HousesFragment extends Fragment {

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
        return inflater.inflate(R.layout.fragment_houses, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerView = (RecyclerView) container.findViewById(R.id.recyclerViewMainCars);
        Log.d("RV house"," id = "+recyclerView.toString());
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}
