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
import java.util.stream.Collectors;

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


    // 10. Student Transcript
    public StudentTranscriptDTO getTranscript(int studentId) {

        Student student = studentRepository.getById(studentId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Student not found"
                        ));

        List<Enrollment> enrollments =
                enrollmentRepository.getByStudentId(studentId);

        List<CourseResultDTO> results = new ArrayList<>();

        double totalCredits = 0;
        double totalPoints = 0;

        for (Enrollment enrollment : enrollments) {

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

                if (enrollment.getGrade() != null &&
                        enrollment.getGrade() >= 2.00) {

                    totalCredits += course.getCredit();

                    totalPoints +=
                            course.getCredit()
                                    * enrollment.getGrade();
                }
            }
        }

        double cgpa = totalCredits == 0
                ? 0
                : totalPoints / totalCredits;

        return new StudentTranscriptDTO(
                student,
                results,
                totalCredits,
                cgpa
        );
    }


    // 11. Course Roster
    public CourseRosterDTO getRoster(int courseId) {

        Course course = courseRepository.getById(courseId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Course not found"
                        ));

        List<Enrollment> enrollments =
                enrollmentRepository.getByCourseId(courseId);

        List<Student> students = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {

            studentRepository.getById(
                    enrollment.getStudentId()
            ).ifPresent(students::add);
        }

        long seatFilled = enrollments.size();

        long seatsRemaining = Math.max(
                0,
                course.getCapacity() - seatFilled
        );

        double averageGrade = enrollments.stream()
                .filter(e -> e.getGrade() != null)
                .mapToDouble(Enrollment::getGrade)
                .average()
                .orElse(0);

        return new CourseRosterDTO(
                course,
                course.getInstructor(),
                students,
                seatFilled,
                seatsRemaining,
                averageGrade
        );
    }


    // 12. Department Summary
    public List<DepartmentSummaryDTO> getDepartmentSummary() {

        Set<String> departments =
                studentRepository.getAll()
                        .stream()
                        .map(Student::getDepartment)
                        .collect(Collectors.toSet());

        List<DepartmentSummaryDTO> result =
                new ArrayList<>();

        for (String department : departments) {

            List<Student> students =
                    studentRepository.getByDepartment(department);

            Set<Integer> studentIds =
                    students.stream()
                            .map(Student::getId)
                            .collect(Collectors.toSet());

            List<Enrollment> enrollments =
                    enrollmentRepository.getAll()
                            .stream()
                            .filter(e ->
                                    studentIds.contains(
                                            e.getStudentId()))
                            .toList();

            double totalPoints = 0;
            double totalCredits = 0;

            for (Enrollment enrollment : enrollments) {

                if (enrollment.getGrade() != null) {

                    Course course =
                            courseRepository
                                    .getById(
                                            enrollment.getCourseId())
                                    .orElse(null);

                    if (course != null) {

                        totalPoints +=
                                course.getCredit()
                                        * enrollment.getGrade();

                        totalCredits +=
                                course.getCredit();
                    }
                }
            }

            double averageCgpa =
                    totalCredits == 0
                            ? 0
                            : totalPoints / totalCredits;


            // Most popular course
            String popularCourse = "None";

            Optional<Map.Entry<Integer, Long>>
                    popularCourseId =
                    enrollments.stream()
                            .collect(Collectors.groupingBy(
                                    Enrollment::getCourseId,
                                    Collectors.counting()
                            ))
                            .entrySet()
                            .stream()
                            .max(
                                    Map.Entry.comparingByValue()
                            );

            if (popularCourseId.isPresent()) {

                int courseId =
                        popularCourseId.get().getKey();

                popularCourse =
                        courseRepository
                                .getById(courseId)
                                .map(Course::getCode)
                                .orElse("None");
            }

            result.add(
                    new DepartmentSummaryDTO(
                            department,
                            students.size(),
                            enrollments.size(),
                            averageCgpa,
                            popularCourse
                    )
            );
        }

        return result;
    }


    // 13. Top Performers
    public List<TopPerformerDTO> getTopPerformers(
            int limit) {

        List<TopPerformerDTO> result =
                new ArrayList<>();

        for (Student student :
                studentRepository.getAll()) {

            List<Enrollment> enrollments =
                    enrollmentRepository
                            .getByStudentId(
                                    student.getId());

            double totalCredits = 0;
            double totalPoints = 0;
            long coursesPassed = 0;

            for (Enrollment enrollment :
                    enrollments) {

                if (enrollment.getGrade() != null) {

                    Course course =
                            courseRepository
                                    .getById(
                                            enrollment.getCourseId())
                                    .orElse(null);

                    if (course != null &&
                            enrollment.getGrade() >= 2.00) {

                        totalCredits +=
                                course.getCredit();

                        totalPoints +=
                                course.getCredit()
                                        * enrollment.getGrade();

                        coursesPassed++;
                    }
                }
            }

            double cgpa =
                    totalCredits == 0
                            ? 0
                            : totalPoints / totalCredits;

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