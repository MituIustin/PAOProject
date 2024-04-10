public class Main {
    public static void main(String[] args) {

        // Basic logic for the application

        int user = 0;
        Service service = new Service();
        while (user != -1)
        {
            int command = service.show_services(user);
            user = service.execute_command(command, user);
        }
        System.out.println("Have a nice day!");
    }
}
