package Java课程实验FX;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Spacecraft {
    private int id;
    private String name;
    private Timestamp launchTime;
    private String launchSite;
    private String summary;
    public Spacecraft() {
        super();
    }
    public Spacecraft(String name, Timestamp launchTime, String launchSite,
                      String summary) {
        super();
        this.name = name;
        this.launchTime = launchTime;
        this.launchSite = launchSite;
        this.summary = summary;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLaunchTime() {
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern
                ("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time=launchTime.toLocalDateTime();
        String localTime=time.format(formatter);
        return localTime;
    }
    public void setLaunchTime(Timestamp launchTime) {
        this.launchTime = launchTime;
    }
    public String getLaunchSite() {
        return launchSite;
    }
    public void setLaunchSite(String launchSite) {
        this.launchSite = launchSite;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }

}
