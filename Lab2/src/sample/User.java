package sample;

class User {

    enum UserType {
        STUDENT ("student"),
        PROFESSOR ("professor"),
        UNDEFINED("none");
        UserType(String s) { }
    }

    private long loginId;
    private String name;
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
}
