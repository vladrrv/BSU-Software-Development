package sample;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CourseOffering {
    private long offeringId;
    private StringProperty course = new SimpleStringProperty();
    private StringProperty teacher = new SimpleStringProperty();
    private BooleanProperty primary = new SimpleBooleanProperty();
    private BooleanProperty alternate = new SimpleBooleanProperty();

    CourseOffering(long offeringId, String course, String teacher, Boolean primary, Boolean alternate) {
        this.offeringId = offeringId;
        this.course.set(course);
        this.teacher.set(teacher);
        this.primary.set(primary);
        this.alternate.set(alternate);

        this.primary.addListener(c -> {
            if (isPrimary() && isAlternate()) {
                alternateProperty().set(false);
            }
        });
        this.alternate.addListener(c -> {
            if (isPrimary() && isAlternate()) {
                primaryProperty().set(false);
            }
        });
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

    public long getOfferingId() {
        return offeringId;
    }
}
