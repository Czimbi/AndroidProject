package com.example.tattoshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private FirebaseAuth mauth;
    EditText emailET;
    EditText passwdET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mauth = FirebaseAuth.getInstance();
        emailET = findViewById(R.id.LogInEmail);
        passwdET = findViewById(R.id.LogInPasswd);
        FirebaseUser currentUser = mauth.getCurrentUser();
        if(currentUser == null){
            openReservation();
        }
    }

    public void openRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    private void openReservation(){
        Intent intent = new Intent(this, ReserveTimeActivity.class);
        startActivity(intent);
    }
    private boolean validateFields(){
        if(TextUtils.isEmpty(emailET.getText())){
            emailET.setError("Az email megadása kötelező");
            return false;
        }
        if(TextUtils.isEmpty(passwdET.getText())) {
            passwdET.setError("A jelszó megadása kötelező");
            return false;
        }
        return true;
    }

    public void logInUsr(View view) {
        String email = emailET.getText().toString();
        String passwd = passwdET.getText().toString();
        if(validateFields()){
            mauth.signInWithEmailAndPassword(email, passwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Log.i(LOG_TAG, "Sikeres bejelentkezés");
                                openReservation();
                            }else{
                                Toast.makeText(MainActivity.this, "Sikertelen bejelentkezés", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
}