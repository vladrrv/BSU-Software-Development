package sample;

class User {

    enum UserType {
        STUDENT,
        PROFESSOR,
        REGISTRAR,
        UNDEFINED
    }

    private long loginId;
    private String name;
    private String info;
    private UserType type;

    User(long loginId) {
        this.loginId = loginId;
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
}
