package wallapop;

import sun.reflect.generics.tree.Tree;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Schedule {

    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("y-M-d H:m");

        ArrayList<Show> shows = new ArrayList<Show>();
        shows.add(new Show("Show1", sdf.parse("2013-08-06 18:00"), sdf.parse("2013-08-06 20:00")));
        shows.add(new Show("ShowMorning", sdf.parse("2013-08-06 12:00"), sdf.parse("2013-08-06 15:00")));
        shows.add(new Show("Show2a", sdf.parse("2013-08-06 19:00"), sdf.parse("2013-08-06 21:00")));
        shows.add(new Show("Show2b", sdf.parse("2013-08-06 19:00"), sdf.parse("2013-08-06 21:00")));
        shows.add(new Show("Show3", sdf.parse("2013-08-06 20:00"), sdf.parse("2013-08-06 22:00")));
        shows.add(new Show("Show4", sdf.parse("2013-08-06 22:30"), sdf.parse("2013-08-06 23:45")));
        shows.add(new Show("ShowLong", sdf.parse("2013-08-06 19:30"), sdf.parse("2013-08-06 23:00")));
        shows.add(new Show("ShowNextday2", sdf.parse("2013-08-07 00:30"), sdf.parse("2013-08-07 02:00")));
        shows.add(new Show("ShowNextday1", sdf.parse("2013-08-06 23:30"), sdf.parse("2013-08-07 03:00")));
        shows.add(new Show("ShowNextday3", sdf.parse("2013-08-07 01:30"), sdf.parse("2013-08-07 03:30")));

        List<Show> list = Schedule.findOptimalSchedule(shows);
        for (Show show : list) {
            System.out.println(show.getName());
        }
    }

    public static List<Show> findOptimalSchedule(Collection<Show> shows) {
        List<Show> result = new ArrayList<Show>();


        return result;
    }

}

class Show {
    private String name;
    private Date startTime;
    private Date endTime;

    public Show(String name, Date startTime, Date endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean overlaps(Show anotherShow) {
        return this.endTime.before(anotherShow.startTime) || this.startTime.after(anotherShow.endTime);
    }

    public String getName() {
        return name;
    }
    public Date getStartTime() {
        return startTime;
    }
    public Date getEndTime() {
        return endTime;
    }
}