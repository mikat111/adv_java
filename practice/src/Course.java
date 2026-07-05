public class Course {
    private String courseId;
    private String courseName;
    private CourseType type;
    private int credit;

    public Course(String courseId, String courseName, CourseType type, int credit) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.type = type;
        this.credit = credit;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public CourseType getType() {
        return type;
    }

    public int getCredit() {
        return credit;
    }

    public void displayInfo() {
        System.out.println(
                courseId + " | " +
                        courseName + " | " +
                        type + " | Credit: " +
                        credit
        );
    }
}