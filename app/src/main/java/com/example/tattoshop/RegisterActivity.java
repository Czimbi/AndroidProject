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


public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    private FirebaseAuth mauth;
    //Regisztraciohoz szukseges email es jelszo
    EditText regEmail;
    EditText regPasswd;
    //A jelszó megerősítése
    EditText regPasswdRe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mauth = FirebaseAuth.getInstance();
        regEmail = findViewById(R.id.RegEmail);
        regPasswd = findViewById(R.id.RegPasswd);
        regPasswdRe = findViewById(R.id.RegPasswdAgain);
    }

    public void registerUser(View view) {
        String emailStr = regEmail.getText().toString();
        String passwdStr = regPasswd.getText().toString();
        String passwdReStr = regPasswdRe.getText().toString();
        if(validateFields(emailStr, passwdStr, passwdReStr)){
            mauth.createUserWithEmailAndPassword(emailStr, passwdStr)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Sikeres regisztráció", Toast.LENGTH_SHORT).show();
                                login();
                            }else {
                                Toast.makeText(RegisterActivity.this, "Sikertelen regisztráció", Toast.LENGTH_SHORT).show();
                                Log.w(LOG_TAG, "Sikertelen regisztracio", task.getException());
                            }
                        }
                    });
        }

    }
    private boolean validateFields(String email, String passwd, String rePasswd){
        if(TextUtils.isEmpty(regEmail.getText())){
            regEmail.setError("Az email megadása kötelező");
            return false;
        }
        if(TextUtils.isEmpty(regPasswd.getText())){
            regPasswd.setError("A jelszó megadása kötelező");
            return false;
        }
        if(TextUtils.isEmpty(regPasswdRe.getText())){
            regPasswdRe.setError("Erősítsd meg a jelszavad");
            return false;
        }
        if(!passwd.equals(rePasswd)){
            regPasswd.setError("A jelszavak nem egyeznek");
            regPasswdRe.setError("A jelszavak nem egyeznek");
            return false;
        }
        if(regPasswd.getText().length() < 6){
            regPasswd.setError("A jelszó nem elég hosszú");
            regPasswdRe.setError("A jelszó nem elég hosszú");
        }
        return true;
    }
    private void login(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void cancle(View view) {
        finish();
    }
}