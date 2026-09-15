package com.school.StudentManagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EventID")
    private Long id;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "event_date")
    private String eventDate;

    @Column(name = "event_time")
    private String eventTime;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    @Column(name = "school_year")
    private String schoolYear;

    @Column(name = "ClubID")
    private Integer clubId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }
    public String getEventDate() { return eventDate; }
    public void setEventDate(String eventDate) { this.eventDate = eventDate; }
    public String getEventTime() { return eventTime; }
    public void setEventTime(String eventTime) { this.eventTime = eventTime; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getSchoolYear() { return schoolYear; }
    public void setSchoolYear(String schoolYear) { this.schoolYear = schoolYear; }
    public Integer getClubId() { return clubId; }
    public void setClubId(Integer clubId) { this.clubId = clubId; }
}