package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RequestForm extends AppCompatActivity {
    TextView dateText;
    Button requestingButton;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private int year, month, dayOfMonth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    private String userId;
    EditText statementEdit;
    String requestingEmail;
    String TAG = "Tag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_form);
        dateText = findViewById(R.id.dateText);
        requestingButton = findViewById(R.id.requestingButton);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userId = firebaseAuth.getCurrentUser().getUid();
        statementEdit = findViewById(R.id.editStatement);
        Handler handler= new Handler();
        Intent intent = getIntent();
        String workplaceEmail = intent.getStringExtra("email");
        System.out.println(workplaceEmail);


        DocumentReference documentReference = db.collection("users").document(userId);
        documentReference.addSnapshotListener(this, (documentSnapshot, e) -> {
            if (documentSnapshot.exists()) {
                requestingEmail = documentSnapshot.getString("email");
            } else {
                Log.d("tag", "onEvent: Document do not exists");
            }
        });



        dateText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(RequestForm.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                dateText.setText(day + "/" + (month+1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
                return false;
            }
        });
        requestingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String statement = statementEdit.getText().toString();
                String date = dateText.getText().toString();
                if(date!="" && statement!=""){
                    Map<String,Object> appointment = new HashMap<>();
                    appointment.put("statement",statement);
                    appointment.put("requesting",requestingEmail);
                    appointment.put("workplace",workplaceEmail);
                    appointment.put("date",date);
                    appointment.put("approved", "Waiting...");
                    db.collection("appointments")
                            .add(appointment)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Appointment has been made. You can check it from the appointment page.", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent1 = new Intent(RequestForm.this, AppointmentsList.class);
                                            startActivity(intent1);
                                        }
                                    },1200);







                                    Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Log.w(TAG, "Error adding document", e);
                                }
                            });
                }
                else{
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Fill in all fields!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }



            }
        });










    }
}