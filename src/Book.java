import java.util.List;
import java.util.Objects;

public class Book {
    private static int last_id = 0;
    private int id;
    private String title;
    private Author author;
    private int pages;
    private Section section;
    private int year;

    // Constructors

    public Book(String title, int pages, int year, Author author, Section section) {
        this.title = title;
        this.pages = pages;
        this.year = year;
        this.author = author;
        this.section = section;
        this.id = ++last_id;
    }

    // Getters

    public Author get_author()
    {
        return author;
    }

    public Section get_section()
    {
        return section;
    }

    public int get_id()
    {
        return id;
    }

    public String get_title()
    {
        return title;
    }

    // Static methods

    // Returns a book based on an id.

    public static Book get_book(Library library, int id)
    {
        for (Book book : library.get_books())
        {
            if(book.get_id() == id)
            {
                return book;
            }
        }
        return null;
    }

    // Returns a book based on a title.

    public static Book get_book(Library library, String title)
    {
        for (Book book : library.get_books())
        {
            if(Objects.equals(book.get_title(), title))
            {
                return book;
            }
        }
        return null;
    }

    // A function that shows all books of the library.

    public static void show_all_books(Library library)
    {
        List<Book> books = library.get_books();
        for(Book book : books)
        {
            System.out.println(book);
        }
    }

    @Override
    public String toString()
    {
        return "{" + title + ", " + author + ", " + pages + ", " + year + ", " + section + "}";
    }
}