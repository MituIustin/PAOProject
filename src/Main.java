import java.sql.*;

public class Main {
    public static void main(String[] args) {

        DatabaseService databaseService = DatabaseService.getInstance();
        int user = 0;
        Service service = new Service();

        // basic logic for the login system

        while (user != -1)
        {
            int command = service.show_services(user);
            user = service.execute_command(command, user);
        }
        System.out.println("Have a nice day!");
    }
}
