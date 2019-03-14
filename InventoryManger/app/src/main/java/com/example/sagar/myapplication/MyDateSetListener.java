package com.example.sagar.myapplication;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

import java.util.Calendar;

public class MyDateSetListener implements DatePickerDialog.OnDateSetListener {

    Calendar calendar = Calendar.getInstance();

    public MyDateSetListener() {
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

        calendar.set(Calendar.DATE, dayOfMonth);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.YEAR, year);
    }

    public Calendar getTime() {
        return calendar;
    }
}
