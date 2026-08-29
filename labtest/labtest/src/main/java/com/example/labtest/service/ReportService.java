
        package com.example.labtest.service;

import com.example.labtest.DTO.*;
import com.example.labtest.entity.Course;
import com.example.labtest.entity.Enrollment;
import com.example.labtest.entity.Student;
import com.example.labtest.repository.CourseRepository;
import com.example.labtest.repository.EnrollmentRepository;
import com.example.labtest.repository.StudentRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class ReportService {

    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    private EnrollmentRepository enrollmentRepository;

    public ReportService(
            StudentRepository studentRepository,
            CourseRepository courseRepository,
            EnrollmentRepository enrollmentRepository) {

        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }


    //STUDENT TRANSCRIPT
    public StudentTranscriptDTO getTranscript(int studentId) {

        Student student = studentRepository.getById(studentId);

        List<CourseResultDTO> results = new ArrayList<>();

        double totalCredits = 0;
        double totalPoints = 0;

        for (Enrollment enrollment :
                enrollmentRepository.getAll()) {

            if (enrollment.getStudentId() == studentId) {

                Course course = courseRepository
                        .getById(enrollment.getCourseId())
                        .orElse(null);

                if (course != null) {

                    results.add(new CourseResultDTO(
                            course.getCode(),
                            course.getTitle(),
                            course.getCredit(),
                            enrollment.getSemester(),
                            enrollment.getGrade()
                    ));

                    // Grade >= 2.00 means passed
                    if (enrollment.getGrade() != null &&
                            enrollment.getGrade() >= 2.00) {

                        totalCredits += course.getCredit();

                        totalPoints +=
                                course.getCredit()
                                        * enrollment.getGrade();
                    }
                }
            }
        }

        double cgpa;

        if (totalCredits == 0) {
            cgpa = 0;
        } else {
            cgpa = totalPoints / totalCredits;
        }

        return new StudentTranscriptDTO(
                student,
                results,
                totalCredits,
                cgpa
        );
    }


    // COURSE ROSTER
    public CourseRosterDTO getRoster(int courseId) {

        Course course = courseRepository
                .getById(courseId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Course not found"
                        ));

        List<Student> students = new ArrayList<>();

        long seatFilled = 0;
        double totalGrade = 0;
        int gradedStudents = 0;

        for (Enrollment enrollment :
                enrollmentRepository.getAll()) {

            if (enrollment.getCourseId() == courseId) {

                seatFilled++;

                Student student = studentRepository
                        .getById(enrollment.getStudentId());

                students.add(student);

                if (enrollment.getGrade() != null) {

                    totalGrade += enrollment.getGrade();
                    gradedStudents++;
                }
            }
        }

        long seatsRemaining =
                course.getCapacity() - seatFilled;

        if (seatsRemaining < 0) {
            seatsRemaining = 0;
        }

        double averageGrade;

        if (gradedStudents == 0) {
            averageGrade = 0;
        } else {
            averageGrade = totalGrade / gradedStudents;
        }

        return new CourseRosterDTO(
                course,
                course.getInstructor(),
                students,
                seatFilled,
                seatsRemaining,
                averageGrade
        );
    }


    // DEPARTMENT SUMMARY
    public List<DepartmentSummaryDTO> getDepartmentSummary() {

        List<DepartmentSummaryDTO> result =
                new ArrayList<>();

        Set<String> departments = new HashSet<>();

        // Get all departments
        for (Student student :
                studentRepository.getAll()) {

            departments.add(student.getDepartment());
        }

        // Process each department
        for (String department : departments) {

            List<Student> students =
                    studentRepository
                            .getByDepartment(department);

            int totalEnrollments = 0;

            double totalStudentCgpa = 0;
            int studentsWithGrades = 0;

            List<Enrollment> departmentEnrollments =
                    new ArrayList<>();

            // Find enrollments of department students
            for (Enrollment enrollment :
                    enrollmentRepository.getAll()) {

                for (Student student : students) {

                    if (enrollment.getStudentId()
                            == student.getId()) {

                        departmentEnrollments.add(enrollment);
                        totalEnrollments++;

                        break;
                    }
                }
            }

            // Calculate each student's CGPA
            for (Student student : students) {

                double totalCredits = 0;
                double totalPoints = 0;

                for (Enrollment enrollment :
                        departmentEnrollments) {

                    if (enrollment.getStudentId()
                            == student.getId()) {

                        if (enrollment.getGrade() != null &&
                                enrollment.getGrade() >= 2.00) {

                            Course course = courseRepository
                                    .getById(
                                            enrollment.getCourseId()
                                    )
                                    .orElse(null);

                            if (course != null) {

                                totalCredits +=
                                        course.getCredit();

                                totalPoints +=
                                        course.getCredit()
                                                * enrollment.getGrade();
                            }
                        }
                    }
                }

                if (totalCredits > 0) {

                    double cgpa =
                            totalPoints / totalCredits;

                    totalStudentCgpa += cgpa;
                    studentsWithGrades++;
                }
            }

            double averageCgpa;

            if (studentsWithGrades == 0) {
                averageCgpa = 0;
            } else {
                averageCgpa =
                        totalStudentCgpa / studentsWithGrades;
            }


            // Find most popular course
            int popularCourseId = -1;
            int highestCount = 0;

            for (Enrollment enrollment :
                    departmentEnrollments) {

                int courseId =
                        enrollment.getCourseId();

                int count = 0;

                for (Enrollment e :
                        departmentEnrollments) {

                    if (e.getCourseId() == courseId) {
                        count++;
                    }
                }

                if (count > highestCount) {

                    highestCount = count;
                    popularCourseId = courseId;
                }
            }

            String popularCourse = "None";

            if (popularCourseId != -1) {

                Optional<Course> course =
                        courseRepository
                                .getById(popularCourseId);

                if (course.isPresent()) {
                    popularCourse =
                            course.get().getCode();
                }
            }

            result.add(
                    new DepartmentSummaryDTO(
                            department,
                            students.size(),
                            totalEnrollments,
                            averageCgpa,
                            popularCourse
                    )
            );
        }

        return result;
    }


    // TOP PERFORMERS
    public List<TopPerformerDTO> getTopPerformers(
            int limit) {

        List<TopPerformerDTO> result =
                new ArrayList<>();

        for (Student student :
                studentRepository.getAll()) {

            double totalCredits = 0;
            double totalPoints = 0;
            long coursesPassed = 0;

            for (Enrollment enrollment :
                    enrollmentRepository.getAll()) {

                if (enrollment.getStudentId()
                        == student.getId()) {

                    if (enrollment.getGrade() != null &&
                            enrollment.getGrade() >= 2.00) {

                        Course course = courseRepository
                                .getById(
                                        enrollment.getCourseId()
                                )
                                .orElse(null);

                        if (course != null) {

                            totalCredits +=
                                    course.getCredit();

                            totalPoints +=
                                    course.getCredit()
                                            * enrollment.getGrade();

                            coursesPassed++;
                        }
                    }
                }
            }

            double cgpa;

            if (totalCredits == 0) {
                cgpa = 0;
            } else {
                cgpa = totalPoints / totalCredits;
            }

            result.add(
                    new TopPerformerDTO(
                            student.getName(),
                            student.getDepartment(),
                            totalCredits,
                            coursesPassed,
                            cgpa
                    )
            );
        }

        return result.stream()
                .sorted(
                        Comparator.comparing(
                                TopPerformerDTO::getCgpa
                        ).reversed()
                )
                .limit(limit)
                .toList();
    }
}