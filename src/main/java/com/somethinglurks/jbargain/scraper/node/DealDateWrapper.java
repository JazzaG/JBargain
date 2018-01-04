package com.somethinglurks.jbargain.scraper.node;

import com.somethinglurks.jbargain.scraper.util.date.StringToDate;
import org.jsoup.nodes.Element;

import java.util.Date;

public class DealDateWrapper {

    private Element element;

    private Date startDate;
    private Date endDate;

    public DealDateWrapper(Element element) {
        this.element = element;

        loadDates();
    }

    private void loadDates() {
        String dateString = element.select("span.nodeexpiry").text();

        // Remove any prefixes
        dateString = dateString.replaceAll("^[^0-9]", "");

        // Remove marker text
        String markerText = element.select("span.nodeexpiry span.marker").text();
        dateString = dateString.replace(markerText, "").trim();

        // Parse dates
        Date[] dates = StringToDate.parseDealDate(dateString);

        // If the span containing the dates has an 'inactive' class, then the first date
        //   is the start date and second date is the end date
        // If the class does not exist, then the deal has already started and the first
        //   date is the end date

        if (element.select("span.nodeexpiry").hasClass("inactive")) {
            startDate = dates[0];
            endDate = dates[1];
        } else {
            endDate = dates[0];
        }
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
