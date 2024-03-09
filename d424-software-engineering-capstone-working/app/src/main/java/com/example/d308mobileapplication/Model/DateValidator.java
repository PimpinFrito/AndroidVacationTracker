package com.example.d308mobileapplication.Model;

import android.icu.text.SimpleDateFormat;

import java.text.ParseException;
import java.util.Date;

public class DateValidator {
    public static boolean validDate(String date){

        //Expecting MM/DD/YYYY
        String[] dateArray = date.split("/");
        if(dateArray.length != 3) return false;


        int month = Integer.parseInt(dateArray[0]);
        int day = Integer.parseInt(dateArray[1]);
        String year = dateArray[2];

        if(month >12 || month  < 1) return false;
        if(day > 31 || day < 1) return false;
        //Just need to check if format is YYYY
        //Will return result as it's the last check
        return year.length() == 4;
    }

    public static boolean startsBefore(String startDate, String endDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date startDateParsed = dateFormat.parse(startDate);
        Date endDateParsed = dateFormat.parse(endDate);

        //If they are equal return true
        if (startDate.equals(endDate)) return true;
        //Otherwise return if one starts before the other
        return (startDateParsed.before(endDateParsed)
                || startDateParsed.equals(endDateParsed));
    }
    public static String getCurrentDate(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(date);
    }

    public static long getDateInMilliseconds(String passedDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        long milliseconds = 0;
        try{
            Date date = dateFormat.parse(passedDate);
            milliseconds = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;
    }

    public static boolean dateWithinConstraints(String startingConstraints, String endingConstraints,
                                         String targetDate){
        try {
            boolean withinStartingConstraint = startsBefore(startingConstraints, targetDate);
            boolean withinEndingConstraint = startsBefore(targetDate, endingConstraints);
            if(!withinStartingConstraint || !withinEndingConstraint) return false;

        } catch (ParseException e){
            return false;
        }

        return true;


    }
}
