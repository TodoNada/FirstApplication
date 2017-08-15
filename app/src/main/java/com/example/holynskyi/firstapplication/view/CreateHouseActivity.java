package com.example.holynskyi.firstapplication.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.holynskyi.firstapplication.R;
import com.example.holynskyi.firstapplication.db.LocalDbStorage;
import com.example.holynskyi.firstapplication.models.House;

import static com.example.holynskyi.firstapplication.basic.Codes.RESULT_CODE_HOUSE_REGISTERED;

public class CreateHouseActivity extends AppCompatActivity {


    private ImageView ivHouse;
    private EditText etHouseCityRegister;
    private EditText etHouseAdressRegister;
    private EditText etHouseOtherRegister;
    private Button btnRegisterHouseOk;
    private Toolbar toolbarAddHouse;

    private LocalDbStorage localDbStorage;
    private House registerHouse;

    private ImageView ivBackView;

    private long userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_house);

        //inititalize local db
        localDbStorage = new LocalDbStorage(this);

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //take user id
        Intent intent = getIntent();
        userID = Long.parseLong(intent.getStringExtra("USER_ID"));

        Log.d("CREATE HOUSE", "intent is here, UserId=" + userID);


        initListeners();
    }


    private void initListeners() {
        //register house and go back
        btnRegisterHouseOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((etHouseCityRegister.getText().toString().isEmpty()) || (etHouseAdressRegister.getText().toString().isEmpty()))
                    return;
                //push new house into DB
                localDbStorage.reopen();
                registerHouse = new House(localDbStorage.getDb());
                registerHouse.setCity(etHouseCityRegister.getText().toString());
                registerHouse.setAdress(etHouseAdressRegister.getText().toString());
                registerHouse.setOther(etHouseOtherRegister.getText().toString());
                registerHouse.setData(18);
                registerHouse.setUserId(userID);
                if (!registerHouse.insert()) {
                    Log.d("HOUSE CREATION", "HOUSE was not created");
                    localDbStorage.close();
                    return;
                }
                localDbStorage.close();
                Log.d("HOUSE CREATION", "HOUSE was created");

                //set result and go back
                Intent intent = new Intent();
                intent.putExtra("USER_ID_NEW_HOUSE", "" + userID);
                setResult(RESULT_CODE_HOUSE_REGISTERED, intent);
                finish();
            }
        });
        //back Listener
        ivBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    private void initViews() {
        etHouseCityRegister = (EditText) findViewById(R.id.editTextCityAdd);
        etHouseAdressRegister = (EditText) findViewById(R.id.editTextAdressAdd);
        etHouseOtherRegister = (EditText) findViewById(R.id.editTextOtherAdd);
        ivHouse = (ImageView) findViewById(R.id.imageViewHouseAdd);
        btnRegisterHouseOk = (Button) findViewById(R.id.buttonCreateHouse);
        ivBackView = (ImageView) findViewById(R.id.ivBackFromHouseAddActivity);
        toolbarAddHouse = (Toolbar) findViewById(R.id.toolbarAddHouse);
        //setSupportActionBar(toolbarAddHouse);
    }


}
