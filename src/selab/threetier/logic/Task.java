package selab.threetier.logic;

import selab.threetier.storage.Storage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class Task extends Entity {
    private String title;
    private Date start;
    private Date end;

    public String getTitle() {
        return title;
    }

    public void setTitle(String value) {
        title = value;
    }

    public void setStart(Date value) {
        start = value;
    }

    public String getStartDate() {
        return new SimpleDateFormat("YYYY-MM-DD").format(start);
    }

    public String getStartTime() {
        return new SimpleDateFormat("HH:mm:ss").format(start);
    }

    public void setEnd(Date value) {
        end = value;
    }

    public String getEndTime() {
        return new SimpleDateFormat("HH:mm:ss").format(end);
    }

    public void save() {
        Storage.getInstance().getTasks().addOrUpdate(this);
    }

    public static ArrayList<Task> getAll() {
        return Storage.getInstance().getTasks().getAll();
    }

    public boolean overlaps(Task task) {
        return this.startsAmid(task) || task.startsAmid(this);
    }

    private boolean startsAmid(Task task) {
        if (task.getStartDate().compareTo(this.getStartDate()) > 0 && task.getStartDate().compareTo(new SimpleDateFormat("YYYY-MM-DD").format(end)) < 0) {
            return true;
        }
        if (task.getStartDate().compareTo(this.getStartDate()) == 0) {
            if (this.getStartDate().equals(new SimpleDateFormat("YYYY-MM-DD").format(end))) {

                if (task.getStartTime().compareTo(this.getStartTime()) >= 0 && task.getStartTime().compareTo(this.getEndTime()) <= 0) {
                    return true;
                }
            } else {
                if (task.getStartTime().compareTo(this.getStartTime()) >= 0) {
                    return true;
                }
            }
        }

        if (task.getStartDate().compareTo(new SimpleDateFormat("YYYY-MM-DD").format(end)) == 0) {
                if (task.getStartTime().compareTo(this.getStartTime()) >= 0 && task.getStartTime().compareTo(this.getEndTime()) <= 0) {
                    return true;
                }
        }
        return false;
    }

    public boolean isValid() {
        return this.start.compareTo(this.end) < 0;
    }

    @Override
    public int compareTo(Object o) {
        Task task = (Task) o;
        int result = this.getStartDate().compareTo(task.getStartDate());
        if (result < 0) {
            return -1;
        } else if (result == 0) {
            return this.getStartTime().compareTo(task.getStartTime());
        } else {
            return +1;
        }
    }
}
