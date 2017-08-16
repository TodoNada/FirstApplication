package com.example.holynskyi.firstapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.holynskyi.firstapplication.R;
import com.example.holynskyi.firstapplication.db.DatabaseStructure;
import com.example.holynskyi.firstapplication.db.LocalDbStorage;
import com.example.holynskyi.firstapplication.models.User;
import com.example.holynskyi.firstapplication.models.Users;

import static com.example.holynskyi.firstapplication.basic.Codes.RESULT_CODE_USER_REGISTERED;

/**
 * Created by holynskyi on 09.08.17.
 */

public class RegisterActivity extends AppCompatActivity {
    private EditText etNameRegister;
    private EditText etPasswordRegister;
    private Button btnRegisterOk;

    private LocalDbStorage localDbStorage;
    private User registerUser;


    private void initViews() {
        etNameRegister = (EditText) findViewById(R.id.editTextNameRegister);
        etPasswordRegister = (EditText) findViewById(R.id.editTextPasswordRegister);
        btnRegisterOk = (Button) findViewById(R.id.buttonRegisterOk);
    }


    private void initListeners() {
        btnRegisterOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if user name or password are empty, return
                if ((etNameRegister.getText().toString().isEmpty()) || (etPasswordRegister.getText().toString().isEmpty()))
                    return;

                //if user with same name exists, then return
                localDbStorage.reopen();
                Users users = new Users(localDbStorage.getDb());
                if (users.loadFromDb(DatabaseStructure.columns.user.userName + " = ?", new String[]{"" + etNameRegister.getText().toString()}, 0)) {
                    localDbStorage.close();
                    Toast.makeText(RegisterActivity.this, "User " + etNameRegister.getText().toString() + " already exist, please take other name!", Toast.LENGTH_LONG).show();
                    return;
                }
                //push new user into DB
                localDbStorage.reopen();
                registerUser = new User(localDbStorage.getDb());
                registerUser.setName(etNameRegister.getText().toString());
                registerUser.setPassword(etPasswordRegister.getText().toString());
                registerUser.setCars(0);
                if (!registerUser.insert()) {
                    localDbStorage.close();
                    return;
                }
                //set result
                Intent intent = new Intent();
                intent.putExtra("USER_NAME", etNameRegister.getText().toString());
                setResult(RESULT_CODE_USER_REGISTERED, intent);
                finish();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //inititalize local db
        localDbStorage = new LocalDbStorage(this);

        initViews();

    }

    @Override
    protected void onResume() {
        super.onResume();

        initListeners();
    }
}
