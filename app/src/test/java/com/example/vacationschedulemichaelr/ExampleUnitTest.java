package com.example.vacationschedulemichaelr;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.vacationschedulemichaelr.UI.VacationAdapter;
import com.example.vacationschedulemichaelr.entities.Vacation;

import java.util.Collections;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void name() {

    }

    @Test
    public void testVacationNameEntered() {
        int vacationID = 0;
        String vacationName = "gfdgfdgd";
        String hotelStayLocation = "FDSFSDFSD";
        String startDate = "432423";
        String endDate = "543543543";


        // Create a Vacation object with a name
        Vacation vacation = new Vacation(vacationID, vacationName, hotelStayLocation, startDate, endDate);
        vacation.setVacationName("Test Vacation");

        // Check if the name is entered correctly
        assertEquals("Test Vacation", vacation.getVacationName());

        Vacation vacationEmptyName = new Vacation(vacationID, vacationName, hotelStayLocation, startDate, endDate);
        vacationEmptyName.setVacationName("");

        assertTrue(vacationEmptyName.getVacationName().isEmpty());
    }


}
