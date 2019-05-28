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

    static User getUser(String email, String password) {

        //Get user login_id
        String q = String.format(
                "SELECT id FROM logins " +
                "WHERE email = '%s' AND password = '%s';",
                email, password);
        BigInteger id = query(q);
        long loginId = (id == null)? -1 : id.longValue();
        if (loginId < 0) return null;

        // Get user type
        q = String.format(
                "SELECT type FROM logins " +
                "WHERE id = '%s';",
                loginId);
        String type = query(q);
        User.UserType t = (type == null)? User.UserType.UNDEFINED :
                User.UserType.valueOf(type.toUpperCase());

        // Get user name
        String tableName = type + 's';
        q = String.format(
                "SELECT name FROM %s " +
                "WHERE login_id = '%s';",
                tableName, loginId);
        String name = query(q);

        // Get user info
        String info = "";

        return new User(loginId, email, password, name, info, t);
    }

    static boolean isRegistrationOpen() {
        String q = "SELECT is_registration_open FROM config;";
        Boolean res = query(q);
        return (res != null)? res: false;
    }

    static boolean switchRegistration() {
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
        return isOpen;
    }

    static int getRandId() {
        return new Random().nextInt(9999999);
    }

    static long getStudentId(User user) {
        String q = String.format(
                "SELECT id FROM students " +
                "WHERE login_id = '%s';",
                user.getLoginId());
        BigInteger res = query(q);
        return res.longValue();
    }

    static long getProfessorId(User user) {
        String q = String.format(
                "SELECT id FROM professors " +
                "WHERE login_id = '%s';",
                user.getLoginId());
        BigInteger res = query(q);
        return res.longValue();
    }

    static ObservableList<CourseOffering> getOfferingsForStudent(long studentId) {

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

    static ObservableList<Roster> getRosters(long professorId) {
        ObservableList<Roster> l = FXCollections.observableArrayList();
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            String q = String.format(
                    "SELECT r.id, description " +
                    "FROM rosters r " +
                    "LEFT JOIN course_offerings co ON co.id = r.course_offering_id " +
                    "LEFT JOIN courses c ON c.id = co.course_id " +
                    "WHERE co.professor_id = %d;",
                    professorId);
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {
                long rosterId = ((BigInteger) rs.getObject(1)).longValue();
                String description = (String) rs.getObject(2);
                l.add(new Roster(rosterId, description));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return l;
    }

    static ObservableList<Grade> getGradesForRoster(long rosterId) {
        ObservableList<Grade> l = FXCollections.observableArrayList();
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            String q = String.format(
                    "SELECT s.id, name, grade " +
                    "FROM grades g " +
                    "LEFT JOIN rosters r ON r.id = g.roster_id " +
                    "LEFT JOIN course_offerings co ON co.id = r.course_offering_id " +
                    "LEFT JOIN students s ON s.id = g.student_id " +
                    "WHERE roster_id = '%d';",
                    rosterId);
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {
                long studentId = ((BigInteger) rs.getObject(1)).longValue();
                String studentName = (String) rs.getObject(2);
                Long grade = (Long) rs.getObject(3);
                l.add(new Grade(studentId, studentName, grade));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return l;
    }

    static ObservableList<Grade> getGradesForStudent(long studentId) {
        ObservableList<Grade> l = FXCollections.observableArrayList();
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            String q = String.format(
                    "SELECT description, grade " +
                            "FROM grades g " +
                            "LEFT JOIN rosters r ON r.id = g.roster_id " +
                            "LEFT JOIN course_offerings co ON co.id = r.course_offering_id " +
                            "LEFT JOIN courses c ON c.id = co.course_id " +
                            "LEFT JOIN students s ON s.id = g.student_id " +
                            "WHERE student_id = '%d';",
                    studentId);
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {
                String name = (String) rs.getObject(1);
                Long grade = (Long) rs.getObject(2);
                l.add(new Grade(name, grade));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return l;
    }

    static ObservableList<Course> getCoursesForProfessor(long professorId) {
        String q = String.format(
                "with co_by_professor_id as (" +
                "    select * from course_offerings where professor_id = %d) " +
                "select c.id, c.description course_description, professor_id " +
                "from courses c " +
                "         join co_by_professor_id co ON co.course_id = c.id " +
                "UNION " +
                "select c.id, c.description course_description, professor_id " +
                "from courses c " +
                "         left join course_offerings co ON co.course_id = c.id " +
                "where professor_id IS NULL;",
                professorId);

        ObservableList<Course> l = FXCollections.observableArrayList();

        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {
                long courseId = ((BigInteger) rs.getObject(1)).longValue();
                String description = (String) rs.getObject(2);
                BigInteger profId = (BigInteger) rs.getObject(3);
                boolean selected = profId != null;
                l.add(new Course(courseId, description, selected));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return l;
    }

    static ObservableList<Course> getCourses() {
        String q = "select id, description, price from courses";
        ObservableList<Course> l = FXCollections.observableArrayList();
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {
                long courseId = ((BigInteger) rs.getObject(1)).longValue();
                String description = (String) rs.getObject(2);
                Long price = (Long) rs.getObject(3);
                l.add(new Course(courseId, description, price.intValue()));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return l;
    }

    static ObservableList<User> getStudents() {
        String q = "SELECT login_id, email, password, name FROM logins JOIN students s on logins.id = s.login_id";

        ObservableList<User> l = FXCollections.observableArrayList();

        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {
                long loginId = ((BigInteger) rs.getObject(1)).longValue();
                String email = (String) rs.getObject(2);
                String password = (String) rs.getObject(3);
                String name = (String) rs.getObject(4);
                l.add(new User(loginId, email, password, name, "", User.UserType.STUDENT));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return l;
    }

    static ObservableList<User> getProfessors() {
        String q = "SELECT login_id, email, password, name FROM logins JOIN professors p on logins.id = p.login_id";

        ObservableList<User> l = FXCollections.observableArrayList();

        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {
                long loginId = ((BigInteger) rs.getObject(1)).longValue();
                String email = (String) rs.getObject(2);
                String password = (String) rs.getObject(3);
                String name = (String) rs.getObject(4);
                l.add(new User(loginId, email, password, name, "", User.UserType.PROFESSOR));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return l;
    }

    static void updateStudentOfferings(long studentId, ObservableList<CourseOffering> offerings) {
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            String q = String.format("delete from student_course_offerings where student_id = %d;", studentId);
            st.execute(q);
            q = String.format("delete from grades where student_id = %d;", studentId);
            st.execute(q);
            for (var offering : offerings) {
                long scoId = getRandId();
                long gradeId = getRandId();
                long offeringId = offering.getOfferingId();
                boolean isAlt = offering.isAlternate();
                if (offering.isPrimary() || isAlt) {
                    q = String.format(
                            "INSERT INTO student_course_offerings VALUES (%d, %d, %d, %b)",
                            scoId, studentId, offeringId, isAlt);
                    st.execute(q);
                    q = String.format(
                            "INSERT INTO grades VALUES (%d, %d, %d, null)",
                            gradeId, offeringId, studentId);
                    st.execute(q);
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void updateCourseOfferings(long professorId, ObservableList<Course> courses) {
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            String q = String.format("delete from course_offerings where professor_id = %d;", professorId);
            st.execute(q);
            for (var course : courses) {
                long coId = getRandId();
                long courseId = course.getCourseId();
                boolean isSel = course.isSelected();
                if (isSel) {
                    q = String.format("INSERT INTO course_offerings VALUES (%d, %d, %d)", coId, professorId, courseId);
                    st.execute(q);
                    q = String.format("INSERT INTO rosters VALUES (%d, %d)", coId, coId);
                    st.execute(q);
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void updateGrades(long rosterId, ObservableList<Grade> grades) {
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            String q = String.format("delete from grades where roster_id = %d;", rosterId);
            st.execute(q);
            for (var grade : grades) {
                long gradeId = getRandId();
                long studentId = grade.getStudentId();
                String g = grade.getGrade();
                if (!g.equals(Grade.NOGRADE)) {
                    long gradeVal = Long.parseLong(grade.getGrade());
                    q = String.format(
                            "INSERT INTO grades VALUES (%d, %d, %d, %d)",
                            gradeId, rosterId, studentId, gradeVal);
                } else {
                    q = String.format(
                            "INSERT INTO grades VALUES (%d, %d, %d, null)",
                            gradeId, rosterId, studentId);
                }
                st.execute(q);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void addProfessor(String email, String password, String name, String info) {
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            int loginId = getRandId();
            int professorId = getRandId();
            String q = String.format(
                    "INSERT INTO logins VALUES (%d, '%s', 'professor', '%s')",
                    loginId, email, password);
            st.execute(q);
            q = String.format(
                    "INSERT INTO professors VALUES (%d, %d, '%s')",
                    professorId, loginId, name);
            st.execute(q);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void editProfessor(long loginId, String email, String password, String name, String info) {
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            String q = String.format(
                    "UPDATE professors SET name = '%s' " +
                    "WHERE login_id = %d", name, loginId);
            st.execute(q);
            q = String.format(
                    "UPDATE logins SET email = '%s', password = '%s' " +
                    "WHERE id = %d", email, password, loginId);
            st.execute(q);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void addStudent(String email, String password, String name, String info) {
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            int loginId = getRandId();
            int studentId = getRandId();
            String q = String.format(
                    "INSERT INTO logins VALUES (%d, '%s', 'student', '%s')",
                    loginId, email, password);
            st.execute(q);
            q = String.format(
                    "INSERT INTO students VALUES (%d, %d, '%s')",
                    studentId, loginId, name);
            st.execute(q);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void editStudent(long loginId, String email, String password, String name, String info) {
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            String q = String.format(
                    "UPDATE students SET name = '%s' " +
                    "WHERE login_id = %d", name, loginId);
            st.execute(q);
            q = String.format(
                    "UPDATE logins SET email = '%s', password = '%s' " +
                    "WHERE id = %d", email, password, loginId);
            st.execute(q);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void deleteUser(long loginId) {
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            String q = String.format("delete from logins where id = %d;", loginId);
            st.execute(q);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static boolean doesEmailExist(String email) {
        String q = String.format("SELECT id FROM logins WHERE email = '%s'", email);
        BigInteger id = query(q);
        return id != null;
    }

    static void addCourse(String description, int price) {
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            int courseId = getRandId();
            String q = String.format(
                    "INSERT INTO logins VALUES (%d, '%s', %d)",
                    courseId, description, price);
            st.execute(q);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void editCourse(long courseId, String description, int price) {
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            String q = String.format(
                    "UPDATE courses SET description = '%s', price = %d " +
                    "WHERE id = %d", description, price, courseId);
            st.execute(q);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void deleteCourse(long courseId) {
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            String q = String.format("delete from courses where id = %d;", courseId);
            st.execute(q);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
