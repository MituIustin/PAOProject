import java.util.Scanner;

public class Service {

    Library library;

    public Service()
    {
        library = new Library();
    }

    public int show_services(int user_id)
    {
        Scanner scanner = new Scanner(System.in);
        if(user_id == 0)
        {
            System.out.println("Welcome ! You need an account ! Pick a service :\n");
            System.out.println("1. Log In");
            System.out.println("2. Sing In");
            System.out.println("3. Exit");
        }
        else
        {
            System.out.println("Pick a service :\n");
            System.out.println("1. Show Books");
            System.out.println("2. Show Authors");
            System.out.println("3. Find my favorite section");
            System.out.println("4. Show me which books I borrowed");
            System.out.println("5. Borrow a book");
            System.out.println("6. Return a book");
            System.out.println("7. Change password");
            System.out.println("8. Donate a book");
            System.out.println("9. Log Out");
            System.out.println("10. Exit");
        }
        return scanner.nextInt();
    }

    public int execute_command(int command, int user_id)
    {
        if(user_id == 0)
        {
            switch(command)
            {
                case 1:
                    user_id = Reader.log_in(library);
                    break;
                case 2:
                    user_id = library.add_reader();
                    break;
                case 3:
                    return  -1;

            }
        }
        else if (user_id > 0)
        {
            switch(command) {
                case 1:
                    library.show_books();
                    break;
                case 2:
                    Author.show_authors();
                    break;
                case 3:
                    library.fav_section(user_id);
                    break;
                case 4:
                    BookLoan.show_borrowed_books(user_id);
                    break;
                case 5:
                    library.borrow_book(user_id);
                    break;
                case 6:
                    library.return_book(user_id);
                    break;
                case 7:
                    Reader.change_password(user_id);
                    break;
                case 8:
                    library.add_book();
                    break;
                case 9:
                    return 0;
                case 10:
                    return -1;
            }
        }
        return user_id;
    }
}