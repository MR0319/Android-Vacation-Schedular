package com.example.vacationschedulemichaelr.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "vacations")
public class Vacation {

    @PrimaryKey(autoGenerate = true)
    private int vacationID;
    private String vacationName;
    private String hotelStayLocation;
    private String startDate;
    private String endDate;

    public Vacation(int vacationID, String vacationName, String hotelStayLocation, String startDate, String endDate) {
        this.vacationID = vacationID;
        this.vacationName = vacationName;
        this.hotelStayLocation = hotelStayLocation;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public String getVacationName() {
        return vacationName;
    }

    public void setVacationName(String vacationName) {
        this.vacationName = vacationName;
    }

    public String getHotelStayLocation() {
        return hotelStayLocation;
    }

    public void setHotelStayLocation(String hotelStayLocation) {
        this.hotelStayLocation = hotelStayLocation;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String toString(){
        return vacationName;
    }
}
