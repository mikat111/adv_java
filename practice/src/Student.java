public class Student {
    private String studentId;
    private  String studentName;
    private Course[] enrolledCourses;
    private int count;

    public Student(String studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
        enrolledCourses = new Course[6];
        System.out.println("Student Created");
    }

    public void enroll (Course c)
    {
        enrolledCourses[count++] =c;

    }
    public void showCourses() {

        System.out.println("Enrolled Courses:");

        for (Course c : enrolledCourses) {
            if (c != null) {
                c.displayInfo();
            }
        }
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    class Grade {
        private Course course;
        private double marks;

        public Grade(Course course, double marks) {
            this.course = course;
            this.marks = marks;
        }

        public Course getCourse() {
            return course;
        }

        public double getMarks() {
            return marks;
        }

        public String getLetterGrade() {
            if (marks >= 80)
                return "A";
            else if (marks >= 70)
                return "B";
            else if (marks >= 60)
                return "C";
            else if (marks >= 50)
                return "D";
            else
                return "F";
        }
    }
        static class Validator{

            public static boolean isValidId(String id) {
                if (id.startsWith("S") && id.length() >= 4)
                    return true;
                else
                    return false;
            }
        }





}
