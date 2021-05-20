package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {
    ImageView imageView;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TextView textViewUserEmail;
    TextView textViewName;
    TextView textViewPhone;
    TextView textViewAddress;
    TextView textViewWorkplace;
    TextView textViewCategory;
    Button userLogout;
    Button updateButton;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        imageView = findViewById(R.id.imageview);
        imageView.setImageResource(R.drawable.mechanic);
        updateButton=findViewById(R.id.pUpdate);


        textViewName = findViewById(R.id.pName);
        textViewAddress= findViewById(R.id.pAdress);
        textViewPhone = findViewById(R.id.pPhone);
        textViewWorkplace =findViewById(R.id.pWorkPlaceName);
        textViewUserEmail = findViewById(R.id.pEmail);
        textViewCategory=findViewById(R.id.uCategory);


        userLogout = findViewById(R.id.pSignOut);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();



        userId = firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("users").document(userId);
        documentReference.addSnapshotListener(this,(documentSnapshot,e)->{
               if(documentSnapshot != null){
                   textViewName.setText(documentSnapshot.getString("name"));
                   textViewPhone.setText(documentSnapshot.getString("phone"));
                   textViewWorkplace.setText(documentSnapshot.getString("workplace"));
                   textViewAddress.setText(documentSnapshot.getString("address"));
                   textViewUserEmail.setText(firebaseAuth.getCurrentUser().getEmail().toString());
                   textViewCategory.setText(documentSnapshot.getString("category"));
                   boolean isAdmin = documentSnapshot.getBoolean("isAdmin");
                   if (isAdmin!=true){
                       textViewAddress.setVisibility(View.INVISIBLE);
                       textViewWorkplace.setVisibility(View.INVISIBLE);
                       textViewCategory.setVisibility(View.INVISIBLE);
                   }
               }
               else{
                   Log.d("tag", "onEvent: Document do not exists");
               }
        });
        userLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,ProfileUpdateActivity.class));
            }
        });
    }
}