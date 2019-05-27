package sample;

class User {

    enum UserType {
        STUDENT,
        PROFESSOR,
        ADMIN,
        UNDEFINED
    }

    private long loginId;
    private String name;
    private String info;
    private UserType type;

    User(long loginId) {
        this.loginId = loginId;
    }

    public User(long loginId, String name, UserType type) {
        this.loginId = loginId;
        this.name = name;
        this.type = type;
    }

    long getLoginId() {
        return loginId;
    }

    UserType getType() {
        return type;
    }

    void setType(UserType type) {
        this.type = type;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return name;
    }
}
