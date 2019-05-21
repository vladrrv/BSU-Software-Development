package sample;

class WindowController extends Controller {
    private User user;

    User getUser() {
        return user;
    }

    void setUser(User user) {
        this.user = user;
    }

    @Override
    void init() {
        super.init();
    }
}
