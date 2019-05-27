package sample;

import javafx.beans.property.*;

public class Grade {
    private long studentId;
    private StringProperty student = new SimpleStringProperty();
    private StringProperty course = new SimpleStringProperty();
    private StringProperty grade = new SimpleStringProperty();
    static final String[] GRADES_STR = {
            "--", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"
    };
    static final String NOGRADE = GRADES_STR[0];

    public Grade(String course, Long grade) {
        this.course.set(course);
        this.grade.set((grade != null)? Long.toString(grade): NOGRADE);
    }

    public Grade(long studentId, String student, Long grade) {
        this.studentId = studentId;
        this.student.set(student);
        this.grade.set((grade != null)? Long.toString(grade): NOGRADE);
    }

    public long getStudentId() {
        return studentId;
    }

    public String getStudent() {
        return student.get();
    }

    public StringProperty studentProperty() {
        return student;
    }

    public String getCourse() {
        return course.get();
    }

    public StringProperty courseProperty() {
        return course;
    }

    public String getGrade() {
        return grade.get();
    }

    public StringProperty gradeProperty() {
        return grade;
    }
}
