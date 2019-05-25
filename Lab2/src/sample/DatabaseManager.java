package sample;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.math.BigInteger;
import java.sql.*;

class DatabaseManager {

    private static final String dbURL = "jdbc:mysql://localhost:3306/lab2";
    private static final String dbUser = "root";
    private static final String dbPassword = "universe";

    private static <T> T query(String q) {
        T result = null;
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);
            if (rs.next()) {
                result = (T) rs.getObject(1);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    static long getLoginId(String email, String password) {
        String q = String.format(
                "SELECT id FROM logins " +
                "WHERE email = '%s' AND password = '%s';",
                email, password);
        BigInteger id = query(q);
        return id == null ? -1 : id.longValue();
    }

    static void setUserFields(User user) {

        // Get user type
        String q = String.format(
                "SELECT type FROM logins " +
                "WHERE id = '%s';",
                user.getLoginId());
        String type = query(q);
        User.UserType t = (type == null)? User.UserType.UNDEFINED :
                User.UserType.valueOf(type.toUpperCase());
        user.setType(t);

        // Get user name
        String tableName = type + 's';
        q = String.format(
                "SELECT name FROM %s " +
                "WHERE login_id = '%s';",
                tableName, user.getLoginId());
        String name = query(q);
        user.setName(name);

        // Get user info

    }

    static boolean isRegistrationOpen() {
        String q = "SELECT is_open FROM state;";
        // TODO: query if reg is open
        return true;
    }

    static long getStudentId(User user) {
        String q = String.format(
                "SELECT id FROM students " +
                "WHERE login_id = '%s';",
                user.getLoginId());
        BigInteger res = query(q);
        return res.longValue();
    }

    static ObservableList<CourseOffering> getOfferings(long studentId) {

        // TODO: query to get list of all course offerings
        String q = String.format(
                "SELECT id FROM course_offerings JOIN  " +
                "WHERE id = '%s';",
                studentId);

        var l = FXCollections.observableArrayList(
                new CourseOffering( "c1"    , "x", false, false  ),
                new CourseOffering( "c2"    , "x", true, false  ),
                new CourseOffering( "c3"    , "y", false, true  )
        );

        return l;
    }
}
