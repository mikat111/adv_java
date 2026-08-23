package com.example.labtest.DTO;

import com.example.labtest.entity.Course;
import com.example.labtest.entity.Student;

import java.util.List;

public class CourseRosterDTO {



        private Course course;
        private String instructor;
        private List<Student> students;
        private long seatFilled;
        private long seatsRemaining;
        private double classAverageGrade;

        public CourseRosterDTO(Course course,
                               String instructor,
                               List<Student> students,
                               long seatFilled,
                               long seatsRemaining,
                               double classAverageGrade) {
            this.course = course;
            this.instructor = instructor;
            this.students = students;
            this.seatFilled = seatFilled;
            this.seatsRemaining = seatsRemaining;
            this.classAverageGrade = classAverageGrade;
        }

        public Course getCourse() {
            return course;
        }

        public String getInstructor() {
            return instructor;
        }

        public List<Student> getStudents() {
            return students;
        }

        public long getSeatFilled() {
            return seatFilled;
        }

        public long getSeatsRemaining() {
            return seatsRemaining;
        }

        public double getClassAverageGrade() {
            return classAverageGrade;
        }

}
