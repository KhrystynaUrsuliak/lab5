package labs.lab5.dao;

import labs.lab5.model.Tutor;
import labs.lab5.model.Language;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TutorDao {
    private final Connection connection;

    public TutorDao() {
        try {
            connection = DataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Tutor add(Tutor tutor) throws SQLException {
        String query = "INSERT INTO tutor (first_name, last_name, birth_date, language_id, salary) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, tutor.getFirstName());
            ps.setString(2, tutor.getLastName());
            ps.setDate(3, Date.valueOf(tutor.getBirthDate()));
            ps.setInt(4, getLanguageId(tutor.getLanguage()));
            ps.setDouble(5, tutor.getSalary());
            ps.executeUpdate();

            // Retrieve the generated ID and set it in the Tutor object
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                tutor.setId(generatedKeys.getInt(1));
            }
            return tutor;
        }
    }

    public List<Tutor> getAll() throws SQLException {
        String query = "SELECT * FROM tutor";
        List<Tutor> tutors = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                tutors.add(fromResultSet(rs));
            }
        }
        return tutors;
    }

    public Tutor getById(int id) throws SQLException {
        String query = "SELECT * FROM tutor WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return fromResultSet(rs);
            }
            throw new SQLException("Tutor with ID " + id + " not found.");
        }
    }

    public boolean update(Tutor tutor) throws SQLException {
        String query = "UPDATE tutor SET first_name = ?, last_name = ?, birth_date = ?, language_id = ?, salary = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, tutor.getFirstName());
            ps.setString(2, tutor.getLastName());
            ps.setDate(3, Date.valueOf(tutor.getBirthDate()));
            ps.setInt(4, getLanguageId(tutor.getLanguage()));
            ps.setDouble(5, tutor.getSalary());
            ps.setInt(6, tutor.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteById(int id) throws SQLException {
        String query = "DELETE FROM tutor WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Tutor fromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        LocalDate birthDate = rs.getDate("birth_date").toLocalDate();
        int languageId = rs.getInt("language_id");
        Language language = getLanguageById(languageId);
        double salary = rs.getDouble("salary");

        Tutor tutor = new Tutor(firstName, lastName, birthDate, language, salary);
        tutor.setId(id);
        return tutor;
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
