package com.example.vacationschedulemichaelr.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vacationschedulemichaelr.R;
import com.example.vacationschedulemichaelr.database.Repository;
import com.example.vacationschedulemichaelr.database.VacationsDatabaseBuilder;
import com.example.vacationschedulemichaelr.entities.Excursion;
import com.example.vacationschedulemichaelr.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {

    int amountExcursions;
    int vacationID;
    String name;
    String hotel;
    EditText editTitle;
    EditText editHotel;
    Repository repository;
    Vacation currentVacation;
    Button buttonStartDate;
    Button buttonEndDate;
    DatePickerDialog.OnDateSetListener myStartDate;
    DatePickerDialog.OnDateSetListener myEndDate;

    final Calendar myCalender = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);
        FloatingActionButton fab=findViewById(R.id.floatingActionButton2);

        editTitle=findViewById(R.id.vacationName);
        editHotel=findViewById(R.id.vacationPlaceOfStay);

        vacationID = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        hotel = getIntent().getStringExtra("hotel");

        editTitle.setText(name);
        editHotel.setText(hotel);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("vacaID", vacationID);
                intent.putExtra("N", name);
                intent.putExtra("H", hotel);
                intent.putExtra("startDate", buttonStartDate.getText().toString());
                intent.putExtra("endDate", buttonEndDate.getText().toString());
                startActivity(intent);
            }
        });


        //Calender / Date Button for START DATE assigned from XML file
        buttonStartDate = findViewById(R.id.startDate);
        String myDateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myDateFormat, Locale.US);
        String currentDate = sdf.format(new Date());
       // buttonStartDate.setText();

        //button end Date assigned from XML file
        buttonEndDate = findViewById(R.id.endDate);
       // buttonEndDate.setText(currentDate);


        //Button Start Date
        buttonStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = buttonStartDate.getText().toString();
                try {
                    myCalender.setTime(sdf.parse(info));
                } catch (ParseException e){
                    e.printStackTrace();
                }
                new  DatePickerDialog(VacationDetails.this,myStartDate,myCalender.get(Calendar.YEAR),
                                        myCalender.get(Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Button End Date
        buttonEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = buttonEndDate.getText().toString();
                try {
                    myCalender.setTime(sdf.parse(info));
                } catch (ParseException e){
                    e.printStackTrace();
                }
                new  DatePickerDialog(VacationDetails.this,myEndDate,myCalender.get(Calendar.YEAR),
                        myCalender.get(Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //DAte picked for Start Date Button
        myStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month,
                                  int dayOfMonth) {
                myCalender.set(Calendar.YEAR, year);
                myCalender.set(Calendar.MONTH, month);
                myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }
        };

        //Date picked for End Date Button
        myEndDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month,
                                  int dayOfMonth) {
                myCalender.set(Calendar.YEAR, year);
                myCalender.set(Calendar.MONTH, month);
                myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();

            }
        };

        RecyclerView recyclerView = findViewById(R.id.recyclerExcursionDetails);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion e : repository.getmAllExcursions()){
            if (e.getVacationID() == vacationID)filteredExcursions.add(e);
        }

        excursionAdapter.setExcursions(filteredExcursions);

        //creates an ID and STARTS WITH -1 SO IT DOESNT GRAB FROM DB AND CHECKS
        int vacationID = getIntent().getIntExtra("id", -1);

        /*  Goes through all of the available Vacations to verify the Vacation
            Selected has a Start Date and End Date if so it grabs the dates
             from the Database and adds them to Buttons
        */
            //Vacaton vacation : repository For loops goes through all vacations

        for (Vacation vacation : repository.getmAllVacations()) {
            if (vacation.getVacationID() == vacationID) {
                if (vacation.getStartDate() != null && vacation.getEndDate() != null) {
                    buttonStartDate.setText(vacation.getStartDate());
                    buttonEndDate.setText(vacation.getEndDate());
                }
                break;
            }
        }
    }

    //Update LAbel for Date Button
    private void updateLabel(){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        buttonStartDate.setText(sdf.format(myCalender.getTime()));
    }

    private void updateLabelEnd(){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        buttonEndDate.setText(sdf.format(myCalender.getTime()));
    }



    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_vacationdetails,menu);
        return true;
    }

    //Grabs vacation SAVES OR DELETES it also checks for vacationID
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.vacationSave){

            //Strings to verify startDate and end Date
            String startDate = buttonStartDate.getText().toString();
            String endDate = buttonEndDate.getText().toString();

            //Strings used to verify the contents within the text fields are not empty
            String vacationName = editTitle.getText().toString();
            String hotelName = editHotel.getText().toString();

            //Validation for Vacation Name
            if (TextUtils.isEmpty(vacationName)){
                Toast.makeText(VacationDetails.this, "Please enter a NAME for the Vacation", Toast.LENGTH_SHORT).show();
                return false;
            }

            //Validation for the Hotel Name
            if (TextUtils.isEmpty(hotelName)){
                Toast.makeText(VacationDetails.this, "Please enter a HOTEL for your Vacation", Toast.LENGTH_SHORT).show();
                return false;
            }

            //If either start date or the end date is null a Toast appears and it DOESN'T SAVE TO DATABASE
            if (TextUtils.isEmpty(startDate) || TextUtils.isEmpty(endDate)) {
                Toast.makeText(VacationDetails.this, "Please select the Start and end Dates", Toast.LENGTH_SHORT).show();
                return false;
            }

            SimpleDateFormat sdft = new SimpleDateFormat("MM/dd/yy", Locale.US);
            Date parsedStartDate;
            Date parsedEndDate;

            //Verification to make sure Dates are being used
            try {
                parsedStartDate = sdft.parse(startDate);
                parsedEndDate = sdft.parse(endDate);
            } catch (ParseException e) {
                Toast.makeText(VacationDetails.this, "Please Select Dates", Toast.LENGTH_LONG).show();
                return false;
            }


            //Validation to guarantee end date is after start date
            if (parsedEndDate.before(parsedStartDate)) {
                Toast.makeText(VacationDetails.this, "End date cannot be BEFORE Start date", Toast.LENGTH_LONG).show();
                return false;
            }

            Vacation vacation;
            if (vacationID == -1){
                if (repository.getmAllVacations().size() == 0) vacationID = 1;
                else vacationID = repository.getmAllVacations().get(repository.getmAllVacations().size() - 1).getVacationID() + 1;
                vacation = new Vacation(vacationID, editTitle.getText().toString(), editHotel.getText().toString(),
                                        buttonStartDate.getText().toString(), buttonEndDate.getText().toString());
                repository.insert(vacation);
                this.finish();
            }
            else {
                vacation = new Vacation(vacationID, editTitle.getText().toString(), editHotel.getText().toString(),
                                        buttonStartDate.getText().toString(), buttonEndDate.getText().toString());
                repository.update(vacation);
                this.finish();
            }
        }

        //DELETES Vacations also doesn't allow deletion if vacation has an excursion
        if (item.getItemId() == R.id.vacationDelete){
            for (Vacation vaca: repository.getmAllVacations()){
                if (vaca.getVacationID() == vacationID)currentVacation = vaca;
            }
            amountExcursions = 0;
            for (Excursion excursion: repository.getmAllExcursions()){
                if(excursion.getVacationID() == vacationID)++amountExcursions;
            }
            if (amountExcursions == 0){
                repository.delete(currentVacation);
                Toast.makeText(VacationDetails.this, currentVacation.getVacationName() + " was deleted", Toast.LENGTH_LONG).show();
                VacationDetails.this.finish();
            }
            else {
                Toast.makeText(VacationDetails.this, "Can't delete a Vacation with Excursions", Toast.LENGTH_LONG).show();
            }
        }

        //Back button
        if (item.getItemId() == R.id.backButton){
            Intent intent = new Intent(VacationDetails.this, VacationList.class);
            startActivity(intent);
        }

        //NOTIFICATION which displays on day of vacation and ending day while displaying the name of Vacation
        if (item.getItemId() == R.id.vacationNotify){

            String dataFromStartDate = buttonStartDate.getText().toString();
            String dataFromEndDate = buttonEndDate.getText().toString();

            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            SimpleDateFormat sdfEndDate = new SimpleDateFormat(myFormat, Locale.US);
            Date startDate = null;
            Date endDate = null;

            try {
                startDate = sdf.parse(dataFromStartDate);
                endDate = sdfEndDate.parse(dataFromEndDate);
            } catch (ParseException e){
                e.printStackTrace();
            }

            Intent startingIntent = new Intent(VacationDetails.this, MyReceiver.class);
            startingIntent.putExtra("star",name + " Vacation Starting");

            Intent endingIntent = new Intent(VacationDetails.this, MyReceiver.class);
            endingIntent.putExtra("star", name + " Vacation Ending");

            PendingIntent startPendingIntent = PendingIntent.getBroadcast(VacationDetails.this, 0, startingIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            PendingIntent endPendingIntent = PendingIntent.getBroadcast(VacationDetails.this, 1, endingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            /*
            PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent,PendingIntent.FLAG_IMMUTABLE);


            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            */
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null) {

                alarmManager.set(AlarmManager.RTC_WAKEUP, startDate.getTime(), startPendingIntent);

                alarmManager.set(AlarmManager.RTC_WAKEUP, endDate.getTime(), endPendingIntent);
            }
            return true;
        }
        return true;

    }
}