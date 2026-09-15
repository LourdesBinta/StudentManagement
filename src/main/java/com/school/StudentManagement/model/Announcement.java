package com.school.StudentManagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "announcements")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AnnouncementID")
    private Long id;

    @Column(name = "ClubID")
    private Integer clubId;

    @Column(name = "Title")
    private String title;

    @Column(name = "Message")
    private String message;

    @Column(name = "posted_by")
    private String postedBy;

    @Column(name = "posted_date")
    private String postedDate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getClubId() { return clubId; }
    public void setClubId(Integer clubId) { this.clubId = clubId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getPostedBy() { return postedBy; }
    public void setPostedBy(String postedBy) { this.postedBy = postedBy; }
    public String getPostedDate() { return postedDate; }
    public void setPostedDate(String postedDate) { this.postedDate = postedDate; }
}