package com.example.labtest.service;

import com.example.labtest.entity.Student;
import com.example.labtest.repository.EnrollmentRepository;
import com.example.labtest.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private StudentRepository studentRepository;
    private EnrollmentRepository enrollmentRepository;

    public StudentService(StudentRepository studentRepository,
                          EnrollmentRepository enrollmentRepository) {

        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public void save(Student student) {
        studentRepository.save(student);
    }

    public List<Student> getAll(String department) {

        if (department == null) {
            return studentRepository.getAll();
        }

        return studentRepository.getByDepartment(department);
    }

    public Student getById(int id) {
        return studentRepository.getById(id)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));
    }

    public void update(Student student, int id) {
        studentRepository.update(student, id);
    }

    public void delete(int id) {
        studentRepository.delete(id);
    }
    }