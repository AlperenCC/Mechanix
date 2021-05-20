package com.example.myapplication;

import java.io.Serializable;
import java.sql.Statement;

public class Appointment implements Serializable {


    public String requesting;
    public String workplace;
    public String date;
    public String statement;
    public String approved;
    public String dateId;

    public Appointment(String dateId,String requesting, String workplace, String date, String statement, String approved) {
        this.requesting = requesting;
        this.workplace = workplace;
        this.date = date;
        this.statement = statement;
        this.approved = approved;
        this.dateId = dateId;
    }

    public String getDateId() {
        return dateId;
    }

    public void setDateId(String dateId) {
        this.dateId = dateId;
    }

    public String getRequesting() {
        return requesting;
    }

    public void setRequesting(String requesting) {
        this.requesting = requesting;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

}
