import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Person {
    protected int id;
    protected String f_name;
    protected String l_name;
    private final int birth_year;

    // Constructor

    public Person(String f_name, String l_name, int birth_year, boolean create) {
        DatabaseService databaseService = DatabaseService.getInstance();
        this.id = databaseService.get_all_persons() + 1;
        this.f_name = f_name;
        this.l_name = l_name;
        this.birth_year = birth_year;
        if(create)
        {
            databaseService.create_person(this.id, this.f_name, this.l_name, this.birth_year);
        }
    }

    // returns f_name + " " + l_name

    static public String getName(int personId) {

        String query = "SELECT * FROM persons WHERE id = ?";
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
                String f_name_ = resultSet.getString("f_name");
                String l_name_ = resultSet.getString("l_name");

                return f_name_ + " " + l_name_;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    // returns the id of a person

    static public int getIdByName(String name) {

        String query = "SELECT * FROM persons WHERE CONCAT(f_name, ' ', l_name) = ?";;
        DatabaseService databaseService = DatabaseService.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseService.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int personId = resultSet.getInt("id");
                return personId;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    // Getters

    public int get_id()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return f_name + ", " + l_name + ", " + birth_year;
    }
}
