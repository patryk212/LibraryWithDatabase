import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class LibraryApp {
    public static void main(String[] args) throws SQLException {

        CRUD crud = new CRUD();
        usersCRUD usersCRUD = new usersCRUD();

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("Welcome to my Library App!");
            System.out.println("1.Register");
            System.out.println("2.Login");
            System.out.println("3.Exit");

            int choice1 = scanner.nextInt();
            scanner.nextLine();

            switch (choice1) {

                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter first name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Enter last name: ");
                    String lastName = scanner.nextLine();

                    LocalDate currentDate = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String registrationDate = currentDate.format(formatter);

                    usersCRUD.registerUser(username, password, email, firstName, lastName, registrationDate);
                    break;

                case 2:

                    boolean loginSuccess = false;
                    String userRole = null;

                    while (!loginSuccess) {
                        System.out.print("Enter username: ");
                        String loginUsername = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String loginPassword = scanner.nextLine();

                        userRole = usersCRUD.loginUser(loginUsername, loginPassword);

                        if (userRole != null) {
                            System.out.println("Login successful! Welcome, " + loginUsername + ".");
                            loginSuccess = true;
                        } else {
                            System.out.println("Login failed! Invalid username or password.");
                        }
                    }

                    if (userRole.equals("user")) {
                        while (true) {
                            System.out.println("1.Show All Books");
                            System.out.println("2.Search books.");
                            System.out.println("3.Logout");
                            System.out.println("4.Exit");
                            System.out.println("Enter your choice:");

                            int choice = scanner.nextInt();
                            scanner.nextLine();

                            switch (choice) {

                                case 1:
                                    crud.readAllDataInTable();
                                    break;

                                case 2:
                                    System.out.println("Search options:");
                                    System.out.println("1. By phrase");
                                    System.out.println("2. By publisher");
                                    System.out.println("3. By title");
                                    System.out.println("4. By genre");
                                    System.out.print("Enter your choice: ");

                                    int searchChoice = scanner.nextInt();
                                    scanner.nextLine();

                                    switch (searchChoice) {
                                        case 1:
                                            System.out.print("Enter the phrase to search for: ");
                                            String searchPhrase = scanner.nextLine();
                                            crud.searchBooksByPhrase(searchPhrase);
                                            break;

                                        case 2:
                                            System.out.print("Enter the publisher to search for: ");
                                            String searchPublisher = scanner.nextLine();
                                            crud.searchBooksByPublisher(searchPublisher);
                                            break;

                                        case 3:
                                            System.out.print("Enter the title: ");
                                            String searchTitle = scanner.nextLine();
                                            crud.searchBooksByTitle(searchTitle);
                                            break;

                                        case 4:
                                            System.out.print("Enter the genre: ");
                                            String searchGenre = scanner.nextLine();
                                            crud.searchBooksByGenre(searchGenre);
                                            break;

                                        default:
                                            System.out.println("Invalid choice!");
                                    }
                                    break;

                                case 3:
                                    System.out.println("Logged out. Goodbye!");
                                    userRole = null;
                                    break;

                                case 4:
                                    System.out.println("Goodbye!");
                                    return;

                                default:
                                    System.out.println("Invalid choice!");
                            }
                        }
                    } else if (userRole.equals("employee")) {
                        while (true) {
                            System.out.println("1.Show All Books");
                            System.out.println("2.Add Book");
                            System.out.println("3.Remove Book");
                            System.out.println("4.Add copies to existing book");
                            System.out.println("5.Search books.");
                            System.out.println("6.Edit book information.");
                            System.out.println("7.Logout");
                            System.out.println("8.Exit");
                            System.out.println("Enter your choice:");

                            int choice = scanner.nextInt();
                            scanner.nextLine();

                            switch (choice) {

                                case 1:
                                    crud.readAllDataInTable();
                                    break;

                                case 2:
                                    System.out.println("Enter book details: ");
                                    System.out.print("Title: ");
                                    String title = scanner.nextLine();
                                    System.out.print("Author: ");
                                    String author = scanner.nextLine();
                                    System.out.print("Genre: ");
                                    String genre = scanner.nextLine();
                                    System.out.print("Description: ");
                                    String description = scanner.nextLine();
                                    System.out.print("How many copies you want to add: ");
                                    int available_copies = scanner.nextInt();
                                    scanner.nextLine();
                                    System.out.print("Publisher: ");
                                    String publisher = scanner.nextLine();

                                    crud.createBook(title, author, genre, description, available_copies, publisher);
                                    break;

                                case 3:
                                    System.out.print("Enter book id to delete: ");
                                    int bookToDelete = scanner.nextInt();
                                    crud.deleteBook(bookToDelete);
                                    break;

                                case 4:
                                    System.out.print("Enter the title of the book to add copies to: ");
                                    String bookTitleToAddCopies = scanner.nextLine();

                                    if (crud.doesBookExist(bookTitleToAddCopies)) {
                                        System.out.print("Enter the number of copies to add: ");
                                        int copiesToAdd = scanner.nextInt();
                                        scanner.nextLine();

                                        crud.addBookCopies(bookTitleToAddCopies, copiesToAdd);
                                    } else {
                                        System.out.println("Book not found");
                                    }
                                    break;

                                case 5:
                                    System.out.println("Search options:");
                                    System.out.println("1. By phrase");
                                    System.out.println("2. By publisher");
                                    System.out.println("3. By title");
                                    System.out.println("4. By genre");
                                    System.out.print("Enter your choice: ");

                                    int searchChoice = scanner.nextInt();
                                    scanner.nextLine();

                                    switch (searchChoice) {
                                        case 1:
                                            System.out.print("Enter the phrase to search for: ");
                                            String searchPhrase = scanner.nextLine();
                                            crud.searchBooksByPhrase(searchPhrase);
                                            break;

                                        case 2:
                                            System.out.print("Enter the publisher to search for: ");
                                            String searchPublisher = scanner.nextLine();
                                            crud.searchBooksByPublisher(searchPublisher);
                                            break;

                                        case 3:
                                            System.out.print("Enter the title: ");
                                            String searchTitle = scanner.nextLine();
                                            crud.searchBooksByTitle(searchTitle);
                                            break;

                                        case 4:
                                            System.out.print("Enter the genre: ");
                                            String searchGenre = scanner.nextLine();
                                            crud.searchBooksByGenre(searchGenre);
                                            break;

                                        default:
                                            System.out.println("Invalid choice!");
                                    }
                                    break;

                                case 6:
                                    System.out.print("Enter book id to edit: ");
                                    int bookToEdit = scanner.nextInt();
                                    crud.editBook(bookToEdit);
                                    break;

                                case 7:
                                    System.out.println("Logged out. Goodbye!");
                                    userRole = null;
                                    break;

                                case 8:
                                    System.out.println("Goodbye!");
                                    return;

                                default:
                                    System.out.println("Invalid choice!");
                            }
                        }
                    } else {
                        System.out.println("Login failed. Invalid username or password.");
                    }
                    break;

                case 3:
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
