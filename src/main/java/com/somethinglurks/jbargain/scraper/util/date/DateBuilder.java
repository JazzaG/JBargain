package com.somethinglurks.jbargain.scraper.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateBuilder {

    private String date;
    private String month;
    private String year;
    private String time;

    public DateBuilder() {

    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setTime(String time) {
        if (time == null || time.isEmpty()) {
            time = "0:00am";
        }
        this.time = time;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public Date build() {
        DateFormat format = new SimpleDateFormat("dd MMM yyyy hh:mma");

        try {
            return format.parse(String.format("%s %s %s %s", date.trim(), month.trim(), year.trim(), time.trim()));
        } catch (ParseException e) {
            return null;
        }
    }

}
