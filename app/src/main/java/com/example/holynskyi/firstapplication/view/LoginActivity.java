package com.example.holynskyi.firstapplication.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import static com.example.holynskyi.firstapplication.basic.Codes.REQUEST_CODE_USER_REGISTER;
import static com.example.holynskyi.firstapplication.basic.Codes.RESULT_CODE_USER_REGISTERED;

public class LoginActivity extends AppCompatActivity {


    private LocalDbStorage localDbStorage;

    private EditText etName;
    private EditText etPassword;
    private Button btnSignIn;
    private Button btnRegister;

    private long userLoggedId;


    private void initViews() {
    etName = (EditText) findViewById(R.id.editTextName);
    etPassword = (EditText) findViewById(R.id.editTextPassword);
    btnSignIn = (Button)findViewById(R.id.buttonSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if empty edit texts then return
                if ((etName.getText().toString().isEmpty()) || (etPassword.getText().toString().isEmpty())) return;

                localDbStorage.reopen();
                Users users = new Users(localDbStorage.getDb());
                if (!users.loadFromDb(DatabaseStructure.columns.user.userName+" = ?",new String[] {""+etName.getText().toString()},0)) {
                    Log.d("User","Unable to find user in db");
                    localDbStorage.close();
                    Toast.makeText(LoginActivity.this,"User "+etName.getText().toString()+" doesnt exist, please register!",Toast.LENGTH_LONG).show();
                    return;
                }
                //take user id and send into next activity
                User user = (User)users.get(0);
                userLoggedId = user.getId();
                Log.d("User","user in db = "+userLoggedId);
                localDbStorage.close();

                if (!user.getPassword().equals(etPassword.getText().toString())) {
                    Log.d("User","Unable to find user in db");
                    Toast.makeText(LoginActivity.this,"User "+etName.getText().toString()+" incorrect password!",Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(LoginActivity.this,ShowCarActivity.class);
                intent.putExtra("USER_ID",""+userLoggedId);
                startActivity(intent);
            }
        });

        btnRegister = (Button)findViewById(R.id.buttonRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,REQUEST_CODE_USER_REGISTER);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        Log.d("LOGIN ACTIVITY","INTENT IS HERE");
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

        localDbStorage = new LocalDbStorage(this);

        initViews();
    }
}
