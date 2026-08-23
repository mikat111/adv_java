package com.example.labtest.DTO;

public class DepartmentSummaryDTO {
    private String department;
    private long numberOfStudents;
    private long totalEnrollments;
    private double averageCgpa;
    private String mostPopularCourse;

    public DepartmentSummaryDTO(String department,
                                long numberOfStudents,
                                long totalEnrollments,
                                double averageCgpa,
                                String mostPopularCourse) {
        this.department = department;
        this.numberOfStudents = numberOfStudents;
        this.totalEnrollments = totalEnrollments;
        this.averageCgpa = averageCgpa;
        this.mostPopularCourse = mostPopularCourse;
    }

    public String getDepartment() {
        return department;
    }

    public long getNumberOfStudents() {
        return numberOfStudents;
    }

    public long getTotalEnrollments() {
        return totalEnrollments;
    }

    public double getAverageCgpa() {
        return averageCgpa;
    }

    public String getMostPopularCourse() {
        return mostPopularCourse;
    }
}
