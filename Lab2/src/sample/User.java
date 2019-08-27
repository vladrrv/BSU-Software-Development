package sample;

class User {

    private long loginId;
    private String email;
    private String password;
    private String name;
    private String photoURL;

    public User(long loginId, String email, String password, String name, String photoURL) {
        this.loginId = loginId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.photoURL = photoURL;
    }

    long getLoginId() {
        return loginId;
    }

    String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    boolean isStudent() {
        return this instanceof Student;
    }

    boolean isProfessor() {
        return this instanceof Professor;
    }

    String getInfo() {
        return "";
    }

    @Override
    public String toString() {
        return name;
    }
}
