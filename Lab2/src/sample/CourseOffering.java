package sample;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CourseOffering {
    private StringProperty course = new SimpleStringProperty();
    private StringProperty teacher = new SimpleStringProperty();
    private BooleanProperty primary = new SimpleBooleanProperty();
    private BooleanProperty alternate = new SimpleBooleanProperty();

    public CourseOffering(String course, String teacher, Boolean primary, Boolean alternate) {
        this.course.set(course);
        this.teacher.set(teacher);
        this.primary.set(primary);
        this.alternate.set(alternate);
    }

    public String getCourse() {
        return course.get();
    }

    public StringProperty courseProperty() {
        return course;
    }

    public String getTeacher() {
        return teacher.get();
    }

    public StringProperty teacherProperty() {
        return teacher;
    }

    public boolean isPrimary() {
        return primary.get();
    }

    public BooleanProperty primaryProperty() {
        return primary;
    }

    public boolean isAlternate() {
        return alternate.get();
    }

    public BooleanProperty alternateProperty() {
        return alternate;
    }
}
