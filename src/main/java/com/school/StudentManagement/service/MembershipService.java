package com.school.StudentManagement.service;

import com.school.StudentManagement.model.Membership;
import com.school.StudentManagement.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MembershipService {

    @Autowired
    private MembershipRepository membershipRepository;

    public List<Membership> getAllMemberships() { return membershipRepository.findAll(); }
    public Membership getMembershipById(Long id) { return membershipRepository.findById(id).orElse(null); }
    public Membership saveMembership(Membership membership) { return membershipRepository.save(membership); }
    public void markAsInactive(Long id) {
        Membership membership = membershipRepository.findById(id).orElse(null);
        if (membership != null) {
            membership.setStatus("Inactive");
            membershipRepository.save(membership);
        }
    }
}