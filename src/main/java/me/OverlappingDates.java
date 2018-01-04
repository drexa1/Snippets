package me;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OverlappingDates {

    public static void main(String[] args) {
        List<DateRange> ranges = new ArrayList<>();
        ranges.add(new DateRange(LocalDate.of(2017, Month.JANUARY, 17 ), LocalDate.of(2017, Month.MARCH, 7)));
        ranges.add(new DateRange(LocalDate.of(2017, Month.FEBRUARY, 12 ), LocalDate.of(2017, Month.FEBRUARY, 16)));
        ranges.add(new DateRange(LocalDate.of(2017, Month.FEBRUARY, 14 ), LocalDate.of(2017, Month.MARCH, 25)));

        System.out.println("ranges: " + ranges);
        List<DateRange> overlapping = findOverlapping(ranges);
        System.out.println("overlapping intervals: " + overlapping);
    }

    /**
     * Split up each interval into start and end points
     * Sort the points
     * Iterate through the points and create the new intervals between all neighbouring points.
     */
    public static List<DateRange> findOverlapping(List<DateRange> ranges) {
        List<LocalDate> dates = new ArrayList<> ();
        for(DateRange range : ranges) {
            dates.add(range.start);
            dates.add(range.stop);
        }
        // Sort the collection of dates.
        Collections.sort(dates);
        // Loop the sorted dates, creating DateRange objects as we go
        List<DateRange> overlapping = new ArrayList<> ();
        for(int i = 1; i < dates.size(); i++) {
            LocalDate start = dates.get(i - 1); // silly index counting
            LocalDate stop = dates.get(i);
            if(!start.equals(stop)) {  // If equal, ignore and move on to next loop
                DateRange range = new DateRange(start, stop);
                overlapping.add(range);
            }
        }
        return overlapping;
    }
}

class DateRange {
    public LocalDate start , stop ;
    public DateRange(LocalDate start, LocalDate stop) {
        if(stop.isBefore(start)){
            throw new IllegalArgumentException ("The stop date is before the start date." );
        }
        this.start = start;
        this.stop = stop;
    }
    @Override
    public String toString () {
        return "{"+start+","+stop+"}";
    }
}
