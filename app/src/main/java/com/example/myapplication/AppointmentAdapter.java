package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.List;

public class AppointmentAdapter extends BaseAdapter {

    private LayoutInflater appointmentInflater;
    private List<Appointment> appointmentList;

    public AppointmentAdapter(Activity activity, List<Appointment> appointmentList){
        appointmentInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.appointmentList = appointmentList;
    }

    @Override
    public int getCount() {
        return appointmentList.size();
    }

    @Override
    public Object getItem(int position) {
        return appointmentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View lineView;
        lineView = appointmentInflater.inflate(R.layout.costum_appointments_list,null);
        TextView requestingTV = lineView.findViewById(R.id.requestingTV);
        TextView appointmentDateTV = lineView.findViewById(R.id.appointmentDateTV);

        Appointment appointment = appointmentList.get(position);
        requestingTV.setText(appointment.requesting);
        appointmentDateTV.setText(appointment.date);
        return lineView;
    }
}
