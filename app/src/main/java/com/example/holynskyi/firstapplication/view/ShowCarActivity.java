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
import static com.example.holynskyi.firstapplication.basic.Codes.RESULT_CODE_HOUSE_REGISTERED;

/**
 * Created by holynskyi on 09.08.17.
 */

public class ShowCarActivity extends AppCompatActivity {

    private LocalDbStorage localDbStorage;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    private android.support.v7.widget.Toolbar toolbar;
    private ImageView btnBack;
    private ImageView btnAddCar;

    private long userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cars);

        Log.d("CAR SHOW","On resume");

        //inititalize local db
        localDbStorage = new LocalDbStorage(this);


        Intent intent = getIntent();
        userId = Long.parseLong(intent.getStringExtra("USER_ID"));

        Log.d("LIST ACTIVITY", "USER_ID = " + userId);

        initToolBarAndItsButtons();

        initTabLayout();

    }

    @Override
    protected void onResume() {
        super.onResume();

        initListeners();

    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new CarsFragment(userId), getString(R.string.tab1));
        viewPagerAdapter.addFragment(new HousesFragment(userId), getString(R.string.tab2));
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void initToolBarAndItsButtons() {
        btnBack = (ImageView) findViewById(R.id.ivBack);
        btnAddCar = (ImageView) findViewById(R.id.ivAdd);
        toolbar = (Toolbar) findViewById(R.id.toolbarShowCars);
    }


    private void initTabLayout() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

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

                String string = "" + tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getText();

                if (string.equals(getString(R.string.tab1))) {
                    //car tab selected, so go to AddCar Activity
                    Intent intent = new Intent(ShowCarActivity.this, CreateCarActivity.class);
                    intent.putExtra("USER_ID", "" + userId);
                    startActivityForResult(intent, REQUEST_CODE_CAR_REGISTER);
                }

                if (string.equals(getString(R.string.tab2))) {
                    //house tab selected, so go to AddHouse Activity
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
            Log.d("LIST ACTIVITY", "result data is NULL");
            return;
        }

        Log.d("RESULT IS HERE", "REQUEST = " + requestCode + " RESULT =" + resultCode);

        if (requestCode == REQUEST_CODE_CAR_REGISTER) {
            if (resultCode == RESULT_CODE_CAR_REGISTERED) {
                userId = Long.parseLong(data.getStringExtra("USER_ID_NEW_CAR"));
                // Todo: Acthung! tab id must be only car
                //refresh CarFragment
                CarsFragment carFragment = (CarsFragment) viewPagerAdapter.getItem(0);
                if (!carFragment.notifyDataWasRefresh(userId)) {
                    Log.d("Car list REFRESH", " not refreshed");
                }

                return;
            }
        }

        if (requestCode == REQUEST_CODE_HOUSE_REGISTER) {
            if (resultCode == RESULT_CODE_HOUSE_REGISTERED) {
                userId = Long.parseLong(data.getStringExtra("USER_ID_NEW_HOUSE"));
                // Todo: Acthung! tab id must be only house

                HousesFragment housesFragment = (HousesFragment) viewPagerAdapter.getItem(1);
                if (!housesFragment.notifyDataWasRefresh(userId)) {
                    Log.d("Car list REFRESH", " not refreshed");
                }

                return;
            }
        }

    }









}
