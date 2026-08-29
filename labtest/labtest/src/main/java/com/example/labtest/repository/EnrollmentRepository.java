package com.example.labtest.repository;

import com.example.labtest.entity.Course;
import com.example.labtest.entity.Enrollment;
import com.example.labtest.entity.Student;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EnrollmentRepository {

    static List<Enrollment> enrollments = new ArrayList<>();

    static {
        enrollments.add(new Enrollment(
                1, 1, 1, "Spring 2025", 3.75
        ));

        enrollments.add(new Enrollment(
                2, 1, 2, "Spring 2025", 3.50
        ));

        enrollments.add(new Enrollment(
                3, 2, 1, "Spring 2025", 3.00
        ));

        enrollments.add(new Enrollment(
                4, 2, 3, "Fall 2025", 2.75
        ));

        enrollments.add(new Enrollment(
                5, 3, 4, "Spring 2025", 3.25
        ));

        enrollments.add(new Enrollment(
                6, 4, 1, "Fall 2025", 2.50
        ));

        enrollments.add(new Enrollment(
                7, 5, 1, "Spring 2026", null
        ));

        enrollments.add(new Enrollment(
                8, 5, 3, "Spring 2026", null
        ));
    }

    // GET ALL
    public List<Enrollment> getAll() {
        return enrollments;
    }

    // GET BY ID
    public Optional<Enrollment> getById(int id) {

        return enrollments.stream()
                .filter(e -> e.getId() == id)
                .findFirst();
    }

        // CREATE ENROLLMENT
        public void save(
                Enrollment enrollment,
                List<Student> students,
                List<Course> courses) {

            // Check student
            boolean studentExists = students.stream()
                    .anyMatch(s ->
                            s.getId() == enrollment.getStudentId()
                    );

            if (!studentExists) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Student not found"
                );
            }

            // Check course
            boolean courseExists = courses.stream()
                    .anyMatch(c ->
                            c.getId() == enrollment.getCourseId()
                    );

            if (!courseExists) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course not found"
                );
            }

            // Check duplicate enrollment
            boolean alreadyEnrolled = enrollments.stream()
                    .anyMatch(e ->
                            e.getStudentId() == enrollment.getStudentId()
                                    &&
                                    e.getCourseId() == enrollment.getCourseId()
                                    &&
                                    e.getSemester().equals(
                                            enrollment.getSemester()
                                    )
                    );

            if (alreadyEnrolled) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Student already enrolled in this course for this semester"
                );
            }

            // Check course capacity
            for (Course c : courses) {

                if (c.getId() == enrollment.getCourseId()) {

                    long seatFilled = enrollments.stream()
                            .filter(e ->
                                    e.getCourseId() == enrollment.getCourseId()
                                            &&
                                            e.getSemester().equals(
                                                    enrollment.getSemester()
                                            )
                            )
                            .count();

                    if (seatFilled >= c.getCapacity()) {
                        throw new ResponseStatusException(
                                HttpStatus.CONFLICT,
                                "Course capacity exceeded"
                        );
                    }
                }


            // Add enrollment
            enrollments.add(enrollment);
        }
    }

// UPDATE GRADE
public void updateGrade(int id, double grade) {

    for (Enrollment e : enrollments) {

        if (e.getId() == id) {

            e.setGrade(grade);

            return;
        }
    }

}

// DELETE
    public void delete(int id) {
        enrollments.removeIf(e ->
                e.getId() == id);
    }
}