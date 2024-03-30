package com.example.vacationschedulemichaelr.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.vacationschedulemichaelr.dao.ExcursionDAO;
import com.example.vacationschedulemichaelr.dao.VacationDAO;
import com.example.vacationschedulemichaelr.entities.Excursion;
import com.example.vacationschedulemichaelr.entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version = 3, exportSchema = false)
public abstract class VacationsDatabaseBuilder extends RoomDatabase {

    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();
    private static volatile VacationsDatabaseBuilder INSTANCE;

    static VacationsDatabaseBuilder getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (VacationsDatabaseBuilder.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), VacationsDatabaseBuilder.class,"MyVacationDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }


        }
        return INSTANCE;
    }


}
