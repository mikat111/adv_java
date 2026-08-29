package com.example.labtest.api;

import com.example.labtest.entity.Course;
import com.example.labtest.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseAPI {

    private CourseService courseservice;

    public CourseAPI(CourseService courseservice) {
        this.courseservice = courseservice;
    }

    // GET ALL
    @GetMapping
    public List<Course> getAll() {
        return courseservice.getAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Course getById(@PathVariable int id) {

        return courseservice.getById(id)
                .orElse(null);
    }

    // FILTER BY MIN CREDIT
    @GetMapping("/filter")
    public List<Course> getByMinCredit(
            @RequestParam double minCredit) {

        return courseservice.getByMinCredit(minCredit);
    }

    // CREATE
    @PostMapping
    public void save(@RequestBody Course course) {
        courseservice.save(course);
    }

    // UPDATE
    @PutMapping("/{id}")
    public void update(
            @PathVariable int id,
            @RequestBody Course course) {

        courseservice.update(course, id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        courseservice.delete(id);
    }
}
