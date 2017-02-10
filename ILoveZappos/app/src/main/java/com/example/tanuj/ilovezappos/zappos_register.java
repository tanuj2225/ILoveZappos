package com.example.tanuj.ilovezappos;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.tanuj.ilovezappos.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class zappos_register extends AppCompatActivity implements View.OnClickListener {
    EditText fullName,sEmail,cPassword,rPassword;
    Button signup,back;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth auth;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zappos_register);
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        fullName= (EditText) findViewById(R.id.fullName);
        sEmail= (EditText) findViewById(R.id.inputEmail);
        cPassword= (EditText) findViewById(R.id.inputPassword);
        rPassword= (EditText) findViewById(R.id.reenterPassword);
        signup= (Button) findViewById(R.id.signup);
        back= (Button) findViewById(R.id.backToLogin);
        signup.setOnClickListener(this);
        back.setOnClickListener(this);
        myRef=database.getReference();
        user=new User();
    }
    public boolean passwordValidation(String cPass,String rPass){
        if(cPass.equals(rPass)){
            return true;
        }
        else{
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backToLogin:
                Intent in =new Intent(zappos_register.this,WelcomeScreen.class);
                startActivity(in);
                break;
            case R.id.signup:
                if(CheckFieldValidator.checkField(sEmail)&&CheckFieldValidator.checkField(fullName)&&CheckFieldValidator.checkField(cPassword)&&CheckFieldValidator.checkField(rPassword)){
                if(passwordValidation(cPassword.getText().toString(),rPassword.getText().toString())){
                    auth.createUserWithEmailAndPassword(sEmail.getText().toString(),cPassword.getText().toString()).addOnCompleteListener(zappos_register.this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(zappos_register.this, "Authentication failed." + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                user.setEmail(sEmail.getText().toString());
                                user.setFullname(fullName.getText().toString());
                                String key = myRef.child("Users").push().getKey();
                                user.setUserKey(auth.getCurrentUser().getUid());
                                Map<String, Object> postValues = user.toMap();
                                Map<String, Object> childUpdates = new HashMap<>();
                                childUpdates.put("/Users/" + auth.getCurrentUser().getUid(), postValues);
                                myRef.updateChildren(childUpdates);
                                startActivity(new Intent(zappos_register.this, HomeScreen.class));
                                finish();
                            }
                        }
                    });
                }
            }
                break;
        }
    }
}
