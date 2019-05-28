package sample;

class User {

    enum UserType {
        STUDENT,
        PROFESSOR,
        ADMIN,
        UNDEFINED
    }

    private long loginId;
    private String email;
    private String password;
    private String name;
    private String info;
    private UserType type;

    User(long loginId, String email, String password, String name, String info, UserType type) {
        this.loginId = loginId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.info = info;
        this.type = type;
    }

    long getLoginId() {
        return loginId;
    }

    UserType getType() {
        return type;
    }

    String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return name;
    }
}
