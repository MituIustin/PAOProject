import java.util.*;
import java.util.List;

public class Reader extends Person {
    private String username;            // used for log in
    private String password;            // used for log in
    private Set<Integer> books_id;      // id's of read books

    // Constructor

    public Reader(String f_name, String l_name, int birth_year, String username, String password, Set<Integer> books_id)
    {
        super(f_name, l_name, birth_year);
        this.username = username;
        this.password = password;
        if (books_id != null) {
            this.books_id = books_id;
        }
        else
        {
            this.books_id = new HashSet<>();
        }
    }

    // Getters

    private String get_username()
    {
        return this.username;
    }
    public Set<Integer> get_books_id()
    {
        return books_id;
    }

    public static Reader get_reader(Library library, int id)
    {
        for (Reader reader : library.get_readers())
        {
            if(reader.get_id() == id)
            {
                return reader;
            }
        }
        return null;
    }

    // This method shows on the screen all books that the user read.

    public void show_already_read(List<Book> books)
    {
        for(Book book : books)
        {
            if (books_id.contains(book.get_id()))
            {
                System.out.println(book);
            }
        }
    }


    public void add_read_book(int book_id)
    {
        books_id.add(book_id);
    }

    // Static methods

    // Used for log in. Checks all readers usernames and returns it's id.

    private static int check_username(List<Reader> readers, String username)
    {
        for(Reader reader : readers)
        {
            if(Objects.equals(username, reader.get_username()))
            {
                return reader.id;
            }
        }
        return -1;
    }

    // Same as check_username but return true/false based on the password

    private static boolean check_password(List<Reader> readers, int id,  String password)
    {
        for(Reader reader : readers)
        {
            if(reader.id == id)
            {
                return reader.password.equals(password);
            }
        }
        return false;
    }

    // Log In function

    public static int log_in(Library library)
    {
        List<Reader> users = library.get_readers();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        int id_user = Reader.check_username(users, username);

        while(id_user == -1)
        {
            System.out.println("There is no account associated with this username, try again.");
            username = scanner.nextLine();
            id_user = check_username(users, username);
        }

        System.out.println("Enter your password: ");
        String user_password = scanner.nextLine();
        boolean succes = check_password(users, id_user, user_password);
        while (!succes)
        {
            System.out.println("Wrong password, try again.");
            user_password = scanner.nextLine();
            succes = check_password(users, id_user, user_password);
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
