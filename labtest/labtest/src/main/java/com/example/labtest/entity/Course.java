package com.example.labtest.entity;

public class Course {
    private int id;
    private String code;
    private String title;
    private double credit;
    private String instructor;
    private int capacity;



    public Course(int id, String code, String title, double credit,
                  String instructor, int capacity) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.credit = credit;
        this.instructor = instructor;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", credit=" + credit +
                ", instructor='" + instructor + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}
