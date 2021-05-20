package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AppointmentViewing extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    String userEmail = firebaseUser.getEmail();
    TextView dateTV ;
    TextView requestingTV ;
    TextView workplaceTV ;
    TextView statementTV;
    Button approveButton;
    Button rejectButton;
    TextView stateTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_viewing);
        dateTV = findViewById(R.id.dateTV);
        requestingTV = findViewById(R.id.requestingTV);
        workplaceTV = findViewById(R.id.workplaceTV);
        statementTV = findViewById(R.id.statementTV);
        approveButton = findViewById(R.id.approveButton);
        rejectButton = findViewById(R.id.rejectButton);
        stateTV = findViewById(R.id.stateTV);
        Intent intent1 = new Intent(AppointmentViewing.this,AppointmentsList.class);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Appointment appointment = (Appointment) bundle.get("selectedAppointment");
        String date = appointment.date;
        String requesting = appointment.requesting;
        String workplace = appointment.workplace;
        String statement = appointment.statement;
        String dateID = appointment.dateId;
        String state = appointment.approved;
        dateTV.setText("Date:  "+date);
        requestingTV.setText("Requesting:  "+requesting);
        statementTV.setText("Statement:  "+statement);
        workplaceTV.setText("Workplace:  " +workplace);
        stateTV.setText("State: "+state);

        if (requesting.equals(userEmail)){
            rejectButton.setVisibility(View.INVISIBLE);
            approveButton.setVisibility(View.INVISIBLE);
        }
        if (state.equals("Approved") || state.equals("Rejected")){
            rejectButton.setVisibility(View.INVISIBLE);
            approveButton.setVisibility(View.INVISIBLE);
        }
        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("appointments").document(dateID).update("approved","Approved");
                finish();
                startActivity(intent1);
            }
        });
        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("appointments").document(dateID).update("approved","Rejected");
                finish();
                startActivity(intent1);
            }
        });


    }
}