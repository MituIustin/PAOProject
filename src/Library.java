import java.util.*;

public class Library {
    private List<Book> books;
    private List<Author> authors;
    private List<Reader> readers;
    private List<Section> sections;
    private List<BookLoan> book_loans;

    // Getters

    public List<Book> get_books() {
        return books;
    }
    public List<Author> get_authors() {
        return authors;
    }
    public List<Reader> get_readers() {
        return readers;
    }
    public List<Section> get_sections()
    {
        return sections;
    }
    public List<BookLoan> get_book_loans() {
        return book_loans;
    }

    // Returns reader based on his id.

    public Reader get_reader(int id)
    {
        for(Reader reader : readers)
        {
            if (id == reader.get_id())
            {
                return reader;
            }
        }
        return null;
    }

    // Function that replaces the database that will be implemented.

    private void create_books()
    {
        books = new ArrayList<>();

        Book book1 = new Book("Time of Contempt", 200 , 1992, Author.get_author(this, "Andrzej Sapkowski"), Section.get_section(this, "Fiction"));
        Book book2 = new Book("Baptism of Fire", 230 , 1994, Author.get_author(this, "Andrzej Sapkowski"), Section.get_section(this, "Fiction"));
        Book book3 = new Book("The Last Wish", 300 , 1991, Author.get_author(this, "Andrzej Sapkowski"), Section.get_section(this, "Fiction"));
        Book book4 = new Book("Thus Spoke Zarathustra", 100, 1883, Author.get_author(this, "Friedrich Nietzsche"), Section.get_section(this, "Philosophy"));
        Book book5 = new Book("Critique of Pure Reason", 150, 1781, Author.get_author(this, "Immanuel Kant"), Section.get_section(this, "Philosophy"));
        Book book6 = new Book("The Raven", 50, 1845, Author.get_author(this,"Edgar Poe"), Section.get_section(this, "Poetry"));
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        books.add(book6);
    }

    // Function that replaces the database that will be implemented.

    private void create_authors()
    {
        authors = new ArrayList<>();
        Author author1 = new Author("Andrzej", "Sapkowski", 1948, true);
        Author author2 = new Author("Friedrich", "Nietzsche", 1844, false);
        Author author3 = new Author("Immanuel", "Kant", 1724, false);
        Author author4 = new Author("Edgar", "Poe", 1809, false);
        authors.add(author1);
        authors.add(author2);
        authors.add(author3);
        authors.add(author4);
    }

    // Function that replaces the database that will be implemented.

    private void create_readers()
    {
        readers = new ArrayList<>();
        Set<Integer> read_books = new HashSet<>();
        read_books.add(1);
        read_books.add(2);
        read_books.add(3);
        read_books.add(4);
        Reader reader1 = new Reader("Iustin", "Mitu", 2003, "iustin.mitu@gmail.com", "iustin123", read_books);
        Reader reader2 = new Reader("Alexia", "Ionescu", 2004, "alexia.ionescu@gmail.com", "alexia123", null);
        readers.add(reader1);
        readers.add(reader2);
    }

    // Function that replaces the database that will be implemented.

    private void create_sections()
    {
        sections = new ArrayList<>();
        Section section1 = new Section("Software Engineering");
        Section section2 = new Section("Philosophy");
        Section section3 = new Section("Fiction");
        Section section4 = new Section("Poetry");
        sections.add(section1);
        sections.add(section2);
        sections.add(section3);
        sections.add(section4);
    }

    // Function that replaces the database that will be implemented.

    private void create_bookloans()
    {
        book_loans = new ArrayList<>();
        BookLoan book_loan1 = new BookLoan(Book.get_book(this, 1), Reader.get_reader(this, 5));
        BookLoan book_loan2 = new BookLoan(Book.get_book(this,2), Reader.get_reader(this, 6));
        BookLoan book_loan3 = new BookLoan(Book.get_book(this,3), Reader.get_reader(this, 5));
        BookLoan book_loan4 = new BookLoan(Book.get_book(this,4), Reader.get_reader(this, 6));
        BookLoan book_loan5 = new BookLoan(Book.get_book(this,5), Reader.get_reader(this, 5));
        BookLoan book_loan6 = new BookLoan(Book.get_book(this,6), Reader.get_reader(this, 6));
        book_loans.add(book_loan1);
        book_loans.add(book_loan2);
        book_loans.add(book_loan3);
        book_loans.add(book_loan4);
        book_loans.add(book_loan5);
        book_loans.add(book_loan6);
    }

    // Constructors

    public Library()
    {
        create_authors();
        create_sections();
        create_books();
        create_readers();
        create_bookloans();
    }

    // Sign In function, adds a new reader.

    public int add_reader()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your first name: ");
        String first_name = scanner.nextLine();
        System.out.print("Enter your last name: ");
        String last_name = scanner.nextLine();
        System.out.print("Enter your birthday year: ");
        int birthday_year = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter an username: ");
        String username = scanner.nextLine();
        System.out.print("Enter a password: ");
        String password = scanner.nextLine();
        Reader new_reader = new Reader(first_name, last_name, birthday_year, username, password, null);
        readers.add(new_reader);
        return new_reader.get_id();
    }

    // Shows on the screen all the books based on the user's preferences.

    public void show_books()
    {
        System.out.println("Pick a number!\n");
        System.out.println("1. Show all books.");
        System.out.println("2. Show all books of an author.");
        System.out.println("3. Show all books with a specific genre.");
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        scanner.nextLine();
        while (option < 1 || option > 3)
        {
            System.out.println("Invalid option!");
            option = scanner.nextInt();
            scanner.nextLine();
        }
        if(option == 1)
        {
            Book.show_all_books(this);
        } else if(option == 2)
        {
            System.out.println("What author?");
            boolean exists = false;
            String author_s_name = scanner.nextLine();
            Author author = Author.get_author(this, author_s_name);
            for(Book book : books)
            {
                if(author == book.get_author())
                {
                    System.out.println(book);
                    exists = true;
                }
            }
            if (!exists)
            {
                System.out.println("Author not found!");
            }
        } else
        {
            System.out.println("What section?");
            boolean exists = false;
            String genre_s_name = scanner.nextLine();
            Section section = Section.get_section(this, genre_s_name);
            for(Book book : books)
            {
                if(section == book.get_section())
                {
                    System.out.println(book);
                    exists = true;
                }
            }
            if (!exists)
            {
                System.out.println("Section not found!");
            }
        }
    }

    // This functions checks all the books that the user read, and finds the most read section.

    public void fav_section(int user_id)
    {
        int max_freq = -1;
        Section temp_section = null;
        for(Section section : sections)
        {
            int aux = section.get_frequency(books, this.get_reader(user_id));
            if(aux > max_freq)
            {
                max_freq = aux;
                temp_section = section;
            }
        }
        System.out.println("Your favorite section is " + temp_section.get_name() + "! (" + max_freq + " books)");
    }

    // Donate a book, add a new book to the library.

    public void add_book()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the title of the book :");
        String title = scanner.nextLine();
        System.out.println("How many pages ?");
        int pages = scanner.nextInt();
        System.out.println("What year was it written?");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the author of the book :");
        String authors_name = scanner.nextLine();
        Author author = Author.get_author(this, authors_name);
        if(!authors.contains(author))
        {
            authors.add(author);
        }
        System.out.println("Enter the genre of the book :");
        String genre = scanner.nextLine();
        Section section = Section.get_section(this, genre);
        if(!sections.contains(section))
        {
            sections.add(section);
        }
        books.add(new Book(title, pages, year, author, section));
        System.out.println("Thanks for your donation !");
    }

    // Borrow a book function,

    public void borrow_book(int user_id)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the title of the book :");
        String title = scanner.nextLine();
        Book borrowed_book = Book.get_book(this, title);
        while(borrowed_book == null)
        {
            System.out.println("That book does not exist! Try again :");
            title = scanner.nextLine();
            borrowed_book = Book.get_book(this, title);
        }
        BookLoan loan = new BookLoan(borrowed_book, get_reader(user_id));
        book_loans.add(loan);
        System.out.println("You borrowed the book succesfully!");
    }

    // Return a book function

    public void return_book(int user_id)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the title of the book :");
        String title = scanner.nextLine();
        Book borrowed_book = Book.get_book(this, title);
        while(borrowed_book == null)
        {
            System.out.println("You don't have that book! Try again.");
            title = scanner.nextLine();
            borrowed_book = Book.get_book(this, title);
        }
        Iterator<BookLoan> iterator = book_loans.iterator();
        while (iterator.hasNext()) {
            BookLoan loan_ = iterator.next();
            if (loan_.get_reader().get_id() == user_id && loan_.get_book().equals(borrowed_book)) {
                loan_.get_reader().add_read_book(loan_.get_book().get_id());
                iterator.remove();
                break;
            }
        }
        System.out.println("You returned the book succesfully!");
    }
}
