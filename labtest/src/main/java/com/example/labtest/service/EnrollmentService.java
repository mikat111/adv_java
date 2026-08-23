package com.example.labtest.service;

import com.example.labtest.entity.Enrollment;
import com.example.labtest.repository.CourseRepository;
import com.example.labtest.repository.EnrollmentRepository;
import com.example.labtest.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    private EnrollmentRepository enrollmentRepository;
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;

    public EnrollmentService(
            EnrollmentRepository enrollmentRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository) {

        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public void save(Enrollment enrollment) {

        enrollmentRepository.save(
                enrollment,
                studentRepository.getAll(),
                courseRepository.getAll()
        );
    }

    public List<Enrollment> getAll() {
        return enrollmentRepository.getAll();
    }

    public Enrollment getById(int id) {
        return enrollmentRepository.getById(id).orElse(null);
    }

    public void updateGrade(int id, Double grade) {
        enrollmentRepository.updateGrade(id, grade);
    }

    public void delete(int id) {
        enrollmentRepository.delete(id);
    }
}