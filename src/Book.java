import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class Book {
    private int id;
    private String title;
    private int author;
    private int pages;
    private int section;
    private int year;

    // Constructor

    public Book(String title, int pages, int year, int author,  int section) {
        this.title = title;
        this.pages = pages;
        this.year = year;
        this.author = author;
        this.section = section;

        DatabaseService databaseService = DatabaseService.getInstance();
        this.id = databaseService.get_all_books() + 1;
        databaseService.create_book(this.id, this.title, this.pages, this.year, this.author, this.section);

    }

    // returns the title of a book based on books's id

    static public String get_title_by_id(int id_)
    {
        String query = "SELECT * FROM books WHERE id = ?";
        DatabaseService databaseService = DatabaseService.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseService.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id_);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String title = resultSet.getString("title");
                return title;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    // returns the id of the book based on the book's title

    public static int get_book(String title)
    {
        String query = "SELECT * FROM books WHERE title = ?";
        DatabaseService databaseService = DatabaseService.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseService.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                int id_ = resultSet.getInt("id");
                return id_;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // shows all books from the database

    public static void show_all_books()
    {
        String query = "SELECT * FROM books";
        DatabaseService databaseService = DatabaseService.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseService.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                int author_ = resultSet.getInt("author");
                int person_id = Author.getPersonByAuthorId(author_);
                String name_ = Person.getName(person_id);
                int pages_ = resultSet.getInt("pages");
                int year_ = resultSet.getInt("year");

                System.out.println(title + " - " + author_ + " (" + year_ + "," + pages_ + " pages)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString()
    {
        return "{" + title + ", " + author + ", " + pages + ", " + year + ", " + section + "}";
    }
}