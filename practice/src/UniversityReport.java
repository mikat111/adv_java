public class UniversityReport implements Reportable {

    private Student student;

    public UniversityReport(Student student) {
        this.student = student;
    }

    @Override
    public void generateReport() {

        printHeader();

        System.out.println(
                "Student ID: " +
                        student.getStudentId()
        );

        System.out.println(
                "Student Name: " +
                        student.getStudentName()
        );

        student.showCourses();

    }
}