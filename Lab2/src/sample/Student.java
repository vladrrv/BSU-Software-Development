package sample;

public class Student extends User {
    private int course;
    private String group;

    public Student(long loginId, String email, String password, String name, int course, String group, String photoURL) {
        super(loginId, email, password, name, photoURL);
        this.course = course;
        this.group = group;
    }

    public int getCourse() {
        return course;
    }

    public String getGroup() {
        return group;
    }

    @Override
    String getInfo() {
        return String.format("Course %d\nGroup %s", course, group);
    }
}
