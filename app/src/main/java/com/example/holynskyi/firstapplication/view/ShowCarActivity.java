package com.example.holynskyi.firstapplication.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import com.example.holynskyi.firstapplication.adapters.ViewPagerAdapter;
import com.example.holynskyi.firstapplication.R;
import com.example.holynskyi.firstapplication.adapters.CarAdapter;
import com.example.holynskyi.firstapplication.adapters.HouseAdapter;
import com.example.holynskyi.firstapplication.db.DatabaseStructure;
import com.example.holynskyi.firstapplication.db.LocalDbStorage;
import com.example.holynskyi.firstapplication.dialogs.OnCarItemSelectedListener;
import com.example.holynskyi.firstapplication.dialogs.OnHouseItemSelectedListener;
import com.example.holynskyi.firstapplication.models.Car;
import com.example.holynskyi.firstapplication.models.Cars;
import com.example.holynskyi.firstapplication.models.House;
import com.example.holynskyi.firstapplication.models.Houses;

import static com.example.holynskyi.firstapplication.basic.Codes.REQUEST_CODE_CAR_REGISTER;
import static com.example.holynskyi.firstapplication.basic.Codes.REQUEST_CODE_HOUSE_REGISTER;
import static com.example.holynskyi.firstapplication.basic.Codes.RESULT_CODE_CAR_REGISTERED;

/**
 * Created by holynskyi on 09.08.17.
 */

public class ShowCarActivity extends AppCompatActivity implements OnCarItemSelectedListener, OnHouseItemSelectedListener {

    private LocalDbStorage localDbStorage;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private CarsFragment carsFragment;
    private HousesFragment housesFragment;

    private RecyclerView recyclerViewCars;
    private RecyclerView recyclerViewHouses;

    private android.support.v7.widget.Toolbar toolbar;
    private ImageView btnBack;
    private ImageView btnAddCar;

    private CarAdapter carAdapter;
    private Cars cars;
    private HouseAdapter houseAdapter;
    private Houses houses;
    private ViewPagerAdapter viewPagerAdapter;
    private long userId;

    // for cars getting from DB
    private boolean getCarsFromDb(long userLocalId, Cars someCars) {
        localDbStorage.reopen();
        someCars.setDb(localDbStorage.getDb());
        boolean loaded = someCars.loadFromDb(DatabaseStructure.columns.car.userId + " = ?", new String[]{"" + userLocalId}, 0);
        localDbStorage.close();
        return loaded;
    }

    // for houses getting from DB
    private boolean getHousesFromDb(long userLocalId, Houses someHouses) {
        localDbStorage.reopen();
        someHouses.setDb(localDbStorage.getDb());
        boolean loaded = someHouses.loadFromDb(DatabaseStructure.columns.house.userId + " = ?", new String[]{"" + userLocalId}, 0);
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
        houses = new Houses();

        initViews();

    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        userId = Long.parseLong(intent.getStringExtra("USER_ID"));

        Log.d("LIST ACTIVITY", "USER_ID = " + userId);

        cars.clear();

        if (!getCarsFromDb(userId, cars)) {
            Log.d("LIST ACTIVITY","no cars are in db");
        }

        Log.d("LIST ACTIVITY", "cars size=" + cars.size());

        houses.clear();

        if (!getHousesFromDb(userId, houses)) {
            Log.d("LIST ACTIVITY","no houses are in db");
        }

        Log.d("LIST ACTIVITY", "houses size=" + houses.size());

        initListeners();

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CarsFragment(), "Cars");
        adapter.addFragment(new HousesFragment(), "Houses");
        viewPager.setAdapter(adapter);
    }

    private void initViews() {
        btnBack = (ImageView) findViewById(R.id.ivBack);
        btnAddCar = (ImageView) findViewById(R.id.ivAdd);

        toolbar = (Toolbar) findViewById(R.id.toolbarShowCars);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new CarsFragment(), "Cars");
        viewPagerAdapter.addFragment(new HousesFragment(), "Houses");
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tablayoutMain);
        tabLayout.setupWithViewPager(viewPager);


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
                //first tab selected, so go to AddCar Activity
                if (tabLayout.getSelectedTabPosition() == 0) {
                    Intent intent = new Intent(ShowCarActivity.this, CreateCarActivity.class);
                    intent.putExtra("USER_ID", "" + userId);
                    startActivityForResult(intent, REQUEST_CODE_CAR_REGISTER);
                }
                //second tab selected, so go to AddHouse Activity
                if (tabLayout.getSelectedTabPosition() == 1) {
                    Intent intent = new Intent(ShowCarActivity.this, CreateHouseActivity.class);
                    intent.putExtra("USER_ID", "" + userId);
                    startActivityForResult(intent, REQUEST_CODE_HOUSE_REGISTER);
                }
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

    @Override
    public void itemHouseSelected(final int position, final long id) {
        Log.d("Interface of deleting", " go!");
        AlertDialog ad = new AlertDialog.Builder(this).create();
        ad.setTitle("Alert");
        ad.setMessage("Delete this house?");
        ad.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                House house = (House) houses.get(position);
                Log.d("HOUSE to delete", "id = " + house.getId());
                localDbStorage.reopen();
                house.setDb(localDbStorage.getDb());
                if (!house.remove()) {
                    Log.d("HOUSE LIST", "unable to delete house from db");
                    localDbStorage.close();
                    return;
                }
                localDbStorage.close();
                houses.clear();
                if (!getHousesFromDb(userId, houses)) {
                    Log.d("HOUSE LIST", "unable to upload houses from db");
                    return;
                }
                Log.d("Interface of del houses", "was deleted");
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
