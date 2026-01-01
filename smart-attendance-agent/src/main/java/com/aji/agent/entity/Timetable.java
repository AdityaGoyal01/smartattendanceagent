package com.aji.agent.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "timetable")
public class Timetable {

    @Id
    private String id;

    private String subjectName;
    private String day;
    private String time;

    public String getId() { return id; }
    public String getSubjectName() { return subjectName; }
    public String getDay() { return day; }
    public String getTime() { return time; }

    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    public void setDay(String day) { this.day = day; }
    public void setTime(String time) { this.time = time; }
}
