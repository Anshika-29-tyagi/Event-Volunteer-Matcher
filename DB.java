import java.sql.*;

public class DB {
    private static Connection conn;

    // Update these if your MySQL uses different user/password/host
    private static final String URL = "jdbc:mysql://localhost:3306/eventdb?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "AN@123tyagi#";

    public static void init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
            createTablesIfNotExist();
            System.out.println("Connected to DB.");
        } catch (Exception e) {
            System.out.println("DB init error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static Connection getConn() {
        return conn;
    }

    public static void close() {
        try { if (conn != null) conn.close(); } catch (Exception e) {}
    }

    private static void createTablesIfNotExist() {
        try (Statement st = conn.createStatement()) {
            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS volunteers (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(200), branch VARCHAR(100), year INT, " +
                "interests TEXT, skills TEXT, experience INT" +
                ")"
            );
            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS events (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "event_name VARCHAR(200), required_skills TEXT, volunteers_needed INT, event_date DATE" +
                ")"
            );
            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS event_volunteer (" +
                "event_id INT, volunteer_id INT, " +
                "PRIMARY KEY(event_id, volunteer_id)" +
                ")"
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
