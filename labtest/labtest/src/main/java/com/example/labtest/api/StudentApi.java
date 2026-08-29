package com.example.labtest.api;

import com.example.labtest.entity.Student;
import com.example.labtest.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentApi {

    private StudentService studentservice;

    public StudentApi(StudentService studentservice) {
        this.studentservice = studentservice;
    }

    // GET ALL
    @GetMapping
    public List<Student> getAll() {
        return studentservice.getAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Student getById(@PathVariable int id) {
        return studentservice.getById(id);
    }

    // FILTER BY DEPARTMENT
    @GetMapping("/department/{department}")
    public List<Student> getByDepartment(
            @PathVariable String department) {

        return studentservice.getByDepartment(department);
    }

    // CREATE
    @PostMapping
    public void save(@RequestBody Student student) {
        studentservice.save(student);
    }

    // UPDATE
    @PutMapping("/{id}")
    public void update(
            @PathVariable int id,
            @RequestBody Student student) {

        studentservice.update(student, id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        studentservice.delete(id);
    }
}