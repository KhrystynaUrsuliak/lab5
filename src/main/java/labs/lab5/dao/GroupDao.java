package labs.lab5.dao;

import labs.lab5.model.Group;
import labs.lab5.model.Course;
import labs.lab5.model.Tutor;
import labs.lab5.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDao {
    private final Connection connection;

    public GroupDao() {
        try {
            connection = DataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Group add(Group group) throws SQLException {
        String query = "INSERT INTO groupings (name, course_id, tutor_id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, group.getName());
            ps.setInt(2, group.getCourse().getId()); // course_id as int
            ps.setInt(3, group.getTutor().getId());   // tutor_id as int
            ps.executeUpdate();

            // Retrieve and set generated ID
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                group.setId(generatedKeys.getInt(1));
            }
            return group;
        }
    }

    public List<Group> getAll() throws SQLException {
        String query = "SELECT * FROM groupings";
        List<Group> groups = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                groups.add(fromResultSet(rs));
            }
        }
        return groups;
    }

    public Group getById(int id) throws SQLException {
        String query = "SELECT * FROM groupings WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return fromResultSet(rs);
            }
            throw new SQLException("Group with ID " + id + " not found.");
        }
    }

    public boolean update(Group group) throws SQLException {
        String query = "UPDATE groupings SET name = ?, course_id = ?, tutor_id = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, group.getName());
            ps.setInt(2, group.getCourse().getId());
            ps.setInt(3, group.getTutor().getId());
            ps.setInt(4, group.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteById(int id) throws SQLException {
        String query = "DELETE FROM groupings WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Group fromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");

        int courseId = rs.getInt("course_id");
        int tutorId = rs.getInt("tutor_id");

        Course course = new CourseDao().getById(courseId);
        Tutor tutor = new TutorDao().getById(tutorId);

        Group group = new Group(name, course, tutor, new ArrayList<>());
        group.setId(id);
        return group;
    }
}
