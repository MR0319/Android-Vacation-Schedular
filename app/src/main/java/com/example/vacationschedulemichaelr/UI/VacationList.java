package com.example.vacationschedulemichaelr.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.vacationschedulemichaelr.R;
import com.example.vacationschedulemichaelr.database.Repository;
import com.example.vacationschedulemichaelr.entities.Excursion;
import com.example.vacationschedulemichaelr.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class VacationList extends AppCompatActivity {

private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_list);
        FloatingActionButton fab=findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VacationList.this, VacationDetails.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerVacationList);
        repository = new Repository(getApplication());
        List<Vacation> allVacations = repository.getmAllVacations();
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacation(allVacations);
       // System.out.println(getIntent().getStringExtra("test"));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;
    }

    protected void onResume(){

        super.onResume();
        List<Vacation> allVacations = repository.getmAllVacations();
        RecyclerView recyclerView = findViewById(R.id.recyclerVacationList);
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacation(allVacations);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        /*
        if(item.getItemId()==R.id.sample){
           // Toast.makeText(VacationList.this,"put in sample data", Toast.LENGTH_LONG).show();

            repository = new Repository(getApplication());

            //Vacation vacation = new Vacation(0, "Puerto Rico", "Hampton Inn");

           // Excursion excursion = new Excursion(0, "Rope Climbing", 1);
           // repository.insert(vacation);
           // repository.insert(excursion);

            return true;
        }*/
        if (item.getItemId()== android.R.id.home){
            this.finish();
            //Intent intent=new Intent(VacationList.this,VacationDetails.class);
            //startActivity(intent);
        return true;
        }
        return true;
    }
}