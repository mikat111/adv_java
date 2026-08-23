package com.example.labtest.api;

import com.example.labtest.entity.Course;
import com.example.labtest.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseAPI {

    private CourseService courseService;

    public CourseAPI(CourseService courseService) {
        this.courseService = courseService;
    }

    // 6. POST /api/courses
    @PostMapping
    public void createCourse(@RequestBody Course course) {
        courseService.save(course);
    }

    // 7. GET /api/courses?minCredit=3
    @GetMapping
    public List<Course> getCourses(
            @RequestParam(required = false) Double minCredit) {

        return courseService.getAll(minCredit);
    }
}