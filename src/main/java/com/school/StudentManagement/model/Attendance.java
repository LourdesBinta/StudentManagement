package com.school.StudentManagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AttendanceID")
    private Long id;

    @Column(name = "EventID")
    private Integer eventId;

    @Column(name = "StudentID")
    private Integer studentId;

    @Column(name = "attendance_status")
    private String attendanceStatus;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getEventId() { return eventId; }
    public void setEventId(Integer eventId) { this.eventId = eventId; }
    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }
    public String getAttendanceStatus() { return attendanceStatus; }
    public void setAttendanceStatus(String attendanceStatus) { this.attendanceStatus = attendanceStatus; }
}