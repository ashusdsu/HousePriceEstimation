package com.example.ashu.housepredictor;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class Welcome extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) this.findViewById(R.id.editText);
        editTextPassword = (EditText) this.findViewById(R.id.editText2);
        progressDialog = new ProgressDialog(this);
    }

    public void signin(View view)
    {
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();


        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Signing in Please Wait...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        Log.i("inside login", "testing");
                        if(task.isSuccessful()){
                            //start the profile activity
                            Toast.makeText(Welcome.this, "Login Successful", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), ClickImage.class));
                        }
                        else
                        {
                            Toast.makeText(Welcome.this, "Login Unsuccessful", Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    public void register(View view)
    {
        startActivity(new Intent(getApplicationContext(), RegisterApp.class));
    }
}

