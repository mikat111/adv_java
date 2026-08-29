package com.example.labtest.service;

import com.example.labtest.entity.Course;
import com.example.labtest.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private CourseRepository courserepository;

    public CourseService(CourseRepository courserepository) {
        this.courserepository = courserepository;
    }

    // GET ALL
    public List<Course> getAll() {
        return courserepository.getAll();
    }

    // GET BY ID
    public Optional<Course> getById(int id) {
        return courserepository.getById(id);
    }

    // FILTER BY MIN CREDIT
    public List<Course> getByMinCredit(double minCredit) {
        return courserepository.getByMinCredit(minCredit);
    }

    // CREATE
    public void save(Course course) {
        courserepository.save(course);
    }

    // UPDATE
    public void update(Course course, int id) {
        courserepository.update(course, id);
    }

    // DELETE
    public void delete(int id) {
        courserepository.delete(id);
    }
}
