import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookLoan {
    private int id;
    private int book;
    private int reader;

    // Constructor

    public BookLoan(int book, int reader){
        this.book = book;
        this.reader = reader;
        DatabaseService databaseService = DatabaseService.getInstance();
        this.id = databaseService.get_all_bookloans() + 1;
        databaseService.create_bookloan(book, reader);
    }

    // returns a list of books ids

    static public List<Integer> getBooksByReaderId(int readerId)
    {
        String query = "SELECT * FROM bookloan";
        DatabaseService databaseService = DatabaseService.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Integer> bookIds = new ArrayList<>();
        try {
            connection = databaseService.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int reader_ = resultSet.getInt("reader");
                int book_ = resultSet.getInt("book");
                if(reader_ == readerId)
                {

                    bookIds.add(book_);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookIds;
    }

    // shows all books that a reader borrowed

    public static void show_borrowed_books(int user_id)
    {
        List<Integer> books_id = getBooksByReaderId(user_id);
        for(Integer bookid : books_id)
        {
            String title = Book.get_title_by_id(bookid);
            System.out.println(title);
        }
    }

}
