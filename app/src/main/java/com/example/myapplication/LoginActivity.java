package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private String lEmail;
    private String lPassword;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth= FirebaseAuth.getInstance();
        imageView = findViewById(R.id.imageview);
        imageView.setImageResource(R.drawable.mechanic);


        EditText userEmail = (EditText)findViewById(R.id.leditTextEmail);
        EditText userPassword = (EditText)findViewById(R.id.leditTextPassword);
        Button buttonLogin = (Button)findViewById(R.id.loginbutton);
        TextView forgotPassword = findViewById(R.id.forgotPassword);
        forgotPassword.setText(Html.fromHtml("<u>"+"Forgot Password"+"</u>"));




        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fEmail = userEmail.getText().toString();
                forgotPassword(fEmail);
            }
        });


        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                lEmail = userEmail.getText().toString();
                lPassword = userPassword.getText().toString();
                if(lEmail.isEmpty()||lPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Lütfen gerekli alanları doldurunuz!",Toast.LENGTH_SHORT).show();
                }
                else{
                    loginFunc();
                }
            }
        });
    }
    private void loginFunc() {

        firebaseAuth.signInWithEmailAndPassword(lEmail,lPassword).addOnCompleteListener(LoginActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent loginToProfile = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(loginToProfile);
                            finish();
                        }
                        else{
                            // hata
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }

    private void forgotPassword(String userEmail){
        firebaseAuth.sendPasswordResetEmail(userEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Check your email and create new password", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            System.out.println("Tıklandı");
                        }
                    }
                });
    }
}