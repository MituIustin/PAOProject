import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Author extends Person {
    private final boolean alive;

    // Constructors

    public Author(String f_name, String l_name, int b_year, boolean alive, boolean create)
    {
        super(f_name, l_name, b_year, create);
        this.alive = alive;
        DatabaseService databaseService = DatabaseService.getInstance();
        databaseService.create_author(this.alive, super.get_id());
    }

    // return person's id based on authors's id

    static public int getPersonByAuthorId(int authorId) {

        String query = "SELECT * FROM authors WHERE idauthors = ?";
        DatabaseService databaseService = DatabaseService.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseService.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, authorId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("person");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;

    }

    // returns auhors's id based on person's id

    static public int getIdByPersonId(int personId)
    {
        String query = "SELECT * FROM authors WHERE person = ?";
        DatabaseService databaseService = DatabaseService.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseService.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, personId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("idauthors");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // show all authors drom the database

    public static void show_authors()
    {
        String query = "SELECT * FROM authors";
        DatabaseService databaseService = DatabaseService.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseService.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id_ = resultSet.getInt("idauthors");
                int person_id = Author.getPersonByAuthorId(id_);
                String name_ = Person.getName(person_id);

                System.out.println(name_ + " \n" );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString()
    {
        return "Author{" + super.toString() + "} ";
    }

}
