package com.school.StudentManagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StudentID")
    private Long id;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "grade")
    private String grade;

    @Column(name = "section")
    private String section;

    @Column(name = "ContactNumber")
    private String contactNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "StudentStatus")
    private String studentStatus;

    @Column(name = "EnrolmentYear")
    private String enrolmentYear;

    @Column(name = "GraduationYear")
    private String graduationYear;

    @Column(name = "DateLeft")
    private String dateLeft;

    @Column(name = "ReasonLeft")
    private String reasonLeft;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getStudentStatus() { return studentStatus; }
    public void setStudentStatus(String studentStatus) { this.studentStatus = studentStatus; }
    public String getEnrolmentYear() { return enrolmentYear; }
    public void setEnrolmentYear(String enrolmentYear) { this.enrolmentYear = enrolmentYear; }
    public String getGraduationYear() { return graduationYear; }
    public void setGraduationYear(String graduationYear) { this.graduationYear = graduationYear; }
    public String getDateLeft() { return dateLeft; }
    public void setDateLeft(String dateLeft) { this.dateLeft = dateLeft; }
    public String getReasonLeft() { return reasonLeft; }
    public void setReasonLeft(String reasonLeft) { this.reasonLeft = reasonLeft; }
}