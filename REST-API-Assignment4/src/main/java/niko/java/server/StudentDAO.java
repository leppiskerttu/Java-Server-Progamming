package niko.java.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private String jdbcURL = "jdbc:mariadb://mariadb.vamk.fi:3306/e2102947_students";
    private String jdbcUserName = "e2102947";
    private String jdbcPassword = "rYcBZh3fqSm";

    // Constructors (similar to UserDAO, you can create your own constructors)

    private static final String SELECT_ALL_STUDENTS_QUERY = "SELECT * FROM students";
    private static final String SELECT_STUDENT_BY_ID = "SELECT * FROM students WHERE id=?";
    private static final String INSERT_STUDENT_QUERY = "INSERT INTO students (id, firstName, lastName) VALUES (?, ?, ?)";
    private static final String DELETE_STUDENT_QUERY = "DELETE FROM students WHERE id=?";

    protected Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcURL, jdbcUserName, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void insertStudent(Student student) {
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_STUDENT_QUERY);) {
            ps.setString(1, student.getId());
            ps.setString(2, student.getFirstName());
            ps.setString(3, student.getLastName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> selectAllStudents() {
        List<Student> students = new ArrayList<Student>();

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ALL_STUDENTS_QUERY);) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");

                students.add(new Student(id, firstName, lastName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    public Student selectStudentByID(String id) {
        Student student = null;

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_STUDENT_BY_ID);) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");

                student = new Student(id, firstName, lastName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return student;
    }

    public boolean deleteStudent(String id) {
        boolean rowDeleted = false;
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_STUDENT_QUERY);) {
            ps.setString(1, id);
            rowDeleted = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
}
