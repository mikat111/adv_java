package com.example.labtest.service;

import com.example.labtest.entity.Student;
import com.example.labtest.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private StudentRepository studentrepository;

    public StudentService(StudentRepository studentrepository) {
        this.studentrepository = studentrepository;
    }

    // GET ALL
    public List<Student> getAll() {
        return studentrepository.getAll();
    }

    // GET BY ID
    public Student getById(int id) {
        return studentrepository.getById(id);
    }

    // FILTER BY DEPARTMENT
    public List<Student> getByDepartment(String department) {
        return studentrepository.getByDepartment(department);
    }

    // CREATE
    public void save(Student student) {
        studentrepository.save(student);
    }

    // UPDATE
    public void update(Student student, int id) {
        studentrepository.update(student, id);
    }

    // DELETE
    public void delete(int id) {
        studentrepository.delete(id);
    }
}