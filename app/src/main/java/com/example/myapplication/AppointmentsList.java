package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AppointmentsList extends AppCompatActivity {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userEmail = firebaseUser.getEmail();
    List<Appointment> appointmentList = new ArrayList<>();
    ListView listView;
    SwipeRefreshLayout refreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments_list);
        refreshLayout = findViewById(R.id.swipe_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
            }
        });

        AppointmentAdapter adapter = new AppointmentAdapter(this, appointmentList);
        listView = findViewById(R.id.appointmentsListView);

        db.collection("appointments").whereEqualTo("workplace", userEmail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> appointments = document.getData();

                        String requesting = appointments.get("requesting").toString();
                        String workplace = appointments.get("workplace").toString();
                        String date = appointments.get("date").toString();
                        String statement = appointments.get("statement").toString();
                        String approved = appointments.get("approved").toString();
                        String dateId = document.getId();

                        appointmentList.add(new Appointment(dateId, requesting, workplace, date, statement, approved));
                        System.out.println(dateId);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        db.collection("appointments").whereEqualTo("requesting", userEmail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> appointments = document.getData();

                        String requesting = appointments.get("requesting").toString();
                        String workplace = appointments.get("workplace").toString();
                        String date = appointments.get("date").toString();
                        String statement = appointments.get("statement").toString();
                        String approved = appointments.get("approved").toString();
                        String dateId = document.getId();

                        appointmentList.add(new Appointment(dateId, requesting, workplace, date, statement, approved));
                        System.out.println(dateId);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Appointment selectedAppointment = (Appointment) adapter.getItem(position);
                String dateId = selectedAppointment.dateId;
                Intent intent1 = new Intent(AppointmentsList.this,AppointmentViewing.class);
                intent1.putExtra("selectedAppointment",selectedAppointment);
                intent1.putExtra("dateId",dateId);
                finish();
                startActivity(intent1);
            }
        });
    }
}