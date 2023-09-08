import java.sql.*;

public class usersCRUD {


    Connection connection = DriverManager.getConnection("jdbc:sqlite:libraryDatabase.db");
    PreparedStatement preparedStatement = null;
    Statement statement = null;
    ResultSet resultSet = null;


    public usersCRUD() throws SQLException {

    }

    public String loginUser(String username, String password) {
        try {
            String selectQuery = "SELECT role FROM users WHERE username = ? AND password = ?";
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("role");
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void registerUser(String username, String password, String email, String first_name, String last_name, String registration_date){
        try {
            String insertQuery = "INSERT INTO users (username, password, email, first_name, last_name, role, registration_date) VALUES (?, ?, ?, ?, ?, 'user', ?);";
            preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, first_name);
            preparedStatement.setString(5, last_name);
            preparedStatement.setString(6, registration_date);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0){
                System.out.println("User registered successfully!");
            }else {
                System.out.println("User registration failed.");
            }

        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("User registration failed.");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

