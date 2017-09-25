package wallapop;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by drexa on 8/1/2017.
 */
class Show {

    private String name;
    private Date startTime;
    private Date endTime;

    private static SimpleDateFormat sdf = new SimpleDateFormat("EEE d H:mm");

    public Show(String name, Date startTime, Date endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
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

    @Override
    public String toString() {
        return this.name + " [" + sdf.format(this.startTime) + " - " + sdf.format(this.endTime) + "]";
    }
}
