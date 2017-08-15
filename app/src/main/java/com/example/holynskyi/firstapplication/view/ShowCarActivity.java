package com.example.holynskyi.firstapplication.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.holynskyi.firstapplication.R;
import com.example.holynskyi.firstapplication.adapters.CarAdapter;
import com.example.holynskyi.firstapplication.db.DatabaseStructure;
import com.example.holynskyi.firstapplication.db.LocalDbStorage;
import com.example.holynskyi.firstapplication.dialogs.OnCarItemSelectedListener;
import com.example.holynskyi.firstapplication.models.Car;
import com.example.holynskyi.firstapplication.models.Cars;

import static com.example.holynskyi.firstapplication.basic.Codes.REQUEST_CODE_CAR_REGISTER;
import static com.example.holynskyi.firstapplication.basic.Codes.RESULT_CODE_CAR_REGISTERED;

/**
 * Created by holynskyi on 09.08.17.
 */

public class ShowCarActivity extends AppCompatActivity implements OnCarItemSelectedListener {


    private LocalDbStorage localDbStorage;
    private RecyclerView recyclerView;

    private android.support.v7.widget.Toolbar toolbar;
    private ImageView btnBack;
    private ImageView btnAddCar;

    private CarAdapter carAdapter;
    private Cars cars;
    private long userId;

    // for notes getting from DB
    private boolean getCarsFromDb(long userLocalId, Cars someCars) {
        localDbStorage.reopen();
        someCars.setDb(localDbStorage.getDb());
        boolean loaded = someCars.loadFromDb(DatabaseStructure.columns.car.userId + " = ?", new String[]{"" + userLocalId}, 0);
        localDbStorage.close();
        return loaded;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cars);

        Log.d("CAR SHOW","On resume");

        //inititalize local db
        localDbStorage = new LocalDbStorage(this);

        cars = new Cars();

        initViews();

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("CAR SHOW","On resume");

        Intent intent = getIntent();
        userId = Long.parseLong(intent.getStringExtra("USER_ID"));

        Log.d("CARS", "USER_ID = " + userId);

        cars.clear();

        if (!getCarsFromDb(userId, cars)) return;

        Log.d("CAR LIST ACTIVITY", "size=" + cars.size());

        initListeners();

    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbarShowCars);
        //setSupportActionBar(toolbar);

        btnBack = (ImageView) findViewById(R.id.ivBack);
        btnAddCar = (ImageView) findViewById(R.id.ivAdd);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMain);
        LinearLayoutManager llm = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(llm);
        // recyclerView.addItemDecoration(new DividerItemDecoration(this, getResources().getDrawable(R.drawable.divider)));
        //   recyclerView.setItemAnimator(new FadeInLeftAnimator());
        carAdapter = new CarAdapter(cars);
        carAdapter.setOnCarItemSelectedListener(this);
        recyclerView.setAdapter(carAdapter);
    }

    private void initListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowCarActivity.this, CreateCarActivity.class);
                intent.putExtra("USER_ID", "" + userId);
                startActivityForResult(intent, REQUEST_CODE_CAR_REGISTER);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            Log.d("CAR LIST", "result data is NULL");
            return;
        }

        Log.d("RESULT IS HERE", "REQUEST = " + requestCode + " RESULT =" + resultCode);
        if (requestCode == REQUEST_CODE_CAR_REGISTER) {
            if (resultCode == RESULT_CODE_CAR_REGISTERED) {
                userId = Long.parseLong(data.getStringExtra("USER_ID_NEW_CAR"));
                if (!getCarsFromDb(userId, cars)) return;
                carAdapter.notifyDataSetChanged();
            }
        }

    }


    @Override
    public void itemCarSelected(final int position, final long id) {
        Log.d("Interface of deleting", " go!");
        AlertDialog ad = new AlertDialog.Builder(this).create();
        ad.setTitle("Alert");
        ad.setMessage("Delete this car?");
        ad.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Car car = (Car) cars.get(position);
                Log.d("CAR to delete", "id = " + car.getId());
                localDbStorage.reopen();
                car.setDb(localDbStorage.getDb());
                if (!car.remove()) {
                    Log.d("CAR LIST", "unable to delete car from db");
                    localDbStorage.close();
                    return;
                }
                localDbStorage.close();
                cars.clear();
                if (!getCarsFromDb(userId, cars)) {
                    Log.d("CAR LIST", "unable to upload cars from db");
                    return;
                }
                Log.d("Interface of deleting", "was deleted");
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
