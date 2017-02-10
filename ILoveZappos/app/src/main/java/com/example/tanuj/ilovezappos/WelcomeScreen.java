package com.example.tanuj.ilovezappos;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tanuj.ilovezappos.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class WelcomeScreen extends AppCompatActivity implements View.OnClickListener {
    EditText Email,Password;
    Button backToSignup,login;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth auth;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        database = FirebaseDatabase.getInstance();
        myRef=database.getReference();
        login= (Button) findViewById(R.id.login);
        backToSignup= (Button) findViewById(R.id.BackToSignup);
        login.setOnClickListener(this);
        backToSignup.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
        Email = (EditText) findViewById(R.id.inputEmail);
        Password = (EditText) findViewById(R.id.inputPassword);
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(WelcomeScreen.this, HomeScreen.class));
            finish();
        }
    }

    @Override
    public void onClick(View view) {
switch(view.getId()){
    case R.id.login:
        if(CheckFieldValidator.checkField(Email)&&CheckFieldValidator.checkField(Password)){
            auth.signInWithEmailAndPassword(Email.getText().toString(),Password.getText().toString()).addOnCompleteListener(WelcomeScreen.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        // there was an error
                        if (Password.length() < 6) {
                            Password.setError("Password must be six length in minimum");
                        } else {
                            Toast.makeText(WelcomeScreen.this, "Authentication failed, check your email and password or sign up", Toast.LENGTH_LONG).show();
                        }
                    } else {

                        Intent intent = new Intent(WelcomeScreen.this, HomeScreen.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
        break;
    case R.id.BackToSignup:
        Intent in =new Intent(WelcomeScreen.this,zappos_register.class);
        startActivity(in);
        break;
}
    }
}
