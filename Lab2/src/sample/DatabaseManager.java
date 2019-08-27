package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.Random;

class DatabaseManager {

    private static final String dbURL = "jdbc:mysql://localhost:3306/lab2";
    private static final String dbUser = "root";
    private static final String dbPassword = "universe";

    private static int getRandId() {
        return new Random().nextInt(9999999);
    }

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

        // Get user name
        String tableName = type + 's';
        q = String.format(
                "SELECT name FROM %s " +
                        "WHERE login_id = '%s';",
                tableName, loginId);
        String name = query(q);

        User user = null;
        switch (type) {
            case "student": {
                q = String.format(
                        "SELECT course FROM students " +
                                "WHERE login_id = '%s';",
                        loginId);
                int course = query(q);
                q = String.format(
                        "SELECT `group` FROM students " +
                                "WHERE login_id = '%s';",
                        loginId);
                String group = query(q);
                q = String.format(
                        "SELECT photo_url FROM students " +
                                "WHERE login_id = '%s';",
                        loginId);
                String photoURL = query(q);
                user = new Student(loginId, email, password, name, course, group, photoURL);
                break;
            }
            case "professor": {
                q = String.format(
                        "SELECT degree FROM professors " +
                                "WHERE login_id = '%s';",
                        loginId);
                String degree = query(q);
                q = String.format(
                        "SELECT department FROM professors " +
                                "WHERE login_id = '%s';",
                        loginId);
                String department = query(q);
                q = String.format(
                        "SELECT photo_url FROM professors " +
                                "WHERE login_id = '%s';",
                        loginId);
                String photoURL = query(q);
                user = new Professor(loginId, email, password, name, photoURL, degree, department);
                break;
            }
            case "admin": {
                user = new User(loginId, email, password, name, "");
                break;
            }
        }

        return user;
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

    static ObservableList<Long> getStudentIds() {
        String q = "select id from students";
        ObservableList<Long> l = FXCollections.observableArrayList();
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {
                long studentId = ((BigInteger) rs.getObject(1)).longValue();
                l.add(studentId);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return l;
    }

    static ObservableList<Long> getStudentIdsForCourseOffering(long coId) {
        String q = String.format(
                "select student_id from student_course_offerings where course_offering_id = %d",
                coId);
        ObservableList<Long> l = FXCollections.observableArrayList();
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {
                long studentId = ((BigInteger) rs.getObject(1)).longValue();
                l.add(studentId);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return l;
    }

    static ObservableList<Long> getUnfixedOfferingIds() {
        String q = "select co.id " +
                "from course_offerings co join student_course_offerings sco on co.id = sco.course_offering_id " +
                "where sco.is_alt is not null";
        ObservableList<Long> l = FXCollections.observableArrayList();
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {
                long offeringId = ((BigInteger) rs.getObject(1)).longValue();
                l.add(offeringId);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return l;
    }

    static ObservableList<Long> getStudentAltOfferingIds(long studentId) {
        String q = String.format(
                "select id from student_course_offerings where student_id = %d and is_alt = true",
                studentId);
        ObservableList<Long> l = FXCollections.observableArrayList();
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {
                long offeringId = ((BigInteger) rs.getObject(1)).longValue();
                l.add(offeringId);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return l;
    }

    static long getNStudentsForCourseOffering(long coId) {
        String q = String.format(
                "select count(*) from student_course_offerings where course_offering_id = %d and is_alt = false",
                coId);
        return query(q);
    }

    static long getNFixedCoursesForStudent(long studentId) {
        String q = String.format(
                "select count(*) from student_course_offerings where student_id = %d and is_alt is null",
                studentId);
        return query(q);
    }

    static void fixCourseOffering(long coId) {
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            String q = String.format(
                    "UPDATE student_course_offerings SET is_alt = null " +
                    "WHERE course_offering_id = %d", coId);
            st.execute(q);
            q = String.format("INSERT INTO rosters VALUES (%d, %d)", coId, coId);
            st.execute(q);
            // for each student in roster make grades
            for (var studentId : getStudentIdsForCourseOffering(coId)) {
                long gradeId = getRandId();
                q = String.format(
                        "INSERT INTO grades VALUES (%d, %d, %d, null)",
                        gradeId, coId, studentId);
                st.execute(q);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void makePrimary(long scoId) {
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            String q = String.format(
                    "UPDATE student_course_offerings SET is_alt = 0 " +
                    "WHERE id = %d", scoId);
            st.execute(q);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void removeUnfixedSCO() {
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            String q = "DELETE FROM student_course_offerings WHERE is_alt is not null";
            st.execute(q);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void clearAll() {
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            String q = "DELETE FROM rosters WHERE TRUE";
            st.execute(q);
            q = "DELETE FROM course_offerings WHERE TRUE";
            st.execute(q);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static ObservableList<Roster> getRostersForProfessor(long professorId) {
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

    static ObservableList<Student> getStudents() {
        String q = "SELECT login_id, email, password, name, course, `group`, photo_url " +
                "FROM logins JOIN students s on logins.id = s.login_id";

        ObservableList<Student> l = FXCollections.observableArrayList();

        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {
                long loginId = ((BigInteger) rs.getObject(1)).longValue();
                String email = (String) rs.getObject(2);
                String password = (String) rs.getObject(3);
                String name = (String) rs.getObject(4);
                int course = (Integer) rs.getObject(5);
                String group = (String) rs.getObject(6);
                String photoURL = (String) rs.getObject(7);
                l.add(new Student(loginId, email, password, name, course, group, photoURL));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return l;
    }

    static ObservableList<Professor> getProfessors() {
        String q = "SELECT login_id, email, password, name, degree, department, photo_url " +
                "FROM logins JOIN professors p on logins.id = p.login_id";

        ObservableList<Professor> l = FXCollections.observableArrayList();

        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {
                long loginId = ((BigInteger) rs.getObject(1)).longValue();
                String email = (String) rs.getObject(2);
                String password = (String) rs.getObject(3);
                String name = (String) rs.getObject(4);
                String degree = (String) rs.getObject(5);
                String department = (String) rs.getObject(6);
                String photoURL = (String) rs.getObject(7);
                l.add(new Professor(loginId, email, password, name, photoURL, degree, department));
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
            for (var offering : offerings) {
                long scoId = getRandId();
                long offeringId = offering.getOfferingId();
                boolean isAlt = offering.isAlternate();
                if (offering.isPrimary() || isAlt) {
                    q = String.format(
                            "INSERT INTO student_course_offerings VALUES (%d, %d, %d, %b)",
                            scoId, studentId, offeringId, isAlt);
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

    static void addProfessor(String email, String password, String name,
                             String degree, String department, String photoURL) {
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
                    "INSERT INTO professors VALUES (%d, %d, '%s', '%s', '%s', '%s')",
                    professorId, loginId, name, degree, department, photoURL);
            st.execute(q);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void editProfessor(long loginId, String email, String password, String name,
                              String degree, String department, String photoURL) {
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            String q = String.format(
                    "UPDATE professors SET name = '%s', degree = '%s', department = '%s', photo_url = '%s' " +
                    "WHERE login_id = %d", name, degree, department, photoURL, loginId);
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

    static void addStudent(String email, String password, String name,
                           int course, String group, String photoURL) {
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
                    "INSERT INTO students VALUES (%d, %d, '%s', %d, '%s', '%s')",
                    studentId, loginId, name, course, group, photoURL);
            st.execute(q);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void editStudent(long loginId, String email, String password, String name,
                            int course, String group, String photoURL) {
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            String q = String.format(
                    "UPDATE students SET name = '%s', course = %d, `group` = %s, photo_url = '%s' " +
                    "WHERE login_id = %d", name, course, group, photoURL, loginId);
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
                    "INSERT INTO courses VALUES (%d, '%s', %d)",
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

    static String getPaymentString() {
        StringBuilder sb = new StringBuilder("Student,Bill");
        String q = "select name, SUM(price) " +
                "from student_course_offerings sco " +
                "JOIN course_offerings co ON co.id = sco.course_offering_id " +
                "JOIN courses c ON c.id = co.course_id " +
                "JOIN students s ON s.id = sco.student_id " +
                "GROUP BY student_id";
        try {
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {
                String name = (String) rs.getObject(1);
                double bill = ((BigDecimal) rs.getObject(2)).doubleValue();
                sb.append('\n');
                sb.append(name);
                sb.append(',');
                sb.append(bill);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
