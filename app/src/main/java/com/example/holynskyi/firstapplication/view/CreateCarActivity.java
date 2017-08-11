package com.example.holynskyi.firstapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.util.Log;

import com.example.holynskyi.firstapplication.R;
import com.example.holynskyi.firstapplication.db.LocalDbStorage;
import com.example.holynskyi.firstapplication.models.Car;

/**
 * Created by holynskyi on 09.08.17.
 */

public class CreateCarActivity extends AppCompatActivity {

    private ImageView ivCar;
    private EditText etCarNameRegister;
    private EditText etCarDetailsRegister;
    private Button btnRegisterCarOk;

    private LocalDbStorage localDbStorage;
    private Car registerCar;

    private long userID;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_car);

        //take user id
        Intent intent =  getIntent();
        userID = Long.parseLong(intent.getStringExtra("USER_ID"));

        Log.d("CREATE CAR","intent is here, UserId="+userID);
        //inititalize local db
        localDbStorage = new LocalDbStorage(this);

        initViews();

    }

    @Override
    protected void onResume() {
        super.onResume();
        btnRegisterCarOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((etCarNameRegister.getText().toString().isEmpty()) || (etCarDetailsRegister.getText().toString().isEmpty())) return;
                //push new car into DB
                localDbStorage.reopen();
                registerCar = new Car(localDbStorage.getDb());
                registerCar.setTitleOne(etCarNameRegister.getText().toString());
                registerCar.setTitleTwo(etCarDetailsRegister.getText().toString());
                registerCar.setData(18);
                registerCar.setUserId(userID);
                if (!registerCar.insert()) {
                    Log.d("CAR CREATION","Car was not created");
                    localDbStorage.close();
                    return;
                }
                Log.d("CAR CREATION","Car was created");
                //set result
                localDbStorage.close();
                Intent intent = new Intent(CreateCarActivity.this, ShowCarActivity.class);
                intent.putExtra("USER_ID","1");
                startActivity(intent);
             onBackPressed();
            }
        });
    }

    private void initViews() {
        etCarNameRegister = (EditText) findViewById(R.id.editTextTitle);
        etCarDetailsRegister = (EditText) findViewById(R.id.editTextDescription);
        ivCar = (ImageView) findViewById(R.id.imageViewCar);
        btnRegisterCarOk = (Button)findViewById(R.id.buttonCreateCar);
    }


}
