package com.example.labtest.service;

import com.example.labtest.entity.Enrollment;
import com.example.labtest.repository.CourseRepository;
import com.example.labtest.repository.EnrollmentRepository;
import com.example.labtest.repository.StudentRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    private EnrollmentRepository enrollmentrepository;
    private StudentRepository studentrepository;
    private CourseRepository courserepository;

    public EnrollmentService(
            EnrollmentRepository enrollmentrepository,
            StudentRepository studentrepository,
            CourseRepository courserepository) {

        this.enrollmentrepository = enrollmentrepository;
        this.studentrepository = studentrepository;
        this.courserepository = courserepository;
    }

    // GET ALL
    public List<Enrollment> getAll() {
        return enrollmentrepository.getAll();
    }

    // GET BY ID
    public Optional<Enrollment> getById(int id) {
        return enrollmentrepository.getById(id);
    }

    // CREATE ENROLLMENT
    public void save(Enrollment enrollment) {

        enrollmentrepository.save(
                enrollment,
                studentrepository.getAll(),
                courserepository.getAll()
        );
    }

    // UPDATE GRADE
    public void updateGrade(int id, double grade) {
        enrollmentrepository.updateGrade(id, grade);
    }

    // DELETE
    public void delete(int id) {
        enrollmentrepository.delete(id);
    }
}