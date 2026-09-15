package com.school.StudentManagement.service;

import com.school.StudentManagement.model.Student;
import com.school.StudentManagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public void markAsInactive(Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student != null) {
            student.setStudentStatus("Inactive");
            studentRepository.save(student);
        }
    }
    public Student findByName(String firstName, String lastName) {
        return studentRepository.findAll().stream()
                .filter(s -> s.getFirstName() != null && s.getLastName() != null)
                .filter(s -> s.getFirstName().equalsIgnoreCase(firstName)
                        && s.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }
}