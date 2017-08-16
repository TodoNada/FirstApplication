package com.example.holynskyi.firstapplication.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.holynskyi.firstapplication.R;
import com.example.holynskyi.firstapplication.adapters.CarAdapter;
import com.example.holynskyi.firstapplication.db.DatabaseStructure;
import com.example.holynskyi.firstapplication.db.LocalDbStorage;
import com.example.holynskyi.firstapplication.dialogs.OnCarItemSelectedListener;
import com.example.holynskyi.firstapplication.models.Car;
import com.example.holynskyi.firstapplication.models.Cars;

/**
 * Created by holynskyi on 15.08.17.
 */

public class CarsFragment extends Fragment implements OnCarItemSelectedListener {

    private RecyclerView recyclerView;

    private CarAdapter carAdapter;
    private Cars cars;
    private LocalDbStorage localDbStorage;
    private long userId;

    ViewGroup container;

    public CarsFragment() {
    }

    @SuppressLint("ValidFragment")
    public CarsFragment(long userId) {
        this.userId = userId;
        cars = new Cars();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cars, container, false);
        initRecyclerView(view);
        return view;
    }

    public void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewMainCars);

        //inititalize local db
        localDbStorage = new LocalDbStorage(getContext());

        if (getCarsFromDb(userId, cars)) {
            carAdapter = new CarAdapter(cars);
            if (carAdapter != null) {
                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(carAdapter);
                carAdapter.setOnCarItemSelectedListener(this);
            }
        } else {
            Log.d("FRAGMENT CAR", "no cars are in db, userId = " + userId);
        }
    }

    // for cars getting from DB
    private boolean getCarsFromDb(long userLocalId, Cars someCars) {
        localDbStorage.reopen();
        someCars.setDb(localDbStorage.getDb());
        boolean loaded = someCars.loadFromDb(DatabaseStructure.columns.car.userId + " = ?", new String[]{"" + userLocalId}, 0);
        localDbStorage.close();
        return loaded;
    }


    protected boolean notifyDataWasRefresh(long userIdToRefresh) {

        cars.clear();
        if (!getCarsFromDb(userIdToRefresh, cars)) return false;

        if (carAdapter == null) return false;

        carAdapter.notifyDataSetChanged();

        return true;
    }


    @Override
    public void itemCarSelected(final int position, final long id) {
        AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
        ad.setTitle("Alert");
        ad.setMessage("Delete this car?");
        ad.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Car car = (Car) cars.get(position);
                localDbStorage.reopen();
                car.setDb(localDbStorage.getDb());
                if (!car.remove()) {
                    localDbStorage.close();
                    return;
                }
                localDbStorage.close();
                cars.clear();
                if (!getCarsFromDb(userId, cars)) {
                    return;
                }
                carAdapter.notifyDataSetChanged();
            }
        });

        ad.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                carAdapter.notifyItemChanged(position);
            }
        });

        ad.setCancelable(true);
        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                carAdapter.notifyItemChanged(position);
            }
        });

        ad.show();

    }
}

