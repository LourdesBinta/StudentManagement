package com.school.StudentManagement.service;

import com.school.StudentManagement.model.Club;
import com.school.StudentManagement.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClubService {

    @Autowired
    private ClubRepository clubRepository;

    public List<Club> getAllClubs() { return clubRepository.findAll(); }
    public Club getClubById(Long id) { return clubRepository.findById(id).orElse(null); }
    public Club saveClub(Club club) { return clubRepository.save(club); }
    public void deleteClub(Long id) { clubRepository.deleteById(id); }
}