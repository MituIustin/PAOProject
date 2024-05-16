import java.util.*;
import java.util.List;
import java.sql.*;

public class Reader extends Person {
    private String username;            // used for log in
    private String password;            // used for log in

    // Constructor

    public Reader(String f_name, String l_name, int birth_year, String username, String password, boolean create)
    {
        super(f_name, l_name, birth_year, create);
        this.username = username;
        this.password = password;
        if(create) {
            DatabaseService databaseService = DatabaseService.getInstance();
            databaseService.create_reader(this.username, this.password, super.get_id());
        }
    }

    public String get_password()
    {
        return password;
    }

    // returns a Reader variable

    static public Reader getReaderById(int readerId) {
        Reader reader = null;
        String query = "SELECT * FROM readers WHERE id = ?";
        DatabaseService databaseService = DatabaseService.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseService.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, readerId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String username_ = resultSet.getString("username");
                String password_ = resultSet.getString("password");

                reader = new Reader("", "", 0, username_, password_, false);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reader;
    }

    // Change password method

    static public void change_password(int user_id) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your current password: ");
        String current_password = scanner.nextLine();

        String selectQuery = "SELECT password FROM readers WHERE id = ?";
        String updateQuery = "UPDATE readers SET password = ? WHERE id = ?";
        DatabaseService databaseService = DatabaseService.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseService.getConnection();
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, user_id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                if (!current_password.equals(storedPassword)) {
                    System.out.println("Password does not match");
                    return;
                }
            } else {
                System.out.println("User not found");
                return;
            }

            resultSet.close();
            preparedStatement.close();

            System.out.print("Enter your new password: ");
            String new_password = scanner.nextLine();

            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, new_password);
            preparedStatement.setInt(2, user_id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Password changed successfully!");
                AuditService.logAction("update_readers");
            } else {
                System.out.println("Failed to change password. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Used for log in. Checks all readers usernames and returns it's id.

    static private int check_username(String username) {
        String query = "SELECT id FROM readers WHERE username = ?";
        DatabaseService databaseService = DatabaseService.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int readerId = -1;

        try {
            connection = databaseService.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                readerId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return readerId;
    }

    // Same as check_username but return true/false based on the password

    private static boolean check_password(int id,  String password)
    {
        Reader r = Reader.getReaderById(id);
        return Objects.equals(password, r.get_password());
    }

    // Log In function

    public static int log_in(Library library)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        int id_user = Reader.check_username(username);

        while(id_user == -1)
        {
            System.out.println("There is no account associated with this username, try again.");
            username = scanner.nextLine();
            id_user = check_username(username);
        }

        System.out.println("Enter your password: ");
        String user_password = scanner.nextLine();
        boolean succes = check_password(id_user, user_password);
        while (!succes)
        {
            System.out.println("Wrong password, try again.");
            user_password = scanner.nextLine();
            succes = check_password(id_user, user_password);
        }

        System.out.println("You have successfully logged in.");
        return id_user;
    }

    @Override
    public String toString()
    {
        return "Reader {" + super.toString() + "} ";
    }
}
