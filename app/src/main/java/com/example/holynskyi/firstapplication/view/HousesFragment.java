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
import com.example.holynskyi.firstapplication.adapters.HouseAdapter;
import com.example.holynskyi.firstapplication.db.DatabaseStructure;
import com.example.holynskyi.firstapplication.db.LocalDbStorage;
import com.example.holynskyi.firstapplication.dialogs.OnHouseItemSelectedListener;
import com.example.holynskyi.firstapplication.models.Houses;
import com.example.holynskyi.firstapplication.models.House;

/**
 * Created by holynskyi on 15.08.17.
 */

public class HousesFragment extends Fragment implements OnHouseItemSelectedListener {

    private RecyclerView recyclerView;

    private HouseAdapter houseAdapter;
    private Houses houses;
    private LocalDbStorage localDbStorage;
    private long userId;

    public HousesFragment() {
    }

    @SuppressLint("ValidFragment")
    public HousesFragment(long userId) {
        this.userId = userId;
        houses = new Houses();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_houses, container, false);
        initRecyclerView(view);
        return view;
    }


    public void initRecyclerView(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewMainHouses);

        //inititalize local db
        localDbStorage = new LocalDbStorage(getContext());
        houses.clear();

        if (getHousesFromDb(userId, houses)) {
            houseAdapter = new HouseAdapter(houses);
            if (houseAdapter != null) {
                LinearLayoutManager llmHouse = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(llmHouse);
                recyclerView.setAdapter(houseAdapter);
                houseAdapter.setOnHouseItemSelectedListener(this);
            }
        } else {
            Log.d("FRAGMENT HOUSE", "no houses are in db, userId = " + userId);
        }
    }


    // for houses getting from DB
    private boolean getHousesFromDb(long userLocalId, Houses someHouses) {
        localDbStorage.reopen();
        someHouses.setDb(localDbStorage.getDb());
        boolean loaded = someHouses.loadFromDb(DatabaseStructure.columns.house.userId + " = ?", new String[]{"" + userLocalId}, 0);
        localDbStorage.close();
        return loaded;
    }


    protected boolean notifyDataWasRefresh(long userIdToRefresh) {

        houses.clear();
        if (!getHousesFromDb(userIdToRefresh, houses)) return false;

        if (houseAdapter == null) return false;

        houseAdapter.notifyDataSetChanged();

        return true;
    }

    @Override
    public void itemHouseSelected(final int position, final long id) {
        AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
        ad.setTitle("Alert");
        ad.setMessage("Delete this house?");
        ad.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                House house = (House) houses.get(position);
                localDbStorage.reopen();
                house.setDb(localDbStorage.getDb());
                if (!house.remove()) {
                    localDbStorage.close();
                    return;
                }
                localDbStorage.close();
                houses.clear();
                if (!getHousesFromDb(userId, houses)) {
                    return;
                }
                houseAdapter.notifyDataSetChanged();
            }
        });

        ad.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                houseAdapter.notifyItemChanged(position);
            }
        });

        ad.setCancelable(true);
        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                houseAdapter.notifyItemChanged(position);
            }
        });

        ad.show();

    }
}
