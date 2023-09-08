import java.sql.*;
import java.util.Scanner;

public class CRUD {

    Connection connection = DriverManager.getConnection("jdbc:sqlite:libraryDatabase.db");
    PreparedStatement preparedStatement = null;
    Statement statement = null;
    ResultSet resultSet = null;
    Scanner scanner = new Scanner(System.in);

    public CRUD() throws SQLException {
    }

    public void editBook(int books_id){
        try {
            String selectQuery = "SELECT * FROM Library WHERE books_id = ?";
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, books_id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                System.out.println("Book found:");
                System.out.println("Title: " + resultSet.getString("title"));
                System.out.println("Author: " + resultSet.getString("author"));
                System.out.println("Genre: " + resultSet.getString("genre"));
                System.out.println("Description: " + resultSet.getString("description"));
                System.out.println("Available_copies: " + resultSet.getInt("available_copies"));
                System.out.println("Publisher: " + resultSet.getString("publisher"));

                System.out.println("Select what you want to edit:");
                System.out.println("1. Title");
                System.out.println("2. Author");
                System.out.println("3. Genre");
                System.out.println("4. Description");
                System.out.println("5. Available Copies");
                System.out.println("6. Publisher");
                System.out.println("7. Cancel");
                System.out.print("Enter your choice: ");


                int editChoice = scanner.nextInt();
                scanner.nextLine();

                String columnToEdit = "";
                String newValue = "";

                switch (editChoice) {
                    case 1:
                        columnToEdit = "title";
                        System.out.print("Enter new title: ");
                        newValue = scanner.nextLine();
                        break;
                    case 2:
                        columnToEdit = "author";
                        System.out.print("Enter new author: ");
                        newValue = scanner.nextLine();
                        break;
                    case 3:
                        columnToEdit = "genre";
                        System.out.print("Enter new genre: ");
                        newValue = scanner.nextLine();
                        break;
                    case 4:
                        columnToEdit = "description";
                        System.out.print("Enter new description: ");
                        newValue = scanner.nextLine();
                        break;
                    case 5:
                        columnToEdit = "available_copies";
                        System.out.print("Enter new available copies: ");
                        newValue = scanner.nextLine();
                        break;
                    case 6:
                        columnToEdit = "publisher";
                        System.out.print("Enter new publisher: ");
                        newValue = scanner.nextLine();
                        break;
                    case 7:
                        System.out.println("Edit canceled.");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                        return;
                }
                String updateQuery = "UPDATE Library SET "  + columnToEdit +  " = ? WHERE books_id = ?";
                preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(1, newValue);
                preparedStatement.setInt(2, books_id);
                preparedStatement.executeUpdate();

                System.out.println("Book information updated.");
            }else {
                System.out.println("Book not found.");
            }

        }catch (SQLException e){
        e.printStackTrace();
    }finally {
        try{
            if (resultSet != null){
                resultSet.close();
            }
            if (preparedStatement!= null){
                preparedStatement.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}


    public void addBookCopies(String title, int additionalCopies){
        try {
            String updateQuery = "UPDATE Library SET available_copies = available_copies + ? WHERE title = ?";
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setInt(1, additionalCopies);
            preparedStatement.setString(2, title);
            preparedStatement.executeUpdate();

            System.out.println("Added " + additionalCopies + " copies to the book: " + title);

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if (resultSet != null){
                    preparedStatement.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

    }

    public boolean doesBookExist(String title){
        try {
            String query = "SELECT COUNT(*) FROM Library WHERE title = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                int count = resultSet.getInt(1);
                return count > 0;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (resultSet != null){
                    resultSet.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean doesPublisherExist(String publisher){
        try {
            String query = "SELECT COUNT(*) FROM Library WHERE publisher = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, publisher);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                int count = resultSet.getInt(1);
                return  count > 0;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (resultSet != null){
                    resultSet.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return false;
    }
    
    public void createBook(String title, String author, String genre, String description, int available_copies, String publisher) {
        if (doesPublisherExist(publisher)) {
            System.out.println("Book with the same publisher already exists.");
            return;
        }
        if (doesBookExist(title)) {
            System.out.println("Book already exists. Adding copies...");
            addBookCopies(title, available_copies);
        } else {
            try {
                String insertQuery = "INSERT INTO Library(title, author, genre, description, available_copies, publisher) VALUES(?,?,?,?,?,?);";
                preparedStatement = connection.prepareStatement(insertQuery);

                preparedStatement.setString(1, title);
                preparedStatement.setString(2, author);
                preparedStatement.setString(3, genre);
                preparedStatement.setString(4, description);
                preparedStatement.setInt(5, available_copies);
                preparedStatement.setString(6, publisher);

                preparedStatement.executeUpdate();
                System.out.println("Book added !");

            } catch (SQLException e) {
                e.printStackTrace();
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

    public void deleteBook(int books_id) {
        try {
            String selectQuery = "SELECT * FROM Library WHERE books_id = ?";
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, books_id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Book found:");
                System.out.println("Title: " + resultSet.getString("title"));
                System.out.println("Author: " + resultSet.getString("author"));
                System.out.println("Genre: " + resultSet.getString("genre"));
                System.out.println("Description: " + resultSet.getString("description"));
                System.out.println("Available_copies: " + resultSet.getInt("available_copies"));
                System.out.println("Publisher: " + resultSet.getString("publisher"));

                System.out.println("Do you want delete this book?(yes/no): ");

                Scanner scanner = new Scanner(System.in);
                String confirmation = scanner.nextLine();

                if (confirmation.equalsIgnoreCase("yes")) {
                    System.out.print("Enter the number of copies to delete: ");
                    int copiesToDelete = scanner.nextInt();

                    int availableCopies = resultSet.getInt("available_copies");
                    if (copiesToDelete <= availableCopies) {
                        availableCopies -= copiesToDelete;

                        if (availableCopies == 0) {
                            String deleteQuery = "DELETE FROM Library WHERE books_id = ?";
                            preparedStatement = connection.prepareStatement(deleteQuery);
                            preparedStatement.setInt(1, books_id);
                            preparedStatement.executeUpdate();

                            System.out.println("Book with id: " + books_id + " deleted.");
                        } else {
                            String updateQuery = "UPDATE Library SET available_copies = ? WHERE books_id = ?";
                            preparedStatement = connection.prepareStatement(updateQuery);
                            preparedStatement.setInt(1, availableCopies);
                            preparedStatement.setInt(2, books_id);
                            preparedStatement.executeUpdate();

                            System.out.println(copiesToDelete + " copies of the book with id: " + books_id + " deleted.");
                        }
                    } else {
                        System.out.println("Not enough available copies to delete.");
                    }
                } else {
                    System.out.println("Deletion cancelled.");
                }
            } else {
                System.out.println("Book not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    public void readAllDataInTable() throws SQLException {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Library;");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                System.out.println("Library: ");
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = resultSet.getObject(i);
                    System.out.println(columnName + ": " + value);
                }
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void searchBooksByPhrase(String phrase){
        try {
            String searchQuery = "SELECT * FROM Library WHERE title LIKE ? OR author LIKE ? or genre LIKE ? OR description LIKE ? OR publisher LIKE ?";
            preparedStatement = connection.prepareStatement(searchQuery);
            preparedStatement.setString(1, "%" + phrase + "%");
            preparedStatement.setString(2, "%" + phrase + "%");
            preparedStatement.setString(3, "%" + phrase + "%");
            preparedStatement.setString(4, "%" + phrase + "%");
            preparedStatement.setString(5, "%" + phrase + "%");
            resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("Search results:");
                while (resultSet.next()) {
                    System.out.println("Title: " + resultSet.getString("title"));
                    System.out.println("Author: " + resultSet.getString("author"));
                    System.out.println("Genre: " + resultSet.getString("genre"));
                    System.out.println("Description: " + resultSet.getString("description"));
                    System.out.println("Available_copies: " + resultSet.getInt("available_copies"));
                    System.out.println("Publisher: " + resultSet.getString("publisher"));
                    System.out.println();
                }
            }else {
                System.out.println("No results found.");
            }


        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (resultSet != null){
                    resultSet.close();
                }
                if (preparedStatement != null){
                    preparedStatement.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }


    }
    public void searchBooksByTitle(String title){
        try {
            String searchQuery = "SELECT * FROM Library WHERE title LIKE ?";
            preparedStatement = connection.prepareStatement(searchQuery);
            preparedStatement.setString(1, "%" + title + "%");
            resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("Search results:");
            while (resultSet.next()) {
                System.out.println("Book found:");
                System.out.println("Title: " + resultSet.getString("title"));
                System.out.println("Author: " + resultSet.getString("author"));
                System.out.println("Genre: " + resultSet.getString("genre"));
                System.out.println("Description: " + resultSet.getString("description"));
                System.out.println("Available_copies: " + resultSet.getInt("available_copies"));
                System.out.println("Publisher: " + resultSet.getString("publisher"));
                System.out.println();
            }

            }else {
                System.out.println("No results found");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (resultSet != null){
                    resultSet.close();
                }
                if (preparedStatement != null){
                    preparedStatement.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }


    }
    public void searchBooksByPublisher(String publisher){
        try {
            String searchQuery = "SELECT * FROM Library WHERE publisher LIKE ?";
            preparedStatement = connection.prepareStatement(searchQuery);
            preparedStatement.setString(1, "%" + publisher + "%");
            resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("Search results: ");
                while (resultSet.next()) {
                    System.out.println("Title: " + resultSet.getString("title"));
                    System.out.println("Author: " + resultSet.getString("author"));
                    System.out.println("Genre: " + resultSet.getString("genre"));
                    System.out.println("Description: " + resultSet.getString("description"));
                    System.out.println("Available_copies: " + resultSet.getInt("available_copies"));
                    System.out.println("Publisher: " + resultSet.getString("publisher"));
                    System.out.println();
                }
            }else {
                System.out.println("No results found!");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (resultSet != null){
                    resultSet.close();
                }
                if (preparedStatement != null){
                    preparedStatement.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }


    }
    public void searchBooksByGenre(String genre){
        try {
            String searchQuery = "SELECT * FROM Library WHERE genre LIKE ?";
            preparedStatement = connection.prepareStatement(searchQuery);
            preparedStatement.setString(1, "%" +genre+"%");
            resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("Results found: ");
                while (resultSet.next()) {
                    System.out.println("Title: " + resultSet.getString("title"));
                    System.out.println("Author: " + resultSet.getString("author"));
                    System.out.println("Genre: " + resultSet.getString("genre"));
                    System.out.println("Description: " + resultSet.getString("description"));
                    System.out.println("Available_copies: " + resultSet.getInt("available_copies"));
                    System.out.println("Publisher: " + resultSet.getString("publisher"));
                    System.out.println();
                }
            }else {
                System.out.println("No results found");
            }


        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (resultSet != null){
                    resultSet.close();
                }
                if (preparedStatement != null){
                    preparedStatement.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws SQLException {



    }
}