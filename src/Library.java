import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Library {
    private List<Integer> books;
    private List<Integer> authors;
    private List<Integer> readers;
    private List<Integer> sections;
    private List<Integer> book_loans;

    // creates the library

    public Library()
    {
        DatabaseService databaseService = DatabaseService.getInstance();
        books = databaseService.getAllBooks();
        authors = databaseService.getAllAuthors();
        readers = databaseService.getAllReaders();
        sections = databaseService.getAllSections();
        book_loans = databaseService.getAllBookLoans();
    }

    // Sign In function, adds a new reader.

    public int add_reader()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your first name: ");
        String first_name = scanner.nextLine();
        System.out.print("Enter your last name: ");
        String last_name = scanner.nextLine();
        System.out.print("Enter your birthday year: ");
        int birthday_year = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter an username: ");
        String username = scanner.nextLine();
        System.out.print("Enter a password: ");
        String password = scanner.nextLine();
        Reader new_reader = new Reader(first_name, last_name, birthday_year, username, password, true);
        readers.add(new_reader.get_id());
        return new_reader.get_id();
    }

    // Shows on the screen all the books based on the user's preferences.

    public void show_books()
    {
        System.out.println("Pick a number!\n");
        System.out.println("1. Show all books.");
        System.out.println("2. Show all books of an author.");
        System.out.println("3. Show all books with a specific genre.");
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        scanner.nextLine();
        while (option < 1 || option > 3)
        {
            System.out.println("Invalid option!");
            option = scanner.nextInt();
            scanner.nextLine();
        }
        if(option == 1)
        {
            Book.show_all_books();
        } else if(option == 2)
        {
            System.out.println("What author?");
            boolean exists = false;
            String author_s_name = scanner.nextLine();
            String query = "SELECT * FROM books";
            DatabaseService databaseService = DatabaseService.getInstance();
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try {
                connection = databaseService.getConnection();
                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String title = resultSet.getString("title");
                    int author_ = resultSet.getInt("author");
                    int person_id = Author.getPersonByAuthorId(author_);
                    String name_ = Person.getName(person_id);
                    int pages_ = resultSet.getInt("pages");
                    int year_ = resultSet.getInt("year");

                    if(name_.equals(author_s_name))
                    {
                        System.out.println(title + " - " + author_ + " (" + year_ + "," + pages_ + " pages)\n");
                        exists = true;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (!exists)
            {
                System.out.println("Author not found!");
            }


        } else
        {
            System.out.println("What section?");
            String genre_s_name = scanner.nextLine();
            int section = Section.getSectionByName(genre_s_name);

            String query = "SELECT * FROM books";
            DatabaseService databaseService = DatabaseService.getInstance();
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try {
                connection = databaseService.getConnection();
                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String title = resultSet.getString("title");
                    int author_ = resultSet.getInt("author");
                    int person_id = Author.getPersonByAuthorId(author_);
                    String name_ = Person.getName(person_id);
                    int pages_ = resultSet.getInt("pages");
                    int year_ = resultSet.getInt("year");
                    int section_ = resultSet.getInt("section");

                    if(section_ == section)
                    {
                        System.out.println(title + " - " + author_ + " (" + year_ + "," + pages_ + " pages)\n");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (section == -1)
            {
                System.out.println("Section not found!");
            }
        }
    }

    // This functions checks all the books that the user read, and finds the most read section.

    public void fav_section(int user_id)
    {
        List<Integer> books_id = BookLoan.getBooksByReaderId(user_id);
        int max_freq = -1;
        String fav_section = "";
        for(Integer book_id : books_id)
        {
            int freq = 0;
            String section = Section.getSectionByBookId(book_id);
            for(Integer book_idd : books_id)
            {
                String temp_sec = Section.getSectionByBookId(book_idd);
                if(temp_sec.equals(section))
                {
                    freq++;
                }
            }
            if(freq > max_freq)
            {
                max_freq = freq;
                fav_section = section;
            }
        }
        System.out.println(fav_section + "\n");
    }

    // Donate a book, add a new book to the library.

    public void add_book()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the title of the book :");
        String title = scanner.nextLine();
        System.out.println("How many pages ?");
        int pages = scanner.nextInt();
        System.out.println("What year was it written?");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the author of the book :");
        String authors_name = scanner.nextLine();
        int person = Person.getIdByName(authors_name);
        int author = Author.getIdByPersonId(person);
        if (author == -1)
        {
            System.out.println("This author does not exist!");
            return;
        }
        System.out.println("Enter the genre of the book :");
        String genre = scanner.nextLine();
        int section = Section.getSectionByName(genre);
        if(section == -1)
        {
            System.out.println("This section does not exist!");
            return;
        }
        Book new_book = new Book(title, pages,year, author, section);
        System.out.println("Thanks for your donation !");
    }

    // Borrow a book function,

    public void borrow_book(int user_id)
    {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the title of the book :");
        String title = scanner.nextLine();
        int borrowed_book = Book.get_book(title);
        while(borrowed_book == -1)
        {
            System.out.println("That book does not exist! Try again :");
            title = scanner.nextLine();
            borrowed_book = Book.get_book(title);
        }
        BookLoan loan = new BookLoan(borrowed_book, user_id);
        System.out.println("You borrowed the book succesfully!");
    }



    private boolean deleteBookLoan(int userId, int bookId) {
        String query = "DELETE FROM bookloan WHERE reader = ? AND book = ?";
        DatabaseService databaseService = DatabaseService.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean success = false;

        try {
            connection = databaseService.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, bookId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                AuditService.logAction("delete_bookloan");
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    // Return a book function

    public void return_book(int user_id)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the title of the book :");
        String title = scanner.nextLine();
        int borrowed_book = Book.get_book(title);
        while(borrowed_book == -1)
        {
            System.out.println("You don't have that book! Try again.");
            title = scanner.nextLine();
            borrowed_book = Book.get_book(title);
        }
        if (deleteBookLoan(user_id, borrowed_book)) {
            System.out.println("You returned the book successfully!");
        } else {
            System.out.println("Failed to return the book. Please try again.");
        }
    }
}
