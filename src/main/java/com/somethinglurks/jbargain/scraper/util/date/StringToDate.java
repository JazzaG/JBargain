package com.somethinglurks.jbargain.scraper.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringToDate {

    private StringToDate() {

    }

    /**
     * Parses a date string in the format of dd/mm/yyyy - hh:mm
     *
     * @param string Date string
     * @param includeTime Flag to indicate if string contains a time
     * @return Date object, or null if string is an incorrect format
     */
    public static Date parsePostDate(String string, boolean includeTime) {
        // Grab date values from string
        Pattern pattern = Pattern.compile("([0-9]{2}/[0-9]{2}/[0-9]{4} - [0-9]{2}:[0-9]{2})");
        Matcher matcher = pattern.matcher(string);

        if (!matcher.find()) {
            return null;
        }

        // Build date object
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy - H:m");

        try {
            return format.parse(matcher.group(1));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parses a date string from a deal post
     *
     * @param string Date string
     * @return Array of Date objects where elements are respective to the date string
     */
    public static Date[] parseDealDate(String string) {
        List<String> dates = Arrays.asList(string.split("[\u2013|-]"));

        // Reverse the array to operate on the second date first
        Collections.reverse(dates);

        Pattern pattern = Pattern.compile("([0-9]{1,2})( [A-Za-z]{3}|)( [0-9]{4}|)( [0-9]{1,2}:[0-9]{2}[a-z]{2}|)");
        Matcher matcher;
        DateBuilder builder = new DateBuilder();

        Date[] result = new Date[2];
        int index = dates.size() - 1;

        for (String date : dates) {
            matcher = pattern.matcher(date);

            if (matcher.find()) {
                builder.setDate(matcher.group(1));
                builder.setTime(matcher.group(4));

                // Use year if it's there
                if (matcher.group(3) != null && !matcher.group(3).isEmpty()) {
                    builder.setYear(matcher.group(3));
                } else {
                    String month = matcher.group(2) == null ? "" : matcher.group(2);
                    String currentYear = Calendar.getInstance().get(Calendar.YEAR) + "";

                    if (month.equalsIgnoreCase(builder.getMonth())) {
                        if (builder.getYear() == null || builder.getYear().isEmpty()) {
                            builder.setYear(currentYear);
                        }
                    } else {
                        if (!month.isEmpty()) {
                            builder.setYear(currentYear);
                        }
                    }
                }

                // Use month if it's there
                if (matcher.group(2) != null && !matcher.group(2).isEmpty()) {
                    builder.setMonth(matcher.group(2));
                }

                result[index] = builder.build();
            }

            index--;
        }

        return result;
    }

    public static Date parseCompetitionDate(String string) {
        Pattern pattern = Pattern.compile("([0-9]{1,2}/[0-9]{1,2}/[0-9]{4})( [0-9]{1,2}:[0-9]{2}[a-z]{2}|)");
        Matcher matcher = pattern.matcher(string);

        Date date = null;
        String time;
        if (matcher.find()) {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mma");

            // Set time to midnight if not matched
            if (matcher.group(2) == null || matcher.group(2).isEmpty()) {
                time = "0:00am";
            } else {
                time = matcher.group(2);
            }

            // Build date
            try {
                date = format.parse(String.format("%s %s", matcher.group(1).trim(), time.trim()));
            } catch (ParseException ignored) {
            }
        }

        return date;
    }

}
