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

    // GET BY STUDENT
    public List<Enrollment> getByStudentId(int studentId) {

        return enrollments.stream()
                .filter(e ->
                        e.getStudentId() == studentId)
                .toList();
    }

    // GET BY COURSE
    public List<Enrollment> getByCourseId(int courseId) {

        return enrollments.stream()
                .filter(e ->
                        e.getCourseId() == courseId)
                .toList();
    }

    // CREATE ENROLLMENT
    public void save(
            Enrollment enrollment,
            List<Student> students,
            List<Course> courses) {

        // Check student
        boolean studentExists = students.stream()
                .anyMatch(s ->
                        s.getId() == enrollment.getStudentId());

        if (!studentExists) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Student not found"
            );
        }

        // Find course
        Course course = courses.stream()
                .filter(c ->
                        c.getId() == enrollment.getCourseId())
                .findFirst()
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Course not found"
                        ));

        // Check duplicate enrollment
        boolean alreadyEnrolled = enrollments.stream()
                .anyMatch(e ->
                        e.getStudentId() ==
                                enrollment.getStudentId()
                                &&
                                e.getCourseId() ==
                                        enrollment.getCourseId()
                                &&
                                e.getSemester()
                                        .equalsIgnoreCase(
                                                enrollment.getSemester()
                                        )
                );

        if (alreadyEnrolled) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Student already enrolled in this course for this semester"
            );
        }

        // Check capacity
        long seatFilled = enrollments.stream()
                .filter(e ->
                        e.getCourseId() ==
                                enrollment.getCourseId()
                                &&
                                e.getSemester()
                                        .equalsIgnoreCase(
                                                enrollment.getSemester()
                                        )
                )
                .count();

        if (seatFilled >= course.getCapacity()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Course capacity exceeded"
            );
        }

        enrollments.add(enrollment);
    }

    // UPDATE GRADE
    public void updateGrade(int id, Double grade) {

        if (grade != null &&
                (grade < 0 || grade > 4)) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Grade must be between 0.00 and 4.00"
            );
        }

        Enrollment enrollment = getById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Enrollment not found"
                        ));

        enrollment.setGrade(grade);
    }

    // DELETE
    public void delete(int id) {

        getById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Enrollment not found"
                        ));

        enrollments.removeIf(e ->
                e.getId() == id);
    }
}