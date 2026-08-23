package com.example.labtest.repository;

import com.example.labtest.entity.Student;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {

    static List<Student> students = new ArrayList<>();

    static {
        students.add(new Student(
                1,
                "Mikat",
                "mikat@gmail.com",
                "CSE",
                2023
        ));

        students.add(new Student(
                2,
                "Nabil",
                "nabil@gmail.com",
                "CSE",
                2022
        ));

        students.add(new Student(
                3,
                "Riyasat",
                "riyasat@gmail.com",
                "EEE",
                2023
        ));

        students.add(new Student(
                4,
                "Momin",
                "momin@gmail.com",
                "BBA",
                2022
        ));

        students.add(new Student(
                5,
                "Rahim",
                "rahim@gmail.com",
                "CSE",
                2024
        ));
    }

    // GET ALL
    public List<Student> getAll() {
        return students;
    }

    // GET BY ID
    public Optional<Student> getById(int id) {

        return students.stream()
                .filter(s -> s.getId() == id)
                .findFirst();
    }

    // FILTER BY DEPARTMENT
    public List<Student> getByDepartment(String department) {

        return students.stream()
                .filter(s ->
                        s.getDepartment()
                                .equalsIgnoreCase(department))
                .toList();
    }

    // CREATE
    public void save(Student student) {

        boolean emailExists = students.stream()
                .anyMatch(s ->
                        s.getEmail()
                                .equalsIgnoreCase(student.getEmail()));

        if (emailExists) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists"
            );
        }

        students.add(student);
    }

    // UPDATE
    public void update(Student student, int id) {

        Student existing = getById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Student not found"
                        ));

        boolean emailExists = students.stream()
                .anyMatch(s ->
                        s.getId() != id &&
                                s.getEmail().equalsIgnoreCase(student.getEmail())
                );

        if (emailExists) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists"
            );
        }

        existing.setName(student.getName());
        existing.setEmail(student.getEmail());
        existing.setDepartment(student.getDepartment());
        existing.setAdmissionYear(student.getAdmissionYear());
    }

    // DELETE
    public void delete(int id) {

        getById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Student not found"
                        ));

        students.removeIf(s -> s.getId() == id);
    }}