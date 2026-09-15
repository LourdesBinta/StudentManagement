package com.school.StudentManagement.service;

import com.school.StudentManagement.model.Announcement;
import com.school.StudentManagement.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }

    public List<Announcement> getAnnouncementsByClub(Integer clubId) {
        return announcementRepository.findByClubId(clubId);
    }

    public Announcement saveAnnouncement(Announcement announcement) {
        return announcementRepository.save(announcement);
    }

    public void deleteAnnouncement(Long id) {
        announcementRepository.deleteById(id);
    }

    public Announcement getAnnouncementById(Long id) {
        return announcementRepository.findById(id).orElse(null);
    }
}