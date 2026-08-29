package com.example.labtest.repository;

import com.example.labtest.entity.Course;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CourseRepository {

    static List<Course> courses = new ArrayList<>();

    static {
        courses.add(new Course(
                1,
                "CSE101",
                "Introduction to Programming",
                3.0,
                "Dr. Rahman",
                40
        )
        );

        courses.add(new Course(
                2,
                "CSE201",
                "Data Structures",
                3.0,
                "Dr. Karim",
                35
        )
        );

        courses.add(new Course(
                3,
                "CSE301",
                "Database Systems",
                3.0,
                "Dr. Hasan",
                30
        ));

        courses.add(new Course(
                4,
                "MAT101",
                "Calculus",
                3.0,
                "Dr. Ahmed",
                45
        ));
    }

    // GET ALL
    public List<Course> getAll() {
        return courses;
    }

    // GET BY ID
    public Optional<Course> getById(int id) {

        return courses.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    // FILTER BY MIN CREDIT
    public List<Course> getByMinCredit(double minCredit) {

        return courses.stream()
                .filter(c -> c.getCredit() >= minCredit)
                .toList();
    }

    // Create unique course
    public void save(Course course) {

        for (Course c : courses) {
            if (c.getCode().equalsIgnoreCase(course.getCode())) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Course code already exists"
                );
            }
        }
        courses.add(course);
    }

    // UPDATE
    public void update(Course course, int id) {

        for (Course c : courses) {

            if (c.getId() == id) {

                c.setCode(course.getCode());
                c.setTitle(course.getTitle());
                c.setCredit(course.getCredit());
                c.setInstructor(course.getInstructor());
                c.setCapacity(course.getCapacity());

                return;
            }
        }

    }

    // DELETE
    public void delete(int id) {
        courses.removeIf(c -> c.getId() == id);
    }
}