package sample;

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

    static User.UserType getUserType(User user) {
        String q = String.format(
                "SELECT type FROM logins " +
                "WHERE id = '%s';",
                user.getLoginId());
        String type = query(q);
        return (type == null)? User.UserType.UNDEFINED :
                User.UserType.valueOf(type.toUpperCase());
    }

    static String getUserName(User user) {
        String tableName = user.getType().name().toLowerCase() + 's';
        String q = String.format(
                "SELECT name FROM %s " +
                "WHERE login_id = '%s';",
                tableName, user.getLoginId());
        String name = query(q);
        return (name == null)? "none" : name;
    }
}
