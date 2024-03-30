package com.example.vacationschedulemichaelr.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "excursions")
public class Excursion {

    @PrimaryKey(autoGenerate = true)
    private int excursionID;
    private String excursionName;
    private String excursionDay;
    //@ColumnInfo(name = "vacationID")
    private int vacationID;



    public Excursion(int excursionID, String excursionName, String excursionDay, int vacationID) {
        this.excursionID = excursionID;
        this.excursionName = excursionName;
        this.excursionDay = excursionDay;
        this.vacationID = vacationID;
    }

    public int getExcursionID() {
        return excursionID;
    }

    public void setExcursionID(int excursionID) {
        this.excursionID = excursionID;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }
    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }


    public String getExcursionDay() {
        return excursionDay;
    }

    public void setExcursionDay(String excursionDay) {
        this.excursionDay = excursionDay;
    }
}
