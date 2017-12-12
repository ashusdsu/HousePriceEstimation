package com.example.ashu.housepredictor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterApp extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextEmail;
    private EditText editTextCountry;
    private EditText editTextState;
    private EditText editTextCity;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("userPresent");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_app);
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
// fetch data

        } else {
// display error
            Log.i("info", "error in connection");
        }

        editTextUsername = (EditText) findViewById(R.id.username);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextCountry = (EditText) findViewById(R.id.country);
        editTextState = (EditText) findViewById(R.id.state);
        editTextCity = (EditText) findViewById(R.id.city);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void submit(View view)
    {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String country = editTextCountry.getText().toString().trim();
        String city = editTextCity.getText().toString().trim();
        String state = editTextState.getText().toString().trim();

        String nmail = username+"@abc.com";

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;

        }

        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;

        }

        if (password.length() < 8)
        {
            Toast.makeText(this, "Please Enter Long Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(username))
        {
            Toast.makeText(this, "Please Enter Username", Toast.LENGTH_SHORT).show();
            return;

        }

        if (TextUtils.isEmpty(country))
        {
            Toast.makeText(this, "Please Enter Country", Toast.LENGTH_SHORT).show();
            return;

        }

        if (TextUtils.isEmpty(city))
        {
            Toast.makeText(this, "Please Enter City", Toast.LENGTH_SHORT).show();
            return;

        }

        if (TextUtils.isEmpty(state))
        {
            Toast.makeText(this, "Please Enter State", Toast.LENGTH_SHORT).show();
            return;

        }
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(nmail, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(RegisterApp.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterApp.this, Welcome.class);
                    finish();
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(RegisterApp.this, "Could not Register, please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
        String l = "1";
        String s = "0";
        UserClass userInfo = new UserClass(username, email, country, state, city, l, s );
        myRef.child(username).setValue(userInfo);
    }
}
