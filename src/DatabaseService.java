import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {
    private static DatabaseService instance;
    private Connection connection;

    private DatabaseService() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/login",
                    "root",
                    "password"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void create_person(int id, String firstName, String lastName, int birth_year) {
        try {
            String sql = "INSERT INTO persons (id, f_name, l_name, birth_year) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            statement.setInt(4, birth_year);
            statement.executeUpdate();
            AuditService.logAction("create_person");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void create_reader(String username, String password, int person) {
        try {
            String sql = "INSERT INTO readers (username, password, person) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setInt(3, person);
            statement.executeUpdate();
            AuditService.logAction("create_reader");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void create_author(boolean alive, int person) {
        try {
            String sql = "INSERT INTO authors (alive, person) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1, alive);
            statement.setInt(2, person);
            statement.executeUpdate();
            AuditService.logAction("create_author");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void create_book(int id, String title, int pages, int year, int author, int section) {
        try {
            String sql = "INSERT INTO books (id, title, pages, year, author, section) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setString(2, title);
            statement.setInt(3, pages);
            statement.setInt(4, year);
            statement.setInt(5, author);
            statement.setInt(6, section);
            statement.executeUpdate();
            AuditService.logAction("create_book");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void create_bookloan(int book, int author) {
        try {
            String sql = "INSERT INTO bookloan (book, reader) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, book);
            statement.setInt(2, author);
            statement.executeUpdate();
            AuditService.logAction("create_bookloan");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void create_section(int id_, String name_) {
        try {
            String sql = "INSERT INTO sections (id, name) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id_);
            statement.setString(2, name_);
            statement.executeUpdate();
            AuditService.logAction("create_section");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int get_all_persons() {
        try {
            String sql = "SELECT COUNT(*) FROM persons";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public int get_all_books() {
        try {
            String sql = "SELECT COUNT(*) FROM books";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public int get_all_sections() {
        try {
            String sql = "SELECT COUNT(*) FROM sections";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int get_all_bookloans() {
        try {
            String sql = "SELECT COUNT(*) FROM bookloan";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Integer> getAllBooks() {
        List<Integer> bookIds = new ArrayList<>();
        try {
            String sql = "SELECT id FROM books";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int bookId = resultSet.getInt("id");
                bookIds.add(bookId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookIds;
    }
    public List<Integer> getAllAuthors() {
        List<Integer> authorIds = new ArrayList<>();
        try {
            String sql = "SELECT idauthors FROM authors";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int authorId = resultSet.getInt("idauthors");
                authorIds.add(authorId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authorIds;
    }
    public List<Integer> getAllBookLoans() {
        List<Integer> bookloanIds = new ArrayList<>();
        try {
            String sql = "SELECT id FROM bookloan";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int bookloanId = resultSet.getInt("id");
                bookloanIds.add(bookloanId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookloanIds;
    }
    public List<Integer> getAllReaders() {
        List<Integer> readerIds = new ArrayList<>();
        try {
            String sql = "SELECT id FROM readers";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int readerId = resultSet.getInt("id");
                readerIds.add(readerId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return readerIds;
    }
    public List<Integer> getAllSections() {
        List<Integer> sectionIds = new ArrayList<>();
        try {
            String sql = "SELECT id FROM sections";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int sectionId = resultSet.getInt("id");
                sectionIds.add(sectionId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sectionIds;
    }

}


