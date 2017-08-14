package com.example.holynskyi.firstapplication.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.holynskyi.firstapplication.R;
import com.example.holynskyi.firstapplication.db.DatabaseStructure;
import com.example.holynskyi.firstapplication.db.LocalDbStorage;
import com.example.holynskyi.firstapplication.models.User;
import com.example.holynskyi.firstapplication.models.Users;
import com.example.holynskyi.firstapplication.utils.UsersPreferences;


import java.util.HashSet;
import java.util.Set;

import static com.example.holynskyi.firstapplication.basic.Codes.REQUEST_CODE_USER_REGISTER;
import static com.example.holynskyi.firstapplication.basic.Codes.RESULT_CODE_USER_REGISTERED;

public class LoginActivity extends AppCompatActivity {


    private LocalDbStorage localDbStorage;
    private UsersPreferences usersPreferences;
    private Set<String> setOfUsersNames;

    private AutoCompleteTextView etName;
    private EditText etPassword;
    private Button btnSignIn;
    private Button btnRegister;

    private long userLoggedId;


    private void initListeners() {

        // sign in Listener
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if empty edit texts then return
                if ((etName.getText().toString().isEmpty()) || (etPassword.getText().toString().isEmpty()))
                    return;

                localDbStorage.reopen();
                Users users = new Users(localDbStorage.getDb());
                if (!users.loadFromDb(DatabaseStructure.columns.user.userName + " = ?", new String[]{"" + etName.getText().toString()}, 0)) {
                    Log.d("User", "Unable to find user in db");
                    localDbStorage.close();
                    Toast.makeText(LoginActivity.this, "User " + etName.getText().toString() + " doesnt exist, please register!", Toast.LENGTH_LONG).show();
                    return;
                }
                //take user id and send into next activity
                User user = (User) users.get(0);
                userLoggedId = user.getId();
                Log.d("User", "user in db = " + userLoggedId);
                localDbStorage.close();

                if (!user.getPassword().equals(etPassword.getText().toString())) {
                    Log.d("User", "Unable to find user in db");
                    Toast.makeText(LoginActivity.this, "User " + etName.getText().toString() + " incorrect password!", Toast.LENGTH_LONG).show();
                    return;
                }
                //try to put new logged user name into preferenes
                if (!usersPreferences.putUserNameIntoPreferences(user.getName())) {
                    Log.d("SHARED PREFS", "unable to put new user name into preferences");
                }

                Intent intent = new Intent(LoginActivity.this, ShowCarActivity.class);
                intent.putExtra("USER_ID", "" + userLoggedId);
                startActivity(intent);
            }
        });
        //register Listener
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, REQUEST_CODE_USER_REGISTER);
            }
        });

    }

    private void initViews() {
        //autocompleting
        etName = (AutoCompleteTextView) findViewById(R.id.editTextName);
        etPassword = (EditText) findViewById(R.id.editTextPassword);
        btnSignIn = (Button) findViewById(R.id.buttonSignIn);
        btnRegister = (Button) findViewById(R.id.buttonRegister);

        localDbStorage = new LocalDbStorage(this);

        usersPreferences = UsersPreferences.getInstance(this, "USERSNAMES", Context.MODE_PRIVATE);

        setOfUsersNames = new HashSet<>(usersPreferences.getStringSet());

        if ((setOfUsersNames != null) && (setOfUsersNames.size() > 0)) {
            Log.d("SHARED PREFS", "Set size is " + setOfUsersNames.size());
            String[] strings = setOfUsersNames.toArray(new String[setOfUsersNames.size()]);
            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings);
            etName.setAdapter(stringArrayAdapter);
        } else {
            Log.d("SHARED PREFS", "Set size is 0 or it is NULL");
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        Log.d("LOGIN ACTIVITY", "INTENT IS HERE");
        if (data == null) return;

        if (requestCode == REQUEST_CODE_USER_REGISTER) {
            if (resultCode == RESULT_CODE_USER_REGISTERED) {
                String userName = data.getStringExtra("USER_NAME");
                etName.setText(userName);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initListeners();
    }
}
