package com.example.labtest.api;

import com.example.labtest.entity.Student;
import com.example.labtest.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentApi {

    private StudentService studentService;

    public StudentApi(StudentService studentService) {
        this.studentService = studentService;
    }

    // 1. POST /api/students
    @PostMapping
    public void createStudent(@RequestBody Student student) {
        studentService.save(student);
    }

    // 2. GET /api/students
    // GET /api/students?department=CSE
    @GetMapping
    public List<Student> getStudents(
            @RequestParam(required = false) String department) {

        return studentService.getAll(department);
    }

    // 3. GET /api/students/{id}
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable int id) {
        return studentService.getById(id);
    }

    // 4. PUT /api/students/{id}
    @PutMapping("/{id}")
    public void updateStudent(
            @PathVariable int id,
            @RequestBody Student student) {

        studentService.update(student, id);
    }

    // 5. DELETE /api/students/{id}
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable int id) {
        studentService.delete(id);
    }
}