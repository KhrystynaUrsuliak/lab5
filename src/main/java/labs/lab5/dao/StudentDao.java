package labs.lab5.dao;

import labs.lab5.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    private final Connection connection;

    public StudentDao() {
        try {
            connection = DataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Student add(Student student) throws SQLException {
        String query = "INSERT INTO student (first_name, last_name, email, initial_level) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getLastName());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getInitialLevel());
            ps.executeUpdate();

            // Retrieve and set generated ID
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                student.setId(generatedKeys.getInt(1));
            }
            return student;
        }
    }

    public List<Student> getAll() throws SQLException {
        String query = "SELECT * FROM student";
        List<Student> students = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                students.add(fromResultSet(rs));
            }
        }
        return students;
    }

    public Student getById(int id) throws SQLException {
        String query = "SELECT * FROM student WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return fromResultSet(rs);
            }
            throw new SQLException("Student with ID " + id + " not found.");
        }
    }

    public boolean update(Student student) throws SQLException {
        String query = "UPDATE student SET first_name = ?, last_name = ?, email = ?, initial_level = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getLastName());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getInitialLevel());
            ps.setInt(5, student.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteById(int id) throws SQLException {
        String query = "DELETE FROM student WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Student fromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String email = rs.getString("email");
        String initialLevel = rs.getString("initial_level");

        Student student = new Student(firstName, lastName, email, initialLevel);
        student.setId(id);
        return student;
    }
}
