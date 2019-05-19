package sample;

import java.sql.*;

class DatabaseManager {

    static boolean doesUserExist(String login, String password) {
        String q =
                "SELECT COUNT(*) FROM logins " +
                "WHERE email = '" + login + "' AND password = '" + password + "';";
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab2",
                    "root","universe");
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


}
