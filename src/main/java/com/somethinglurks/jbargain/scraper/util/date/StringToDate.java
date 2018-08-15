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
     * Parses the post date
     *
     * @param string Date string
     * @param includeTime Flag to indicate if string contains a time
     * @return Date object, or null if string is an incorrect format
     */
    public static Date parsePostDate(String string, boolean includeTime) {
        // Determine how to parse the date
        Matcher matcher = Pattern.compile("[0-9]+ min ago").matcher(string);
        if (matcher.find()) {
            return parseRelativePostDate(string);
        } else {
            return parseFormattedPostDate(string, includeTime);
        }
    }

    /**
     * Parses a relative post date, i.e "15 hours 6 min ago"
     *
     * @param string Date string
     * @return Date object, or null if string did not contain a relative post date
     */
    public static Date parseRelativePostDate(String string) {
        Matcher matcher = Pattern.compile("(?:([0-9]+) hour[s]? )?(?:([0-9]+) min)").matcher(string);

        if (!matcher.find()) {
            return null;
        }

        int hoursAgo = matcher.group(1) == null ? 0 : Integer.parseInt(matcher.group(1)); // hours may not be present
        int minutesAgo = Integer.parseInt(matcher.group(2));

        long now = System.currentTimeMillis();
        now -= hoursAgo * (1000 * 60 * 60); // subtract hours in ms
        now -= minutesAgo * (1000 * 60); // subtract minutes in ms

        return new Date(now);
    }

    /**
     * Parses a post date in the format of dd/mm/yyyy - hh:mm
     *
     * @param string Date string
     * @param includeTime Flag to indicate if the date string contains the time
     * @return Date object, or null if the date could not be found in the string
     */
    public static Date parseFormattedPostDate(String string, boolean includeTime) {
        // Grab date values from string
        Pattern pattern = Pattern.compile("([0-9]{2}/[0-9]{2}/[0-9]{4} - [0-9]{2}:[0-9]{2})");
        Matcher matcher = pattern.matcher(string);

        if (!matcher.find()) {
            return null;
        }

        // Build date object
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy" + (includeTime ? " - H:m" : ""));

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
