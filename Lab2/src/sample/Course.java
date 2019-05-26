package sample;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Course {
    private long courseId;
    private StringProperty description = new SimpleStringProperty();
    private BooleanProperty selected = new SimpleBooleanProperty();

    public Course(long courseId, String description, Boolean selected) {
        this.courseId = courseId;
        this.description.set(description);
        this.selected.set(selected);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }


}
