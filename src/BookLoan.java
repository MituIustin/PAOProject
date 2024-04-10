import java.util.List;

public class BookLoan {
    private static int last_id = 0;
    private int id;
    private Book book;
    private Reader reader;

    // Constructors

    public BookLoan(Book book, Reader reader){
        this.book = book;
        this.reader = reader;
        this.id = ++last_id;
    }

    // Getters

    public Reader get_reader()
    {
        return this.reader;
    }

    public Book get_book()
    {
        return this.book;
    }

    // Static methods

    // This function takes a user and finds all the books that he's currently has.

    public static void show_borrowed_books(List<BookLoan> loans, int user_id)
    {
        for(BookLoan loan : loans)
        {
            if(loan.get_reader().get_id() == user_id)
            {
                System.out.println(loan);
            }
        }
    }

    @Override
    public String toString()
    {
        return this.reader.toString() + "borrowed " + this.book.toString();
    }

}
