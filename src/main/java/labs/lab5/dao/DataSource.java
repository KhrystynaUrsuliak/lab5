package labs.lab5.dao;

import java.io.InputStream;
import java.io.IOException;
import java.sql.Statement;
import java.util.Properties;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSource {
    private static HikariConfig config;
    private static HikariDataSource ds;

    static {
        try (InputStream input = MethodHandles.lookup().lookupClass().getClassLoader().getResourceAsStream("datasource.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            config = new HikariConfig(prop);
            ds = new HikariDataSource(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void createTablesStructure() throws SQLException {
        String createCourse = """
        create table if not exists course
        (
          id INT AUTO_INCREMENT PRIMARY KEY,
          name VARCHAR(100) NOT NULL,
          language_id INT,
          level VARCHAR(10),
          start_date DATE,
          end_date DATE,
          price DOUBLE,
          FOREIGN KEY (language_id) REFERENCES Language(id)
        );
        """;

        String createLanguage = """
        create table if not exists language
        (
          id INT AUTO_INCREMENT PRIMARY KEY,
          name ENUM('ENGLISH', 'GERMAN', 'SPANISH', 'FRENCH', 'MANDARIN', 'ROMANIAN') NOT NULL
        );
        """;

        String createGroup = """
        create table if not exists groupings
        (
          id INT AUTO_INCREMENT PRIMARY KEY,
          name VARCHAR(50) NOT NULL,
          course_id INT,
          tutor_id INT,
          FOREIGN KEY (course_id) REFERENCES Course(id),
          FOREIGN KEY (tutor_id) REFERENCES Tutor(id)
        );
        """;

        String createStudent = """
        create table if not exists student
        (
          id INT AUTO_INCREMENT PRIMARY KEY,
          first_name VARCHAR(50) NOT NULL,
          last_name VARCHAR(50) NOT NULL,
          email VARCHAR(100) UNIQUE NOT NULL,
          initial_level VARCHAR(10)
        );
        """;

        String createTutor = """
        create table if not exists tutor
        (
          id INT AUTO_INCREMENT PRIMARY KEY,
          first_name VARCHAR(50) NOT NULL,
          last_name VARCHAR(50) NOT NULL,
          birth_date DATE,
          language_id INT,
          salary DOUBLE,
          FOREIGN KEY (language_id) REFERENCES Language(id)
        );
        """;

        try (Connection conn = getConnection();
             Statement st = conn.createStatement()) {
            conn.setAutoCommit(false);
            st.execute(createLanguage);
            st.execute(createCourse);
            st.execute(createTutor);
            st.execute(createStudent);
            st.execute(createGroup);
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
