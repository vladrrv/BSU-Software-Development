package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigInteger;
import java.sql.*;
import java.util.Random;

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
        String q = "SELECT is_registration_open FROM config;";
        Boolean res = query(q);
        return (res != null)? res: false;
    }

    static void switchRegistration() {
        boolean isOpen = isRegistrationOpen();
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            String q = "delete from config where id = 1;";
            st.execute(q);
            q = String.format("INSERT INTO config VALUES (1, %b)", !isOpen);
            st.execute(q);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

        String q = String.format(
                "with co_by_student_id as (select * from student_course_offerings where student_id = %d) " +
                "select co.id, c.description course_description, p.name professor_name, is_alt " +
                "from course_offerings co " +
                "     join professors p ON p.id = co.professor_id " +
                "     join courses c ON c.id = co.course_id " +
                "     left join co_by_student_id ON co_by_student_id.course_offering_id = co.id;",
                studentId);

        ObservableList<CourseOffering> l = FXCollections.observableArrayList();

        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {
                long offeringId = ((BigInteger) rs.getObject(1)).longValue();
                String course = (String) rs.getObject(2);
                String teacher = (String) rs.getObject(3);
                Boolean alt = (Boolean) rs.getObject(4);
                boolean isPrimary = false, isAlt = false;
                if (alt != null) {
                    isPrimary = !alt;
                    isAlt = alt;
                }
                l.add(new CourseOffering(offeringId, course, teacher, isPrimary, isAlt));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return l;
    }

    private static int getRandId() {
        return new Random().nextInt(9999999);
    }

    static void updateStudentOfferings(long studentId, ObservableList<CourseOffering> offerings) {

        String q = "delete from student_course_offerings where student_id = 1;";

        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            st.execute(q);
            for (var offering : offerings) {
                long offeringId = offering.getOfferingId();
                boolean isAlt = offering.isAlternate();
                if (offering.isPrimary() || isAlt) {
                    q = String.format("INSERT INTO student_course_offerings VALUES (%d, %d, %d, %b)", getRandId(), studentId, offeringId, isAlt);
                    st.execute(q);
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static ObservableList<Grade> getGrades(long studentId) {

        String q = String.format(
                "SELECT description, grade " +
                "FROM grades g " +
                "LEFT JOIN rosters r ON r.id = g.roster_id " +
                "LEFT JOIN course_offerings co ON co.id = r.course_offering_id " +
                "LEFT JOIN courses c ON c.id = co.course_id " +
                "LEFT JOIN students s ON s.id = g.student_id " +
                "WHERE student_id = '%d';",
                studentId);

        ObservableList<Grade> l = FXCollections.observableArrayList();

        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {
                String name = (String) rs.getObject(1);
                Long grade = (Long) rs.getObject(2);
                l.add(new Grade(name, grade.intValue()));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return l;
    }
}
