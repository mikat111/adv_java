package com.example.labtest.DTO;

public class CourseResultDTO {
    private String courseCode;
    private String title;
    private double credit;
    private String semester;
    private Double grade;

    public CourseResultDTO(String courseCode,
                           String title,
                           double credit,
                           String semester,
                           Double grade) {
        this.courseCode = courseCode;
        this.title = title;
        this.credit = credit;
        this.semester = semester;
        this.grade = grade;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public double getCredit() {
        return credit;
    }

    public String getSemester() {
        return semester;
    }

    public Double getGrade() {
        return grade;
    }
}
