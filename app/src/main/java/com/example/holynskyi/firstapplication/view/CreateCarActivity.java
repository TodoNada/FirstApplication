package com.example.holynskyi.firstapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.util.Log;

import com.example.holynskyi.firstapplication.R;
import com.example.holynskyi.firstapplication.db.LocalDbStorage;
import com.example.holynskyi.firstapplication.models.Car;
import com.example.holynskyi.firstapplication.utils.UsersPreferences;

import static com.example.holynskyi.firstapplication.basic.Codes.RESULT_CODE_CAR_REGISTERED;

/**
 * Created by holynskyi on 09.08.17.
 */

public class CreateCarActivity extends AppCompatActivity {

    private ImageView ivCar;
    private EditText etCarNameRegister;
    private EditText etCarDetailsRegister;
    private Button btnRegisterCarOk;
    private Toolbar toolbarAddCar;

    private LocalDbStorage localDbStorage;
    private Car registerCar;

    private ImageView ivBackView;

    private long userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_car);

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

        Log.d("CREATE CAR", "intent is here, UserId=" + userID);

        initListeners();
    }


    private void initListeners() {
        //register car and go back
        btnRegisterCarOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((etCarNameRegister.getText().toString().isEmpty()) || (etCarDetailsRegister.getText().toString().isEmpty()))
                    return;
                //push new car into DB
                localDbStorage.reopen();
                registerCar = new Car(localDbStorage.getDb());
                registerCar.setTitleOne(etCarNameRegister.getText().toString());
                registerCar.setTitleTwo(etCarDetailsRegister.getText().toString());
                registerCar.setData(18);
                registerCar.setUserId(userID);
                if (!registerCar.insert()) {
                    localDbStorage.close();
                    return;
                }
                localDbStorage.close();

                //set result and go back
                Intent intent = new Intent();
                intent.putExtra("USER_ID_NEW_CAR", "" + userID);
                setResult(RESULT_CODE_CAR_REGISTERED, intent);
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
        etCarNameRegister = (EditText) findViewById(R.id.editTextTitle);
        etCarDetailsRegister = (EditText) findViewById(R.id.editTextDescription);
        ivCar = (ImageView) findViewById(R.id.imageViewCar);
        btnRegisterCarOk = (Button) findViewById(R.id.buttonCreateCar);
        ivBackView = (ImageView) findViewById(R.id.ivBackFromCarAddActivity);
        toolbarAddCar = (Toolbar) findViewById(R.id.toolbarAddCar);
    }


}
