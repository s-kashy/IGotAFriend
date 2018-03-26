package com.example.mycomputer.igotafriend;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * Created by My computer on 1/17/2018.
 */

public class DateFormat {
    public static String formatDateToString(Date date ){
        SimpleDateFormat DATE_FORMAT =new SimpleDateFormat("dd-MM-yyyy");
       return DATE_FORMAT.format(date);
    }
    public static Date formatForStringToDate(String date) {
       SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        try {
            return DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
