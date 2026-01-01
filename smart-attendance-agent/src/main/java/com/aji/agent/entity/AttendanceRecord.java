package com.aji.agent.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "attendance")
public class AttendanceRecord {

    @Id
    private String id;

    private String subjectName;
    private boolean present;
    private LocalDate date = LocalDate.now();
    private String day;      // e.g., "Monday"
    private String time;

    public String getId() { return id; }
    public String getSubjectName() { return subjectName; }
    public boolean isPresent() { return present; }
    public LocalDate getDate() { return date; }

    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    public void setPresent(boolean present) { this.present = present; }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
