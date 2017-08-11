package com.example.holynskyi.firstapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.holynskyi.firstapplication.models.Cars;

/**
 * Created by holynskyi on 09.08.17.
 */

public class ShowCarActivity extends AppCompatActivity {

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
        boolean loaded = someCars.loadFromDb(DatabaseStructure.columns.car.userId+" = ?",new String[] {""+userLocalId},0);
        localDbStorage.close();
        return loaded;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cars);

        //inititalize local db
        localDbStorage = new LocalDbStorage(this);

        Intent intent = getIntent();
        userId = Long.parseLong(intent.getStringExtra("USER_ID"));

        Log.d("CARS","USER_ID = "+userId);
        cars = new Cars();
        if (!getCarsFromDb(userId,cars)) return;

        Log.d("CAR LIST ACTIVITY","size="+cars.size());

        initViews();

    }

    @Override
    protected void onResume() {
        super.onResume();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    onBackPressed();
            }
        });

        btnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowCarActivity.this,CreateCarActivity.class);
                intent.putExtra("USER_ID",""+userId);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initViews(){
        toolbar = (Toolbar)findViewById(R.id.toolbarShowCars);
        // setSupportActionBar(toolbar);
        
        btnBack = (ImageView)findViewById(R.id.ivBack);
        btnAddCar = (ImageView)findViewById(R.id.ivAdd);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMain);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        carAdapter = new CarAdapter(cars);
        recyclerView.setAdapter(carAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
/*
        if (data == null) {
            Log.d("CAR LIST","result data is NULL");
            return;
        }
        Log.d("CAR CREATED","REQUEST IS HERE");
        if (requestCode == REQUEST_CODE_CAR_REGISTER) {
            if (resultCode == RESULT_CODE_CAR_REGISTERED) {
                userId = Long.parseLong(data.getStringExtra("USER_ID_NEW_CAR"));
                if (!getCarsFromDb(userId, cars)) return;
                carAdapter.notifyDataSetChanged();
            }
        }
        */
    }


}
