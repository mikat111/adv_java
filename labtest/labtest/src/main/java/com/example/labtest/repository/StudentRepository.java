package com.example.labtest.repository;

import com.example.labtest.entity.Student;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

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
    public Student getById(int id) {

        for (Student s : students) {

            if (s.getId() == id) {
                return s;
            }
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Student not found"
        );
    }

    // Filter by dept
    public List<Student> getByDepartment(String department) {

        return students.stream()
                .filter(s -> s.getDepartment().equalsIgnoreCase(department))
                .toList();
    }

    // Create reject duplicate
    public void save(Student student) {

        for (Student s : students) {

            if (s.getEmail().equalsIgnoreCase(student.getEmail())) {

                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Email already exists"
                );
            }
        }

        students.add(student);
    }

// Update
    public void update(Student student, int id) {

        for (Student s : students) {

            if (s.getId() == id) {

                s.setName(student.getName());
                s.setEmail(student.getEmail());
                s.setDepartment(student.getDepartment());
                s.setAdmissionYear(student.getAdmissionYear());

                return;
            }
        }
    }

    // DELETE
    public void delete(int id) {

        for (Student s : students) {

            if (s.getId() == id) {
                students.remove(s);
                return;
            }
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Student not found"
        );
    }
}
