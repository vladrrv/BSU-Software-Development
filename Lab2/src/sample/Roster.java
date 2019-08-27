package sample;

public class Roster {
    private long rosterId;
    private String course;

    public Roster(long rosterId, String course) {
        this.rosterId = rosterId;
        this.course = course;
    }

    public long getRosterId() {
        return rosterId;
    }

    public String getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return course;
    }
}
