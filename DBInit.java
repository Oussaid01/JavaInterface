import java.sql.Connection;
import java.sql.Statement;

public class DBInit {
    public static void init() {
        //user table
        String sqlUser = """
            CREATE TABLE IF NOT EXISTS user (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL,
                email TEXT NOT NULL UNIQUE,
                club TEXT
            );
        """;

        //test user
        String insertTestUser = """
            INSERT OR IGNORE INTO user (username, password, email, club)
            VALUES ('admin', '1234', 'admin@example.com', 'ORENDA JE');
        """;

        //activities table
        String sqlActivities = """
            CREATE TABLE IF NOT EXISTS activities (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                activity TEXT NOT NULL,
                type TEXT NOT NULL,
                date DATE NOT NULL
            );
        """;

        //club table
        String sqlClub = """
            CREATE TABLE IF NOT EXISTS club (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL UNIQUE
            );
        """;

        //default clubs
        String[] defaultClubs = {
            "INSERT OR IGNORE INTO club (name) VALUES ('ORENDA JE');",
            "INSERT OR IGNORE INTO club (name) VALUES ('ISAMM PROBLEM SOLVING CLUB');",
            "INSERT OR IGNORE INTO club (name) VALUES ('CLUB ROBOTIQUE ISAMM');"
        };

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            //create tables
            stmt.execute(sqlUser);
            stmt.execute(sqlActivities);
            stmt.execute(sqlClub);

            //insert test user
            stmt.execute(insertTestUser);

            // Insert default clubs
            for (String clubSql : defaultClubs) {
                stmt.execute(clubSql);
            }

            // stmt.execute("delete from club;");
            System.out.println("Tables 'user', 'activities', and 'club' created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
