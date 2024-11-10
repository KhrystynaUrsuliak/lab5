package labs.lab5.dao;

import labs.lab5.model.Course;
import labs.lab5.model.Language;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {
    private final Connection connection;

    public CourseDao() {
        try {
            connection = DataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Course add(Course course) throws SQLException {
        String query = "INSERT INTO Course (name, language_id, level, start_date, end_date, price) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, course.getName());
            ps.setInt(2, getLanguageId(course.getLanguage())); // language_id as int
            ps.setString(3, course.getLevel());
            ps.setDate(4, Date.valueOf(course.getStartDate()));
            ps.setDate(5, Date.valueOf(course.getEndDate()));
            ps.setDouble(6, course.getPrice());
            ps.executeUpdate();

            // Retrieve and set generated ID
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                course.setId(generatedKeys.getInt(1));
            }
            return course;
        }
    }

    public List<Course> getAll() throws SQLException {
        String query = "SELECT * FROM Course";
        List<Course> courses = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                courses.add(fromResultSet(rs));
            }
        }
        return courses;
    }

    public Course getById(int id) throws SQLException {
        String query = "SELECT * FROM Course WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return fromResultSet(rs);
            }
            throw new SQLException("Course with ID " + id + " not found.");
        }
    }

    public boolean update(Course course) throws SQLException {
        String query = "UPDATE Course SET name = ?, language_id = ?, level = ?, start_date = ?, end_date = ?, price = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, course.getName());
            ps.setInt(2, getLanguageId(course.getLanguage()));
            ps.setString(3, course.getLevel());
            ps.setDate(4, Date.valueOf(course.getStartDate()));
            ps.setDate(5, Date.valueOf(course.getEndDate()));
            ps.setDouble(6, course.getPrice());
            ps.setInt(7, course.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteById(int id) throws SQLException {
        String query = "DELETE FROM Course WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Course fromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        Language language = getLanguageById(rs.getInt("language_id"));
        String level = rs.getString("level");
        LocalDate startDate = rs.getDate("start_date").toLocalDate();
        LocalDate endDate = rs.getDate("end_date").toLocalDate();
        double price = rs.getDouble("price");

        Course course = new Course(name, language, level, startDate, endDate, price);
        course.setId(id);
        return course;
    }

    // Method to get the language_id from the Language object
    private int getLanguageId(Language language) throws SQLException {
        String query = "SELECT id FROM Language WHERE name = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, language.name());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new SQLException("Language " + language + " not found in database.");
            }
        }
    }

    // Method to get the Language object by its id
    private Language getLanguageById(int languageId) throws SQLException {
        String query = "SELECT name FROM Language WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, languageId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Language.valueOf(rs.getString("name"));
            } else {
                throw new SQLException("Language with ID " + languageId + " not found in database.");
            }
        }
    }
}
