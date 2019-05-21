package sample;

import java.sql.*;

class DatabaseManager {

    private static final String dbUser = "root";
    private static final String dbPassword = "universe";

    static boolean doesUserExist(User user) {
        String q = String.format(
                "SELECT COUNT(*) FROM logins " +
                "WHERE email = '%s' AND password = '%s';",
                user.getLogin(), user.getPassword());
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab2", dbUser, dbPassword);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);
            rs.next();
            int count = rs.getInt(1);
            //while(rs.next())
            //    System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
            con.close();
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    static String getUserType(User user) {
        String q = String.format(
                "SELECT type FROM logins " +
                        "WHERE email = '%s' AND password = '%s';",
                user.getLogin(), user.getPassword());
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab2", dbUser, dbPassword);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);
            rs.next();
            String type = rs.getString(1);
            con.close();
            return type;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "none";
    }
}
