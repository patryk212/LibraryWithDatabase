import java.sql.*;

public class DatabaseTableData {

    public static void main(String[] args) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:sqlite:libraryDatabase.db");

        try {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Users;");

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (resultSet.next()){
            System.out.println("Library: ");
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object value = resultSet.getObject(i);
                System.out.println(columnName + ": " + value);
            }
            System.out.println();
        }

        resultSet.close();
        statement.close();
        connection.close();


        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
