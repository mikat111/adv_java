public class Main {

    public static void main(String[] args) {

        // Type Inference (var)

        var c1 = new Course(
                "C101",
                "Physics",
                CourseType.SCIENCE,
                3
        );

        var c2 = new Course(
                "C102",
                "Programming",
                CourseType.ENGINEERING,
                4
        );

        var student =
                new Student("S1001", "Mikat");

        student.enroll(c1);
        student.enroll(c2);

        // Static Nested Class

        System.out.println(
                Student.Validator.isValidId("S1001")
        );

        // Inner Class

        Student.Grade grade =
                student.new Grade(c1, 85);

        System.out.println(
                "Grade: "
                        + grade.getLetterGrade()
        );

        // Dynamic Method Dispatch

        User u;

        u = new StudentUser();
        u.login();

        u = new TeacherUser();
        u.login();

        // Report

        var report =
                new UniversityReport(student);

        report.generateReport();

        // Varargs

        Notification.sendMessages(
                "Registration Complete",
                "Class starts Monday",
                "Check Student Portal"
        );

        // Footer

        Reportable.printFooter();
    }
}