package sample;

import javafx.beans.property.*;

public class Course {
    private long courseId;
    private StringProperty description = new SimpleStringProperty();
    private IntegerProperty price = new SimpleIntegerProperty();
    private BooleanProperty selected = new SimpleBooleanProperty();

    public Course(long courseId, String description, Boolean selected) {
        this.courseId = courseId;
        this.description.set(description);
        this.selected.set(selected);
    }

    public Course(long courseId, String description, int price) {
        this.courseId = courseId;
        this.description.set(description);
        this.price.set(price);
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

    public long getCourseId() {
        return courseId;
    }

    public int getPrice() {
        return price.get();
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
