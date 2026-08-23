package com.example.labtest.api;

import com.example.labtest.entity.Enrollment;
import com.example.labtest.service.EnrollmentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentAPI {

    private EnrollmentService enrollmentService;

    public EnrollmentAPI(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    // 8. POST /api/enrollments
    @PostMapping
    public void enroll(@RequestBody Enrollment enrollment) {
        enrollmentService.save(enrollment);
    }

    // 9. PATCH /api/enrollments/{id}/grade
    @PatchMapping("/{id}/grade")
    public void updateGrade(
            @PathVariable int id,
            @RequestBody Double grade) {

        enrollmentService.updateGrade(id, grade);
    }
}