package sample;

public class Professor extends User {
    private String degree;
    private String department;


    public Professor(long loginId, String email, String password, String name, String photoURL, String degree, String department) {
        super(loginId, email, password, name, photoURL);
        this.degree = degree;
        this.department = department;
    }

    public String getDegree() {
        return degree;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    String getInfo() {
        return String.format("%s at %s department", degree, department);
    }
}
