package com.school.StudentManagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "clubs")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ClubID")
    private Long id;

    @Column(name = "club_name")
    private String clubName;

    @Column(name = "description")
    private String description;

    @Column(name = "advisor_name")
    private String advisorName;

    @Column(name = "meeting_day")
    private String meetingDay;

    @Column(name = "meeting_time")
    private String meetingTime;

    @Column(name = "max_members")
    private Integer maxMembers;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getClubName() { return clubName; }
    public void setClubName(String clubName) { this.clubName = clubName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getAdvisorName() { return advisorName; }
    public void setAdvisorName(String advisorName) { this.advisorName = advisorName; }
    public String getMeetingDay() { return meetingDay; }
    public void setMeetingDay(String meetingDay) { this.meetingDay = meetingDay; }
    public String getMeetingTime() { return meetingTime; }
    public void setMeetingTime(String meetingTime) { this.meetingTime = meetingTime; }
    public Integer getMaxMembers() { return maxMembers; }
    public void setMaxMembers(Integer maxMembers) { this.maxMembers = maxMembers; }
}