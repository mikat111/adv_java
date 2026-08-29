package com.example.labtest.DTO;

public class TopPerformerDTO {

    private String name;
    private String department;
    private double creditsCompleted;
    private long coursesPassed;
    private double cgpa;

    public TopPerformerDTO(String name,
                           String department,
                           double creditsCompleted,
                           long coursesPassed,
                           double cgpa) {
        this.name = name;
        this.department = department;
        this.creditsCompleted = creditsCompleted;
        this.coursesPassed = coursesPassed;
        this.cgpa = cgpa;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getCreditsCompleted() {
        return creditsCompleted;
    }

    public long getCoursesPassed() {
        return coursesPassed;
    }

    public double getCgpa() {
        return cgpa;
    }
}
