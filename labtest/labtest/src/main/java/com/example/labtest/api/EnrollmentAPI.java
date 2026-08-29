package com.example.labtest.api;

import com.example.labtest.entity.Enrollment;
import com.example.labtest.service.EnrollmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentAPI {

    private EnrollmentService enrollmentservice;

    public EnrollmentAPI(EnrollmentService enrollmentservice) {
        this.enrollmentservice = enrollmentservice;
    }

    // GET ALL
    @GetMapping
    public List<Enrollment> getAll() {
        return enrollmentservice.getAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Enrollment getById(@PathVariable int id) {

        return enrollmentservice.getById(id)
                .orElse(null);
    }

    // CREATE ENROLLMENT
    @PostMapping
    public void save(@RequestBody Enrollment enrollment) {
        enrollmentservice.save(enrollment);
    }

    // UPDATE GRADE
    @PatchMapping("/{id}/grade")
    public void updateGrade(
            @PathVariable int id,
            @RequestParam double grade) {

        enrollmentservice.updateGrade(id, grade);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        enrollmentservice.delete(id);
    }
}