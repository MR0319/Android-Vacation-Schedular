package com.example.vacationschedulemichaelr.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.example.vacationschedulemichaelr.R;
import com.example.vacationschedulemichaelr.database.Repository;
import com.example.vacationschedulemichaelr.entities.Excursion;
import com.example.vacationschedulemichaelr.entities.Vacation;

import java.util.Date;
import java.util.Locale;


public class ExcursionDetails extends AppCompatActivity {

    int excursionID;
    EditText editExcursionName;
    String excursionName;
    Button buttonDate;
    Repository repository;
    EditText editNote;
    DatePickerDialog.OnDateSetListener excursionDate;
    int vacaID;
    String vacationName;
    String vacationHotel;
    String vacationStartDate;
    String vacationEndDate;
    Excursion currentExcursion;

    final Calendar myCalender = Calendar.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);

        repository = new Repository(getApplication());

        excursionName = getIntent().getStringExtra("name");
        editExcursionName = findViewById(R.id.excursioneditText);
        editExcursionName.setText(excursionName);

        excursionID = getIntent().getIntExtra("id", -1);
        vacaID = getIntent().getIntExtra("vacaID", -1);

        vacationName = getIntent().getStringExtra("N");
        vacationHotel = getIntent().getStringExtra("H");

        vacationStartDate = getIntent().getStringExtra("startDate");
        vacationEndDate = getIntent().getStringExtra("endDate");

        if (vacationName == null){
            for (Vacation vacation: repository.getmAllVacations()){
                if (vacation.getVacationID() == vacaID){
                    vacationName = vacation.getVacationName();
                    vacationHotel = vacation.getHotelStayLocation();
                    vacationStartDate = vacation.getStartDate();
                    vacationEndDate = vacation.getEndDate();
                }
            }
        }
        editNote = findViewById(R.id.note);

        buttonDate = findViewById(R.id.excursionDateButtonEx);
        String myDateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myDateFormat, Locale.US);

        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;

                String info = buttonDate.getText().toString();
                try {
                    myCalender.setTime(sdf.parse(info));
                } catch (ParseException e){
                    e.printStackTrace();
                }
                new  DatePickerDialog(ExcursionDetails.this,excursionDate,myCalender.get(Calendar.YEAR),
                        myCalender.get(Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        excursionDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month,
                                  int dayOfMonth) {
                myCalender.set(Calendar.YEAR, year);
                myCalender.set(Calendar.MONTH, month);
                myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }
        };


        int excursionID2 = getIntent().getIntExtra("id", -1);

        //Update button on Excursions so that the excursion day stays for items in the Database
        for (Excursion excursion: repository.getmAllExcursions()) {
            if (excursion.getExcursionID() == excursionID2) {
                if (excursion.getExcursionDay() != null) {
                    buttonDate.setText(excursion.getExcursionDay());
                }
                break;
            }
        }

        Spinner spinner = findViewById(R.id.spinner);
        ArrayList<Vacation> vacationArrayList = new ArrayList<>();
        vacationArrayList.addAll(repository.getmAllVacations());

        ArrayAdapter<Vacation>vacationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, vacationArrayList);
        spinner.setAdapter(vacationAdapter);
    }
    private void updateLabel(){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        buttonDate.setText(sdf.format(myCalender.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursiondetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if ( item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        // return true;
//                Intent intent=new Intent(PartDetails.this,MainActivity.class);
//                startActivity(intent);
//                return true;

        if (item.getItemId() == R.id.excursionsave) {
            Excursion excursion;

            String excursionTipDay = buttonDate.getText().toString();
            String startDate = vacationStartDate;
            String endDate = vacationEndDate;

            String excursionName = editExcursionName.getText().toString();

            //Validation for Excursion Title!
            if (TextUtils.isEmpty(excursionName)){
                Toast.makeText(ExcursionDetails.this,"Please enter a Name for the Excursion!", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (TextUtils.isEmpty(excursionTipDay)){
                Toast.makeText(ExcursionDetails.this, "Please select a Date for the Excursion!", Toast.LENGTH_LONG).show();
                return false;
            }

            SimpleDateFormat sdft = new SimpleDateFormat("MM/dd/yy", Locale.US);
            Date parsedExcursionDate;
            Date startingVacationDate;
            Date endingVacationDate;

            //Verification so a Date is chosen for Excursions
            try {
                 parsedExcursionDate = sdft.parse(excursionTipDay);
                 startingVacationDate = sdft.parse(startDate);
                 endingVacationDate = sdft.parse(endDate);
            } catch (ParseException e) {
                Log.e("VacationDetails", "Error parsing dates:", e);
                Toast.makeText(ExcursionDetails.this, "Please Select an Excursion Date", Toast.LENGTH_LONG).show();
                return false;
            }

            //Verification so excursion date is chosen during vacation days
            if(parsedExcursionDate.before(startingVacationDate) || parsedExcursionDate.after(endingVacationDate)){
                Toast.makeText(ExcursionDetails.this, "Please select Date Between Start & End Dates", Toast.LENGTH_LONG).show();
                return false;
            }
            if (excursionID == -1) {
                if (repository.getmAllExcursions().size() == 0)
                    excursionID = 1;
                else
                    excursionID = repository.getmAllExcursions().get(repository.getmAllExcursions().size() - 1).getExcursionID() + 1;
                excursion = new Excursion(excursionID, editExcursionName.getText().toString(), buttonDate.getText().toString(), vacaID);
                repository.insert(excursion);
            } else {
                excursion = new Excursion(excursionID, editExcursionName.getText().toString(), buttonDate.getText().toString(), vacaID);
                repository.update(excursion);
            }

            finish();

            Intent intent = new Intent(ExcursionDetails.this, VacationList.class);
            startActivity(intent);

            return true;
        }

        if (item.getItemId() == R.id.excursionDelete){
            for (Excursion excursion:repository.getmAllExcursions()){
                if (excursion.getExcursionID() == excursionID)currentExcursion = excursion;
            }
            repository.delete(currentExcursion);
            Toast.makeText(ExcursionDetails.this, currentExcursion.getExcursionName() + " was deleted",Toast.LENGTH_LONG).show();
            ExcursionDetails.this.finish();
            Intent intent = new Intent(ExcursionDetails.this, VacationList.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.share){
            Intent sentIntent = new Intent();
            sentIntent.setAction(Intent.ACTION_SEND);
            sentIntent.putExtra(Intent.EXTRA_TEXT, editNote.getText().toString() + "Vacation Name: " + vacationName + "\nHotel: " + vacationHotel +
                                                                                        "\nStarts: " + vacationStartDate + "\nEnds: " + vacationEndDate);
            sentIntent.putExtra(Intent.EXTRA_TITLE, editNote.getText().toString()+ "EXTRA_TITLE");
            sentIntent.setType("text/plain");
            Intent shareIntent=Intent.createChooser(sentIntent, null);
            startActivity(shareIntent);
            return true;
        }

        if (item.getItemId() == R.id.notify){
            String date = buttonDate.getText().toString();

            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            Date excursionDate = null;

            try {
                excursionDate = sdf.parse(date);
            } catch (ParseException e){
                e.printStackTrace();
            }

            Intent startingIntent = new Intent(ExcursionDetails.this, MyReceiverExcursion.class);
            startingIntent.putExtra("mulch",excursionName + " Excursion Starting");

            PendingIntent excursionPendingIntent = PendingIntent.getBroadcast(ExcursionDetails.this, 3, startingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            if (alarmManager != null) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, excursionDate.getTime(), excursionPendingIntent);
            }
            return true;

        }

        return super.onOptionsItemSelected(item);
    }
}