import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Section {
    private String name;
    private int id;

    // Constructor

    public Section(String name) {
        this.name = name;
        this.id = id;
        DatabaseService databaseService = DatabaseService.getInstance();
        this.id = databaseService.get_all_sections() + 1;
        databaseService.create_section(this.id, this.name);
    }

    // return section's id based on the name

    static public int getSectionByName(String name_)
    {
        String query = "SELECT * FROM sections WHERE name = ?";
        DatabaseService databaseService = DatabaseService.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseService.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name_);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id_ = resultSet.getInt("id");
                return id_;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // returns a book's section

    static public String getSectionByBookId(int bookId)
    {
        String query = "SELECT * FROM books WHERE id = ?";
        DatabaseService databaseService = DatabaseService.getInstance();
        try {
            Connection connection = databaseService.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int section_ = resultSet.getInt("section");

                query = "SELECT * FROM sections WHERE id = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, section_);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next())
                {
                    String sec_name = resultSet.getString("name");
                    return sec_name;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";

    }

    @Override
    public String toString() {
        return "Section{" + name + "} ";
    }
}