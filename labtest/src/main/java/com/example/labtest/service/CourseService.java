package com.example.labtest.service;

import com.example.labtest.entity.Course;
import com.example.labtest.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void save(Course course) {
        courseRepository.save(course);
    }

    public List<Course> getAll(Double minCredit) {

        if (minCredit == null) {
            return courseRepository.getAll();
        }

        return courseRepository.getByMinCredit(minCredit);
    }

    public Course getById(int id) {
        return courseRepository.getById(id).orElse(null);
    }

    public void update(Course course, int id) {
        courseRepository.update(course, id);
    }

    public void delete(int id) {
        courseRepository.delete(id);
    }
}