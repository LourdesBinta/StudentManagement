package com.school.StudentManagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "memberships")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MembershipID")
    private Long id;

    @Column(name = "StudentID")
    private Integer studentId;

    @Column(name = "ClubID")
    private Integer clubId;

    @Column(name = "date_joined")
    private String dateJoined;

    @Column(name = "date_left")
    private String dateLeft;

    @Column(name = "status")
    private String status;

    @Column(name = "school_year")
    private String schoolYear;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }
    public Integer getClubId() { return clubId; }
    public void setClubId(Integer clubId) { this.clubId = clubId; }
    public String getDateJoined() { return dateJoined; }
    public void setDateJoined(String dateJoined) { this.dateJoined = dateJoined; }
    public String getDateLeft() { return dateLeft; }
    public void setDateLeft(String dateLeft) { this.dateLeft = dateLeft; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getSchoolYear() { return schoolYear; }
    public void setSchoolYear(String schoolYear) { this.schoolYear = schoolYear; }
}