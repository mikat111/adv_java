package com.example.labtest.DTO;

import com.example.labtest.entity.Student;

import java.util.List;

public class StudentTranscriptDTO {
    private Student student;
    private List<CourseResultDTO> courses;
    private double totalCreditsEarned;
    private double cgpa;

    public StudentTranscriptDTO(Student student,
                                List<CourseResultDTO> courses,
                                double totalCreditsEarned,
                                double cgpa) {
        this.student = student;
        this.courses = courses;
        this.totalCreditsEarned = totalCreditsEarned;
        this.cgpa = cgpa;
    }

    public Student getStudent() {
        return student;
    }

    public List<CourseResultDTO> getCourses() {
        return courses;
    }

    public double getTotalCreditsEarned() {
        return totalCreditsEarned;
    }

    public double getCgpa() {
        return cgpa;
    }
}
